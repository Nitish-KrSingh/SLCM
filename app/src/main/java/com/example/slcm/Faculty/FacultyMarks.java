package com.example.slcm.Faculty;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.slcm.DatabaseManager;
import com.example.slcm.R;
import com.example.slcm.Student.Student;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Objects;

public class FacultyMarks extends AppCompatActivity {

    private ListView listView;
    private   ArrayList<Student> studentList;
    private  CustomStudentMarksListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_marks);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Enter Marks");
        int selectedClass = getIntent().getIntExtra("SELECTED_CLASS", -1);
        String selectedSection = getIntent().getStringExtra("SELECTED_SECTION");
        int selectedSubject = getIntent().getIntExtra("SELECTED_SUBJECT", -1);
        int facultyId = getIntent().getIntExtra("FACULTY_ID", -1);
        listView = findViewById(R.id.students);
        studentList = new ArrayList<>();
        adapter= new CustomStudentMarksListAdapter(this, studentList);
        listView.setAdapter(adapter);
        retrieveStudentsForFacultyMarks(facultyId, selectedClass, selectedSection, selectedSubject);
        DatabaseManager databaseManager = new DatabaseManager(this);
        Cursor cursor = databaseManager.getStudentsForFacultyMarks(facultyId, selectedClass, selectedSection, selectedSubject);
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
    private void retrieveStudentsForFacultyMarks(int facultyId, int selectedClass, String selectedSection, int selectedSubject) {
        Log.d("ClassGet", "Retrieving students for faculty: " + facultyId + " class " + selectedClass + " section " + selectedSection + " subject " + selectedSubject);
        DatabaseManager databaseManager = new DatabaseManager(this);
        Cursor cursor = databaseManager.getStudentsForFacultyMarks(facultyId, selectedClass, selectedSection, selectedSubject);
        if (cursor != null) {
            int studentNameIndex = cursor.getColumnIndex("StudentName");
            int registrationNumberIndex = cursor.getColumnIndex("RegistrationNumber");

            if (studentNameIndex != -1 && registrationNumberIndex != -1) {
                if (cursor.moveToFirst()) {
                    do {
                        String studentName = cursor.getString(studentNameIndex);
                        String registrationNumber = cursor.getString(registrationNumberIndex);
                        Student student = new Student(studentName, registrationNumber);
                        studentList.add(student);
                        Log.d("ClassGet", "Name: " + studentName + " Registration Number: " + registrationNumber);
                    } while (cursor.moveToNext());
                    adapter.notifyDataSetChanged();
                } else {
                    Log.d("DebugTag1", "Cursor is empty.");
                }
            } else {
                Log.e("CursorError", "Columns 'StudentName' and 'RegistrationNumber' not found in cursor.");
            }
            cursor.close();
        } else {
            Log.d("DebugTag2", "Cursor is empty.");
            Toast.makeText(this, "No students found", Toast.LENGTH_SHORT).show();
        }
    }

}

