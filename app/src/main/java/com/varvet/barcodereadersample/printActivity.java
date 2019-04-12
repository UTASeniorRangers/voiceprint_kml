package com.varvet.barcodereadersample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class printActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.print_activity);

        TextView selectedFile = findViewById(R.id.selectCardviewFile);

    }
}
