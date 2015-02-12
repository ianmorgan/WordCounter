package com.example;

/**
 * An efficient way of keeping a word as an array of chars
 */
public class Word {
    private char[] word;

    public Word(char[] word) {
        this.word = word;
    }

    public Word(String word) {
        this(word.toCharArray());
    }

    @Override
    public int hashCode() {
        return word.length;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Word) {
            char[] otherWord = ((Word) other).word;
            if (otherWord.length == word.length) {
                for (int i = 0; i < word.length; i++) {
                    if (word[i] != otherWord[i]) {
                        return false;
                    }
                }
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return new String(word);
    }

}
