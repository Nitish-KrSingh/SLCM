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
import android.widget.Toast;

import com.example.slcm.DatabaseManager;
import com.example.slcm.R;
import com.example.slcm.Student.Student;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

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
        Button Submit_Attendance = (Button) findViewById(R.id.Fac_Submit_Att_Btn);
        Button all_Student_present = (Button) findViewById(R.id.all_present);
        Button all_Student_absent = (Button) findViewById(R.id.all_absent);

        int selectedClass = getIntent().getIntExtra("SELECTED_CLASS", -1);
        String selectedSection = getIntent().getStringExtra("SELECTED_SECTION");
        int facultyId = getIntent().getIntExtra("FACULTY_ID", -1);
        String select_date_for_attendance = getIntent().getStringExtra("ATT_SELECTED_DATE");
        int selectedSubject = getIntent().getIntExtra("SELECTED_SUBJECT", -1);

        studentList = retrieveStudentsForFacultyMarks(facultyId, selectedClass, selectedSection, selectedSubject);
        System.out.println(studentList);
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
                Map<String, String> presentMap = adapter.GetPresentMap();
                for(Student student : studentList){
                    Attendance attendance = new Attendance(student.getRollNumber(), selectedClass, selectedSubject, select_date_for_attendance, presentMap.get(student.getRollNumber()));
                    databaseManager.AddAttendance(attendance);
                }

                Toast.makeText(FacultyAttendance.this, "Attendance submitted!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FacultyAttendance.this, FacultyDashboard.class);
                startActivity(intent);
                finish();
            }
        });

//        retrieveStudentsForFacultyMarks(facultyId, selectedClass, selectedSection, selectedSubject);
        Cursor cursor = databaseManager.getStudentsForFacultyMarks(facultyId, selectedClass, selectedSection, selectedSubject);

//        all_Student_present.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (studentList.size() == 0) {
//                    Toast.makeText(FacultyAttendance.this, "No student present.", Toast.LENGTH_SHORT).show();
//                } else {
//                    for (Student student : studentList) {
//
//                        student.setAttendanceStatus(AttendanceStatus.PRESENT);
//                    }
//
//
//                    adapter.notifyDataSetChanged();
//                }
//            }
//        });




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
//                    adapter.notifyDataSetChanged();
                } else {
                    Log.d("DebugTag1", "Cursor is empty.");
                }
            } else {
                Log.e("CursorError", "Columns 'StudentName' and 'RegistrationNumber' not found in cursor.");
            }
            cursor.close();
            return result;
        } else {
            Log.d("DebugTag2", "Cursor is empty.");
            Toast.makeText(this, "No students found", Toast.LENGTH_SHORT).show();
            return new ArrayList<Student>();

        }

    }


}