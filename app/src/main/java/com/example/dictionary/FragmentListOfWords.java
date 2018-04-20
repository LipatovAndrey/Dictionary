package com.example.dictionary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


/**
 * Created by Андрей on 12.05.2017.
 */

public class FragmentListOfWords extends Fragment {
    private ListView listView;
    private EditText editText;
    private TextView wordCount;
    Map<String, String> map;
    ReadFromTXTDictionary readFromTXTDictionary;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readFromTXTDictionary = new ReadFromTXTDictionary();
        this.map = readFromTXTDictionary.readfromfile(getResources().openRawResource(R.raw.dictionarya));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.itemforfragment, container, false);
        listView = (ListView) root.findViewById(R.id.listView);
        editText = (EditText) root.findViewById(R.id.editText);
        wordCount = (TextView) root.findViewById(R.id.wordCount);
        listView.setAdapter(new listViewAdapter(container.getContext(), map));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view.findViewById(R.id.first);
                TextView tv2 = (TextView) view.findViewById(R.id.second);
               ((iteractWithanotherFragment) getActivity()).AddToMyDict(tv.getText(), tv2.getText());
            }
        });


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(start>count){
                listView.setAdapter(new listViewAdapter(getContext(), map));
            }
            }

            @Override
            public void afterTextChanged(Editable s) {
                List<String> probablyKeys = new ArrayList<String>();
                Map<String, String> maphelper = new TreeMap<String, String>();
                Set<String>set =  map.keySet();
                Iterator<String> iterator = set.iterator();
                while (iterator.hasNext()){
                    String testkey = iterator.next();
                    if (testkey.equalsIgnoreCase(String.valueOf(s))){
                        Toast.makeText(container.getContext(), "==", Toast.LENGTH_SHORT).show();
                    }
                    if (testkey.contains(s)){
                        probablyKeys.add(testkey);
                    }
                }
                for (String s1: probablyKeys){  if(map.containsKey(s1)){
                    maphelper.put(s1,map.get(s1));
                }}
                wordCount.setText(String.valueOf(probablyKeys.size()));
                listView.setAdapter(new listViewAdapter(getContext(), maphelper));
            }
        });
        return root;
    }
}
