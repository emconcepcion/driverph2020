package com.cav.DriverphTruckerlearningPH2020;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import static com.cav.DriverphTruckerlearningPH2020.Constant._1;
import static com.cav.DriverphTruckerlearningPH2020.Constant._2;
import static com.cav.DriverphTruckerlearningPH2020.Constant._3;


public class PrepareForTest extends AppCompatActivity {

    public static String chapter;
    Button buttonStartQuiz, back_btn;
    private TextView textViewChapter, secondChance;
    String qChapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare_for_test);

        Dashboard.getmInstanceActivity().loadDataAllAttemptsAndLevels();

        buttonStartQuiz = findViewById(R.id.btn_start_myTest);
        back_btn = findViewById(R.id.btn_back_to_quizMenu);
        textViewChapter = findViewById(R.id.textview_module_title);
        secondChance = findViewById(R.id.textview_instruct_msg1);
        secondChance.setText("We believe in second chances, too.\nTake a deep breath and take the test again.");


        if (Lessons_Menu.isFromLessonsMenu) {
            SharedPreferences sp1 = getApplicationContext()
                    .getSharedPreferences("SharedPrefChapter", Context.MODE_PRIVATE);
            String lessonChap = sp1.getString("chapter", "");
            textViewChapter.setText(lessonChap);
        } else if (Quizzes_menu.isFromQuizMenu) {
            SharedPreferences sp2 = getApplicationContext()
                    .getSharedPreferences("ChapFromQuizzes", Context.MODE_PRIVATE);
            qChapter = sp2.getString("Qchapter", "");
            textViewChapter.setText(qChapter);
        }

        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToQuizMenu();
            }
        });
    }

    private void backToQuizMenu() {
        Intent intent = new Intent(PrepareForTest.this, Basic_Content.class);
        Bundle bundle = new Bundle();
        bundle.putString("module", qChapter);
        String moduleName = "";
        switch(qChapter){
            case "1":
                moduleName = _1;
                break;
            case "2":
                moduleName = _2;
                break;
            case "3":
                moduleName = _3;
                break;
        }
        bundle.putString("moduleName", moduleName);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void startQuiz() {
        String chapTitle = textViewChapter.getText().toString();
        if (checkNetworkConnection()) {
            Intent i = new Intent(PrepareForTest.this, QuizActivity.class);
            Bundle b = new Bundle();
            b.putString("chapter", chapTitle);
            Dashboard.recentModule.setText(chapTitle);
            i.putExtras(b);
            startActivity(i);
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(PrepareForTest.this).create();
            alertDialog.setTitle("Log in to Continue");
            alertDialog.setMessage("Please connect to the internet and log in before clicking \"Retake the Test.\"");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(PrepareForTest.this, Login.class));
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }

    //check for internet connection
    public boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}