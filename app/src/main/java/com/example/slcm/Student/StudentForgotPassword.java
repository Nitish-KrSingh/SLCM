package com.example.slcm.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.slcm.DatabaseManager;
import com.example.slcm.Faculty.FacultyForgotPassword;
import com.example.slcm.R;

import java.util.Objects;

public class StudentForgotPassword extends AppCompatActivity {

    EditText student_forgot_registration_number,student_forgot_new_password,student_forgot_confirm_password;
    Button student_forgot_change_btn;
    DatabaseManager databaseManager = new DatabaseManager(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_forgot_password);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Student - Forgot Password");

        student_forgot_registration_number=(EditText)findViewById(R.id.Stud_Username_Edit) ;
        student_forgot_new_password=(EditText)findViewById(R.id.New_Password_Edit) ;
        student_forgot_confirm_password=(EditText) findViewById(R.id.Confirm_Password_Edit);
        student_forgot_change_btn=(Button) findViewById(R.id.submit);

        student_forgot_change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String inputText = student_forgot_registration_number.getText().toString();
                int stud_forgot_registration_number = Integer.parseInt(inputText);
                String stud_forgot_new_pass = student_forgot_new_password.getText().toString();
                String stud_forgot_confirm_pass = student_forgot_confirm_password.getText().toString();

                if (student_forgot_registration_number==null) {
                    Toast.makeText(StudentForgotPassword.this, "Enter Registration Number", Toast.LENGTH_SHORT).show();
                } else if (stud_forgot_new_pass.equals("")) {
                    student_forgot_new_password.setError("Empty Confirm Password");
                    Toast.makeText(StudentForgotPassword.this, "Enter New Password", Toast.LENGTH_SHORT).show();
                } else if (stud_forgot_confirm_pass.equals("")) {
                    student_forgot_confirm_password.setError("Empty Confirm Password");
                    Toast.makeText(StudentForgotPassword.this, "Enter Confirm Password", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (stud_forgot_new_pass.equals(stud_forgot_confirm_pass)) {
                        boolean updateStudentForgotPassword = databaseManager.updateStud_Forgot_password(stud_forgot_registration_number,stud_forgot_new_pass);
                        if(updateStudentForgotPassword) {
                            Toast.makeText(StudentForgotPassword.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(StudentForgotPassword.this, "Invalid Registration Number ", Toast.LENGTH_SHORT).show();

                        }

                    }
                    else{
                        student_forgot_new_password.setError("Password did not matched");
                        student_forgot_confirm_password.setError("Password did not matched ");
                        Toast.makeText(StudentForgotPassword.this, "Password did not matched", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }
}