package com.cav.DriverphTruckerlearningPH2020;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;

import static com.cav.DriverphTruckerlearningPH2020.Dashboard.Uid_PREFS;
import static com.cav.DriverphTruckerlearningPH2020.Dashboard.dashboard_email;
import static com.cav.DriverphTruckerlearningPH2020.Lessons_Basic_Content.isFromLessonBasicContent;

public class VoiceResponse extends AppCompatActivity {

    MediaRecorder mediaRecorder = null;
    MediaPlayer mediaPlayer = null;
    Button start_rec,  play_rec, btn_next;
    TextView textView, userName, chapVr, whatWasLearned;

    public static String fileName = "recorded.3gp";
    String file = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_response);

        requestPermission();

        SharedPreferences sharedPreferences = getSharedPreferences("mySavedAttempt", MODE_PRIVATE);
        String uname = sharedPreferences.getString("username", "");
        SharedPreferences spr = getSharedPreferences("SharedPrefChapter", MODE_PRIVATE);
        String chapter = spr.getString("chapter", "");

        start_rec = findViewById(R.id.btn_start_record);
        textView = findViewById(R.id.textView_status);
        userName = findViewById(R.id.user_name);
        play_rec = findViewById(R.id.btn_playback);
        btn_next = findViewById(R.id.btn_next);
        chapVr = findViewById(R.id.chapterVR);
        whatWasLearned = findViewById(R.id.whatHaveYouLearned);

        userName.setText("\nHi, " + uname);
        chapVr.setText(chapter);
        textView.setVisibility(View.GONE);

        if (Lesson.isFromMyProgressNav) {
            btn_next.setVisibility(View.GONE);
            start_rec.setVisibility(View.GONE);
            textView.setText(R.string.play_recording);
            whatWasLearned.setText("\nListen to what you have recently recorded as your recitation for this session.\n\n");
        }else if (isFromLessonBasicContent){
            btn_next.setVisibility(View.VISIBLE);
            textView.setText(R.string.playback_ended);
        }

        btn_next.setVisibility(View.INVISIBLE);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer!=null && mediaPlayer.isPlaying()){
                    mediaPlayer.release();
                }
                SharedPreferences sharedPreferences = getSharedPreferences(Uid_PREFS, MODE_PRIVATE);
                int user_id = sharedPreferences.getInt("user_id", 0);
                Intent intent = new Intent(VoiceResponse.this, Simulation.class);
                Bundle bundle = new Bundle();
                bundle.putInt("user_idFromServer", user_id);
                bundle.putInt("user_idFromDashboard", user_id);
                bundle.putString("email", dashboard_email);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

        final boolean[] clicked = {false};
        start_rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!clicked[0]){
                    clicked[0] = true;
                    start_rec.setSelected(true);
                    onRecord(true);
                }else{
                    stop();
                    clicked[0] = false;
                    start_rec.setSelected(false);
                }
            }
        });


        play_rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(start_rec.isSelected()){
                    textView.setText(R.string.stop_rec_first_before_play);
                }else {
                    onPlay(true);
                }
            }
        });

    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat
                    .requestPermissions(VoiceResponse.this, new String[]{Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

    private void onRecord(boolean start) {
        if (start) {
            record();
            textView.setVisibility(View.VISIBLE);
            textView.setText(R.string.start_recording);
        } else {
            if (mediaRecorder != null){
                stop();
            }
        }
    }

    private void onPlay(boolean start) {
        if (start) {
            play();
            textView.setVisibility(View.VISIBLE);
            File noFile = new File(file);
            if (!noFile.exists()){
                textView.setText(R.string.noRecording);
            }else{
                textView.setText(R.string.play_recording);
            }
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    if (Lesson.isFromMyProgressNav) {
                        btn_next.setVisibility(View.GONE);
                        start_rec.setVisibility(View.GONE);
                        textView.setText("Go ahead, visit your lessons\nand tell us about your new learning.");
                        whatWasLearned.setText("\nListen to what you have recently recorded as your recitation.\n\n\n\n\n");
                    }else if (isFromLessonBasicContent) {
                        textView.setText(R.string.playback_ended);
                        btn_next.setVisibility(View.VISIBLE);
                    }
                }
            });
        } else {
            stop();
        }
    }

    private void record(){
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(file);
        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaRecorder.start();
    }

    private void stop() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
        textView.setVisibility(View.VISIBLE);
        textView.setText(R.string.rec_stop);
    }

    private void play() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(file);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteRecording() {
        try {
            File fileDelete = new File(file);
            if (fileDelete.exists()) {
                fileDelete.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        if(mediaPlayer!=null && mediaPlayer.isPlaying()){
            mediaPlayer.release();
        }
        super.onBackPressed();
    }
}
