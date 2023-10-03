package com.example.slcm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.slcm.Student.StudentForgotPassword;
import com.example.slcm.Student.StudentDashboard;

public class StudentLogin extends AppCompatActivity {

    TextView faculty_login_page, forgotp;
    Button student_signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        faculty_login_page = findViewById(R.id.Faculty_SignIn_Link);
         student_signin = findViewById(R.id.Student_SignIn_Btn);
         forgotp = findViewById(R.id.Forgot_Password);

        faculty_login_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fac_login = new Intent(StudentLogin.this , FacultyLogin.class);
                startActivity(fac_login);
            }
        });

        forgotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgot_password = new Intent(StudentLogin.this , StudentForgotPassword.class);
                startActivity(forgot_password);
            }

        });

       student_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dashboard = new Intent(StudentLogin.this , StudentDashboard.class);
                startActivity(dashboard);
            }

        });

    }
}