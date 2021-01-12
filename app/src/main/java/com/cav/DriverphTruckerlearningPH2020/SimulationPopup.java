package com.cav.DriverphTruckerlearningPH2020;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SimulationPopup extends AppCompatActivity {

    TextView tv;
    Button submitbutton, quitbutton;
    RadioGroup radio_g;
    RadioButton rb1,rb2,rb3,rb4;
    Dialog myDialog;

    String questions[] = {
            "Which method can be defined only once in a program?",
            "Which of these is not a bitwise operator?",
            "Which keyword is used by method to refer to the object that invoked it?",
            "Which of these keywords is used to define interfaces in Java?",
            "Which of these access specifiers can be used for an interface?",
            "Which of the following is correct way of importing an entire package ‘pkg’?",
            "What is the return type of Constructors?",
            "Which of the following package stores all the standard java classes?",
            "Which of these method of class String is used to compare two String objects for their equality?",
            "An expression involving byte, int, & literal numbers is promoted to which of these?"
    };

    String answers[] = {"main method","<=","this","interface","public","import pkg.*","None of the mentioned","java","equals()","int"};

    String opt[] = {
            "finalize method","main method","static method","private method",
            "&","&=","|=","<=",
            "import","this","catch","abstract",
            "Interface","interface","intf","Intf",
            "public","protected","private","All of the mentioned",
            "Import pkg.","import pkg.*","Import pkg.*","import pkg.",
            "int","float","void","None of the mentioned",
            "lang","java","util","java.packages",
            "equals()","Equals()","isequal()","Isequal()",
            "int","long","byte","float"
    };

    int flag=0;
    public static int marks=0,correct=0,wrong=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation_popup);

        setScreen();
        setBtn();

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

        myDialog = new Dialog(this);
    }

    private void showPopup2(){
        //To move to next popup screen START
        myDialog.setContentView(R.layout.activity_simulation_popup2);
        Button closepop2;
        closepop2 = myDialog.findViewById(R.id.btn_closePop);
        closepop2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
        // END
    }

    private void setBtn(){

        /**
         Button btnAction = findViewById(R.id.buttonAction);
         btnAction.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        finish();
        }
        });
         **/

        submitbutton = findViewById(R.id.buttonAction);
        tv = findViewById(R.id.txtQuestion);

        radio_g = findViewById(R.id.answersgrp);
        rb1 = findViewById(R.id.radioButton);
        rb2 = findViewById(R.id.radioButton2);
        rb3 = findViewById(R.id.radioButton3);
        rb4 = findViewById(R.id.radioButton4);
        tv.setText(questions[flag]);
        rb1.setText(opt[0]);
        rb2.setText(opt[1]);
        rb3.setText(opt[2]);
        rb4.setText(opt[3]);
        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(radio_g.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(getApplicationContext(), "Please select one choice", Toast.LENGTH_SHORT).show();
                    return;
                }
                RadioButton uans = (RadioButton) findViewById(radio_g.getCheckedRadioButtonId());
                String ansText = uans.getText().toString();
//                Toast.makeText(getApplicationContext(), ansText, Toast.LENGTH_SHORT).show();
                if(ansText.equals(answers[flag])) {
                    correct++;
                    Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_SHORT).show();

                    showPopup2();
                }
                else {
                    wrong++;
                    Toast.makeText(getApplicationContext(), "Wrong", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(SimulationPopup.this,SimulationPopup2.class));

                    showPopup2();
                }

                flag++;

                //if (score != null)
                //score.setText(""+correct);

                if(flag<questions.length)
                {
                    tv.setText(questions[flag]);
                    rb1.setText(opt[flag*4]);
                    rb2.setText(opt[flag*4 +1]);
                    rb3.setText(opt[flag*4 +2]);
                    rb4.setText(opt[flag*4 +3]);
                }

                radio_g.clearCheck();
            }
        });
    }



}