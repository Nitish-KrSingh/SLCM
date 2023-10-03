package com.example.slcm.Faculty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.slcm.FacultyLogin;
import com.example.slcm.R;
import com.example.slcm.Student.StudentAnnouncements;
import com.example.slcm.Student.StudentAttendance;
import com.example.slcm.Student.StudentDashboard;
import com.example.slcm.Student.StudentFeesActivity;
import com.example.slcm.Student.StudentInternalMarks;
import com.example.slcm.Student.StudentMarksheet;
import com.example.slcm.Student.StudentProfileDashboard;
import com.example.slcm.Student.StudentTimetable;
import com.example.slcm.StudentLogin;

import java.util.Objects;

public class FacultyDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_dashboard);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Faculty Dashboard");
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