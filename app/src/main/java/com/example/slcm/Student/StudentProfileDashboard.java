package com.example.slcm.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.slcm.Faculty.FacultyChangePassword;
import com.example.slcm.Faculty.FacultyProfileDashboard;
import com.example.slcm.R;

import java.util.Objects;

public class StudentProfileDashboard extends AppCompatActivity {
    Button std_change_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile_dashboard);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");


        std_change_btn=(Button) findViewById(R.id.btnMcaB);

        std_change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fac_change_pass=new Intent(StudentProfileDashboard.this, StudentChangePassword.class);
                startActivity(fac_change_pass);
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