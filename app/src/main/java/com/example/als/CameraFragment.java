package com.example.als;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CameraFragment extends Fragment{

    private ImageView lastImage;
    private Button getImage;
    private LinearLayout progressIndicator;
    private String url;

    public CameraFragment(String url) {
        this.url = url;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);

        progressIndicator = view.findViewById(R.id.get_image_progress);

        lastImage = view.findViewById(R.id.last_image);
        Picasso.get().load(Constants.firebase_image).into(lastImage);
//        Setting up preview for full image
        lastImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog nagDialog = new Dialog(getContext(),android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                nagDialog.setContentView(R.layout.preview_image);
                ImageView ivPreview = (ImageView)nagDialog.findViewById(R.id.preview_image);
                ivPreview.setImageDrawable(lastImage.getDrawable());
                ImageButton backButton = (ImageButton) nagDialog.findViewById(R.id.back_button_dialog);
                backButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        nagDialog.dismiss();
                    }
                });
                nagDialog.show();
            }
        });

        getImage = view.findViewById(R.id.get_image_btn);
        getImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressIndicator.setVisibility(View.VISIBLE);
                getImage.setVisibility(View.GONE);

                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder().url(url).build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), "Server Down", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    String json = response.body().string();
                                    if (json.equals("404")) {
                                        Toast.makeText(getContext(), "Server Down", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    Constants.firebase_image = json;
                                    Picasso.get().load(Constants.firebase_image).into(lastImage);
                                    getImage.setVisibility(View.VISIBLE);
                                    progressIndicator.setVisibility(View.GONE);

                                } catch (IOException e) {
                                    Toast.makeText(getContext(), "Null returned from server", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                });
            }
        });
        return view;
    }
}