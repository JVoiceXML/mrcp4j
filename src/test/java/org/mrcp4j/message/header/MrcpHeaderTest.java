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

/**
 * Unit tests for {@link MrcpHeader}.
 */
public class MrcpHeaderTest {

    @Test
    public void testConstructorWithValidValues() {
        MrcpHeaderName headerName = MrcpHeaderName.CHANNEL_IDENTIFIER;
        String valueString = "test@speechrecog";
        Object valueObject = "test object";
        
        MrcpHeader header = new MrcpHeader(headerName, valueString, valueObject);
        
        assertEquals("Header name should match", headerName, header.getHeaderName());
        assertEquals("Value string should match", valueString, header.getValueString());
        assertEquals("Name string should match header name toString", headerName.toString(), header.getNameString());
        assertTrue("Should be valid value", header.isValidValue());
    }

    @Test
    public void testGetValueObjectValid() throws IllegalValueException {
        MrcpHeaderName headerName = MrcpHeaderName.CHANNEL_IDENTIFIER;
        String valueString = "test@speechrecog";
        String valueObject = "test object";
        
        MrcpHeader header = new MrcpHeader(headerName, valueString, valueObject);
        
        Object result = header.getValueObject();
        assertEquals("Value object should match", valueObject, result);
    }

    @Test(expected = IllegalValueException.class)
    public void testGetValueObjectWithNullValue() throws IllegalValueException {
        MrcpHeaderName headerName = MrcpHeaderName.CHANNEL_IDENTIFIER;
        String valueString = "test@speechrecog";
        
        MrcpHeader header = new MrcpHeader(headerName, valueString, null);
        
        assertFalse("Should not be valid value", header.isValidValue());
        header.getValueObject(); // Should throw exception
    }

    @Test(expected = IllegalValueException.class)
    public void testGetValueObjectWithThrowableValue() throws IllegalValueException {
        MrcpHeaderName headerName = MrcpHeaderName.CHANNEL_IDENTIFIER;
        String valueString = "invalid@value";
        RuntimeException throwableValue = new RuntimeException("Parse error");
        
        MrcpHeader header = new MrcpHeader(headerName, valueString, throwableValue);
        
        assertFalse("Should not be valid value", header.isValidValue());
        header.getValueObject(); // Should throw IllegalValueException
    }

    @Test
    public void testGetValueObjectWithIllegalValueException() {
        MrcpHeaderName headerName = MrcpHeaderName.CHANNEL_IDENTIFIER;
        String valueString = "invalid@value";
        IllegalValueException throwableValue = new IllegalValueException("Invalid value");
        
        MrcpHeader header = new MrcpHeader(headerName, valueString, throwableValue);
        
        assertFalse("Should not be valid value", header.isValidValue());
        
        try {
            header.getValueObject();
            fail("Should have thrown IllegalValueException");
        } catch (IllegalValueException e) {
            assertEquals("Should be the same exception", throwableValue, e);
        }
    }

    @Test
    public void testGetValueObjectWithOtherThrowable() {
        MrcpHeaderName headerName = MrcpHeaderName.CHANNEL_IDENTIFIER;
        String valueString = "invalid@value";
        NumberFormatException throwableValue = new NumberFormatException("Invalid number");
        
        MrcpHeader header = new MrcpHeader(headerName, valueString, throwableValue);
        
        assertFalse("Should not be valid value", header.isValidValue());
        
        try {
            header.getValueObject();
            fail("Should have thrown IllegalValueException");
        } catch (IllegalValueException e) {
            assertEquals("Cause should be the original throwable", throwableValue, e.getCause());
            assertTrue("Message should contain original value string", 
                      e.getMessage().contains(valueString));
        }
    }

    @Test
    public void testIsValidValueWithValidObject() {
        MrcpHeader header = new MrcpHeader(MrcpHeaderName.CHANNEL_IDENTIFIER, "test", "valid object");
        assertTrue("Should be valid value", header.isValidValue());
    }

    @Test
    public void testIsValidValueWithNullObject() {
        MrcpHeader header = new MrcpHeader(MrcpHeaderName.CHANNEL_IDENTIFIER, "test", null);
        assertFalse("Should not be valid value", header.isValidValue());
    }

    @Test
    public void testIsValidValueWithThrowableObject() {
        MrcpHeader header = new MrcpHeader(MrcpHeaderName.CHANNEL_IDENTIFIER, "test", new RuntimeException());
        assertFalse("Should not be valid value", header.isValidValue());
    }

    @Test
    public void testAppendTo() {
        MrcpHeaderName headerName = MrcpHeaderName.CHANNEL_IDENTIFIER;
        String valueString = "test@speechrecog";
        
        MrcpHeader header = new MrcpHeader(headerName, valueString, "object");
        
        StringBuilder sb = new StringBuilder("prefix:");
        StringBuilder result = header.appendTo(sb);
        
        assertSame("Should return same StringBuilder", sb, result);
        assertEquals("Should contain header format", "prefix:" + headerName + ":" + valueString, sb.toString());
    }

    @Test
    public void testAppendToEmptyStringBuilder() {
        MrcpHeaderName headerName = MrcpHeaderName.CONTENT_LENGTH;
        String valueString = "123";
        
        MrcpHeader header = new MrcpHeader(headerName, valueString, Integer.valueOf(123));
        
        StringBuilder sb = new StringBuilder();
        header.appendTo(sb);
        
        assertEquals("Should contain header format", headerName + ":" + valueString, sb.toString());
    }

    @Test
    public void testToString() {
        MrcpHeaderName headerName = MrcpHeaderName.CHANNEL_IDENTIFIER;
        String valueString = "test@speechrecog";
        
        MrcpHeader header = new MrcpHeader(headerName, valueString, "object");
        
        String result = header.toString();
        String expected = headerName + ":" + valueString;
        
        assertEquals("ToString should match expected format", expected, result);
    }

    @Test
    public void testToStringWithSpecialCharacters() {
        MrcpHeaderName headerName = MrcpHeaderName.CONTENT_TYPE;
        String valueString = "application/json; charset=utf-8";
        
        MrcpHeader header = new MrcpHeader(headerName, valueString, "object");
        
        String result = header.toString();
        String expected = headerName + ":" + valueString;
        
        assertEquals("ToString should handle special characters", expected, result);
    }

    @Test
    public void testConstructorWithNullValues() {
        // Testing edge cases - the constructor is package-private so we can test it
        MrcpHeader header = new MrcpHeader(MrcpHeaderName.CHANNEL_IDENTIFIER, null, null);
        
        assertEquals("Header name should be set", MrcpHeaderName.CHANNEL_IDENTIFIER, header.getHeaderName());
        assertNull("Value string should be null", header.getValueString());
        assertFalse("Should not be valid value", header.isValidValue());
    }
}