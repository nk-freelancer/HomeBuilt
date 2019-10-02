package com.example.lenovo.homebuilt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lenovo.homebuilt.R;

import java.util.ArrayList;


public class RDServiceRepairAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> name;
    private ArrayList<Integer> price;

    public RDServiceRepairAdapter(Context context, ArrayList<String> name, ArrayList<Integer> price) {
        this.context = context;
        this.name = name;
        this.price = price;
    }

    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.custom_card_repair_layout,null);

        TextView _name = (TextView) convertView.findViewById(R.id.service_repair_name);
        TextView _price = (TextView) convertView.findViewById(R.id.service_repair_price);

        _name.setText(name.get(position));
        _price.setText(String.valueOf(price.get(position)));
        return  convertView;
    }
}
