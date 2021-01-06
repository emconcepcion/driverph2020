package com.cav.DriverphTruckerlearningPH2020;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Evaluation_Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_menu);

        CardView cardViewBasicBtn = findViewById(R.id.cardView_eMenu_basic_competencies);

        cardViewBasicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(com.cav.DriverphTruckerlearningPH2020.Evaluation_Menu.this,Evaluation_Basic_Content.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(com.cav.DriverphTruckerlearningPH2020.Evaluation_Menu.this,Lessons_Menu.class));
    }
}