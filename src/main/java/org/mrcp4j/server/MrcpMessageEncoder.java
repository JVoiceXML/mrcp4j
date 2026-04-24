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

import static org.mrcp4j.message.MrcpMessage.CRLF;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.mrcp4j.message.MrcpEvent;
import org.mrcp4j.message.MrcpResponse;
import org.mrcp4j.message.MrcpServerMessage;
import org.mrcp4j.message.header.MrcpHeader;


/**
 * Encodes {@link org.mrcp4j.message.MrcpMessage} instances into MRCPv2 specification format.
 *
 * @author Niels Godfredsen {@literal <}<a href="mailto:ngodfredsen@users.sourceforge.net">ngodfredsen@users.sourceforge.net</a>{@literal >}
 */
public class MrcpMessageEncoder {

    private StringBuilder _encodeBuf = new StringBuilder();

    public void encode(Object message, OutputStream out) throws IOException {

        // clear encode buffer
        _encodeBuf.delete(0, _encodeBuf.length());

        // append start line
        int offset = -1;
        if (message instanceof MrcpResponse) {
            offset = appendResponseLine(_encodeBuf, ((MrcpResponse) message));
        } else if (message instanceof MrcpEvent) {
            offset = appendEventLine(_encodeBuf, ((MrcpEvent) message));
        } else {
            throw new IOException("Unsupported message type: " + message.getClass().getName());
        }

        // append headers
        MrcpServerMessage serverMessage = (MrcpServerMessage) message;
        for (MrcpHeader header : serverMessage.getHeaders()) {
            _encodeBuf.append(header.toString()).append(CRLF);
        }

        // append CRLF line
        _encodeBuf.append(CRLF);

        // append message body if present
        if (serverMessage.hasContent()) {
            _encodeBuf.append(serverMessage.getContent());
        }

        // encode to bytes to get accurate byte count (important for non-ASCII content bodies)
        // the placeholder space at 'offset' acts as the separator that follows the length field
        byte[] withoutLength = _encodeBuf.toString().getBytes(StandardCharsets.UTF_8);

        // compute message-length: byte count of the full message after inserting the length string
        // at position 'offset' (the placeholder space is kept and follows the length digits)
        // solve: messageLength = withoutLength.length + len(Integer.toString(messageLength))
        int base = withoutLength.length;
        int d = Integer.toString(base).length();
        int messageLength = base + d;
        if (Integer.toString(messageLength).length() > d) {
            messageLength = base + d + 1;
        }
        String messageLengthString = Integer.toString(messageLength);

        // assemble final byte array: bytes[0..offset) + lengthString + bytes[offset..end)
        // (the placeholder space at 'offset' is preserved as the separator after the length)
        byte[] lengthBytes = messageLengthString.getBytes(StandardCharsets.US_ASCII);
        byte[] result = new byte[messageLength];
        System.arraycopy(withoutLength, 0, result, 0, offset);
        System.arraycopy(lengthBytes, 0, result, offset, lengthBytes.length);
        System.arraycopy(withoutLength, offset, result, offset + lengthBytes.length,
                withoutLength.length - offset);

        serverMessage.setMessageLength(messageLength);

        // write result to out
        out.write(result);
        out.flush();
    }

    private static int appendEventLine(StringBuilder encodeBuf, MrcpEvent event) {
        String version = event.getVersion();
        encodeBuf.append(version).append(' ');
        // message length will be inserted at this position after headers and content are encoded
        encodeBuf.append(' ').append(event.getEventName());
        encodeBuf.append(' ').append(event.getRequestID());
        encodeBuf.append(' ').append(event.getRequestState());
        encodeBuf.append(CRLF);
        return version.length() + 1;
    }

    private static int appendResponseLine(StringBuilder encodeBuf, MrcpResponse response) {
        String version = response.getVersion();
        encodeBuf.append(version).append(' ');
        // message length will be inserted at this position after headers and content are encoded
        encodeBuf.append(' ').append(response.getRequestID());
        encodeBuf.append(' ').append(response.getStatusCode());
        encodeBuf.append(' ').append(response.getRequestState());
        encodeBuf.append(CRLF);
        return version.length() + 1;
    }
}