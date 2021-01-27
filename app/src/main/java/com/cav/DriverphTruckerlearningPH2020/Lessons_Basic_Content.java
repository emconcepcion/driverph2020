package com.cav.DriverphTruckerlearningPH2020;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.cav.DriverphTruckerlearningPH2020.Constant.SP_LESSONID;
import static com.cav.DriverphTruckerlearningPH2020.Constant._1;
import static com.cav.DriverphTruckerlearningPH2020.Constant._2;
import static com.cav.DriverphTruckerlearningPH2020.Constant._3;
import static com.cav.DriverphTruckerlearningPH2020.Dashboard.dashboard_email;
import static com.cav.DriverphTruckerlearningPH2020.Dashboard.nameVR;
import static com.cav.DriverphTruckerlearningPH2020.Constant.SERVER_USER_PROGRESS;
import static com.cav.DriverphTruckerlearningPH2020.Constant.retrieveUrl;

public class Lessons_Basic_Content extends AppCompatActivity {
    WebView content;
    Button download, textspeech;
    TextToSpeech textToSpeech;
    public static TextView email_lesson;
    Button btnNext;
    private SeekBar mSeekBarPitch, mSeekBarSpeed;
    private TextView pitch, speed;
    public static String module, course, status, dateStarted, dateFinished,
            htmlData, lessonpdf, lessonId, currentLessonId, moduleName;
    public static boolean isFromLessonBasicContent;
    String firstspeech, secondspeech, thirdspeech, fourthspeech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons__basic__content);
        isFromLessonBasicContent = true;
        Lesson.isFromMyProgressNav = false;

        ActivityCompat.requestPermissions(Lessons_Basic_Content.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        module = getIntent().getStringExtra("module");
        course = getIntent().getStringExtra("course");
        lessonId = getIntent().getStringExtra("lessonId");
        status = String.valueOf(getIntent().getIntExtra("status", 0));
        dateStarted = getIntent().getStringExtra("dateStarted");
        dateFinished = getIntent().getStringExtra("dateFinished");
        currentLessonId = getIntent().getStringExtra("currLessonId");

        SharedPreferences sharedPreferences = getSharedPreferences(SP_LESSONID, MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("lessonTitle", course);
        switch(module){
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
        myEdit.putString("moduleName", moduleName);
        myEdit.putString("moduleId", module);
        myEdit.putString("lessonId", currentLessonId);
        myEdit.apply();

        SharedPreferences spr = getSharedPreferences("SharedPrefChapter", MODE_PRIVATE);
        SharedPreferences.Editor editor = spr.edit();
        editor.putString("chapter", moduleName);
        editor.apply();

        insertUserProgressModules();
        retrievedatas();

        email_lesson = findViewById(R.id.emailBContent);
        email_lesson.setText(dashboard_email);

        content = (WebView) findViewById(R.id.content);
        content.getSettings().setJavaScriptEnabled(true);


        btnNext = findViewById(R.id.btn_to_voiceR);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToSpeech.stop();
                SharedPreferences sp = getSharedPreferences("mySavedAttempt", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sp.edit();
                editor1.putString("email", dashboard_email);
                editor1.putString("username", nameVR);
                editor1.apply();
                startActivity(new Intent(Lessons_Basic_Content.this, VoiceResponse.class));

            }
        });

        download = findViewById(R.id.download);
        content = findViewById(R.id.content);
        textspeech = findViewById(R.id.textspeech);
        mSeekBarPitch = findViewById(R.id.seek_bar_pitch);
        mSeekBarSpeed = findViewById(R.id.seek_bar_speed);
        pitch = findViewById(R.id.pitch);
        speed = findViewById(R.id.speed);

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {
                        textspeech.setEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                PrintTheWebPage(content);
            }
        });

        textspeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txttospeech();
            }
        });

        mSeekBarSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                playtxtspeech();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSeekBarPitch.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                playtxtspeech();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        textToSpeech.stop();
