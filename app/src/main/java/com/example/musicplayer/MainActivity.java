package com.example.musicplayer;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toolbar;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set the tool bar and title
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.app_name));
    }
}