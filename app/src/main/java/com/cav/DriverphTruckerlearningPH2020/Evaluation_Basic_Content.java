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

import static com.cav.DriverphTruckerlearningPH2020.Constant._1;
import static com.cav.DriverphTruckerlearningPH2020.Constant._2;
import static com.cav.DriverphTruckerlearningPH2020.Constant._3;

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
            passorfailed.setTextColor(Color.RED);
            details.setText("We're sorry, but you have failed the " + getIntent().getStringExtra("module") + " module test." +
                    "\n\nWe recommend you to please review this module to pass the test and unlock the succeeding modules.");
        }else if(res >= 8 && attempt >= 5) {
            passorfailed.setText("PASSED");
            passorfailed.setTextColor(Color.GREEN);
            details.setText("Well done! You have passed the \n" + getIntent().getStringExtra("module") +" module test.\n" +
                    "\nYou can now move on to the next chapter or you can review the lessons to learn more about this module!" + "\n\nHowever, based on the number of retakes that you have for this module, we have detected that you had a difficult time passing the test. Therefore, we highly recommend you to continue reviewing this module even after passing the test. \n" +
                    "\nThe concepts that were discussed must be thoroughly learned and the practical application must be applied on a regular basis to improve your knowledge and roadworthiness.");
        }else if((res == 8) || (res == 9)){
            passorfailed.setText("PASSED");
            passorfailed.setTextColor(Color.GREEN);
            details.setText("Well done! You have passed the \n" + getIntent().getStringExtra("module") + " module test." +
                    "\n\nYou can now move on to the next chapter or you can review the lessons to learn more about this module!");
        }else if(res == 10){
            passorfailed.setText("PASSED");
            passorfailed.setTextColor(Color.GREEN);
            recommendation.setVisibility(View.INVISIBLE);
            details.setText("Excellent job! \nYou have achieved a perfect score!\nYou can now move on to the next chapter!");
        }


        if(getIntent().getStringExtra("module").equals(_1)){
            details2.setText("For this module, you are expected to work on further studying the different traffic and road signs, including the pavement markings.\n\nWe highly suggest that you read the detailed feedback for any questions that you answered incorrectly. Try to memorize the meaning of each traffic sign and the pavement markings. Random questions will be asked during your Driving Licensure Exam.\n\nThis will not only help you obey traffic rules, but will ultimately prevent you from violating the rules and getting involved in accidents caused by incorrect interpretation of the road signs. ");
        }else if(getIntent().getStringExtra("module").equals(_2)){
            details2.setText("For this module, studying about the traffic rules and regulations will equip you on how you should strictly observe the traffic rules before and while driving. We suggest that you focus on learning more about the detailed lessons of this module, which you can definitely use while on the road.\n\nA good knowledge about the traffic rules and regulations will keep you from unnecessary violation tickets if you are aware if you have done anything wrong, or vindicate yourself if you know that what you have committed is not at all a traffic violation.");
        }else if(getIntent().getStringExtra("module").equals(_3)){
            details2.setText("For this module, it is important that you have learned about being knowledgeable of the know-hows of driving. You will be expected to possess a good driving attitude and skills each time you are on the road.\n\nWe suggest that you review the topics of this module to better understand how to handle different driving scenarios, when to give way to another motorist, what to do during an accident, the consequences of negligent and irresponsible driving and a lot more.\n\nMake sure that you understand the lessons before going out on the road again.");
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