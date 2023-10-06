package com.example.slcm;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseManager extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "slcm_database";
    private static final int DATABASE_VERSION = 1;

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DatabaseStatus", "Creating database and tables");
        // Create your tables and initial dummy data here
        db.execSQL("CREATE TABLE IF NOT EXISTS UserProfile (" +
                "UserID INTEGER PRIMARY KEY," +
                "RegistrationNumber INTEGER," +
                "Username TEXT," +
                "Password TEXT," +
                "UserType TEXT," +
                "Name TEXT," +
                "DOB DATE," +
                "Age INTEGER," +
                "OtherDetails TEXT," +
                "Semester INTEGER," +
                "Department TEXT," +
                "ClassID INTEGER," +
                "FOREIGN KEY (ClassID) REFERENCES Class(ClassID));");

        db.execSQL("CREATE TABLE IF NOT EXISTS Class (" +
                "ClassID INTEGER PRIMARY KEY," +
                "ClassName TEXT," +
                "Section TEXT);");

        db.execSQL("CREATE TABLE IF NOT EXISTS Attendance (" +
                "AttendanceID INTEGER PRIMARY KEY," +
                "Date DATE," +
                "ClassID INTEGER," +
                "StudentID INTEGER," +
                "Status TEXT," +
                "FOREIGN KEY (StudentID) REFERENCES UserProfile(UserID)," +
                "FOREIGN KEY (ClassID) REFERENCES Class(ClassID));");

        db.execSQL("CREATE TABLE IF NOT EXISTS Marks (" +
                "MarksID INTEGER PRIMARY KEY," +
                "Date DATE," +
                "ClassID INTEGER," +
                "StudentID INTEGER," +
                "Subject TEXT," +
                "Assignment1 INTEGER," +
                "Assignment2 INTEGER," +
                "Assignment3 INTEGER," +
                "Assignment4 INTEGER," +
                "Midterm INTEGER," +
                "TotalMarks INTEGER," +
                "FOREIGN KEY (StudentID) REFERENCES UserProfile(UserID)," +
                "FOREIGN KEY (ClassID) REFERENCES Class(ClassID));");

        db.execSQL("CREATE TABLE IF NOT EXISTS Announcements (" +
                "AnnouncementID INTEGER PRIMARY KEY," +
                "Title TEXT," +
                "Message TEXT," +
                "Date DATE," +
                "UserID INTEGER," +
                "FOREIGN KEY (UserID) REFERENCES UserProfile(UserID));");

        db.execSQL("CREATE TABLE IF NOT EXISTS Fees (" +
                "FeesID INTEGER PRIMARY KEY," +
                "DatePaid DATE," +
                "Amount REAL," +
                "Purpose TEXT," +
                "StudentID INTEGER," +
                "FOREIGN KEY (StudentID) REFERENCES UserProfile(UserID));");
        insertDummyData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades if needed
        Log.d("DatabaseStatus", "Upgrading database from version " + oldVersion + " to " + newVersion);
    }
    private void insertDummyData(SQLiteDatabase db) {
        // Insert dummy data into UserProfile table
        ContentValues userValues = new ContentValues();
        userValues.put("RegistrationNumber", 123456);
        userValues.put("Username", "faculty1");
        userValues.put("Password", "faculty_password");
        userValues.put("UserType", "Faculty");
        userValues.put("Name", "Faculty One");
        userValues.put("DOB", "1990-01-01");
        userValues.put("Age", 30);
        userValues.put("OtherDetails", "Additional faculty details");
        userValues.put("Semester", 0);
        userValues.put("Department", "Computer Science");
        userValues.put("ClassID", 1);
        db.insert("UserProfile", null, userValues);

        // Insert dummy data into Class table
        ContentValues classValues = new ContentValues();
        classValues.put("ClassName", "ClassA");
        classValues.put("Section", "Section1");
        db.insert("Class", null, classValues);

        // Insert dummy data into Attendance table
        ContentValues attendanceValues = new ContentValues();
        attendanceValues.put("Date", "2023-01-01");
        attendanceValues.put("ClassID", 1);
        attendanceValues.put("StudentID", 1); // Reference to UserProfile UserID
        attendanceValues.put("Status", "Present");
        db.insert("Attendance", null, attendanceValues);

        // Insert dummy data into Marks table
        ContentValues marksValues = new ContentValues();
        marksValues.put("Date", "2023-01-01");
        marksValues.put("ClassID", 1);
        marksValues.put("StudentID", 1); // Reference to UserProfile UserID
        marksValues.put("Subject", "Math");
        marksValues.put("Assignment1", 90);
        marksValues.put("Assignment2", 85);
        marksValues.put("Assignment3", 92);
        marksValues.put("Assignment4", 88);
        marksValues.put("Midterm", 95);
        marksValues.put("TotalMarks", 450);
        db.insert("Marks", null, marksValues);

        // Insert dummy data into Announcements table
        ContentValues announcementValues = new ContentValues();
        announcementValues.put("Title", "Important Announcement");
        announcementValues.put("Message", "This is an important announcement for all students.");
        announcementValues.put("Date", "2023-01-01");
        announcementValues.put("UserID", 1); // Reference to UserProfile UserID
        db.insert("Announcements", null, announcementValues);

        // Insert dummy data into Fees table
        ContentValues feesValues = new ContentValues();
        feesValues.put("DatePaid", "2023-01-01");
        feesValues.put("Amount", 500.0);
        feesValues.put("Purpose", "Course");
        feesValues.put("StudentID", 1); // Reference to UserProfile UserID
        db.insert("Fees", null, feesValues);
    }
}
