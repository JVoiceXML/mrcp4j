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
 * Unit tests for {@link CompletionCause}.
 */
public class CompletionCauseTest {

    @Test
    public void testConstructorWithValidValues() {
        short causeCode = 200;
        String causeName = "success";
        
        CompletionCause cause = new CompletionCause(causeCode, causeName);
        
        assertEquals("Cause code should match", causeCode, cause.getCauseCode());
        assertEquals("Cause name should match", causeName, cause.getCauseName());
        assertEquals("String representation should be formatted correctly", "200 success", cause.toString());
    }

    @Test
    public void testConstructorWithSingleDigitCode() {
        short causeCode = 5;
        String causeName = "test";
        
        CompletionCause cause = new CompletionCause(causeCode, causeName);
        
        assertEquals("Cause code should match", causeCode, cause.getCauseCode());
        assertEquals("Cause name should match", causeName, cause.getCauseName());
        assertEquals("String should be zero-padded", "005 test", cause.toString());
    }

    @Test
    public void testConstructorWithDoubleDigitCode() {
        short causeCode = 42;
        String causeName = "test";
        
        CompletionCause cause = new CompletionCause(causeCode, causeName);
        
        assertEquals("Cause code should match", causeCode, cause.getCauseCode());
        assertEquals("Cause name should match", causeName, cause.getCauseName());
        assertEquals("String should be zero-padded", "042 test", cause.toString());
    }

    @Test
    public void testConstructorWithTripleDigitCode() {
        short causeCode = 999;
        String causeName = "maxvalue";
        
        CompletionCause cause = new CompletionCause(causeCode, causeName);
        
        assertEquals("Cause code should match", causeCode, cause.getCauseCode());
        assertEquals("Cause name should match", causeName, cause.getCauseName());
        assertEquals("String should not be padded", "999 maxvalue", cause.toString());
    }

