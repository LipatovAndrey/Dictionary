package com.example.dictionary;

import android.app.ListFragment;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements iteractWithanotherFragment {

    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.mainPgr);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

    }

    @Override
    public void AddToMyDict(CharSequence word1, CharSequence word2) {
        ReadFromTXTDictionary readFromTXTDictionary =new ReadFromTXTDictionary();
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        ((FragmentMyDict)fragments.get(1)).addWord((String) word1,(String) word2);
    }

    @Override
    public String getMediaFolder() {
        String separator ="/";
        String path = getApplicationContext().getFilesDir().toString()+separator + "dictionary.txt";
        return path;
    }

}

