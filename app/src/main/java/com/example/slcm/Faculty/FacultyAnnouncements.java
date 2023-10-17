package com.example.slcm.Faculty;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.slcm.FacultyLogin;
import com.example.slcm.R;

import java.util.ArrayList;
import java.util.Objects;

public class FacultyAnnouncements extends AppCompatActivity {

    private ArrayList<String> faculty_announcement_List;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_announcements);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Announcements");

        SharedPreferences sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE);
        int loggedInFacultyId = sharedPreferences.getInt("FACULTY_ID", -1); // Get the FacultyID

        if (loggedInFacultyId == -1) {
            Log.d("DebugTag", "loggedInFacultyId is invalid.");
            Toast.makeText(this, "Faculty ID not found.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, FacultyLogin.class);
            startActivity(intent);
            finish();
        } else {
            ListView faculty_announcement_ListView = findViewById(R.id.fac_announcement_ListView);
            faculty_announcement_List = new ArrayList<>();
            adapter = new ArrayAdapter<String>(this, R.layout.activity_faculty_announcement_list_item, R.id.listItemButton, faculty_announcement_List);
            faculty_announcement_ListView.setAdapter(adapter);
            retrieveAnnouncementForFaculty();
            faculty_announcement_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedAnnouncement = faculty_announcement_List.get(position);
                    String[] splitAnnouncement = selectedAnnouncement.split("\nDate:");
                    String title = splitAnnouncement[0];
                    String date = splitAnnouncement[1];
                    Log.d("DebugTag", "Date: " + date + "Title" + title);

                    DatabaseManager databaseManager = new DatabaseManager(FacultyAnnouncements.this);
                    String message = databaseManager.getAnnouncementMessage(title, date);

                    if (message != null) {
                        Intent intent = new Intent(FacultyAnnouncements.this, FacultyViewAnnouncement.class);
                        intent.putExtra("title", title);
                        intent.putExtra("date", date);
                        intent.putExtra("message", message);
                        startActivity(intent);
                    } else {
                        Log.d("DebugTag", "No message found.");
                    }
                }
            });
        }
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

    private void retrieveAnnouncementForFaculty() {
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
                    faculty_announcement_List.add(announcement);
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