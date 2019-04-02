package com.varvet.barcodereadersample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.octoprint.api.FileCommand;
import org.octoprint.api.model.OctoPrintFileInformation;

import java.util.ArrayList;
import java.util.List;

public class selectFileActivity extends AppCompatActivity {


    public RecyclerView recyclerView;
    ListAdapterWithRecyclerView listAdapterWithRecyclerView;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_activity_recycler_view);


        FileCommand allFiles = new FileCommand(MainActivity.octoPrint);
        List<OctoPrintFileInformation> Files = allFiles.listFiles();

        recyclerView = (RecyclerView) findViewById(R.id.recycleListView);
        listAdapterWithRecyclerView = new ListAdapterWithRecyclerView(this, Files);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listAdapterWithRecyclerView);

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(selectFileActivity.this,printActivity.class));

            }
        });


    }

    public void ForwardButton(MenuItem item) {
        Intent intent = new Intent(selectFileActivity.this,moveHeadActivity.class);
        startActivity(intent);
    }

    public void ReverseButton(MenuItem item) {
        Intent intent = new Intent(selectFileActivity.this,settingsActivity.class);
        startActivity(intent);
    }

    public void HomeButton(MenuItem item) {
        Intent intent = new Intent (selectFileActivity.this,homeActivity.class);
        startActivity(intent);
    }


}
