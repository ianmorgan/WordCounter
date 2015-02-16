package com.example;

/**
 * A generic Key - Value pair for use in map style collections.
 */

public class KeyValue<K, V> {
    final K key;
    final V value;

    public KeyValue(K key, V value) {
        this.key = key;
        this.value = value;
    }
}