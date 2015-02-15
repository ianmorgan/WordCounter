package com.example;

import java.util.Comparator;

/**
 * Collection style operations on an array.
 */
public class ArrayUtils<T> {

    private T[] data;

    public ArrayUtils(T[] data) {
        this.data = data;
    }

    /**
     * Sort  the first N elements
     * The remaining items are left unsorted.
     * This is optimised for sorting a small collection from a larger array
     *
     * <p/>
     * * @param number
     *
     * @param comparator
     * @return
     */
    public Object[] sortFirstN(int number, Comparator<T> comparator) {

        // not the most optimised sort algorithm, but as we only intended
        // to retrieve a smallish number, so its not so critical
        for (int i = 0; i < Math.min(number, data.length - 1); i++) {
            int index = i;
            for (int j = i + 1; j < data.length; j++)
                if (comparator.compare(data[j], data[index]) < 0)
                    index = j;

            T smaller = data[index];
            data[index] = data[i];
            data[i] = smaller;
        }

        return data;
    }
}
