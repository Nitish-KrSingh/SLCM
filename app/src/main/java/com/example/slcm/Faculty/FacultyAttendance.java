package com.example.slcm.Faculty;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.slcm.R;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class FacultyAttendance extends AppCompatActivity {

private CustomFacultyListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_attendance);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Enter Attendance");

        ListView listView = findViewById(R.id.Fac_Stud_Att_List); // Use ListView for your list

        // Sample list of roll numbers
        String[] rollNumbers = {"220970030", "220970031", "220970032"};

        // Initialize the custom adapter
        adapter = new CustomFacultyListAdapter(this, rollNumbers);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.faculty_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FacultyMenuHandler.handleMenuAction(item, this);
        return super.onOptionsItemSelected(item);
    }
}