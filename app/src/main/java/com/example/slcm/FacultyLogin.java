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

import com.example.slcm.Faculty.FacultyDashboard;
import com.example.slcm.Faculty.FacultyForgotPassword;

public class FacultyLogin extends AppCompatActivity {

    TextView student_login_page, forgotp;
    Button faculty_signin;
    private EditText fac_username;
    private EditText fac_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_login);
        student_login_page = findViewById(R.id.Student_SignIn_Link);
        faculty_signin = findViewById(R.id.Faculty_SignIn_Btn);
        forgotp = findViewById(R.id.Faculty_Forgot_Password);
        fac_username = findViewById(R.id.Fac_Username_Edit);
        fac_password = findViewById(R.id.Fac_Password_Edit);

        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
        student_login_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s_login = new Intent(FacultyLogin.this, StudentLogin.class);
                startActivity(s_login);
            }
        });
        forgotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgot_password = new Intent(FacultyLogin.this, FacultyForgotPassword.class);
                startActivity(forgot_password);
            }

        });
        faculty_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fac_userid = fac_username.getText().toString();
                String fac_pass = fac_password.getText().toString();

                if (fac_userid.equals("")) {
                    fac_username.setError("Empty UserID");
                    Toast.makeText(FacultyLogin.this, "Enter UserID", Toast.LENGTH_SHORT).show();
                } else if (fac_pass.equals("")) {
                    fac_password.setError("Empty Password");
                    Toast.makeText(FacultyLogin.this, "Enter Password", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkCredentials = databaseManager.checkEmailPassword_for_fac(fac_userid, fac_pass);
                    if (checkCredentials) {
                        int facultyId = databaseManager.getFacultyId(fac_userid, fac_pass);
                        if (facultyId != -1) {

                            Toast.makeText(FacultyLogin.this, "Logged in successfully!", Toast.LENGTH_SHORT).show();
                            SharedPreferences sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE);
                            sharedPreferences.edit().putString("LOGIN_USER", fac_userid).apply();
                            sharedPreferences.edit().putString("LOGIN_TYPE", "Faculty").apply();
                            sharedPreferences.edit().putInt("FACULTY_ID", facultyId).apply();
                            Intent intent = new Intent(FacultyLogin.this, FacultyDashboard.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(FacultyLogin.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(FacultyLogin.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
