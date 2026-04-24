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
        
        Assert.assertEquals("Header name should match", headerName, header.getHeaderName());
        Assert.assertEquals("Value string should match", valueString, header.getValueString());
        Assert.assertEquals("Name string should match header name toString", headerName.toString(), header.getNameString());
        Assert.assertTrue("Should be valid value", header.isValidValue());
    }

    @Test
    public void testGetValueObjectValid() throws IllegalValueException {
        MrcpHeaderName headerName = MrcpHeaderName.CHANNEL_IDENTIFIER;
        String valueString = "test@speechrecog";
        String valueObject = "test object";
        
        MrcpHeader header = new MrcpHeader(headerName, valueString, valueObject);
        
        Object result = header.getValueObject();
        Assert.assertEquals("Value object should match", valueObject, result);
    }

    @Test(expected = IllegalValueException.class)
    public void testGetValueObjectWithNullValue() throws IllegalValueException {
        MrcpHeaderName headerName = MrcpHeaderName.CHANNEL_IDENTIFIER;
        String valueString = "test@speechrecog";
        
        MrcpHeader header = new MrcpHeader(headerName, valueString, null);
        
        Assert.assertFalse("Should not be valid value", header.isValidValue());
        header.getValueObject(); // Should throw exception
    }

    @Test(expected = IllegalValueException.class)
    public void testGetValueObjectWithThrowableValue() throws IllegalValueException {
        MrcpHeaderName headerName = MrcpHeaderName.CHANNEL_IDENTIFIER;
        String valueString = "invalid@value";
        RuntimeException throwableValue = new RuntimeException("Parse error");
        
        MrcpHeader header = new MrcpHeader(headerName, valueString, throwableValue);
        
        Assert.assertFalse("Should not be valid value", header.isValidValue());
        header.getValueObject(); // Should throw IllegalValueException
    }

    @Test
    public void testGetValueObjectWithIllegalValueException() {
        MrcpHeaderName headerName = MrcpHeaderName.CHANNEL_IDENTIFIER;
        String valueString = "invalid@value";
        IllegalValueException throwableValue = new IllegalValueException("Invalid value");
        
        MrcpHeader header = new MrcpHeader(headerName, valueString, throwableValue);
        
        Assert.assertFalse("Should not be valid value", header.isValidValue());
        
        try {
            header.getValueObject();
            Assert.fail("Should have thrown IllegalValueException");
        } catch (IllegalValueException e) {
            Assert.assertEquals("Should be the same exception", throwableValue, e);
        }
    }

    @Test
    public void testGetValueObjectWithOtherThrowable() {
        MrcpHeaderName headerName = MrcpHeaderName.CHANNEL_IDENTIFIER;
        String valueString = "invalid@value";
        NumberFormatException throwableValue = new NumberFormatException("Invalid number");
        
        MrcpHeader header = new MrcpHeader(headerName, valueString, throwableValue);
        
        Assert.assertFalse("Should not be valid value", header.isValidValue());
        
        try {
            header.getValueObject();
            Assert.fail("Should have thrown IllegalValueException");
        } catch (IllegalValueException e) {
            Assert.assertEquals("Cause should be the original throwable", throwableValue, e.getCause());
            Assert.assertTrue("Message should contain original value string", 
                      e.getMessage().contains(valueString));
        }
    }

    @Test
    public void testIsValidValueWithValidObject() {
        MrcpHeader header = new MrcpHeader(MrcpHeaderName.CHANNEL_IDENTIFIER, "test", "valid object");
        Assert.assertTrue("Should be valid value", header.isValidValue());
    }

    @Test
    public void testIsValidValueWithNullObject() {
        MrcpHeader header = new MrcpHeader(MrcpHeaderName.CHANNEL_IDENTIFIER, "test", null);
        Assert.assertFalse("Should not be valid value", header.isValidValue());
    }

    @Test
    public void testIsValidValueWithThrowableObject() {
        MrcpHeader header = new MrcpHeader(MrcpHeaderName.CHANNEL_IDENTIFIER, "test", new RuntimeException());
        Assert.assertFalse("Should not be valid value", header.isValidValue());
    }

    @Test
    public void testAppendTo() {
        MrcpHeaderName headerName = MrcpHeaderName.CHANNEL_IDENTIFIER;
        String valueString = "test@speechrecog";
        
        MrcpHeader header = new MrcpHeader(headerName, valueString, "object");
        
        StringBuilder sb = new StringBuilder("prefix:");
        StringBuilder result = header.appendTo(sb);
        
        Assert.assertSame("Should return same StringBuilder", sb, result);
        Assert.assertEquals("Should contain header format", "prefix:" + headerName + ":" + valueString, sb.toString());
    }

    @Test
    public void testAppendToEmptyStringBuilder() {
        MrcpHeaderName headerName = MrcpHeaderName.CONTENT_LENGTH;
        String valueString = "123";
        
        MrcpHeader header = new MrcpHeader(headerName, valueString, Integer.valueOf(123));
        
        StringBuilder sb = new StringBuilder();
        header.appendTo(sb);
        
        Assert.assertEquals("Should contain header format", headerName + ":" + valueString, sb.toString());
    }

    @Test
    public void testToString() {
        MrcpHeaderName headerName = MrcpHeaderName.CHANNEL_IDENTIFIER;
        String valueString = "test@speechrecog";
        
        MrcpHeader header = new MrcpHeader(headerName, valueString, "object");
        
        String result = header.toString();
        String expected = headerName + ":" + valueString;
        
        Assert.assertEquals("ToString should match expected format", expected, result);
    }

    @Test
    public void testToStringWithSpecialCharacters() {
        MrcpHeaderName headerName = MrcpHeaderName.CONTENT_TYPE;
        String valueString = "application/json; charset=utf-8";
        
        MrcpHeader header = new MrcpHeader(headerName, valueString, "object");
        
        String result = header.toString();
        String expected = headerName + ":" + valueString;
        
        Assert.assertEquals("ToString should handle special characters", expected, result);
    }

    @Test
    public void testConstructorWithNullValues() {
        // Testing edge cases - the constructor is package-private so we can test it
        MrcpHeader header = new MrcpHeader(MrcpHeaderName.CHANNEL_IDENTIFIER, null, null);
        
        Assert.assertEquals("Header name should be set", MrcpHeaderName.CHANNEL_IDENTIFIER, header.getHeaderName());
        Assert.assertNull("Value string should be null", header.getValueString());
        Assert.assertFalse("Should not be valid value", header.isValidValue());
    }
}