package com.example.lenovo.homebuilt.activity;

import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import com.example.lenovo.homebuilt.R;
import com.example.lenovo.homebuilt.adapter.ServiceTabAdapter;
import com.example.lenovo.homebuilt.fragment.InstallationFragment;
import com.example.lenovo.homebuilt.fragment.MyCartFragment;

public class BookServiceActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_service);


        initVar();
        String service_name = getIntent().getStringExtra("service");
        toolbar.setTitle(service_name);
        setSupportActionBar(toolbar);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initVar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.book_service_tabLayout);
        viewPager = (ViewPager) findViewById(R.id.book_service_viewPager);
    }


    private void setupViewPager(ViewPager viewPager) {
        ServiceTabAdapter adapter = new ServiceTabAdapter(getSupportFragmentManager());
        adapter.addFragment(new InstallationFragment(),"Installation");
        adapter.addFragment(new MyCartFragment(),"Repair");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
