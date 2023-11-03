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
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.slcm.DatabaseManager;
import com.example.slcm.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class FacultyProfile extends AppCompatActivity {

    private ListView facultyDetailsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_profile);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Your details");
        SharedPreferences sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE);
        int loggedInFacultyId = sharedPreferences.getInt("FACULTY_ID", -1);

        facultyDetailsListView = findViewById(R.id.facultyDetailsListView);

        displayFacultyDetails(loggedInFacultyId);
        FloatingActionButton fabMessage = findViewById(R.id.fabMessage);
        fabMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event to open the message activity.
                Intent intent = new Intent(FacultyProfile.this, FacultyChatViewStudent.class);
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

    private void displayFacultyDetails(int facultyId) {
        DatabaseManager dbHelper = new DatabaseManager(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor nameCursor = db.rawQuery("SELECT Name FROM FacultyProfile WHERE FacultyID = ?",  new String[]{String.valueOf(facultyId)});
        String facultyName = "";
        if (nameCursor.moveToFirst()) {
            facultyName = nameCursor.getString(nameCursor.getColumnIndexOrThrow("Name"));
        }
        TextView facultyNameTextView = findViewById(R.id.profname);
        facultyNameTextView.setText(facultyName);
        Cursor cursor = db.rawQuery("SELECT Username, Age, DOB, Gender, PhoneNumber, Email, Department, Designation, AcademicRole, AreasOfInterest FROM FacultyProfile WHERE FacultyID = ?",  new String[]{String.valueOf(facultyId)});
        ProfileAdapter adapter = new ProfileAdapter(this, cursor);
        facultyDetailsListView.setAdapter(adapter);
    }


}
