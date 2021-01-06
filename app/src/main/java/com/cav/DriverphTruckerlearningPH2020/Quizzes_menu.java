package com.cav.DriverphTruckerlearningPH2020;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.cav.DriverphTruckerlearningPH2020.BackgroundTask.SHARED_PREFS;
import static com.cav.DriverphTruckerlearningPH2020.Constant.MODULE_ID_1;
import static com.cav.DriverphTruckerlearningPH2020.Constant.MODULE_ID_2;
import static com.cav.DriverphTruckerlearningPH2020.Constant.MODULE_ID_3;

public class Quizzes_menu extends AppCompatActivity {
    private static final String Server_All_Attempts_URL = "https://phportal.net/driverph/get_all_attempts.php";

    public static CardView cardViewMod1;
    public static CardView cardViewMod2;
    public static CardView cardViewMod3;
    TextView tChapter, mylatestModLocked, mylatestModCompleted;
    Button btn_leaderBoard;
    Button btn_list_completed_quizzes;
    public static boolean isFromQuizMenu;
    public static String myLatestUserId;
    public static String myLatestAttempt;
    public static String myLatestChapter;
    public static String myLatestIsUnlocked;
    public static String myLatestIsCompleted;
    public static String dash_email;
    @SuppressLint("StaticFieldLeak")
    public static TextView myEmailQMenu, userIdQMenu, uidDb_txt, updatedChapter;
    public static int latestUnlocked, latestCompleted;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizzes_menu);

        tChapter = findViewById(R.id.tChap);

        cardViewMod1 = findViewById(R.id.cardView_basic_competencies);
        cardViewMod2 = findViewById(R.id.cardView_common_competencies);
        cardViewMod3 = findViewById(R.id.cardView_core_competencies);
        cardViewMod1.setClickable(true);

        loadDataAllAttemptsAndLevels();

        btn_leaderBoard = findViewById(R.id.button5);
        btn_list_completed_quizzes = findViewById(R.id.button6);
        myEmailQMenu = findViewById(R.id.email_qMenu);
        userIdQMenu = findViewById(R.id.txt_userIdQuiz);
        uidDb_txt = findViewById(R.id.txt_uidDbQuizMenu);
        updatedChapter = findViewById(R.id.myprogresslesson);

        mylatestModLocked = findViewById(R.id.latestMod1Locked);
        mylatestModCompleted = findViewById(R.id.latestMod1Completed);
        mylatestModLocked.setText(com.cav.DriverphTruckerlearningPH2020.Dashboard.myLatestIsUnlocked);
        mylatestModCompleted.setText(com.cav.DriverphTruckerlearningPH2020.Dashboard.myLatestIsCompleted);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        dash_email = sharedPreferences.getString("email", "");
        Intent i = getIntent();
        Bundle b = i.getExtras();
