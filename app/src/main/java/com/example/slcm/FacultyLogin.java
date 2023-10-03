package com.example.slcm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.slcm.Faculty.FacultyDashboard;
import com.example.slcm.Faculty.FacultyForgotPassword;
import com.example.slcm.Student.StudentForgotPassword;

public class FacultyLogin extends AppCompatActivity {

    TextView student_login_page, forgotp ;
    Button faculty_signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_login);
        student_login_page = findViewById(R.id.Student_SignIn_Link);
        faculty_signin = findViewById(R.id.Faculty_SignIn_Btn);
        forgotp = findViewById(R.id.Faculty_Forgot_Password);

        student_login_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s_login = new Intent(FacultyLogin.this , StudentLogin.class);
                startActivity(s_login);
            }
        });
        forgotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgot_password = new Intent(FacultyLogin.this , FacultyForgotPassword.class);
                startActivity(forgot_password);
            }

        });
        faculty_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dashboard = new Intent(FacultyLogin.this , FacultyDashboard.class);
                startActivity(dashboard);
            }
        });

    }
}