package com.example;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by ian on 13/02/2015.
 */
public class SimpleHashMap<K, V> {
    private int bucketCount;
    private GrowableBuffer<KeyValue<K, V>>[] buckets;

    // number of buckets - should be  4,8,16,32 etc
    public SimpleHashMap(int bucketSize) {
        bucketCount = bucketSize;
        buckets = new GrowableBuffer[bucketCount];
        for (int i = 0; i < bucketCount; i++) {
            buckets[i] = null;
        }
    }

    public SimpleHashMap() {
        this(1024);
    }


    public void put(K key, V value) {
        int hash = (key.hashCode() % bucketCount);

        GrowableBuffer<KeyValue<K, V>> bucket = buckets[hash];
        if (bucket == null) {
            bucket = new GrowableBuffer();
            buckets[hash] = bucket;
        }

        bucket.append(new KeyValue<K, V>(key, value));
    }

    public V get(K key) {
        int hash = (key.hashCode() % bucketCount);

        GrowableBuffer<KeyValue<K, V>> bucket = buckets[hash];
        if (bucket != null) {
            for (int i = 0; i < bucket.length; i++) {
                KeyValue<K, V> item = (KeyValue<K, V>) bucket.buf[i];
                if (item.key.equals(key)) {
                    return item.value;
                }
            }
        }
        return null;
    }


    public Object[] toArray() {

        // TODO - for efficient we should ideally make some attempt to size correctly
        GrowableBuffer<KeyValue<K, V>> combined = new GrowableBuffer();
        for (int i = 0; i < bucketCount; i++) {
            GrowableBuffer<KeyValue<K, V>> bucket = buckets[i];
            if (bucket != null) {
                for (int j = 0; j < bucket.length; j++) {
                    combined.append((KeyValue<K, V>) bucket.buf[j]);
                }
            }
        }

        // work around generic array limitations
        // in Java language
        Object[] result = Arrays.copyOf(combined.buf, combined.length);
        return result;
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

    private class IteratorImpl implements Iterator<KeyValue<K, V>> {
        private boolean nextRead;
        private KeyValue<K, V> next;
        private int currentBucket;
        private int currentBucketItem;

        @Override
        public boolean hasNext() {
            if (!nextRead) {
                readAhead();
            }
            return next != null;
        }

        @Override
        public KeyValue<K, V> next() {
            if (!nextRead) {
                readAhead();
            }
            if (next != null) {
                nextRead = false;
                return next;
            } else {
                throw new NoSuchElementException();
            }
        }

        public void remove() {
            throw new RuntimeException("This should never have been allowed by Mr Gosling!");
        }


        private void readAhead() {


        }

    }


}
