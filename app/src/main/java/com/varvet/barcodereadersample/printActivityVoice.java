package com.varvet.barcodereadersample;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.octoprint.api.FileCommand;
import org.octoprint.api.JobCommand;
import org.octoprint.api.PrinterCommand;

import static com.varvet.barcodereadersample.selectFileActivity.EXTRA_POSITION;

public class printActivityVoice extends AppCompatActivity {



    public String print(PrinterCommand printerState) {

        FileCommand print = new FileCommand(MainActivity.octoPrint);
        System.out.println("checking"+EXTRA_POSITION);
        printActivity obj = new printActivity();

        final String selectedFileName = print.listFiles().get(getIntent().getIntExtra(EXTRA_POSITION,0)).getName();

        //isPrinting returns false if printer state is not printing
        if(!printerState.getCurrentState().isPrinting())
        {
            return selectedFileName;
//            print.printFile(selectedFileName);
//            return "Job is Printing";
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
            return "Job was canceled";
        }
        else
        {
            return "Job is already canceled";
        }
    }

}
