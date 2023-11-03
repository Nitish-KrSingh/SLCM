package com.example.slcm.Faculty;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.slcm.DatabaseManager;
import com.example.slcm.FacultyLogin;
import com.example.slcm.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class FacultyProfileDashboard extends AppCompatActivity {

    private static void openActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_profile_dashboard);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");
        Button fac_view_details = findViewById(R.id.FacProfileViewDetails);
        Button fac_change_pass = findViewById(R.id.FacProfileChangePassword);
        Button fac_logout = findViewById(R.id.FacProfileLogout);
        SharedPreferences sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE);
        int loggedInFacultyId = sharedPreferences.getInt("FACULTY_ID", -1);
        DatabaseManager dbHelper = new DatabaseManager(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor nameCursor = db.rawQuery("SELECT Name FROM FacultyProfile WHERE FacultyID = ?",  new String[]{String.valueOf(loggedInFacultyId)});
        String facultyName = "";
        if (nameCursor.moveToFirst()) {
            facultyName = nameCursor.getString(nameCursor.getColumnIndexOrThrow("Name"));
        }
        TextView facultyNameTextView = findViewById(R.id.profname);
        facultyNameTextView.setText(facultyName);
        fac_view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fac_view_details = new Intent(FacultyProfileDashboard.this, FacultyProfile.class);
                startActivity(fac_view_details);
            }
        });
        fac_change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fac_change_pass = new Intent(FacultyProfileDashboard.this, FacultyChangePassword.class);
                startActivity(fac_change_pass);
            }
        });
        fac_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE);
                sharedPreferences.edit().putString("LOGIN_USER", "").apply();
                openActivity(FacultyProfileDashboard.this, FacultyLogin.class);
            }
        });
        FloatingActionButton fabMessage = findViewById(R.id.fabMessage);
        fabMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event to open the message activity.
                Intent intent = new Intent(FacultyProfileDashboard.this, FacultyChatViewStudent.class);
                startActivity(intent);
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