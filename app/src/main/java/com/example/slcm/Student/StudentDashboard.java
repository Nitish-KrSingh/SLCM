package com.example.slcm.Student;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.slcm.DatabaseManager;
import com.example.slcm.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class StudentDashboard extends AppCompatActivity {

    Button stud_announcement_btn, stud_timetable_btn, stud_attendance_btn, stud_mark_sheet_btn, stud_internal_mark_btn, stud_fees_btn;
    private ArrayList<String> student_dashboard_announcement_List;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Student Dashboard");

        stud_announcement_btn = findViewById(R.id.BtnAnnouncement);
        stud_timetable_btn = findViewById(R.id.BtnTimeTable);
        stud_attendance_btn = findViewById(R.id.BtnAttendance);
        stud_mark_sheet_btn = findViewById(R.id.BtnMarkSheet);
        stud_internal_mark_btn = findViewById(R.id.BtnInternalMarks);
        stud_fees_btn = findViewById(R.id.BtnFees);
        TextView name = findViewById(R.id.name);
        SharedPreferences sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE);
        int loggedInStudentId = sharedPreferences.getInt("STUDENT_ID", -1);
        DatabaseManager dbHelper = new DatabaseManager(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor nameCursor = db.rawQuery("SELECT Name FROM StudentProfile WHERE StudentID = ?",  new String[]{String.valueOf(loggedInStudentId)});
        String studentName = "";
        if (nameCursor.moveToFirst()) {
            studentName = nameCursor.getString(nameCursor.getColumnIndexOrThrow("Name"));
        }
       name.setText("Hello "+studentName+"!");
        ListView student_dashboard_announcement_ListView = findViewById(R.id.ann_ListView);
        student_dashboard_announcement_List = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.activity_faculty_announcement_list_item, R.id.listItemButton, student_dashboard_announcement_List);
        student_dashboard_announcement_ListView.setAdapter(adapter);
        retrieveDashboardAnnouncementForStudent();
        student_dashboard_announcement_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedAnnouncement = student_dashboard_announcement_List.get(position);
                String[] splitAnnouncement = selectedAnnouncement.split("\nDate:");
                String title = splitAnnouncement[0];
                String date = splitAnnouncement[1];
                Log.d("DebugTag", "Date: " + date + "Title" + title);

                DatabaseManager databaseManager = new DatabaseManager(StudentDashboard.this);
                String message = databaseManager.getAnnouncementMessage(title, date);

                if (message != null) {
                    Intent intent = new Intent(StudentDashboard.this, StudentViewAnnouncement.class);
                    intent.putExtra("title", title);
                    intent.putExtra("date", date);
                    intent.putExtra("message", message);
                    startActivity(intent);
                } else {
                    Log.d("DebugTag", "No message found.");
                }
            }
        });

        stud_announcement_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stud_announcement_intent = new Intent(StudentDashboard.this, StudentAnnouncements.class);
                startActivity(stud_announcement_intent);
            }
        });


        stud_timetable_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stud_timetable_intent = new Intent(StudentDashboard.this, StudentTimetable.class);
                startActivity(stud_timetable_intent);
            }
        });

        stud_attendance_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stud_attendance_intent = new Intent(StudentDashboard.this, StudentAttendance.class);
                startActivity(stud_attendance_intent);
            }
        });

        stud_mark_sheet_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stud_mark_sheet_intent = new Intent(StudentDashboard.this, StudentMarksheet.class);
                startActivity(stud_mark_sheet_intent);
            }
        });

        stud_internal_mark_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stud_internal_mark_intent = new Intent(StudentDashboard.this, StudentInternalMarks.class);
                startActivity(stud_internal_mark_intent);
            }
        });

        stud_fees_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stud_fees_intent = new Intent(StudentDashboard.this, StudentFees.class);
                startActivity(stud_fees_intent);
            }
        });
        FloatingActionButton fabMessage = findViewById(R.id.fabMessage);
        fabMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event to open the message activity.
                Intent intent = new Intent(StudentDashboard.this, StudentChatViewFaculty.class);
                startActivity(intent);
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
        Cursor cursor = databaseManager.getAnnouncement();

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
                    String announcement = title + "\nDate:" + date;
                    student_dashboard_announcement_List.add(announcement);

                }

                adapter.notifyDataSetChanged();
                cursor.close();
            }
        } else {
            Log.d("DebugTag", "Cursor is null.");
        }
    }
}