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

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Unit tests for {@link MrcpResponse}.
 */
public class MrcpResponseTest {

    @Test
    public void testStatusConstants() {
        // Success codes
        assertEquals("STATUS_SUCCESS should be 200", 200, MrcpResponse.STATUS_SUCCESS);
        assertEquals("STATUS_SUCCESS_SOME_OPTIONAL_HEADERS_IGNORED should be 201", 201, 
                    MrcpResponse.STATUS_SUCCESS_SOME_OPTIONAL_HEADERS_IGNORED);
        
        // Client failure codes
        assertEquals("STATUS_METHOD_NOT_ALLOWED should be 401", 401, MrcpResponse.STATUS_METHOD_NOT_ALLOWED);
        assertEquals("STATUS_METHOD_NOT_VALID_IN_STATE should be 402", 402, MrcpResponse.STATUS_METHOD_NOT_VALID_IN_STATE);
        assertEquals("STATUS_UNSUPPORTED_HEADER should be 403", 403, MrcpResponse.STATUS_UNSUPPORTED_HEADER);
        assertEquals("STATUS_ILLEGAL_VALUE_FOR_HEADER should be 404", 404, MrcpResponse.STATUS_ILLEGAL_VALUE_FOR_HEADER);
        assertEquals("STATUS_RESOURCE_NOT_ALLOCATED should be 405", 405, MrcpResponse.STATUS_RESOURCE_NOT_ALLOCATED);
        assertEquals("STATUS_MANDATORY_HEADER_MISSING should be 406", 406, MrcpResponse.STATUS_MANDATORY_HEADER_MISSING);
        assertEquals("STATUS_OPERATION_FAILED should be 407", 407, MrcpResponse.STATUS_OPERATION_FAILED);
        assertEquals("STATUS_UNRECOGNIZED_MESSAGE_ENTITY should be 408", 408, MrcpResponse.STATUS_UNRECOGNIZED_MESSAGE_ENTITY);
        assertEquals("STATUS_UNSUPPORTED_HEADER_VALUE should be 409", 409, MrcpResponse.STATUS_UNSUPPORTED_HEADER_VALUE);
        assertEquals("STATUS_NON_MONOTONIC_SEQUENCE_NUMBER should be 410", 410, MrcpResponse.STATUS_NON_MONOTONIC_SEQUENCE_NUMBER);
        
        // Server failure codes
        assertEquals("STATUS_SERVER_INTERNAL_ERROR should be 501", 501, MrcpResponse.STATUS_SERVER_INTERNAL_ERROR);
        assertEquals("STATUS_PROTOCOL_VERSION_NOT_SUPPORTED should be 502", 502, MrcpResponse.STATUS_PROTOCOL_VERSION_NOT_SUPPORTED);
        assertEquals("STATUS_PROXY_TIMEOUT should be 503", 503, MrcpResponse.STATUS_PROXY_TIMEOUT);
        assertEquals("STATUS_MESSAGE_TOO_LARGE should be 504", 504, MrcpResponse.STATUS_MESSAGE_TOO_LARGE);
    }

    @Test
    public void testSetAndGetStatusCode() {
        MrcpResponse response = new MrcpResponse();
        
        // Test initial state
        assertEquals("Initial status code should be -1", -1, response.getStatusCode());
        
        // Test setting valid status codes
        response.setStatusCode(MrcpResponse.STATUS_SUCCESS);
        assertEquals("Status code should be set to SUCCESS", MrcpResponse.STATUS_SUCCESS, response.getStatusCode());
        
        response.setStatusCode(MrcpResponse.STATUS_METHOD_NOT_ALLOWED);
        assertEquals("Status code should be set to METHOD_NOT_ALLOWED", MrcpResponse.STATUS_METHOD_NOT_ALLOWED, response.getStatusCode());
        
        response.setStatusCode(MrcpResponse.STATUS_SERVER_INTERNAL_ERROR);
        assertEquals("Status code should be set to SERVER_INTERNAL_ERROR", MrcpResponse.STATUS_SERVER_INTERNAL_ERROR, response.getStatusCode());
    }

