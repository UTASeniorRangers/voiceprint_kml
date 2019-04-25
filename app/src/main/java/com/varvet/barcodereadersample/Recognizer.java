package com.varvet.barcodereadersample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import com.varvet.barcodereadersample.GetIndex;

import org.octoprint.api.JobCommand;
import org.octoprint.api.PrinterCommand;


public class Recognizer implements RecognitionListener {

    //Possible static leak
    private static Recognizer instance;
    public String text = "";

    private SpeechRecognizer speech = null;
    private String LOG_TAG = "VoiceRecognitionActivity";
    private Context context;

    private ProgressBar progressBar;
    private TextView output;

    //Instance object = new Instance();

    public static synchronized Recognizer getInstance() {
        if (instance == null) {
            instance = new Recognizer();
        }

        return instance;
    }

    public void initializeSpeech(Context context) {
        this.context = context;

        progressBar = ((Activity)context).findViewById(R.id.progressBar);
        //output = ((Activity)context).findViewById(R.id.output);

        speech = SpeechRecognizer.createSpeechRecognizer(context);
        speech.setRecognitionListener(this);
        Intent recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,"en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,context.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,context.getPackageName());

        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3); //Unneeded

        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);

        speech.startListening(recognizerIntent);
        //Toast.makeText(this.context,"speech initialization is done", Toast.LENGTH_SHORT).show();
    }

    public void kill() {
        speech.destroy();
    }

    @Override
    public void onReadyForSpeech(Bundle params) {

    }

    @Override
    public void onBeginningOfSpeech() {
       // Toast.makeText(this.context,"speech beginning", Toast.LENGTH_SHORT).show();
        Log.i(LOG_TAG, "onBeginningOfSpeech");
        progressBar.setIndeterminate(false);
        progressBar.setMax(10);

    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);
        progressBar.setProgress((int) rmsdB);

    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(LOG_TAG, "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {
        //Toast.makeText(this.context,"speech ended", Toast.LENGTH_SHORT).show();
        Log.i(LOG_TAG, "onEndOfSpeech");
        progressBar.setIndeterminate(false);
        progressBar.setVisibility(View.INVISIBLE);
        speech.stopListening();

    }

    @Override
    public void onError(int error) {
        String errorMessage = getErrorText(error);
        Log.d(LOG_TAG, "FAILED " + errorMessage);

        if (errorMessage.equals("No match")) {
            //Restart audio recording
            Toast.makeText(this.context, "NO MATCH TRY AGAIN", Toast.LENGTH_SHORT).show();
            initializeSpeech(context);
        }

    }

    public String getText() {
        return text;
    }

    @Override
    public void onResults(Bundle results) {
        Log.i(LOG_TAG, "onResults");
        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        assert matches != null;
        text += matches.get(0);
        Toast.makeText(this.context,"text: "+text, Toast.LENGTH_SHORT).show();

        //TODO: Put into another method
        //TODO: [FIX] CAUSES 119 Skipped frames, The application may be doing too much work on its main thread. Put in Async task?
        //TODO: Awful implementation - use getLayout() method from Navigation class

        if (this.context instanceof com.varvet.barcodereadersample.moveHeadActivity) {

            ArrayList<String> items = new ArrayList<String>(Arrays.asList("Left","Right","Front","Back","Up","Down","Home","Quit"));
            int index = GetIndex.getIndex(text,items);

            switch(index) {
                case 0:
                    moveHead.moveLeft();
                    break;
                case 1:
                    moveHead.moveRight();
                    break;
                case 2:
                    moveHead.moveFront();
                    break;
                case 3:
                    moveHead.moveBack();
                    break;
                case 4:
                    moveHead.moveUp();
                    break;
                case 5:
                    moveHead.moveDown();
                    break;
                case 6:
                    moveHead.moveHome();
                    break;
                default:
                    break;
            }

            //TODO: get the voice recognizer to re-initialize after moving the head once

            //TODO: [BUG] Recognizer stops working after several seconds of no audio input
            if (index != 7) {
                text = "";
                initializeSpeech(this.context);
            }
            else {
                kill();
            }

            //TODO: Get position and file name ---------------------------------------------------
        } else if (this.context instanceof com.varvet.barcodereadersample.printActivity) {
            ArrayList<String> items = new ArrayList<String>(Arrays.asList("Print","Stop"));
            int index = GetIndex.getIndex(text,items);

            final JobCommand job = new JobCommand(MainActivity.octoPrint);
            final PrinterCommand printerState = new PrinterCommand(MainActivity.octoPrint);

            String response = "";

            printActivityVoice obj = new printActivityVoice();

            switch (index) {
                case 0:
                    response = obj.print(printerState);
                    Toast.makeText(this.context,response,Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    response = obj.stop(job, printerState);
                    Toast.makeText(this.context,response,Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }

            kill();
            initializeSpeech(this.context);

        } else {
            Toast.makeText(this.context,"WHAT ARE YOU DOING HERE?!?!", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        Log.i(LOG_TAG, "onPartialResults");
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        Log.i(LOG_TAG, "onEvent");
    }

    private static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }
}
