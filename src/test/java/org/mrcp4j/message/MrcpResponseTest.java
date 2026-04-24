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
package org.mrcp4j.message;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link MrcpResponse}.
 */
public class MrcpResponseTest {

    @Test
    public void testStatusConstants() {
        // Success codes
        Assert.assertEquals("STATUS_SUCCESS should be 200", 200, MrcpResponse.STATUS_SUCCESS);
        Assert.assertEquals("STATUS_SUCCESS_SOME_OPTIONAL_HEADERS_IGNORED should be 201", 201, 
                    MrcpResponse.STATUS_SUCCESS_SOME_OPTIONAL_HEADERS_IGNORED);
        
        // Client failure codes
        Assert.assertEquals("STATUS_METHOD_NOT_ALLOWED should be 401", 401, MrcpResponse.STATUS_METHOD_NOT_ALLOWED);
        Assert.assertEquals("STATUS_METHOD_NOT_VALID_IN_STATE should be 402", 402, MrcpResponse.STATUS_METHOD_NOT_VALID_IN_STATE);
        Assert.assertEquals("STATUS_UNSUPPORTED_HEADER should be 403", 403, MrcpResponse.STATUS_UNSUPPORTED_HEADER);
        Assert.assertEquals("STATUS_ILLEGAL_VALUE_FOR_HEADER should be 404", 404, MrcpResponse.STATUS_ILLEGAL_VALUE_FOR_HEADER);
        Assert.assertEquals("STATUS_RESOURCE_NOT_ALLOCATED should be 405", 405, MrcpResponse.STATUS_RESOURCE_NOT_ALLOCATED);
        Assert.assertEquals("STATUS_MANDATORY_HEADER_MISSING should be 406", 406, MrcpResponse.STATUS_MANDATORY_HEADER_MISSING);
        Assert.assertEquals("STATUS_OPERATION_FAILED should be 407", 407, MrcpResponse.STATUS_OPERATION_FAILED);
        Assert.assertEquals("STATUS_UNRECOGNIZED_MESSAGE_ENTITY should be 408", 408, MrcpResponse.STATUS_UNRECOGNIZED_MESSAGE_ENTITY);
        Assert.assertEquals("STATUS_UNSUPPORTED_HEADER_VALUE should be 409", 409, MrcpResponse.STATUS_UNSUPPORTED_HEADER_VALUE);
        Assert.assertEquals("STATUS_NON_MONOTONIC_SEQUENCE_NUMBER should be 410", 410, MrcpResponse.STATUS_NON_MONOTONIC_SEQUENCE_NUMBER);
        
        // Server failure codes
        Assert.assertEquals("STATUS_SERVER_INTERNAL_ERROR should be 501", 501, MrcpResponse.STATUS_SERVER_INTERNAL_ERROR);
        Assert.assertEquals("STATUS_PROTOCOL_VERSION_NOT_SUPPORTED should be 502", 502, MrcpResponse.STATUS_PROTOCOL_VERSION_NOT_SUPPORTED);
        Assert.assertEquals("STATUS_PROXY_TIMEOUT should be 503", 503, MrcpResponse.STATUS_PROXY_TIMEOUT);
        Assert.assertEquals("STATUS_MESSAGE_TOO_LARGE should be 504", 504, MrcpResponse.STATUS_MESSAGE_TOO_LARGE);
    }

    @Test
    public void testSetAndGetStatusCode() {
        MrcpResponse response = new MrcpResponse();
        
        // Test initial state
        Assert.assertEquals("Initial status code should be -1", -1, response.getStatusCode());
        
        // Test setting valid status codes
        response.setStatusCode(MrcpResponse.STATUS_SUCCESS);
        Assert.assertEquals("Status code should be set to SUCCESS", MrcpResponse.STATUS_SUCCESS, response.getStatusCode());
        
        response.setStatusCode(MrcpResponse.STATUS_METHOD_NOT_ALLOWED);
        Assert.assertEquals("Status code should be set to METHOD_NOT_ALLOWED", MrcpResponse.STATUS_METHOD_NOT_ALLOWED, response.getStatusCode());
        
        response.setStatusCode(MrcpResponse.STATUS_SERVER_INTERNAL_ERROR);
        Assert.assertEquals("Status code should be set to SERVER_INTERNAL_ERROR", MrcpResponse.STATUS_SERVER_INTERNAL_ERROR, response.getStatusCode());
    }

    @Test
    public void testSetStatusCodeWithCustomValues() {
        MrcpResponse response = new MrcpResponse();
        
        // Test setting custom status code
        short customCode = 999;
        response.setStatusCode(customCode);
        Assert.assertEquals("Custom status code should be set", customCode, response.getStatusCode());
        
        // Test setting zero
        response.setStatusCode((short) 0);
        Assert.assertEquals("Zero status code should be set", 0, response.getStatusCode());
        
        // Test setting negative value
        response.setStatusCode((short) -100);
        Assert.assertEquals("Negative status code should be set", -100, response.getStatusCode());
    }

