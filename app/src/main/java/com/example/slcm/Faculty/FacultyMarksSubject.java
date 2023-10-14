package com.example.slcm.Faculty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


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


import com.example.slcm.DatabaseManager;
import com.example.slcm.R;

import java.util.ArrayList;

import java.util.Objects;

public class FacultyMarksSubject extends AppCompatActivity {
    private ListView subjectListView;
    private ArrayList<String> subjectList;
    private ArrayAdapter<String> adapter;
    private int subjectIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_marks_subject);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Marks - Select Subject");

        int selectedClass = getIntent().getIntExtra("SELECTED_CLASS", -1);
        String selectedSection = getIntent().getStringExtra("SELECTED_SECTION");
        int facultyId = getIntent().getIntExtra("FACULTY_ID", -1);
        subjectListView = findViewById(R.id.subjectListView);
        subjectList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.activity_faculty_marks_list_item, R.id.listItemButton, subjectList);
        subjectListView.setAdapter(adapter);
        retrieveSubjectsForFaculty(facultyId, selectedClass, selectedSection);
        subjectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int selectedSubject = subjectIndex;
                Intent intent = new Intent(FacultyMarksSubject.this, FacultyMarks.class);
                intent.putExtra("SELECTED_CLASS", selectedClass);
                intent.putExtra("SELECTED_SECTION", selectedSection);
                intent.putExtra("SELECTED_SUBJECT", selectedSubject);
                intent.putExtra("FACULTY_ID", facultyId);
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

    private void retrieveSubjectsForFaculty(int facultyId, int selectedClass, String selectedSection) {
        Log.d("ClassGet", "Retrieving subjects for faculty: " + facultyId+"class"+selectedClass+"section"+selectedSection);
        DatabaseManager databaseManager = new DatabaseManager(this);
        Cursor cursor = databaseManager.getSubjectsForFaculty(facultyId, selectedClass, selectedSection);
        if (cursor != null) {
            subjectIndex = cursor.getColumnIndex("SubjectName");
            if (subjectIndex == -1) {
                Log.e("CursorError", "Column 'S.SubjectName' not found in cursor.");
                Toast.makeText(this, "Subject not found", Toast.LENGTH_SHORT).show();
            } else {
                if (cursor.moveToFirst()) {
                    do {
                        String subject = cursor.getString(subjectIndex);
                        Log.d("ClassGet", "Subject:"+subjectIndex+"name"+subject);
                        subjectList.add(subject);
                    } while (cursor.moveToNext());
                    adapter.notifyDataSetChanged();
                } else {
                    Log.d("DebugTag", "Cursor is empty.");
                }
                cursor.close();
            }
        } else {
            Log.d("DebugTag", "Cursor is null.");
        }
    }

}