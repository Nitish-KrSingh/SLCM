package com.example.slcm.Faculty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.slcm.DatabaseManager;
import com.example.slcm.FacultyLogin;
import com.example.slcm.R;

import java.util.ArrayList;
import java.util.Objects;

public class FacultyAnnouncements extends AppCompatActivity {

    private ListView faculty_announcement_ListView;
    private ArrayList<String> faculty_announcement_List;
    private ArrayAdapter<String> adapter;
    private int loggedInFacultyId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_announcements);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Announcements");

        SharedPreferences sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE);
        loggedInFacultyId = sharedPreferences.getInt("FACULTY_ID", -1); // Get the FacultyID

        if (loggedInFacultyId == -1) {
            Log.d("DebugTag", "loggedInFacultyId is invalid.");
            Toast.makeText(this, "Faculty ID not found.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, FacultyLogin.class);
            startActivity(intent);
            finish();
        } else {
            faculty_announcement_ListView = findViewById(R.id.fac_announcement_ListView);
            faculty_announcement_List = new ArrayList<>();
            adapter = new ArrayAdapter<String>(this, R.layout.activity_faculty_announcement_list_item, R.id.listItemButton, faculty_announcement_List);
            faculty_announcement_ListView.setAdapter(adapter);
            retrieveAnnouncementForFaculty();
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
        Cursor cursor = databaseManager.getAnnouncementForFaculty();

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