package com.cav.DriverphTruckerlearningPH2020;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Evaluation_Basic_Content extends AppCompatActivity {

    ConstraintLayout seeDetailsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation__basic__content);

        btnSetter();
    }

    public void btnSetter(){
        seeDetailsContainer = findViewById(R.id.seeDetails_container);

        TextView btnTxtViewDetails = findViewById(R.id.textView36);
        btnTxtViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (seeDetailsContainer.getVisibility() == View.VISIBLE)
                    seeDetailsContainer.setVisibility(View.INVISIBLE);
                else
                    seeDetailsContainer.setVisibility(View.VISIBLE);
            }
        });

        Button btnBack = findViewById(R.id.button6);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(com.cav.DriverphTruckerlearningPH2020.Evaluation_Basic_Content.this, com.cav.DriverphTruckerlearningPH2020.Evaluation_Menu.class));
            }
        });
    }
}