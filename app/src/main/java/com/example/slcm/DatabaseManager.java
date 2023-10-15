package com.example.slcm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseManager extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "slcm_database";
    private static final int DATABASE_VERSION = 1;

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    SQLiteDatabase db = this.getWritableDatabase();

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DatabaseStatus", "Creating database and tables");
        db.execSQL("CREATE TABLE IF NOT EXISTS FacultyProfile (" +
                "FacultyID INTEGER PRIMARY KEY," +
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
                "StudentID INTEGER PRIMARY KEY," +
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

        db.execSQL("CREATE TABLE IF NOT EXISTS ClassAssignment (" +
                "AssignmentID INTEGER PRIMARY KEY," +
                "FacultyID INTEGER," +
                "ClassID INTEGER," +
                "SubjectID INTEGER," +
                "FOREIGN KEY (FacultyID) REFERENCES FacultyProfile(UserID)," +
                "FOREIGN KEY (ClassID) REFERENCES Class(ClassID)," +
                "FOREIGN KEY (SubjectID) REFERENCES Subjects(SubjectID));");

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
                "FOREIGN KEY (StudentID) REFERENCES StudentProfile(UserID)," + // Corrected table name
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
                "FOREIGN KEY (StudentID) REFERENCES StudentProfile(UserID)," + // Corrected table name
                "FOREIGN KEY (ClassID) REFERENCES Class(ClassID));");

        db.execSQL("CREATE TABLE IF NOT EXISTS Announcements (" +
                "AnnouncementID INTEGER PRIMARY KEY," +
                "Title TEXT," +
                "Message TEXT," +
                "Date DATE," +
                "UserId INTEGER," +
                "FOREIGN KEY (UserId) REFERENCES FacultyProfile(FacultyID));"); // Corrected table name

        db.execSQL("CREATE TABLE IF NOT EXISTS Fees (" +
                "FeesID INTEGER PRIMARY KEY," +
                "DatePaid DATE," +
                "Amount REAL," +
                "Purpose TEXT," +
                "StudentID INTEGER," +
                "FOREIGN KEY (StudentID) REFERENCES StudentProfile(UserID));"); // Corrected table name

        db.execSQL("CREATE TABLE IF NOT EXISTS Subjects (" +
                "SubjectID INTEGER PRIMARY KEY," +
                "SubjectName TEXT);");
        insertDummyData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DatabaseStatus", "Upgrading database from version " + oldVersion + " to " + newVersion);
    }

    private void insertDummyData(SQLiteDatabase db) {
        ContentValues facultyValues1 = new ContentValues();
        facultyValues1.put("Username", "XYZ");
        facultyValues1.put("Password", "12345");
        facultyValues1.put("UserType", "Faculty");
        facultyValues1.put("Name", "Faculty One");
        facultyValues1.put("DOB", "1990-01-01");
        facultyValues1.put("Age", 30);
        facultyValues1.put("Department", "Computer Science");
        facultyValues1.put("ClassID", 1);
        long facultyId1 = db.insert("FacultyProfile", null, facultyValues1);

        ContentValues facultyValues2 = new ContentValues();
        facultyValues2.put("Username", "PQR");
        facultyValues2.put("Password", "12345");
        facultyValues2.put("UserType", "Faculty");
        facultyValues2.put("Name", "Faculty Two");
        facultyValues2.put("DOB", "1985-05-15");
        facultyValues2.put("Age", 36);
        facultyValues2.put("Department", "Computer Science");
        facultyValues2.put("ClassID", 2);
        long facultyId2 = db.insert("FacultyProfile", null, facultyValues2);

        ContentValues studentValues1 = new ContentValues();
        studentValues1.put("RegistrationNumber", 220970054);
        studentValues1.put("Password", "12345");
        studentValues1.put("UserType", "Student");
        studentValues1.put("Name", "Nitish");
        studentValues1.put("DOB", "2000-01-01");
        studentValues1.put("Age", 20);
        studentValues1.put("Semester", 2);
        studentValues1.put("Department", "Computer Science");
        studentValues1.put("ClassID", 1);
        long studentId1 = db.insert("StudentProfile", null, studentValues1);

        ContentValues studentValues2 = new ContentValues();
        studentValues2.put("RegistrationNumber", 220970055);
        studentValues2.put("Password", "12345");
        studentValues2.put("UserType", "Student");
        studentValues2.put("Name", "Misha");
        studentValues2.put("DOB", "1999-03-10");
        studentValues2.put("Age", 22);
        studentValues2.put("Semester", 3);
        studentValues2.put("Department", "Mathematics");
        studentValues2.put("ClassID", 2);
        long studentId2 = db.insert("StudentProfile", null, studentValues2);

        ContentValues subjectValues1 = new ContentValues();
        subjectValues1.put("SubjectName", "OOPS");
        db.insert("Subjects", null, subjectValues1);

        ContentValues subjectValues2 = new ContentValues();
        subjectValues2.put("SubjectName", "Java");
        db.insert("Subjects", null, subjectValues2);

        ContentValues classAssignmentValues1 = new ContentValues();
        classAssignmentValues1.put("FacultyID", facultyId1);
        classAssignmentValues1.put("ClassID", 1);
        classAssignmentValues1.put("SubjectID", 1);
        db.insert("ClassAssignment", null, classAssignmentValues1);

        ContentValues classAssignmentValues2 = new ContentValues();
        classAssignmentValues2.put("FacultyID", facultyId2);
        classAssignmentValues2.put("ClassID", 2);
        classAssignmentValues2.put("SubjectID", 2);
        db.insert("ClassAssignment", null, classAssignmentValues2);

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

        ContentValues attendanceValues1 = new ContentValues();
        attendanceValues1.put("Date", "2023-01-01");
        attendanceValues1.put("ClassID", 1);
        attendanceValues1.put("StudentID", studentId1);
        attendanceValues1.put("Status", "Present");
        db.insert("Attendance", null, attendanceValues1);

        ContentValues attendanceValues2 = new ContentValues();
        attendanceValues2.put("Date", "2023-01-02");
        attendanceValues2.put("ClassID", 2);
        attendanceValues2.put("StudentID", studentId2);
        attendanceValues2.put("Status", "Absent");
        db.insert("Attendance", null, attendanceValues2);

        ContentValues marksValues1 = new ContentValues();
        marksValues1.put("Date", "2023-01-01");
        marksValues1.put("ClassID", 1);
        marksValues1.put("StudentID", studentId1);
        marksValues1.put("Subject", "Math");
        marksValues1.put("Assignment1", 90);
        marksValues1.put("Assignment2", 85);
        marksValues1.put("Assignment3", 92);
        marksValues1.put("Assignment4", 88);
        marksValues1.put("Midterm", 95);
        marksValues1.put("TotalMarks", 450);
        db.insert("Marks", null, marksValues1);

        ContentValues marksValues2 = new ContentValues();
        marksValues2.put("Date", "2023-01-02");
        marksValues2.put("ClassID", 2);
        marksValues2.put("StudentID", studentId2);
        marksValues2.put("Subject", "Science");
        marksValues2.put("Assignment1", 88);
        marksValues2.put("Assignment2", 92);
        marksValues2.put("Assignment3", 89);
        marksValues2.put("Assignment4", 91);
        marksValues2.put("Midterm", 94);
        marksValues2.put("TotalMarks", 454);
        db.insert("Marks", null, marksValues2);

        ContentValues announcementValues1 = new ContentValues();
        announcementValues1.put("Title", "Important Announcement 1");
        announcementValues1.put("Message", "This is an important announcement for all students.");
        announcementValues1.put("Date", "2023-01-01");
        announcementValues1.put("FacultyID", 1);
        db.insert("Announcements", null, announcementValues1);

        ContentValues announcementValues2 = new ContentValues();
        announcementValues2.put("Title", "Important Announcement 2");
        announcementValues2.put("Message", "Another important announcement for students.");
        announcementValues2.put("Date", "2023-01-02");
        announcementValues2.put("FacultyID", 2);
        db.insert("Announcements", null, announcementValues2);

        ContentValues feesValues1 = new ContentValues();
        feesValues1.put("DatePaid", "2023-01-01");
        feesValues1.put("Amount", 500.0);
        feesValues1.put("Purpose", "Course");
        feesValues1.put("StudentID", studentId1);
        db.insert("Fees", null, feesValues1);

        ContentValues feesValues2 = new ContentValues();
        feesValues2.put("DatePaid", "2023-01-02");
        feesValues2.put("Amount", 600.0);
        feesValues2.put("Purpose", "Exam");
        feesValues2.put("StudentID", studentId2);
        db.insert("Fees", null, feesValues2);
    }

    public Boolean checkEmailPassword(String student_reg_number, String student_password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from StudentProfile where RegistrationNumber = ? and Password = ?", new String[]{student_reg_number, student_password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkEmailPassword_for_fac(String fac_user_name, String fac_password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from FacultyProfile where Username = ? and Password = ?", new String[]{fac_user_name, fac_password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }


    public Cursor getClassSectionsForFaculty(int facultyId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "SELECT Class.ClassID, Class.ClassName, Class.Section " +
                "FROM ClassAssignment " +
                "INNER JOIN Class ON ClassAssignment.ClassID = Class.ClassID " +
                "WHERE FacultyID = ?";
        String[] selectionArgs = {String.valueOf(facultyId)};
        return db.rawQuery(sqlQuery, selectionArgs);
    }

    public int getFacultyId(String fac_user_name, String fac_password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT FacultyID FROM FacultyProfile WHERE Username = ? AND Password = ?", new String[]{fac_user_name, fac_password});

        if (cursor.moveToFirst()) {
            int facultyIdIndex = cursor.getColumnIndex("FacultyID");
            if (facultyIdIndex != -1) {
                int facultyId = cursor.getInt(facultyIdIndex);
                cursor.close();
                return facultyId;
            }
        } else {
            cursor.close();
            return -1;
        }
        return -1;
    }

    public int getStudentId(String student_reg_number, String student_password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT StudentID FROM StudentProfile WHERE RegistrationNumber = ? AND Password = ?", new String[]{student_reg_number, student_password});

        if (cursor.moveToFirst()) {
            int studentIdIndex = cursor.getColumnIndex("StudentID");
            if (studentIdIndex != -1) {
                int studentId = cursor.getInt(studentIdIndex);
                cursor.close();
                return studentId;
            }
        } else {
            cursor.close();
            return -1;
        }
        return -1;
    }

    public Cursor getSubjectsForFaculty(int facultyId, int classId, String section) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "SELECT S.SubjectID, S.SubjectName AS SubjectName " +
                "FROM ClassAssignment CA " +
                "INNER JOIN Subjects S ON CA.SubjectID = S.SubjectID " +
                "INNER JOIN Class C ON CA.ClassID = C.ClassID " +
                "WHERE CA.FacultyID = ? AND C.ClassID = ? AND C.Section = ?";

        String[] selectionArgs = {String.valueOf(facultyId), String.valueOf(classId), section};
        return db.rawQuery(sqlQuery, selectionArgs);
    }

    public Cursor getStudentsForFacultyMarks(int facultyId, int selectedClass, String selectedSection, int selectedSubject) {
        SQLiteDatabase db = this.getReadableDatabase();

        // First, retrieve the ClassID for the selectedClass and selectedSection combination
        Cursor classCursor = db.rawQuery(
                "SELECT ClassID FROM Class WHERE ClassID = ? AND Section = ?",
                new String[]{String.valueOf(selectedClass), selectedSection}
        );
        int classId = -1;
        if (classCursor != null && classCursor.moveToFirst()) {
            classId = classCursor.getColumnIndex("ClassID");
            if (classId != -1) {
                classId = classCursor.getInt(classId);
                classCursor.close();
            }
        } else {
            Log.e("DatabaseError", "Class and section combination not found.");
            return null;
        }

        String sqlQuery = "SELECT SP.Name AS StudentName, SP.RegistrationNumber AS RegistrationNumber " +
                "FROM StudentProfile SP " +
                "INNER JOIN Class C ON SP.ClassID = C.ClassID " +
                "INNER JOIN ClassAssignment CA ON C.ClassID = CA.ClassID " +
                "WHERE CA.FacultyID = ? AND CA.ClassID = ? AND CA.SubjectID = ?";
        String[] selectionArgs = {String.valueOf(facultyId), String.valueOf(classId), String.valueOf(selectedSubject)};
        return db.rawQuery(sqlQuery, selectionArgs);
    }


    //Creating Faculty Announcement
    public Boolean creatingFacultyAnnouncement(int loggedInFacultyId, String title, String message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues announcementValues = new ContentValues();
        announcementValues.put("Title", title);
        announcementValues.put("Message", message);
        SimpleDateFormat pcDateFormat = new SimpleDateFormat(DateFormat.getBestDateTimePattern(Locale.getDefault(), "yyyy-MM-dd"), Locale.getDefault());
        Date date = new Date();
        String formattedDate = pcDateFormat.format(date);

        announcementValues.put("Date", formattedDate.format(formattedDate));
        announcementValues.put("UserID", loggedInFacultyId);

        long result = db.insert("Announcements", null, announcementValues);
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }


    //Faculty Announcement
    public Cursor getAnnouncementForFaculty() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "SELECT Title, Message, Date FROM Announcements ORDER BY AnnouncementID DESC";
        return db.rawQuery(sqlQuery, null);
    }

    //Student Announcement
    public Cursor getAnnouncementForStudent() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "SELECT Title, Message, Date FROM Announcements ORDER BY AnnouncementID DESC";
        return db.rawQuery(sqlQuery, null);
    }

    public Cursor getAnnouncementForFacultyDashboard() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "SELECT Title, Message, Date FROM Announcements ORDER BY AnnouncementID DESC";
        return db.rawQuery(sqlQuery, null);
    }


    public Cursor getAnnouncementForStudentDashboard() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "SELECT Title, Message, Date FROM Announcements ORDER BY AnnouncementID DESC";
        return db.rawQuery(sqlQuery, null);
    }


}