//        Lessons_Menu.isFromLessonsMenu=false;
        Intent intent = new Intent(Lessons_Basic_Content.this, Basic_Content.class);
        Bundle extras = new Bundle();
        extras.putString("email", email_lesson.getText().toString());
        extras.putString("module", module);
        intent.putExtras(extras);
        startActivity(intent);
    }

    public void retrievedatas() {

        class show_prod extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("course", course);

                //returing the response
                return requestHandler.sendPostRequest(retrieveUrl, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    //Converting response to JSON Object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")){
                        lessonId = obj.getString("id");
                        htmlData = obj.getString("htmldata");
                        content.loadData(htmlData, "text/html", "UTF-8");
                        lessonpdf = htmlData.replaceAll("<br>", "\n\n");
                        lessonpdf = lessonpdf.replaceAll("\\<.*?\\>", " ");
                        int f = lessonpdf.length() / 4;
                        int i = f * 2;
                        int g = f * 3;
                        firstspeech = lessonpdf.substring(0, f);
                        f++;
                        secondspeech = lessonpdf.substring(f, i);
                        i++;
                        thirdspeech = lessonpdf.substring(i, g);
                        g++;
                        fourthspeech = lessonpdf.substring(g);
                    }
                } catch (Exception e) {
//                    Toast.makeText(Lessons_Basic_Content.this, "Exception: " + e, Toast.LENGTH_SHORT).show();
                    if (e instanceof TimeoutError) {
                        Toast.makeText(Lessons_Basic_Content.this, "Timeout error. Please try again later.", Toast.LENGTH_SHORT).show();
                    } else if (e instanceof NoConnectionError) {
                        checkNetworkConnection();
                        Toast.makeText(Lessons_Basic_Content.this, R.string.conn_net, Toast.LENGTH_SHORT).show();
                    } else if (e instanceof AuthFailureError) {
                        Toast.makeText(Lessons_Basic_Content.this, "Auth error. Please try again later.", Toast.LENGTH_SHORT).show();
                    } else if (e instanceof ServerError) {
                        Toast.makeText(Lessons_Basic_Content.this, "Server error. Please try again later.", Toast.LENGTH_SHORT).show();
                    } else if (e instanceof NetworkError) {
                        Toast.makeText(Lessons_Basic_Content.this, "Network error", Toast.LENGTH_SHORT).show();
                    } else if (e instanceof ParseError) {
                        Toast.makeText(Lessons_Basic_Content.this, "Parse error. Please try again later.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        show_prod show = new show_prod();
        show.execute();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    PrintJob printJob;

    //a boolean to check the status of printing
    boolean printBtnPressed=false;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void PrintTheWebPage(WebView webView) {

        //set printBtnPressed true
        printBtnPressed=true;

        // Creating  PrintManager instance
        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);

        //setting the name of job
        String jobName = course;

        // Creating  PrintDocumentAdapter instance
        PrintDocumentAdapter printAdapter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            printAdapter = webView.createPrintDocumentAdapter(jobName);
        }

        // Create a print job with name and adapter instance
        assert printManager != null;
        printJob = printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onResume() {
        super.onResume();
        if(printJob!=null &&printBtnPressed) {
            if (printJob.isCompleted()) {
                //Showing Toast Message
                retrievedatas();
                //Toast.makeText(this, "Completed", Toast.LENGTH_SHORT).show();
            } else if (printJob.isStarted()) {
                //Showing Toast Message
                retrievedatas();
                //Toast.makeText(this, "isStarted", Toast.LENGTH_SHORT).show();

            } else if (printJob.isBlocked()) {
                //Showing Toast Message
                //retrievedatas();
                Toast.makeText(this, "Download was blocked", Toast.LENGTH_SHORT).show();
            } else if (printJob.isCancelled()) {
                //Showing Toast Message
                retrievedatas();
                Toast.makeText(this, "PDF Download was canceled", Toast.LENGTH_SHORT).show();

            } else if (printJob.isFailed()) {
                //Showing Toast Message
                retrievedatas();
                //Toast.makeText(this, "isFailed", Toast.LENGTH_SHORT).show();

            } else if (printJob.isQueued()) {
                //Showing Toast Message
                retrievedatas();
                //Toast.makeText(this, "isQueued", Toast.LENGTH_SHORT).show();
            }
            //set printBtnPressed false
            printBtnPressed=false;
        }
    }

    public void txttospeech() {
        String txttts = textspeech.getText().toString();

        if(txttts.equals("Play")) {
            textspeech.setText("Stop");
            textspeech.setTextColor(Color.TRANSPARENT);
//            download.setVisibility(View.INVISIBLE);
            pitch.setVisibility(View.VISIBLE);
            speed.setVisibility(View.VISIBLE);
            mSeekBarPitch.setVisibility(View.VISIBLE);
            mSeekBarSpeed.setVisibility(View.VISIBLE);
            playtxtspeech();
        }else {
            textToSpeech.stop();
            textspeech.setText("Play");
            textspeech.setTextColor(Color.TRANSPARENT);
            download.setVisibility(View.VISIBLE);
            pitch.setVisibility(View.GONE);
            speed.setVisibility(View.GONE);
            mSeekBarPitch.setVisibility(View.GONE);
            mSeekBarSpeed.setVisibility(View.GONE);
        }
    }

    public void playtxtspeech() {
        float pitch = (float) mSeekBarPitch.getProgress() / 50;
        if (pitch < 0.1) pitch = 0.1f;
        float speed = (float) mSeekBarSpeed.getProgress() / 50;
        if (speed < 0.1) speed = 0.1f;
        textToSpeech.setPitch(pitch);
        textToSpeech.setSpeechRate(speed);
        textToSpeech.speak(firstspeech, TextToSpeech.QUEUE_FLUSH, null);
        textToSpeech.speak(secondspeech, TextToSpeech.QUEUE_ADD, null);
        textToSpeech.speak(thirdspeech, TextToSpeech.QUEUE_ADD, null);
        textToSpeech.speak(fourthspeech, TextToSpeech.QUEUE_ADD, null);
    }

    //load all data for user's progress / latest module
    public void insertUserProgressModules() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();
        Database db = new Database(this);
        db.Open();
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.DEPRECATED_GET_OR_POST,
                SERVER_USER_PROGRESS,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressDialog.dismiss();
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
//                        Toast.makeText(Lessons_Basic_Content.this, volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                        if (volleyError instanceof TimeoutError) {
                            Toast.makeText(Lessons_Basic_Content.this, "Timeout error. Please try again later.", Toast.LENGTH_SHORT).show();
                        } else if (volleyError instanceof NoConnectionError) {
                            checkNetworkConnection();
                            Toast.makeText(Lessons_Basic_Content.this, R.string.conn_net, Toast.LENGTH_SHORT).show();
                        } else if (volleyError instanceof AuthFailureError) {
                            Toast.makeText(Lessons_Basic_Content.this, "Auth error. Please try again later.", Toast.LENGTH_SHORT).show();
                        } else if (volleyError instanceof ServerError) {
                            Toast.makeText(Lessons_Basic_Content.this, "Server error. Please try again later.", Toast.LENGTH_SHORT).show();
                        } else if (volleyError instanceof NetworkError) {
                            Toast.makeText(Lessons_Basic_Content.this, "Network error", Toast.LENGTH_SHORT).show();
                        } else if (volleyError instanceof ParseError) {
                            Toast.makeText(Lessons_Basic_Content.this, "Parse error. Please try again later.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", dashboard_email);
                params.put("module", module);
                params.put("lessonId", lessonId);
                params.put("status", status);
                params.put("dateStarted", dateStarted);
                params.put("dateFinished", dateFinished);
                Log.d("email", dashboard_email + "");
                Log.d("udpating module: ", "update last accessed module successful...");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}