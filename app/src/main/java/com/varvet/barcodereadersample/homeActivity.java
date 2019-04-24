package com.varvet.barcodereadersample;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.MenuItem;
import android.view.View;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;
import android.support.v7.app.ActionBar;

import org.octoprint.api.OctoPrintInstance;

import java.net.MalformedURLException;
import java.util.Objects;


public class homeActivity extends AppCompatActivity {

    public static String IP = MainActivity.savedAPI;
    public static String API = MainActivity.IP;
    private TextView ip;
    private TextView api;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        setActionBar("Home");

        //If action bar need to be customized
        //Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //getSupportActionBar().setCustomView(R.layout.actionbar_layout);

    }

    public void setActionBar(String heading){
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        actionBar.setTitle(heading);
        actionBar.show();
    }


    public void ForwardButton(MenuItem item) {
        Intent intent = new Intent(homeActivity.this,settingsActivity.class);
        startActivity(intent);
    }

    public void ReverseButton(MenuItem item) {
        Intent intent = new Intent(homeActivity.this,MainActivity.class);
        startActivity(intent);
    }

    public void HomeButton(MenuItem item) {
        Intent intent = new Intent (homeActivity.this,homeActivity.class);
        startActivity(intent);
    }


    // call this function in home_activity.xml like android:onClick="NextButton" for "NEXT"
//    public void NextButton(View view)
//    {
//        Intent intent = new Intent(homeActivity.this,settingsActivity.class);
//        startActivity(intent);
//    }
//
//
//    public void BackButton(View view)
//    {
//        Intent intent = new Intent(homeActivity.this,MainActivity.class);
//        startActivity(intent);
//    }


}
