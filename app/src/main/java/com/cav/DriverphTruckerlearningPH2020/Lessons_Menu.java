package com.cav.DriverphTruckerlearningPH2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Lessons_Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons__menu);

        CardView cardViewBasic = findViewById(R.id.cardView_basic_competencies);
        Button btnEvaluation = findViewById(R.id.button5);

        cardViewBasic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Lessons_Menu.this,Lessons_Basic_Content.class));
            }
        });

        btnEvaluation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Lessons_Menu.this,Evaluation_Menu.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Lessons_Menu.this,Dashboard.class));
    }
}