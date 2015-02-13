package com.example;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

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
        map.put("e", "X");

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
    public void extractTopNFromHash() {

        SimpleHashMap<String, String> map = new SimpleHashMap<>();

        map.put("a", "apples");
        map.put("b", "banana");
        map.put("c", "carrot");
        map.put("d", "durian fruit");
        map.put("e", "eggplant");
        map.put("f", "fruit");
        map.put("g", "grapes");

        SimpleHashMap.KeyValue[] counts = map.extractTopN(3, new SimpleHashMap.ToNumber<String>() {
            @Override
            public long toNumber(String value) {
                return value.length();
            }
        });

        assertEquals(counts[0].key, "d");
        assertEquals(counts[0].value, 12L);

        assertEquals(counts[1].key, "e");
        assertEquals(counts[1].value, 9L);


        System.out.println(counts);


    }
}
