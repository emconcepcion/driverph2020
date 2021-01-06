package com.cav.DriverphTruckerlearningPH2020;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import static com.cav.DriverphTruckerlearningPH2020.Constant._1;
import static com.cav.DriverphTruckerlearningPH2020.Constant._2;
import static com.cav.DriverphTruckerlearningPH2020.Constant._3;

public class Lessons_Menu extends AppCompatActivity {

    public static TextView tChapter;
    public static CardView cardViewMod1;
    public static CardView cardViewMod2;
    public static CardView cardViewMod3;
    SharedPreferences sp;
    public static TextView myEmailLesson;
    public static boolean isFromLessonsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons__menu);

        tChapter = findViewById(R.id.tChap_Lesson);
        cardViewMod1 = findViewById(R.id.cardView_basic_competencies);
        cardViewMod2 = findViewById(R.id.cardView_common_competencies);
        cardViewMod3 = findViewById(R.id.cardView_core_competencies);
        cardViewMod1.setClickable(true);

        Button btnEvaluation = findViewById(R.id.button5);
        myEmailLesson = findViewById(R.id.email_lesson);
//        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
//        String emailLesson = sharedPreferences.getString(EMAIL, "");
        myEmailLesson.setText(com.cav.DriverphTruckerlearningPH2020.Dashboard.dashboard_email);
        com.cav.DriverphTruckerlearningPH2020.Quizzes_menu.isFromQuizMenu = false;
        isFromLessonsMenu=true;

        cardViewMod1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tChapter.setText(_1);
                String chapTest = tChapter.getText().toString();
                sp = getSharedPreferences("SharedPrefChapter", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("chapter", chapTest);
                editor.apply();
                startActivity(new Intent(com.cav.DriverphTruckerlearningPH2020.Lessons_Menu.this, com.cav.DriverphTruckerlearningPH2020.Lessons_Basic_Content.class));

                String compe = "1";
                Intent intent = new Intent(com.cav.DriverphTruckerlearningPH2020.Lessons_Menu.this, com.cav.DriverphTruckerlearningPH2020.Basic_Content.class);
                Bundle extras = new Bundle();
                extras.putString("module", compe);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        cardViewMod2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFromLessonsMenu=true;
                tChapter.setText(_2);
                String chapTest = tChapter.getText().toString();
                sp = getSharedPreferences("SharedPrefChapter", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("chapter", chapTest);
                editor.apply();
                startActivity(new Intent(com.cav.DriverphTruckerlearningPH2020.Lessons_Menu.this, com.cav.DriverphTruckerlearningPH2020.Lessons_Basic_Content.class));
            }
        });

        cardViewMod3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tChapter.setText(_3);
                String chapTest = tChapter.getText().toString();
                sp = getSharedPreferences("SharedPrefChapter", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("chapter", chapTest);
                editor.apply();
                startActivity(new Intent(com.cav.DriverphTruckerlearningPH2020.Lessons_Menu.this, com.cav.DriverphTruckerlearningPH2020.Lessons_Basic_Content.class));
            }
        });

        btnEvaluation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(com.cav.DriverphTruckerlearningPH2020.Lessons_Menu.this, com.cav.DriverphTruckerlearningPH2020.Evaluation_Menu.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(com.cav.DriverphTruckerlearningPH2020.Lessons_Menu.this, com.cav.DriverphTruckerlearningPH2020.Dashboard.class);
        Bundle extras = new Bundle();
        extras.putString("email", myEmailLesson.getText().toString());
        intent.putExtras(extras);
        startActivity(intent);
    }
}