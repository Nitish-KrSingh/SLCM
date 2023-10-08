package com.example.slcm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.os.Bundle;
import android.os.Handler;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;
import android.content.Intent;

import com.example.slcm.Student.StudentDashboard;

public class SplashActivity extends AppCompatActivity {
    private DatabaseManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // Initialize the database helper
        db = new DatabaseManager(this);
        // Check if the database already exists
        if (!doesDatabaseExist()) {
            // Database doesn't exist, handle the error gracefully
            Log.e("DatabaseError", "Database doesn't exist, creating tables and dummy data");
            try {
                db.getWritableDatabase(); // This will call onCreate method in MyDatabaseHelper
                Toast.makeText(this, "Database created with dummy data", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                // Handle any further exception that might occur during database creation
                Log.e("DatabaseError", "Error creating database: " + e.getMessage());
            }
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(fetchData()){
                    return;
                }
                Intent intent = new Intent(SplashActivity.this, StudentLogin.class);
                startActivity(intent);
                finish();
            }
        }, 3000);





    }
    // Check if the database file exists
    private boolean doesDatabaseExist() {
        SQLiteDatabase checkDB = null;
        try {
            // Attempt to open the database
            checkDB = SQLiteDatabase.openDatabase(getDatabasePath(DatabaseManager.DATABASE_NAME).getPath(), null, SQLiteDatabase.OPEN_READONLY);
            // Close the database if it was successfully opened
            if (checkDB != null) {
                checkDB.close();
            }
            // Log a message indicating whether the database exists
            if (checkDB != null) {
                Log.d("DatabaseStatus", "Database exists.");
            } else {
                Log.d("DatabaseStatus", "Database does not exist.");
            }
            return checkDB != null;
        } catch (SQLiteCantOpenDatabaseException e) {
            // Handle the exception here, for example, by creating the database
            Log.e("DatabaseError", "Error opening database: " + e.getMessage());

            // Log a message indicating that the database does not exist
            Log.d("DatabaseStatus", "Database does not exist.");

            // You can add code here to create the database or take other actions
            return false;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Close the database when the activity is destroyed
        if (db != null) {
            db.close();
        }
    }

    boolean fetchData(){
        SharedPreferences sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE);
        String data = sharedPreferences.getString("LOGIN_USER","");
        Log.d("LOGIN_USER", "fetchData: "+data);
        if(!data.isEmpty()){
            Intent intent = new Intent(SplashActivity.this, StudentDashboard.class);
            startActivity(intent);
            finish();
            return true;
        }
        return false;

    }

}



