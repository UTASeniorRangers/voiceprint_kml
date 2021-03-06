package com.varvet.barcodereadersample;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.octoprint.api.FileCommand;
import org.octoprint.api.JobCommand;
import org.octoprint.api.PrinterCommand;

import static com.varvet.barcodereadersample.selectFileActivity.EXTRA_PRINTTIME;
import static com.varvet.barcodereadersample.selectFileActivity.PREFS_NAME;
import static com.varvet.barcodereadersample.selectFileActivity.EXTRA_FILE_NAME;
import static com.varvet.barcodereadersample.selectFileActivity.EXTRA_POSITION;


public class printActivity extends AppCompatActivity {


    Recognizer voice = Recognizer.getInstance();
    FileCommand print = new FileCommand(MainActivity.octoPrint);
   // Button stpBtn = findViewById(R.id.stopButton);
   // Button prtBtn = findViewById(R.id.printButton);


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.print_activity);
        TextView selectedFile = findViewById(R.id.selectedFileName);

        final TextView estimatedPrintTime = findViewById(R.id.printTime);
        final Button stopBtn = findViewById(R.id.stopButton);
        final Button printButton = findViewById(R.id.printButton);

        setActionBar("Print Activity");
        stopBtn.setEnabled(false);

        final JobCommand job = new JobCommand(MainActivity.octoPrint);
        final PrinterCommand printerState = new PrinterCommand(MainActivity.octoPrint);

        printButton.setEnabled(true);

        selectedFile.setText(String.format("Selected: [%s]",getIntent().getStringExtra(EXTRA_FILE_NAME)));

        estimatedPrintTime.setText(String.format("Duration: %.2f ",getIntent().getFloatExtra(EXTRA_PRINTTIME,0))+"minutes");

        // start listening for user command
        Listening();

        printButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // FileCommand print = new FileCommand(MainActivity.octoPrint);
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

    public String print(PrinterCommand printerState) {
        System.out.println("let me get that "+PREFS_NAME); //TODO: ERROR


        String selectedFileName = printData.getFileName();
        FileCommand print = new FileCommand(MainActivity.octoPrint);
      //  System.out.println("checking"+EXTRA_POSITION);

        //isPrinting returns false if printer state is not printing
        if(!printerState.getCurrentState().isPrinting())
        {
            print.printFile(selectedFileName);
            //stpBtn.setEnabled(true);
            return "Job is Printing";
        }
        else
        {
            return "Let the previous job finished";
        }


    }

    public String stop(JobCommand job, PrinterCommand printerState) {
        if(printerState.getCurrentState().isPrinting() || printerState.getCurrentState().isPaused())
        {
            job.updateJob(JobCommand.JobState.CANCEL);
            //prtBtn.setEnabled(true);
            return "Job was canceled";
        }
        else
        {
            return "Job is already canceled";
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

    public void Listening() {
        voice.initializeSpeech(this);
    }

    public void ForwardButton(MenuItem item) {
//        Intent intent = new Intent(printActivity.this);
//        startActivity(intent);
        voice.kill();
    }

    public void ReverseButton(MenuItem item) {
        Intent intent = new Intent(printActivity.this,fileChooserActivity.class);
        startActivity(intent);
        voice.kill();
    }

    public void HomeButton(MenuItem item) {
        Intent intent = new Intent (printActivity.this,homeActivity.class);
        startActivity(intent);
        voice.kill();
    }
}
