package com.example.slcm;


import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class AttendanceActivity extends AppCompatActivity {

    private ListView listView;
    private CustomListAdapter adapter; // Use your custom adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        listView = findViewById(R.id.students);

        // Sample list of roll numbers
        String[] rollNumbers = {"220970030", "220970031", "220970032"};

        // Initialize the custom adapter
        adapter = new CustomListAdapter(this, rollNumbers);
        listView.setAdapter(adapter);
    }
}