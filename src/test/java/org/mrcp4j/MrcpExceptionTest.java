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
package org.mrcp4j;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Unit tests for {@link MrcpException}.
 */
public class MrcpExceptionTest {

    @Test
    public void testDefaultConstructor() {
        MrcpException exception = new MrcpException();
        assertNull("Message should be null", exception.getMessage());
        assertNull("Cause should be null", exception.getCause());
    }

    @Test
    public void testMessageConstructor() {
        String message = "Test error message";
        MrcpException exception = new MrcpException(message);
        assertEquals("Message should match", message, exception.getMessage());
        assertNull("Cause should be null", exception.getCause());
    }

    @Test
    public void testMessageConstructorWithNull() {
        MrcpException exception = new MrcpException((String) null);
        assertNull("Message should be null", exception.getMessage());
        assertNull("Cause should be null", exception.getCause());
    }

    @Test
    public void testMessageAndCauseConstructor() {
        String message = "Test error message";
        Exception cause = new RuntimeException("Root cause");
        MrcpException exception = new MrcpException(message, cause);
        assertEquals("Message should match", message, exception.getMessage());
        assertEquals("Cause should match", cause, exception.getCause());
    }

    @Test
    public void testMessageAndCauseConstructorWithNullMessage() {
        Exception cause = new RuntimeException("Root cause");
        MrcpException exception = new MrcpException(null, cause);
        assertNull("Message should be null", exception.getMessage());
        assertEquals("Cause should match", cause, exception.getCause());
    }

    @Test
    public void testMessageAndCauseConstructorWithNullCause() {
        String message = "Test error message";
        MrcpException exception = new MrcpException(message, null);
        assertEquals("Message should match", message, exception.getMessage());
        assertNull("Cause should be null", exception.getCause());
    }

    @Test
    public void testCauseConstructor() {
        Exception cause = new RuntimeException("Root cause");
        MrcpException exception = new MrcpException(cause);
        assertEquals("Message should be cause's toString", cause.toString(), exception.getMessage());
        assertEquals("Cause should match", cause, exception.getCause());
    }

    @Test
    public void testCauseConstructorWithNull() {
        MrcpException exception = new MrcpException((Throwable) null);
        assertNull("Message should be null", exception.getMessage());
        assertNull("Cause should be null", exception.getCause());
    }

    @Test
    public void testInheritanceFromException() {
        MrcpException exception = new MrcpException("Test message");
        assertTrue("Should be instance of Exception", exception instanceof Exception);
        assertTrue("Should be instance of Throwable", exception instanceof Throwable);
    }

    @Test
    public void testStackTrace() {
        MrcpException exception = new MrcpException("Test message");
        StackTraceElement[] stackTrace = exception.getStackTrace();
        assertNotNull("Stack trace should not be null", stackTrace);
        assertTrue("Stack trace should have elements", stackTrace.length > 0);
    }

    @Test
    public void testChainedExceptions() {
        Exception rootCause = new IllegalArgumentException("Root cause");
        Exception intermediateCause = new RuntimeException("Intermediate cause", rootCause);
        MrcpException exception = new MrcpException("Top level message", intermediateCause);
        
        assertEquals("Top level message", exception.getMessage());
        assertEquals(intermediateCause, exception.getCause());
        assertEquals(rootCause, exception.getCause().getCause());
    }
}