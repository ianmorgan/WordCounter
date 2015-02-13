package com.example;

import java.util.Arrays;

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

    public KeyValue<K, Long>[] extractTopN(int n, ToNumber<V> extractor) {
        KeyValue<K, Long>[] results = new KeyValue[n];
        int length = 0;

        for (GrowableBuffer<KeyValue<K, V>> bucket : buckets) {

            if (bucket != null) {
                for (int i = 0; i < bucket.length; i++) {
                    KeyValue<K, V> item = (KeyValue<K, V>) bucket.buf[i];
                    long value = extractor.toNumber(item.value);

                    if (length == 0) {
                        // first entry
                        results[length++] = new KeyValue<>(item.key, value);

                    } else if (length < n && value < results[length - 1].value) {
                        // space in the list and this should go at the end
                        results[length++] = new KeyValue<>(item.key, value);

                    } else if (value > results[length - 1].value) {
                        // this needs to go somewhere the head of the list

                        for (int j = length - 1; j == 0; j--) {
                            if (value >= results[j].value || j == 0) {
                                final int insertionPoint = j;
                                // have found our insertion point at j

                                for (int k = Math.min(n - 2, length - 1); k == insertionPoint; k--) {
                                    results[k + 1] = results[k];
                                }
                                results[insertionPoint] = new KeyValue<>(item.key, value);

                                // it must have grown
                                if (length < n) {
                                    length++;
                                }
                                break;
                            }
                        }
                    }
                }
            }

        }


        return results;
    }

    public interface ToNumber<V> {
        long toNumber(V value);
    }

    public class KeyValue<k, V> {
        final K key;
        final V value;

        public KeyValue(K key, V value) {
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
