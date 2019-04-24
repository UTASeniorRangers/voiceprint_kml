package com.varvet.barcodereadersample;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import org.octoprint.api.FileCommand;
import org.octoprint.api.JobCommand;
import org.octoprint.api.PrinterCommand;

import static com.varvet.barcodereadersample.selectFileActivity.EXTRA_POSITION;

public class printActivityVoice {


    public static String print(JobCommand job, PrinterCommand printerState, Intent intent) {
        FileCommand print = new FileCommand(MainActivity.octoPrint);
        final String selectedFileName = print.listFiles().get(intent.getIntExtra(EXTRA_POSITION,0)).getName();

        //isPrinting returns false if printer state is not printing
        if(!printerState.getCurrentState().isPrinting())
        {
            print.printFile(selectedFileName);
            return "Job is Printing";
        }
        else
        {
            return "Let the previous job finished";
        }
    }

    public static String stop(JobCommand job, PrinterCommand printerState) {
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
