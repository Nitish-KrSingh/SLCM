package com.example.slcm.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.slcm.DatabaseManager;
import com.example.slcm.Faculty.FacultyChangePassword;
import com.example.slcm.R;

import java.util.Objects;


public class StudentChangePassword extends AppCompatActivity {

    private int loggedInStudentId;
    EditText student_new_password,student_confirm_password;
    Button student_change_submit_btn;

    DatabaseManager databaseManager = new DatabaseManager(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_change_password);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change Password");
        SharedPreferences sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE);
        loggedInStudentId = sharedPreferences.getInt("STUDENT_ID", -1);

        student_new_password = (EditText) findViewById(R.id.fac_new_password);
        student_confirm_password=(EditText) findViewById(R.id.fac_password);
        student_change_submit_btn=(Button) findViewById(R.id.submit);

        student_change_submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stud_new_password = student_new_password.getText().toString();
                String stud_confirm_password = student_confirm_password.getText().toString();

                if (stud_new_password.equals("")) {
                    student_new_password.setError("Empty New Password");
                    Toast.makeText(StudentChangePassword.this, "Enter New Password", Toast.LENGTH_SHORT).show();
                } else if (stud_confirm_password.equals("")) {
                    student_confirm_password.setError("Empty Confirm Password");
                    Toast.makeText(StudentChangePassword.this, "Enter Confirm Password", Toast.LENGTH_SHORT).show();
                } else {
                    if (stud_new_password.equals(stud_confirm_password)) {
                        boolean updateStudentPassword = databaseManager.update_Student_change_password(loggedInStudentId,stud_new_password);
                        if(updateStudentPassword) {
                            Toast.makeText(StudentChangePassword.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(StudentChangePassword.this, "Password did not changed ", Toast.LENGTH_SHORT).show();

                        }

                    }
                    else{
                        student_new_password.setError("Password did not matched");
                        student_confirm_password.setError("Password did not matched ");
                        Toast.makeText(StudentChangePassword.this, "Password did not matched", Toast.LENGTH_SHORT).show();
                    }
                }
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