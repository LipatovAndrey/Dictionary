package com.example.dictionary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Андрей on 12.05.2017.
 */

public class listViewAdapter extends BaseAdapter {
    private Map<String , String> map;
    private List<String> listOfKeys;

    public listViewAdapter(Context context, Map<String, String> map) {
        this.map = map;
        listOfKeys = new LinkedList<>();
        Set<String> set = this.map.keySet();
        Iterator<String> iterator = set.iterator();



        while (iterator.hasNext()) {
            listOfKeys.add(iterator.next());
        }

    }

    @Override
    public int getCount() {
        return listOfKeys.size();
    }

    @Override
    public String getItem(int position) {
        return map.get(listOfKeys.get(position));
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        convertView = layoutInflater.inflate(R.layout.listitem, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.eng = (TextView) convertView.findViewById(R.id.first);
            viewHolder.rus = (TextView) convertView.findViewById(R.id.second);
            convertView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
       holder.eng.setText(listOfKeys.get(position));
        holder.rus.setText(getItem(position));
        return convertView;
    }
    class ViewHolder{
        private TextView rus;
        private TextView eng;
    }
}
