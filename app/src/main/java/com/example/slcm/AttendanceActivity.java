package com.example.slcm;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AttendanceActivity extends AppCompatActivity {

    private ListView listView;
    private CustomListAdapter adapter; // Use your custom adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Attendance");

        listView = findViewById(R.id.students);

        // Sample list of roll numbers
        String[] rollNumbers = {"220970030", "220970031", "220970032"};

        // Initialize the custom adapter
        adapter = new CustomListAdapter(this, rollNumbers);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ham, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_item1) {
            Toast.makeText(this, "Clicked on about ", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menu_item2) {
            Toast.makeText(this, "Clicked on setting ", Toast.LENGTH_SHORT).show();
        }


        return super.onOptionsItemSelected(item);


    }
}