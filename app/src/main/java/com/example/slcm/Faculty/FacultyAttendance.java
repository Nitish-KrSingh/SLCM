package com.example.slcm.Faculty;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.slcm.DatabaseManager;
import com.example.slcm.R;
import com.example.slcm.Student.Student;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import android.app.AlertDialog;
import android.content.DialogInterface;

public class FacultyAttendance extends AppCompatActivity {

    private ArrayList<Student> studentList;
    private CustomStudentAttendanceListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_attendance);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Enter Attendance");
        ListView listView = findViewById(R.id.Fac_Stud_Att_List);
        Button Submit_Attendance = findViewById(R.id.Fac_Submit_Att_Btn);
        Button all_Student_present = findViewById(R.id.all_present);
        Button all_Student_absent = findViewById(R.id.all_absent);
        TextView details = findViewById(R.id.pagedetails);

        int selectedClass = getIntent().getIntExtra("SELECTED_CLASS", -1);
        String selectedSection = getIntent().getStringExtra("SELECTED_SECTION");
        String selectedClassName= getIntent().getStringExtra("SELECTED_CLASSNAME");
        String selectedSubjectName= getIntent().getStringExtra("SELECTED_SUBJECTNAME");
        int facultyId = getIntent().getIntExtra("FACULTY_ID", -1);
        String select_date_for_attendance = getIntent().getStringExtra("ATT_SELECTED_DATE");
        int selectedSubject = getIntent().getIntExtra("SELECTED_SUBJECT", -1);

        String prevdet="Date: "+select_date_for_attendance+"\nClass: "+selectedClassName+"-"+selectedSection+"\nSubject: "+selectedSubjectName;
        details.setText(prevdet);

        studentList = retrieveStudentsForFacultyMarks(facultyId, selectedClass, selectedSection, selectedSubject);
       // System.out.println(studentList);
        adapter = new CustomStudentAttendanceListAdapter(this, studentList);
        listView.setAdapter(adapter);

        all_Student_present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.SetAllPresent();
            }
        });

        all_Student_absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.SetAllAbsent();
            }
        });

        DatabaseManager databaseManager = new DatabaseManager(this);

        Submit_Attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(FacultyAttendance.this);
                alertDialogBuilder.setTitle("Confirm Attendance:");
                alertDialogBuilder.setMessage("Submit the attendance taken?");

                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Map<String, String> presentMap = adapter.GetPresentMap();
                        for (Student student : studentList) {
                            Attendance attendance = new Attendance(student.getRollNumber(), selectedClass, selectedSubject, select_date_for_attendance, presentMap.get(student.getRollNumber()));
                            databaseManager.AddAttendance(attendance);
                        }

                        Toast.makeText(FacultyAttendance.this, "Attendance submitted!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(FacultyAttendance.this, FacultyDashboard.class);
                        startActivity(intent);
                        finish();
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


    private ArrayList<Student> retrieveStudentsForFacultyMarks(int facultyId, int selectedClass, String selectedSection, int selectedSubject) {
        Log.d("ClassGet", "Retrieving students for faculty: " + facultyId + " class " + selectedClass + " section " + selectedSection + " subject " + selectedSubject);
        ArrayList<Student> result = new ArrayList<>();
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
                        result.add(student);
                        Log.d("ClassGet", "Name: " + studentName + " Registration Number: " + registrationNumber);
                    } while (cursor.moveToNext());

                } else {
                    Log.d("DebugTag1", "Cursor is empty.");
                }
            } else {
                Log.e("CursorError", "Columns 'StudentName' and 'RegistrationNumber' not found in cursor.");
            }
            return result;
        } else {
            Log.d("DebugTag2", "Cursor is empty.");
            Toast.makeText(this, "No students found", Toast.LENGTH_SHORT).show();
            return new ArrayList<Student>();

        }
    }
}