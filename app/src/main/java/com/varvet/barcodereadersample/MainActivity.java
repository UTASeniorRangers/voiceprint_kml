package com.varvet.barcodereadersample;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.AlertDialog;
import android.widget.ListView;


import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.varvet.barcodereadersample.barcode.BarcodeCaptureActivity;

import org.octoprint.api.OctoPrintInstance;
import org.octoprint.api.PrinterCommand;

import java.net.MalformedURLException;
import java.nio.file.InvalidPathException;


// branch "voice" created off of master
public class MainActivity extends AppCompatActivity {

    private static final int BARCODE_READER_REQUEST_CODE = 1;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private TextView mResultTextView = null;
    private TextInputLayout layout;
    private TextInputEditText IP_add = null;
    public static Button connBtn;

    public static void setConnBtn(Button connBtn) {
        connBtn.setClickable(false);
    }

    public Button resumeBtn;
    public static String savedAPI = null;
    public static String IP = null;
    RadioButton saveApiButton;
    RadioButton saveIPButton;
    public static OctoPrintInstance octoPrint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        doPermAudio();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        mResultTextView = findViewById(R.id.result_textview);
        Button btn = (Button) findViewById(R.id.scan_barcode_button);
        connBtn = (Button) findViewById(R.id.connect);
        resumeBtn = (Button) findViewById(R.id.resumeButton);
        saveApiButton = (RadioButton) findViewById(R.id.saveButton);
        saveApiButton.setEnabled(false);
        saveIPButton = (RadioButton) findViewById(R.id.saveIPButton1);
        saveIPButton.setEnabled(false);
        resumeBtn.setEnabled(false);

//        connBtn.addTextChangedListener(connTextWatcher);

//        if(IP == null && savedAPI == null)
//        {
//            resumeBtn.setClickable(false);
//            //resumeBtn.setEnabled(false);
//        }
//        else
//        {
//            resumeBtn.setClickable(true);
//            //resumeBtn.setEnabled(true);
//        }

        resumeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    octoPrint = new OctoPrintInstance(IP, 80, savedAPI);
                    PrinterCommand printer = new PrinterCommand(octoPrint);

                    // getCurrentState should return "Operational" or "Closed" and isReady should return true
                    System.out.println("printer current state is: ");

                    boolean state1 = printer.getCurrentState().isPrinting();
                    boolean state2 = printer.getCurrentState().isReady();
                    boolean state3 = printer.getCurrentState().isOperational();
                    boolean state4 = printer.getCurrentState().isPaused();

                    if (state1 || state2 || state3 || state4) // returns "true"
                    {
                        //printer.sendExtruderCommand(PrinterCommand.ToolCommand.TARGET_TEMP,0,80);
                        getDialog(R.string.dialog_title, R.string.dialog_message, R.string.CANCEL, R.string.OK);
                    } else {
                        //when input (API or IP) is wrong, display alert
                        getDialog(R.string.dialog_title, R.string.dialog_message1, R.string.OK, R.string.dummy);
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



        connBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    try {

                        octoPrint = new OctoPrintInstance(IP, 80, savedAPI);
                        PrinterCommand printer = new PrinterCommand(octoPrint);

                        // getCurrentState should return "Operational" or "Closed" and isReady should return true
                        System.out.println("printer current state: ");

                        boolean state1 = printer.getCurrentState().isPrinting();
                        boolean state2 = printer.getCurrentState().isReady();
                        boolean state3 = printer.getCurrentState().isOperational();
                        boolean state4 = printer.getCurrentState().isPaused();


                        if (state1 || state2 || state3 || state4) // returns "true"
                        {
                            //printer.sendExtruderCommand(PrinterCommand.ToolCommand.TARGET_TEMP,0,80);
                            getDialog(R.string.dialog_title, R.string.dialog_message, R.string.CANCEL, R.string.OK);
                            resumeBtn.setEnabled(true);
                        } else {
                            //when input (API or IP) are invalid, display alert
                            getDialog(R.string.dialog_title, R.string.dialog_message1, R.string.OK, R.string.dummy);
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                connBtn.setEnabled(false);
            }
        });


        //Barcode Scan Button
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this.getApplicationContext(), BarcodeCaptureActivity.class);
                MainActivity.this.startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
                saveApiButton.setChecked(false);

            }
        });

        //Save API radio button
        saveApiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connBtn.setEnabled(true);
                resumeBtn.setEnabled(false);
                savedAPI = mResultTextView.getText().toString();
                Toast.makeText(MainActivity.this, savedAPI, Toast.LENGTH_SHORT).show();
            }
        });


        IP_add = findViewById(R.id.IP_input);

        //TextWatcher is not working for saveIPButton
        IP_add.addTextChangedListener(connTextWatcher);

        saveIPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connBtn.setEnabled(true);
                resumeBtn.setEnabled(false);
                IP = IP_add.getText().toString();
                Toast.makeText(MainActivity.this, IP, Toast.LENGTH_SHORT).show();
            }
        });



    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == BARCODE_READER_REQUEST_CODE)
        {
            if (resultCode == 0)
            {
                if (data != null)
                {
                    Barcode barcode = (Barcode) data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    mResultTextView.setText(barcode.displayValue);
                    saveApiButton.setEnabled(true);
                }
                else {
                    mResultTextView.setText(getString(R.string.no_barcode_captured));
                    saveApiButton.setEnabled(false);
                }

            } else {
                Log.e(LOG_TAG, String.format(getString(R.string.barcode_error_format),
                        CommonStatusCodes.getStatusCodeString(resultCode)));
                saveApiButton.setEnabled(false);
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }



    public TextWatcher connTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            saveIPButton.setEnabled(true);
            saveIPButton.setChecked(false);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    public void getDialog(int title, int message, int button1, int button2)
    {
        // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);


        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(message);
        builder.setTitle(title);

        builder.setPositiveButton(button2, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(MainActivity.this,homeActivity.class);
                startActivity(intent);

            }
        });

        builder.setNegativeButton(button1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        // 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
        AlertDialog dialog = builder.create();
//        ListView setDiv = dialog.getListView();
//        setDiv.setDivider(new ColorDrawable(Color.BLUE));
//        setDiv.setDividerHeight(2);
        dialog.show();
    }

    //Method used to get permission
    void doPermAudio()
    {
        int MY_PERMISSIONS_RECORD_AUDIO = 1;
        MainActivity thisActivity = this;

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(thisActivity,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    MY_PERMISSIONS_RECORD_AUDIO);
        }
    }

}
//-end-

