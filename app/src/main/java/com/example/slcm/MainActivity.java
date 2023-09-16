package com.example.slcm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView faculty_login_page ;
    TextView forgotp ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        faculty_login_page = findViewById(R.id.faculty_signin);
         forgotp = findViewById(R.id.forgot_password);

        faculty_login_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fac_login = new Intent(MainActivity.this , Faculty_login.class);
                startActivity(fac_login);
            }
        });

        forgotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent marks = new Intent(MainActivity.this , Internal_marks.class);
                startActivity(marks);
            }

        });

    }
}