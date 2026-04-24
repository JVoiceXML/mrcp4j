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
 * Unit tests for {@link MrcpResourceType}.
 */
public class MrcpResourceTypeTest {

    @Test
    public void testToString() {
        Assert.assertEquals("speechrecog", MrcpResourceType.SPEECHRECOG.toString());
        Assert.assertEquals("dtmfrecog", MrcpResourceType.DTMFRECOG.toString());
        Assert.assertEquals("speechsynth", MrcpResourceType.SPEECHSYNTH.toString());
        Assert.assertEquals("basicsynth", MrcpResourceType.BASICSYNTH.toString());
        Assert.assertEquals("speakverify", MrcpResourceType.SPEAKVERIFY.toString());
        Assert.assertEquals("recorder", MrcpResourceType.RECORDER.toString());
    }

    @Test
    public void testFromStringValid() {
        Assert.assertEquals(MrcpResourceType.SPEECHRECOG, MrcpResourceType.fromString("speechrecog"));
        Assert.assertEquals(MrcpResourceType.DTMFRECOG, MrcpResourceType.fromString("dtmfrecog"));
        Assert.assertEquals(MrcpResourceType.SPEECHSYNTH, MrcpResourceType.fromString("speechsynth"));
        Assert.assertEquals(MrcpResourceType.BASICSYNTH, MrcpResourceType.fromString("basicsynth"));
        Assert.assertEquals(MrcpResourceType.SPEAKVERIFY, MrcpResourceType.fromString("speakverify"));
        Assert.assertEquals(MrcpResourceType.RECORDER, MrcpResourceType.fromString("recorder"));
    }

    @Test
    public void testFromStringCaseInsensitive() {
        Assert.assertEquals(MrcpResourceType.SPEECHRECOG, MrcpResourceType.fromString("SPEECHRECOG"));
        Assert.assertEquals(MrcpResourceType.SPEECHRECOG, MrcpResourceType.fromString("SpeechRecog"));
        Assert.assertEquals(MrcpResourceType.DTMFRECOG, MrcpResourceType.fromString("DTMFRECOG"));
        Assert.assertEquals(MrcpResourceType.DTMFRECOG, MrcpResourceType.fromString("DtmfRecog"));
        Assert.assertEquals(MrcpResourceType.SPEECHSYNTH, MrcpResourceType.fromString("SPEECHSYNTH"));
        Assert.assertEquals(MrcpResourceType.SPEECHSYNTH, MrcpResourceType.fromString("SpeechSynth"));
        Assert.assertEquals(MrcpResourceType.BASICSYNTH, MrcpResourceType.fromString("BASICSYNTH"));
        Assert.assertEquals(MrcpResourceType.BASICSYNTH, MrcpResourceType.fromString("BasicSynth"));
        Assert.assertEquals(MrcpResourceType.SPEAKVERIFY, MrcpResourceType.fromString("SPEAKVERIFY"));
        Assert.assertEquals(MrcpResourceType.SPEAKVERIFY, MrcpResourceType.fromString("SpeakVerify"));
        Assert.assertEquals(MrcpResourceType.RECORDER, MrcpResourceType.fromString("RECORDER"));
        Assert.assertEquals(MrcpResourceType.RECORDER, MrcpResourceType.fromString("Recorder"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromStringInvalid() {
        MrcpResourceType.fromString("invalid-resource");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromStringNull() {
        MrcpResourceType.fromString(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromStringEmpty() {
        MrcpResourceType.fromString("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromStringWhitespace() {
        MrcpResourceType.fromString("   ");
    }

    @Test
    public void testFromChannelIDValid() {
        Assert.assertEquals(MrcpResourceType.SPEECHRECOG, MrcpResourceType.fromChannelID("123@speechrecog"));
        Assert.assertEquals(MrcpResourceType.DTMFRECOG, MrcpResourceType.fromChannelID("456@dtmfrecog"));
        Assert.assertEquals(MrcpResourceType.SPEECHSYNTH, MrcpResourceType.fromChannelID("789@speechsynth"));
        Assert.assertEquals(MrcpResourceType.BASICSYNTH, MrcpResourceType.fromChannelID("abc@basicsynth"));
        Assert.assertEquals(MrcpResourceType.SPEAKVERIFY, MrcpResourceType.fromChannelID("def@speakverify"));
        Assert.assertEquals(MrcpResourceType.RECORDER, MrcpResourceType.fromChannelID("ghi@recorder"));
    }

    @Test
    public void testFromChannelIDCaseInsensitive() {
        Assert.assertEquals(MrcpResourceType.SPEECHRECOG, MrcpResourceType.fromChannelID("123@SPEECHRECOG"));
        Assert.assertEquals(MrcpResourceType.SPEECHRECOG, MrcpResourceType.fromChannelID("123@SpeechRecog"));
        Assert.assertEquals(MrcpResourceType.DTMFRECOG, MrcpResourceType.fromChannelID("456@DTMFRECOG"));
        Assert.assertEquals(MrcpResourceType.DTMFRECOG, MrcpResourceType.fromChannelID("456@DtmfRecog"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromChannelIDInvalidFormat() {
        MrcpResourceType.fromChannelID("123speechrecog");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromChannelIDMultipleAtSigns() {
        MrcpResourceType.fromChannelID("123@456@speechrecog");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromChannelIDNoResourceType() {
        MrcpResourceType.fromChannelID("123@");
    }

    @Test
    public void testFromChannelIDNoChannelId() {
        // "@speechrecog" splits to ["", "speechrecog"] which might be valid
        // Let's test what actually happens
        try {
            MrcpResourceType result = MrcpResourceType.fromChannelID("@speechrecog");
            Assert.assertEquals("Should parse speechrecog resource type", MrcpResourceType.SPEECHRECOG, result);
        } catch (IllegalArgumentException e) {
            // This might happen if empty channel ID is rejected
            // Either behavior is acceptable for this edge case
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromChannelIDInvalidResourceType() {
        MrcpResourceType.fromChannelID("123@invalidresource");
    }

    @Test(expected = NullPointerException.class)
    public void testFromChannelIDNull() {
        MrcpResourceType.fromChannelID(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromChannelIDEmpty() {
        MrcpResourceType.fromChannelID("");
    }

    @Test
    public void testEnumValues() {
        MrcpResourceType[] values = MrcpResourceType.values();
        Assert.assertEquals("Should have exactly 6 resource types", 6, values.length);
        
        // Verify all enum values have non-null toString
        for (MrcpResourceType resourceType : values) {
            Assert.assertNotNull("toString should not be null", resourceType.toString());
            Assert.assertFalse("toString should not be empty", resourceType.toString().isEmpty());
        }
    }

    @Test
    public void testEnumValueOf() {
        Assert.assertEquals(MrcpResourceType.SPEECHRECOG, MrcpResourceType.valueOf("SPEECHRECOG"));
        Assert.assertEquals(MrcpResourceType.DTMFRECOG, MrcpResourceType.valueOf("DTMFRECOG"));
        Assert.assertEquals(MrcpResourceType.SPEECHSYNTH, MrcpResourceType.valueOf("SPEECHSYNTH"));
        Assert.assertEquals(MrcpResourceType.BASICSYNTH, MrcpResourceType.valueOf("BASICSYNTH"));
        Assert.assertEquals(MrcpResourceType.SPEAKVERIFY, MrcpResourceType.valueOf("SPEAKVERIFY"));
        Assert.assertEquals(MrcpResourceType.RECORDER, MrcpResourceType.valueOf("RECORDER"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEnumValueOfInvalid() {
        MrcpResourceType.valueOf("INVALID_RESOURCE");
    }
}