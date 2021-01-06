package com.cav.DriverphTruckerlearningPH2020;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cav.DriverphTruckerlearningPH2020.databinding.ActivityLessonBinding;
import com.muddzdev.styleabletoast.StyleableToast;


public class Lesson extends AppCompatActivity {

    ActivityLessonBinding binding;
    String[] descriptionData = {"One", "Two", "Three", "Four", "Five", "Six"};
    int arrSize;
    public static TextView arraySize, progress_Module;
    int current_state = 0;
    Bundle data = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLessonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setTitle("Lessons");
        arraySize = findViewById(R.id.arr_size);
        arrSize = descriptionData.length;
        arraySize.setText(String.valueOf(arrSize));
        progress_Module = findViewById(R.id.progress_Module);
        progress_Module.setText(com.cav.DriverphTruckerlearningPH2020.Lesson.progress_Module.getText().toString());

        binding.spb.setLabels(descriptionData)
                .setBarColorIndicator(Color.BLACK)
                .setProgressColorIndicator(getResources().getColor(R.color.colorBlueLight))
                .setLabelColorIndicator(getResources().getColor(R.color.colorRed))
                .setCompletedPosition(0)
                .drawView();

        binding.spb.setCompletedPosition(current_state);

        binding.btnNextLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(current_state<(descriptionData.length-1)) {
                    current_state = current_state + 1;
                    binding.spb.setCompletedPosition(current_state).drawView();
                    if (data != null) {
                        current_state = data.getInt("progress", current_state);

                    } else {
                        Intent i = new Intent(com.cav.DriverphTruckerlearningPH2020.Lesson.this, com.cav.DriverphTruckerlearningPH2020.Lesson.class);
                        data = getIntent().getExtras();
                        binding.spb.setCompletedPosition(current_state);
                        i.putExtra("progress", current_state);
                        finish();
                        startActivity(getIntent());
                    }
//                data.getInt("progress", current_state);

                    StyleableToast.makeText(com.cav.DriverphTruckerlearningPH2020.Lesson.this, "Lesson number: " + (current_state+1), Toast.LENGTH_SHORT, R.style.toastStyle).show();
                }else if(binding.btnNextLesson.isPressed()){
                    startActivity(new Intent(com.cav.DriverphTruckerlearningPH2020.Lesson.this, com.cav.DriverphTruckerlearningPH2020.VoiceResponse.class));
                }

//                Log.d("current state = ", current_state + "");
            }
        });

        binding.btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(current_state>0) {
                    current_state = current_state - 1;
                    binding.spb.setCompletedPosition(current_state).drawView();

                    if (data != null) {
                        current_state = data.getInt("progress", current_state);
                    } else {
                        Intent i = new Intent(com.cav.DriverphTruckerlearningPH2020.Lesson.this, com.cav.DriverphTruckerlearningPH2020.Lesson.class);
                        data = getIntent().getExtras();
                        binding.spb.setCompletedPosition(current_state);
                        i.putExtra("progress", current_state);
                        binding.spb.setCompletedPosition(current_state);
                        finish();
                        startActivity(getIntent());
                    }
                    StyleableToast.makeText(com.cav.DriverphTruckerlearningPH2020.Lesson.this, "Lesson number: " + (current_state-1), Toast.LENGTH_SHORT, R.style.toastStyle).show();
                }
                //  Log.d("current state = ", current_state + "");
            }
        });


    }
}