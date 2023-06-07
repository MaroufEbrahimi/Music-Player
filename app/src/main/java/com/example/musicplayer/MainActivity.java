package com.example.musicplayer;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.google.android.material.internal.TouchObserverFrameLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    // members
    RecyclerView recyclerView;
    SongAdapter songAdapter;
    List<Song> allSongs = new ArrayList<>();
    ActivityResultLauncher<String> storagePermissionLauncher;
    //final String permission = Manifest.permission.READ_EXTERNAL_STORAGE;
    final String permission = Manifest.permission.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set the tool bar and title
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.app_name));

        // recyclerview
        recyclerView = findViewById(R.id.recyclerview);
        storagePermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {
            if (granted) {
                //fetch songs
                fetchSongs();
            } else {
                userResponses();
            }
        });

        //lunch storage permission on create
        storagePermissionLauncher.launch(permission);
    }

    private void userResponses() {
        if(ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED){
            //fetch songs
            fetchSongs();
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(shouldShowRequestPermissionRationale(permission)){
                // show an educational UI user explaining why need this permission
                // use alert dialog
                new AlertDialog.Builder(this)
                        .setTitle("Requesting Permission")
                        .setMessage("Allow us to fetch songs")
                        .setPositiveButton("allow", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                storagePermissionLauncher.launch(permission);
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getApplicationContext(), "You denied us to show songs", Toast.LENGTH_SHORT).show();
                                dialogInterface.dismiss();
                            }
                        }).show();
            }
        }else{
            Toast.makeText(this, "You canceled to show songs", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchSongs() {
        List<Song> songs = new ArrayList<>();
        Uri mediaSearchUri;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            mediaSearchUri = MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        }else {
            mediaSearchUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        }

        //define projection
        String[] projection = new String[]{
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.SIZE,
                MediaStore.Audio.Media.ALBUM_ID,
        };

        String sortOrder = MediaStore.Audio.Media.DATE_ADDED + "DESC";

        // GET THE SONGS
        try(Cursor cursor = getContentResolver().query(mediaSearchUri, projection, null, null, sortOrder)){
            int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
            int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
            int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
            int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE);
            int albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID);
        }
    }

}