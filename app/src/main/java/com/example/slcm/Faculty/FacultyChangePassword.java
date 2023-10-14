package com.example.slcm.Faculty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.slcm.DatabaseManager;
import com.example.slcm.FacultyLogin;
import com.example.slcm.R;

import java.util.Objects;

public class FacultyChangePassword extends AppCompatActivity {
    private int loggedInFacultyId;
    EditText faculty_new_password,faculty_confirm_password;
    Button faculty_change_submit_btn;
//    DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
    DatabaseManager databaseManager = new DatabaseManager(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_change_password);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change Password");
        SharedPreferences sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE);
        loggedInFacultyId = sharedPreferences.getInt("FACULTY_ID", -1); // Get the FacultyID


        faculty_new_password=(EditText) findViewById(R.id.fac_new_password);
        faculty_confirm_password=(EditText) findViewById(R.id.fac_password);
        faculty_change_submit_btn=(Button) findViewById(R.id.submit);
       // int facultyId = getIntent().getIntExtra("FACULTY_ID", -1);

        faculty_change_submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fac_new_password = faculty_new_password.getText().toString();
                String fac_confirm_password = faculty_confirm_password.getText().toString();

                if (fac_new_password.equals("")) {
                    faculty_new_password.setError("Empty New Password");
                    Toast.makeText(FacultyChangePassword.this, "Enter New Password", Toast.LENGTH_SHORT).show();
                } else if (fac_confirm_password.equals("")) {
                    faculty_confirm_password.setError("Empty Confirm Password");
                    Toast.makeText(FacultyChangePassword.this, "Enter Confirm Password", Toast.LENGTH_SHORT).show();
                } else {
                    if (fac_new_password.equals(fac_confirm_password)) {
                        boolean updateFacultyPassword = databaseManager.updateFac_change_password(loggedInFacultyId,fac_new_password);
                        if(updateFacultyPassword) {
                            Toast.makeText(FacultyChangePassword.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(FacultyChangePassword.this, "Password did not changed ", Toast.LENGTH_SHORT).show();

                        }

                    }
                    else{
                        faculty_new_password.setError("Password did not matched");
                        faculty_confirm_password.setError("Password did not matched ");
                        Toast.makeText(FacultyChangePassword.this, "Password did not matched", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.faculty_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FacultyMenuHandler.handleMenuAction(item, this);
        return super.onOptionsItemSelected(item);
    }

}