package com.example.dictionary;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * Created by Андрей on 06.05.2017.
 */

public class ReadFromTXTDictionary {
        Context context;

        public ReadFromTXTDictionary() {

        }
        public Map<String , String> readfromfile(InputStream inFile)  {
            InputStreamReader inputStreamReader = null;
            BufferedReader br = null;
            Map<String, String> words = new TreeMap<>();
            try {
                inputStreamReader = new InputStreamReader(inFile, "UTF-8");
                br = new BufferedReader(inputStreamReader);
                for(String line=br.readLine(); line!=null; line=br.readLine()){
                    String delim = "#";
                    StringTokenizer st = new StringTokenizer(line, delim , false);
                    if (st.countTokens()==2){
                    String key = st.nextToken();
                    String value = st.nextToken();
                    words.put(key.trim() , value);}
                }
                br.close();
            } catch (UnsupportedEncodingException e) {e.printStackTrace();
            } catch (IOException e) {e.printStackTrace();}
            return words;
        }


    public Map<String , String> readfromExternalfile(String inFile, Context context)  {
        InputStreamReader inputStreamReader = null;
        BufferedReader br = null;
        Map<String, String> words = new TreeMap<>();
        try {
            inputStreamReader = new InputStreamReader(new FileInputStream(inFile), "UTF-8");
            br = new BufferedReader(inputStreamReader);
            for(String line=br.readLine(); line!=null; line=br.readLine()){

                String delim = "#";
                StringTokenizer st = new StringTokenizer(line, delim , false);
                if (st.countTokens()==2){
                    String key = st.nextToken();

                    String value = st.nextToken();
                    words.put(key , value);}
            }
            br.close();

        } catch (UnsupportedEncodingException e) {e.printStackTrace();
        } catch (IOException e) {e.printStackTrace();}
        return words;
    }

    public void writeToExternalFile(String outFile, CharSequence word1, CharSequence word2)  {
        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bw = null;

        try {
            outputStreamWriter = new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8");
            bw = new BufferedWriter(outputStreamWriter);
            bw.newLine();bw.write(word1 + "#" + word2);
            bw.close();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
