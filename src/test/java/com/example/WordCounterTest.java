package com.example;

import org.testng.annotations.Test;

import java.io.*;

/**
 * Created by ianmorgan on 11/02/15.
 */
public class WordCounterTest {

    @Test
    public void readStream() throws Exception{

        File f = new File("test.txt");

        InputStream is = new FileInputStream(f);

        BufferedReader in
                = new BufferedReader(new InputStreamReader(is));

        int data = in.read();
        while(data != -1){
            char theChar = (char) data;
            System.out.print(theChar);
            data = in.read();
        }

    }
}
