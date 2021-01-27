package com.cav.DriverphTruckerlearningPH2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import com.muddzdev.styleabletoast.StyleableToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.cav.DriverphTruckerlearningPH2020.Dashboard.Uid_PREFS;
import static com.cav.DriverphTruckerlearningPH2020.Dashboard.dashboard_email;

public class SummarizedScoresServer extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<MyScoresServer> myScoresServerList;
    private TextView myUSerId, myEmailSum;
    private static final String Server_Scores_URL = "https://phportal.net/driverph/scoresOnline.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summarized_scores_server);

        myUSerId = findViewById(R.id.tv_user_id_summary);
        myEmailSum = findViewById(R.id.tv_email_summary);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myScoresServerList = new ArrayList<>();
        loadRecyclerViewData();
        if (!checkNetworkConnection()){
            StyleableToast.makeText(getApplicationContext(), SummarizedScoresServer.this.getString(R.string.connect_to_net_to_view_summary),
                    Toast.LENGTH_LONG, R.style.toastStyle).show();
        }

        SharedPreferences sharedPreferences = getSharedPreferences(Uid_PREFS, MODE_PRIVATE);
        int uid = sharedPreferences.getInt("user_id", 0);
        myUSerId.setText(String.valueOf(uid));

        SharedPreferences sp = getApplicationContext().getSharedPreferences("mySavedAttempt", Context.MODE_PRIVATE);
        String myEmail = sp.getString("email", "");
        myEmailSum.setText(myEmail);

    }

    private void loadRecyclerViewData() {

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.DEPRECATED_GET_OR_POST,
                Server_Scores_URL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Toast.makeText(SummarizedScoresServer.this, "Loading...", Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray array = jsonObject.getJSONArray("data");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                MyScoresServer scoresServer = new MyScoresServer(o.getInt("userId"),
                                        o.getString("email"), o.getInt("numberOfCorrectAnswers"),
                                        o.getInt("numberOfQuestions"), o.getString("module"),
                                        o.getInt("retryCount"), o.getString("minutesToFinish"),
                                        o.getString("createdOn"), o.getInt("isUnlocked"),
                                        o.getInt("passed"));
                                myScoresServerList.add(scoresServer);
                            }
                            adapter = new CustomAdapter(myScoresServerList, getApplicationContext());
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
//                        Toast.makeText(SummarizedScoresServer.this, volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                        if (volleyError instanceof TimeoutError) {
                            Toast.makeText(SummarizedScoresServer.this, "Timeout error. Please try again later.", Toast.LENGTH_SHORT).show();
                        } else if (volleyError instanceof NoConnectionError) {
                            checkNetworkConnection();
                            Toast.makeText(SummarizedScoresServer.this, R.string.conn_net, Toast.LENGTH_SHORT).show();
                        } else if (volleyError instanceof AuthFailureError) {
                            Toast.makeText(SummarizedScoresServer.this, "Auth error. Please try again later.", Toast.LENGTH_SHORT).show();
                        } else if (volleyError instanceof ServerError) {
                            Toast.makeText(SummarizedScoresServer.this, "Server error. Please try again later.", Toast.LENGTH_SHORT).show();
                        } else if (volleyError instanceof NetworkError) {
                            Toast.makeText(SummarizedScoresServer.this, "Network error", Toast.LENGTH_SHORT).show();
                        } else if (volleyError instanceof ParseError) {
                            Toast.makeText(SummarizedScoresServer.this, "Parse error. Please try again later.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String emSum = myEmailSum.getText().toString();
                params.put("email", emSum);
                Log.d("email", emSum + "");
                Log.d("Fetch passed tests:", "successful...");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    //check for internet connection
    public boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


}