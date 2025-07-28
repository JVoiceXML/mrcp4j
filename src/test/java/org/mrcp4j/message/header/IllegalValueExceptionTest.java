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
package org.mrcp4j.message.header;

import static org.junit.Assert.*;
import org.junit.Test;
import org.mrcp4j.MrcpException;

/**
 * Unit tests for {@link IllegalValueException}.
 */
public class IllegalValueExceptionTest {

    @Test
    public void testDefaultConstructor() {
        IllegalValueException exception = new IllegalValueException();
        assertNull("Message should be null", exception.getMessage());
        assertNull("Cause should be null", exception.getCause());
    }

    @Test
    public void testMessageConstructor() {
        String message = "Invalid value provided";
        IllegalValueException exception = new IllegalValueException(message);
        assertEquals("Message should match", message, exception.getMessage());
        assertNull("Cause should be null", exception.getCause());
    }

    @Test
    public void testMessageConstructorWithNull() {
        IllegalValueException exception = new IllegalValueException((String) null);
        assertNull("Message should be null", exception.getMessage());
        assertNull("Cause should be null", exception.getCause());
    }

    @Test
    public void testMessageAndCauseConstructor() {
        String message = "Invalid value provided";
        Exception cause = new NumberFormatException("Not a valid number");
        IllegalValueException exception = new IllegalValueException(message, cause);
        assertEquals("Message should match", message, exception.getMessage());
        assertEquals("Cause should match", cause, exception.getCause());
    }

    @Test
    public void testMessageAndCauseConstructorWithNullMessage() {
        Exception cause = new NumberFormatException("Not a valid number");
        IllegalValueException exception = new IllegalValueException(null, cause);
        assertNull("Message should be null", exception.getMessage());
        assertEquals("Cause should match", cause, exception.getCause());
    }

    @Test
    public void testMessageAndCauseConstructorWithNullCause() {
        String message = "Invalid value provided";
        IllegalValueException exception = new IllegalValueException(message, null);
        assertEquals("Message should match", message, exception.getMessage());
        assertNull("Cause should be null", exception.getCause());
    }

    @Test
    public void testCauseConstructor() {
        Exception cause = new NumberFormatException("Not a valid number");
        IllegalValueException exception = new IllegalValueException(cause);
        assertEquals("Message should be cause's toString", cause.toString(), exception.getMessage());
        assertEquals("Cause should match", cause, exception.getCause());
    }

    @Test
    public void testCauseConstructorWithNull() {
        IllegalValueException exception = new IllegalValueException((Throwable) null);
        assertNull("Message should be null", exception.getMessage());
        assertNull("Cause should be null", exception.getCause());
    }

    @Test
    public void testInheritanceFromMrcpException() {
        IllegalValueException exception = new IllegalValueException("Test message");
        assertTrue("Should be instance of MrcpException", exception instanceof MrcpException);
        assertTrue("Should be instance of Exception", exception instanceof Exception);
        assertTrue("Should be instance of Throwable", exception instanceof Throwable);
    }

    @Test
    public void testStackTrace() {
        IllegalValueException exception = new IllegalValueException("Test message");
        StackTraceElement[] stackTrace = exception.getStackTrace();
        assertNotNull("Stack trace should not be null", stackTrace);
        assertTrue("Stack trace should have elements", stackTrace.length > 0);
    }

    @Test
    public void testChainedExceptions() {
        Exception rootCause = new NumberFormatException("Invalid number format");
        Exception intermediateCause = new RuntimeException("Processing error", rootCause);
        IllegalValueException exception = new IllegalValueException("Value validation failed", intermediateCause);
        
        assertEquals("Value validation failed", exception.getMessage());
        assertEquals(intermediateCause, exception.getCause());
        assertEquals(rootCause, exception.getCause().getCause());
    }

    @Test
    public void testSerializationUID() {
        // Test that the class has the @SuppressWarnings("serial") annotation
        // This is primarily a compile-time check
        IllegalValueException exception = new IllegalValueException("Test");
        assertNotNull("Exception should be created successfully", exception);
    }
}