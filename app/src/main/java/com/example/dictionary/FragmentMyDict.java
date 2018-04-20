package com.example.dictionary;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Андрей on 13.05.2017.
 */

public class FragmentMyDict extends Fragment {
    private ListView listView;
    private EditText editText;
    private TextView wordCount;
    private String path;
    private Context context;
    Map<String, String> myMap;
    ReadFromTXTDictionary readFromTXTDictionary;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readFromTXTDictionary = new ReadFromTXTDictionary();


    }

    @Override
    public void onStart() {
        super.onStart();
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view.findViewById(R.id.first);
                removeWord((String) tv.getText());
                return false;
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.itemformydictfragment, container, false);
        listView = (ListView) root.findViewById(R.id.mylistView);
        editText = (EditText) root.findViewById(R.id.myeditText);
        wordCount = (TextView) root.findViewById(R.id.mywordCount);
        context = container.getContext();
        path = ((iteractWithanotherFragment)container.getContext()).getMediaFolder();

        myMap = readFromTXTDictionary.readfromExternalfile(path,container.getContext());
        listView.setAdapter(new listViewAdapter(context, myMap));


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(start>count){
                    listView.setAdapter(new listViewAdapter(context, myMap));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                List<String> probablyKeys = new ArrayList<String>();
                Map<String, String> maphelper = new TreeMap<String, String>();
                Set<String> set =  myMap.keySet();
                Iterator<String> iterator = set.iterator();
                while (iterator.hasNext()){
                    String testkey = iterator.next();
                    if (testkey.contains(s)){ probablyKeys.add(testkey);}
                }
                for (String s1: probablyKeys){  if(myMap.containsKey(s1)){
                    maphelper.put(s1,myMap.get(s1));
                }}
                wordCount.setText(String.valueOf(probablyKeys.size()));
                listView.setAdapter(new listViewAdapter(context, maphelper));
            }
        });
        return root;
    }

    @Override
    public void onStop() {
        super.onStop();
        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bw = null;
        Set<String> set =  myMap.keySet();
        Iterator<String> iterator = set.iterator();
        try {
            outputStreamWriter = new OutputStreamWriter(new FileOutputStream(path), "UTF-8");
            bw = new BufferedWriter(outputStreamWriter);
            while (iterator.hasNext()){
                String testkey = iterator.next();
                bw.write(testkey + "#" + myMap.get(testkey) +"\n");
            }
            bw.close();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addWord( String word1, String word2) {
        myMap.put(word1, word2);
        listView.setAdapter(new listViewAdapter(context, myMap));


    }
    public void removeWord(String word1){
        myMap.remove(word1);
        listView.setAdapter(new listViewAdapter(context, myMap));

    }
}
