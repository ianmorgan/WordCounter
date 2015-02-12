package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by ianmorgan on 12/02/15.
 */
public class InputStreamTokeniser implements Iterator<Word> {

    private boolean nextRead;
    private Word next;
    private BufferedReader in;


    public InputStreamTokeniser(InputStream is) {
        in = new BufferedReader(new InputStreamReader(is));

//        int data = in.read();
//        while(data != -1){
//            char theChar = (char) data;
//            System.out.print(theChar);
//            data = in.read();
//        }


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
        }
        else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void remove() {
        throw new RuntimeException("This should never have been allowed!");
    }

    private boolean isAlpha(int c) {
        return (c >= 65 && c <= 90) || (c >= 97 && c <= 122);
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

            // for simplicity we will assume no long word  !
            // could possible consider a linked list which could grow indefienetly
            // or even just a plain old  ArrayList
            char[] buf = new char[1024];


            // buffer up until the first non alpha char
            buf[0] = (char) c;
            int charsRead = 1;
            c = in.read();
            while (isAlpha(c)) {
                buf[charsRead++] = (char) c;
                c = in.read();
            }

            // now we have a word - for blazing fast performance it may nice to
            // get live without the copyOf
            next = new Word(Arrays.copyOf(buf, charsRead));
            nextRead = true;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
