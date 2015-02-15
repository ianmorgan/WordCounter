package com.example;

import org.testng.annotations.Test;

import java.util.Comparator;


import static org.testng.Assert.assertEquals;

/**
 * Created by ianmorgan on 15/02/15.
 */
public class ArrayUtilsTest {


    @Test
    public void shouldSortFirstN() {
        Integer[] nums = {1, 9, 3, 2, 7, 4, 6, 5};

        new ArrayUtils(nums).sortFirstN(3, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });

        // first three items are sorted
        assertEquals(nums[0], new Integer(9));
        assertEquals(nums[1], new Integer(7));
        assertEquals(nums[2], new Integer(6));

        // rest of array unordered
        assertEquals(nums[7], new Integer(5));
    }

}


