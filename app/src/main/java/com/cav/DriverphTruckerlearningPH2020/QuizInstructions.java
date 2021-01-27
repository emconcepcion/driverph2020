package com.cav.DriverphTruckerlearningPH2020;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.cav.DriverphTruckerlearningPH2020.Constant._1;
import static com.cav.DriverphTruckerlearningPH2020.Constant._2;
import static com.cav.DriverphTruckerlearningPH2020.Constant._3;
import static com.cav.DriverphTruckerlearningPH2020.Dashboard.Uid_PREFS;


public class QuizInstructions extends AppCompatActivity {

    public static String chapter;
    Button buttonStartQuiz, back_btn;
    private final String QUESTIONS_URL = "https://phportal.net/driverph/questions.php";

    private TextView textViewChapter;
    public static TextView textViewModuleTitle, tvModuleName;
    public TextView myEmailQuizInst, userIdQInst;
    SharedPreferences sp;
    String qChapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_instructions);

        Dashboard.getmInstanceActivity().loadDataAllAttemptsAndLevels();

        buttonStartQuiz = findViewById(R.id.btn_start_quiz);
        back_btn = findViewById(R.id.btn_back_to_take_quiz);
        textViewChapter = findViewById(R.id.textview_chapter_title);
        myEmailQuizInst = findViewById(R.id.myEmailQuizInst);
        userIdQInst = findViewById(R.id.myUserIdQuizInst);
        textViewModuleTitle = findViewById(R.id.textview_moduleTest);
        tvModuleName = findViewById(R.id.tv_moduleName);

        sp = getApplicationContext().getSharedPreferences("mySavedAttempt", Context.MODE_PRIVATE);
        String myEmail = sp.getString("email", "");
        myEmailQuizInst.setText(myEmail);

        SharedPreferences sharedPreferences = getSharedPreferences(Uid_PREFS, MODE_PRIVATE);
        int uid = sharedPreferences.getInt("user_id", 0);
        userIdQInst.setText(String.valueOf(uid));

        //quiz will be taken if user comes from the lessons menu
        if (Lessons_Menu.isFromLessonsMenu) {
            SharedPreferences sp1 = getApplicationContext()
                    .getSharedPreferences("SharedPrefChapter", Context.MODE_PRIVATE);
            String lessonChap = sp1.getString("chapter", "");
            textViewChapter.setText(lessonChap);
        }else if (Quizzes_menu.isFromQuizMenu){
            SharedPreferences sp2 = getApplicationContext()
                    .getSharedPreferences("ChapFromQuizzes", Context.MODE_PRIVATE);
            qChapter = sp2.getString("Qchapter", "");
             textViewChapter.setText(qChapter);
             switch (qChapter){
                 case "1":
                     tvModuleName.setText(_1);
                     break;
                 case "2":
                     tvModuleName.setText(_2);
                     break;
                 case "3":
                     tvModuleName.setText(_3);
                     break;
             }
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
                backToLessons();
            }
        });
        getJSON(QUESTIONS_URL);
    }

    private void backToLessons() {
        Intent intent = new Intent(QuizInstructions.this, Basic_Content.class);
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
            Intent i = new Intent(com.cav.DriverphTruckerlearningPH2020.QuizInstructions.this, QuizActivity.class);
            Bundle b = new Bundle();
            b.putString("chapter", chapTitle);
            i.putExtras(b);
            startActivity(i);
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(QuizInstructions.this).create();
            alertDialog.setTitle("No internet");
            alertDialog.setMessage("Please connect to the internet before clicking \"Begin\"");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
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

    public void getJSON(final String urlWebService) {
        class GetJSON extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), "Loading...", Toast.LENGTH_SHORT).show();
                try {
                    parserQuestionsFromString(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                chapter = textViewChapter.getText().toString();
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }

            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    void parserQuestionsFromString(String refjson) {
        String stringjson = refjson;
        Database db = new Database(this);
        db.Open();
        try {
            JSONObject jObj = new JSONObject(stringjson);

            JSONArray menuitemArray = jObj.getJSONArray("data");

            for (int i = 0; i < menuitemArray.length(); i++) {

                Log.d("questionText " + i,
                        menuitemArray.getJSONObject(i).getString("questionText")
                                .toString());
                Log.d("choiceA: " + i, menuitemArray.getJSONObject(i)
                        .getString("choiceA"));
                Log.d("choiceB: " + i, menuitemArray.getJSONObject(i)
                        .getString("choiceB"));
                Log.d("choiceC: " + i, menuitemArray.getJSONObject(i)
                        .getString("choiceC"));
                Log.d("choiceD: " + i, menuitemArray.getJSONObject(i)
                        .getString("choiceD"));
                Log.d("correctAnswer: " + i, menuitemArray.getJSONObject(i)
                        .getString("correctAnswer"));
                Log.d("module" + i, menuitemArray.getJSONObject(i)
                        .getString("module"));
                Log.d("imageUrl" + i, menuitemArray.getJSONObject(i)
                        .getString("imageUrl"));
                Log.d("questionNumMob" + i, menuitemArray.getJSONObject(i)
                        .getString("questionNumMob"));

                String question = menuitemArray.getJSONObject(i).getString("questionText");
                String option1 = menuitemArray.getJSONObject(i).getString("choiceA");
                String option2 = menuitemArray.getJSONObject(i).getString("choiceB");
                String option3 = menuitemArray.getJSONObject(i).getString("choiceC");
                String option4 = menuitemArray.getJSONObject(i).getString("choiceD");
                String answer_nr = menuitemArray.getJSONObject(i).getString("correctAnswer");
                String chapter = menuitemArray.getJSONObject(i).getString("module");
                String moduleName = menuitemArray.getJSONObject(i).getString("moduleName");
                String image = menuitemArray.getJSONObject(i).getString("imageUrl");
                String quesNum = menuitemArray.getJSONObject(i).getString("questionNumMob");
                Question q1 = new Question(question, option1, option2, option3, option4,
                        Integer.parseInt(answer_nr), chapter, moduleName, image, Integer.parseInt(quesNum));
                db.addQuestion(q1);
            }

        } catch (Exception je) {

            Log.d("json error...", je + "");
        }
        Log.d("Inside aysnc task", "inside asynctask...");
     //   db.close();
    }

}
