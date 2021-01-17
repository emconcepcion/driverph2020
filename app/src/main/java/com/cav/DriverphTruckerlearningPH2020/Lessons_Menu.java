package com.cav.DriverphTruckerlearningPH2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.cav.DriverphTruckerlearningPH2020.Basic_Content.currentLesson;
import static com.cav.DriverphTruckerlearningPH2020.Basic_Content.moduleName;
import static com.cav.DriverphTruckerlearningPH2020.Constant.MODULE_ID_1;
import static com.cav.DriverphTruckerlearningPH2020.Constant.MODULE_ID_2;
import static com.cav.DriverphTruckerlearningPH2020.Constant.MODULE_ID_3;
import static com.cav.DriverphTruckerlearningPH2020.Constant.SP_LESSONID;
import static com.cav.DriverphTruckerlearningPH2020.Constant._1;
import static com.cav.DriverphTruckerlearningPH2020.Constant._2;
import static com.cav.DriverphTruckerlearningPH2020.Constant._3;
import static com.cav.DriverphTruckerlearningPH2020.Dashboard.dashboard_email;
import static com.cav.DriverphTruckerlearningPH2020.Login.SHARED_PREFS;
import static com.cav.DriverphTruckerlearningPH2020.Quizzes_menu.latestCompleted;
import static com.cav.DriverphTruckerlearningPH2020.Quizzes_menu.latestUnlocked;
import static com.cav.DriverphTruckerlearningPH2020.Quizzes_menu.uidDb_txt;
import static com.cav.DriverphTruckerlearningPH2020.Quizzes_menu.userIdQMenu;

public class Lessons_Menu extends AppCompatActivity {

