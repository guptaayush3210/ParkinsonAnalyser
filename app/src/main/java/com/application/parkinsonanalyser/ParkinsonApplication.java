package com.application.parkinsonanalyser;

import android.app.Application;

import java.io.File;

/**
 * Created by ayush on 15/3/16.
 */
public class ParkinsonApplication extends Application {
    String loc = "ParkinsonAnalyser/";
    Integer userID = 1;

    @Override
    public void onCreate(){
        super.onCreate();
        File f = new File(getExternalFilesDir(loc),"");
        File[] files = f.listFiles();
        for (File inFile: files){
            if (inFile.isFile()){
                Integer index = inFile.getName().indexOf(".");
                userID = Integer.parseInt(inFile.getName().substring(0,index));
            }
        }
    }
}
