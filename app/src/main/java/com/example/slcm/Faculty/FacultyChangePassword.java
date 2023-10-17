package com.example.slcm.Faculty;

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

import com.example.slcm.DatabaseManager;
import com.example.slcm.R;

import java.util.Objects;

public class FacultyChangePassword extends AppCompatActivity {
    EditText new_password, confirm_password;
    Button change_submit_btn;
    DatabaseManager databaseManager;
    private String loggedInUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_change_password);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change Password");
        SharedPreferences sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE);
        loggedInUsername = sharedPreferences.getString("LOGIN_USER", "");
        TextView facUsername = findViewById(R.id.Fac_Username);
        new_password = findViewById(R.id.Fac_New_Password_Edit);
        confirm_password = findViewById(R.id.Fac_Confirm_Password_Edit);
        change_submit_btn = findViewById(R.id.submit);
        facUsername.setText(loggedInUsername);
        databaseManager = new DatabaseManager(this);

        change_submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fac_new_password = new_password.getText().toString();
                String fac_confirm_password = confirm_password.getText().toString();

                if (fac_new_password.equals("")) {
                    new_password.setError("Empty New Password");
                    Toast.makeText(FacultyChangePassword.this, "Enter New Password", Toast.LENGTH_SHORT).show();
                } else if (fac_confirm_password.equals("")) {
                    confirm_password.setError("Empty Confirm Password");
                    Toast.makeText(FacultyChangePassword.this, "Enter Confirm Password", Toast.LENGTH_SHORT).show();
                } else {
                    if (fac_new_password.equals(fac_confirm_password)) {
                        boolean updateFacultyPassword = databaseManager.updateFacPassword(loggedInUsername, fac_new_password);
                        if (updateFacultyPassword) {
                            Toast.makeText(FacultyChangePassword.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(FacultyChangePassword.this, "Password did not change", Toast.LENGTH_SHORT).show();
                        }
                        Intent change_pass = new Intent(FacultyChangePassword.this, FacultyProfileDashboard.class);
                        startActivity(change_pass);
                    } else {
                        new_password.setError("Password did not match");
                        confirm_password.setError("Password did not match");
                        Toast.makeText(FacultyChangePassword.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
