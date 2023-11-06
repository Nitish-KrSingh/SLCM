package com.example.slcm.Faculty;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.slcm.DatabaseManager;
import com.example.slcm.R;

import java.util.ArrayList;
import java.util.Objects;

public class FacultyAttendanceSubject extends AppCompatActivity {

    private ListView subjectListView;
    private ArrayList<String> subjectList;
    private ArrayAdapter<String> adapter;
    private Cursor cursor; // Declare cursor as a class-level variable
    private int subjectIDIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_attendance_subject);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Attendance - Select Subject");
        int selectedClass = getIntent().getIntExtra("SELECTED_CLASS", -1);
        String selectedSection = getIntent().getStringExtra("SELECTED_SECTION");
        String selectedClassName = getIntent().getStringExtra("SELECTED_CLASSNAME");
        int facultyId = getIntent().getIntExtra("FACULTY_ID", -1);
        String select_date_for_attendance = getIntent().getStringExtra("ATT_SELECTED_DATE");
        TextView details = findViewById(R.id.pagedetails);
        String prevdet="Selected Date: "+select_date_for_attendance+"\nClass: "+selectedClassName+"-"+selectedSection;
        details.setText(prevdet);
        subjectListView = findViewById(R.id.subjectListView);
        subjectList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.activity_faculty_marks_list_item, R.id.listItemButton, subjectList);
        subjectListView.setAdapter(adapter);
        Log.d("ClassGet", "Retrieving subjects for faculty: " + facultyId + "class" + selectedClass + "section" + selectedSection);
        DatabaseManager databaseManager = new DatabaseManager(this);
        cursor = databaseManager.getSubjectsForFaculty(facultyId, selectedClass, selectedSection); // Initialize the cursor
        if (cursor != null) {
            int subjectNameIndex = cursor.getColumnIndex("SubjectName");
            subjectIDIndex = cursor.getColumnIndex("SubjectID"); // Update subjectIDIndex here
            if (subjectNameIndex == -1 || subjectIDIndex == -1) {
                Log.e("CursorError", "Column 'S.SubjectName' not found in cursor.");
                Toast.makeText(this, "Subject not found", Toast.LENGTH_SHORT).show();
            } else {
                if (cursor.moveToFirst()) {
                    do {
                        String subject = cursor.getString(subjectNameIndex);
                        int subjectId = cursor.getInt(subjectIDIndex);
                        Log.d("ClassGet", "Subject:" + subjectIDIndex + "name" + subject + "id" + subjectId);
                        subjectList.add(subject);
                    } while (cursor.moveToNext());
                    adapter.notifyDataSetChanged();
                } else {
                    Log.d("DebugTag", "Cursor is empty.");
                }
            }
        } else {
            Log.d("DebugTag", "Cursor is null.");
        }
        subjectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Retrieve the selected subject ID from the cursor
                int selectedSubjectId = getSubjectIdFromCursor(position);
                String subjectName=getSubjectNameFromCursor(position);
                // Rest of your code remains the same
                Intent intent = new Intent(FacultyAttendanceSubject.this, FacultyAttendance.class);
                intent.putExtra("SELECTED_CLASS", selectedClass);
                intent.putExtra("SELECTED_SECTION", selectedSection);
                intent.putExtra("SELECTED_CLASSNAME", selectedClassName);
                intent.putExtra("SELECTED_SUBJECTNAME", subjectName);
                intent.putExtra("SELECTED_SUBJECT", selectedSubjectId);
                intent.putExtra("FACULTY_ID", facultyId);
                intent.putExtra("ATT_SELECTED_DATE", select_date_for_attendance);
                startActivity(intent);
            }

            private int getSubjectIdFromCursor(int position) {
                if (cursor != null && cursor.moveToPosition(position)) {
                    int subjectIDIndex = cursor.getColumnIndex("SubjectID");
                    if (subjectIDIndex != -1) {
                        return cursor.getInt(subjectIDIndex);
                    }
                }
                // Return a default value or handle the error as needed
                return -1;
            }
            private String getSubjectNameFromCursor(int position) {
                if (cursor != null && cursor.moveToPosition(position)) {
                    int subjectNameIndex = cursor.getColumnIndex("SubjectName");
                    if (subjectNameIndex != -1) {
                        return cursor.getString(subjectNameIndex);
                    }
                }
                // Return a default value or handle the error as needed
                return null;
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
}
