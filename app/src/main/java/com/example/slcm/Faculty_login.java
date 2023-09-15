package com.example.slcm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Faculty_login extends AppCompatActivity {

    TextView student_login_page ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_login);
        student_login_page = findViewById(R.id.student_signin);

        student_login_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s_login = new Intent(Faculty_login.this , MainActivity.class);
                startActivity(s_login);
            }
        });

    }
}