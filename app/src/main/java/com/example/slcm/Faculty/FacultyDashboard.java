package com.example.slcm.Faculty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.slcm.DatabaseManager;
import com.example.slcm.FacultyLogin;
import com.example.slcm.R;
import com.example.slcm.Student.StudentAnnouncements;
import com.example.slcm.Student.StudentAttendance;
import com.example.slcm.Student.StudentDashboard;
import com.example.slcm.Student.StudentFeesActivity;
import com.example.slcm.Student.StudentInternalMarks;
import com.example.slcm.Student.StudentMarksheet;
import com.example.slcm.Student.StudentProfileDashboard;
import com.example.slcm.Student.StudentTimetable;
import com.example.slcm.StudentLogin;

import java.util.ArrayList;
import java.util.Objects;

public class FacultyDashboard extends AppCompatActivity {

    Button fac_announcement_btn,fac_timetable_btn,fac_attendance_btn,fac_marks_btn;

    private ListView faculty_dashboard_announcement_ListView;
    private ArrayList<String> faculty_dashboard_announcement_List;
    private ArrayAdapter<String> adapter;
    private int loggedInFacultyId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_dashboard);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Faculty Dashboard");

        fac_announcement_btn=(Button) findViewById(R.id.btnAnnouncement);
        fac_timetable_btn=(Button) findViewById(R.id.btnTimeTable);
        fac_attendance_btn=(Button) findViewById(R.id.btnAttendance);
        fac_marks_btn=(Button) findViewById(R.id.btnMarks);
        SharedPreferences sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE);
        loggedInFacultyId = sharedPreferences.getInt("FACULTY_ID", -1); // Get the FacultyID

        if (loggedInFacultyId == -1) {
            Log.d("DebugTag", "loggedInFacultyId is invalid.");
            Toast.makeText(this, "Faculty ID not found.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, FacultyLogin.class);
            startActivity(intent);
            finish();
        } else {
            faculty_dashboard_announcement_ListView = findViewById(R.id.fac_dashboard_announcement_ListView);
            faculty_dashboard_announcement_List = new ArrayList<>();
            adapter = new ArrayAdapter<String>(this, R.layout.activity_faculty_dashboard_announcement_list_item, R.id.listItemButton, faculty_dashboard_announcement_List);
            faculty_dashboard_announcement_ListView.setAdapter(adapter);
            retrieveAnnouncementForFacultyDashboard();
        }


        fac_announcement_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fac_announcement_intent=new Intent(FacultyDashboard.this,FacultyAnnouncements.class);
                startActivity(fac_announcement_intent);
            }
        });

        fac_timetable_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fac_timetable_intent=new Intent(FacultyDashboard.this,FacultyTimetable.class);
                startActivity(fac_timetable_intent);
            }
        });


        fac_attendance_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fac_attendance_intent=new Intent(FacultyDashboard.this,FacultyAttendance.class);
                startActivity(fac_attendance_intent);
            }
        });

        fac_marks_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fac_marks_intent=new Intent(FacultyDashboard.this,FacultyMarksClass.class);
                startActivity(fac_marks_intent);
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

    private void retrieveAnnouncementForFacultyDashboard() {
        DatabaseManager databaseManager = new DatabaseManager(this);
        Cursor cursor = databaseManager.getAnnouncementForFacultyDashboard();

        if (cursor != null) {
            int titleIndex = cursor.getColumnIndex("Title");
            int messageIndex = cursor.getColumnIndex("Message");
            int dateIndex = cursor.getColumnIndex("Date");

            if (titleIndex == -1 || messageIndex == -1 || dateIndex == -1) {
                Log.e("CursorError", "One or more columns not found in cursor");
                Toast.makeText(this, "One or more columns not found in cursor", Toast.LENGTH_SHORT).show();
            } else {
                while (cursor.moveToNext()) {
                    String title = cursor.getString(titleIndex);
                    String message = cursor.getString(messageIndex);
                    String date = cursor.getString(dateIndex);


                    String announcement = title + ": " + message + " (" + date + ")";


                    faculty_dashboard_announcement_List.add(announcement);

                    Log.d("DebugTag", "Added announcement: " + announcement);
                }

                adapter.notifyDataSetChanged();
                cursor.close();
            }
        } else {
            Log.d("DebugTag", "Cursor is null.");
        }
    }
}