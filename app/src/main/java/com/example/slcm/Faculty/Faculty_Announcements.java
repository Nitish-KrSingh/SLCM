package com.example.slcm.Faculty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.slcm.R;

public class Faculty_Announcements extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_announcements);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Announcements");
    }
}