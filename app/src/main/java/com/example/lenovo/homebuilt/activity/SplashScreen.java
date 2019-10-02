package com.example.lenovo.homebuilt.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.lenovo.homebuilt.R;

public class SplashScreen extends AppCompatActivity implements View.OnClickListener {

    Button get_start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        get_start = (Button) findViewById(R.id.getStart);
        get_start.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(SplashScreen.this,HomeScreenActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }
}
