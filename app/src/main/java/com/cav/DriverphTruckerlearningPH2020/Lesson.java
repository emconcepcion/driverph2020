package com.cav.DriverphTruckerlearningPH2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.anton46.stepsview.StepsView;
import com.cav.DriverphTruckerlearningPH2020.databinding.ActivityLessonBinding;
import com.muddzdev.styleabletoast.StyleableToast;

import static com.cav.DriverphTruckerlearningPH2020.Constant.SP_LESSONID;
import static com.cav.DriverphTruckerlearningPH2020.Dashboard.Uid_PREFS;
import static com.cav.DriverphTruckerlearningPH2020.Dashboard.dashboard_email;
import static com.cav.DriverphTruckerlearningPH2020.Dashboard.nameVR;
import static com.cav.DriverphTruckerlearningPH2020.Login.user_id;

public class Lesson extends AppCompatActivity {

    public static boolean isFromMyProgressNav;
    ActivityLessonBinding binding;
    String[] descriptionData = {"Most recent\nLesson", "Most recent\nRecitation", "Completed\nTests",
                                "Performance\nEvaluation", "Keep\nLearning!"};

    public static TextView progress_Module, progress_LessonTitle;
    int current_state = 0;
    Bundle data = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLessonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progress_Module = findViewById(R.id.progress_Module);
        progress_LessonTitle = findViewById(R.id.lessTitle);

        isFromMyProgressNav = true;
        Lessons_Basic_Content.isFromLessonBasicContent = false;

        SharedPreferences shp = getSharedPreferences("MySharedPrefForEmail", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = shp.edit();
        myEdit.putString("driver_email", dashboard_email);
        myEdit.apply();

        SharedPreferences sp1 = getSharedPreferences("mySavedAttempt", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sp1.edit();
        editor1.putString("email", dashboard_email);
        editor1.putString("username", nameVR);
        editor1.apply();

        SharedPreferences shpr = getSharedPreferences("MySharedPrefForEmail", MODE_PRIVATE);
        String thisUserId = shpr.getString("driver_userId", "");
        SharedPreferences sp = getApplicationContext().getSharedPreferences(Uid_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("user_id", Integer.parseInt(thisUserId));
        editor.apply();

        SharedPreferences sharedPreferences = getSharedPreferences(SP_LESSONID, MODE_PRIVATE);
        String progMod = sharedPreferences.getString("moduleName", "");
        String progLess = sharedPreferences.getString("lessonTitle", "");

        String null_lessonId = "No active modules yet.";
        if (progMod.equals("null") || progLess.equals("null")){
            progress_Module.setText(null_lessonId);
        }else{
            progress_Module.setText(progMod);
            progress_LessonTitle.setText(progLess);
        }

        binding.spb.setLabels(descriptionData)
                .setBarColorIndicator(getResources().getColor(R.color.blue))
                .setProgressColorIndicator(getResources().getColor(R.color.yellow))
                .setLabelColorIndicator(getResources().getColor(R.color.dark_blue))
                .setCompletedPosition(0)
                .drawView();

        binding.spb.setCompletedPosition(current_state);

        binding.btnNextLesson.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {

                if(current_state<(descriptionData.length-1)) {
                    current_state = current_state + 1;
                    binding.spb.setCompletedPosition(current_state).drawView();
                    if (data != null) {
                        current_state = data.getInt("progress", current_state);
                        switch (current_state){
                            case 1:
                                progress_Module.setText("Voice Response");
                                progress_LessonTitle.setText("This will play your last recorded recitation for the current session.\n" +
                                        "\nNote that all recordings are only stored momentarily while you are logged in.");
                                startActivity(new Intent(Lesson.this, VoiceResponse.class));
                                break;
                            case 2:
                                progress_Module.setText("Completed tests");
                                progress_LessonTitle.setText("This is where you can find the summary of the " +
                                        "results of the tests that you have successfully passed.");
                                startActivity(new Intent(Lesson.this, SummarizedScoresServer.class));
                                break;
                            case 3:
                                progress_Module.setText("Performance Evaluation");
                                progress_LessonTitle.setText("This shows how well or poor you have performed based " +
                                        "on the scores and number of retries you made for each module.");
                                startActivity(new Intent(Lesson.this, Evaluation_Menu.class));
                                break;
                            case 4:
                                progress_Module.setText("Way to go!");
                                progress_LessonTitle.setText("Keep learning and become a more knowledgeable trucker!");
                                break;
                            default:
                                String null_lessonId = "No active modules yet.";
                                if (progMod.equals("null") || progLess.equals("null")){
                                    progress_Module.setText(null_lessonId);
                                }else{
                                    progress_Module.setText(progMod);
                                    progress_LessonTitle.setText(progLess);
                                }
                                break;
                        }

                    } else {
                        Intent i = new Intent(Lesson.this, Lesson.class);
                        data = getIntent().getExtras();
                        binding.spb.setCompletedPosition(current_state);
                        i.putExtra("progress", current_state);
                        finish();
                        startActivity(getIntent());
                    }
//                    StyleableToast.makeText(Lesson.this, "Lesson number: " + (current_state+1), Toast.LENGTH_SHORT, R.style.toastStyle).show();
                }else if(binding.btnNextLesson.isPressed()){
                    startActivity(new Intent(Lesson.this, Dashboard.class));
                }

            }
        });

        binding.btnPrev.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {

                if(current_state>0) {
                    current_state = current_state - 1;
                    binding.spb.setCompletedPosition(current_state).drawView();

                    if (data != null) {
                        current_state = data.getInt("progress", current_state);
                        switch (current_state){
                            case 1:
                                progress_Module.setText("Voice Response");
                                progress_LessonTitle.setText("This will play your last recorded recitation for the current session.\n" +
                                        "\nNote that all recordings are only stored momentarily while you are logged in.");
                                startActivity(new Intent(Lesson.this, VoiceResponse.class));

                                break;
                            case 2:
                                progress_Module.setText("Completed tests");
                                progress_LessonTitle.setText("This is where you can find the summary of the " +
                                        "results of the tests that you have successfully passed.");
                                startActivity(new Intent(Lesson.this, SummarizedScoresServer.class));
                                break;
                            case 3:
                                progress_Module.setText("Performance Evaluation");
                                progress_LessonTitle.setText(R.string.perEval);
                                startActivity(new Intent(Lesson.this, Evaluation_Menu.class));
                                break;
                            case 4:
                                progress_Module.setText("Way to go!");
                                progress_LessonTitle.setText(R.string.keepLearning);
                                break;
                            default:
                                String null_lessonId = "No active modules yet.";
                                if (progMod.equals("null") || progLess.equals("null")){
                                    progress_Module.setText(null_lessonId);
                                }else{
                                    progress_Module.setText(progMod);
                                    progress_LessonTitle.setText(progLess);
                                }
                                break;
                        }
                    } else {
                        Intent i = new Intent(Lesson.this, Lesson.class);
                        data = getIntent().getExtras();
                        binding.spb.setCompletedPosition(current_state);
                        i.putExtra("progress", current_state);
                        binding.spb.setCompletedPosition(current_state);
                        finish();
                        startActivity(getIntent());
                    }
         //           StyleableToast.makeText(Lesson.this, "Lesson number: " + (current_state+1), Toast.LENGTH_SHORT, R.style.toastStyle).show();
                }
                //  Log.d("current state = ", current_state + "");
            }
        });


    }
}