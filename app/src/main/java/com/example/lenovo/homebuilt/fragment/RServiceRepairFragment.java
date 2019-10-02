package com.example.lenovo.homebuilt.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.lenovo.homebuilt.R;
import com.example.lenovo.homebuilt.adapter.RDServiceRepairAdapter;

import java.util.ArrayList;

public class RServiceRepairFragment extends Fragment {

    ListView service_installation_card;
    ArrayList<String> service_name;
    ArrayList<Integer> service_price ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service_repair_r, container, false);
        initVar(view);

        displayDataList();
        return view;
    }
    private void initVar(View view) {
        service_installation_card = (ListView) view.findViewById(R.id.card_repair_List);
        service_name = new ArrayList<>();
        service_price = new ArrayList<>();
    }

    private void displayDataList() {
        service_name.add("Commode(Close Coupled)");
        service_name.add("Commode(Wall Hanging)");
        service_name.add("Toilet Jet");
        service_name.add("Bath Tub");
        service_name.add("Wall Shower");
        service_name.add("Wash Basin");
        service_name.add("Sink Leakage");
        service_name.add("Toilet paper Holder");

        service_price.add(400);
        service_price.add(300);
        service_price.add(400);
        service_price.add(200);
        service_price.add(100);
        service_price.add(600);
        service_price.add(400);
        service_price.add(300);

        service_installation_card.setAdapter(new RDServiceRepairAdapter(getContext(),service_name,service_price));
    }


}
