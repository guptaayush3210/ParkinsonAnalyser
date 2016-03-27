package com.application.parkinsonanalyser;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class TapCount extends Base {

    TextView tap_count;
    RelativeLayout main_frame;
    TextView time_left,hand;
    Button restart,submit;
    Integer counter = 0,timer = 10;
    final Timer t = new Timer();

    File file;
    FileWriter writer_left,writer_right;
    Integer testphase = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap_count);

        tap_count = (TextView) findViewById(R.id.tapcount);
        main_frame = (RelativeLayout) findViewById(R.id.container);
        time_left = (TextView) findViewById(R.id.timeleft);
        restart = (Button) findViewById(R.id.restart);
        submit = (Button) findViewById(R.id.tap_submit);
        hand = (TextView) findViewById(R.id.taphand);

        main_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timer >= 0) {
                    counter++;
                    tap_count.setText("Tap Count: " + counter.toString());
                }
            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer = 10;
                counter = 0;
                tap_count.setText("Tap Count: "+counter.toString());
                time_left.setText("Time Left: " + timer.toString() + " sec");
            }
        });

        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (timer >= 0) {
                            time_left.setText("Time Left: " + timer.toString() + " sec");
                            timer--;
                        } else
                            submit.setVisibility(View.VISIBLE);
                    }
                });
            }
        }, 1000, 1000);


        try {
            file = new File(f,"tap_left.txt");
            writer_left = new FileWriter(file,true);
        } catch (IOException e) {
            Log.d("here", "here");
            e.printStackTrace();
        }




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String x = counter.toString();
                    if (testphase==0) {
                        writer_left.write(x);
                        testphase++;
                        hand.setText("RIGHT HAND");
                        writer_left.close();
                        try {
                            file = new File(f,"tap_right.txt");
                            writer_right = new FileWriter(file,true);
                        } catch (IOException e) {
                            Log.d("here", "here");
                            e.printStackTrace();
                        }
                        restart.performClick();

                    }
                    else {
                        writer_right.write(x);
                        hand.setText("DONE");
                        writer_right.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
