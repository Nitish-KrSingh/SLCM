package com.example.slcm.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.slcm.Faculty.AttendanceActivity;
import com.example.slcm.Faculty.ChooseDateActivity;
import com.example.slcm.Faculty.MarksActivity;
import com.example.slcm.Faculty.MarksTypeActivity;
import com.example.slcm.R;

public class Student_Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Student Dashboard");

        TextView textview = findViewById(R.id.txtheading);
        textview.setText("Announcements");
        textview.setPaintFlags(textview.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fac_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.profile) {
            Toast.makeText(this, "Clicked on about ", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.fac_attendance) {
            Toast.makeText(this, "Clicked on setting ", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Student_Dashboard.this , AttendanceActivity.class );
            startActivity(intent);

        }
        else if (id == R.id.choose_date) {
            Toast.makeText(this, "Clicked on setting ", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Student_Dashboard.this , ChooseDateActivity.class );
            startActivity(intent);

        }
        else if (id == R.id.marks_table) {
            Toast.makeText(this, "Clicked on setting ", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Student_Dashboard.this , MarksActivity.class );
            startActivity(intent);
        }
        else if (id == R.id.input_marks) {
            Toast.makeText(this, "Clicked on setting ", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Student_Dashboard.this , MarksTypeActivity.class );
            startActivity(intent);
        }
        else if (id == R.id.grade_sheet) {
            Toast.makeText(this, "Clicked on setting ", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Student_Dashboard.this , GradeActivity.class );
            startActivity(intent);
        }



        return super.onOptionsItemSelected(item);


    }

}