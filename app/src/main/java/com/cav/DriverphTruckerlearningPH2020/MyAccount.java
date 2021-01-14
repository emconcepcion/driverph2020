package com.cav.DriverphTruckerlearningPH2020;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAccount extends AppCompatActivity {
    FloatingActionButton fabedit;
    public ImageView avatarimage;
    public TextView tv_fullname, tv_email, tv_username, changepassword;
    public String email, img_num, fname, lname, id;
    ProgressDialog pdLoading;
    SharedPreferences sharedPreferences;
    //    private String retrievedatasUrl="https://driver-ph.000webhostapp.com/driverphtest/retrievemyaccount.php";
    private String retrievedatasUrl="https://phportal.net/driverph/retrievemyaccount.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        Toolbar toolbar = findViewById(R.id.toolbar);
        fabedit = findViewById(R.id.fab_edit);
        avatarimage = findViewById(R.id.avatarimage);
        tv_fullname = findViewById(R.id.tv_fullname);
        tv_email = findViewById(R.id.tv_email);
        tv_username = findViewById(R.id.tv_username);
        changepassword = findViewById(R.id.changepassword);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        pdLoading = new ProgressDialog(MyAccount.this);
        pdLoading.setMessage("\tLoading...");
        pdLoading.setCancelable(true);
        pdLoading.show();
        pdLoading.setMax(3000000);
        email = getIntent().getStringExtra("email");
        myaccountdata(email);


        fabedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAccount.this, AccountEdit.class);
                Bundle extras = new Bundle();
                extras.putString("first_name", fname);
                extras.putString("last_name", lname);
                extras.putString("email", email);
                extras.putString("username", tv_username.getText().toString());
                extras.putString("image", img_num);
                extras.putString("id", id);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAccount.this, ChangePassword.class);
                Bundle extras = new Bundle();
                extras.putString("email", email);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }

    public void myaccountdata(String m_email){
        final String email = m_email;

        class show_prod extends AsyncTask<Void, Void, String> {

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
                params.put("email", email);

                //returing the response
                return requestHandler.sendPostRequest(retrievedatasUrl, params);
            }

            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                try{
                    //Converting response to JSON Object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")){
                        fname = obj.getString("first_name");
                        lname = obj.getString("last_name");
                        tv_fullname.setText(obj.getString("first_name") + " " + obj.getString("last_name"));
                        tv_email.setText(obj.getString("email"));
                        tv_username.setText(obj.getString("username"));
//                        tv_password.setText(obj.getString("password"));
                        img_num = obj.getString("image");
                        id = obj.getString("id");

                        int imageResource = getResources().getIdentifier("avatar" + img_num, "drawable", getPackageName());
                        Drawable image = getResources().getDrawable(imageResource);
                        avatarimage.setImageDrawable(image);
                        pdLoading.dismiss();
                    }
                } catch (Exception e ){
                    Toast.makeText(MyAccount.this, "Exception: "+e, Toast.LENGTH_SHORT).show();
                }
            }
        }
        show_prod show = new show_prod();
        show.execute();
    }
}