package com.example.slcm.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.example.slcm.Faculty.FacultyAnnouncements;
import com.example.slcm.Faculty.FacultyDashboard;
import com.example.slcm.R;


import java.util.ArrayList;
import java.util.Objects;

public class StudentDashboard extends AppCompatActivity {

    private ListView student_dashboard_announcement_ListView;
    private ArrayList<String> student_dashboard_announcement_List;
    private ArrayAdapter<String> adapter;
    Button stud_announcement_btn,stud_timetable_btn,stud_attendance_btn,stud_mark_sheet_btn,stud_internal_mark_btn,stud_fees_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Student Dashboard");


        stud_announcement_btn=(Button) findViewById(R.id.BtnAnnouncement);
        stud_timetable_btn=(Button) findViewById(R.id.BtnTimeTable);
        stud_attendance_btn=(Button) findViewById(R.id.BtnAttendance);
        stud_mark_sheet_btn=(Button) findViewById(R.id.BtnMarkSheet);
        stud_internal_mark_btn=(Button) findViewById(R.id.BtnInternalMarks);
        stud_fees_btn=(Button) findViewById(R.id.BtnFees);

        student_dashboard_announcement_ListView = findViewById(R.id.stud_dashboard_announcement_ListView);
        student_dashboard_announcement_List = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.activity_student_announcement_list_item, R.id.listItemButton, student_dashboard_announcement_List);
        student_dashboard_announcement_ListView.setAdapter(adapter);
        retrieveDashboardAnnouncementForStudent();

        stud_announcement_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stud_announcement_intent=new Intent(StudentDashboard.this, StudentAnnouncements.class);
                startActivity(stud_announcement_intent);
            }
        });


        stud_timetable_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stud_timetable_intent=new Intent(StudentDashboard.this, StudentTimetable.class);
                startActivity(stud_timetable_intent);
            }
        });

        stud_attendance_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stud_attendance_intent=new Intent(StudentDashboard.this, StudentAttendance.class);
                startActivity(stud_attendance_intent);
            }
        });

        stud_mark_sheet_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stud_mark_sheet_intent=new Intent(StudentDashboard.this, StudentMarksheet.class);
                startActivity(stud_mark_sheet_intent);
            }
        });

        stud_internal_mark_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stud_internal_mark_intent=new Intent(StudentDashboard.this, StudentInternalMarks.class);
                startActivity(stud_internal_mark_intent);
            }
        });

        stud_fees_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stud_fees_intent=new Intent(StudentDashboard.this, StudentFeesActivity.class);
                startActivity(stud_fees_intent);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.student_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        StudentMenuHandler.handleMenuAction(item, this);
        return super.onOptionsItemSelected(item);
    }

    private void retrieveDashboardAnnouncementForStudent() {
        DatabaseManager databaseManager = new DatabaseManager(this);
        Cursor cursor = databaseManager.getAnnouncementForStudent();

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


                    student_dashboard_announcement_List.add(announcement);

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