package com.application.parkinsonanalyser;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ReactionTest extends Base {

    Button start,tap,restart,submit;
    TextView result;
    final Timer t = new Timer();
    float time=0;
    RelativeLayout frame;
    RelativeLayout.LayoutParams params;
    DisplayMetrics metrics;

    File file;
    FileWriter writer;

    int px;
    int counter=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reaction_test);

        start = (Button) findViewById(R.id.start);
        tap = (Button) findViewById(R.id.tap);
        result = (TextView) findViewById(R.id.avgresponse);
        restart = (Button) findViewById(R.id.restartresp);
        frame = (RelativeLayout) findViewById(R.id.reaction_frame);
        submit = (Button) findViewById(R.id.submitresp);

        file = new File(f,"reaction.txt");
        try{
            writer = new FileWriter(file,true);
        } catch (IOException e){
            e.printStackTrace();
        }


        params = (RelativeLayout.LayoutParams) tap.getLayoutParams();
        metrics = this.getResources().getDisplayMetrics();

        px = Math.round(80 * (metrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start.setVisibility(View.GONE);
                tap.setVisibility(View.VISIBLE);
                t.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        time++;
                    }
                }, 1, 1);
            }
        });

        tap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                params.leftMargin = new Random().nextInt(metrics.widthPixels - tap.getWidth());
                params.topMargin = new Random().nextInt(metrics.heightPixels - 2*tap.getHeight() - px);
                tap.setLayoutParams(params);

                counter++;

                if (counter == 5){
                    tap.setVisibility(View.GONE);
                    t.cancel();
                    restart.setVisibility(View.VISIBLE);
                    submit.setVisibility(View.VISIBLE);
                    result.setText("Avg response time: "+ String.format("%.2f",(time/5.0))+" ms");
                }
            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    writer.write(String.format("%.2f",(time/5.0)));
                    writer.close();
                    submit.setVisibility(View.GONE);
                    restart.setVisibility(View.GONE);
                    result.setText("Test Recorded. Thank you!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }
}
