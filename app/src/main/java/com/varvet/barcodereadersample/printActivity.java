package com.varvet.barcodereadersample;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.varvet.barcodereadersample.selectFileActivity;

import org.octoprint.api.FileCommand;
import org.octoprint.api.JobCommand;
import org.octoprint.api.OctoPrintInstance;
import org.octoprint.api.PrinterCommand;


import java.io.File;
import java.nio.channels.SelectableChannel;

import static android.media.CamcorderProfile.get;
import static com.varvet.barcodereadersample.selectFileActivity.EXTRA_FILE_NAME;
import static com.varvet.barcodereadersample.selectFileActivity.EXTRA_POSITION;

public class printActivity extends AppCompatActivity {

    Recognizer voice = Recognizer.getInstance();


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.print_activity);
        TextView selectedFile = findViewById(R.id.selectedFileName);
        final Button stopBtn = findViewById(R.id.stopButton);

        setActionBar("Printing Activity");
        Listening();
        stopBtn.setEnabled(false);

        final JobCommand job = new JobCommand(MainActivity.octoPrint);
        final PrinterCommand printerState = new PrinterCommand(MainActivity.octoPrint);

        final Button printButton = findViewById(R.id.printButton);
        printButton.setEnabled(true);

        selectedFile.setText(String.format("Selected: [%s]",getIntent().getStringExtra(EXTRA_FILE_NAME)));


        printButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileCommand print = new FileCommand(MainActivity.octoPrint);
                final String selectedFileName = print.listFiles().get(getIntent().getIntExtra(EXTRA_POSITION,0)).getName();

                //isPrinting returns false if printer state is not printing
                if(!printerState.getCurrentState().isPrinting())
                {
                    print.printFile(selectedFileName);
                    printButton.setEnabled(false);
                    stopBtn.setEnabled(true);
                    Toast.makeText(printActivity.this,"Job is Printing",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(printActivity.this,"Let the previous job finished",Toast.LENGTH_SHORT).show();
                }
            }
        });


        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(printerState.getCurrentState().isPrinting() || printerState.getCurrentState().isPaused())
                {
                    job.updateJob(JobCommand.JobState.CANCEL);
                    printButton.setEnabled(true);
                    Toast.makeText(printActivity.this,"Job is Canceled",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(printActivity.this,"Job is already Canceled",Toast.LENGTH_SHORT).show();
                }
            }
        });


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

    public void Listening() {
        voice.initializeSpeech(this);
    }




    public void ForwardButton(MenuItem item) {
//        Intent intent = new Intent(printActivity.this);
//        startActivity(intent);
    }

    public void ReverseButton(MenuItem item) {
        Intent intent = new Intent(printActivity.this,selectFileActivity.class);
        startActivity(intent);
        voice.kill();
    }

    public void HomeButton(MenuItem item) {
        Intent intent = new Intent (printActivity.this,homeActivity.class);
        startActivity(intent);
        voice.kill();
    }
}
