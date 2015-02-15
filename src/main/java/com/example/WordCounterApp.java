package com.example;

import java.io.*;
import java.util.*;

/**
 * The app to run through a list of words and count them
 */
public class WordCounterApp {

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Must provide a file name");
            return;
        }

        File f = new File(args[0]);
        InputStreamTokeniser tokeniser = new InputStreamTokeniser(new FileInputStream(f));


        SimpleHashMap<Word, MutableLong> counters = new SimpleHashMap<>(8192);
        while (tokeniser.hasNext()) {
            Word word = tokeniser.next();
            MutableLong count = counters.get(word);
            if (count == null) {
                count = new MutableLong();
                counters.put(word, count);
            }
            count.theValue++;
        }

        // now a full list of pairs
        Object[] wordCounts = counters.toArray();


        // sort them highest first
        new ArrayUtils(wordCounts).sortFirstN(20, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                KeyValue<Word, MutableLong> item1 = (KeyValue<Word, MutableLong>) o1;
                KeyValue<Word, MutableLong> item2 = (KeyValue<Word, MutableLong>) o2;

                if (item1.value.theValue > item2.value.theValue) {
                    return -1;
                } else if (item1.value.theValue < item2.value.theValue) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        // top twenty
        for (int i = 0; i < Math.min(wordCounts.length, 20); i++) {
            KeyValue<Word, MutableLong> wc = (KeyValue<Word, MutableLong>) wordCounts[i];
            System.out.println(wc.value.theValue + " " + wc.key);
        }

    }
}