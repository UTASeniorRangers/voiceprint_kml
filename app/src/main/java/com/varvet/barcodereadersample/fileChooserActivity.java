package com.varvet.barcodereadersample;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.net.URI;

public class fileChooserActivity extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST = 45;
    public static final String EXTRA_DEVICE_SELECTED_FILE = "whichFile";

    public static Uri mfileUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_chooser_activity);
        setActionBar("UPLOAD FILE");

        final Button mOctoprintButton = findViewById(R.id.octoprint);
        final Button mDevice = findViewById(R.id.device);
        final Button mSDcard = findViewById(R.id.sdcard);

        mOctoprintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(fileChooserActivity.this, selectFileActivity.class);
                startActivity(intent);
            }
        });

        mDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDevice();
            }
        });

    }

    private void openDevice(){
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            mfileUri = data.getData();
            Cursor returnCursor = getContentResolver().query(mfileUri,null,null,null,null);
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            returnCursor.moveToFirst();
            //System.out.println("device file: " + returnCursor.getString(nameIndex));
            Intent intent = new Intent(fileChooserActivity.this,printActivity.class);
            Toast.makeText(fileChooserActivity.this, "PICKED: %s " + returnCursor.getString(nameIndex), Toast.LENGTH_SHORT).show();
            intent.putExtra(EXTRA_DEVICE_SELECTED_FILE,returnCursor.getString(nameIndex));

            startActivity(intent);
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

    public void ForwardButton(MenuItem item) {
        Intent intent = new Intent(fileChooserActivity.this,moveHeadActivity.class);
        startActivity(intent);
    }

    public void ReverseButton(MenuItem item) {
        Intent intent = new Intent(fileChooserActivity.this,settingsActivity.class);
        startActivity(intent);
    }

    public void HomeButton(MenuItem item) {
        Intent intent = new Intent (fileChooserActivity.this,homeActivity.class);
        startActivity(intent);
    }

}
