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
    TextView module_tv, percent_tv, over, over2, lessons_topic, details, passorfailed, recommendation;
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
        passorfailed = findViewById(R.id.passorfailed);
//        module_tv.setText(getIntent().getStringExtra("module"));
        module = getIntent().getStringExtra("module_code");
        recommendation = findViewById(R.id.textView36);
        getData();
        int res = Integer.parseInt(getIntent().getStringExtra("percent"));
        over.setText(res + "/10");
        over2.setText(getIntent().getStringExtra("attempt") + " Retake(s)");

        if(res <= 7){
            passorfailed.setText("FAILED");
            passorfailed.setTextColor(Color.RED);
            details.setText("You have failed the " + getIntent().getStringExtra("module") + " module." +
                    "\n\nWe recommend you to please review this module to pass the test and unlock the succeeding modules.");
        }else if((res == 8) || (res == 9)){
            passorfailed.setText("PASSED");
            passorfailed.setTextColor(Color.GREEN);
            details.setText("Well done! You have passed the \n" + getIntent().getStringExtra("module") + " module." +
                    "\n\nYou can now move on to the next module!");
        }else if(res == 10){
            passorfailed.setText("PASSED");
            passorfailed.setTextColor(Color.GREEN);
            recommendation.setVisibility(View.INVISIBLE);
            details.setText("Excellent job!\nYou have achieved a perfect score!\nYou can now move on to the next module!");
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