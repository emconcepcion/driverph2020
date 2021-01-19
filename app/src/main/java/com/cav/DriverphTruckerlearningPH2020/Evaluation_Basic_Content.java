package com.cav.DriverphTruckerlearningPH2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Evaluation_Basic_Content extends AppCompatActivity {
    TextView  details2, over, over2, lessons_topic, details, passorfailed, recommendation;
    ConstraintLayout seeDetailsContainer;
    String module;
    List<String> al, lesson_id_key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation__basic__content);

        over = findViewById(R.id.over);
        over2 = findViewById(R.id.over2);
        lessons_topic = findViewById(R.id.textView13);
        details = findViewById(R.id.details);
        details2 = findViewById(R.id.details2);
        passorfailed = findViewById(R.id.passorfailed);
        module = getIntent().getStringExtra("module_code");
        recommendation = findViewById(R.id.textView36);
        getData();
        int res = Integer.parseInt(getIntent().getStringExtra("percent"));
        over.setText(res + "/10");
        over2.setText(getIntent().getStringExtra("attempt") + " Retake(s)");

        int attempt = Integer.parseInt(getIntent().getStringExtra("attempt"));
        if(res <= 7){
            passorfailed.setText("FAILED");
            details.setText("We're sorry, but you have failed the " + getIntent().getStringExtra("module") + " module test." +
                    "\n\nWe recommend you to please review this module to pass the test and unlock the succeeding modules.");
        }else if(res >= 8 && attempt >= 5) {
            passorfailed.setText("PASSED");
            details.setText("Well done! You have passed the \n" + getIntent().getStringExtra("module") +" module test.\n" +
                    "\nYou can now move on to the next chapter or you can review the lessons to learn more from this Chapter!" + "\n\nHowever, based on the number of retakes that you have for this module, we have detected that you had a difficult time passing the test. Therefore, we highly recommend you to continue reviewing this module even after passing the test. \n" +
                    "\nThe concepts that were discussed must be thoroughly learned and the practical application must be applied on a regular basis to improve your knowledge and roadworthiness.");
        }else if((res == 8) || (res == 9)){
            passorfailed.setText("PASSED");
            details.setText("Well done! You have passed the \n" + getIntent().getStringExtra("module") + " module test." +
                    "\n\nYou can now move on to the next chapter or you can review the lessons to learn more from this Chapter!");
        }else if(res == 10){
            passorfailed.setText("PASSED");
            recommendation.setVisibility(View.INVISIBLE);
            details.setText("Excellent job! \nYou have achieved a perfect score!\nYou can now move on to the next chapter!");
        }


        if(getIntent().getStringExtra("module").equals("Basic Competencies")){
            details2.setText("For this module, you must work on further studying how your work relationship and communication is practiced. Your professionalism will add value to your career as a truck driver.");
        }else if(getIntent().getStringExtra("module").equals("Common Competencies")){
            details2.setText("For this module, studying about sealants and adhesive and how these are applied would give you a clear idea which to apply on specific surface materials. Cleanliness through proper waste disposal or storage also improves your self-discipline.");
        }else if(getIntent().getStringExtra("module").equals("Core Competencies")){
            details2.setText("For this module, it is important to be knowledgeable about the parts of your vehicle, how to maintain the truck and which units need to be checked regularly for your safety. Your ability to identify road markers and signals, and also obey traffic rules and regulations are also an indicator of how competent you are as truck driver.");
        }
    }

    private void getData() {

        String value = module;

        String url = Config5.DATA_URL + value;



        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showJSON(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Evaluation_Basic_Content.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSON(String response) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        al = new ArrayList<String>();
        lesson_id_key = new ArrayList<String>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config5.JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);

                al.add(jo.getString(Config5.KEY_TITLE));
                lesson_id_key.add(jo.getString(Config5.KEY_ID));
            }

            StringBuilder builder = new StringBuilder();
            for (String details : al) {
                builder.append(details + "\n\n");
            }
            lessons_topic.setText(builder.toString());

//            Toast.makeText(Evaluation_Basic_Content.this, al.get(0), Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}