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
        db.execSQL("CREATE TABLE IF NOT EXISTS FacultyProfile (" +
                "UserID INTEGER PRIMARY KEY," +
                "Username TEXT," +
                "Password TEXT," +
                "UserType TEXT," +
                "Name TEXT," +
                "DOB DATE," +
                "Age INTEGER," +
                "Department TEXT," +
                "ClassID INTEGER," +
                "FOREIGN KEY (ClassID) REFERENCES Class(ClassID));");

        db.execSQL("CREATE TABLE IF NOT EXISTS StudentProfile (" +
                "UserID INTEGER PRIMARY KEY," +
                "RegistrationNumber INTEGER," +
                "Password TEXT," +
                "UserType TEXT," +
                "Name TEXT," +
                "DOB DATE," +
                "Age INTEGER," +
                "Semester INTEGER," +
                "Department TEXT," +
                "ClassID INTEGER," +
                "FOREIGN KEY (ClassID) REFERENCES Class(ClassID));");

        db.execSQL("CREATE TABLE IF NOT EXISTS FacultyClassAssignment (" +
                "AssignmentID INTEGER PRIMARY KEY," +
                "FacultyID INTEGER," +
                "ClassID INTEGER," +
                "FOREIGN KEY (FacultyID) REFERENCES FacultyProfile(UserID)," +
                "FOREIGN KEY (ClassID) REFERENCES Class(ClassID));");

        db.execSQL("CREATE TABLE IF NOT EXISTS Class (" +
                "ClassID INTEGER PRIMARY KEY," +
                "ClassName TEXT," +
                "Section TEXT," +
                "Semester INTEGER);");

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

        // Insert dummy data into FacultyProfile table
        ContentValues facultyValues = new ContentValues();
        facultyValues.put("Username", "XYZ");
        facultyValues.put("Password", "faculty_password");
        facultyValues.put("UserType", "Faculty");
        facultyValues.put("Name", "XYZ");
        facultyValues.put("DOB", "1990-01-01");
        facultyValues.put("Age", 30);
        facultyValues.put("Department", "Computer Science");
        facultyValues.put("ClassID", 1);
        long facultyId = db.insert("FacultyProfile", null, facultyValues);

        // Insert dummy data into StudentProfile table
        ContentValues studentValues = new ContentValues();
        studentValues.put("RegistrationNumber", 220970054);
        studentValues.put("Password", "student_password");
        studentValues.put("UserType", "Student");
        studentValues.put("Name", "ABC");
        studentValues.put("DOB", "2000-01-01");
        studentValues.put("Age", 20);
        studentValues.put("Semester", 2);
        studentValues.put("Department", "Computer Science");
        studentValues.put("ClassID", 1);
        db.insert("StudentProfile", null, studentValues);

        // Insert dummy data into FacultyClassAssignment table
        ContentValues assignmentValues1 = new ContentValues();
        assignmentValues1.put("FacultyID", facultyId);
        assignmentValues1.put("ClassID", 1);
        db.insert("FacultyClassAssignment", null, assignmentValues1);

        ContentValues assignmentValues2 = new ContentValues();
        assignmentValues2.put("FacultyID", facultyId);
        assignmentValues2.put("ClassID", 2);
        db.insert("FacultyClassAssignment", null, assignmentValues2);

        // Insert dummy data into Class table
        ContentValues classValues1 = new ContentValues();
        classValues1.put("ClassName", "MCA");
        classValues1.put("Section", "A");
        classValues1.put("Semester", 3);
        db.insert("Class", null, classValues1);

        ContentValues classValues2 = new ContentValues();
        classValues2.put("ClassName", "MCA");
        classValues2.put("Section", "B");
        classValues2.put("Semester", 3);
        db.insert("Class", null, classValues2);

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
