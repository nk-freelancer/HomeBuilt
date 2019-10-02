package com.example.lenovo.homebuilt.adapter;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.homebuilt.R;
import com.example.lenovo.homebuilt.model.CustomReview;

import java.util.ArrayList;


public class CustomerReviewSliderAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<CustomReview> customReviewArrayList;


    public CustomerReviewSliderAdapter(Context context, ArrayList<CustomReview> customReviewArrayList) {
        this.context = context;
        this.customReviewArrayList = customReviewArrayList;
    }

    @Override
    public int getCount() {
        return customReviewArrayList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.custom_review_slider_layout, view, false);

        ImageView customer_image = (ImageView) convertView.findViewById(R.id.customer_image);
        TextView customer_text = (TextView) convertView.findViewById(R.id.customer_text);

        customer_image.setImageResource(customReviewArrayList.get(position).getImage());
        customer_text.setText(customReviewArrayList.get(position).getFeedback());
        view.addView(convertView, 0);
        return convertView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
