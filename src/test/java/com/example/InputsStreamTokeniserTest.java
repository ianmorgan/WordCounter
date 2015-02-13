package com.example;

import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.util.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created by ianmorgan on 12/02/15.
 */
public class InputsStreamTokeniserTest {
    private InputStreamTokeniser tokeniser;

    @Test
    public void shouldTokeniseSimpleString() {
        // the basic test case
        tokeniser = new InputStreamTokeniser(new ByteArrayInputStream("moby dick".getBytes()));

        char[] moby = {'m', 'o', 'b', 'y'};
        char[] dick = {'d', 'i', 'c', 'k'};

        List<Word> expected = Arrays.asList(new Word(moby), new Word(dick));

        assertEquals(consumeIter(tokeniser), expected);
    }

    @Test
    public void shouldTokeniseWithPunctuationAndNumbers() {
        tokeniser = new InputStreamTokeniser(
                new ByteArrayInputStream("this, is bad's \npunctuation 101".getBytes()));

        List<Word> expected = Arrays.asList(new Word("this"), new Word("is"),
                new Word("bad"), new Word("s"), new Word("punctuation"));

        assertEquals(consumeIter(tokeniser), expected);
    }

    @Test
    public void shouldConvertToLowerCase() {
        tokeniser = new InputStreamTokeniser(
                new ByteArrayInputStream("THE End".getBytes()));

        List<Word> expected = Arrays.asList(new Word("the"), new Word("end"));

        assertEquals(consumeIter(tokeniser), expected);
    }

    @Test
    public void shouldGrowInternalBuffer() {
        // TODO: ideally this test should on GrowableCharBuffer
        tokeniser = new InputStreamTokeniser(
                new ByteArrayInputStream("aaaaaaa bbbbbbbb ccccccccc".getBytes()));

        List<Word> expected = Arrays.asList(new Word("aaaaaaa"), new Word("bbbbbbbb"), new Word("ccccccccc"));

        assertEquals(consumeIter(tokeniser), expected);
    }


    @Test
    public void shouldTokeniseRandomInputWithoutError() {
        byte[] data = new byte[10000];
        new Random().nextBytes(data);

        tokeniser = new InputStreamTokeniser(new ByteArrayInputStream(data));

        // should find at least one word token unless very unlucky (in monkeys writing shakespeare level of bad luck)
        assertTrue(consumeIter(tokeniser).size() > 0);
    }

    @Test
    public void shouldBeNoTokensInEmptyStream() {
        tokeniser = new InputStreamTokeniser(new ByteArrayInputStream(new byte[0]));
        assertTrue(consumeIter(tokeniser).isEmpty());
    }

    private List<Word> consumeIter(Iterator<Word> words) {
        List<Word> result = new ArrayList<>();
        while (words.hasNext()) {
            result.add(words.next());
        }
        return result;
    }

}
