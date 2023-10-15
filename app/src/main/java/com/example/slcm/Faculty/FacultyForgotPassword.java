package com.example.slcm.Faculty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.slcm.DatabaseManager;
import com.example.slcm.R;

import java.util.Objects;

public class FacultyForgotPassword extends AppCompatActivity {

    EditText fac_forgot_username,fac_forgot_new_password,fac_forgot_confirm_password;
    Button fac_forgot_change_btn;
    DatabaseManager databaseManager = new DatabaseManager(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_forgot_password);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Faculty - Forgot Password");

        fac_forgot_username=(EditText)findViewById(R.id.Stud_Username_Edit) ;
        fac_forgot_new_password=(EditText)findViewById(R.id.Fac_New_Password_Edit) ;
        fac_forgot_confirm_password=(EditText) findViewById(R.id.Fac_Confirm_Password_Edit);
        fac_forgot_change_btn=(Button) findViewById(R.id.submit);

        fac_forgot_change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fac_forgot_user = fac_forgot_username.getText().toString();
                String fac_forgot_new_pass = fac_forgot_new_password.getText().toString();
                String fac_forgot_confirm_pass = fac_forgot_confirm_password.getText().toString();

                if (fac_forgot_user.equals("")) {
                    fac_forgot_username.setError("Empty New Password");
                    Toast.makeText(FacultyForgotPassword.this, "Enter UserName", Toast.LENGTH_SHORT).show();
                } else if (fac_forgot_new_pass.equals("")) {
                    fac_forgot_new_password.setError("Empty Confirm Password");
                    Toast.makeText(FacultyForgotPassword.this, "Enter New Password", Toast.LENGTH_SHORT).show();
                } else if (fac_forgot_confirm_pass.equals("")) {
                    fac_forgot_confirm_password.setError("Empty Confirm Password");
                    Toast.makeText(FacultyForgotPassword.this, "Enter Confirm Password", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (fac_forgot_new_pass.equals(fac_forgot_confirm_pass)) {
                        boolean updateFacultyForgotPassword = databaseManager.updateFac_Forgot_password(fac_forgot_user,fac_forgot_new_pass);
                        if(updateFacultyForgotPassword) {
                            Toast.makeText(FacultyForgotPassword.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(FacultyForgotPassword.this, "Invalid User Name ", Toast.LENGTH_SHORT).show();

                        }

                        }
                    else{
                        fac_forgot_new_password.setError("Password did not matched");
                        fac_forgot_confirm_password.setError("Password did not matched ");
                        Toast.makeText(FacultyForgotPassword.this, "Password did not matched", Toast.LENGTH_SHORT).show();
                    }

                }

                }
        });



    }
}