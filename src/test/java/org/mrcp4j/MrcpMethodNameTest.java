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
 * Unit tests for {@link MrcpMethodName}.
 */
public class MrcpMethodNameTest {

    @Test
    public void testToString() {
        assertEquals("SET-PARAMS", MrcpMethodName.SET_PARAMS.toString());
        assertEquals("GET-PARAMS", MrcpMethodName.GET_PARAMS.toString());
        assertEquals("SPEAK", MrcpMethodName.SPEAK.toString());
        assertEquals("STOP", MrcpMethodName.STOP.toString());
        assertEquals("PAUSE", MrcpMethodName.PAUSE.toString());
        assertEquals("RESUME", MrcpMethodName.RESUME.toString());
        assertEquals("BARGE-IN-OCCURRED", MrcpMethodName.BARGE_IN_OCCURRED.toString());
        assertEquals("CONTROL", MrcpMethodName.CONTROL.toString());
        assertEquals("DEFINE-LEXICON", MrcpMethodName.DEFINE_LEXICON.toString());
        assertEquals("DEFINE-GRAMMAR", MrcpMethodName.DEFINE_GRAMMAR.toString());
        assertEquals("RECOGNIZE", MrcpMethodName.RECOGNIZE.toString());
        assertEquals("INTERPRET", MrcpMethodName.INTERPRET.toString());
        assertEquals("GET-RESULT", MrcpMethodName.GET_RESULT.toString());
        assertEquals("START-INPUT-TIMERS", MrcpMethodName.START_INPUT_TIMERS.toString());
        assertEquals("START-PHRASE-ENROLLMENT", MrcpMethodName.START_PHRASE_ENROLLMENT.toString());
        assertEquals("ENROLLMENT-ROLLBACK", MrcpMethodName.ENROLLMENT_ROLLBACK.toString());
        assertEquals("END-PHRASE-ENROLLMENT", MrcpMethodName.END_PHRASE_ENROLLMENT.toString());
        assertEquals("MODIFY-PHRASE", MrcpMethodName.MODIFY_PHRASE.toString());
        assertEquals("DELETE-PHRASE", MrcpMethodName.DELETE_PHRASE.toString());
        assertEquals("RECORD", MrcpMethodName.RECORD.toString());
        assertEquals("START-SESSION", MrcpMethodName.START_SESSION.toString());
        assertEquals("END-SESSION", MrcpMethodName.END_SESSION.toString());
        assertEquals("QUERY-VOICEPRINT", MrcpMethodName.QUERY_VOICEPRINT.toString());
        assertEquals("DELETE-VOICEPRINT", MrcpMethodName.DELETE_VOICEPRINT.toString());
        assertEquals("VERIFY", MrcpMethodName.VERIFY.toString());
        assertEquals("VERIFY-FROM-BUFFER", MrcpMethodName.VERIFY_FROM_BUFFER.toString());
        assertEquals("VERIFY-ROLLBACK", MrcpMethodName.VERIFY_ROLLBACK.toString());
        assertEquals("CLEAR-BUFFER", MrcpMethodName.CLEAR_BUFFER.toString());
        assertEquals("GET-INTERMEDIATE-RESULT", MrcpMethodName.GET_INTERMEDIATE_RESULT.toString());
    }

