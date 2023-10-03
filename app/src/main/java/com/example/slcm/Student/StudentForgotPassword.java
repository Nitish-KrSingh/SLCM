package com.example.slcm.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.slcm.R;

import java.util.Objects;

public class StudentForgotPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_forgot_password);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Student - Forgot Password");
    }
}