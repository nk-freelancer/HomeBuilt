package com.example.lenovo.homebuilt.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import com.example.lenovo.homebuilt.R;

public class ModularKitchenActivity extends AppCompatActivity {

    Toolbar toolbar;
    String myService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modular_kitchen);

        initVar();
    }

    private void initVar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        myService = getIntent().getStringExtra("service");
        toolbar.setTitle(myService);
    }
}
