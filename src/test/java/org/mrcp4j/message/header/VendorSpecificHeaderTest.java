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
 * Unit tests for {@link VendorSpecificHeader}.
 */
public class VendorSpecificHeaderTest {

    @Test
    public void testConstructorWithValidValues() {
        String name = "X-Custom-Header";
        String value = "custom-value";
        
        VendorSpecificHeader header = new VendorSpecificHeader(name, value);
        
        assertEquals("Name should match", name, header.getNameString());
        assertEquals("Value should match", value, header.getValueString());
    }

    @Test
    public void testConstructorWithNullName() {
        String value = "test-value";
        
        VendorSpecificHeader header = new VendorSpecificHeader(null, value);
        
        assertNull("Name should be null", header.getNameString());
        assertEquals("Value should match", value, header.getValueString());
    }

    @Test
    public void testConstructorWithNullValue() {
        String name = "X-Test-Header";
        
        VendorSpecificHeader header = new VendorSpecificHeader(name, null);
        
        assertEquals("Name should match", name, header.getNameString());
        assertNull("Value should be null", header.getValueString());
    }

    @Test
    public void testConstructorWithBothNull() {
        VendorSpecificHeader header = new VendorSpecificHeader(null, null);
        
        assertNull("Name should be null", header.getNameString());
        assertNull("Value should be null", header.getValueString());
    }

    @Test
    public void testConstructorWithEmptyValues() {
        String name = "";
        String value = "";
        
        VendorSpecificHeader header = new VendorSpecificHeader(name, value);
        
        assertEquals("Name should be empty", name, header.getNameString());
        assertEquals("Value should be empty", value, header.getValueString());
    }

    @Test
    public void testAppendTo() {
        String name = "X-Custom-Header";
        String value = "custom-value";
        VendorSpecificHeader header = new VendorSpecificHeader(name, value);
        
        StringBuilder sb = new StringBuilder("prefix:");
        StringBuilder result = header.appendTo(sb);
        
        assertSame("Should return same StringBuilder", sb, result);
        assertEquals("Should format correctly", "prefix:X-Custom-Header:custom-value", sb.toString());
    }

    @Test
    public void testAppendToEmptyStringBuilder() {
        String name = "X-Test";
        String value = "test-value";
        VendorSpecificHeader header = new VendorSpecificHeader(name, value);
        
        StringBuilder sb = new StringBuilder();
        header.appendTo(sb);
        
        assertEquals("Should format correctly", "X-Test:test-value", sb.toString());
    }

    @Test
    public void testAppendToWithNullName() {
        VendorSpecificHeader header = new VendorSpecificHeader(null, "value");
        
        StringBuilder sb = new StringBuilder();
        header.appendTo(sb);
        
        assertEquals("Should handle null name", "null:value", sb.toString());
    }

    @Test
    public void testAppendToWithNullValue() {
        VendorSpecificHeader header = new VendorSpecificHeader("name", null);
        
        StringBuilder sb = new StringBuilder();
        header.appendTo(sb);
        
        assertEquals("Should handle null value", "name:null", sb.toString());
    }

    @Test
    public void testAppendToWithSpecialCharacters() {
        String name = "X-Header-With-Dashes";
        String value = "value/with/slashes and spaces";
        VendorSpecificHeader header = new VendorSpecificHeader(name, value);
        
        StringBuilder sb = new StringBuilder();
        header.appendTo(sb);
        
        assertEquals("Should handle special characters", "X-Header-With-Dashes:value/with/slashes and spaces", sb.toString());
    }

    @Test
    public void testInheritanceFromMrcpHeader() {
        VendorSpecificHeader header = new VendorSpecificHeader("test", "value");
        assertTrue("Should be instance of MrcpHeader", header instanceof MrcpHeader);
    }

    @Test
    public void testGetHeaderName() {
        VendorSpecificHeader header = new VendorSpecificHeader("test", "value");
        // VendorSpecificHeader passes null to super constructor for header name
        assertNull("Header name should be null", header.getHeaderName());
    }

    @Test
    public void testIsValidValue() {
        VendorSpecificHeader header = new VendorSpecificHeader("test", "value");
        // The value object is the same as the value string, so it should be valid
        assertTrue("Should be valid value", header.isValidValue());
    }

    @Test
    public void testGetValueObject() throws IllegalValueException {
        String value = "test-value";
        VendorSpecificHeader header = new VendorSpecificHeader("test", value);
        
        Object valueObject = header.getValueObject();
        assertEquals("Value object should match value string", value, valueObject);
    }

    @Test
    public void testToString() {
        String value = "test-value";
        VendorSpecificHeader header = new VendorSpecificHeader("test", value);
        
        // toString is inherited from MrcpHeader and uses getNameString() which is overridden
        String result = header.toString();
        assertEquals("ToString should format correctly", "test:test-value", result);
    }

    @Test
    public void testToStringWithNullValues() {
        VendorSpecificHeader header = new VendorSpecificHeader(null, null);
        
        String result = header.toString();
        assertEquals("Should handle null values", "null:null", result);
    }

    @Test
    public void testGetNameStringOverride() {
        // Test that getNameString() is properly overridden and doesn't call super
        String customName = "X-Custom-Header";
        VendorSpecificHeader header = new VendorSpecificHeader(customName, "value");
        
        assertEquals("Should return custom name", customName, header.getNameString());
        
        // Compare with parent class behavior
        assertNull("Header name from parent should be null", header.getHeaderName());
        assertNotEquals("Custom name should differ from parent name", 
                       header.getHeaderName(), header.getNameString());
    }

    @Test
    public void testMultipleHeadersIndependence() {
        VendorSpecificHeader header1 = new VendorSpecificHeader("X-Header1", "value1");
        VendorSpecificHeader header2 = new VendorSpecificHeader("X-Header2", "value2");
        
        assertEquals("First header name should be correct", "X-Header1", header1.getNameString());
        assertEquals("First header value should be correct", "value1", header1.getValueString());
        assertEquals("Second header name should be correct", "X-Header2", header2.getNameString());
        assertEquals("Second header value should be correct", "value2", header2.getValueString());
        
        assertNotEquals("Headers should be independent", header1.getNameString(), header2.getNameString());
        assertNotEquals("Values should be independent", header1.getValueString(), header2.getValueString());
    }
}