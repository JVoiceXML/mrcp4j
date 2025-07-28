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
package org.mrcp4j.client;

import static org.junit.Assert.*;
import org.junit.Test;
import org.mrcp4j.MrcpException;
import org.mrcp4j.message.MrcpResponse;

/**
 * Unit tests for {@link MrcpInvocationException}.
 */
public class MrcpInvocationExceptionTest {

    @Test
    public void testConstructorWithSuccessResponse() {
        MrcpResponse response = new MrcpResponse();
        response.setStatusCode(MrcpResponse.STATUS_SUCCESS);
        
        MrcpInvocationException exception = new MrcpInvocationException(response);
        
        assertEquals("Response should be stored", response, exception.getResponse());
        assertTrue("Message should contain status code", exception.getMessage().contains("200"));
        assertTrue("Message should contain status description", exception.getMessage().contains("STATUS_SUCCESS"));
    }

    @Test
    public void testConstructorWithClientErrorResponse() {
        MrcpResponse response = new MrcpResponse();
        response.setStatusCode(MrcpResponse.STATUS_METHOD_NOT_ALLOWED);
        
        MrcpInvocationException exception = new MrcpInvocationException(response);
        
        assertEquals("Response should be stored", response, exception.getResponse());
        assertTrue("Message should contain status code", exception.getMessage().contains("401"));
        assertTrue("Message should contain status description", exception.getMessage().contains("STATUS_METHOD_NOT_ALLOWED"));
    }

    @Test
    public void testConstructorWithServerErrorResponse() {
        MrcpResponse response = new MrcpResponse();
        response.setStatusCode(MrcpResponse.STATUS_SERVER_INTERNAL_ERROR);
        
        MrcpInvocationException exception = new MrcpInvocationException(response);
        
        assertEquals("Response should be stored", response, exception.getResponse());
        assertTrue("Message should contain status code", exception.getMessage().contains("501"));
        assertTrue("Message should contain status description", exception.getMessage().contains("STATUS_SERVER_INTERNAL_ERROR"));
    }

    @Test
    public void testConstructorWithUnknownStatusCode() {
        MrcpResponse response = new MrcpResponse();
        response.setStatusCode((short) 999);
        
        MrcpInvocationException exception = new MrcpInvocationException(response);
        
        assertEquals("Response should be stored", response, exception.getResponse());
        assertTrue("Message should contain status code", exception.getMessage().contains("999"));
        assertTrue("Message should handle null description", exception.getMessage().contains("null"));
    }

    @Test
    public void testConstructorWithNegativeStatusCode() {
        MrcpResponse response = new MrcpResponse();
        response.setStatusCode((short) -1);
        
        MrcpInvocationException exception = new MrcpInvocationException(response);
        
        assertEquals("Response should be stored", response, exception.getResponse());
        assertTrue("Message should contain status code", exception.getMessage().contains("-1"));
    }

    @Test
    public void testGetResponseReturnsSameInstance() {
        MrcpResponse response = new MrcpResponse();
        response.setStatusCode(MrcpResponse.STATUS_ILLEGAL_VALUE_FOR_HEADER);
        
        MrcpInvocationException exception = new MrcpInvocationException(response);
        
        assertSame("Should return same response instance", response, exception.getResponse());
    }

    @Test
    public void testInheritanceFromMrcpException() {
        MrcpResponse response = new MrcpResponse();
        response.setStatusCode(MrcpResponse.STATUS_SUCCESS);
        
        MrcpInvocationException exception = new MrcpInvocationException(response);
        
        assertTrue("Should be instance of MrcpException", exception instanceof MrcpException);
        assertTrue("Should be instance of Exception", exception instanceof Exception);
        assertTrue("Should be instance of Throwable", exception instanceof Throwable);
    }

    @Test
    public void testMessageFormat() {
        MrcpResponse response = new MrcpResponse();
        response.setStatusCode(MrcpResponse.STATUS_OPERATION_FAILED);
        
        MrcpInvocationException exception = new MrcpInvocationException(response);
        
        String message = exception.getMessage();
        assertNotNull("Message should not be null", message);
        assertTrue("Message should start with 'MRCPv2 Status Code:'", message.startsWith("MRCPv2 Status Code:"));
        assertTrue("Message should contain status code", message.contains("407"));
        assertTrue("Message should be formatted correctly", message.contains("[STATUS_OPERATION_FAILED]"));
    }

    @Test
    public void testStackTrace() {
        MrcpResponse response = new MrcpResponse();
        response.setStatusCode(MrcpResponse.STATUS_SUCCESS);
        
        MrcpInvocationException exception = new MrcpInvocationException(response);
        
        StackTraceElement[] stackTrace = exception.getStackTrace();
        assertNotNull("Stack trace should not be null", stackTrace);
        assertTrue("Stack trace should have elements", stackTrace.length > 0);
    }

    @Test
    public void testMultipleInstancesIndependence() {
        MrcpResponse response1 = new MrcpResponse();
        response1.setStatusCode(MrcpResponse.STATUS_SUCCESS);
        
        MrcpResponse response2 = new MrcpResponse();
        response2.setStatusCode(MrcpResponse.STATUS_METHOD_NOT_ALLOWED);
        
        MrcpInvocationException exception1 = new MrcpInvocationException(response1);
        MrcpInvocationException exception2 = new MrcpInvocationException(response2);
        
        assertEquals("First exception should have first response", response1, exception1.getResponse());
        assertEquals("Second exception should have second response", response2, exception2.getResponse());
        assertNotEquals("Exceptions should have different responses", exception1.getResponse(), exception2.getResponse());
        assertNotEquals("Messages should be different", exception1.getMessage(), exception2.getMessage());
    }

    @Test
    public void testAllStatusCodesProduceValidMessages() {
        short[] statusCodes = {
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
        
        for (short statusCode : statusCodes) {
            MrcpResponse response = new MrcpResponse();
            response.setStatusCode(statusCode);
            
            MrcpInvocationException exception = new MrcpInvocationException(response);
            
            assertNotNull("Message should not be null for status " + statusCode, exception.getMessage());
            assertTrue("Message should contain status code for " + statusCode, 
                      exception.getMessage().contains(String.valueOf(statusCode)));
            assertEquals("Response should match for status " + statusCode, response, exception.getResponse());
        }
    }
}