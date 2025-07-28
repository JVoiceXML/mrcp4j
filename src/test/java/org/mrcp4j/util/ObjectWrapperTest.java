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
package org.mrcp4j.util;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Unit tests for {@link ObjectWrapper}.
 */
public class ObjectWrapperTest {

    @Test
    public void testConstructorWithStringObject() {
        String testString = "test string";
        ObjectWrapper<String> wrapper = new ObjectWrapper<String>(testString);
        assertEquals("Should return wrapped string", testString, wrapper.getObject());
    }

    @Test
    public void testConstructorWithIntegerObject() {
        Integer testInteger = 42;
        ObjectWrapper<Integer> wrapper = new ObjectWrapper<Integer>(testInteger);
        assertEquals("Should return wrapped integer", testInteger, wrapper.getObject());
    }

    @Test
    public void testConstructorWithNullObject() {
        ObjectWrapper<String> wrapper = new ObjectWrapper<String>(null);
        assertNull("Should return null", wrapper.getObject());
    }

    @Test
    public void testConstructorWithComplexObject() {
        StringBuilder testBuilder = new StringBuilder("test");
        ObjectWrapper<StringBuilder> wrapper = new ObjectWrapper<StringBuilder>(testBuilder);
        assertEquals("Should return wrapped StringBuilder", testBuilder, wrapper.getObject());
        assertEquals("Content should match", "test", wrapper.getObject().toString());
    }

    @Test
    public void testGenericTypeString() {
        String testString = "test";
        ObjectWrapper<String> wrapper = new ObjectWrapper<String>(testString);
        String result = wrapper.getObject();
        assertEquals("Should be same string", testString, result);
    }

    @Test
    public void testGenericTypeInteger() {
        Integer testInteger = 123;
        ObjectWrapper<Integer> wrapper = new ObjectWrapper<Integer>(testInteger);
        Integer result = wrapper.getObject();
        assertEquals("Should be same integer", testInteger, result);
    }

    @Test
    public void testGenericTypeObject() {
        Object testObject = new Object();
        ObjectWrapper<Object> wrapper = new ObjectWrapper<Object>(testObject);
        Object result = wrapper.getObject();
        assertSame("Should be same object reference", testObject, result);
    }

    @Test
    public void testMultipleWrappers() {
        String string1 = "first";
        String string2 = "second";
        
        ObjectWrapper<String> wrapper1 = new ObjectWrapper<String>(string1);
        ObjectWrapper<String> wrapper2 = new ObjectWrapper<String>(string2);
        
        assertEquals("First wrapper should return first string", string1, wrapper1.getObject());
        assertEquals("Second wrapper should return second string", string2, wrapper2.getObject());
        assertNotEquals("Wrapped objects should be different", wrapper1.getObject(), wrapper2.getObject());
    }

    @Test
    public void testWrapperIndependence() {
        String original = "original";
        ObjectWrapper<String> wrapper = new ObjectWrapper<String>(original);
        
        // Create another wrapper with null
        ObjectWrapper<String> nullWrapper = new ObjectWrapper<String>(null);
        
        assertEquals("Original wrapper should still work", original, wrapper.getObject());
        assertNull("Null wrapper should return null", nullWrapper.getObject());
    }

    @Test
    public void testMutability() {
        StringBuilder mutableObject = new StringBuilder("initial");
        ObjectWrapper<StringBuilder> wrapper = new ObjectWrapper<StringBuilder>(mutableObject);
        
        // Modify the wrapped object
        mutableObject.append(" modified");
        
        assertEquals("Should reflect changes to wrapped object", "initial modified", wrapper.getObject().toString());
    }

    @Test
    public void testNullAfterCreation() {
        ObjectWrapper<String> wrapper = new ObjectWrapper<String>("test");
        assertEquals("Should initially return test", "test", wrapper.getObject());
        
        // Create new wrapper with null to test null handling
        ObjectWrapper<String> nullWrapper = new ObjectWrapper<String>(null);
        assertNull("New null wrapper should return null", nullWrapper.getObject());
    }
}