package com.example.slcm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.slcm.Student.Internal_marks;
import com.example.slcm.Student.Student_Dashboard;

public class Student_Login extends AppCompatActivity {

    TextView faculty_login_page , student_signin;
    TextView forgotp ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        faculty_login_page = findViewById(R.id.faculty_signin);
         student_signin = findViewById(R.id.student_signin);
         forgotp = findViewById(R.id.forgot_password);

        faculty_login_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fac_login = new Intent(Student_Login.this , Faculty_login.class);
                startActivity(fac_login);
            }
        });

        forgotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent marks = new Intent(Student_Login.this , Internal_marks.class);
                startActivity(marks);
            }

        });

       student_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent marks = new Intent(Student_Login.this , Student_Dashboard.class);
                startActivity(marks);
            }

        });

    }
}