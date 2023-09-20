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

public class MarksActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Marks");
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
        getMenuInflater().inflate(R.menu.menu_ham, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_item1) {
            Toast.makeText(this, "Clicked on about ", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menu_item2) {
            Toast.makeText(this, "Clicked on setting ", Toast.LENGTH_SHORT).show();
        }


        return super.onOptionsItemSelected(item);


    }
}
