package com.example.slcm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.slcm.Student.StudentDashboard;
import com.example.slcm.Student.StudentForgotPassword;

public class StudentLogin extends AppCompatActivity {

    TextView faculty_login_page, forgotp;
    Button student_signin;
    private EditText registrationNumberEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        faculty_login_page = findViewById(R.id.Faculty_SignIn_Link);
        student_signin = findViewById(R.id.Student_SignIn_Btn);
        forgotp = findViewById(R.id.Forgot_Password);
        registrationNumberEditText = findViewById(R.id.Registration_No_Edit);
        passwordEditText = findViewById(R.id.Password_Edit);
        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
        faculty_login_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fac_login = new Intent(StudentLogin.this, FacultyLogin.class);
                startActivity(fac_login);
            }
        });

        forgotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgot_password = new Intent(StudentLogin.this, StudentForgotPassword.class);
                startActivity(forgot_password);
            }
        });


        student_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String registrationNumber = registrationNumberEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (registrationNumber.equals("")) {
                    registrationNumberEditText.setError("Empty Registration Number");
                    Toast.makeText(StudentLogin.this, "Enter Registration Number", Toast.LENGTH_SHORT).show();
                } else if (password.equals("")) {
                    passwordEditText.setError("Empty Password");
                    Toast.makeText(StudentLogin.this, "Enter Password", Toast.LENGTH_SHORT).show();
                } else {

                    Boolean checkCredentials = databaseManager.checkEmailPassword(registrationNumber, password);
                    if (checkCredentials) {
                        int studentId = databaseManager.getStudentId(registrationNumber, password);
                        if (studentId != -1) {

                            Toast.makeText(StudentLogin.this, "Logged in successfully!", Toast.LENGTH_SHORT).show();
                            SharedPreferences sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE);
                            sharedPreferences.edit().putString("LOGIN_USER", registrationNumber).apply();
                            sharedPreferences.edit().putString("LOGIN_TYPE", "Student").apply();
                            sharedPreferences.edit().putInt("STUDENT_ID", studentId).apply();
                            Intent intent = new Intent(StudentLogin.this, StudentDashboard.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(StudentLogin.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(StudentLogin.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
