package com.hellomystar.naimrahat.bhabnamentalhospital;

import android.content.Intent;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class PatientDemo extends AppCompatActivity {


    private int screenWidth;
    private int screenHeight;

    private ImageView arrowUp,arrowDown,arrowLeft,arrowRight,back;

    private float arrowUpX,arrowUpY,arrowDownX,arrowDownY,arrowLeftX,arrowLeftY,arrowRightX,arrowRightY;


    private Handler handler = new Handler();
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_demo);

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PatientDemo.this,DemoActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });


        arrowUp = (ImageView) findViewById(R.id.arrowUp);
        arrowDown = (ImageView) findViewById(R.id.arrowDown);
        arrowLeft = (ImageView) findViewById(R.id.arrowLeft);
        arrowRight = (ImageView) findViewById(R.id.arrowRight);

        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;


        arrowUp.setX(-80.0f);
        arrowUp.setY(-80.0f);
        arrowDown.setX(-80.0f);
        arrowDown.setY(screenHeight + 80.0f);
        arrowRight.setX(screenHeight + 80.0f);
        arrowRight.setY(-80.0f);
        arrowLeft.setX(-80.0f);
        arrowLeft.setY(-80.0f);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        changePos();
                    }
                });
            }
        },0,20);


    }

    private void changePos() {
        //up
        arrowUpY -= 10;
        if(arrowUp.getY()+arrowUp.getHeight()<0){
            arrowUpX = (float)Math.floor(Math.random()*(screenWidth - arrowUp.getWidth()));
            arrowUpY = screenHeight + 100.0f;

        }
        arrowUp.setX(arrowUpX);
        arrowUp.setY(arrowUpY);

        //down
        arrowDownY += 10;
        if(arrowDown.getY()>screenHeight){
            arrowDownX = (float)Math.floor(Math.random()*(screenWidth - arrowDown.getWidth()));
            arrowDownY = -100.0f;

        }
        arrowDown.setX(arrowDownX);
        arrowDown.setY(arrowDownY);

        //right
        arrowRightX += 10;
        if(arrowRight.getX() > screenWidth){
            arrowRightX = -100.0f;
            arrowRightY = (float)Math.floor(Math.random()*(screenHeight - arrowRight.getWidth()));
        }
        arrowRight.setX(arrowRightX);
        arrowRight.setY(arrowRightY);
        //left
        arrowLeftX -= 10;
        if(arrowLeft.getX()+ arrowLeft.getWidth() < 0){
            arrowLeftX = screenWidth +100.0f;
            arrowLeftY = (float)Math.floor(Math.random()*(screenHeight - arrowLeft.getWidth()));
        }
        arrowLeft.setX(arrowLeftX);
        arrowLeft.setY(arrowLeftY);

    }
}
