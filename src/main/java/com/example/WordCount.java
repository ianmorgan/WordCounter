package com.example;

/**
 * Hold a word - count pair
 */
public class WordCount {
    private Word word;
    private Long count;

    public WordCount(Word word, Long count) {
        this.word = word;
        this.count = count;
    }

    public Word word() {
        return word;
    }

    public Long count() {
        return count;
    }
}
