package com.cav.DriverphTruckerlearningPH2020;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class SimulationPopup2 extends AppCompatActivity {

    TextView title,message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation_popup2);

        title = findViewById(R.id.txtView_simulation_popup2);
        message = findViewById(R.id.txtMessage2);

        setScreen();
        setBtn();

        Bundle bundleTxt = this.getIntent().getExtras();
        message.setText(bundleTxt.getString("key_explanation"));
        title.setText(bundleTxt.getString("key_title"));
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

    }

    private void setBtn(){
        Button closeBtn = findViewById(R.id.btn_closePop2);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}// SimulationPopup2 Latest Jan. 17, 9:05 AM