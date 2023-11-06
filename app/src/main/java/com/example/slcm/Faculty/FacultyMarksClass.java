package com.example.slcm.Faculty;

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

import androidx.appcompat.app.AppCompatActivity;

import com.example.slcm.DatabaseManager;
import com.example.slcm.FacultyLogin;
import com.example.slcm.R;

import java.util.ArrayList;
import java.util.Objects;

public class FacultyMarksClass extends AppCompatActivity {
    private ArrayList<String> classSectionList;
    private ArrayAdapter<String> adapter;
    private int loggedInFacultyId;
    private int classNameIndex;
    private Cursor cursor;
    private int sectionIndex;

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
            ListView classSectionListView = findViewById(R.id.classSectionListView);
            classSectionList = new ArrayList<>();
            adapter = new ArrayAdapter<String>(this, R.layout.activity_faculty_marks_list_item, R.id.listItemButton, classSectionList);
            classSectionListView.setAdapter(adapter);
            Log.d("ClassGet", "Retrieving class sections for faculty: " + loggedInFacultyId);
            DatabaseManager databaseManager = new DatabaseManager(this);
             cursor = databaseManager.getClassSectionsForFaculty(loggedInFacultyId);
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
                }
            } else {
                Log.d("DebugTag", "Cursor is null.");
            }

            classSectionListView.setOnItemClickListener((parent, view, position, id) -> {
                String selectedItem = classSectionList.get(position);
                String[] parts = selectedItem.split(" - ");
                String className = parts[0];
                String section = parts[1];

                int selectedClassId = getClassIdFromCursor(className, section);

                Intent intent = new Intent(FacultyMarksClass.this, FacultyMarksType.class);
                intent.putExtra("SELECTED_CLASS", selectedClassId);
                intent.putExtra("SELECTED_SECTION", section);
                intent.putExtra("SELECTED_CLASSNAME", className);
                intent.putExtra("FACULTY_ID", loggedInFacultyId);
                startActivity(intent);
            });
        }
    }
    private int getClassIdFromCursor(String className, String section) {
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String cursorClassName = cursor.getString(classNameIndex);
                String cursorSection = cursor.getString(sectionIndex);
                int id=cursor.getColumnIndex("ClassID");
                if (className.equals(cursorClassName) && section.equals(cursorSection)) {
                    return cursor.getInt(id);
                }
            } while (cursor.moveToNext());
        }
        return -1;
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
