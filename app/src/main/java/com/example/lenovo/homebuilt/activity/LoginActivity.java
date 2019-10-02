package com.example.lenovo.homebuilt.activity;

import android.graphics.Color;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lenovo.homebuilt.R;
import com.example.lenovo.homebuilt.fragment.LoginFragment;
import com.example.lenovo.homebuilt.fragment.SignUpFragment;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button sign_up, log_in;
    Toolbar toolbar;
    TextView toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initVar();

        sign_up.setOnClickListener(this);
        log_in.setOnClickListener(this);
    }

    private void initVar() {
       toolbar = (Toolbar) findViewById(R.id.toolbar);
        sign_up = (Button) findViewById(R.id.sign_up);
        log_in = (Button) findViewById(R.id.log_in);

      toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
     toolbar_title.setText(R.string.sign_up);
        sign_up.setBackgroundResource(R.drawable.selected_bottom_horizontal_line);
        sign_up.setTextColor(Color.parseColor("#9e0028"));
        displayLayout(new SignUpFragment());

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onClick(View v) {
        Fragment temp_fragment = null;
        switch (v.getId()) {
            case R.id.sign_up:

                sign_up.setTextColor(Color.parseColor("#9e0028"));
                log_in.setTextColor(Color.parseColor("#a6a5a5"));
                sign_up.setBackgroundResource(R.drawable.selected_bottom_horizontal_line);
                log_in.setBackgroundResource(R.drawable.unselected_bottom_horizontal_line);
                toolbar_title.setText(R.string.sign_up);
                temp_fragment = new SignUpFragment();

                break;

            case R.id.log_in:
                log_in.setTextColor(Color.parseColor("#9e0028"));
                sign_up.setTextColor(Color.parseColor("#a6a5a5"));
                log_in.setBackgroundResource(R.drawable.selected_bottom_horizontal_line);
                sign_up.setBackgroundResource(R.drawable.unselected_bottom_horizontal_line);
                toolbar_title.setText(R.string.login);
                temp_fragment = new LoginFragment();
                break;

        }
        displayLayout(temp_fragment);

    }

    private void displayLayout(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container, fragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
