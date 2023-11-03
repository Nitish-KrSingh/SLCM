package com.example.slcm.Faculty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.slcm.DatabaseManager;
import com.example.slcm.R;
import com.example.slcm.Student.Student;

import java.util.List;
import java.util.Objects;

public class FacultyUpdateAnnouncement extends AppCompatActivity {

    EditText faculty_update_announcement_title, faculty_update_announcement_msg;
    Button fac_updateAnnouncement_update_btn;
    DatabaseManager databaseManager;
    private int loggedInFacultyId;
    private ArrayAdapter<String> adapter;
    String title,message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_update_announcement);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Update Announcement");

        SharedPreferences sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE);
        loggedInFacultyId = sharedPreferences.getInt("FACULTY_ID", -1); // Get the FacultyID

        faculty_update_announcement_title = findViewById(R.id.edit_fac_update_announcement_title);
        faculty_update_announcement_msg = findViewById(R.id.edit_fac_update_announcement_message);
        fac_updateAnnouncement_update_btn = findViewById(R.id.update);
        databaseManager = new DatabaseManager(this);


        Intent intent = getIntent();
        if (intent != null) {
            title = intent.getStringExtra("title");
            message = intent.getStringExtra("message");

            faculty_update_announcement_title.setText(title);
            faculty_update_announcement_msg.setText(message);

            getSupportActionBar().setTitle(title);


        }
        int getAnnouncementId = databaseManager.getAnnouncementIdFaculty(loggedInFacultyId, title);

        fac_updateAnnouncement_update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updatedTitle = faculty_update_announcement_title.getText().toString();
                String updatedMessage = faculty_update_announcement_msg.getText().toString();

                if (updatedTitle.isEmpty()) {
                    faculty_update_announcement_title.setError("Empty Title");
                    Toast.makeText(FacultyUpdateAnnouncement.this, "Enter Title", Toast.LENGTH_SHORT).show();
                } else if (updatedMessage.isEmpty()) {
                    faculty_update_announcement_msg.setError("Empty Announcement");
                    Toast.makeText(FacultyUpdateAnnouncement.this, "Enter Announcement", Toast.LENGTH_SHORT).show();
                } else {

                    Cursor cursor = databaseManager.updatingFacultyAnnouncement(getAnnouncementId, updatedTitle, updatedMessage);

                    if (cursor != null && cursor.moveToFirst()) {
                        Toast.makeText(FacultyUpdateAnnouncement.this, "Announcement updated successfully", Toast.LENGTH_SHORT).show();
                        cursor.close();
                        Intent intent1 = new Intent(FacultyUpdateAnnouncement.this, FacultyDashboard.class);
                        startActivity(intent1);
                    } else {
                        Toast.makeText(FacultyUpdateAnnouncement.this, "Faculty ID not present", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(FacultyUpdateAnnouncement.this, FacultyDashboard.class);
                        startActivity(intent2);
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