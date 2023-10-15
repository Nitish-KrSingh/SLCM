package com.example.slcm.Faculty;

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

public class FacultyMarksClass extends AppCompatActivity {
    private ListView classSectionListView;
    private ArrayList<String> classSectionList;
    private ArrayAdapter<String> adapter;
    private int loggedInFacultyId;
    private int classNameIndex, sectionIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_marks_class);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Marks - Select Class");
        SharedPreferences sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE);
        loggedInFacultyId = sharedPreferences.getInt("FACULTY_ID", -1); // Get the FacultyID

        if (loggedInFacultyId == -1) {
            Log.d("DebugTag", "loggedInFacultyId is invalid.");
            Toast.makeText(this, "Faculty ID not found.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, FacultyLogin.class);
            startActivity(intent);
            finish();
        } else {
            classSectionListView = findViewById(R.id.classSectionListView);
            classSectionList = new ArrayList<>();
            adapter = new ArrayAdapter<String>(this, R.layout.activity_faculty_marks_list_item, R.id.listItemButton, classSectionList);
            classSectionListView.setAdapter(adapter);
            retrieveClassSectionsForFaculty(loggedInFacultyId);
            classSectionListView.setOnItemClickListener((parent, view, position, id) -> {
                Log.d("DebugTag", "Item clicked at position: " + position);
                String selectedItem = classSectionList.get(position);
                String[] parts = selectedItem.split(" - ");
                String className = parts[0];
                String section = parts[1];
                Intent intent = new Intent(FacultyMarksClass.this, FacultyMarksType.class);
                intent.putExtra("SELECTED_CLASS", classNameIndex);
                intent.putExtra("SELECTED_SECTION", section);
                intent.putExtra("FACULTY_ID", loggedInFacultyId);
                startActivity(intent);
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

    private void retrieveClassSectionsForFaculty(int facultyId) { // Change the parameter type to int
        Log.d("ClassGet", "Retrieving class sections for faculty: " + facultyId);
        DatabaseManager databaseManager = new DatabaseManager(this);
        Cursor cursor = databaseManager.getClassSectionsForFaculty(facultyId);
        if (cursor != null) {
            classNameIndex = cursor.getColumnIndex("ClassName");
            sectionIndex = cursor.getColumnIndex("Section");

            if (classNameIndex == -1 || sectionIndex == -1) {
                Log.e("CursorError", "One or more columns not found in cursor: ClassNameIndex=" + classNameIndex + ", SectionIndex=" + sectionIndex);
                Toast.makeText(this, "Class & Section not found", Toast.LENGTH_SHORT).show();
            } else {
                if (cursor.moveToFirst()) {
                    do {
                        String className = cursor.getString(classNameIndex);
                        String section = cursor.getString(sectionIndex);
                        String classSection = className + " - " + section;
                        classSectionList.add(classSection);
                        Log.d("DebugTag", "Added class section: " + classSection);
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
