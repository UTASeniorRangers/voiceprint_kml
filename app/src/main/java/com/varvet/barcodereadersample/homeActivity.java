package com.varvet.barcodereadersample;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import org.octoprint.api.OctoPrintInstance;

import java.net.MalformedURLException;


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


//        ip = findViewById(R.id.ip);
//        api = findViewById(R.id.api);
//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        if(MainActivity.IP != null && MainActivity.savedAPI !=null)
        {
//            IP = MainActivity.IP;
//            API = MainActivity.savedAPI;
//            ip.setText(IP);
//            api.setText(API);
        }




    }

//    private void setSupportActionBar(Toolbar toolbar) {
//
//    }


    // call this function in home_activity.xml like android:onClick="NextButton" for "NEXT"
    public void NextButton(View view)
    {
        Intent intent = new Intent(homeActivity.this,settingsActivity.class);
        startActivity(intent);
    }


    public void BackButton(View view)
    {
        Intent intent = new Intent(homeActivity.this,MainActivity.class);
        startActivity(intent);
    }


}
