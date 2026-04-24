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

import org.junit.Assert;
import org.junit.Test;
import org.mrcp4j.MrcpException;

/**
 * Unit tests for {@link IllegalValueException}.
 */
public class IllegalValueExceptionTest {

    @Test
    public void testDefaultConstructor() {
        IllegalValueException exception = new IllegalValueException();
        Assert.assertNull("Message should be null", exception.getMessage());
        Assert.assertNull("Cause should be null", exception.getCause());
    }

    @Test
    public void testMessageConstructor() {
        String message = "Invalid value provided";
        IllegalValueException exception = new IllegalValueException(message);
        Assert.assertEquals("Message should match", message, exception.getMessage());
        Assert.assertNull("Cause should be null", exception.getCause());
    }

    @Test
    public void testMessageConstructorWithNull() {
        IllegalValueException exception = new IllegalValueException((String) null);
        Assert.assertNull("Message should be null", exception.getMessage());
        Assert.assertNull("Cause should be null", exception.getCause());
    }

    @Test
    public void testMessageAndCauseConstructor() {
        String message = "Invalid value provided";
        Exception cause = new NumberFormatException("Not a valid number");
        IllegalValueException exception = new IllegalValueException(message, cause);
        Assert.assertEquals("Message should match", message, exception.getMessage());
        Assert.assertEquals("Cause should match", cause, exception.getCause());
    }

    @Test
    public void testMessageAndCauseConstructorWithNullMessage() {
        Exception cause = new NumberFormatException("Not a valid number");
        IllegalValueException exception = new IllegalValueException(null, cause);
        Assert.assertNull("Message should be null", exception.getMessage());
        Assert.assertEquals("Cause should match", cause, exception.getCause());
    }

    @Test
    public void testMessageAndCauseConstructorWithNullCause() {
        String message = "Invalid value provided";
        IllegalValueException exception = new IllegalValueException(message, null);
        Assert.assertEquals("Message should match", message, exception.getMessage());
        Assert.assertNull("Cause should be null", exception.getCause());
    }

    @Test
    public void testCauseConstructor() {
        Exception cause = new NumberFormatException("Not a valid number");
        IllegalValueException exception = new IllegalValueException(cause);
        Assert.assertEquals("Message should be cause's toString", cause.toString(), exception.getMessage());
        Assert.assertEquals("Cause should match", cause, exception.getCause());
    }

    @Test
    public void testCauseConstructorWithNull() {
        IllegalValueException exception = new IllegalValueException((Throwable) null);
        Assert.assertNull("Message should be null", exception.getMessage());
        Assert.assertNull("Cause should be null", exception.getCause());
    }

    @Test
    public void testInheritanceFromMrcpException() {
        IllegalValueException exception = new IllegalValueException("Test message");
        Assert.assertTrue("Should be instance of MrcpException", exception instanceof MrcpException);
        Assert.assertTrue("Should be instance of Exception", exception instanceof Exception);
        Assert.assertTrue("Should be instance of Throwable", exception instanceof Throwable);
    }

    @Test
    public void testStackTrace() {
        IllegalValueException exception = new IllegalValueException("Test message");
        StackTraceElement[] stackTrace = exception.getStackTrace();
        Assert.assertNotNull("Stack trace should not be null", stackTrace);
        Assert.assertTrue("Stack trace should have elements", stackTrace.length > 0);
    }

    @Test
    public void testChainedExceptions() {
        Exception rootCause = new NumberFormatException("Invalid number format");
        Exception intermediateCause = new RuntimeException("Processing error", rootCause);
        IllegalValueException exception = new IllegalValueException("Value validation failed", intermediateCause);
        
        Assert.assertEquals("Value validation failed", exception.getMessage());
        Assert.assertEquals(intermediateCause, exception.getCause());
        Assert.assertEquals(rootCause, exception.getCause().getCause());
    }

    @Test
    public void testSerializationUID() {
        // Test that the class has the @SuppressWarnings("serial") annotation
        // This is primarily a compile-time check
        IllegalValueException exception = new IllegalValueException("Test");
        Assert.assertNotNull("Exception should be created successfully", exception);
    }
}