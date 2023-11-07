package com.example.slcm.Faculty;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.slcm.DatabaseManager;
import com.example.slcm.R;
import com.example.slcm.Student.Student;

import java.util.ArrayList;
import java.util.Objects;

import android.app.AlertDialog;
import android.content.DialogInterface;

public class FacultyMarks extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Student> studentList;
    private CustomStudentEnterMarksListAdapter adapter;
    private int studentID, maxmarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_marks);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Enter Marks");
        int selectedClass = getIntent().getIntExtra("SELECTED_CLASS", -1);
        String selectedSection = getIntent().getStringExtra("SELECTED_SECTION");
        String selectedClassName= getIntent().getStringExtra("SELECTED_CLASSNAME");
        String selectedDate = getIntent().getStringExtra("SELECTED_DATE");
        String selectedSubjectName= getIntent().getStringExtra("SELECTED_SUBJECTNAME");
        String selectedAssignment = getIntent().getStringExtra("ASSIGNMENT_TYPE");
        maxmarks = getIntent().getIntExtra("MAX_MARKS", -1);
        int selectedSubject = getIntent().getIntExtra("SELECTED_SUBJECT", -1);
        int facultyId = getIntent().getIntExtra("FACULTY_ID", -1);
        TextView details = findViewById(R.id.pagedetails);
        String prevdet="Date: "+selectedDate+"\nClass: "+selectedClassName+"-"+selectedSection+"\nAssignment: "+selectedAssignment+" ("+maxmarks+" marks)"+"\nSubject: "+selectedSubjectName;
        details.setText(prevdet);
        listView = findViewById(R.id.students);
        studentList = new ArrayList<>();
        adapter = new CustomStudentEnterMarksListAdapter(this, studentList, selectedAssignment, maxmarks);
        listView.setAdapter(adapter);
        retrieveStudentsForFacultyMarks(facultyId, selectedClass, selectedSection, selectedSubject);
        Button submitBtn = findViewById(R.id.submit);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean allMarksValid = true;

                for (int i = 0; i < adapter.getCount(); i++) {
                    Student student = (Student) adapter.getItem(i);
                    View itemView = listView.getChildAt(i);
                    EditText marksEditText = itemView.findViewById(R.id.editTextMarks);
                    String marksText = marksEditText.getText().toString();

                    if (TextUtils.isEmpty(marksText)) {
                        allMarksValid = false;
                        break;
                    } else {
                        float marks = Float.parseFloat(marksText);

                        if (!isValidMarks(marks, selectedAssignment)) {
                            allMarksValid = false;
                            break;
                        }
                    }
                }

                if (allMarksValid) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(FacultyMarks.this);
                    alertDialogBuilder.setTitle("Confirm Marks");
                    alertDialogBuilder.setMessage("Submit the marks entered?");

                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            saveMarks(selectedSubject, selectedDate, selectedClass, selectedAssignment);
                            dialog.dismiss();
                        }
                    });

                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
                    Toast.makeText(FacultyMarks.this, "Please enter valid marks for all students.", Toast.LENGTH_SHORT).show();
                }
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

    private void retrieveStudentsForFacultyMarks(int facultyId, int selectedClass, String selectedSection, int selectedSubject) {
        Log.d("ClassGet", "Retrieving students for faculty: " + facultyId + " class " + selectedClass + " section " + selectedSection + " subject " + selectedSubject);
        DatabaseManager databaseManager = new DatabaseManager(this);
        Cursor cursor = databaseManager.getStudentsForFacultyMarks(facultyId, selectedClass, selectedSection, selectedSubject);
        if (cursor != null) {
            int studentNameIndex = cursor.getColumnIndex("StudentName");
            int registrationNumberIndex = cursor.getColumnIndex("RegistrationNumber");
            int studentIDIndex = cursor.getColumnIndex("StudentID");

            if (studentNameIndex != -1 && registrationNumberIndex != -1) {
                if (cursor.moveToFirst()) {
                    do {
                        String studentName = cursor.getString(studentNameIndex);
                        String registrationNumber = cursor.getString(registrationNumberIndex);
                        studentID = cursor.getInt(studentIDIndex);
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

    private void saveMarks(int selectedSubject, String selectedDate,  int selectedClass, String selectedAssignment) {
        DatabaseManager databaseManager = new DatabaseManager(this);
        boolean allMarksValid = true;

        for (int i = 0; i < adapter.getCount(); i++) {
            Student student = (Student) adapter.getItem(i);
            View itemView = listView.getChildAt(i);
            EditText marksEditText = itemView.findViewById(R.id.editTextMarks);
            String marksText = marksEditText.getText().toString();

            if (!TextUtils.isEmpty(marksText)) {
                float marks = Float.parseFloat(marksText);

                if (isValidMarks(marks, selectedAssignment)) {
                    int studentId = databaseManager.getStudentIdForMarks(student.getName(), student.getRollNumber());

                    if (studentId != -1) {
                        boolean success = databaseManager.updateOrInsertMarks(
                                selectedSubject,
                                selectedDate,
                                studentId,
                                selectedClass,
                                selectedAssignment,
                                marks
                        );

                        if (!success) {
                            Toast.makeText(this, "Failed to submit marks!", Toast.LENGTH_SHORT).show();
                            allMarksValid = false;
                        }
                    } else {
                        Toast.makeText(this, "Student not found in the database!", Toast.LENGTH_SHORT).show();
                        allMarksValid = false;
                    }
                } else {
                    Toast.makeText(this, "Invalid marks for " + selectedAssignment+" ("+maxmarks+")", Toast.LENGTH_SHORT).show();
                    allMarksValid = false;
                }
            } else {
                allMarksValid = false;
            }
        }

        if (allMarksValid) {
            Toast.makeText(this, selectedAssignment + " marks submitted successfully!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(FacultyMarks.this, FacultyDashboard.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Please enter valid marks for all students.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidMarks(float marks, String assignmentType) {
        if ("Midterm".equals(assignmentType)) {
            return marks >= 0 && marks <= 30;
        } else {
            return marks >= 0 && marks <= 5;
        }
    }
}


