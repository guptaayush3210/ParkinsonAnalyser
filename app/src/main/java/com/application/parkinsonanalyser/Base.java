package com.application.parkinsonanalyser;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import java.io.File;

public class Base extends AppCompatActivity {

    protected DrawerLayout fullLayout;
    protected FrameLayout actContent;
    protected ListView drawerList;
    protected ActionBarDrawerToggle drawerToggle;
    public String location = "ParkinsonAnalyser/";
    public File f;
    public Integer ID;
    public static int curr_section = 0;

    @Override
    public void setContentView(final int layoutResID) {
        fullLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        actContent = (FrameLayout) fullLayout.findViewById(R.id.act_content);

        getLayoutInflater().inflate(layoutResID, actContent, true);
        super.setContentView(fullLayout);

        ID = ((ParkinsonApplication) getApplicationContext()).userID;
        f = new File(getExternalFilesDir(location),ID.toString());
        f.mkdirs();

        drawerList = (ListView) fullLayout.findViewById(R.id.drawer_list);

        // Add your activity here, to get it in drawer menu.
        // Group name starts with *.
        drawerList.setAdapter(new DrawerAdapter(
                this,
                new String[]{
                        "*Home",
                        "Home",
                        "*Tests",
                        "Tap-Count",
                        "Tremor Detect",
                        "Reaction Test",
                        "*Results",
                        "Current Session",
                        "History",
                        "*About",
                        "About",
                }));

        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                drawerList.getChildAt(1).setClickable(false);
                drawerList.getChildAt(1).setAlpha((float) 0.7);
                if (curr_section != position) {
                    curr_section = position;
                    openActivity(position);
                } else
                    fullLayout.closeDrawers();
            }
        });

        drawerToggle = new ActionBarDrawerToggle(Base.this,
                fullLayout,
                R.string.app_name,
                R.string.app_name) {
            public void onDrawerClosed(View drawerView) {
              super.onDrawerClosed(drawerView);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };

        fullLayout.setDrawerListener(drawerToggle);

        final ImageView drawer_icon = (ImageView) findViewById(R.id.burger);
        drawer_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullLayout.openDrawer(drawerList);
            }
        });

        drawer_icon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        drawer_icon.setColorFilter(R.color.background_color, PorterDuff.Mode.MULTIPLY);
                        break;
                    case MotionEvent.ACTION_UP:
                        drawer_icon.clearColorFilter();
                }
                return false;
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK){
            fullLayout.closeDrawers();
            finish();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




    // Add the new activity here too, to set functioning on clicking the drawer icon.
    // About section should be the last.
    public void openActivity(int position){
        switch (position){
            case 1:
                Intent i=new Intent(this,Home.class);
                startActivity(i);
                break;
            case 3:
                Intent i1=new Intent(this,TapCount.class);
                startActivity(i1);
                break;
            case 4:
                Intent i2=new Intent(this,TremorDetect.class);
                startActivity(i2);
                break;
            case 5:
                Intent i3=new Intent(this,ReactionTest.class);
                startActivity(i3);
                break;
            case 7:
                Intent i4=new Intent(this,Home.class);
                startActivity(i4);
                break;
            case 8:
                Intent i5=new Intent(this,Home.class);
                startActivity(i5);
                break;
            case 10:
                Intent i6=new Intent(this,About.class);
                startActivity(i6);
                break;
            default:
                break;
        }
    }


    @Override
    public void onBackPressed(){
        if(fullLayout.isDrawerOpen(GravityCompat.START)){ //replace this with actual function which returns if the drawer is open
            fullLayout.closeDrawers();     // replace this with actual function which closes drawer
        }
        else{
            super.onBackPressed();
        }
    }
}
