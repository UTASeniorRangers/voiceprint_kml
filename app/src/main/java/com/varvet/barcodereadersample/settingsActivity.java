package com.varvet.barcodereadersample;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.octoprint.api.ConnectionCommand;
import org.octoprint.api.OctoPrintInstance;
import org.octoprint.api.PrinterCommand;
import org.octoprint.api.model.ConnectionState;

import java.net.MalformedURLException;


public class settingsActivity extends AppCompatActivity {

    private static String IP = MainActivity.IP;
    private static String API = MainActivity.savedAPI;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        TextView state = findViewById(R.id.state);
        TextView BaudRate = findViewById(R.id.baudrate);
        TextView port = findViewById(R.id.port);
        TextView profile = findViewById(R.id.profile);
        setActionBar("Settings");


        //GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

            try {
                MainActivity.octoPrint = new OctoPrintInstance(IP, 80, API);
                ConnectionCommand currSettings = new ConnectionCommand(MainActivity.octoPrint);

                if (currSettings.getCurrentState().isConnected()) {
                    state.setText(String.format("STATE:  %s", currSettings.getCurrentState().getState()));
                    //gridLayoutManager.addView(state,1);
                    BaudRate.setText(String.format("BAUDRATE:  %s", currSettings.getCurrentState().getBaudrate()));
                    //gridLayoutManager.addView(BaudRate,2);
                    port.setText(String.format("PORT:  %s", currSettings.getCurrentState().getPort()));
                    //gridLayoutManager.addView(port,3);
                    profile.setText(String.format("PROFILE:  %s", currSettings.getCurrentState().getPrinterProfile()));
                   // gridLayoutManager.addView(profile,4);

                } else {
                    Toast.makeText(settingsActivity.this, "Closed Connection", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        Intent intent = new Intent(settingsActivity.this,fileChooserActivity.class);
        startActivity(intent);
    }

    public void ReverseButton(MenuItem item) {
        Intent intent = new Intent(settingsActivity.this,homeActivity.class);
        startActivity(intent);
    }

    public void HomeButton(MenuItem item) {
        Intent intent = new Intent (settingsActivity.this,homeActivity.class);
        startActivity(intent);
    }

//
//    public void NextButton(View view) {
//        Intent intent = new Intent(settingsActivity.this,selectFileActivity.class);
//        startActivity(intent);
//    }
//
//    public void BackButton(View view) {
//        Intent intent = new Intent(settingsActivity.this,homeActivity.class);
//        startActivity(intent);
//    }


}
