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
 * Unit tests for {@link MrcpEventName}.
 */
public class MrcpEventNameTest {

    @Test
    public void testToString() {
        assertEquals("SPEECH-MARKER", MrcpEventName.SPEECH_MARKER.toString());
        assertEquals("SPEAK-COMPLETE", MrcpEventName.SPEAK_COMPLETE.toString());
        assertEquals("START-OF-INPUT", MrcpEventName.START_OF_INPUT.toString());
        assertEquals("RECOGNITION-COMPLETE", MrcpEventName.RECOGNITION_COMPLETE.toString());
        assertEquals("INTERPRETATION-COMPLETE", MrcpEventName.INTERPRETATION_COMPLETE.toString());
        assertEquals("RECORD-COMPLETE", MrcpEventName.RECORD_COMPLETE.toString());
        assertEquals("VERIFICATION-COMPLETE", MrcpEventName.VERIFICATION_COMPLETE.toString());
    }

    @Test
    public void testFromStringValid() {
        assertEquals(MrcpEventName.SPEECH_MARKER, MrcpEventName.fromString("SPEECH-MARKER"));
        assertEquals(MrcpEventName.SPEAK_COMPLETE, MrcpEventName.fromString("SPEAK-COMPLETE"));
        assertEquals(MrcpEventName.START_OF_INPUT, MrcpEventName.fromString("START-OF-INPUT"));
        assertEquals(MrcpEventName.RECOGNITION_COMPLETE, MrcpEventName.fromString("RECOGNITION-COMPLETE"));
        assertEquals(MrcpEventName.INTERPRETATION_COMPLETE, MrcpEventName.fromString("INTERPRETATION-COMPLETE"));
        assertEquals(MrcpEventName.RECORD_COMPLETE, MrcpEventName.fromString("RECORD-COMPLETE"));
        assertEquals(MrcpEventName.VERIFICATION_COMPLETE, MrcpEventName.fromString("VERIFICATION-COMPLETE"));
    }

    @Test
    public void testFromStringCaseInsensitive() {
        assertEquals(MrcpEventName.SPEECH_MARKER, MrcpEventName.fromString("speech-marker"));
        assertEquals(MrcpEventName.SPEECH_MARKER, MrcpEventName.fromString("Speech-Marker"));
        assertEquals(MrcpEventName.SPEAK_COMPLETE, MrcpEventName.fromString("speak-complete"));
        assertEquals(MrcpEventName.SPEAK_COMPLETE, MrcpEventName.fromString("Speak-Complete"));
        assertEquals(MrcpEventName.START_OF_INPUT, MrcpEventName.fromString("start-of-input"));
        assertEquals(MrcpEventName.START_OF_INPUT, MrcpEventName.fromString("Start-Of-Input"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromStringInvalid() {
        MrcpEventName.fromString("INVALID-EVENT");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromStringNull() {
        MrcpEventName.fromString(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromStringEmpty() {
        MrcpEventName.fromString("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromStringWhitespace() {
        MrcpEventName.fromString("   ");
    }

    @Test
    public void testEnumValues() {
        MrcpEventName[] values = MrcpEventName.values();
        assertEquals("Should have exactly 7 event names", 7, values.length);
        
        // Verify all enum values have non-null toString
        for (MrcpEventName eventName : values) {
            assertNotNull("toString should not be null", eventName.toString());
            assertFalse("toString should not be empty", eventName.toString().isEmpty());
        }
    }

    @Test
    public void testEnumValueOf() {
        assertEquals(MrcpEventName.SPEECH_MARKER, MrcpEventName.valueOf("SPEECH_MARKER"));
        assertEquals(MrcpEventName.SPEAK_COMPLETE, MrcpEventName.valueOf("SPEAK_COMPLETE"));
        assertEquals(MrcpEventName.START_OF_INPUT, MrcpEventName.valueOf("START_OF_INPUT"));
        assertEquals(MrcpEventName.RECOGNITION_COMPLETE, MrcpEventName.valueOf("RECOGNITION_COMPLETE"));
        assertEquals(MrcpEventName.INTERPRETATION_COMPLETE, MrcpEventName.valueOf("INTERPRETATION_COMPLETE"));
        assertEquals(MrcpEventName.RECORD_COMPLETE, MrcpEventName.valueOf("RECORD_COMPLETE"));
        assertEquals(MrcpEventName.VERIFICATION_COMPLETE, MrcpEventName.valueOf("VERIFICATION_COMPLETE"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEnumValueOfInvalid() {
        MrcpEventName.valueOf("INVALID_EVENT");
    }
}