package com.example.slcm.Faculty;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.slcm.R;
import com.example.slcm.Student.CustomStudentListAdapter;
import com.example.slcm.Student.Student;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class FacultyMarks extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_marks);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Enter Marks");
        listView = findViewById(R.id.students);
        // Sample student data
        Student[] students = {
                new Student("John", "220970030"),
                new Student("Alice", "220970031"),
                new Student("Krish", "220970032")
        };

        CustomStudentListAdapter adapter = new CustomStudentListAdapter(this, students);
        listView.setAdapter(adapter);
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

