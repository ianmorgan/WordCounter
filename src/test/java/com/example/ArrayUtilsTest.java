package com.example;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * Created by ianmorgan on 15/02/15.
 */
public class ArrayUtilsTest {


    @Test
    public void shouldSortFirstN() {
        List<Integer> nums = Arrays.asList(1, 9, 3, 2, 7, 4, 6, 5);

        Object[] results = new ArrayUtils(nums.toArray(new Integer[0])).sortFirstN(3, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });

        // first three items are sorted
        assertEquals(results[0], new Integer(9));
        assertEquals(results[1], new Integer(7));
        assertEquals(results[2], new Integer(6));

        // rest of array unordered
        assertEquals(results[7], new Integer(5));


    }

}


