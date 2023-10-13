package com.example.slcm.Faculty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.slcm.DatabaseManager;
import com.example.slcm.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FacultyMarksSubject extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_marks_subject);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Marks - Select Subject");
        SharedPreferences sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE);
        String fac_name = sharedPreferences.getString("LOGIN_USER","");
        DatabaseManager db = new DatabaseManager(this);
        String selecteddate = getIntent().getStringExtra("class_name");
        String selectedclass = getIntent().getStringExtra("s_date");

        List<String> assignedClasses = db.getSubjectsForClassAndFaculty(selectedclass,fac_name);

        ArrayAdapter<String> classAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, assignedClasses);
        ListView classListView = findViewById(R.id.subjectListView);
        classListView.setAdapter(classAdapter);

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