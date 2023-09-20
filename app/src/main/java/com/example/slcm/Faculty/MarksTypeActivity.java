package com.example.slcm.Faculty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.slcm.R;

public class MarksTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks_type);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Marks Input");

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