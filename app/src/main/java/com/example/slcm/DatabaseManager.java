package com.example.slcm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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
                "UserID INTEGER," +
                "FOREIGN KEY (UserID) REFERENCES FacultyProfile(UserID));"); // Corrected table name

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
        // Inserting Faculty
        ContentValues facultyValues1 = new ContentValues();
        facultyValues1.put("Username", "faculty1");
        facultyValues1.put("Password", "faculty_password1");
        facultyValues1.put("UserType", "Faculty");
        facultyValues1.put("Name", "Faculty One");
        facultyValues1.put("DOB", "1990-01-01");
        facultyValues1.put("Age", 30);
        facultyValues1.put("Department", "Computer Science");
        facultyValues1.put("ClassID", 1);
        long facultyId1 = db.insert("FacultyProfile", null, facultyValues1);

        ContentValues facultyValues2 = new ContentValues();
        facultyValues2.put("Username", "faculty2");
        facultyValues2.put("Password", "faculty_password2");
        facultyValues2.put("UserType", "Faculty");
        facultyValues2.put("Name", "Faculty Two");
        facultyValues2.put("DOB", "1985-05-15");
        facultyValues2.put("Age", 36);
        facultyValues2.put("Department", "Mathematics");
        facultyValues2.put("ClassID", 2);
        long facultyId2 = db.insert("FacultyProfile", null, facultyValues2);

        // Inserting Students
        ContentValues studentValues1 = new ContentValues();
        studentValues1.put("RegistrationNumber", 220970054);
        studentValues1.put("Password", "student_password1");
        studentValues1.put("UserType", "Student");
        studentValues1.put("Name", "Student One");
        studentValues1.put("DOB", "2000-01-01");
        studentValues1.put("Age", 20);
        studentValues1.put("Semester", 2);
        studentValues1.put("Department", "Computer Science");
        studentValues1.put("ClassID", 1);
        long studentId1 = db.insert("StudentProfile", null, studentValues1);

        ContentValues studentValues2 = new ContentValues();
        studentValues2.put("RegistrationNumber", 220970055);
        studentValues2.put("Password", "student_password2");
        studentValues2.put("UserType", "Student");
        studentValues2.put("Name", "Student Two");
        studentValues2.put("DOB", "1999-03-10");
        studentValues2.put("Age", 22);
        studentValues2.put("Semester", 3);
        studentValues2.put("Department", "Mathematics");
        studentValues2.put("ClassID", 2);
        long studentId2 = db.insert("StudentProfile", null, studentValues2);

        // Inserting Class Assignments
        ContentValues classAssignmentValues1 = new ContentValues();
        classAssignmentValues1.put("FacultyID", facultyId1);
        classAssignmentValues1.put("ClassID", 1);
        classAssignmentValues1.put("SubjectID", 1); // Assuming subject with ID 1
        db.insert("ClassAssignment", null, classAssignmentValues1);

        ContentValues classAssignmentValues2 = new ContentValues();
        classAssignmentValues2.put("FacultyID", facultyId2);
        classAssignmentValues2.put("ClassID", 2);
        classAssignmentValues2.put("SubjectID", 2); // Assuming subject with ID 2
        db.insert("ClassAssignment", null, classAssignmentValues2);

        // Inserting Classes
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

        // Inserting Attendance
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

        // Inserting Marks
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

        // Inserting Announcements
        ContentValues announcementValues1 = new ContentValues();
        announcementValues1.put("Title", "Important Announcement 1");
        announcementValues1.put("Message", "This is an important announcement for all students.");
        announcementValues1.put("Date", "2023-01-01");
        announcementValues1.put("UserID", 1);
        db.insert("Announcements", null, announcementValues1);

        ContentValues announcementValues2 = new ContentValues();
        announcementValues2.put("Title", "Important Announcement 2");
        announcementValues2.put("Message", "Another important announcement for students.");
        announcementValues2.put("Date", "2023-01-02");
        announcementValues2.put("UserID", 2);
        db.insert("Announcements", null, announcementValues2);

        // Inserting Fees
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

    public Boolean checkEmailPassword(String student_reg_number, String student_password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from StudentProfile where RegistrationNumber = ? and Password = ?", new String[]{student_reg_number, student_password});
        if (cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }

    public Boolean checkEmailPassword_for_fac(String fac_user_name, String fac_password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from FacultyProfile where Username = ? and Password = ?", new String[]{fac_user_name, fac_password});
        if (cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }



    public List<String> getClassesAssignedToFaculty(String facultyUsername) {
        List<String> assignedClasses = new ArrayList<>();

        String query = "SELECT Class.ClassName, Class.Section, Class.Semester " +
                "FROM FacultyProfile " +
                "INNER JOIN ClassAssignment ON FacultyProfile.UserID = ClassAssignment.FacultyID " +
                "INNER JOIN Class ON ClassAssignment.ClassID = Class.ClassID " +
                "WHERE FacultyProfile.Username = ?";

        Cursor cursor = db.rawQuery(query, new String[]{facultyUsername});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int classNameIndex = cursor.getColumnIndex("ClassName");
                    int sectionIndex = cursor.getColumnIndex("Section");
                    int semesterIndex = cursor.getColumnIndex("Semester");

                    if (classNameIndex >= 0 && sectionIndex >= 0 && semesterIndex >= 0) {
                        String className = cursor.getString(classNameIndex);
                        String section = cursor.getString(sectionIndex);
                        int semester = cursor.getInt(semesterIndex);

                        String classInfo = "Class: " + className + ", Section: " + section + ", Semester: " + semester;
                        assignedClasses.add(classInfo);
                    }
                } while (cursor.moveToNext());
            }

            cursor.close();
        }

        return assignedClasses;
    }


    public List<String> getSubjectsForClassAndFaculty(String className, String facultyName) {
        List<String> subjectsList = new ArrayList<>();

        // Formulate the SQL query
        String query = "SELECT DISTINCT s.SubjectName FROM Subjects s " +
                "INNER JOIN Class c ON s.ClassID = c.ClassID " +
                "INNER JOIN FacultyProfile f ON c.ClassID = f.ClassID " +
                "WHERE c.ClassName = ? AND f.Name = ?;";

        // Define the selection arguments
        String[] selectionArgs = {className, facultyName};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int subjectNameColumnIndex = cursor.getColumnIndex("SubjectName");
                if (subjectNameColumnIndex != -1) {
                    do {
                        String subjectName = cursor.getString(subjectNameColumnIndex);
                        subjectsList.add(subjectName);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        }

        return subjectsList;
    }




}
