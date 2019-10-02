package com.example.lenovo.homebuilt.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.app.AlertDialog;

import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.lenovo.homebuilt.R;
import com.example.lenovo.homebuilt.fragment.HomeFragment;
import com.example.lenovo.homebuilt.other.UDSharePref;

public class HomeScreenActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;
    TextView nav_footer,nav_header_name,nav_header_email;
    UDSharePref userSharePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        initVar();
        displaySelectedScreen(R.id.nav_home);
        nav_footer.setOnClickListener(this);

    }

    private void initVar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText(R.string.appName);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        nav_footer = (TextView) navigationView.findViewById(R.id.nav_footer);
        View nav_header_view = navigationView.getHeaderView(0);

        nav_header_name = (TextView) nav_header_view.findViewById(R.id.nav_header_name);
        nav_header_email = (TextView) nav_header_view.findViewById(R.id.nav_header_email);
        userSharePref = new UDSharePref(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }


    @Override
    public void onClick(View v) {
        if (userSharePref.getStatus()){
            showLogoutDialog();
        }
        else {
            startActivity(new Intent(HomeScreenActivity.this, LoginActivity.class));
        }
        drawer.closeDrawer(GravityCompat.START);
    }

    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.logout)
                .setMessage("Do you wish to logout?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userSharePref.resetLogin();
                        onStart();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        displaySelectedScreen(id);
        return true;
    }

    private void displaySelectedScreen(int id) {
        Fragment temp_fragment = null;
        switch (id) {

            case R.id.nav_home:
                temp_fragment = new HomeFragment();
                break;

        }

        if (temp_fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_container,temp_fragment);
            ft.commit();
        }

        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (userSharePref.getStatus()){
            nav_footer.setText(R.string.logout);
        }
        else {
            nav_footer.setText(R.string.login);
        }

        nav_header_name.setText(userSharePref.getName());
        nav_header_email.setText(userSharePref.getEmail());
    }
}
