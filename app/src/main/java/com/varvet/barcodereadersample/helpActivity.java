package com.varvet.barcodereadersample;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class helpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_activity);
        setActionBar("Help");

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

    }

    public void ReverseButton(MenuItem item) {
        Intent intent = new Intent(helpActivity.this,fileChooserActivity.class);
        startActivity(intent);
    }

    public void HomeButton(MenuItem item) {
        Intent intent = new Intent (helpActivity.this,homeActivity.class);
        startActivity(intent);
    }
}