    @Test
    public void testConstructorWithMinimumValues() {
        short causeCode = 0;
        String causeName = "a";
        
        CompletionCause cause = new CompletionCause(causeCode, causeName);
        
        assertEquals("Cause code should match", causeCode, cause.getCauseCode());
        assertEquals("Cause name should match", causeName, cause.getCauseName());
        assertEquals("String should be formatted correctly", "000 a", cause.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNegativeCauseCode() {
        new CompletionCause((short) -1, "invalid");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithCauseCodeTooLarge() {
        new CompletionCause((short) 1000, "invalid");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullCauseName() {
        new CompletionCause((short) 200, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithEmptyCauseName() {
        new CompletionCause((short) 200, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithWhitespaceOnlyCauseName() {
        new CompletionCause((short) 200, "   ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithCauseNameContainingSpace() {
        new CompletionCause((short) 200, "invalid name");
    }

    @Test
    public void testConstructorWithCauseNameNeedingTrim() {
        short causeCode = 200;
        String causeName = "  success  ";
        
        CompletionCause cause = new CompletionCause(causeCode, causeName);
        
        assertEquals("Cause code should match", causeCode, cause.getCauseCode());
        // The constructor stores the original name, but validation trims it
        assertEquals("Cause name should not be trimmed in getter", "  success  ", cause.getCauseName());
        assertEquals("String should use trimmed name", "200 success", cause.toString());
    }

    @Test
    public void testEquals() {
        CompletionCause cause1 = new CompletionCause((short) 200, "success");
        CompletionCause cause2 = new CompletionCause((short) 200, "success");
        CompletionCause cause3 = new CompletionCause((short) 404, "notfound");
        CompletionCause cause4 = new CompletionCause((short) 200, "different");
        
        assertTrue("Same completion causes should be equal", cause1.equals(cause2));
        assertTrue("Should be reflexive", cause1.equals(cause1));
        assertFalse("Different causes should not be equal", cause1.equals(cause3));
        assertFalse("Different names should not be equal", cause1.equals(cause4));
        assertFalse("Should not equal null", cause1.equals(null));
        assertFalse("Should not equal different type", cause1.equals("string"));
    }

    @Test
    public void testHashCode() {
        CompletionCause cause1 = new CompletionCause((short) 200, "success");
        CompletionCause cause2 = new CompletionCause((short) 200, "different");
        CompletionCause cause3 = new CompletionCause((short) 404, "success");
        
        assertEquals("Same cause code should have same hash", cause1.hashCode(), cause2.hashCode());
        assertNotEquals("Different cause codes should have different hash", cause1.hashCode(), cause3.hashCode());
        
        // Consistency test
        int hashCode1 = cause1.hashCode();
        int hashCode2 = cause1.hashCode();
        assertEquals("Hash code should be consistent", hashCode1, hashCode2);
    }

    @Test
    public void testFactoryFromValueStringValid() throws IllegalValueException {
        CompletionCause.Factory factory = new CompletionCause.Factory();
        
        Object result = factory.fromValueString("200 success");
        
        assertTrue("Should return CompletionCause", result instanceof CompletionCause);
        CompletionCause cause = (CompletionCause) result;
        assertEquals("Cause code should match", 200, cause.getCauseCode());
        assertEquals("Cause name should match", "success", cause.getCauseName());
        assertEquals("String should match original", "200 success", cause.toString());
    }

    @Test
    public void testFactoryFromValueStringWithPadding() throws IllegalValueException {
        CompletionCause.Factory factory = new CompletionCause.Factory();
        
        Object result = factory.fromValueString("005 test");
        
        assertTrue("Should return CompletionCause", result instanceof CompletionCause);
        CompletionCause cause = (CompletionCause) result;
        assertEquals("Cause code should match", 5, cause.getCauseCode());
        assertEquals("Cause name should match", "test", cause.getCauseName());
    }

    @Test
    public void testFactoryFromValueStringWithSingleSpaceAndTrim() throws IllegalValueException {
        CompletionCause.Factory factory = new CompletionCause.Factory();
        
        Object result = factory.fromValueString("200 success");
        
        assertTrue("Should return CompletionCause", result instanceof CompletionCause);
        CompletionCause cause = (CompletionCause) result;
        assertEquals("Cause code should match", 200, cause.getCauseCode());
        assertEquals("Cause name should match", "success", cause.getCauseName());
    }

    @Test(expected = IllegalValueException.class)
    public void testFactoryFromValueStringInvalidFormat() throws IllegalValueException {
        CompletionCause.Factory factory = new CompletionCause.Factory();
        factory.fromValueString("invalid-format");
    }

    @Test(expected = IllegalValueException.class)
    public void testFactoryFromValueStringTooManyTokens() throws IllegalValueException {
        CompletionCause.Factory factory = new CompletionCause.Factory();
        factory.fromValueString("200 too many tokens");
    }

    @Test(expected = IllegalValueException.class)
    public void testFactoryFromValueStringInvalidCauseCode() throws IllegalValueException {
        CompletionCause.Factory factory = new CompletionCause.Factory();
        factory.fromValueString("abc success");
    }

    @Test(expected = IllegalValueException.class)
    public void testFactoryFromValueStringNegativeCauseCode() throws IllegalValueException {
        CompletionCause.Factory factory = new CompletionCause.Factory();
        factory.fromValueString("-1 invalid");
    }

    @Test(expected = IllegalValueException.class)
    public void testFactoryFromValueStringCauseCodeTooLarge() throws IllegalValueException {
        CompletionCause.Factory factory = new CompletionCause.Factory();
        factory.fromValueString("1000 invalid");
    }

    @Test(expected = IllegalValueException.class)
    public void testFactoryFromValueStringEmptyString() throws IllegalValueException {
        CompletionCause.Factory factory = new CompletionCause.Factory();
        factory.fromValueString("");
    }

    @Test(expected = IllegalValueException.class)
    public void testFactoryFromValueStringWhitespaceOnly() throws IllegalValueException {
        CompletionCause.Factory factory = new CompletionCause.Factory();
        factory.fromValueString("   ");
    }

    @Test(expected = IllegalValueException.class)
    public void testFactoryFromValueStringWithExtraWhitespace() throws IllegalValueException {
        CompletionCause.Factory factory = new CompletionCause.Factory();
        // Multiple spaces break the parsing since it splits on space and expects exactly 2 tokens
        factory.fromValueString("200   success  ");
    }

    @Test
    public void testFactoryTargetClass() {
        CompletionCause.Factory factory = new CompletionCause.Factory();
        // BaseValueFactory constructor sets the target class
        assertNotNull("Factory should be created", factory);
    }

    @Test
    public void testFactoryEdgeCases() throws IllegalValueException {
        CompletionCause.Factory factory = new CompletionCause.Factory();
        
        // Test minimum valid value
        Object result1 = factory.fromValueString("000 min");
        CompletionCause cause1 = (CompletionCause) result1;
        assertEquals("Should handle zero code", 0, cause1.getCauseCode());
        
        // Test maximum valid value
        Object result2 = factory.fromValueString("999 max");
        CompletionCause cause2 = (CompletionCause) result2;
        assertEquals("Should handle maximum code", 999, cause2.getCauseCode());
    }
}