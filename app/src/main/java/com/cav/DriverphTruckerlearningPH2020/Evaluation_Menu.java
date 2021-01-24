package com.cav.DriverphTruckerlearningPH2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.cav.DriverphTruckerlearningPH2020.Constant._1;
import static com.cav.DriverphTruckerlearningPH2020.Constant._2;
import static com.cav.DriverphTruckerlearningPH2020.Constant._3;
import static com.cav.DriverphTruckerlearningPH2020.R.attr.selectableItemBackground;

public class Evaluation_Menu extends AppCompatActivity {
    String dashboard_email, basic, core, common;
    List<String> module, correctAnswer, noOfAttempt;
    ImageView arrow2, arrow3;
    CardView cardViewBasicBtn, cardViewCommonBtn, cardViewCoreBtn, cardView2, cardView3;
    TextView tv1, tv2, tv3, tv_common, tv_core;

    private String retrievedatasUrl = "https://phportal.net/driverph/assesment_percent.php";

    //a list to store all the products
    //List<Config6> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_menu);

        SharedPreferences sh = getSharedPreferences("MySharedPrefForEmail", MODE_PRIVATE);
        dashboard_email = sh.getString("driver_email", "");
        //Toast.makeText(Evaluation_Menu.this, dashboard_email, Toast.LENGTH_LONG).show();
        arrow2 = findViewById(R.id.ic_arrow2);
        arrow3 = findViewById(R.id.ic_arrow3);
        cardView2 = findViewById(R.id.common_percent_tv);
        cardView3 = findViewById(R.id.core_percent_tv);
        tv_common = findViewById(R.id.textView11);
        tv_core = findViewById(R.id.textView12);
        tv1 = findViewById(R.id.textView15);
        tv2 = findViewById(R.id.common_percent);
        tv3 = findViewById(R.id.core_percent);

        retrievedatas(dashboard_email);

        cardViewBasicBtn = findViewById(R.id.cardView_eMenu_basic_competencies);
        cardViewCommonBtn = findViewById(R.id.cardView_common_competencies);
        cardViewCoreBtn = findViewById(R.id.cardView_core_competencies);

        cardViewBasicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tv1.getText().equals("0%")){
                    Toast.makeText(Evaluation_Menu.this, "Haven't Take Quiz Yet", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(Evaluation_Menu.this, Evaluation_Basic_Content.class);
                    Bundle extras = new Bundle();
                    extras.putString("module_code", module.get(0));
                    extras.putString("module", _1);
                    extras.putString("percent", correctAnswer.get(0));
                    extras.putString("attempt", noOfAttempt.get(0));
                    intent.putExtras(extras);
                    startActivity(intent);
                }
            }
        });

        cardViewCommonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tv2.getText().equals("")){

                }else{
                    Intent intent = new Intent(Evaluation_Menu.this, Evaluation_Basic_Content.class);
                    Bundle extras = new Bundle();
                    extras.putString("module_code", module.get(1));
                    extras.putString("module", _2);
                    extras.putString("percent", correctAnswer.get(1));
                    extras.putString("attempt", noOfAttempt.get(1));
                    intent.putExtras(extras);
                    startActivity(intent);
                }
            }
        });

        cardViewCoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( tv3.getText().equals("")){

                }else{
                    Intent intent = new Intent(Evaluation_Menu.this, Evaluation_Basic_Content.class);
                    Bundle extras = new Bundle();
                    extras.putString("module_code", module.get(2));
                    extras.putString("module", _3);
                    extras.putString("percent", correctAnswer.get(2));
                    extras.putString("attempt", noOfAttempt.get(2));
                    intent.putExtras(extras);
                    startActivity(intent);
                }
            }
        });
    }


    public void retrievedatas(String r_email){
        final String email = r_email;

        class show_prod extends AsyncTask<Void, Void, String> {
            ProgressDialog pdLoading = new ProgressDialog(Evaluation_Menu.this);
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pdLoading.setMessage("\tLoading...");
                pdLoading.setMax(2000);
                pdLoading.setCancelable(true);
                pdLoading.show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("email", email);

                //returing the response
                return requestHandler.sendPostRequest(retrievedatasUrl, params);
            }

            @SuppressLint("ResourceAsColor")
            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                module = new ArrayList<String>();
                correctAnswer = new ArrayList<String>();
                noOfAttempt = new ArrayList<String>();
                try{
                    //Converting response to JSON Object
                    JSONObject obj = new JSONObject(s);
                    JSONArray obj1 = obj.getJSONArray("result");

                    //if no error in response
                    for (int i = 0; i < obj1.length(); i++) {
                        JSONObject jo = obj1.getJSONObject(i);

                        module.add(jo.getString("module"));
                        correctAnswer.add(jo.getString("correctAnswer"));
                        noOfAttempt.add(jo.getString("noOfAttempt"));
                    }


                    tv1.setText("0%");

                    if(!module.get(0).equals(null)){
                        int res = Integer.parseInt(correctAnswer.get(0));
                        int tot = res * 10;
                        tv1.setText(tot + "%");

                        if(tot < 70){
                            tv1.setTextColor(R.color.red);
                        }else{
                            tv1.setTextColor(R.color.dark_blue);
                        }
                    }

                    if(module.get(1).equals(null)){
                        arrow2.setVisibility(View.VISIBLE);
                        cardView2.setVisibility(View.INVISIBLE);
                        cardViewCommonBtn.setEnabled(false);
                    }else{
                        tv_common.setTextColor(R.color.red);
                        cardView2.setVisibility(View.VISIBLE);
                        cardViewCommonBtn.setClickable(true);
                        arrow2.setVisibility(View.INVISIBLE);
                        int res = Integer.parseInt(correctAnswer.get(1));
                        int tot = res * 10;
                        tv2.setText(tot + "%");


                        if(tot < 70){
                            tv2.setTextColor(R.color.red);
                        }else{
                            tv2.setTextColor(R.color.dark_blue);
                        }
                    }

                    if(module.get(2).equals(null)){
                        arrow2.setVisibility(View.VISIBLE);
                        cardView2.setVisibility(View.INVISIBLE);
                        cardViewCommonBtn.setEnabled(false);
                    }else{
                        tv_core.setTextColor(R.color.red);
                        cardView3.setVisibility(View.VISIBLE);
                        cardViewCommonBtn.setClickable(true);
                        arrow3.setVisibility(View.INVISIBLE);
                        int res = Integer.parseInt(correctAnswer.get(2));
                        int tot = res * 10;
                        tv3.setText(tot + "%");

                        if(tot < 70){
                            tv3.setTextColor(R.color.red);
                        }else{
                            tv3.setTextColor(R.color.dark_blue);
                        }
                    }
//                    Toast.makeText(Evaluation_Menu.this, correctAnswer.get(0) + " " + correctAnswer.get(1) + " " + correctAnswer.get(2), Toast.LENGTH_SHORT).show();
                } catch (Exception e ){
                    //Toast.makeText(Dashboard.this, "Exception: "+e, Toast.LENGTH_SHORT).show();
                }
                pdLoading.dismiss();
            }
        }

        show_prod show = new show_prod();
        show.execute();
    }
}