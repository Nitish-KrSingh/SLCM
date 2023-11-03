package com.example.slcm.Faculty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.slcm.DatabaseManager;
import com.example.slcm.R;
import com.example.slcm.Student.StudentMenuHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class FacultyCreatedViewAnnouncement extends AppCompatActivity {



    DatabaseManager databaseManager;
    private int loggedInFacultyId;
    String title,message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_created_view_announcement);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE);
        loggedInFacultyId = sharedPreferences.getInt("FACULTY_ID", -1);
        TextView announcementTitle = findViewById(R.id.CreatedAnnouncementTitle);
        TextView announcementDate = findViewById(R.id.CreatedAnnouncementDate);
        TextView announcementMessage = findViewById(R.id.CreatedAnnouncementMessage);
        LinearLayout listItemViewEditBtn= findViewById(R.id.listItemViewEditButton);
        LinearLayout listItemViewDeleteBtn=findViewById(R.id.listItemViewDeleteButton);

        databaseManager = new DatabaseManager(this);



        Intent intent = getIntent();
        if (intent != null) {
            title = intent.getStringExtra("title");
            String date = intent.getStringExtra("date");
            String message = intent.getStringExtra("message");

            announcementTitle.setText(title);
            announcementDate.setText(date);
            announcementMessage.setText(message);
        }
        getSupportActionBar().setTitle(title);


        listItemViewEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FacultyCreatedViewAnnouncement.this, FacultyUpdateAnnouncement.class);
                String titleText = ((TextView) findViewById(R.id.CreatedAnnouncementTitle)).getText().toString();
                String dateText = ((TextView) findViewById(R.id.CreatedAnnouncementDate)).getText().toString();
                String messageText = ((TextView) findViewById(R.id.CreatedAnnouncementMessage)).getText().toString();

                intent.putExtra("title", titleText);
                intent.putExtra("date", dateText);
                intent.putExtra("message", messageText);
                startActivity(intent);
            }
        });

        int getAnnouncementId = -1; // Initialize to a default value

        if (title != null) {
            getAnnouncementId = databaseManager.getAnnouncementIdFaculty(loggedInFacultyId, title);
        }


        int finalGetAnnouncementId = getAnnouncementId;
        listItemViewDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean deleteFacultyAnnouncement = databaseManager.deletingFacultyAnnouncement(finalGetAnnouncementId);
                if (deleteFacultyAnnouncement) {
                    Toast.makeText(FacultyCreatedViewAnnouncement.this, "Announcement Deleted successfully", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(FacultyCreatedViewAnnouncement.this, FacultyDashboard.class);
                    startActivity(intent1);
                } else {
                    Toast.makeText(FacultyCreatedViewAnnouncement.this, "Faculty id not present", Toast.LENGTH_SHORT).show();
                    Intent intent2 = new Intent(FacultyCreatedViewAnnouncement.this, FacultyDashboard.class);
                    startActivity(intent2);
                }

            }
        });

        FloatingActionButton fabMessage = findViewById(R.id.fabMessage);
        fabMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event to open the message activity.
                Intent intent = new Intent(FacultyCreatedViewAnnouncement.this, FacultyChatViewStudent.class);
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
        StudentMenuHandler.handleMenuAction(item, this);
        return super.onOptionsItemSelected(item);
    }



}