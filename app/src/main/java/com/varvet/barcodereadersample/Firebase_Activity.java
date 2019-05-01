package com.varvet.barcodereadersample;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;
import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class Firebase_Activity extends Activity {

    //Recognizer voice = Recognizer.getInstance();
    Button connect_g;
    Button returnBt;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    StorageReference ref;
    FirebaseAuth mAuth;
    EditText f_name;
    ListView listView;
    ArrayList<String> listItems=new ArrayList<String>();
    DownloadManager downloadManager;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_view);
        //Listening();
        mAuth = FirebaseAuth.getInstance();
        connect_g = findViewById(R.id.connect_g);
        returnBt = findViewById(R.id.ret);
        f_name = (EditText) findViewById(R.id.f_name);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        listItems.add("[0]  CFDMP_F1.gcode                22.48MB");
        adapter.notifyDataSetChanged();
        listItems.add("[1]  CFDMP_Tiny_Tester.gcode        1.13MB");
        adapter.notifyDataSetChanged();
        listItems.add("[2]  MMD_golf_ball.gcode          782.63KB");
        adapter.notifyDataSetChanged();
        listItems.add("[3]  MMD_Space_Shuttle.gcode      223.34KB");
        adapter.notifyDataSetChanged();
        listItems.add("[4]  MMD_Yoshi.gcode                1.67MB");
        adapter.notifyDataSetChanged();
        listItems.add("[5]  pyramid.gcode                297.62KB");
        adapter.notifyDataSetChanged();
        listItems.add("[6]  UTA_coaster.gcode            965.62KB");
        adapter.notifyDataSetChanged();
        listView = (ListView) findViewById(R.id.lis);
        listView.setAdapter(adapter);
        listView.setScrollContainer(false);

        connect_g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                download();
            }
        });

        returnBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent return_act = new Intent(Firebase_Activity.this, fileChooserActivity.class);
                startActivity(return_act);
            }
        });

    }

    public void download() {

        storageReference = firebaseStorage.getInstance().getReference();
        int num=Integer.parseInt(f_name.getText().toString());
        String [] list_fn = {"CFDMP_F1", "CFDMP_Tiny_Tester", "MMD_golf_ball", "MMD_Space_Shuttle","MMD_Yoshi", "pyramid", "UTA_coaster" };
        String name = "";
        for (int i = 0; i < list_fn.length; i++) {
            if (num == i) {
                name = list_fn[i];
                break;
            }
        }
        final String name1 = name;
            ref = storageReference.child(name + ".gcode");
            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    String url = uri.toString();
                    downloadfile(Firebase_Activity.this, name1, ".gcode", DIRECTORY_DOWNLOADS, url);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Fail to download", Toast.LENGTH_SHORT).show();
                }
            });
        }

    public void downloadfile(Context context, String filename, String file_extension, String dest_directory, String url) {

        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, dest_directory, filename + file_extension);
        downloadManager.enqueue(request);
        Toast.makeText(getApplicationContext(), filename + " is being downloaded", Toast.LENGTH_SHORT).show();

    }

    //public void Listening() {
    //    voice.initializeSpeech(this);
    //}

}