    @Test
    public void testGetStatusDescForKnownCodes() {
        MrcpResponse response = new MrcpResponse();
        
        response.setStatusCode(MrcpResponse.STATUS_SUCCESS);
        Assert.assertEquals("STATUS_SUCCESS description should match", "STATUS_SUCCESS", response.getStatusDesc());
        
        response.setStatusCode(MrcpResponse.STATUS_METHOD_NOT_ALLOWED);
        Assert.assertEquals("STATUS_METHOD_NOT_ALLOWED description should match", "STATUS_METHOD_NOT_ALLOWED", response.getStatusDesc());
        
        response.setStatusCode(MrcpResponse.STATUS_SERVER_INTERNAL_ERROR);
        Assert.assertEquals("STATUS_SERVER_INTERNAL_ERROR description should match", "STATUS_SERVER_INTERNAL_ERROR", response.getStatusDesc());
        
        response.setStatusCode(MrcpResponse.STATUS_ILLEGAL_VALUE_FOR_HEADER);
        Assert.assertEquals("STATUS_ILLEGAL_VALUE_FOR_HEADER description should match", "STATUS_ILLEGAL_VALUE_FOR_HEADER", response.getStatusDesc());
    }

    @Test
    public void testGetStatusDescForUnknownCode() {
        MrcpResponse response = new MrcpResponse();
        
        response.setStatusCode((short) 999);
        Assert.assertNull("Unknown status code should return null description", response.getStatusDesc());
        
        response.setStatusCode((short) -1);
        Assert.assertNull("Invalid status code should return null description", response.getStatusDesc());
    }

    @Test
    public void testGetStatusDescForAllDefinedCodes() {
        MrcpResponse response = new MrcpResponse();
        
        // Test all success codes
        response.setStatusCode(MrcpResponse.STATUS_SUCCESS);
        Assert.assertNotNull("STATUS_SUCCESS should have description", response.getStatusDesc());
        
        response.setStatusCode(MrcpResponse.STATUS_SUCCESS_SOME_OPTIONAL_HEADERS_IGNORED);
        Assert.assertNotNull("STATUS_SUCCESS_SOME_OPTIONAL_HEADERS_IGNORED should have description", response.getStatusDesc());
        
        // Test all client failure codes
        response.setStatusCode(MrcpResponse.STATUS_METHOD_NOT_ALLOWED);
        Assert.assertNotNull("STATUS_METHOD_NOT_ALLOWED should have description", response.getStatusDesc());
        
        response.setStatusCode(MrcpResponse.STATUS_METHOD_NOT_VALID_IN_STATE);
        Assert.assertNotNull("STATUS_METHOD_NOT_VALID_IN_STATE should have description", response.getStatusDesc());
        
        response.setStatusCode(MrcpResponse.STATUS_UNSUPPORTED_HEADER);
        Assert.assertNotNull("STATUS_UNSUPPORTED_HEADER should have description", response.getStatusDesc());
        
        response.setStatusCode(MrcpResponse.STATUS_ILLEGAL_VALUE_FOR_HEADER);
        Assert.assertNotNull("STATUS_ILLEGAL_VALUE_FOR_HEADER should have description", response.getStatusDesc());
        
        response.setStatusCode(MrcpResponse.STATUS_RESOURCE_NOT_ALLOCATED);
        Assert.assertNotNull("STATUS_RESOURCE_NOT_ALLOCATED should have description", response.getStatusDesc());
        
        response.setStatusCode(MrcpResponse.STATUS_MANDATORY_HEADER_MISSING);
        Assert.assertNotNull("STATUS_MANDATORY_HEADER_MISSING should have description", response.getStatusDesc());
        
        response.setStatusCode(MrcpResponse.STATUS_OPERATION_FAILED);
        Assert.assertNotNull("STATUS_OPERATION_FAILED should have description", response.getStatusDesc());
        
        response.setStatusCode(MrcpResponse.STATUS_UNRECOGNIZED_MESSAGE_ENTITY);
        Assert.assertNotNull("STATUS_UNRECOGNIZED_MESSAGE_ENTITY should have description", response.getStatusDesc());
        
        response.setStatusCode(MrcpResponse.STATUS_UNSUPPORTED_HEADER_VALUE);
        Assert.assertNotNull("STATUS_UNSUPPORTED_HEADER_VALUE should have description", response.getStatusDesc());
        
        response.setStatusCode(MrcpResponse.STATUS_NON_MONOTONIC_SEQUENCE_NUMBER);
        Assert.assertNotNull("STATUS_NON_MONOTONIC_SEQUENCE_NUMBER should have description", response.getStatusDesc());
        
        // Test all server failure codes
        response.setStatusCode(MrcpResponse.STATUS_SERVER_INTERNAL_ERROR);
        Assert.assertNotNull("STATUS_SERVER_INTERNAL_ERROR should have description", response.getStatusDesc());
        
        response.setStatusCode(MrcpResponse.STATUS_PROTOCOL_VERSION_NOT_SUPPORTED);
        Assert.assertNotNull("STATUS_PROTOCOL_VERSION_NOT_SUPPORTED should have description", response.getStatusDesc());
        
        response.setStatusCode(MrcpResponse.STATUS_PROXY_TIMEOUT);
        Assert.assertNotNull("STATUS_PROXY_TIMEOUT should have description", response.getStatusDesc());
        
        response.setStatusCode(MrcpResponse.STATUS_MESSAGE_TOO_LARGE);
        Assert.assertNotNull("STATUS_MESSAGE_TOO_LARGE should have description", response.getStatusDesc());
    }

