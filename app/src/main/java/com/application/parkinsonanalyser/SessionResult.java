package com.application.parkinsonanalyser;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SessionResult extends Base {

    File file;
    FileReader reader;
    TextView tap_left,tap_right,reflex_avg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_result);

        tap_left = (TextView) findViewById(R.id.tap_left);
        tap_right = (TextView) findViewById(R.id.tap_right);
        reflex_avg = (TextView) findViewById(R.id.reflex_avg);

        try{
            file = new File(f,"tap_left.txt");
            reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            String x  = "Left Hand: "+ br.readLine();
            Log.d("File read", x);
            tap_left.setText(x);
            reader.close();

        } catch (IOException e){
            e.printStackTrace();
        }

        try{
            file = new File(f,"tap_right.txt");
            reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            tap_right.setText("Right Hand: " + br.readLine());
            reader.close();

        } catch (IOException e){
            e.printStackTrace();
        }

        try{
            file = new File(f,"reaction.txt");
            reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            reflex_avg.setText("Average Time (ms): " + br.readLine());
            reader.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
