package com.example.slcm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.slcm.Student.SubjectWithMarks;

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
                "FOREIGN KEY (FacultyID) REFERENCES FacultyProfile(FacultyID)," +
                "FOREIGN KEY (ClassID) REFERENCES Class(ClassID)," +
                "FOREIGN KEY (SubjectID) REFERENCES Subjects(SubjectID));");

        db.execSQL("CREATE TABLE IF NOT EXISTS Class (" +
                "ClassID INTEGER PRIMARY KEY," +
                "ClassName TEXT," +
                "Section TEXT," +
                "Semester INTEGER);");

        db.execSQL("CREATE TABLE IF NOT EXISTS Subjects (" +
                "SubjectID INTEGER PRIMARY KEY," +
                "SubjectName TEXT);");

        db.execSQL("CREATE TABLE IF NOT EXISTS Attendance (" +
                "AttendanceID INTEGER PRIMARY KEY," +
                "Date DATE," +
                "ClassID INTEGER," +
                "SubjectID INTEGER," +
                "StudentID TEXT," +
                "Status TEXT," +
                "FOREIGN KEY (StudentID) REFERENCES StudentProfile(RegistrationNumber)," + // Corrected table name
                "FOREIGN KEY (SubjectID) REFERENCES Subjects(SubjectID)," + // Corrected table name
                "FOREIGN KEY (ClassID) REFERENCES Class(ClassID));");

        db.execSQL("CREATE TABLE IF NOT EXISTS Marks (" +
                "MarksID INTEGER PRIMARY KEY," +
                "Date DATE," +
                "ClassID INTEGER," +
                "StudentID INTEGER," +
                "SubjectID INTEGER," +
                "Assignment1 FLOAT," +
                "Assignment2 FLOAT," +
                "Assignment3 FLOAT," +
                "Assignment4 FLOAT," +
                "Midterm FLOAT," +
                "TotalMarks FLOAT," +
                "FOREIGN KEY (StudentID) REFERENCES StudentProfile(StudentID)," +
                "FOREIGN KEY (ClassID) REFERENCES Class(ClassID)," +
                "FOREIGN KEY (SubjectID) REFERENCES Subjects(SubjectID));");


        db.execSQL("CREATE TABLE IF NOT EXISTS Announcements (" +
                "AnnouncementID INTEGER PRIMARY KEY," +
                "Title TEXT," +
                "Message TEXT," +
                "Date DATE," +
                "FacultyID INTEGER," +
                "FOREIGN KEY (FacultyID) REFERENCES FacultyProfile(FacultyID));");

        db.execSQL("CREATE TABLE IF NOT EXISTS Fees (" +
                "FeesID INTEGER PRIMARY KEY," +
                "DatePaid DATE," +
                "Amount REAL," +
                "Purpose TEXT," +
                "StudentID INTEGER," +
                "FOREIGN KEY (StudentID) REFERENCES StudentProfile(StudentID));");


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
        studentValues2.put("ClassID", 1);
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


        ContentValues marksValues1 = new ContentValues();
        marksValues1.put("Date", "2023-10-14");
        marksValues1.put("ClassID", 1);
        marksValues1.put("StudentID", studentId1);
        marksValues1.put("SubjectID", 1);
        marksValues1.put("Assignment1", 3);
        marksValues1.put("Assignment2", 4);
        marksValues1.put("TotalMarks", 7);
        db.insert("Marks", null, marksValues1);

        ContentValues marksValues2 = new ContentValues();
        marksValues2.put("Date", "2023-10-13");
        marksValues2.put("ClassID", 2);
        marksValues2.put("StudentID", studentId2);
        marksValues2.put("SubjectID", 2);
        marksValues2.put("Assignment1", 5);
        marksValues2.put("Assignment2", 5);
        marksValues2.put("Assignment3", 4.5);
        marksValues2.put("TotalMarks", 14.5);
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

    public void AddAttendance(Attendance attendance){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("ClassGet", "date"+attendance.AttendanceDate);
        ContentValues attendanceValues = new ContentValues();
        attendanceValues.put("Date", attendance.AttendanceDate);
        attendanceValues.put("ClassID", attendance.ClassID);
        attendanceValues.put("StudentID", attendance.StudentID);
        attendanceValues.put("SubjectID", attendance.SubjectID);
        attendanceValues.put("Status", attendance.Status);
        db.insert("Attendance", null, attendanceValues);
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

        String sqlQuery = "SELECT SP.Name AS StudentName, SP.RegistrationNumber AS RegistrationNumber, SP.StudentID AS StudentID " +
                "FROM StudentProfile SP " +
                "INNER JOIN Class C ON SP.ClassID = C.ClassID " +
                "INNER JOIN ClassAssignment CA ON C.ClassID = CA.ClassID " +
                "WHERE CA.FacultyID = ? AND CA.ClassID = ? AND CA.SubjectID = ?";
        String[] selectionArgs = {String.valueOf(facultyId), String.valueOf(classId), String.valueOf(selectedSubject)};
        return db.rawQuery(sqlQuery, selectionArgs);
    }

    public float retrieveAssignmentMarks(int selectedSubject, int selectedClass, String selectedAssignment) {
        Log.d("ClassGet", "Retrieving Assignment1 marks for class: " + selectedClass + " subject: " + selectedSubject + " assignment: " + selectedAssignment);
        String table = "Marks";
        String assignmentColumn = selectedAssignment;
        String subjectIdColumn = "SubjectID";
        String classIdColumn = "ClassID";

        String query = "SELECT " + table + "." + assignmentColumn +
                " FROM " + table +
                " WHERE " + table + "." + subjectIdColumn + " = ?" +
                " AND " + table + "." + classIdColumn + " = ?";

        // Provide the actual values for the placeholders
        String[] selectionArgs = new String[]{String.valueOf(selectedSubject), String.valueOf(selectedClass)};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        float marks = 0.0f;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                marks = cursor.getFloat(cursor.getColumnIndexOrThrow(assignmentColumn));
                Log.d("Assignment Marks", "Assignment Marks: " + marks);
            } else {
                Log.d("DebugTag", "Cursor is empty.");
                return 0;
            }
        } else {
            Log.d("DebugTag", "Cursor is null.");
            return 0;
        }
        cursor.close();
        return marks;
    }

    public boolean updateMarks(int subjectId, String date, int studentID, int classId, String assignmentType, float marks) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("SubjectID", subjectId);
        values.put("Date", date);
        values.put("ClassID", classId);
        values.put("StudentID", studentID);

        switch (assignmentType) {
            case "Assignment1":
                values.put("Assignment1", marks);
                break;
            case "Assignment2":
                values.put("Assignment2", marks);
                break;
            case "Assignment3":
                values.put("Assignment3", marks);
                break;
            case "Assignment4":
                values.put("Assignment4", marks);
                break;
            case "Midterm":
                values.put("Midterm", marks);
                break;
            default:
                Log.e("UpdateMarks", "Invalid assignment type: " + assignmentType);
                db.close();
                return false;
        }

        String whereClause = "StudentID = ? AND SubjectID = ? AND ClassID = ?";
        String[] whereArgs = {String.valueOf(studentID), String.valueOf(subjectId), String.valueOf(classId)};
        Cursor cursor = db.query("Marks", null, whereClause, whereArgs, null, null, null);
        Log.d("UpdateMarks", "StudentID: " + studentID+"SUbject"+subjectId+"Class"+classId);
        if (cursor.moveToFirst()) {
            int rowsUpdated = db.update("Marks", values, whereClause, whereArgs);
            db.close();
            if (rowsUpdated > 0) {
                Log.d("UpdateMarks", "Marks updated successfully for StudentID: " + studentID);
            } else {
                Log.d("UpdateMarks", "Failed to update marks for StudentID: " + studentID);
            }
            return rowsUpdated > 0;
        } else {

            long result = db.insert("Marks", null, values);
            db.close();
            if (result != -1) {
                Log.d("UpdateMarks", "Marks inserted successfully for StudentID: " + studentID);
            } else {
                Log.d("UpdateMarks", "Failed to insert marks for StudentID: " + studentID);
            }
            return result != -1;
        }
    }
    public Boolean updateFacPassword(String fac_username, String fac_new_pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Password", fac_new_pass);
        String whereClause = "Username = ?";
        String[] whereArgs = {fac_username};

        try {
            int rowsUpdated = db.update("FacultyProfile", contentValues, whereClause, whereArgs);
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
    }
    public Boolean updateStudentPassword(String StudentRegno,String stud_password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Password", stud_password);

        String whereClause = "RegistrationNumber= ?";
        String[] whereArgs = {StudentRegno};
        try {
        int rowsUpdated = db.update("StudentProfile", contentValues, whereClause, whereArgs);
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
    }
    public List<SubjectWithMarks> getSubjectsAndMarksForStudent(int studentId, int semester) {
        List<SubjectWithMarks> subjectsList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String sqlQuery = "SELECT S.SubjectID, S.SubjectName, " +
                "M.Assignment1, M.Assignment2, M.Assignment3, M.Assignment4, M.Midterm " +
                "FROM Subjects S " +
                "INNER JOIN ClassAssignment CA ON S.SubjectID = CA.SubjectID " +
                "INNER JOIN StudentProfile SP ON CA.ClassID = SP.ClassID " +
                "INNER JOIN Class C ON CA.ClassID = C.ClassID " +  // Join with the Class table
                "LEFT JOIN Marks M ON S.SubjectID = M.SubjectID AND SP.StudentID = M.StudentID " +
                "WHERE SP.StudentID = ? AND C.Semester = ?"; // Retrieve Semester from the Class table

        String[] selectionArgs = { String.valueOf(studentId), String.valueOf(semester)};
        Log.d("UpdateMarks", "Semester"+semester);

        Cursor cursor = db.rawQuery(sqlQuery, selectionArgs);

        if (cursor != null && cursor.moveToFirst()) {
            int subjectIdIndex = cursor.getColumnIndex("SubjectID");
            int subjectNameIdIndex = cursor.getColumnIndex("SubjectName");
            int assignment1Index = cursor.getColumnIndex("Assignment1");
            int assignment2Index = cursor.getColumnIndex("Assignment2");
            int assignment3Index = cursor.getColumnIndex("Assignment3");
            int assignment4Index = cursor.getColumnIndex("Assignment4");
            int midtermIndex = cursor.getColumnIndex("Midterm");

            do {
                int subjectId = cursor.getInt(subjectIdIndex);
                String subjectName = cursor.getString(subjectNameIdIndex);
                Double assignment1 = cursor.isNull(assignment1Index) ? null : cursor.getDouble(assignment1Index);
                Double assignment2 = cursor.isNull(assignment2Index) ? null : cursor.getDouble(assignment2Index);
                Double assignment3 = cursor.isNull(assignment3Index) ? null : cursor.getDouble(assignment3Index);
                Double assignment4 = cursor.isNull(assignment4Index) ? null : cursor.getDouble(assignment4Index);
                Double midterm = cursor.isNull(midtermIndex) ? null : cursor.getDouble(midtermIndex);

                subjectsList.add(new SubjectWithMarks(subjectId, subjectName, assignment1, assignment2, assignment3, assignment4, midterm));
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return subjectsList;
    }


}

