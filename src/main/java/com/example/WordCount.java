package com.example;

/**
 * Generic way to manipulate a pair
 */
public class WordCount {
    private Word word;
    private Long count;

    public WordCount(Word one, Long two) {
        this.word = one;
        this.count = two;
    }

    public Word word() {
        return word;
    }

    public Long count() {
        return count;
    }
}
