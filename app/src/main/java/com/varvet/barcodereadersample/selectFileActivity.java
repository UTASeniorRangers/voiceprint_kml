package com.varvet.barcodereadersample;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.varvet.barcodereadersample.printActivity;
import org.octoprint.api.FileCommand;
import org.octoprint.api.model.OctoPrintFileInformation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static com.varvet.barcodereadersample.selectFileActivity.PREFS_NAME;

public class selectFileActivity extends AppCompatActivity implements ListAdapterWithRecyclerView.OnItemClickListener {

    public static final String EXTRA_FILE_NAME = "fileName";
    public static final String EXTRA_POSITION = "filePosition";

    public static final String PREFS_NAME = "File";

    public RecyclerView recyclerView;
    ListAdapterWithRecyclerView listAdapterWithRecyclerView;
    static FileCommand allFiles = new FileCommand(MainActivity.octoPrint);
    static List<OctoPrintFileInformation> Files = allFiles.listFiles();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_activity_recycler_view);
        setActionBar("OctoPrint Storage");


        recyclerView = (RecyclerView) findViewById(R.id.recycleListView);
        listAdapterWithRecyclerView = new ListAdapterWithRecyclerView(this, Files);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listAdapterWithRecyclerView);

        listAdapterWithRecyclerView.setOnItemClickListener(selectFileActivity.this);
//
//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // attaching bottom sheet behaviour - hide / show on scroll
//        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
//        layoutParams.setBehavior(new BottomNavigationBehavior());
    }

    @Override
    public void OnItemClick(int position) {
        Intent intent = new Intent(selectFileActivity.this, printActivity.class);

        String fileName = allFiles.listFiles().get(position).getName();

        printData.setFileName(fileName);

        intent.putExtra(EXTRA_FILE_NAME, fileName);
        intent.putExtra(EXTRA_POSITION, position);

        System.out.println("Heyyyyyyyy thats working ......."+allFiles.listFiles().get(position).getName());

        startActivity(intent);

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
       // Intent intent = new Intent(selectFileActivity.this,moveHeadActivity.class);
       // startActivity(intent);
    }

    public void ReverseButton(MenuItem item) {
        Intent intent = new Intent(selectFileActivity.this,fileChooserActivity.class);
        startActivity(intent);
    }

    public void HomeButton(MenuItem item) {
        Intent intent = new Intent (selectFileActivity.this,homeActivity.class);
        startActivity(intent);
    }


}
