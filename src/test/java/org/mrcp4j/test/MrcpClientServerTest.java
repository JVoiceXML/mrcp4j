/*
 * MRCP4J - Java API implementation of MRCPv2 specification
 *
 * Copyright (C) 2005-2008 SpeechForge - http://www.speechforge.org
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
package org.mrcp4j.test;

import static org.mrcp4j.client.MrcpProvider.PROTOCOL_TCP_MRCPv2;

import java.net.InetAddress;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mrcp4j.MrcpMethodName;
import org.mrcp4j.MrcpRequestState;
import org.mrcp4j.client.MrcpChannel;
import org.mrcp4j.client.MrcpFactory;
import org.mrcp4j.client.MrcpProvider;
import org.mrcp4j.message.MrcpResponse;
import org.mrcp4j.message.request.MrcpRequest;
import org.mrcp4j.message.request.MrcpRequestFactory.UnimplementedRequest;
import org.mrcp4j.message.request.StartInputTimersRequest;
import org.mrcp4j.message.request.StopRequest;
import org.mrcp4j.server.MrcpServerSocket;
import org.mrcp4j.server.MrcpSession;
import org.mrcp4j.server.provider.RecogOnlyRequestHandler;
import org.mrcp4j.test.util.MrcpTestUtil;

public class MrcpClientServerTest {

    private static final int MRCP_SERVER_PORT = 32414;

    private MrcpServerSocket _mrcpServer;
    private MrcpChannel _recogChannel;

    // for some reason the @Before method is not getting called (maybe something to do with maven?)
    // so instead explicitly call the method in each test
    @Before
    public void setUp() throws Exception {

        MrcpTestUtil.configureLog4j(this);

        if (_mrcpServer != null) // TODO: shutdown server in tear down method instead
            return;

        String channelID = Long.toString(System.currentTimeMillis()) + "@" + "SPEECHRECOG";

        // setup server
        _mrcpServer = new MrcpServerSocket(MRCP_SERVER_PORT);
        _mrcpServer.openChannel(channelID, new DummyRequestHandler());

        // setup client
        MrcpProvider provider = MrcpFactory.newInstance().createProvider();
        _recogChannel = provider.createChannel(channelID, InetAddress.getLocalHost(), MRCP_SERVER_PORT, PROTOCOL_TCP_MRCPv2);
    }

    @Test
    public void testSetParams() throws Exception {

        // force setup
        setUp();

        MrcpRequest request = _recogChannel.createRequest(MrcpMethodName.SET_PARAMS);
        MrcpResponse response = _recogChannel.sendRequest(request);
        Assert.assertEquals(MrcpResponse.STATUS_SUCCESS, response.getStatusCode());
    }

    @Test
    @Ignore()  
    public void testLargeGrammar() throws Exception {

        // force setup
        setUp();

        MrcpRequest request = _recogChannel.createRequest(MrcpMethodName.DEFINE_GRAMMAR);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < 256; i++) { // TODO: make this larger (currently causes the response to never be received)
            content.append('a');
        }
        request.setContent("application/jsgf", null, content.toString());
        MrcpResponse response = _recogChannel.sendRequest(request);
        Assert.assertEquals(MrcpResponse.STATUS_SUCCESS, response.getStatusCode());
    }

    private static class DummyRequestHandler implements RecogOnlyRequestHandler {

        public MrcpResponse startInputTimers(StartInputTimersRequest request, MrcpSession session) {
            return session.createResponse(MrcpResponse.STATUS_SUCCESS, MrcpRequestState.COMPLETE);
        }

        public MrcpResponse stop(StopRequest request, MrcpSession session) {
            return session.createResponse(MrcpResponse.STATUS_SUCCESS, MrcpRequestState.COMPLETE);
        }

        public MrcpResponse getParams(UnimplementedRequest request, MrcpSession session) {
            return session.createResponse(MrcpResponse.STATUS_SUCCESS, MrcpRequestState.COMPLETE);
        }

        public MrcpResponse setParams(UnimplementedRequest request, MrcpSession session) {
            return session.createResponse(MrcpResponse.STATUS_SUCCESS, MrcpRequestState.COMPLETE);
        }

        public MrcpResponse defineGrammar(UnimplementedRequest request, MrcpSession session) {
            return session.createResponse(MrcpResponse.STATUS_SUCCESS, MrcpRequestState.COMPLETE);
        }

        public MrcpResponse getResult(UnimplementedRequest request, MrcpSession session) {
            return session.createResponse(MrcpResponse.STATUS_SUCCESS, MrcpRequestState.COMPLETE);
        }

        public MrcpResponse interpret(UnimplementedRequest request, MrcpSession session) {
            return session.createResponse(MrcpResponse.STATUS_SUCCESS, MrcpRequestState.COMPLETE);
        }

        public MrcpResponse recognize(UnimplementedRequest request, MrcpSession session) {
            return session.createResponse(MrcpResponse.STATUS_SUCCESS, MrcpRequestState.COMPLETE);
        }
        
    }
}
