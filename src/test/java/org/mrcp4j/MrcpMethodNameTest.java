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

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link MrcpMethodName}.
 */
public class MrcpMethodNameTest {

    @Test
    public void testToString() {
        Assert.assertEquals("SET-PARAMS", MrcpMethodName.SET_PARAMS.toString());
        Assert.assertEquals("GET-PARAMS", MrcpMethodName.GET_PARAMS.toString());
        Assert.assertEquals("SPEAK", MrcpMethodName.SPEAK.toString());
        Assert.assertEquals("STOP", MrcpMethodName.STOP.toString());
        Assert.assertEquals("PAUSE", MrcpMethodName.PAUSE.toString());
        Assert.assertEquals("RESUME", MrcpMethodName.RESUME.toString());
        Assert.assertEquals("BARGE-IN-OCCURRED", MrcpMethodName.BARGE_IN_OCCURRED.toString());
        Assert.assertEquals("CONTROL", MrcpMethodName.CONTROL.toString());
        Assert.assertEquals("DEFINE-LEXICON", MrcpMethodName.DEFINE_LEXICON.toString());
        Assert.assertEquals("DEFINE-GRAMMAR", MrcpMethodName.DEFINE_GRAMMAR.toString());
        Assert.assertEquals("RECOGNIZE", MrcpMethodName.RECOGNIZE.toString());
        Assert.assertEquals("INTERPRET", MrcpMethodName.INTERPRET.toString());
        Assert.assertEquals("GET-RESULT", MrcpMethodName.GET_RESULT.toString());
        Assert.assertEquals("START-INPUT-TIMERS", MrcpMethodName.START_INPUT_TIMERS.toString());
        Assert.assertEquals("START-PHRASE-ENROLLMENT", MrcpMethodName.START_PHRASE_ENROLLMENT.toString());
        Assert.assertEquals("ENROLLMENT-ROLLBACK", MrcpMethodName.ENROLLMENT_ROLLBACK.toString());
        Assert.assertEquals("END-PHRASE-ENROLLMENT", MrcpMethodName.END_PHRASE_ENROLLMENT.toString());
        Assert.assertEquals("MODIFY-PHRASE", MrcpMethodName.MODIFY_PHRASE.toString());
        Assert.assertEquals("DELETE-PHRASE", MrcpMethodName.DELETE_PHRASE.toString());
        Assert.assertEquals("RECORD", MrcpMethodName.RECORD.toString());
        Assert.assertEquals("START-SESSION", MrcpMethodName.START_SESSION.toString());
        Assert.assertEquals("END-SESSION", MrcpMethodName.END_SESSION.toString());
        Assert.assertEquals("QUERY-VOICEPRINT", MrcpMethodName.QUERY_VOICEPRINT.toString());
        Assert.assertEquals("DELETE-VOICEPRINT", MrcpMethodName.DELETE_VOICEPRINT.toString());
        Assert.assertEquals("VERIFY", MrcpMethodName.VERIFY.toString());
        Assert.assertEquals("VERIFY-FROM-BUFFER", MrcpMethodName.VERIFY_FROM_BUFFER.toString());
        Assert.assertEquals("VERIFY-ROLLBACK", MrcpMethodName.VERIFY_ROLLBACK.toString());
        Assert.assertEquals("CLEAR-BUFFER", MrcpMethodName.CLEAR_BUFFER.toString());
        Assert.assertEquals("GET-INTERMEDIATE-RESULT", MrcpMethodName.GET_INTERMEDIATE_RESULT.toString());
    }

