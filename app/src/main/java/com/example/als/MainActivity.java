package com.example.als;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private LinearLayout mShowProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mShowProgress = findViewById(R.id.show_progress);
//        Loading password from firebase
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("users").child("password").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Couldn't reach the server", Toast.LENGTH_SHORT).show();
                }
                else {
                    Constants.firebase_otp = String.valueOf(task.getResult().getValue());
                    //Setting up default fragment
                    mShowProgress.setVisibility(View.GONE);
                    PasswordFragment passwordFragment = new PasswordFragment();
                    FragmentTransaction passwordFragmentTransaction = getSupportFragmentManager().beginTransaction();
                    passwordFragmentTransaction.replace(R.id.home_content, passwordFragment, "");
                    passwordFragmentTransaction.commit();
                }
            }
        });
//        Loading last image from firebase
        database.child("users").child("image").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Couldn't reach the server", Toast.LENGTH_SHORT).show();
                }
                else {
                    Constants.firebase_image = String.valueOf(task.getResult().getValue());
                }
            }
        });


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

    private void loadContent() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("users").child("password").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Couldn't reach the server", Toast.LENGTH_SHORT).show();
                }
                else {
                    Constants.firebase_otp = String.valueOf(task.getResult().getValue());
                }
            }
        });
    }
}