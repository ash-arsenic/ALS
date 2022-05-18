package com.example.als;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoadDataFromApi {
    ApiListener listener;

    public LoadDataFromApi(Fragment context) {
        try{
            listener = (ApiListener) context;
        }catch(ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement ApiListener");
        }
    }

    public interface ApiListener {
        void send(String json, String type);
    }

    void load(String url, String type) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                listener.send("Failed", "");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                listener.send(response.body().string(), type);
            }
        });
    }
}