    @Test
    public void testFromStringValid() {
        Assert.assertEquals(MrcpMethodName.SET_PARAMS, MrcpMethodName.fromString("SET-PARAMS"));
        Assert.assertEquals(MrcpMethodName.GET_PARAMS, MrcpMethodName.fromString("GET-PARAMS"));
        Assert.assertEquals(MrcpMethodName.SPEAK, MrcpMethodName.fromString("SPEAK"));
        Assert.assertEquals(MrcpMethodName.STOP, MrcpMethodName.fromString("STOP"));
        Assert.assertEquals(MrcpMethodName.PAUSE, MrcpMethodName.fromString("PAUSE"));
        Assert.assertEquals(MrcpMethodName.RESUME, MrcpMethodName.fromString("RESUME"));
        Assert.assertEquals(MrcpMethodName.BARGE_IN_OCCURRED, MrcpMethodName.fromString("BARGE-IN-OCCURRED"));
        Assert.assertEquals(MrcpMethodName.CONTROL, MrcpMethodName.fromString("CONTROL"));
        Assert.assertEquals(MrcpMethodName.DEFINE_LEXICON, MrcpMethodName.fromString("DEFINE-LEXICON"));
        Assert.assertEquals(MrcpMethodName.DEFINE_GRAMMAR, MrcpMethodName.fromString("DEFINE-GRAMMAR"));
        Assert.assertEquals(MrcpMethodName.RECOGNIZE, MrcpMethodName.fromString("RECOGNIZE"));
        Assert.assertEquals(MrcpMethodName.INTERPRET, MrcpMethodName.fromString("INTERPRET"));
        Assert.assertEquals(MrcpMethodName.GET_RESULT, MrcpMethodName.fromString("GET-RESULT"));
        Assert.assertEquals(MrcpMethodName.START_INPUT_TIMERS, MrcpMethodName.fromString("START-INPUT-TIMERS"));
        Assert.assertEquals(MrcpMethodName.START_PHRASE_ENROLLMENT, MrcpMethodName.fromString("START-PHRASE-ENROLLMENT"));
        Assert.assertEquals(MrcpMethodName.ENROLLMENT_ROLLBACK, MrcpMethodName.fromString("ENROLLMENT-ROLLBACK"));
        Assert.assertEquals(MrcpMethodName.END_PHRASE_ENROLLMENT, MrcpMethodName.fromString("END-PHRASE-ENROLLMENT"));
        Assert.assertEquals(MrcpMethodName.MODIFY_PHRASE, MrcpMethodName.fromString("MODIFY-PHRASE"));
        Assert.assertEquals(MrcpMethodName.DELETE_PHRASE, MrcpMethodName.fromString("DELETE-PHRASE"));
        Assert.assertEquals(MrcpMethodName.RECORD, MrcpMethodName.fromString("RECORD"));
        Assert.assertEquals(MrcpMethodName.START_SESSION, MrcpMethodName.fromString("START-SESSION"));
        Assert.assertEquals(MrcpMethodName.END_SESSION, MrcpMethodName.fromString("END-SESSION"));
        Assert.assertEquals(MrcpMethodName.QUERY_VOICEPRINT, MrcpMethodName.fromString("QUERY-VOICEPRINT"));
        Assert.assertEquals(MrcpMethodName.DELETE_VOICEPRINT, MrcpMethodName.fromString("DELETE-VOICEPRINT"));
        Assert.assertEquals(MrcpMethodName.VERIFY, MrcpMethodName.fromString("VERIFY"));
        Assert.assertEquals(MrcpMethodName.VERIFY_FROM_BUFFER, MrcpMethodName.fromString("VERIFY-FROM-BUFFER"));
        Assert.assertEquals(MrcpMethodName.VERIFY_ROLLBACK, MrcpMethodName.fromString("VERIFY-ROLLBACK"));
        Assert.assertEquals(MrcpMethodName.CLEAR_BUFFER, MrcpMethodName.fromString("CLEAR-BUFFER"));
        Assert.assertEquals(MrcpMethodName.GET_INTERMEDIATE_RESULT, MrcpMethodName.fromString("GET-INTERMEDIATE-RESULT"));
    }

    @Test
    public void testFromStringCaseInsensitive() {
        Assert.assertEquals(MrcpMethodName.SET_PARAMS, MrcpMethodName.fromString("set-params"));
        Assert.assertEquals(MrcpMethodName.SET_PARAMS, MrcpMethodName.fromString("Set-Params"));
        Assert.assertEquals(MrcpMethodName.SPEAK, MrcpMethodName.fromString("speak"));
        Assert.assertEquals(MrcpMethodName.SPEAK, MrcpMethodName.fromString("Speak"));
        Assert.assertEquals(MrcpMethodName.BARGE_IN_OCCURRED, MrcpMethodName.fromString("barge-in-occurred"));
        Assert.assertEquals(MrcpMethodName.BARGE_IN_OCCURRED, MrcpMethodName.fromString("Barge-In-Occurred"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromStringInvalid() {
        MrcpMethodName.fromString("INVALID-METHOD");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromStringNull() {
        MrcpMethodName.fromString(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromStringEmpty() {
        MrcpMethodName.fromString("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromStringWhitespace() {
        MrcpMethodName.fromString("   ");
    }

    @Test
    public void testEnumValues() {
        MrcpMethodName[] values = MrcpMethodName.values();
        Assert.assertTrue("Should have at least 28 method names", values.length >= 28);
        
        // Verify all enum values have non-null toString
        for (MrcpMethodName methodName : values) {
            Assert.assertNotNull("toString should not be null", methodName.toString());
            Assert.assertFalse("toString should not be empty", methodName.toString().isEmpty());
        }
    }

    @Test
    public void testEnumValueOf() {
        Assert.assertEquals(MrcpMethodName.SET_PARAMS, MrcpMethodName.valueOf("SET_PARAMS"));
        Assert.assertEquals(MrcpMethodName.GET_PARAMS, MrcpMethodName.valueOf("GET_PARAMS"));
        Assert.assertEquals(MrcpMethodName.SPEAK, MrcpMethodName.valueOf("SPEAK"));
        Assert.assertEquals(MrcpMethodName.STOP, MrcpMethodName.valueOf("STOP"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEnumValueOfInvalid() {
        MrcpMethodName.valueOf("INVALID_METHOD");
    }
}