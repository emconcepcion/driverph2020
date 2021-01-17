package com.cav.DriverphTruckerlearningPH2020;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.muddzdev.styleabletoast.StyleableToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NetworkMonitor extends BroadcastReceiver {
    public static final String TEST_POST_ALL = "https://phportal.net/driverph/paa.php";
    StringRequest stringRequest, stringRequest1;
    int counter = 0;
    @Override
    public void onReceive(Context context, Intent intent) {

        if (checkNetworkConnection(context)){

            QuizDbHelper dbHelper = new QuizDbHelper(context);
            SQLiteDatabase database = dbHelper.getWritableDatabase();

            //retrieve content using cursor
            Cursor cursor = dbHelper.readFromLocalDatabase(database);

            while (cursor.moveToNext()){
                int sync_status = cursor.getInt(cursor.getColumnIndex(DbContract.ScoresTable.SYNC_STATUS));
                if (sync_status == DbContract.SYNC_STATUS_FAILED && counter < 1){
                    int userIdY = cursor.getInt(cursor.getColumnIndex(DbContract.ScoresTable.COLUMN_NAME_USER_ID));
                    String emailY = cursor.getString(cursor.getColumnIndex(DbContract.ScoresTable.COLUMN_NAME_EMAIL));
                    int scoreY = cursor.getInt(cursor.getColumnIndex(DbContract.ScoresTable.COLUMN_NAME_SCORE));
                    int num_itemsY = cursor.getInt(cursor.getColumnIndex(DbContract.ScoresTable.COLUMN_NAME_NUM_ITEMS));
                    String chapterY = cursor.getString(cursor.getColumnIndex(DbContract.ScoresTable.COLUMN_NAME_CHAPTER));
                    int num_attemptY = cursor.getInt(cursor.getColumnIndex(DbContract.ScoresTable.COLUMN_NAME_NUM_ATTEMPT));
                    String durationY =  cursor.getString(cursor.getColumnIndex(DbContract.ScoresTable.COLUMN_NAME_DURATION));
                    String date_takenY = cursor.getString(cursor.getColumnIndex(DbContract.ScoresTable.COLUMN_NAME_DATE_TAKEN));
                    int isLockedY = cursor.getInt(cursor.getColumnIndex(DbContract.ScoresTable.COLUMN_NAME_IS_LOCKED));
                    int isCompletedY = cursor.getInt(cursor.getColumnIndex(DbContract.ScoresTable.COLUMN_NAME_IS_COMPLETED));

                    stringRequest = new StringRequest(Request.Method.POST, DbContract.ScoresTable.SERVER_ALL_ATTEMPTS_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20*1000,3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                        String Response = jsonObject.getString("response");
                                        counter = 1;
                                        if (Response.equals("OK")){
                                            dbHelper.updateLocalDatabase(userIdY,emailY,scoreY,num_itemsY,chapterY,num_attemptY,durationY,
                                                    date_takenY, isLockedY, isCompletedY, DbContract.SYNC_STATUS_SAVED,database);
                                            context.sendBroadcast(new Intent(DbContract.ScoresTable.UI_UPDATE_BROADCAST));
                                            StyleableToast.makeText(context, context.getString(R.string.updated),
                                                    Toast.LENGTH_LONG, R.style.toastStyle).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("user_id", String.valueOf(userIdY));
                            params.put("email", emailY);
                            params.put("score", String.valueOf(scoreY));
                            params.put("num_of_items", String.valueOf(num_itemsY));
                            params.put("chapter", chapterY);
                            params.put("num_of_attempt", String.valueOf(num_attemptY));
                            params.put("duration", durationY);
                            params.put("date_taken", date_takenY);
                            params.put("isUnlocked", String.valueOf(isLockedY));
                            params.put("isCompleted", String.valueOf(isCompletedY));
                            return params;
                        }
                    };

                    MySingleton.getInstance(context).addToRequestQueue(stringRequest);
                    dbHelper.updateLocalDatabase(userIdY,emailY,scoreY,num_itemsY,chapterY,num_attemptY,durationY,
                                    date_takenY, isLockedY, isCompletedY, DbContract.SYNC_STATUS_SAVED,database);
                    context.sendBroadcast(new Intent(DbContract.ScoresTable.UI_UPDATE_BROADCAST));
                    StyleableToast.makeText(context, context.getString(R.string.updated),
                            Toast.LENGTH_LONG, R.style.toastStyle).show();


//                    if(QuizActivity.unlocked){
                        stringRequest1 = new StringRequest(Request.Method.POST, TEST_POST_ALL,
//                                DbContract.ScoresTable.SERVER_URL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            stringRequest1.setRetryPolicy(new DefaultRetryPolicy(20*1000,3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                            String Response = jsonObject.getString("response");
                                            counter = 1;
                                            if (Response.equals("OK")){
                                                dbHelper.updateLocalDatabase(userIdY,emailY,scoreY,num_itemsY,chapterY,num_attemptY,durationY,
                                                        date_takenY, isLockedY, isCompletedY, DbContract.SYNC_STATUS_SAVED,database);
                                                context.sendBroadcast(new Intent(DbContract.ScoresTable.UI_UPDATE_BROADCAST));
//                                            StyleableToast.makeText(context, context.getString(R.string.updated),
//                                                    Toast.LENGTH_LONG, R.style.toastStyle).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        })
                        {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("userId", String.valueOf(userIdY));
                                params.put("email", emailY);
                                params.put("score", String.valueOf(scoreY));
                                params.put("num_of_items", String.valueOf(num_itemsY));
                                params.put("chapter", chapterY);
                                params.put("num_of_attempt", String.valueOf(num_attemptY));
                                params.put("duration", durationY);
                                params.put("date_taken", date_takenY);
                                params.put("isUnlocked", String.valueOf(isLockedY));
                                params.put("isCompleted", String.valueOf(isCompletedY));
                                return params;
                            }
                        };
                        MySingleton.getInstance(context).addToRequestQueue(stringRequest1);
//                    }
                }
            }
            //  dbHelper.close();
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }else{
        }
    }
    //check for internet connection
    public boolean checkNetworkConnection(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}

//modified networkmonitor