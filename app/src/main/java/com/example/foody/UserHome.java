package com.example.foody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserHome extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;
    private Toolbar toolbar;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        bottomNavigation = findViewById(R.id.user_bottom_navigation);
        frameLayout = findViewById(R.id.user_main_frame);
        toolbar = findViewById(R.id.user_toolbar);

        toolbar.setTitle("FOODy");
        setSupportActionBar(toolbar);

        setFragment(new HomePage());

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_user_home:
                        setFragment(new HomePage());
                        return true;
                    case R.id.nav_user_purchase_history:
                        setFragment(new PurchaseItems());
                        return true;
                    case R.id.nav_user_cart:
                        setFragment(new CartItems());
                        return true;
                    case R.id.nav_user_profile:
                        setFragment(new UserProfile());
                        return true;
                    default:
                        return false;
                }
            }
        });
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

    private void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.user_main_frame,fragment);
        transaction.commit();
    }
}