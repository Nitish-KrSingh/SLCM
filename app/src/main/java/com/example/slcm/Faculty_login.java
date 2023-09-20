package com.example.slcm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.slcm.Faculty.Faculty_Dashboard;

public class Faculty_login extends AppCompatActivity {

    TextView student_login_page ;
    Button b_faculty_signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_login);
        student_login_page = findViewById(R.id.student_signin);
        b_faculty_signin = findViewById(R.id.b_faculty_signin);

        student_login_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s_login = new Intent(Faculty_login.this , Student_Login.class);
                startActivity(s_login);
            }
        });

        b_faculty_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s_login = new Intent(Faculty_login.this , Faculty_Dashboard.class);
                startActivity(s_login);
            }
        });


    }
}