package com.cav.DriverphTruckerlearningPH2020;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Quizzes_menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizzes_menu);

        CardView cardViewBasic = findViewById(R.id.cardView_basic_competencies);
        Button btn_leaderboard = findViewById(R.id.button5);
        Button btn_list_completed_quizzes = findViewById(R.id.button6);

        cardViewBasic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Quizzes_menu.this, QuizInstructions.class));
            }
        });

        btn_leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://driver-ph.000webhostapp.com/"));
                startActivity(intent);
            }
        });

        btn_list_completed_quizzes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Quizzes_menu.this, CompletedQuizzes.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Quizzes_menu.this,Dashboard.class));
    }


}