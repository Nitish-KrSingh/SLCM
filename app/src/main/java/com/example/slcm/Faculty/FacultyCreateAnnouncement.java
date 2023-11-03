package com.example.slcm.Faculty;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.slcm.DatabaseManager;
import com.example.slcm.R;
import com.example.slcm.Student.StudentMenuHandler;

import java.util.Objects;

public class FacultyCreateAnnouncement extends AppCompatActivity {

    EditText faculty_create_announcement_title, faculty_create_announcement_msg;
    Button fac_createAnnouncement_submit_btn;
    DatabaseManager databaseManager;
    private int loggedInFacultyId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_create_announcement);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Create Announcement");

        SharedPreferences sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE);
        loggedInFacultyId = sharedPreferences.getInt("FACULTY_ID", -1); // Get the FacultyID

        faculty_create_announcement_title = findViewById(R.id.edit_fac_create_announcement_title);
        faculty_create_announcement_msg = findViewById(R.id.edit_fac_create_announcement_message);
        fac_createAnnouncement_submit_btn = findViewById(R.id.submit);
        databaseManager = new DatabaseManager(this);

        fac_createAnnouncement_submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = faculty_create_announcement_title.getText().toString();
                String message = faculty_create_announcement_msg.getText().toString();

                if (title.isEmpty()) {
                    faculty_create_announcement_title.setError("Empty Title");
                    Toast.makeText(FacultyCreateAnnouncement.this, "Enter Title", Toast.LENGTH_SHORT).show();
                } else if (message.isEmpty()) {
                    faculty_create_announcement_msg.setError("Empty Announcement");
                    Toast.makeText(FacultyCreateAnnouncement.this, "Enter Announcement", Toast.LENGTH_SHORT).show();
                } else {
                    boolean insertFacultyAnnouncement = databaseManager.creatingFacultyAnnouncement(loggedInFacultyId, title, message);
                    if (insertFacultyAnnouncement) {
                        Toast.makeText(FacultyCreateAnnouncement.this, "Announcement created successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(FacultyCreateAnnouncement.this, FacultyDashboard.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(FacultyCreateAnnouncement.this, "Faculty id not present", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(FacultyCreateAnnouncement.this, FacultyDashboard.class);
                        startActivity(intent);
                    }
                }
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