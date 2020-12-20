package com.cav.DriverphTruckerlearningPH2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class QuizInstructions extends AppCompatActivity {

    Button buttonStartQuiz, back_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_instructions);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        toolbar.setTitle("Quiz Instructions");
        buttonStartQuiz = findViewById(R.id.btn_start_quiz);
        back_btn = findViewById(R.id.btn_back_to_take_quiz);
        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToLessons();
            }
        });

    }

    private void backToLessons() {
        startActivity(new Intent(QuizInstructions.this, Lessons_Basic_Content.class));
    }

    private void startQuiz() {
        Intent intent = new Intent(QuizInstructions.this, QuizActivity.class);
        startActivity(intent);
    }
}