package com.cav.DriverphTruckerlearningPH2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cav.DriverphTruckerlearningPH2020.QuizActivity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QuizResults extends AppCompatActivity {

    ListView listView;
    TextView score_result, chapter_name;
    Button btn_willRetake, btn_willReview, btn_willUnlock;

    public int attempt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_results);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        btn_willRetake = findViewById(R.id.btn_retake);
        btn_willReview = findViewById(R.id.btn_review);
        btn_willUnlock = findViewById(R.id.btn_unlock);
        btn_willRetake.setVisibility(View.INVISIBLE);
        btn_willReview.setVisibility(View.INVISIBLE);
        btn_willUnlock.setVisibility(View.INVISIBLE);

        score_result = findViewById(R.id.txt_score_result);
        chapter_name = findViewById(R.id.chapter_name_result);
        listView = findViewById(R.id.list_view);
        String thisChapter = getIntent().getExtras().getString("chapter");
        chapter_name.setText(thisChapter);
        showResult();


    }

    public void showResult(){
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList = (ArrayList<String>) getIntent().getSerializableExtra("askedQuestions");
        int txt_score_result = getIntent().getExtras().getInt("score");
        int items_test = getIntent().getExtras().getInt("items");

        score_result.setText(txt_score_result + "/" + items_test);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);


        if(txt_score_result > (items_test * 0.8)){
           btn_willUnlock.setVisibility(View.VISIBLE);
           btn_willUnlock.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   unlock();
               }
           });
        }else if(txt_score_result < (items_test * 0.8)){
           btn_willRetake.setVisibility(View.VISIBLE);
           btn_willRetake.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   retake();
               }
           });

           btn_willReview.setVisibility(View.VISIBLE);
           btn_willReview.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   review();
               }
           });
        }

    }

    public void unlock(){
        startActivity(new Intent(QuizResults.this, Lessons_Menu.class));
    }
    public void review(){
        startActivity(new Intent(QuizResults.this, Lessons_Basic_Content.class));
    }
    public void retake() {
        //Intent intent = getIntent();

        SharedPreferences sp = getApplicationContext().getSharedPreferences("mySavedAttempt", Context.MODE_PRIVATE);
        int incAttempt = sp.getInt("attempt", 1);
//        attempt.setText(String.valueOf(incAttempt));

//        Bundle bundle = getIntent().getExtras();
//        int myRetakeAttempt = bundle.getInt("attempt");

        sp = getSharedPreferences("mySavedAttempt", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("attempt", (++incAttempt));
        editor.apply();
        Toast.makeText(this, "attempt was saved", Toast.LENGTH_SHORT).show();

        Intent resultIntent = new Intent(QuizResults.this, QuizActivity.class);
     //   resultIntent.putExtra("toQuizActAttempt", (++incAttempt));
        startActivity(resultIntent);
    }


//        QuizDbHelper dbHelper = new QuizDbHelper(this);
//        SQLiteDatabase database = dbHelper.getReadableDatabase();
//        Question question = new Question();
//        Cursor cursor = dbHelper.readFromLocalDatabase(database);
//
//        String thisChapter = chapter_name.getText().toString();
//        boolean matched = thisChapter.equals(question.getChapter());
//        if (matched){
//            QuizActivity quizActivity = new QuizActivity();
//        }


}

