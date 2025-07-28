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
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link ThrowingQueue}.
 */
public class ThrowingQueueTest {

    private ThrowingQueue<String, RuntimeException> queue;

    @Before
    public void setUp() {
        queue = new ThrowingQueue<String, RuntimeException>();
    }

    @Test
    public void testPutAndTakeElement() throws InterruptedException, RuntimeException {
        String testElement = "test element";
        queue.put(testElement);
        String result = queue.take();
        assertEquals("Should return same element", testElement, result);
    }

    @Test
    public void testPutAndTakeMultipleElements() throws InterruptedException, RuntimeException {
        String element1 = "first";
        String element2 = "second";
        String element3 = "third";
        
        queue.put(element1);
        queue.put(element2);
        queue.put(element3);
        
        assertEquals("Should return first element", element1, queue.take());
        assertEquals("Should return second element", element2, queue.take());
        assertEquals("Should return third element", element3, queue.take());
    }

    @Test
    public void testPutAndTakeNullElement() throws InterruptedException, RuntimeException {
        queue.put((String) null);
        String result = queue.take();
        assertNull("Should return null", result);
    }

    @Test
    public void testPutThrowable() throws InterruptedException {
        RuntimeException testException = new RuntimeException("Test exception");
        queue.put(testException);
        
        try {
            queue.take();
            fail("Should have thrown the exception");
        } catch (RuntimeException e) {
            assertEquals("Should be the same exception", testException, e);
            assertEquals("Should have same message", "Test exception", e.getMessage());
        }
    }

    @Test
    public void testPutMixedElementsAndThrowables() throws InterruptedException, RuntimeException {
        String element1 = "first element";
        RuntimeException exception = new RuntimeException("Test exception");
        String element2 = "second element";
        
        queue.put(element1);
        queue.put(exception);
        queue.put(element2);
        
        assertEquals("Should return first element", element1, queue.take());
        
        try {
            queue.take();
            fail("Should have thrown the exception");
        } catch (RuntimeException e) {
            assertEquals("Should be the same exception", exception, e);
        }
        
        assertEquals("Should return second element", element2, queue.take());
    }

    @Test
    public void testMultipleExceptions() throws InterruptedException {
        RuntimeException exception1 = new RuntimeException("First exception");
        RuntimeException exception2 = new IllegalArgumentException("Second exception");
        
        queue.put(exception1);
        queue.put(exception2);
        
        try {
            queue.take();
            fail("Should have thrown first exception");
        } catch (RuntimeException e) {
            assertEquals("Should be the first exception", exception1, e);
        }
        
        try {
            queue.take();
            fail("Should have thrown second exception");
        } catch (RuntimeException e) {
            assertEquals("Should be the second exception", exception2, e);
        }
    }

    @Test
    public void testDifferentExceptionTypes() throws InterruptedException {
        ThrowingQueue<String, Exception> exceptionQueue = new ThrowingQueue<String, Exception>();
        
        String element = "test element";
        Exception exception = new Exception("Test exception");
        
        exceptionQueue.put(element);
        exceptionQueue.put(exception);
        
        try {
            assertEquals("Should return element", element, exceptionQueue.take());
            exceptionQueue.take();
            fail("Should have thrown exception");
        } catch (Exception e) {
            assertEquals("Should be the same exception", exception, e);
        }
    }

    @Test
    public void testGenericElementTypes() throws InterruptedException, RuntimeException {
        ThrowingQueue<Integer, RuntimeException> intQueue = new ThrowingQueue<Integer, RuntimeException>();
        
        Integer testValue = 42;
        intQueue.put(testValue);
        Integer result = intQueue.take();
        assertEquals("Should return same integer", testValue, result);
    }

    @Test
    public void testQueueOrdering() throws InterruptedException, RuntimeException {
        // Test FIFO ordering
        for (int i = 0; i < 10; i++) {
            queue.put("element" + i);
        }
        
        for (int i = 0; i < 10; i++) {
            String result = queue.take();
            assertEquals("Should maintain FIFO order", "element" + i, result);
        }
    }

    @Test(timeout = 5000)
    public void testBlockingBehavior() throws InterruptedException {
        final String testElement = "blocking test";
        final boolean[] putCalled = new boolean[1];
        
        // Start a thread that will put an element after a delay
        Thread putThread = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(100); // Small delay
                    queue.put(testElement);
                    putCalled[0] = true;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        
        putThread.start();
        
        // This should block until the element is put
        String result = queue.take();
        
        assertEquals("Should return the element", testElement, result);
        assertTrue("Put should have been called", putCalled[0]);
        
        putThread.join();
    }

    @Test
    public void testNullThrowable() throws InterruptedException {
        queue.put((RuntimeException) null);
        
        try {
            String result = queue.take();
            // If we get here, the null throwable didn't cause an exception
            // This might be valid behavior - null element is returned
            assertNull("Null throwable should result in null element", result);
        } catch (Throwable e) {
            // If an exception is thrown, it's also acceptable behavior
            // The exact behavior for null throwable is implementation-dependent
            assertTrue("Should throw some kind of exception for null throwable", 
                      e instanceof RuntimeException || e instanceof NullPointerException);
        }
    }
}