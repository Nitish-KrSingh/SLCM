package com.example.slcm.Student;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.slcm.DatabaseManager;
import com.example.slcm.Faculty.ProfileAdapter;
import com.example.slcm.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class StudentProfile extends AppCompatActivity {
    private ListView studentDetailsListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Your details");
        SharedPreferences sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE);
        int loggedInStudentId = sharedPreferences.getInt("STUDENT_ID", -1);
        studentDetailsListView = findViewById(R.id.studentDetailsListView);
        displayStudentDetails(loggedInStudentId);
        FloatingActionButton fabMessage = findViewById(R.id.fabMessage);
        fabMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event to open the message activity.
                Intent intent = new Intent(StudentProfile.this, StudentChatViewFaculty.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.student_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        StudentMenuHandler.handleMenuAction(item, this);
        return super.onOptionsItemSelected(item);
    }
    private void displayStudentDetails(int studentId) {
        DatabaseManager dbHelper = new DatabaseManager(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor nameCursor = db.rawQuery("SELECT Name, RegistrationNumber FROM StudentProfile WHERE StudentID = ?",  new String[]{String.valueOf(studentId)});
        String studentName = "", studentregno="";
        if (nameCursor.moveToFirst()) {
            studentName = nameCursor.getString(nameCursor.getColumnIndexOrThrow("Name"));
            studentregno= nameCursor.getString(nameCursor.getColumnIndexOrThrow("RegistrationNumber"));
        }
        TextView studentNameTextView = findViewById(R.id.studname);
        TextView studentRegno = findViewById(R.id.studregnno);
        studentNameTextView.setText(studentName);
        studentRegno.setText(studentregno);
        Cursor cursor = db.rawQuery(
                "SELECT s.RegistrationNumber,   s.Semester, c.ClassName, c.Section, s.Age, s.DOB, s.Gender, s.PhoneNumber, s.Email, s.Department " +
                        "FROM StudentProfile s " +
                        "JOIN Class c ON s.ClassID = c.ClassID " +
                        "WHERE s.StudentID = ?",  new String[]{String.valueOf(studentId)}
        );
        ProfileAdapter adapter = new ProfileAdapter(this, cursor);
        studentDetailsListView.setAdapter(adapter);
    }
}