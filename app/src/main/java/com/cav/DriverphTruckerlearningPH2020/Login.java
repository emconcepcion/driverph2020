package com.cav.DriverphTruckerlearningPH2020;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.muddzdev.styleabletoast.StyleableToast;

import org.json.JSONObject;

import java.util.HashMap;

import static com.cav.DriverphTruckerlearningPH2020.Constant.SP_LESSONID;
import static com.cav.DriverphTruckerlearningPH2020.Dashboard.SERVER_DASHBOARD;

public class Login extends AppCompatActivity {
    EditText username, password1;
    public String u_name, pword, password2;
    public static String email;
    TextView fgtpassword;
    private String retrievedatasUrl = "https://phportal.net/driverph/login.php";
    private String retrieveprogress = "https://phportal.net/driverph/retrieve_progress.php";
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String EMAIL = "text";
    public static String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (!checkNetworkConnection()){
            StyleableToast.makeText(getApplicationContext(), Login.this.getString(R.string.conn_net),
                    Toast.LENGTH_LONG, R.style.toastStyle).show();
        }
        Button loginBtn = findViewById(R.id.btn_login);
        TextView signUpView = findViewById(R.id.textView_signUp);
        username = findViewById(R.id.login_username);
        password1 = findViewById(R.id.login_password);
        fgtpassword = findViewById(R.id.textView_fgtpassword);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                u_name = username.getText().toString().trim();
                pword = password1.getText().toString().trim();
                if (u_name.isEmpty() || pword.isEmpty()) {
                    Toast.makeText(Login.this, "Please Fill all the Textbox", Toast.LENGTH_LONG).show();
                } else {
                    userLogin();
                }
                ;
            }
        });

        signUpView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.cav.DriverphTruckerlearningPH2020.Login.this, Registration.class);
                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        fgtpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ForgotPassword.class);
                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void userLogin() {
        final String uname = username.getText().toString().trim();
        final String pass = password1.getText().toString().trim();

        class show_prod extends AsyncTask<Void, Void, String> {
            ProgressDialog pdLoading = new ProgressDialog(com.cav.DriverphTruckerlearningPH2020.Login.this);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pdLoading.setMessage("\tLoading...");
                pdLoading.setCancelable(true);
                pdLoading.show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("username", uname);
                params.put("password", pass);
                //returing the response
                return requestHandler.sendPostRequest(retrievedatasUrl, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    //Converting response to JSON Object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        email = obj.getString("email");
                        user_id = obj.getString("id");
                        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPrefForEmail", MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        myEdit.putString("driver_email", email);
                        myEdit.putString("driver_password", password1.getText().toString());
                        myEdit.putString("driver_userId", user_id);
                        myEdit.commit();
                        pdLoading.dismiss();
                        progress();

                        pdLoading.dismiss();
                    } else {
                        pdLoading.dismiss();
                        Toast.makeText(Login.this, "Incorrect", Toast.LENGTH_SHORT).show();
                    }
//                            Toast.makeText(Login.this, email, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
//                    Toast.makeText(Login.this, "Exception: " + e, Toast.LENGTH_SHORT).show();
                    if (e instanceof TimeoutError) {
                        Toast.makeText(Login.this, "Timeout error. Please try again later.", Toast.LENGTH_SHORT).show();
                    } else if (e instanceof NoConnectionError) {
                        checkNetworkConnection();
                        Toast.makeText(Login.this, R.string.conn_net, Toast.LENGTH_SHORT).show();
                    } else if (e instanceof AuthFailureError) {
                        Toast.makeText(Login.this, "Auth error. Please try again later.", Toast.LENGTH_SHORT).show();
                    } else if (e instanceof ServerError) {
                        Toast.makeText(Login.this, "Server error. Please try again later.", Toast.LENGTH_SHORT).show();
                    } else if (e instanceof NetworkError) {
                        Toast.makeText(Login.this, "Network error", Toast.LENGTH_SHORT).show();
                    } else if (e instanceof ParseError) {
                        Toast.makeText(Login.this, "Parse error. Please try again later.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        show_prod show = new show_prod();
        show.execute();
    }

    public boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public void progress() {
        final String uid = user_id;

        class progress_class extends AsyncTask<Void, Void, String> {

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
                params.put("id", uid);

                //returing the response
                return requestHandler.sendPostRequest(retrieveprogress, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    //Converting response to JSON Object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        String lesson_id = obj.getString("lessonId");
                        String lesson_title = obj.getString("lessonTitle");
                        String module_id = obj.getString("moduleId");
                        String module_name = obj.getString("moduleName");
                        Intent intent = new Intent(Login.this, Dashboard.class);
                        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                        SharedPreferences sharedPreferences = getSharedPreferences(SP_LESSONID, MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        myEdit.putString("lessonId", lesson_id);
                        myEdit.putString("lessonTitle", lesson_title);
                        myEdit.putString("moduleId", module_id);
                        myEdit.putString("moduleName", module_name);
                        myEdit.apply();
                    }
                } catch (Exception e) {
                    Toast.makeText(Login.this, "Exception: " + e, Toast.LENGTH_SHORT).show();
                }
            }
        }
        progress_class show = new progress_class();
        show.execute();
    }
}
