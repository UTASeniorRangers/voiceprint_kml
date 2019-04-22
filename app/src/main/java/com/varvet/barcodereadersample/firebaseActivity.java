package com.varvet.barcodereadersample;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.varvet.barcodereadersample.MainActivity;

import java.util.ArrayList;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class firebaseActivity extends AppCompatActivity {

    Button connect_g;
    Button returnBt;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    StorageReference ref;
    EditText f_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_view);

        connect_g = findViewById(R.id.connect_g);
        returnBt = findViewById(R.id.ret);
        f_name = (EditText) findViewById(R.id.f_name);

        connect_g.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                download();
            }

        });

        returnBt.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Intent return_act = new Intent(firebaseActivity.this, MainActivity.class);
                startActivity(return_act);
            }
        });

    }

    public void download() {

        String name = "";
        String[] file_name = {
                "Hi",
                "Hello"
        };

        storageReference = firebaseStorage.getInstance().getReference();
        String cont = f_name.getText().toString();
        int content = Integer.parseInt(cont);

        for(int i = 0; i < file_name.length; i++) {

            if (i == content) {
                name = file_name[i];
                break;
            }

        }

        final String name1 = name;
        final String name2 = name1 + ".gcode";

        ref = storageReference.child(name2);
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                downloadfile(firebaseActivity.this,name1 , ".gcode", DIRECTORY_DOWNLOADS, url);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void downloadfile(Context context, String filename, String file_extension, String dest_directory, String url) {

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, dest_directory, filename + file_extension);

        downloadManager.enqueue(request);
        Toast.makeText(getApplicationContext(), filename + " is being downloaded", Toast.LENGTH_SHORT).show();

    }

}
