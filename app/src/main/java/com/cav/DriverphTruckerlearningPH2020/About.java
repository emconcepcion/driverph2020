package com.cav.DriverphTruckerlearningPH2020;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class About extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView tv = (TextView) findViewById(R.id.tv_about);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tv.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        }
    }
}