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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.slcm.DatabaseManager;
import com.example.slcm.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;


public class StudentChangePassword extends AppCompatActivity {

    EditText studentNewPassword, studentConfirmPassword;
    Button studentChangeSubmitBtn;
    DatabaseManager databaseManager;
    private String loggedInRegistrationNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_change_password);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change Password");
        SharedPreferences sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE);
        loggedInRegistrationNo = sharedPreferences.getString("LOGIN_USER", "");
        studentNewPassword = findViewById(R.id.New_Password_Edit);
        studentConfirmPassword = findViewById(R.id.Confirm_Password_Edit);
        studentChangeSubmitBtn = findViewById(R.id.submit);
        int studentId = sharedPreferences.getInt("STUDENT_ID", -1);
        databaseManager = new DatabaseManager(this);
        SQLiteDatabase db = databaseManager.getReadableDatabase();
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
        studentChangeSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studNewPassword = studentNewPassword.getText().toString();
                String studConfirmPassword = studentConfirmPassword.getText().toString();

                if (studNewPassword.equals("")) {
                    studentNewPassword.setError("Empty New Password");
                    Toast.makeText(StudentChangePassword.this, "Enter New Password", Toast.LENGTH_SHORT).show();
                } else if (studConfirmPassword.equals("")) {
                    studentConfirmPassword.setError("Empty Confirm Password");
                    Toast.makeText(StudentChangePassword.this, "Enter Confirm Password", Toast.LENGTH_SHORT).show();
                } else {
                    if (studNewPassword.equals(studConfirmPassword)) {
                        boolean updateStudentPassword = databaseManager.updateStudentPassword(loggedInRegistrationNo, studNewPassword);
                        if (updateStudentPassword) {
                            Toast.makeText(StudentChangePassword.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(StudentChangePassword.this, "Password did not change", Toast.LENGTH_SHORT).show();
                        }
                        Intent change_pass = new Intent(StudentChangePassword.this, StudentProfileDashboard.class);
                        startActivity(change_pass);
                    } else {
                        studentNewPassword.setError("Password did not match");
                        studentConfirmPassword.setError("Password did not match");
                        Toast.makeText(StudentChangePassword.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                }
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
}