    @Test
    public void testSetStatusCodeWithCustomValues() {
        MrcpResponse response = new MrcpResponse();
        
        // Test setting custom status code
        short customCode = 999;
        response.setStatusCode(customCode);
        assertEquals("Custom status code should be set", customCode, response.getStatusCode());
        
        // Test setting zero
        response.setStatusCode((short) 0);
        assertEquals("Zero status code should be set", 0, response.getStatusCode());
        
        // Test setting negative value
        response.setStatusCode((short) -100);
        assertEquals("Negative status code should be set", -100, response.getStatusCode());
    }

    @Test
    public void testGetStatusDescForKnownCodes() {
        MrcpResponse response = new MrcpResponse();
        
        response.setStatusCode(MrcpResponse.STATUS_SUCCESS);
        assertEquals("STATUS_SUCCESS description should match", "STATUS_SUCCESS", response.getStatusDesc());
        
        response.setStatusCode(MrcpResponse.STATUS_METHOD_NOT_ALLOWED);
        assertEquals("STATUS_METHOD_NOT_ALLOWED description should match", "STATUS_METHOD_NOT_ALLOWED", response.getStatusDesc());
        
        response.setStatusCode(MrcpResponse.STATUS_SERVER_INTERNAL_ERROR);
        assertEquals("STATUS_SERVER_INTERNAL_ERROR description should match", "STATUS_SERVER_INTERNAL_ERROR", response.getStatusDesc());
        
        response.setStatusCode(MrcpResponse.STATUS_ILLEGAL_VALUE_FOR_HEADER);
        assertEquals("STATUS_ILLEGAL_VALUE_FOR_HEADER description should match", "STATUS_ILLEGAL_VALUE_FOR_HEADER", response.getStatusDesc());
    }

    @Test
    public void testGetStatusDescForUnknownCode() {
        MrcpResponse response = new MrcpResponse();
        
        response.setStatusCode((short) 999);
        assertNull("Unknown status code should return null description", response.getStatusDesc());
        
        response.setStatusCode((short) -1);
        assertNull("Invalid status code should return null description", response.getStatusDesc());
    }

    @Test
    public void testGetStatusDescForAllDefinedCodes() {
        MrcpResponse response = new MrcpResponse();
        
        // Test all success codes
        response.setStatusCode(MrcpResponse.STATUS_SUCCESS);
        assertNotNull("STATUS_SUCCESS should have description", response.getStatusDesc());
        
        response.setStatusCode(MrcpResponse.STATUS_SUCCESS_SOME_OPTIONAL_HEADERS_IGNORED);
        assertNotNull("STATUS_SUCCESS_SOME_OPTIONAL_HEADERS_IGNORED should have description", response.getStatusDesc());
        
        // Test all client failure codes
        response.setStatusCode(MrcpResponse.STATUS_METHOD_NOT_ALLOWED);
        assertNotNull("STATUS_METHOD_NOT_ALLOWED should have description", response.getStatusDesc());
        
        response.setStatusCode(MrcpResponse.STATUS_METHOD_NOT_VALID_IN_STATE);
        assertNotNull("STATUS_METHOD_NOT_VALID_IN_STATE should have description", response.getStatusDesc());
        
        response.setStatusCode(MrcpResponse.STATUS_UNSUPPORTED_HEADER);
        assertNotNull("STATUS_UNSUPPORTED_HEADER should have description", response.getStatusDesc());
        
        response.setStatusCode(MrcpResponse.STATUS_ILLEGAL_VALUE_FOR_HEADER);
        assertNotNull("STATUS_ILLEGAL_VALUE_FOR_HEADER should have description", response.getStatusDesc());
        
        response.setStatusCode(MrcpResponse.STATUS_RESOURCE_NOT_ALLOCATED);
        assertNotNull("STATUS_RESOURCE_NOT_ALLOCATED should have description", response.getStatusDesc());
        
        response.setStatusCode(MrcpResponse.STATUS_MANDATORY_HEADER_MISSING);
        assertNotNull("STATUS_MANDATORY_HEADER_MISSING should have description", response.getStatusDesc());
        
        response.setStatusCode(MrcpResponse.STATUS_OPERATION_FAILED);
        assertNotNull("STATUS_OPERATION_FAILED should have description", response.getStatusDesc());
        
        response.setStatusCode(MrcpResponse.STATUS_UNRECOGNIZED_MESSAGE_ENTITY);
        assertNotNull("STATUS_UNRECOGNIZED_MESSAGE_ENTITY should have description", response.getStatusDesc());
        
        response.setStatusCode(MrcpResponse.STATUS_UNSUPPORTED_HEADER_VALUE);
        assertNotNull("STATUS_UNSUPPORTED_HEADER_VALUE should have description", response.getStatusDesc());
        
        response.setStatusCode(MrcpResponse.STATUS_NON_MONOTONIC_SEQUENCE_NUMBER);
        assertNotNull("STATUS_NON_MONOTONIC_SEQUENCE_NUMBER should have description", response.getStatusDesc());
        
        // Test all server failure codes
        response.setStatusCode(MrcpResponse.STATUS_SERVER_INTERNAL_ERROR);
        assertNotNull("STATUS_SERVER_INTERNAL_ERROR should have description", response.getStatusDesc());
        
        response.setStatusCode(MrcpResponse.STATUS_PROTOCOL_VERSION_NOT_SUPPORTED);
        assertNotNull("STATUS_PROTOCOL_VERSION_NOT_SUPPORTED should have description", response.getStatusDesc());
        
        response.setStatusCode(MrcpResponse.STATUS_PROXY_TIMEOUT);
        assertNotNull("STATUS_PROXY_TIMEOUT should have description", response.getStatusDesc());
        
        response.setStatusCode(MrcpResponse.STATUS_MESSAGE_TOO_LARGE);
        assertNotNull("STATUS_MESSAGE_TOO_LARGE should have description", response.getStatusDesc());
    }

