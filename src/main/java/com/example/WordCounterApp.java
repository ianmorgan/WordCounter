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


        Map<Word, MutableLong> counters = new HashMap<>();

        SimpleHashMap<Word, MutableLong> counters2 = new SimpleHashMap<>();

        while (tokeniser.hasNext()) {
            Word word = tokeniser.next();
            MutableLong count = counters.get(word);
            if (count == null) {
                count = new MutableLong();
                counters.put(word, count);
            }
            count.theValue++;


            MutableLong count2 = counters2.get(word);
            if (count2 == null) {
                count2 = new MutableLong();
                counters2.put(word, count2);
            }
            count2.theValue++;
        }

        // now a full list of pairs
        List<WordCount> wordCounts = new ArrayList<>(counters.size());
        for (Map.Entry<Word, MutableLong> entry : counters.entrySet()) {
            wordCounts.add(new WordCount(entry.getKey(), entry.getValue().theValue));
        }

        Object[] wordCounts2 = counters2.toArray();

        System.out.println(wordCounts.size());
        System.out.println(wordCounts2.length);


        // sort them highest first
        Collections.sort(wordCounts, new Comparator<WordCount>() {
            @Override
            public int compare(WordCount a, WordCount b) {
                return b.count().compareTo(a.count());
            }
        });

        new ArrayUtils(wordCounts2).sortFirstN(20, new Comparator() {
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
        for (int i = 0; i < Math.min(wordCounts.size(), 20); i++) {
            WordCount wc = wordCounts.get(i);
            System.out.println(wc.count() + " " + wc.word());
        }

        for (int i = 0; i < Math.min(wordCounts2.length, 20); i++) {
            KeyValue<Word, MutableLong> wc = (KeyValue<Word, MutableLong>)wordCounts2[i];
            System.out.println(wc.value.theValue + " " + wc.key);
        }

    }
}