    public static TextView tChapter, userIdLess, uidDbLess;
    TextView mylatestModLockedLesson, mylatestModCompletedLesson;
    public static CardView cardViewMod1;
    public static CardView cardViewMod2;
    public static CardView cardViewMod3;
    SharedPreferences sp;
    public static TextView myEmailLesson;
    public static boolean isFromLessonsMenu;
    public static String compe, lessonIdServer, lessonTitleServer,
            moduleIdServer, moduleNameServer, mod, dash_email;
    public static int latestUnlocked, latestCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons__menu);

        tChapter = findViewById(R.id.tChap_Lesson);
        userIdLess = findViewById(R.id.txt_userIdLess);
        uidDbLess = findViewById(R.id.txt_uidDbLess);
        mylatestModLockedLesson = findViewById(R.id.latestMod1LockedLesson);
        mylatestModCompletedLesson = findViewById(R.id.latestMod1CompletedLesson);
        mylatestModLockedLesson.setText(Dashboard.myLatestIsUnlocked);
        mylatestModCompletedLesson.setText(Dashboard.myLatestIsCompleted);

        cardViewMod1 = findViewById(R.id.cardView_basic_competencies);
        cardViewMod2 = findViewById(R.id.cardView_common_competencies);
        cardViewMod3 = findViewById(R.id.cardView_core_competencies);

        Button btnEvaluation = findViewById(R.id.button5);
        myEmailLesson = findViewById(R.id.email_lesson);
        Quizzes_menu.isFromQuizMenu = false;
        isFromLessonsMenu=true;

        SharedPreferences shp = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        dash_email = shp.getString("email", "");
        Intent i = getIntent();
        Bundle b = i.getExtras();
        int uidDb = b.getInt("user_idFromServer");
        int uid = b.getInt("user_idFromDashboard");
        myEmailLesson.setText(dash_email);
        uidDbLess.setText(String.valueOf(uidDb));
        userIdLess.setText(String.valueOf(uid));

        SharedPreferences sharedPreferences = getSharedPreferences(SP_LESSONID, MODE_PRIVATE);
        lessonIdServer = sharedPreferences.getString("lessonId", "");
        lessonTitleServer = sharedPreferences.getString("lessonTitle", "");
        moduleIdServer = sharedPreferences.getString("moduleId", "");
        moduleNameServer = sharedPreferences.getString("moduleName", "");

        cardViewMod1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tChapter.setText(_1);
                String chapTest = tChapter.getText().toString();
                sp = getSharedPreferences("SharedPrefChapter", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("chapter", chapTest);
                editor.apply();
                startActivity(new Intent(Lessons_Menu.this,Lessons_Basic_Content.class));

                compe = MODULE_ID_1;
                mod = _1;
                goToModuleList();
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
                startActivity(new Intent(Lessons_Menu.this,Lessons_Basic_Content.class));

                compe = MODULE_ID_2;
                mod = _2;
                goToModuleList();
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
                startActivity(new Intent(Lessons_Menu.this,Lessons_Basic_Content.class));

                compe = MODULE_ID_3;
                mod = _3;
                goToModuleList();
            }
        });

        lockAndUnlockModules();

        btnEvaluation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Lessons_Menu.this,Evaluation_Menu.class));
            }
        });
    }

    public void lockAndUnlockModules() {

        int currUser = Integer.parseInt(userIdLess.getText().toString());
        int dbUser = Integer.parseInt(uidDbLess.getText().toString());
        boolean sameUser = String.valueOf(dbUser).equals(String.valueOf(currUser));
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        latestUnlocked = bundle.getInt("myLatestIsUnlocked");
        latestCompleted = bundle.getInt("myLatestIsCompleted");
        String myLatestChapter = bundle.getString("myLatestChapter");

        //lock all completed quizzes read from database, all converted to Constant - for updating to ids
        switch (myLatestChapter) {
            case MODULE_ID_1:
                if (sameUser && myLatestChapter.equals(MODULE_ID_1)
                       && latestCompleted == 1) {
                    cardViewMod2.setClickable(true);
                    passedLockMod1();
                    lockedMod3();
                } else {
                    lockedMod2();
                    lockedMod3();
                }
                break;
            case MODULE_ID_2:
                if (sameUser && myLatestChapter.equals(MODULE_ID_2)
                      && latestCompleted == 1) {
                    cardViewMod3.setClickable(true);
                    passedLockMod1();
                    passedLockMod2();
                } else {
                    passedLockMod1();
                    lockedMod3();
                }
                break;
            case MODULE_ID_3:
                if (sameUser && myLatestChapter.equals(MODULE_ID_3)
                        && latestCompleted == 1) {
                    passedLockMod1();
                    passedLockMod2();
                    passedLockMod3();
                } else{
                    passedLockMod1();
                    passedLockMod2();
                }
                break;
        }

        if (sameUser && myLatestChapter.equals(MODULE_ID_1) &&
                latestCompleted == 0) {
            cardViewMod1.setClickable(true);
        }

        if (myLatestChapter.equals("")) {
            initialLock();
        }

        if (!sameUser && !myLatestChapter.equals("")) {
            initialLock();
        }
    }

    public void initialLock(){
        cardViewMod1.setClickable(true);
        lockedMod2();
        lockedMod3();
    }

    public void passedLockMod1(){
        cardViewMod1.setClickable(true);
    }

    public void passedLockMod2(){
        cardViewMod2.setClickable(true);
    }

    public void passedLockMod3(){
        cardViewMod3.setClickable(true);
    }

    public void lockedMod2(){
        cardViewMod2.setClickable(false);
        cardViewMod2.setBackground(ContextCompat.getDrawable(this, R.drawable.mod_lock));
    }

    public void lockedMod3(){
        cardViewMod3.setClickable(false);
        cardViewMod3.setBackground(ContextCompat.getDrawable(this, R.drawable.mod_lock));
    }


    public void goToModuleList(){
        Intent intent = new Intent(Lessons_Menu.this, Basic_Content.class);
        Bundle extras = new Bundle();
        extras.putString("module", compe);
        extras.putString("moduleName", mod);
        intent.putExtras(extras);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Dashboard.activeLesson.setText(currentLesson);
        Dashboard.activeModule.setText(moduleName);
        Intent intent = new Intent(Lessons_Menu.this, Dashboard.class);
        Bundle extras = new Bundle();
        extras.putString("email", myEmailLesson.getText().toString());
        intent.putExtras(extras);
        startActivity(intent);
    }
}