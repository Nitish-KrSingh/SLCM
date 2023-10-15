package com.example.slcm.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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

public class StudentAnnouncements extends AppCompatActivity {


    private ListView student_announcement_ListView;
    private ArrayList<String> student_announcement_List;
    private ArrayAdapter<String> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_announcements);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Announcements");


        student_announcement_ListView = findViewById(R.id.stud_announcement_ListView);
        student_announcement_List = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.activity_student_announcement_list_item, R.id.listItemButton, student_announcement_List);
        student_announcement_ListView.setAdapter(adapter);
            retrieveAnnouncementForStudent();


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