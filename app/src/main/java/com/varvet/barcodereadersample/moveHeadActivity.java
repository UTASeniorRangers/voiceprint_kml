package com.varvet.barcodereadersample;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import org.octoprint.api.PrinterCommand;
import org.octoprint.api.model.Axis;


public class moveHeadActivity extends AppCompatActivity {

    Recognizer voice = Recognizer.getInstance();
    public String x_axis;
    public String y_axis;
    public String z_axis;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.move_head_activity);
        setActionBar("Move Printer Arm");
        Listening();

        ImageButton back = (ImageButton) findViewById(R.id.backHeadArrow);
        ImageButton front = (ImageButton) findViewById(R.id.frontHeadArrow);
        ImageButton left = (ImageButton) findViewById(R.id.leftHeadArrow);
        ImageButton right = (ImageButton) findViewById(R.id.rightHeadArrow);
        ImageButton up = (ImageButton) findViewById(R.id.upHeadArrow);
        ImageButton down = (ImageButton) findViewById(R.id.downHeadArrow);
        ImageButton home1 = (ImageButton) findViewById(R.id.homeHeadBtn);
        ImageButton home2 = (ImageButton) findViewById(R.id.homeHeadBtn2);

        TextView X = findViewById(R.id.x_axis);
        TextView Y = findViewById(R.id.y_axis);
        TextView Z = findViewById(R.id.z_axis);

        x_axis = X.getText().toString();
        y_axis = Y.getText().toString();
        z_axis = Z.getText().toString();

//        X.addTextChangedListener(connTextWatcher);
//        Y.addTextChangedListener(connTextWatcher);
//        Z.addTextChangedListener(connTextWatcher);

        final PrinterCommand printer = new PrinterCommand(MainActivity.octoPrint);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printer.moveOnAxis(Axis.getAxis("y"),Double.parseDouble(y_axis));
            }
        });

        front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printer.moveOnAxis(Axis.getAxis("y"),-Double.parseDouble(y_axis));
            }
        });

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printer.moveOnAxis(Axis.getAxis("x"),-Double.parseDouble(x_axis));
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printer.moveOnAxis(Axis.getAxis("x"),Double.parseDouble(x_axis));
            }
        });

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printer.moveOnAxis(Axis.getAxis("z"),Double.parseDouble(z_axis));
            }
        });

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printer.moveOnAxis(Axis.getAxis("z"),-Double.parseDouble(z_axis));
            }
        });

        home1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printer.moveHome();
            }
        });

        home2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printer.moveHome();
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
        voice.kill();
        Intent intent = new Intent(moveHeadActivity.this,moveHeadActivity.class);
        startActivity(intent);
        voice.kill();
    }

    public void ReverseButton(MenuItem item) {
        voice.kill();
        Intent intent = new Intent(moveHeadActivity.this,selectFileActivity.class);
        voice.kill();
        startActivity(intent);
    }

    public void HomeButton(MenuItem item) {
        Intent intent = new Intent(moveHeadActivity.this, homeActivity.class);
        startActivity(intent);
        voice.kill();
    }

    public void Listening() {
        voice.initializeSpeech(this);
    }


    public TextWatcher connTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            x_axis = X.getText().toString().trim();
//            y_axis = Y.getText().toString().trim();
//            z_axis = Z.getText().toString().trim();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };



}
