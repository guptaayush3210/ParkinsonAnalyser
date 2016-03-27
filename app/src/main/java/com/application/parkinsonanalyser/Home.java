package com.application.parkinsonanalyser;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class Home extends Base {

    Button set;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        set = (Button) findViewById(R.id.setuserid);

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ID = ++((ParkinsonApplication) getApplicationContext()).userID;
                Log.d("ID open", ID.toString());
                file = new File(getExternalFilesDir(location), ID.toString() + ".txt");
                if (ID == 2) {
                    try {
                        file.createNewFile();
                        Log.d("file", file.getName());
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("create file", e.toString());
                    }
                } else {
                    File f = new File(getExternalFilesDir(location), "");
                    File[] files = f.listFiles();
                    for (File inFile : files) {
                        if (inFile.isFile()) {
                            Log.d("ID check", inFile.getName());
                            inFile.renameTo(file);
                        }
                    }
                }

                Toast.makeText(getApplicationContext(), "User Updated", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ( keyCode == KeyEvent.KEYCODE_MENU ) {
            // do nothing
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
