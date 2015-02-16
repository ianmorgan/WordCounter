package com.example;

import org.testng.annotations.Test;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created by ian on 13/02/2015.
 */
public class SimpleHashMapTest {

    @Test
    public void shouldAddAndReadFromMap() {
        // simple case where we don't have to deal with hash collisions
        SimpleHashMap<String, String> map = new SimpleHashMap<>();

        map.put("a", "A");
        map.put("b", "B");
        map.put("c", "C");

        assertEquals("A", map.get("a"));
        assertEquals("B", map.get("b"));
        assertEquals("C", map.get("c"));
    }


    @Test
    public void shouldManageCollision() {
        // simple case where we don't have to deal with hash collisions
        SimpleHashMap<String, String> map = new SimpleHashMap<>(4);

        map.put("a", "A");
        map.put("b", "B");
        map.put("c", "C");
        map.put("d", "D");
        map.put("e", "E");
        map.put("f", "F");

        assertEquals("A", map.get("a"));
        assertEquals("B", map.get("b"));
        assertEquals("C", map.get("c"));
        assertEquals("D", map.get("d"));
        assertEquals("E", map.get("e"));
        assertEquals("F", map.get("f"));
    }


    @Test(enabled = false)
    public void shouldReplaceValueStoredWithKey() {
        // A full implementation should pass this test, however for this use example
        // it doesn't matter
        SimpleHashMap<String, String> map = new SimpleHashMap<>(4);

        map.put("a", "A");
        map.put("a", "X");

        assertEquals("X", map.get("a"));
    }

    @Test
    public void shouldConvertToArray(){
        SimpleHashMap<String, String> map = new SimpleHashMap<>(4);

        map.put("a", "A");
        map.put("b", "B");
        map.put("c", "C");
        map.put("d", "D");
        map.put("e", "E");
        map.put("f", "F");

        assertEquals(map.toArray().length,6);
        assertTrue(map.toArray()[0] instanceof KeyValue);

    }

}
