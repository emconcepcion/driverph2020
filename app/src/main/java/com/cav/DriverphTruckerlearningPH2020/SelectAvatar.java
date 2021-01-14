package com.cav.DriverphTruckerlearningPH2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class SelectAvatar extends AppCompatActivity {

    public String email, img_num, fname, lname,username, password, id, oldimage;
    ImageView frame1, frame2, frame3, frame4, frame5, frame6, avatar1, avatar2, avatar3, avatar4, avatar5, avatar6, imageView7;
    TextView textView12;
    Button selectbtn, cancelbtn;
    private String retrieveUrl="https://phportal.net/driverph/change_avatar.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_avatar);

        frame1 = findViewById(R.id.frame1);
        frame2 = findViewById(R.id.frame2);
        frame3 = findViewById(R.id.frame3);
        frame4 = findViewById(R.id.frame4);
        frame5 = findViewById(R.id.frame5);
        frame6 = findViewById(R.id.frame6);
        avatar1 = findViewById(R.id.avatar_1);
        avatar2 = findViewById(R.id.avatar_2);
        avatar3 = findViewById(R.id.avatar_3);
        avatar4 = findViewById(R.id.avatar_4);
        avatar5 = findViewById(R.id.avatar_5);
        avatar6 = findViewById(R.id.avatar_6);
        imageView7 = findViewById(R.id.imageView7);
        textView12 = findViewById(R.id.textView12);
        selectbtn = findViewById(R.id.selectbtn);
        cancelbtn = findViewById(R.id.cancelbtn);
        email = getIntent().getStringExtra("email");
        img_num = getIntent().getStringExtra("image");
        id = getIntent().getStringExtra("id");
        fname = getIntent().getStringExtra("first_name");
        lname = getIntent().getStringExtra("last_name");
        username = getIntent().getStringExtra("username");
        oldimage = getIntent().getStringExtra("image");
        Toast.makeText(SelectAvatar.this, id, Toast.LENGTH_SHORT).show();
        framesetv();

        selectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(id);
                if(i >= 1) {
                    Intent intent = new Intent(SelectAvatar.this, AccountEdit.class);
                    Bundle extras = new Bundle();
                    extras.putString("first_name", fname);
                    extras.putString("last_name", lname);
                    extras.putString("email", email);
                    extras.putString("username", username);
                    extras.putString("image", img_num);
                    extras.putString("id", id);
                    intent.putExtras(extras);
                    startActivity(intent);
                }else if(i == 0){
                    save();
                }
            }
        });

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(id);
                if(i >= 1) {
                    Intent intent = new Intent(SelectAvatar.this, AccountEdit.class);
                    Bundle extras = new Bundle();
                    extras.putString("first_name", fname);
                    extras.putString("last_name", lname);
                    extras.putString("email", email);
                    extras.putString("username", username);
                    extras.putString("image", img_num);
                    extras.putString("id", id);
                    intent.putExtras(extras);
                    startActivity(intent);
                }else if(i == 0){
                    Intent intent = new Intent(SelectAvatar.this, Dashboard.class);
                    startActivity(intent);
                }

            }
        });
    }

    public void framesetv(){
        int imageResource = getResources().getIdentifier("avatar" + img_num, "drawable", getPackageName());
        Drawable image = getResources().getDrawable(imageResource);
        imageView7.setImageDrawable(image);
    }

    private void save(){
        final String email1 = email;
        final String avatar1 = img_num;
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
                params.put("email", email1);
                params.put("avatar", avatar1);

                //returing the response
                return requestHandler.sendPostRequest(retrieveUrl, params);
            }

            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                Toast.makeText(SelectAvatar.this, "Avatar Change", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SelectAvatar.this, Dashboard.class);
                startActivity(intent);
            }
        }

        show_prod show = new show_prod();
        show.execute();
    }

    public void clickavatar1(View view){
        img_num = "1";
        framesetv();
    }

    public void clickavatar2(View view){
        img_num = "2";
        framesetv();
    }

    public void clickavatar3(View view){
        img_num = "3";
        framesetv();
    }

    public void clickavatar4(View view){
        img_num = "4";
        framesetv();
    }

    public void clickavatar5(View view){
        img_num = "5";
        framesetv();
    }

    public void clickavatar6(View view){
        img_num = "6";
        framesetv();
    }

    public void clickavatar7(View view){
        img_num = "7";
        framesetv();
    }

    public void clickavatar8(View view){
        img_num = "8";
        framesetv();
    }

    public void clickavatar9(View view){
        img_num = "9";
        framesetv();
    }

    public void clickavatar10(View view){
        img_num = "11";
        framesetv();
    }

    public void clickavatar11(View view){
        img_num = "11";
        framesetv();
    }

    public void clickavatar12(View view){
        img_num = "12";
        framesetv();
    }

    public void clickavatar13(View view){
        img_num = "13";
        framesetv();
    }

    public void clickavatar14(View view){
        img_num = "14";
        framesetv();
    }

    public void clickavatar15(View view){
        img_num = "15";
        framesetv();
    }

    public void clickavatar16(View view){
        img_num = "16";
        framesetv();
    }

    public void clickavatar17(View view){
        img_num = "17";
        framesetv();
    }

    public void clickavatar18(View view){
        img_num = "18";
        framesetv();
    }
}