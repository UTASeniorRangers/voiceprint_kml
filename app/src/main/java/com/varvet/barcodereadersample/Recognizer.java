package com.varvet.barcodereadersample;

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


public class Recognizer implements RecognitionListener {

    //Possible static leak
    private static Recognizer instance;
    public String text = "";

    private SpeechRecognizer speech = null;
    private String LOG_TAG = "VoiceRecognitionActivity";
    private Context context;

   // private ProgressBar progressBar;
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

        //progressBar = ((Activity)context).findViewById(R.id.progressBar);
        //output = ((Activity)context).findViewById(R.id.output);

        speech = SpeechRecognizer.createSpeechRecognizer(context);
        speech.setRecognitionListener(this);
        Intent recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,"en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,context.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,context.getPackageName());

        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3); //Unneeded

       // progressBar.setVisibility(View.VISIBLE);
       // progressBar.setIndeterminate(true);

        speech.startListening(recognizerIntent);
        Toast.makeText(this.context,"speech initialization is done", Toast.LENGTH_SHORT).show();
    }

    public void kill() {
        speech.destroy();
    }

    @Override
    public void onReadyForSpeech(Bundle params) {

    }

    @Override
    public void onBeginningOfSpeech() {
        Toast.makeText(this.context,"speech beginning", Toast.LENGTH_SHORT).show();
        Log.i(LOG_TAG, "onBeginningOfSpeech");
       // progressBar.setIndeterminate(false);
       // progressBar.setMax(10);

    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);
       // progressBar.setProgress((int) rmsdB);

    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(LOG_TAG, "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {
        Toast.makeText(this.context,"speech ended", Toast.LENGTH_SHORT).show();
        Log.i(LOG_TAG, "onEndOfSpeech");
     //   progressBar.setIndeterminate(false);
       // progressBar.setVisibility(View.INVISIBLE);
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
        Toast.makeText(this.context,"text detected: "+text, Toast.LENGTH_SHORT).show();

        //TODO: Put into another method
        //TODO: [FIX] CAUSES 119 Skipped frames, The application may be doing too much work on its main thread. Put in Async task?
        //TODO: Awful implementation - use getLayout() method from Navigation class

        if (this.context instanceof com.varvet.barcodereadersample.moveHeadActivity) {

            ArrayList<String> items = new ArrayList<String>(Arrays.asList("Left","Right","Front","Back","Up","Down","Home","Quit"));
            int index = GetIndex.getIndex(text,items);

            switch(index){
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
            if (index != 7) {
                text = "";
                initializeSpeech(this.context);
            }
            else {
                kill();
            }
        }




        /* if (this.context instanceof com.rangers.voiceprint.Setters.Set_Precision) {
//            TextView precision = ((Activity)context).findViewById(R.id.precision_text);
            output.setText(text);

            object.setPrecision(text);

            //TODO: Toast message to display set parameter
            Toast.makeText(this.context, "PRECISION SET TO: " + text, Toast.LENGTH_SHORT).show();

            //Go to next activity
            Intent set_infill = new Intent(context, com.rangers.voiceprint.Setters.Set_Infill.class);
            context.startActivity(set_infill);
        }
        else if (this.context instanceof com.rangers.voiceprint.Setters.Set_Infill) {
//            TextView infill = ((Activity)context).findViewById(R.id.infill_text);
            output.setText(text);

            object.setInfill(text);

            Toast.makeText(this.context, "INFILL SET TO: " + text, Toast.LENGTH_SHORT).show();

            Intent set_support = new Intent(context, Set_Support.class);
            context.startActivity(set_support);
        }
        else if (this.context instanceof com.rangers.voiceprint.Setters.Set_Support) {
//            TextView infill = ((Activity)context).findViewById(R.id.infill_text);
            output.setText(text);

            object.setSupport(text);

            Toast.makeText(this.context, "SUPPORT SET TO: " + text, Toast.LENGTH_SHORT).show();

            Intent set_raft = new Intent(context, Set_Raft.class);
            context.startActivity(set_raft);
        }
        else if (this.context instanceof com.rangers.voiceprint.Setters.Set_Raft) {
//            TextView infill = ((Activity)context).findViewById(R.id.infill_text);
            output.setText(text);

            object.setRaft(text);

            Toast.makeText(this.context, "RAFT SET TO: " + text, Toast.LENGTH_SHORT).show();

            Intent confirm = new Intent(context, Confirm.class);
            confirm.putExtra("Instance", object);
            context.startActivity(confirm);
        }
        else {
            Log.e("ON RESULT", "CONTEXT NOT FOUND");
        } */

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
