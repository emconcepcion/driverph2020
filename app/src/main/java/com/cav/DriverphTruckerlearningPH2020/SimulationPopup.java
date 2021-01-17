package com.cav.DriverphTruckerlearningPH2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class SimulationPopup extends AppCompatActivity {

    TextView question,explanation,explanationTitle;
    ImageView questionImg;
    Button answer1,answer2,answer3,answer4;

    private SimulationPopupQuestions mQuestions = new SimulationPopupQuestions();

    private String mAnswer;
    private int mQuestionsLenght = mQuestions.mQuestions.length;

    String mExplanation,mTitle;

    Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation_popup);

        random = new Random();
        setScreen();
        updateQuestion(random.nextInt(mQuestionsLenght));
    }

    private void setScreen(){

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);
        if(getWindow() != null) {
            getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        question = findViewById(R.id.txtQuestion);
        explanation = findViewById(R.id.txtView_explanationHolder);
        explanationTitle = findViewById(R.id.txtView_explanationTitleHolder);
        questionImg = findViewById(R.id.imgView_QuestionPic);

        answer1 = findViewById(R.id.btnAnswer1);
        answer2 = findViewById(R.id.btnAnswer2);
        answer3 = findViewById(R.id.btnAnswer3);
        answer4 = findViewById(R.id.btnAnswer4);

        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answer1.getText()==mAnswer){
                    explanationTitle.setText(R.string.correct);
                }else {
                    explanationTitle.setText(R.string.incorrect);
                }
                mTitle = explanationTitle.getText().toString();
                mExplanation = explanation.getText().toString();
                Intent intent = new Intent(SimulationPopup.this, SimulationPopup2.class);
                Bundle bundleSend = new Bundle();
                bundleSend.putString("key_explanation",mExplanation);
                bundleSend.putString("key_title",mTitle);

                intent.putExtras(bundleSend);
                startActivity(intent);
                finish();
            }
        });

        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answer2.getText()==mAnswer){
                    explanationTitle.setText(R.string.correct);
                }else {
                    explanationTitle.setText(R.string.incorrect);
                }
                mTitle = explanationTitle.getText().toString();
                mExplanation = explanation.getText().toString();
                Intent intent = new Intent(SimulationPopup.this, SimulationPopup2.class);
                Bundle bundleSend = new Bundle();
                bundleSend.putString("key_explanation",mExplanation);
                bundleSend.putString("key_title",mTitle);

                intent.putExtras(bundleSend);
                startActivity(intent);
                finish();
            }
        });

        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answer3.getText()==mAnswer){
                    explanationTitle.setText(R.string.correct);
                }else {
                    explanationTitle.setText(R.string.incorrect);
                }
                mTitle = explanationTitle.getText().toString();
                mExplanation = explanation.getText().toString();
                Intent intent = new Intent(SimulationPopup.this, SimulationPopup2.class);
                Bundle bundleSend = new Bundle();
                bundleSend.putString("key_explanation",mExplanation);
                bundleSend.putString("key_title",mTitle);

                intent.putExtras(bundleSend);
                startActivity(intent);
                finish();
            }
        });

        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answer4.getText()==mAnswer){
                    explanationTitle.setText(R.string.correct);
                }else {
                    explanationTitle.setText(R.string.incorrect);
                }
                mTitle = explanationTitle.getText().toString();
                mExplanation = explanation.getText().toString();
                Intent intent = new Intent(SimulationPopup.this, SimulationPopup2.class);
                Bundle bundleSend = new Bundle();
                bundleSend.putString("key_explanation",mExplanation);
                bundleSend.putString("key_title",mTitle);

                //intent.putExtra("KEY", mExplanation);
                intent.putExtras(bundleSend);
                startActivity(intent);
                finish();
            }
        });
    }

    private void updateQuestion(int num){
        question.setText(mQuestions.getQuestions(num));
        explanation.setText(mQuestions.getExplanation(num));
        questionImg.setImageResource(mQuestions.getQuestionPics(num));
        answer1.setText(mQuestions.getChoice1(num));
        answer2.setText(mQuestions.getChoice2(num));
        answer3.setText(mQuestions.getChoice3(num));
        answer4.setText(mQuestions.getChoice4(num));

        mAnswer = mQuestions.getCorrectAnswers(num);

        mExplanation = explanation.toString().trim();

    }

}//SIMULATION POPUP Jan. 17, 9:05 AM