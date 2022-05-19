package com.example.als;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class PasswordFragment extends Fragment {

    private TextView digit1, digit2, digit3, digit4;
    private Button generatePassword;
    private int otp = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_password, container, false);

        digit1 = view.findViewById(R.id.digit_one);
        digit1.setText(Constants.firebase_otp.substring(0, 1));
        digit2 = view.findViewById(R.id.digit_two);
        digit2.setText(Constants.firebase_otp.substring(1, 2));
        digit3 = view.findViewById(R.id.digit_three);
        digit3.setText(Constants.firebase_otp.substring(2, 3));
        digit4 = view.findViewById(R.id.digit_four);
        digit4.setText(Constants.firebase_otp.substring(3, 4));

        generatePassword = view.findViewById(R.id.generate_password_btn);
        generatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otp = setOtp();
                Constants.firebase_otp = String.valueOf(otp);
                saveOtp(otp);
            }
        });
        return view;
    }

    private int setOtp() {
        Random random = new Random();
        int d1 = random.nextInt(9) + 1;
        int d2 = random.nextInt(10);
        int d3 = random.nextInt(10);
        int d4 = random.nextInt(10);

        digit1.setText(String.valueOf(d1));
        digit2.setText(String.valueOf(d2));
        digit3.setText(String.valueOf(d3));
        digit4.setText(String.valueOf(d4));

        return d1*1000 + d2*100 + d3*10 + d4;
    }

    private void saveOtp(int otp) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("users").child("password").setValue(otp);
    }
}