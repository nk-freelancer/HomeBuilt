package com.example.lenovo.homebuilt.adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.homebuilt.R;
import com.example.lenovo.homebuilt.model.Services;

import java.util.ArrayList;


public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Services> servicesArrayList;

    public ServiceAdapter(Context context, ArrayList<Services> servicesArrayList) {
        this.context = context;
        this.servicesArrayList = servicesArrayList;
    }

    @Override
    public ServiceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_trending_services_layout,parent,false);
        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(ServiceAdapter.ViewHolder holder, int position) {
        holder.service_image.setImageResource(servicesArrayList.get(position).getIcon());
        holder.service_name.setText(servicesArrayList.get(position).getName());
        holder.service_charge.setText(String.valueOf(servicesArrayList.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return servicesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView service_image;
        TextView service_name;
        TextView service_charge;

        public ViewHolder(View itemView) {
            super(itemView);

            service_image = (ImageView) itemView.findViewById(R.id.trending_service_image);
            service_name = (TextView) itemView.findViewById(R.id.trending_service_name);
            service_charge = (TextView) itemView.findViewById(R.id.trending_service_charge);
        }
    }
}