package com.varvet.barcodereadersample;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import static com.varvet.barcodereadersample.selectFileActivity.EXTRA_PRINTTIME;
import static com.varvet.barcodereadersample.fileChooserActivity.EXTRA_DEVICE_SELECTED_FILE;

public class printActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.print_activity);

        TextView selectedFile = findViewById(R.id.selectedFileName);
        final Button cancelBtn = findViewById(R.id.cancelButton);
        final Button pauseBtn = findViewById(R.id.pauseButton);
        final Button resumeBtn = findViewById(R.id.resumeButton);
        setActionBar("Printing Activity");
        pauseBtn.setEnabled(false);
        cancelBtn.setEnabled(false);
        resumeBtn.setEnabled(false);

        final JobCommand job = new JobCommand(MainActivity.octoPrint);
        final PrinterCommand printerState = new PrinterCommand(MainActivity.octoPrint);

        final Button printButton = findViewById(R.id.printButton);
        printButton.setEnabled(true);
        final TextView estimatedPrintTime = findViewById(R.id.printTime);


        if(getIntent().getStringExtra(EXTRA_FILE_NAME) != null) {
            selectedFile.setText(String.format("Selected: [%s]",getIntent().getStringExtra(EXTRA_FILE_NAME)));
        } else {
            selectedFile.setText(String.format("Selected: [%s]",getIntent().getStringExtra(EXTRA_DEVICE_SELECTED_FILE)));
        }

        estimatedPrintTime.setText(String.format("Duration: %.2f ", getIntent().getFloatExtra(EXTRA_PRINTTIME,0)));


        printButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FileCommand print = new FileCommand(MainActivity.octoPrint);


                final String selectedFileName = print.listFiles().get(getIntent().getIntExtra(EXTRA_POSITION,0)).getName();
                printButton.setBackgroundColor(R.drawable.button_bg_round);
                //isPrinting returns false if printer state is not printing
                if(printerState.getCurrentState().isOperational() && !printerState.getCurrentState().isPrinting())
                {

                    if(selectedFileName != null) {
                        print.printFile(selectedFileName);
                    }
                    else {
                        print.uploadFile(fileChooserActivity.mfileUri.getScheme());
                    }


                    printButton.setBackgroundColor(R.drawable.button_bg_round);
                    printButton.setEnabled(false);
                    pauseBtn.setEnabled(true);
                    cancelBtn.setEnabled(true);
                    Toast.makeText(printActivity.this,"Job is Printing",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(printActivity.this,"Let the previous job finished",Toast.LENGTH_SHORT).show();
                }
            }
        });


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(printerState.getCurrentState().isPrinting() || printerState.getCurrentState().isPaused())
                {
                    job.updateJob(JobCommand.JobState.CANCEL);
                    printButton.setBackgroundColor(R.drawable.button_bg_rounded_corners);
                    printButton.setEnabled(true);
                    Toast.makeText(printActivity.this,"Job is Canceled",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(printActivity.this,"Job is already Canceled",Toast.LENGTH_SHORT).show();
                }
            }
        });


        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(printerState.getCurrentState().isPrinting())
                {
                    job.updateJob(JobCommand.JobState.PAUSE);
                    resumeBtn.setEnabled(true);
                    printButton.setEnabled(false);
                    Toast.makeText(printActivity.this,"Job is Paused",Toast.LENGTH_SHORT).show();
                }
                else if(printerState.getCurrentState().isPaused())
                {
                    Toast.makeText(printActivity.this,"Job is already Paused ",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(printActivity.this, "Invalid action", Toast.LENGTH_SHORT).show();
                }

            }
        });


        /**
         * TODO: CSE-SD Lab's printers causes Error in printing, therefore, resume button in disabled
         */
        resumeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(printerState.getCurrentState().isPaused())
//                {
//                    job.updateJob(JobCommand.JobState.RESUME);
//                    resumeBtn.setEnabled(false);
//                    Toast.makeText(printActivity.this,"Job is Resume ",Toast.LENGTH_SHORT).show();
//                }
//                else if(printerState.getCurrentState().isPrinting())
//                {
//                    Toast.makeText(printActivity.this,"Job is already Printing ",Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    Toast.makeText(printActivity.this,"Invalid action ",Toast.LENGTH_SHORT).show();
//                }
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




    public void ForwardButton(MenuItem item) {
//        Intent intent = new Intent(printActivity.this);
//        startActivity(intent);
    }

    public void ReverseButton(MenuItem item) {
        Intent intent = new Intent(printActivity.this,fileChooserActivity.class);
        startActivity(intent);
    }

    public void HomeButton(MenuItem item) {
        Intent intent = new Intent (printActivity.this,homeActivity.class);
        startActivity(intent);
    }
}
