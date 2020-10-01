package com.example.foody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminHome extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;
    private Toolbar toolbar;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        bottomNavigation = findViewById(R.id.admin_bottom_navigation);
        frameLayout = findViewById(R.id.admin_main_frame);
        toolbar = findViewById(R.id.admin_toolbar);

        toolbar.setTitle("FOODy Admin");
        setSupportActionBar(toolbar);

        setFragment(new AdminHomePage());

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_admin_home:
                        setFragment(new AdminHomePage());
                        return true;
                    case R.id.nav_admin_add_product:
                        setFragment(new AddProduct());
                        return true;
                    case R.id.nav_admin_profile:
                        setFragment(new AdminProfile());
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.admin_main_frame,fragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_user_logout:
                //auth.signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                return true;
            default:
                return false;
        }
    }
}