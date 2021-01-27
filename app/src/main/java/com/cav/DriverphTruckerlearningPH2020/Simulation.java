package com.cav.DriverphTruckerlearningPH2020;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.media.MediaPlayer;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Simulation extends Activity {
    public static boolean isFromSimulation;
    private int mBGFarMoveX = 0;
    private int mBGNearMoveX = 0;
    //String restartHere = String.valueOf(R.string.restart);

    Bitmap bmp, pause, resume;
    Bitmap background,kinfe,note1,powerimg,note2;
    Bitmap run1;
    Bitmap run2;
    Bitmap run3;
    Bitmap coin;
    Bitmap off,on;
    Bitmap exit;
    // MediaPlayer mp1,jump,takecoin;
    private SurfaceHolder holder;
    //private gameloop gameLoopThread;
    private int x = 0,y=0,z=0,delay=0,getx,gety,sound=1;
    int show=0,sx,sy;
    int cspeed=0,kspeed=0,gameover=0;
    int score=0,health=100,reset=0;
    int pausecount=0,volume,power=0,powerrun=0,shieldrun=0;

    MediaPlayer mp1,jump,takecoin, kinfehit;
    gameloop gameLoopThread;
    String selectedGender = "male";
    Dialog instructionsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instructionsDialog = new Dialog(this);
        //showInstructions();

        isFromSimulation = true;
        //for no title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(new GameView(this));

        mp1.setLooping(true);
        mp1.start();
    }

    private void exitDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Simulation.this);
        builder.setTitle("Exit game");
        builder.setMessage("Are you sure?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Simulation.this, Dashboard.class));
                SharedPreferences pref = getApplicationContext().getSharedPreferences("higher", MODE_PRIVATE);
                Editor editor = pref.edit();
                editor.putInt("Score", score);
                editor.commit();
                System.exit(0);

            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gameLoopThread.setPause(0);
                pausecount=0;
                mp1.start();
            }
        });
        AlertDialog dialog = builder.show();
    }

    public class GameView extends SurfaceView{

        private void doDrawRunning(Canvas canvas1){
            // decrement the far background
            //mBGFarMoveX = mBGFarMoveX - 1;
            mBGFarMoveX = mBGFarMoveX - 10;
            // decrement the near background
            mBGNearMoveX = mBGNearMoveX - 4;
            // calculate the wrap factor for matching image draw
            int newFarX = background.getWidth() - (-mBGFarMoveX);
            // if we have scrolled all the way, reset to start
            if (newFarX <= 0) {
                mBGFarMoveX = 0;
                // only need one draw
                canvas1.drawBitmap(background, mBGFarMoveX, 0, null);
            } else {
                // need to draw original and wrap
                canvas1.drawBitmap(background, mBGFarMoveX, 0, null);
                canvas1.drawBitmap(background, newFarX, 0, null);
            }
        }



        @SuppressWarnings("deprecation")
        @SuppressLint("NewApi")
        public GameView(Context context) {
            super(context);
            try {
                gameLoopThread = new gameloop(this);
                holder = getHolder();

                holder.addCallback(new SurfaceHolder.Callback() {
                    @SuppressWarnings("deprecation")
                    @Override
                    public void surfaceDestroyed(SurfaceHolder holder) {
                        //for stopping the game
                        gameLoopThread.setRunning(false);
                        gameLoopThread.getThreadGroup().interrupt();
                    }

                    @SuppressLint("WrongCall")
                    @Override
                    public void surfaceCreated(SurfaceHolder holder) {
                        gameLoopThread.setRunning(true);
                        gameLoopThread.start();
                    }

                    @Override
                    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                    }
                });

                //getting the screen size
                Display display = getWindowManager().getDefaultDisplay();

                sx = display.getWidth();
                sy = display.getHeight();

                cspeed = sx / 2;
                kspeed = sx / 2;
                powerrun = (3 * sx / 4);
                shieldrun = sx / 8;
                background = BitmapFactory.decodeResource(getResources(), R.drawable.game_background_153);
                run1 = BitmapFactory.decodeResource(getResources(), R.drawable.truck_char);
                run2 = BitmapFactory.decodeResource(getResources(), R.drawable.truck_char2);
                run3 = BitmapFactory.decodeResource(getResources(), R.drawable.truck_char2);

                coin = BitmapFactory.decodeResource(getResources(), R.drawable.dfuel_small);
                off = BitmapFactory.decodeResource(getResources(), R.drawable.sound_off1);
                on = BitmapFactory.decodeResource(getResources(), R.drawable.sound_on1);
                exit = BitmapFactory.decodeResource(getResources(), R.drawable.power_off1);

                kinfe = BitmapFactory.decodeResource(getResources(), R.drawable.dcone_small);
                note1 = BitmapFactory.decodeResource(getResources(), R.drawable.note1);
                note2 = BitmapFactory.decodeResource(getResources(), R.drawable.note2);
                pause = BitmapFactory.decodeResource(getResources(), R.drawable.pause_btn);
                powerimg = BitmapFactory.decodeResource(getResources(), R.drawable.heart_up);
                resume = BitmapFactory.decodeResource(getResources(), R.drawable.resume_btn);

                off = Bitmap.createScaledBitmap(off, 90, 90, true);
                exit = Bitmap.createScaledBitmap(exit, 90, 90, true);
                on = Bitmap.createScaledBitmap(on, 90, 90, true);
                pause = Bitmap.createScaledBitmap(pause, 90, 90, true);
                resume = Bitmap.createScaledBitmap(resume, 90, 90, true);
                powerimg = Bitmap.createScaledBitmap(powerimg, 40, 40, true);

                //powerup
                note2 = Bitmap.createScaledBitmap(note2, sx, sy, true);
                //health dec
                note1 = Bitmap.createScaledBitmap(note1, sx, sy, true);

                //background=Bitmap.createScaledBitmap(background, 2*sx,sy, true);
                background = Bitmap.createScaledBitmap(background, 2 * sx, sy, true);

                mp1 = MediaPlayer.create(Simulation.this, R.raw.game_bg_music1);
                jump = MediaPlayer.create(Simulation.this, R.raw.jump);
                takecoin = MediaPlayer.create(Simulation.this, R.raw.cointake);
            } catch (Exception e) {
                Toast.makeText(Simulation.this, "Android version must be 5.1 and above", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Simulation.this, Dashboard.class));
            }
        }

        // on touch method
        @Override
        public boolean onTouchEvent(MotionEvent event) {

            if(event.getAction()==MotionEvent.ACTION_DOWN)
            {
                show=1;
                getx=(int) event.getX();
                gety=(int) event.getY();

                //exit
                if(getx<90&&gety<90)
                {
                    gameLoopThread.setPause(1);
                    pausecount=1;
                    mp1.pause();

                    exitDialog();
                }

                //sound off
                if(getx>130&&getx<200&&gety<150)
                {
                    sound=0;
                    mp1.pause();
                }

                //sound on
                if(getx>250 && getx<350 && gety<150)
                {
                    sound=1;
                    mp1.start();
                    mp1.setLooping(true);
                    takecoin.start();
                    jump.start();
                }

                if(getx>((sx/2)-200)&&gety<(sy/2)+250)
                {
                    if(health<=0)
                    {
                        gameLoopThread.setPause(0);
                        health=100;
                        score=0;
                    }
                }

                //pause game
                if(getx>(sx-150)&&gety<150&&pausecount==0)
                {
                    mp1.pause();
                    gameLoopThread.setPause(1);
                    pausecount=1;
                }
                else if(getx>(sx-150)&&gety<150&&pausecount==1)
                {
                    gameLoopThread.setPause(0);
                    pausecount=0;
                    mp1.start();
                }
            }
            return true;
        }

        @SuppressLint("WrongCall")
        @Override
        protected void onDraw(Canvas canvas)
        {
            //volume
            SharedPreferences pref = getApplicationContext().getSharedPreferences("higher", MODE_PRIVATE);
            Editor editor = pref.edit();

            canvas.drawColor(Color.BLACK);

            //background moving
            z=z-10;
            if(z==-sx)
            {
                doDrawRunning(canvas);
            }
            else
            {
                doDrawRunning(canvas);
                //canvas.drawBitmap(background, z, 0,null);        // original
            }

            //running player
            x+=5;   //run speed
            if(x==20)
            {
                x=5;
            }

            if(show==0)
            {
                if(x%2==0)
                {
                    canvas.drawBitmap(run3, sx/16, 15*sy/18, null);
                }
                else
                {
                    canvas.drawBitmap(run1, sx/16, 15*sy/18, null);
                }
                // canvas.drawBitmap(run1, sx/16, 15*sy/18, null); Original code

                //knife hit
                //if(kspeed<=sx/4&&kspeed>=sx/24) Original Code
                if(kspeed==sx/4)
                {
                    kspeed=sx/2;
                    health-=25;
                    canvas.drawBitmap(note1, 0, 0, null);
                }

                //power take
                //if(powerrun==sx/8) Original Code
                if(powerrun==sx/4)
                {
                    powerrun=3/sx;
                    health+=25;
                    canvas.drawBitmap(note2, 0, 0, null);
                }
            }

            // for jump
            if(show==1)
            {
                if(sound==1)
                {
                    jump.start();
                }
                canvas.drawBitmap(run2, sx/16, 3*sy/4, null);
                //point up
                if(cspeed==sx/4)
                //if(cspeed<=sx/8&&cspeed>=sx/16) Original Code
                {
                    if(sound==1)
                    {
                        takecoin.start();
                    }

                    if(pausecount==0)       //---------------- Pause and Resume condition
                    {
                        //mp1.pause();
                        pausecount=1;
                    }

                    cspeed=sx/2;
                    score+=10;

                    //showPopup();
                    startActivity(new Intent(Simulation.this,SimulationPopup.class));

                    Paint resumePaint = new Paint();
                    resumePaint.setColor(Color.RED);
                    resumePaint.setTextSize(50);
                    resumePaint.setStrokeWidth(10);
                    resumePaint.setAntiAlias(true);
                    resumePaint.setFakeBoldText(true);

                    //canvas.drawText("Click the", (sx/2)-200, sy/4, resumePaint);
                    canvas.drawText("Click the", (sx/2)-100, sy/4, resumePaint);
                    canvas.drawText("Play button ", (sx/2)-100, sy/2, resumePaint);
//                    canvas.drawText("Play button ", (sx/2)-200, sy/2, resumePaint); ORIGINAL CODE ABOVE
                    canvas.drawText("To continue", (sx/2)-150, (sy/2)+250, resumePaint);

                    gameLoopThread.setPause(1);
                    canvas.drawBitmap(background, sx, sy, null);
                }

                // jump-hold
                delay+=1;
                if(delay==3)
                {
                    show=0;
                    delay=0;
                }
            }

            //health up loop
            powerrun=powerrun-10;
            canvas.drawBitmap(powerimg, powerrun, 15*sy/18, null);
            if(powerrun<0)
            {
                powerrun=3*sx/4;
            }

            //for coins loop
            cspeed=cspeed-5;
            canvas.drawBitmap(coin, cspeed, 3*sy/4, null);
            if(cspeed<0)
            {
                cspeed=sx/2;
            }

            //knife loop
            kspeed=kspeed-20;
            canvas.drawBitmap(kinfe, kspeed, 15*sy/18, null);
            if(kspeed<0)
            {
                kspeed=sx;
            }

            //score
            Paint paint = new Paint();
            paint.setColor(Color.BLUE);
            paint.setAntiAlias(true);
            paint.setFakeBoldText(true);
            //score txt size
            paint.setTextSize(50);
            paint.setTextAlign(Align.CENTER);

            // score text position
            canvas.drawText("Score : "+ score, sx/2, 40, paint);

            //exit icon position
            canvas.drawBitmap(exit, 0, 10, null);

            //sound off icon position
            canvas.drawBitmap(off, 130, 0, null);

            //sound on icon position
            canvas.drawBitmap(on, 250, 0, null);

            //pause icon position
            if(pausecount==0){
                canvas.drawBitmap(pause, (sx-150), 10, null);
            }
            else{
                canvas.drawBitmap(resume, (sx-150), 10, null);
            }

            //canvas.drawBitmap(pause, (sx-25), 0, null);

            //health
            Paint myPaint = new Paint();
            myPaint.setColor(Color.RED);
            myPaint.setStrokeWidth(10);
            myPaint.setAntiAlias(true);
            myPaint.setFakeBoldText(true);
            myPaint.setTextSize(50);
            canvas.drawText("Health : "+ health, 0, 150, myPaint);
            //health bar position, width & length
            canvas.drawRect(0, 155, 250, 170, myPaint);

            //game over
            Paint restartPaint = new Paint();
            restartPaint.setColor(Color.YELLOW);
            restartPaint.setTextSize(50);
            restartPaint.setStrokeWidth(10);
            restartPaint.setAntiAlias(true);
            restartPaint.setFakeBoldText(true);
            if(health<=0)
            {
                gameover=1;
                mp1.pause();

                editor.putInt("score", score);
                editor.commit();

                canvas.drawText("YOUR SCORE : "+ score, (sx/2)-200, sy/4, myPaint);
                canvas.drawText("GAMEOVER OVER", (sx/2)-200, sy/2, myPaint);
                canvas.drawText("Restart Game", (sx/2)-150, (sy/2)+250, restartPaint);
                gameLoopThread.setPause(1);
                canvas.drawBitmap(background, sx, sy, null);
            }

            // restart
            if(reset==1)
            {
                gameLoopThread.setPause(0);
                health=100;
                score=0;
            }
        }

    }

    @Override
    public void onBackPressed() {
    }
}//Latest Working Jan. 27, 5:36