package com.example.slcm.Student;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.slcm.DatabaseManager;
import com.example.slcm.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class StudentAnnouncements extends AppCompatActivity {


    private ArrayList<String> student_announcement_List;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_announcements);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Announcements");


        ListView student_announcement_ListView = findViewById(R.id.announcement_ListView);
        student_announcement_List = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.activity_faculty_announcement_list_item, R.id.listItemButton, student_announcement_List);
        student_announcement_ListView.setAdapter(adapter);
        retrieveAnnouncementForStudent();
        student_announcement_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedAnnouncement = student_announcement_List.get(position);
                String[] splitAnnouncement = selectedAnnouncement.split("\nDate:");
                String title = splitAnnouncement[0];
                String date = splitAnnouncement[1];
                Log.d("DebugTag", "Date: " + date + "Title" + title);

                DatabaseManager databaseManager = new DatabaseManager(StudentAnnouncements.this);
                String message = databaseManager.getAnnouncementMessage(title, date);

                if (message != null) {  // Updated line
                    Intent intent = new Intent(StudentAnnouncements.this, StudentViewAnnouncement.class);
                    intent.putExtra("title", title);
                    intent.putExtra("date", date);
                    intent.putExtra("message", message);
                    startActivity(intent);
                } else {
                    Log.d("DebugTag", "No message found.");
                }
            }
        });
        FloatingActionButton fabMessage = findViewById(R.id.fabMessage);
        fabMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event to open the message activity.
                Intent intent = new Intent(StudentAnnouncements.this, StudentChatViewFaculty.class);
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

    private void retrieveAnnouncementForStudent() {
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
                    student_announcement_List.add(announcement);

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