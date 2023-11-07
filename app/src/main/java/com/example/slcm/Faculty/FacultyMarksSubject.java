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

public class FacultyMarksSubject extends AppCompatActivity {
    private ListView subjectListView;
    private ArrayList<String> subjectList;
    private ArrayAdapter<String> adapter;
    private int subjectId;
    private String selectedAssignment;
    private String subject;
    private Cursor cursor; // Declare cursor as a class-level variable
    private int subjectIDIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_marks_subject);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Marks - Select Subject");
        int selectedClass = getIntent().getIntExtra("SELECTED_CLASS", -1);
        String selectedSection = getIntent().getStringExtra("SELECTED_SECTION");
        String selectedClassName = getIntent().getStringExtra("SELECTED_CLASSNAME");
        String selectedDate = getIntent().getStringExtra("SELECTED_DATE");
        selectedAssignment = getIntent().getStringExtra("ASSIGNMENT_TYPE");
        int maxmarks = getIntent().getIntExtra("MAX_MARKS", -1);
        int facultyId = getIntent().getIntExtra("FACULTY_ID", -1);
        TextView details = findViewById(R.id.pagedetails);
        String prevdet="Selected Date: "+selectedDate+"\nClass: "+selectedClassName+"-"+selectedSection+", Assignment: "+selectedAssignment+" ("+maxmarks+" marks)";
        details.setText(prevdet);
        subjectListView = findViewById(R.id.subjectListView);
        subjectList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.activity_faculty_marks_list_item, R.id.listItemButton, subjectList);
        subjectListView.setAdapter(adapter);
        DatabaseManager databaseManager = new DatabaseManager(this);
        cursor = databaseManager.getSubjectsForFaculty(facultyId, selectedClass, selectedSection); // Initialize the cursor
        if (cursor != null) {
            int subjectNameIndex = cursor.getColumnIndex("SubjectName");
            subjectIDIndex = cursor.getColumnIndex("SubjectID");
            if (subjectNameIndex == -1 || subjectIDIndex == -1) {
                Log.e("CursorError", "Column 'SubjectName' not found in cursor.");
                Toast.makeText(this, "Subject not found", Toast.LENGTH_SHORT).show();
            } else {
                if (cursor.moveToFirst()) {
                    do {
                        String subject = cursor.getString(subjectNameIndex);
                        subjectId = cursor.getInt(subjectIDIndex);
                        Log.d("ClassGet", "Subject:" + subjectId + "name" + subject + "id" + subjectId);
                        if (checkMarks(subjectId, selectedClass, selectedAssignment)) {
                            subjectListView.setBackgroundResource(R.color.green);
                        } else {
                            subjectListView.setBackgroundResource(R.color.red);
                        }
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
                int selectedSubject = getSubjectIdFromCursor(position);
                String subjectName=getSubjectNameFromCursor(position);
                if (checkMarks(subjectId, selectedClass, selectedAssignment)) {

                    Intent intent = new Intent(FacultyMarksSubject.this, FacultyMarks.class);
                    intent.putExtra("SELECTED_CLASS", selectedClass);
                    intent.putExtra("SELECTED_SECTION", selectedSection);
                    intent.putExtra("SELECTED_SUBJECT", selectedSubject);
                    intent.putExtra("SELECTED_CLASSNAME", selectedClassName);
                    intent.putExtra("SELECTED_DATE", selectedDate);
                    intent.putExtra("SELECTED_SUBJECTNAME", subjectName);
                    intent.putExtra("MAX_MARKS", maxmarks);
                    intent.putExtra("ASSIGNMENT_TYPE", selectedAssignment);
                    intent.putExtra("FACULTY_ID", facultyId);
                    startActivity(intent);
                } else {
                    Toast.makeText(FacultyMarksSubject.this, "Marks already entered for " + selectedAssignment, Toast.LENGTH_SHORT).show();
                }
            }
            private int getSubjectIdFromCursor(int position) {
                if (cursor != null && cursor.moveToPosition(position)) {
                    int subjectIDIndex = cursor.getColumnIndex("SubjectID");
                    if (subjectIDIndex != -1) {
                        return cursor.getInt(subjectIDIndex);
                    }
                }
                return -1;
            }
            private String getSubjectNameFromCursor(int position) {
                if (cursor != null && cursor.moveToPosition(position)) {
                    int subjectNameIndex = cursor.getColumnIndex("SubjectName");
                    if (subjectNameIndex != -1) {
                        return cursor.getString(subjectNameIndex);
                    }
                }
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

    private Boolean checkMarks(int subjectIndex, int selectedClass, String selectedAssignment) {
        DatabaseManager databaseManager = new DatabaseManager(this);
        float marks = databaseManager.retrieveAssignmentMarks(subjectIndex, selectedClass, selectedAssignment);
        Log.d("Assignment Marks", selectedAssignment + " Marks for Subject " + subject + ": " + marks);
        return !(marks > 0.0);
    }
}