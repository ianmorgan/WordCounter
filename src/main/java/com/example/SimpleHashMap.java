package com.example;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ian on 13/02/2015.
 */
public class SimpleHashMap<K, V> {
    private int bucketCount;
    private HashEntry<K, V>[] buckets;

    private GrowableBuffer<HashEntry<K, V>>[] buckets2;

    // number of buckets - should be  4,8,16,32 etc
    public SimpleHashMap(int bucketSize) {
        bucketCount = bucketSize;
        buckets = new HashEntry[bucketCount];
        buckets2 = new GrowableBuffer[bucketCount];

        for (int i = 0; i < bucketCount; i++) {
            buckets[i] = null;
        }
    }

    public SimpleHashMap() {
        this(1024);
    }


    public void put(K key, V value) {
        int hash = (key.hashCode() % bucketCount);

        GrowableBuffer<HashEntry<K, V>> bucket = buckets2[hash];
        if (bucket == null) {
            bucket = new GrowableBuffer();
            buckets2[hash] = bucket;
        }

        bucket.append(new HashEntry<K, V>(key, value));
    }

    public V get(K key) {
        int hash = (key.hashCode() % bucketCount);

        GrowableBuffer<HashEntry<K, V>> bucket = buckets2[hash];
        if (bucket != null) {
            for (int i = 0; i < bucket.length; i++) {
                HashEntry<K, V> item = (HashEntry<K, V>) bucket.buf[i];
                if (item.key.equals(key)) {
                    return item.value;
                }
            }
        }
        return null;
    }


    private class HashEntry<k, V> {
        final K key;
        final V value;

        public HashEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private class GrowableBuffer<V> {
        Object[] buf = new Object[4];
        int length;

        public void append(V value) {
            if (length >= buf.length) {
                buf = Arrays.copyOf(buf, buf.length * 2);
            }
            buf[length++] = value;
        }

    }


}
