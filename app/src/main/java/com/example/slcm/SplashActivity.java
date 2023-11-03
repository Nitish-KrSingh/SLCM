package com.example.slcm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.slcm.Faculty.FacultyDashboard;
import com.example.slcm.Student.StudentDashboard;

public class SplashActivity extends AppCompatActivity {
    private DatabaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        db = new DatabaseManager(this);
        if (!doesDatabaseExist()) {
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
                if (fetchData()) {
                    return;
                }
                Intent intent = new Intent(SplashActivity.this, StudentLogin.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }

    private boolean doesDatabaseExist() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(getDatabasePath(DatabaseManager.DATABASE_NAME).getPath(), null, SQLiteDatabase.OPEN_READONLY);
            if (checkDB != null) {
                checkDB.close();
            }
            if (checkDB != null) {
                Log.d("DatabaseStatus", "Database exists.");
            } else {
                Log.d("DatabaseStatus", "Database does not exist.");
            }
            return checkDB != null;
        } catch (SQLiteCantOpenDatabaseException e) {
            Log.e("DatabaseError", "Error opening database: " + e.getMessage());
            Log.d("DatabaseStatus", "Database does not exist.");
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null) {
            db.close();
        }
    }

    boolean fetchData() {
        SharedPreferences sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE);
        String data = sharedPreferences.getString("LOGIN_USER", "");
        String type = sharedPreferences.getString("LOGIN_TYPE", "");
        Log.d("LOGIN_USER", "fetchData: " + data);
        Log.d("LOGIN_TYPE", "fetchData: " + type);
        if (!data.isEmpty()) {
            if (type.equals("Student")) {
                Intent intent = new Intent(SplashActivity.this, StudentDashboard.class);
                startActivity(intent);
                finish();
                return true;
            } else if (type.equals("Faculty")) {
                Intent intent = new Intent(SplashActivity.this, FacultyDashboard.class);
                startActivity(intent);
                finish();
                return true;
            }
        }
        return false;
    }
}



