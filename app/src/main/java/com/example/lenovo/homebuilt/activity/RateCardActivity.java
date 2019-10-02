package com.example.lenovo.homebuilt.activity;

import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import com.example.lenovo.homebuilt.R;
import com.example.lenovo.homebuilt.adapter.ServiceTabAdapter;
import com.example.lenovo.homebuilt.fragment.RServiceInstallationFragment;
import com.example.lenovo.homebuilt.fragment.RServiceRepairFragment;

public class RateCardActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_card);

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
        tabLayout = (TabLayout) findViewById(R.id.rate_card_service_tabLayout);
        viewPager = (ViewPager) findViewById(R.id.rate_card_service_viewPager);

    }


    private void setupViewPager(ViewPager viewPager) {
        ServiceTabAdapter adapter = new ServiceTabAdapter(getSupportFragmentManager());
        adapter.addFragment(new RServiceInstallationFragment(),"Installation");
        adapter.addFragment(new RServiceRepairFragment(),"Repair");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
