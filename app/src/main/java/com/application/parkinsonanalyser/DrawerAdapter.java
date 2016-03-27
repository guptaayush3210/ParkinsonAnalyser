package com.application.parkinsonanalyser;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by ayush on 27/3/16.
 */
public class DrawerAdapter extends ArrayAdapter<String> {

    private String[] item_list;
    private Context _context;
    public View v;
    public DrawerAdapter(Context context,String[] list){
        super(context,R.layout.list_view_item,list);
        _context = context;
        item_list = list;
    }

    @Override
    public View getView(int pos,View convertView, ViewGroup parent){
        v = convertView;

        String temp = getItem(pos);
        final String item;
        boolean isGroup = false;

        if (temp.startsWith("*")){
            Log.d("Check", "true");
            item = temp.substring(1);
            isGroup = true;
        }
        else {
            Log.d("Check","true");
            item = temp;
        }

        if(v==null) {
            LayoutInflater vi = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (isGroup)
                v=vi.inflate(R.layout.list_view_group, null);
            else
                v=vi.inflate(R.layout.list_view_item, null);
        }

        TextView tv;
        if (isGroup)
            tv = (TextView) v.findViewById(R.id.group);
        else
            tv = (TextView) v.findViewById(R.id.item);

        tv.setText(item);
        return v;
    }

    @Override
    public boolean isEnabled(int position){
        String temp = getItem(position);
        if (temp.startsWith("*")){
            return false;
        }
        return true;
    }
}
