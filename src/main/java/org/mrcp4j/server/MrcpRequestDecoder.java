/*
 * MRCP4J - Java API implementation of MRCPv2 specification
 *
 * Copyright (C) 2005-2006 SpeechForge - http://www.speechforge.org
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307, USA.
 *
 * Contact: ngodfredsen@users.sourceforge.net
 *
 */
package org.mrcp4j.server;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

import org.mrcp4j.message.header.IllegalValueException;
import org.mrcp4j.message.header.MrcpHeader;
import org.mrcp4j.message.header.MrcpHeaderName;
import org.mrcp4j.message.request.MrcpRequest;
import org.mrcp4j.message.request.MrcpRequestFactory;

/**
 * Decodes request messages received in MRCPv2 format into {@link org.mrcp4j.message.request.MrcpRequest} instances.
 *
 * @author Niels Godfredsen {@literal <}<a href="mailto:ngodfredsen@users.sourceforge.net">ngodfredsen@users.sourceforge.net</a>{@literal >}
 */
public class MrcpRequestDecoder {

    private StringBuilder decodeBuf = new StringBuilder();

    public MrcpRequest decode(InputStream in) throws IOException, ParseException, IllegalValueException {
        // create request from request-line
        String requestLine = readLine(in);
        if (requestLine == null) {
            return null;
        }
        MrcpRequest request = createRequest(requestLine);

        // read message-header
        String line;
        while ((line = readLine(in)) != null && !(line = line.trim()).equals("")) {
            // TODO: handle multi-line headers
            int index = line.indexOf(':');
            if (index < 1) {
                throw new ParseException("Incorrect message-header format!", -1);
            }
            String name = line.substring(0, index);
            String value = line.substring(index + 1).trim();
            MrcpHeader header = MrcpHeaderName.createHeader(name, value);
            request.addHeader(header);
        }

        // read request message body if present
        MrcpHeader contentLengthHeader = request.getHeader(MrcpHeaderName.CONTENT_LENGTH);
        int contentLength = 0;
        if (contentLengthHeader != null) {
            contentLength = ((Integer) contentLengthHeader.getValueObject()).intValue();
        }
        if (contentLength > 0) {
            if (contentLength > 1024 * 1024) { // 1 MB limit
                throw new IOException("Content length exceeds maximum allowed size: " + contentLength);
            }
            byte[] body = new byte[contentLength];
            int read = 0;
            while (read < contentLength) {
                int n = in.read(body, read, contentLength - read);
                if (n < 0) {
                    throw new IOException("Unexpected end of stream while reading message body");
                }
                read += n;
            }
            request.setContent(new String(body, 0, contentLength, java.nio.charset.StandardCharsets.UTF_8));
        }

        return request;
    }

    private String readLine(InputStream in) throws IOException {
        decodeBuf.delete(0, decodeBuf.length());
        int b;
        while ((b = in.read()) >= 0) {
            switch (b) {
            case '\r':
                break;
            case '\n':
                return decodeBuf.toString();
            default:
                decodeBuf.append((char) b);
            }
        }
        // end of stream
        return decodeBuf.length() > 0 ? decodeBuf.toString() : null;
    }

    private static final int REQUEST_LINE_MRCP_VERSION_PART   = 0;
    private static final int REQUEST_LINE_MESSAGE_LENGTH_PART = 1;
    private static final int REQUEST_LINE_METHOD_NAME_PART    = 2;
    private static final int REQUEST_LINE_REQUEST_ID_PART     = 3;
    private static final int REQUEST_LINE_PART_COUNT          = 4;

    public static MrcpRequest createRequest(String requestLine) throws ParseException {

        if (requestLine == null || (requestLine = requestLine.trim()).length() < 1) {
            throw new ParseException("No request-line provided!", -1);
        }

        String[] requestLineParts = requestLine.split(" ");
        if (requestLineParts.length != REQUEST_LINE_PART_COUNT) {
            throw new ParseException("Incorrect request-line format!", -1);
        }

        MrcpRequest request = null;

        // construct request from method-name
        try {
            request = MrcpRequestFactory.createRequest(requestLineParts[REQUEST_LINE_METHOD_NAME_PART]);
        } catch (IllegalArgumentException e) {
            String message = "Incorrect method-name format!";
            throw (ParseException) new ParseException(message, -1).initCause(e);
        }

        // mrcp-version
        request.setVersion(requestLineParts[REQUEST_LINE_MRCP_VERSION_PART]);  //TODO: need to check here if version is supported, or maybe at higher level...

        // message-length
        try {
            request.setMessageLength(
                Integer.parseInt(requestLineParts[REQUEST_LINE_MESSAGE_LENGTH_PART])
            );
        } catch (NumberFormatException e) {
            String message = "Incorrect message-length format!";
            throw (ParseException) new ParseException(message, -1).initCause(e);
        }

        // request-id
        try {
            request.setRequestID(
                Long.parseLong(requestLineParts[REQUEST_LINE_REQUEST_ID_PART])
            );
        } catch (NumberFormatException e) {
            String message = "Incorrect request-id format!";
            throw (ParseException) new ParseException(message, -1).initCause(e);
        }

        return request;
    }
}
