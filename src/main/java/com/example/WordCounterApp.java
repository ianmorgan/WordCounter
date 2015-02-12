package com.example;

import java.io.*;
import java.util.*;

/**
 * An efficient way of keeping a word as an array of chars
 */
public class WordCounterApp {

    public static void main(String[] args) throws Exception {

        if (args.length == 0) {
            System.out.println("Must provide a file name");
            return;
        }

        File f = new File(args[0]);
        InputStreamTokeniser tokeniser = new InputStreamTokeniser(new FileInputStream(f));


        Map<Word, MutableLong> counters = new HashMap<Word, MutableLong>();
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
        List<WordCount> wordCounts = new ArrayList<WordCount>(counters.size());
        for (Map.Entry<Word, MutableLong> entry : counters.entrySet()) {
            wordCounts.add(new WordCount(entry.getKey(), entry.getValue().theValue));
        }

        // sort them highest first
        Collections.sort(wordCounts, new Comparator<WordCount>() {
            @Override
            public int compare(WordCount a, WordCount b) {
                return b.count().compareTo(a.count());
            }
        });

        // first twenty
        for (int i = 0; i < Math.min(wordCounts.size(), 20); i++) {
            WordCount wc = wordCounts.get(i);
            System.out.println(wc.count() + "=" + wc.word());
        }

    }
}