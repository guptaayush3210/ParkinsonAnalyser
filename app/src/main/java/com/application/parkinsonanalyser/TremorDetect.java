package com.application.parkinsonanalyser;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class TremorDetect extends Base implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private FileWriter writer_left,writer_right;
    private File file;

    TextView hand;
    Integer testphase = 0;
    Button start,submit;
    ProgressBar progressBar;
    Integer timer = -1;
    Boolean take_readings = false;
    final Timer t =  new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tremor_detect);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mSensor , SensorManager.SENSOR_DELAY_NORMAL);

        hand = (TextView) findViewById(R.id.hand);
        start = (Button) findViewById(R.id.starttremor);
        submit = (Button) findViewById(R.id.submittremor);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        try {;
            file = new File(f,"tremor_left.txt");
            writer_left = new FileWriter(file,true);
        } catch (IOException e) {
            Log.d("here",e.toString());
            e.printStackTrace();
        }

        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (timer > 0) {
                            progressBar.setProgress(100 - 10*timer);
                            timer--;
                        } else {
                            take_readings = false;
                            submit.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        }, 1, 1000);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start.setVisibility(View.GONE);
                submit.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                timer = 10;
                take_readings = true;
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (testphase == 0){
                    testphase++;
                    try {
                        writer_left.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        file = new File(f,"tremor_right.txt");
                        writer_right = new FileWriter(file,true);
                    } catch (IOException e) {
                        Log.d("here", "here");
                        e.printStackTrace();
                    }
                    start.setVisibility(View.VISIBLE);
                    submit.setVisibility(View.GONE);
                    hand.setText("Stretch your right hand and then click start");
                }
                else{
                    hand.setText("Both Tests Taken. Good Job :)");
                    submit.setVisibility(View.GONE);
                    try {
                        writer_right.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    @Override
    public void onAccuracyChanged(Sensor mSensor,int accuracy){
        //
    }

    @Override
    public void onSensorChanged(SensorEvent event){
//        final double alpha = 0.8;
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER || !take_readings)
            return;

        String x = event.values[0]+","+event.values[1]+","+event.values[2]+";\n";
        try {
            Log.d("text",x);
            if (testphase==0) {
                writer_left.write(x);
            }
            else {
                writer_right.write(x);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause(){
        mSensorManager.unregisterListener(this);
        try {
            writer_left.close();
            if (testphase == 1)
                writer_right.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onPause();
    }

    @Override
    public void onResume(){
        mSensorManager.registerListener(this, mSensor , SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }

}
