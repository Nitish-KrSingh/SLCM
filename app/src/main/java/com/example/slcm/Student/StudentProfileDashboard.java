package com.example.slcm.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.slcm.Faculty.FacultyChangePassword;
import com.example.slcm.Faculty.FacultyMenuHandler;
import com.example.slcm.Faculty.FacultyProfile;
import com.example.slcm.Faculty.FacultyProfileDashboard;
import com.example.slcm.FacultyLogin;
import com.example.slcm.R;
import com.example.slcm.StudentLogin;

import java.util.Objects;

public class StudentProfileDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile_dashboard);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");
        Button fac_view_details=findViewById(R.id.ProfileViewDetails);
        TextView regNo=findViewById(R.id.Registration_No_Text);
        Button fac_change_pass=findViewById(R.id.ProfileChangePassword);
        Button fac_logout=findViewById(R.id.ProfileLogout);
        SharedPreferences sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE);
        String loggedInRegNo = sharedPreferences.getString("LOGIN_USER", "");
        regNo.setText(loggedInRegNo);
        fac_view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent view_details=new Intent(StudentProfileDashboard.this, StudentProfile.class);
                startActivity(view_details);
            }
        });
        fac_change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent change_pass=new Intent(StudentProfileDashboard.this,StudentChangePassword.class);
                startActivity(change_pass);
            }
        });
        fac_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE);
                sharedPreferences.edit().putString("LOGIN_USER", "").apply();
                openActivity(StudentProfileDashboard.this, StudentLogin.class);
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
        FacultyMenuHandler.handleMenuAction(item, this);
        return super.onOptionsItemSelected(item);
    }
    private static void openActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }
}