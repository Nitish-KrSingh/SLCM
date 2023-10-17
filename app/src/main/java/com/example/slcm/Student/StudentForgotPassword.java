package com.example.slcm.Student;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.slcm.DatabaseManager;
import com.example.slcm.R;
import com.example.slcm.StudentLogin;

import java.util.Objects;

public class StudentForgotPassword extends AppCompatActivity {
    EditText NewPassword, ConfirmPassword, RegistrationNumber;
    Button ChangeBtn;
    DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_forgot_password);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Student - Forgot Password");

        RegistrationNumber = findViewById(R.id.Registration_No_Edit);
        NewPassword = findViewById(R.id.Stud_New_Password_Edit);
        ConfirmPassword = findViewById(R.id.Stud_Confirm_Password_Edit);
        ChangeBtn = findViewById(R.id.submit);
        databaseManager = new DatabaseManager(this);
        ChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String regNo = RegistrationNumber.getText().toString();
                String studForgotNewPass = NewPassword.getText().toString();
                String studForgotConfirmPass = ConfirmPassword.getText().toString();

                if (regNo.isEmpty()) {
                    Toast.makeText(StudentForgotPassword.this, "Enter Registration Number", Toast.LENGTH_SHORT).show();
                } else if (studForgotNewPass.isEmpty()) {
                    NewPassword.setError("Empty Confirm Password");
                    Toast.makeText(StudentForgotPassword.this, "Enter New Password", Toast.LENGTH_SHORT).show();
                } else if (studForgotConfirmPass.isEmpty()) {
                    ConfirmPassword.setError("Empty Confirm Password");
                    Toast.makeText(StudentForgotPassword.this, "Enter Confirm Password", Toast.LENGTH_SHORT).show();
                } else {
                    if (studForgotNewPass.equals(studForgotConfirmPass)) {
                        boolean updateStudentForgotPassword = databaseManager.updateStudentPassword(regNo, studForgotNewPass);
                        if (updateStudentForgotPassword) {
                            Toast.makeText(StudentForgotPassword.this, "Password changed successfully", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(StudentForgotPassword.this, "Invalid Registration Number ", Toast.LENGTH_SHORT).show();
                        }
                        Intent change_pass = new Intent(StudentForgotPassword.this, StudentLogin.class);
                        startActivity(change_pass);
                    } else {
                        NewPassword.setError("Password did not match");
                        ConfirmPassword.setError("Password did not match ");
                        Toast.makeText(StudentForgotPassword.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
