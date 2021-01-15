package com.cav.DriverphTruckerlearningPH2020;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;

public class Buffer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buffer);

        ProgressDialog pdLoading;

        pdLoading = new ProgressDialog(Buffer.this);
        pdLoading.setMessage("\tLoading...");
        pdLoading.setCancelable(true);
        pdLoading.show();
    }
}