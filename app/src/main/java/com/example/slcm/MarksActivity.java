package com.example.slcm;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class MarksActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks);

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
}
