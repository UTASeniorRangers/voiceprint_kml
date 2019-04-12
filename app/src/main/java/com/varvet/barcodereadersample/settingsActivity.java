package com.varvet.barcodereadersample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

            try {
                MainActivity.octoPrint = new OctoPrintInstance(IP, 80, API);
                ConnectionCommand currSettings = new ConnectionCommand(MainActivity.octoPrint);

                if (currSettings.getCurrentState().isConnected()) {
                    state.setText(String.format("STATE:  %s", currSettings.getCurrentState().getState()));
                    BaudRate.setText(String.format("BAUDRATE:  %s", currSettings.getCurrentState().getBaudrate()));
                    port.setText(String.format("PORT:  %s", currSettings.getCurrentState().getPort()));
                    profile.setText(String.format("PROFILE:  %s", currSettings.getCurrentState().getPrinterProfile()));


                } else {
                    Toast.makeText(settingsActivity.this, "Closed Connection", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }



    public void NextButton(View view) {
        Intent intent = new Intent(settingsActivity.this,selectFileActivity.class);
        startActivity(intent);
    }

    public void BackButton(View view) {
        Intent intent = new Intent(settingsActivity.this,homeActivity.class);
        startActivity(intent);
    }


}