//        dashboard_email = b.getString("email");
        int uidDb = b.getInt("user_idFromServer");
        int uid = b.getInt("user_idFromDashboard");
        myEmailQMenu.setText(dash_email);
        uidDb_txt.setText(String.valueOf(uidDb));
        userIdQMenu.setText(String.valueOf(uid));

        Lessons_Menu.isFromLessonsMenu = false;
        isFromQuizMenu = true;

        cardViewMod1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFromQuizMenu = true;
                tChapter.setText(MODULE_ID_1);
                com.cav.DriverphTruckerlearningPH2020.Dashboard.recentModule.setText(MODULE_ID_1);
                String chapTest1 = tChapter.getText().toString();
                SharedPreferences sp1 = getSharedPreferences("ChapFromQuizzes", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sp1.edit();
                editor1.putString("Qchapter", chapTest1);
                editor1.apply();
                startActivity(new Intent(com.cav.DriverphTruckerlearningPH2020.Quizzes_menu.this, com.cav.DriverphTruckerlearningPH2020.QuizInstructions.class));
            }
        });

        cardViewMod2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFromQuizMenu = true;
                tChapter.setText(MODULE_ID_2);
                com.cav.DriverphTruckerlearningPH2020.Dashboard.recentModule.setText(MODULE_ID_2);
                String chapTest2 = tChapter.getText().toString();
                SharedPreferences sp2 = getSharedPreferences("ChapFromQuizzes", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = sp2.edit();
                editor2.putString("Qchapter", chapTest2);
                editor2.apply();
                startActivity(new Intent(com.cav.DriverphTruckerlearningPH2020.Quizzes_menu.this, com.cav.DriverphTruckerlearningPH2020.QuizInstructions.class));
            }
        });

        cardViewMod3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFromQuizMenu = true;
                tChapter.setText(MODULE_ID_3);
                com.cav.DriverphTruckerlearningPH2020.Dashboard.recentModule.setText(MODULE_ID_3);
                String chapTest3 = tChapter.getText().toString();
                SharedPreferences sp3 = getSharedPreferences("ChapFromQuizzes", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor3 = sp3.edit();
                editor3.putString("Qchapter", chapTest3);
                editor3.apply();
                startActivity(new Intent(com.cav.DriverphTruckerlearningPH2020.Quizzes_menu.this, com.cav.DriverphTruckerlearningPH2020.QuizInstructions.class));
            }
        });

        lockAndUnlockModules();

        btn_leaderBoard.setOnClickListener(new View.OnClickListener() {
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
                startActivity(new Intent(com.cav.DriverphTruckerlearningPH2020.Quizzes_menu.this, com.cav.DriverphTruckerlearningPH2020.CompletedQuizzes.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(com.cav.DriverphTruckerlearningPH2020.Quizzes_menu.this, com.cav.DriverphTruckerlearningPH2020.Dashboard.class);
        Bundle extras = new Bundle();
        extras.putString("email", myEmailQMenu.getText().toString());
        intent.putExtras(extras);
        startActivity(intent);
        finish();
    }

    private void lockAndUnlockModules() {

        // locked 0, unlocked 1
        int currUser = Integer.parseInt(userIdQMenu.getText().toString());
        String currChap = tChapter.getText().toString();
        int dbUser = Integer.parseInt(uidDb_txt.getText().toString());
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
                        && latestUnlocked == 0 && latestCompleted == 1) {
                    cardViewMod2.setClickable(true);
                    cardViewMod1.setClickable(false);
                    cardViewMod1.setBackground(ContextCompat.getDrawable(this, R.drawable.passed_locked));
                    cardViewMod3.setClickable(false);
                    cardViewMod3.setBackground(ContextCompat.getDrawable(this, R.drawable.mod_lock));
                } else {
                    cardViewMod2.setClickable(false);
                    cardViewMod2.setBackground(ContextCompat.getDrawable(this, R.drawable.mod_lock));
                    cardViewMod3.setClickable(false);
                    cardViewMod3.setBackground(ContextCompat.getDrawable(this, R.drawable.mod_lock));
                }
                break;
            case MODULE_ID_2:
                if (sameUser && myLatestChapter.equals(MODULE_ID_2)
                        && latestUnlocked == 0 && latestCompleted == 1) {
                    cardViewMod3.setClickable(true);
                    cardViewMod1.setClickable(false);
                    cardViewMod1.setBackground(ContextCompat.getDrawable(this, R.drawable.passed_locked));
                    cardViewMod2.setClickable(false);
                    cardViewMod2.setBackground(ContextCompat.getDrawable(this, R.drawable.passed_locked));
                } else {
                    cardViewMod1.setClickable(false);
                    cardViewMod1.setBackground(ContextCompat.getDrawable(this, R.drawable.passed_locked));
                    cardViewMod3.setClickable(false);
                    cardViewMod3.setBackground(ContextCompat.getDrawable(this, R.drawable.mod_lock));
                }
                break;
            case MODULE_ID_3:
                if (sameUser && myLatestChapter.equals(MODULE_ID_3)
                        && latestUnlocked == 0 && latestCompleted == 1) {
                    cardViewMod3.setClickable(false);
                    cardViewMod3.setBackground(ContextCompat.getDrawable(this, R.drawable.passed_locked));
                    cardViewMod1.setClickable(false);
                    cardViewMod1.setBackground(ContextCompat.getDrawable(this, R.drawable.passed_locked));
                    cardViewMod2.setClickable(false);
                    cardViewMod2.setBackground(ContextCompat.getDrawable(this, R.drawable.passed_locked));
                }
                break;
        }

        if (myLatestChapter.equals("")) {
            cardViewMod1.setClickable(true);
            cardViewMod2.setClickable(false);
            cardViewMod2.setBackground(ContextCompat.getDrawable(this, R.drawable.mod_lock));
            cardViewMod3.setClickable(false);
            cardViewMod3.setBackground(ContextCompat.getDrawable(this, R.drawable.mod_lock));
        }

        if (sameUser && latestUnlocked == 1 && latestCompleted == 0) {
            cardViewMod1.setClickable(true);
        }

        if (sameUser && myLatestChapter.equals("")) {
            cardViewMod1.setClickable(true);
            cardViewMod2.setClickable(false);
            cardViewMod2.setBackground(ContextCompat.getDrawable(this, R.drawable.mod_lock));
            cardViewMod3.setClickable(false);
            cardViewMod3.setBackground(ContextCompat.getDrawable(this, R.drawable.mod_lock));
        }
    }

    //load all data attempts and unlocked modules from web
    public void loadDataAllAttemptsAndLevels() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();
        com.cav.DriverphTruckerlearningPH2020.Database db = new com.cav.DriverphTruckerlearningPH2020.Database(this);
        db.Open();
        StringRequest stringRequest = new StringRequest(Request.Method.DEPRECATED_GET_OR_POST,
                Server_All_Attempts_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressDialog.dismiss();
                        Toast.makeText(com.cav.DriverphTruckerlearningPH2020.Quizzes_menu.this, "Loading all attempts", Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jObj = new JSONObject(s);

                            JSONArray menuitemArray = jObj.getJSONArray("data");

                            for (int i = 0; i < menuitemArray.length(); i++) {

                                Log.d("user_id " + i,
                                        menuitemArray.getJSONObject(i).getString("user_id")
                                                .toString());
                                Log.d("email: " + i, menuitemArray.getJSONObject(i)
                                        .getString("email"));
                                Log.d("score: " + i, menuitemArray.getJSONObject(i)
                                        .getString("score"));
                                Log.d("num_of_items: " + i, menuitemArray.getJSONObject(i)
                                        .getString("num_of_items"));
                                Log.d("chapter: " + i, menuitemArray.getJSONObject(i)
                                        .getString("chapter"));
                                Log.d("num_of_attempt: " + i, menuitemArray.getJSONObject(i)
                                        .getString("num_of_attempt"));
                                Log.d("duration: " + i, menuitemArray.getJSONObject(i)
                                        .getString("duration"));
                                Log.d("date_taken: " + i, menuitemArray.getJSONObject(i)
                                        .getString("date_taken"));
                                Log.d("isUnlocked: " + i, menuitemArray.getJSONObject(i)
                                        .getString("isUnlocked"));
                                Log.d("isCompleted: " + i, menuitemArray.getJSONObject(i)
                                        .getString("isCompleted"));

                                myLatestUserId = menuitemArray.getJSONObject(i).getString("user_id");
                                String email = menuitemArray.getJSONObject(i).getString("email");
                                String score = menuitemArray.getJSONObject(i).getString("score");
                                String num_items = menuitemArray.getJSONObject(i).getString("num_of_items");
                                myLatestChapter = menuitemArray.getJSONObject(i).getString("chapter");
                                myLatestAttempt = menuitemArray.getJSONObject(i).getString("num_of_attempt");
                                String duration = menuitemArray.getJSONObject(i).getString("duration");
                                String date_taken = menuitemArray.getJSONObject(i).getString("date_taken");
                                myLatestIsUnlocked = menuitemArray.getJSONObject(i).getString("isUnlocked");
                                myLatestIsCompleted = menuitemArray.getJSONObject(i).getString("isCompleted");
                                com.cav.DriverphTruckerlearningPH2020.MyScoresServer mS1 = new com.cav.DriverphTruckerlearningPH2020.MyScoresServer(Integer.parseInt(myLatestUserId), email, Integer.parseInt(score),
                                        Integer.parseInt(num_items), myLatestChapter, Integer.parseInt(myLatestAttempt), duration,
                                        date_taken, Integer.parseInt(myLatestIsUnlocked), Integer.parseInt(myLatestIsCompleted));
                                db.addScoresServer(mS1);
                            }

                            Toast.makeText(com.cav.DriverphTruckerlearningPH2020.Quizzes_menu.this, "Fetched from attempts: " + myLatestUserId, Toast.LENGTH_SHORT).show();
                            Toast.makeText(com.cav.DriverphTruckerlearningPH2020.Quizzes_menu.this, "Latest Chapter: " + myLatestChapter, Toast.LENGTH_SHORT).show();
                            com.cav.DriverphTruckerlearningPH2020.Dashboard.recentModule.setText(myLatestChapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast.makeText(com.cav.DriverphTruckerlearningPH2020.Quizzes_menu.this, volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String fetchedChap = com.cav.DriverphTruckerlearningPH2020.Dashboard.recentModule.getText().toString();
                params.put("email", dash_email);
                params.put("chapter", fetchedChap);
                Log.d("email", dash_email + "");
                Log.d("yes", "successful...");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}