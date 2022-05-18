package com.example.als;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting up default fragment
        PasswordFragment passwordFragment = new PasswordFragment();
        FragmentTransaction passwordFragmentTransaction = getSupportFragmentManager().beginTransaction();
        passwordFragmentTransaction.replace(R.id.home_content, passwordFragment, "");
        passwordFragmentTransaction.commit();

        //Setting up bottom navigation bar items
        bottomNavigationView = findViewById(R.id.home_bottom_navigation_bar);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home_password:
                        PasswordFragment passwordFragment = new PasswordFragment();
                        FragmentTransaction passwordFragmentTransaction = getSupportFragmentManager().beginTransaction();
                        passwordFragmentTransaction.replace(R.id.home_content, passwordFragment, "");
                        passwordFragmentTransaction.commit();
                        return true;

                    case R.id.home_camera:
                        CameraFragment cameraFragment = new CameraFragment("https://als-2913.loca.lt/debug");
                        FragmentTransaction cameraFragmentTransaction = getSupportFragmentManager().beginTransaction();
                        cameraFragmentTransaction.replace(R.id.home_content, cameraFragment, "");
                        cameraFragmentTransaction.commit();
                        return true;

                    case R.id.home_location:
                        LocationFragment locationFragment= new LocationFragment();
                        FragmentTransaction locationFragmentTransaction = getSupportFragmentManager().beginTransaction();
                        locationFragmentTransaction.replace(R.id.home_content, locationFragment, "");
                        locationFragmentTransaction.commit();
                        return true;

                    case R.id.home_history:
                        HistoryFragment historyFragment = new HistoryFragment();
                        FragmentTransaction historyFragmentTransaction = getSupportFragmentManager().beginTransaction();
                        historyFragmentTransaction.replace(R.id.home_content, historyFragment, "");
                        historyFragmentTransaction.commit();
                        return true;

                    default:
                        return false;
                }
            }
        });
    }
}