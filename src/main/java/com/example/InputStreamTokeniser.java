package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by ian morgan on 12/02/15.
 * <p/>
 * Converts an input stream to a stream of tokens.
 */
public class InputStreamTokeniser implements Iterator<Word> {

    private boolean nextRead;
    private Word next;
    private BufferedReader in;

    public InputStreamTokeniser(InputStream is) {
        in = new BufferedReader(new InputStreamReader(is));
    }

    @Override
    public boolean hasNext() {
        if (!nextRead) {
            readAhead();
        }
        return next != null;
    }

    @Override
    public Word next() {
        if (!nextRead) {
            readAhead();
        }
        if (next != null) {
            nextRead = false;
            return next;
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void remove() {
        throw new RuntimeException("This should never have been allowed by Mr Gosling!");
    }

    private boolean isAlpha(int c) {
        return (c >= 65 && c <= 90) || (c >= 97 && c <= 122);
    }

    private char toLowerCase(int ch) {
        return (char) (ch > 90 ? ch : ch + 32);
    }

    private void readAhead() {
        try {
            // start capture group
            int c = in.read();
            while (!isAlpha(c) && (c != -1)) {
                c = in.read();
            }

            // end of stream - give up
            if (c == -1) {
                next = null;
                nextRead = true;
                return;
            }


            // buffer up until the first non alpha char
            GrowableCharBuffer buf = new GrowableCharBuffer();
            buf.addChar(toLowerCase(c));
            c = in.read();
            while (isAlpha(c)) {
                buf.addChar(toLowerCase(c));
                c = in.read();
            }

            next = new Word(buf.buffer());
            nextRead = true;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private class GrowableCharBuffer {
        private char[] buf = new char[8];
        private int length;

        public void addChar(char c) {
            if (length >= buf.length) {
                buf = Arrays.copyOf(buf, buf.length * 2);
            }
            buf[length++] = c;
        }

        public char[] buffer() {
            // now we have a word - for blazing fast performance it may nice to
            // live without the copyOf, but it would use up a tad more memory
            return Arrays.copyOf(buf, length);
        }


    }
}
