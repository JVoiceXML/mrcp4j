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
import java.io.OutputStream;
import java.net.Socket;
import java.text.ParseException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.mrcp4j.MrcpRequestState;
import org.mrcp4j.message.MrcpEvent;
import org.mrcp4j.message.MrcpResponse;
import org.mrcp4j.message.header.IllegalValueException;
import org.mrcp4j.message.request.MrcpRequest;

/**
 *
 * @author Niels Godfredsen {@literal <}<a href="mailto:ngodfredsen@users.sourceforge.net">ngodfredsen@users.sourceforge.net</a>{@literal >}
 */
public class MrcpProtocolHandler implements Runnable {

    private static Logger _log = LogManager.getLogger(MrcpProtocolHandler.class);

    private final MrcpRequestProcessor _requestProcessor;
    private final Socket _socket;

    public MrcpProtocolHandler(MrcpRequestProcessor requestProcessor, Socket socket) {
        _requestProcessor = requestProcessor;
        _socket = socket;
    }

    @Override
    public void run() {
        _log.debug("OPENED");
        try {
            InputStream in = _socket.getInputStream();
            OutputStream out = _socket.getOutputStream();
            MrcpRequestDecoder decoder = new MrcpRequestDecoder();
            MrcpMessageEncoder encoder = new MrcpMessageEncoder();

            while (!_socket.isClosed()) {
                MrcpRequest request;
                try {
                    request = decoder.decode(in);
                    if (request == null) {
                        break;
                    }
                } catch (ParseException e) {
                    _log.debug(e, e);
                    break;
                } catch (IllegalValueException e) {
                    _log.debug(e, e);
                    break;
                }

                processRequest(request, out, encoder);
            }
        } catch (IOException e) {
            _log.warn("Connection error: " + e.getMessage(), e);
        } finally {
            try {
                _socket.close();
            } catch (IOException e) {
                _log.debug("Error closing socket", e);
            }
            _log.debug("CLOSED");
        }
    }

    private void processRequest(MrcpRequest request, OutputStream out, MrcpMessageEncoder encoder) throws IOException {
        MrcpResponse response = _requestProcessor.processRequest(request);
        encoder.encode(response, out);

        MrcpRequestState requestState = response.getRequestState();

        while (!requestState.equals(MrcpRequestState.COMPLETE) && !_socket.isClosed()) {
            MrcpEvent event = _requestProcessor.getNextEvent(request);
            if (event != null) {
                encoder.encode(event, out);
                requestState = event.getRequestState();
            } else {
                break;
            }
        }
    }
}