    @Test
    public void testInheritanceFromMrcpServerMessage() {
        MrcpResponse response = new MrcpResponse();
        assertTrue("Should be instance of MrcpServerMessage", response instanceof MrcpServerMessage);
    }

    @Test
    public void testStatusCodeRanges() {
        // Test that status codes are in expected ranges
        assertTrue("Success codes should be 2xx", MrcpResponse.STATUS_SUCCESS >= 200 && MrcpResponse.STATUS_SUCCESS < 300);
        assertTrue("Success codes should be 2xx", MrcpResponse.STATUS_SUCCESS_SOME_OPTIONAL_HEADERS_IGNORED >= 200 && MrcpResponse.STATUS_SUCCESS_SOME_OPTIONAL_HEADERS_IGNORED < 300);
        
        assertTrue("Client error codes should be 4xx", MrcpResponse.STATUS_METHOD_NOT_ALLOWED >= 400 && MrcpResponse.STATUS_METHOD_NOT_ALLOWED < 500);
        assertTrue("Client error codes should be 4xx", MrcpResponse.STATUS_ILLEGAL_VALUE_FOR_HEADER >= 400 && MrcpResponse.STATUS_ILLEGAL_VALUE_FOR_HEADER < 500);
        assertTrue("Client error codes should be 4xx", MrcpResponse.STATUS_NON_MONOTONIC_SEQUENCE_NUMBER >= 400 && MrcpResponse.STATUS_NON_MONOTONIC_SEQUENCE_NUMBER < 500);
        
        assertTrue("Server error codes should be 5xx", MrcpResponse.STATUS_SERVER_INTERNAL_ERROR >= 500 && MrcpResponse.STATUS_SERVER_INTERNAL_ERROR < 600);
        assertTrue("Server error codes should be 5xx", MrcpResponse.STATUS_PROTOCOL_VERSION_NOT_SUPPORTED >= 500 && MrcpResponse.STATUS_PROTOCOL_VERSION_NOT_SUPPORTED < 600);
        assertTrue("Server error codes should be 5xx", MrcpResponse.STATUS_MESSAGE_TOO_LARGE >= 500 && MrcpResponse.STATUS_MESSAGE_TOO_LARGE < 600);
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
                assertNotEquals("Status codes should be unique", codes[i], codes[j]);
            }
        }
    }
}