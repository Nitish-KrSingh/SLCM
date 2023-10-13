package com.example.slcm.Faculty;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.slcm.DatabaseManager;
import com.example.slcm.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FacultyAttendanceClass extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_attendance_class);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Attendance - Select Class");

        DatabaseManager db = new DatabaseManager(this);

        String selectedDate = getIntent().getStringExtra("selectedDate");

        SharedPreferences sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE);
        String fac_name = sharedPreferences.getString("LOGIN_USER","");


        List<String> assignedClasses = db.getClassesAssignedToFaculty(fac_name);



        ArrayAdapter<String> classAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, assignedClasses);
        ListView classListView = findViewById(R.id.classListView);
        classListView.setAdapter(classAdapter);


        classListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedClass = assignedClasses.get(position);
                Intent intent = new Intent(FacultyAttendanceClass.this , FacultyAttendanceSubject.class);
                intent.putExtra("class_name" , selectedClass);
                intent.putExtra("s_date" , selectedDate);

                startActivity(intent);
            }
        });



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