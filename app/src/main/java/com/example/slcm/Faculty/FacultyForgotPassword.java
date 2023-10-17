package com.example.slcm.Faculty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.slcm.DatabaseManager;
import com.example.slcm.FacultyLogin;
import com.example.slcm.R;

import java.util.Objects;

public class FacultyForgotPassword extends AppCompatActivity {

    EditText new_password, confirm_password, username;
    Button change_btn;
    DatabaseManager databaseManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_forgot_password);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Faculty - Forgot Password");

        databaseManager = new DatabaseManager(this);

        username = findViewById(R.id.Fac_Username_Edit);
        new_password = findViewById(R.id.Fac_New_Password_Edit);
        confirm_password = findViewById(R.id.Fac_Confirm_Password_Edit);
        change_btn = findViewById(R.id.submit);

        change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String fac_forgot_new_pass = new_password.getText().toString();
                String fac_forgot_confirm_pass = confirm_password.getText().toString();
                if (user.equals("")) {
                    new_password.setError("Empty Username");
                    Toast.makeText(FacultyForgotPassword.this, "Enter Username", Toast.LENGTH_SHORT).show();
                } else if (fac_forgot_new_pass.equals("")) {
                    new_password.setError("Empty New Password");
                    Toast.makeText(FacultyForgotPassword.this, "Enter New Password", Toast.LENGTH_SHORT).show();
                } else if (fac_forgot_confirm_pass.equals("")) {
                    confirm_password.setError("Empty Confirm Password");
                    Toast.makeText(FacultyForgotPassword.this, "Enter Confirm Password", Toast.LENGTH_SHORT).show();
                } else {
                    if (fac_forgot_new_pass.equals(fac_forgot_confirm_pass)) {
                        boolean updateFacultyForgotPassword = databaseManager.updateFacPassword(user, fac_forgot_new_pass);
                        if (updateFacultyForgotPassword) {
                            Toast.makeText(FacultyForgotPassword.this, "Password changed successfully", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(FacultyForgotPassword.this, "Invalid UserName", Toast.LENGTH_SHORT).show();
                        }
                        Intent change_pass = new Intent(FacultyForgotPassword.this, FacultyLogin.class);
                        startActivity(change_pass);
                    } else {
                        new_password.setError("Password did not match");
                        confirm_password.setError("Password did not match");
                        Toast.makeText(FacultyForgotPassword.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
