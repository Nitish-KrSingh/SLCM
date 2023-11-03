package com.example.slcm.Student;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.slcm.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class StudentViewAnnouncement extends AppCompatActivity {
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_announcement);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        TextView announcementTitle = findViewById(R.id.announcementTitle);
        TextView announcementDate = findViewById(R.id.announcementDate);
        TextView announcementMessage = findViewById(R.id.announcementMessage);

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
        FloatingActionButton fabMessage = findViewById(R.id.fabMessage);
        fabMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event to open the message activity.
                Intent intent = new Intent(StudentViewAnnouncement.this, StudentChatViewFaculty.class);
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
}