    @Test
    public void testFromStringValid() {
        assertEquals(MrcpMethodName.SET_PARAMS, MrcpMethodName.fromString("SET-PARAMS"));
        assertEquals(MrcpMethodName.GET_PARAMS, MrcpMethodName.fromString("GET-PARAMS"));
        assertEquals(MrcpMethodName.SPEAK, MrcpMethodName.fromString("SPEAK"));
        assertEquals(MrcpMethodName.STOP, MrcpMethodName.fromString("STOP"));
        assertEquals(MrcpMethodName.PAUSE, MrcpMethodName.fromString("PAUSE"));
        assertEquals(MrcpMethodName.RESUME, MrcpMethodName.fromString("RESUME"));
        assertEquals(MrcpMethodName.BARGE_IN_OCCURRED, MrcpMethodName.fromString("BARGE-IN-OCCURRED"));
        assertEquals(MrcpMethodName.CONTROL, MrcpMethodName.fromString("CONTROL"));
        assertEquals(MrcpMethodName.DEFINE_LEXICON, MrcpMethodName.fromString("DEFINE-LEXICON"));
        assertEquals(MrcpMethodName.DEFINE_GRAMMAR, MrcpMethodName.fromString("DEFINE-GRAMMAR"));
        assertEquals(MrcpMethodName.RECOGNIZE, MrcpMethodName.fromString("RECOGNIZE"));
        assertEquals(MrcpMethodName.INTERPRET, MrcpMethodName.fromString("INTERPRET"));
        assertEquals(MrcpMethodName.GET_RESULT, MrcpMethodName.fromString("GET-RESULT"));
        assertEquals(MrcpMethodName.START_INPUT_TIMERS, MrcpMethodName.fromString("START-INPUT-TIMERS"));
        assertEquals(MrcpMethodName.START_PHRASE_ENROLLMENT, MrcpMethodName.fromString("START-PHRASE-ENROLLMENT"));
        assertEquals(MrcpMethodName.ENROLLMENT_ROLLBACK, MrcpMethodName.fromString("ENROLLMENT-ROLLBACK"));
        assertEquals(MrcpMethodName.END_PHRASE_ENROLLMENT, MrcpMethodName.fromString("END-PHRASE-ENROLLMENT"));
        assertEquals(MrcpMethodName.MODIFY_PHRASE, MrcpMethodName.fromString("MODIFY-PHRASE"));
        assertEquals(MrcpMethodName.DELETE_PHRASE, MrcpMethodName.fromString("DELETE-PHRASE"));
        assertEquals(MrcpMethodName.RECORD, MrcpMethodName.fromString("RECORD"));
        assertEquals(MrcpMethodName.START_SESSION, MrcpMethodName.fromString("START-SESSION"));
        assertEquals(MrcpMethodName.END_SESSION, MrcpMethodName.fromString("END-SESSION"));
        assertEquals(MrcpMethodName.QUERY_VOICEPRINT, MrcpMethodName.fromString("QUERY-VOICEPRINT"));
        assertEquals(MrcpMethodName.DELETE_VOICEPRINT, MrcpMethodName.fromString("DELETE-VOICEPRINT"));
        assertEquals(MrcpMethodName.VERIFY, MrcpMethodName.fromString("VERIFY"));
        assertEquals(MrcpMethodName.VERIFY_FROM_BUFFER, MrcpMethodName.fromString("VERIFY-FROM-BUFFER"));
        assertEquals(MrcpMethodName.VERIFY_ROLLBACK, MrcpMethodName.fromString("VERIFY-ROLLBACK"));
        assertEquals(MrcpMethodName.CLEAR_BUFFER, MrcpMethodName.fromString("CLEAR-BUFFER"));
        assertEquals(MrcpMethodName.GET_INTERMEDIATE_RESULT, MrcpMethodName.fromString("GET-INTERMEDIATE-RESULT"));
    }

    @Test
    public void testFromStringCaseInsensitive() {
        assertEquals(MrcpMethodName.SET_PARAMS, MrcpMethodName.fromString("set-params"));
        assertEquals(MrcpMethodName.SET_PARAMS, MrcpMethodName.fromString("Set-Params"));
        assertEquals(MrcpMethodName.SPEAK, MrcpMethodName.fromString("speak"));
        assertEquals(MrcpMethodName.SPEAK, MrcpMethodName.fromString("Speak"));
        assertEquals(MrcpMethodName.BARGE_IN_OCCURRED, MrcpMethodName.fromString("barge-in-occurred"));
        assertEquals(MrcpMethodName.BARGE_IN_OCCURRED, MrcpMethodName.fromString("Barge-In-Occurred"));
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
        assertTrue("Should have at least 28 method names", values.length >= 28);
        
        // Verify all enum values have non-null toString
        for (MrcpMethodName methodName : values) {
            assertNotNull("toString should not be null", methodName.toString());
            assertFalse("toString should not be empty", methodName.toString().isEmpty());
        }
    }

    @Test
    public void testEnumValueOf() {
        assertEquals(MrcpMethodName.SET_PARAMS, MrcpMethodName.valueOf("SET_PARAMS"));
        assertEquals(MrcpMethodName.GET_PARAMS, MrcpMethodName.valueOf("GET_PARAMS"));
        assertEquals(MrcpMethodName.SPEAK, MrcpMethodName.valueOf("SPEAK"));
        assertEquals(MrcpMethodName.STOP, MrcpMethodName.valueOf("STOP"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEnumValueOfInvalid() {
        MrcpMethodName.valueOf("INVALID_METHOD");
    }
}