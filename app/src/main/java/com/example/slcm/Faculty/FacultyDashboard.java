package com.example.slcm.Faculty;

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
import com.example.slcm.FacultyLogin;
import com.example.slcm.R;
import com.example.slcm.Student.StudentChatViewFaculty;
import com.example.slcm.Student.StudentDashboard;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class FacultyDashboard extends AppCompatActivity {

    Button fac_announcement_btn, fac_timetable_btn, fac_attendance_btn, fac_marks_btn;
    TextView name;

    private ArrayList<String> faculty_dashboard_announcement_List;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_dashboard);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Faculty Dashboard");

        fac_announcement_btn = findViewById(R.id.btnAnnouncement);
        name = findViewById(R.id.name);
        fac_timetable_btn = findViewById(R.id.btnTimeTable);
        fac_attendance_btn = findViewById(R.id.btnAttendance);
        fac_marks_btn = findViewById(R.id.btnMarks);
        SharedPreferences sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE);
        int loggedInFacultyId = sharedPreferences.getInt("FACULTY_ID", -1); // Get the FacultyID
        if (loggedInFacultyId == -1) {
            Log.d("DebugTag", "loggedInFacultyId is invalid.");
            Toast.makeText(this, "Faculty ID not found.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, FacultyLogin.class);
            startActivity(intent);
            finish();
        } else {
                DatabaseManager databaseManager= new DatabaseManager(this);
                SQLiteDatabase db = databaseManager.getReadableDatabase();
                Cursor nameCursor = db.rawQuery("SELECT Name FROM FacultyProfile WHERE FacultyID = ?",  new String[]{String.valueOf(loggedInFacultyId)});
                String facultyName = "";
                if (nameCursor.moveToFirst()) {
                    facultyName = nameCursor.getString(nameCursor.getColumnIndexOrThrow("Name"));
                }
                name.setText("Hello "+facultyName+"!");
            ListView faculty_dashboard_announcement_ListView = findViewById(R.id.fac_dashboard_announcement_ListView);
            faculty_dashboard_announcement_List = new ArrayList<>();
            adapter = new ArrayAdapter<String>(this, R.layout.activity_faculty_announcement_list_item, R.id.listItemButton, faculty_dashboard_announcement_List);
            faculty_dashboard_announcement_ListView.setAdapter(adapter);
            retrieveAnnouncementForFacultyDashboard();
            faculty_dashboard_announcement_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedAnnouncement = faculty_dashboard_announcement_List.get(position);
                    String[] splitAnnouncement = selectedAnnouncement.split("\nDate:");
                    String title = splitAnnouncement[0];
                    String date = splitAnnouncement[1];
                    Log.d("DebugTag", "Date: " + date + "Title" + title);

                    DatabaseManager databaseManager = new DatabaseManager(FacultyDashboard.this);
                    int announcementFacultyId = databaseManager.getAnnouncementFacId(title, date);
                    String message = databaseManager.getAnnouncementMessage(title, date);
                    if (message != null) {
                        if (announcementFacultyId == loggedInFacultyId) {
                            Intent intent = new Intent(FacultyDashboard.this, FacultyCreatedViewAnnouncement.class);
                            intent.putExtra("title", title);
                            intent.putExtra("date", date);
                            intent.putExtra("message", message);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(FacultyDashboard.this, FacultyViewAnnouncement.class);
                            intent.putExtra("title", title);
                            intent.putExtra("date", date);
                            intent.putExtra("message", message);
                            startActivity(intent);
                        }
                    } else {
                        Log.d("DebugTag", "No message found.");
                    }
                }
            });

        }


        fac_announcement_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fac_announcement_intent = new Intent(FacultyDashboard.this, FacultyAnnouncements.class);
                startActivity(fac_announcement_intent);
            }
        });

        fac_timetable_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fac_timetable_intent = new Intent(FacultyDashboard.this, FacultyTimetable.class);
                startActivity(fac_timetable_intent);
            }
        });


        fac_attendance_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fac_attendance_intent = new Intent(FacultyDashboard.this, FacultyAttendanceChooseDate.class);
                startActivity(fac_attendance_intent);
            }
        });

        fac_marks_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fac_marks_intent = new Intent(FacultyDashboard.this, FacultyMarksClass.class);
                startActivity(fac_marks_intent);
            }
        });
        FloatingActionButton fabMessage = findViewById(R.id.fabMessage);
        fabMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event to open the message activity.
                Intent intent = new Intent(FacultyDashboard.this, FacultyChatViewStudent.class);
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

    private void retrieveAnnouncementForFacultyDashboard() {
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