    @Test
    public void testInheritanceFromMrcpServerMessage() {
        MrcpResponse response = new MrcpResponse();
        Assert.assertTrue("Should be instance of MrcpServerMessage", response instanceof MrcpServerMessage);
    }

    @Test
    public void testStatusCodeRanges() {
        // Test that status codes are in expected ranges
        Assert.assertTrue("Success codes should be 2xx", MrcpResponse.STATUS_SUCCESS >= 200 && MrcpResponse.STATUS_SUCCESS < 300);
        Assert.assertTrue("Success codes should be 2xx", MrcpResponse.STATUS_SUCCESS_SOME_OPTIONAL_HEADERS_IGNORED >= 200 && MrcpResponse.STATUS_SUCCESS_SOME_OPTIONAL_HEADERS_IGNORED < 300);
        
        Assert.assertTrue("Client error codes should be 4xx", MrcpResponse.STATUS_METHOD_NOT_ALLOWED >= 400 && MrcpResponse.STATUS_METHOD_NOT_ALLOWED < 500);
        Assert.assertTrue("Client error codes should be 4xx", MrcpResponse.STATUS_ILLEGAL_VALUE_FOR_HEADER >= 400 && MrcpResponse.STATUS_ILLEGAL_VALUE_FOR_HEADER < 500);
        Assert.assertTrue("Client error codes should be 4xx", MrcpResponse.STATUS_NON_MONOTONIC_SEQUENCE_NUMBER >= 400 && MrcpResponse.STATUS_NON_MONOTONIC_SEQUENCE_NUMBER < 500);
        
        Assert.assertTrue("Server error codes should be 5xx", MrcpResponse.STATUS_SERVER_INTERNAL_ERROR >= 500 && MrcpResponse.STATUS_SERVER_INTERNAL_ERROR < 600);
        Assert.assertTrue("Server error codes should be 5xx", MrcpResponse.STATUS_PROTOCOL_VERSION_NOT_SUPPORTED >= 500 && MrcpResponse.STATUS_PROTOCOL_VERSION_NOT_SUPPORTED < 600);
        Assert.assertTrue("Server error codes should be 5xx", MrcpResponse.STATUS_MESSAGE_TOO_LARGE >= 500 && MrcpResponse.STATUS_MESSAGE_TOO_LARGE < 600);
    }

    @Test
    public void testStatusCodeConstants() {
        // Verify status codes are unique
        short[] codes = {
            MrcpResponse.STATUS_SUCCESS,
            MrcpResponse.STATUS_SUCCESS_SOME_OPTIONAL_HEADERS_IGNORED,
            MrcpResponse.STATUS_METHOD_NOT_ALLOWED,
            MrcpResponse.STATUS_METHOD_NOT_VALID_IN_STATE,
            MrcpResponse.STATUS_UNSUPPORTED_HEADER,
            MrcpResponse.STATUS_ILLEGAL_VALUE_FOR_HEADER,
            MrcpResponse.STATUS_RESOURCE_NOT_ALLOCATED,
            MrcpResponse.STATUS_MANDATORY_HEADER_MISSING,
            MrcpResponse.STATUS_OPERATION_FAILED,
            MrcpResponse.STATUS_UNRECOGNIZED_MESSAGE_ENTITY,
            MrcpResponse.STATUS_UNSUPPORTED_HEADER_VALUE,
            MrcpResponse.STATUS_NON_MONOTONIC_SEQUENCE_NUMBER,
            MrcpResponse.STATUS_SERVER_INTERNAL_ERROR,
            MrcpResponse.STATUS_PROTOCOL_VERSION_NOT_SUPPORTED,
            MrcpResponse.STATUS_PROXY_TIMEOUT,
            MrcpResponse.STATUS_MESSAGE_TOO_LARGE
        };
        
        // Check that all codes are unique
        for (int i = 0; i < codes.length; i++) {
            for (int j = i + 1; j < codes.length; j++) {
                Assert.assertNotEquals("Status codes should be unique", codes[i], codes[j]);
            }
        }
    }
}