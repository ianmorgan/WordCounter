package com.example;

/**
 * Created by ianmorgan on 15/02/15.
 */

public class KeyValue<K, V> {
    final K key;
    final V value;

    public KeyValue(K key, V value) {
        this.key = key;
        this.value = value;
    }
}