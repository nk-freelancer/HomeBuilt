package com.example.lenovo.homebuilt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.lenovo.homebuilt.IncDescQuantity;
import com.example.lenovo.homebuilt.R;
import com.example.lenovo.homebuilt.model.BSInstallation;

import java.util.ArrayList;

public class BSInstallationAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<BSInstallation> bsInstallationArrayList;
    IncDescQuantity incDescQuantity;

    public BSInstallationAdapter(Context context, ArrayList<BSInstallation> bsInstallationArrayList,IncDescQuantity incDescQuantity) {
        this.context = context;
        this.bsInstallationArrayList = bsInstallationArrayList;
        this.incDescQuantity = incDescQuantity;
    }

    @Override
    public int getCount() {
        return bsInstallationArrayList.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.custom_installation_book_service_list,null);

        TextView name = (TextView) convertView.findViewById(R.id.service_name);
        ImageButton desc_quantity = (ImageButton) convertView.findViewById(R.id.desc_quantity);
        ImageButton inc_quantity  = (ImageButton) convertView.findViewById(R.id.inc_quantity);
        TextView quantity = (TextView) convertView.findViewById(R.id.quantity);
        TextView price = (TextView) convertView.findViewById(R.id.service_price);

        name.setText(bsInstallationArrayList.get(position).getName());
        quantity.setText(String.valueOf(bsInstallationArrayList.get(position).getQuantity()));
        price.setText(String.valueOf(bsInstallationArrayList.get(position).getPrice()));

        final View finalConvertView = convertView;
        desc_quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incDescQuantity.onDescQuantity(finalConvertView,position);
            }
        });

        inc_quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incDescQuantity.onIncQuantity(finalConvertView,position);
            }
        });
         return convertView;
    }
}
