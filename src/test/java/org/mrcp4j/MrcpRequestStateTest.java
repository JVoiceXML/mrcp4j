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
 * Unit tests for {@link MrcpRequestState}.
 */
public class MrcpRequestStateTest {

    @Test
    public void testToString() {
        Assert.assertEquals("PENDING", MrcpRequestState.PENDING.toString());
        Assert.assertEquals("IN-PROGRESS", MrcpRequestState.IN_PROGRESS.toString());
        Assert.assertEquals("COMPLETE", MrcpRequestState.COMPLETE.toString());
    }

    @Test
    public void testFromStringValid() {
        Assert.assertEquals(MrcpRequestState.PENDING, MrcpRequestState.fromString("PENDING"));
        Assert.assertEquals(MrcpRequestState.IN_PROGRESS, MrcpRequestState.fromString("IN-PROGRESS"));
        Assert.assertEquals(MrcpRequestState.COMPLETE, MrcpRequestState.fromString("COMPLETE"));
    }

    @Test
    public void testFromStringCaseInsensitive() {
        Assert.assertEquals(MrcpRequestState.PENDING, MrcpRequestState.fromString("pending"));
        Assert.assertEquals(MrcpRequestState.PENDING, MrcpRequestState.fromString("Pending"));
        Assert.assertEquals(MrcpRequestState.IN_PROGRESS, MrcpRequestState.fromString("in-progress"));
        Assert.assertEquals(MrcpRequestState.IN_PROGRESS, MrcpRequestState.fromString("In-Progress"));
        Assert.assertEquals(MrcpRequestState.COMPLETE, MrcpRequestState.fromString("complete"));
        Assert.assertEquals(MrcpRequestState.COMPLETE, MrcpRequestState.fromString("Complete"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromStringInvalid() {
        MrcpRequestState.fromString("INVALID-STATE");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromStringNull() {
        MrcpRequestState.fromString(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromStringEmpty() {
        MrcpRequestState.fromString("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromStringWhitespace() {
        MrcpRequestState.fromString("   ");
    }

    @Test
    public void testEnumValues() {
        MrcpRequestState[] values = MrcpRequestState.values();
        Assert.assertEquals("Should have exactly 3 request states", 3, values.length);
        
        // Verify all enum values have non-null toString
        for (MrcpRequestState requestState : values) {
            Assert.assertNotNull("toString should not be null", requestState.toString());
            Assert.assertFalse("toString should not be empty", requestState.toString().isEmpty());
        }
    }

    @Test
    public void testEnumValueOf() {
        Assert.assertEquals(MrcpRequestState.PENDING, MrcpRequestState.valueOf("PENDING"));
        Assert.assertEquals(MrcpRequestState.IN_PROGRESS, MrcpRequestState.valueOf("IN_PROGRESS"));
        Assert.assertEquals(MrcpRequestState.COMPLETE, MrcpRequestState.valueOf("COMPLETE"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEnumValueOfInvalid() {
        MrcpRequestState.valueOf("INVALID_STATE");
    }
}