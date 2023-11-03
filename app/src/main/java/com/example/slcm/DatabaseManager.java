package com.example.slcm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;
import android.util.Log;

import com.example.slcm.Faculty.Attendance;
import com.example.slcm.Student.SubjectWithAttendance;
import com.example.slcm.Student.SubjectWithMarks;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseManager extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "slcm_database";
    private static final int DATABASE_VERSION = 1;
    SQLiteDatabase db = this.getWritableDatabase();

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

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
                "PhoneNumber TEXT," +
                "Gender TEXT," +
                "Email TEXT," +
                "Department TEXT,"+
                "Designation TEXT," +
                "AcademicRole TEXT," +
                "AreasOfInterest TEXT);");


        db.execSQL("CREATE TABLE IF NOT EXISTS StudentProfile (" +
                "StudentID INTEGER PRIMARY KEY," +
                "RegistrationNumber INTEGER," +
                "Password TEXT," +
                "UserType TEXT," +
                "Name TEXT," +
                "DOB DATE," +
                "Age INTEGER," +
                "PhoneNumber TEXT," +
                "Gender TEXT," +
                "Email TEXT," +
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
                "Attended INTEGER," +
                "Missed INTEGER," +
                "FOREIGN KEY (StudentID) REFERENCES StudentProfile(RegistrationNumber)," +
                "FOREIGN KEY (SubjectID) REFERENCES Subjects(SubjectID)," +
                "FOREIGN KEY (ClassID) REFERENCES Class(ClassID));");

        db.execSQL("CREATE TABLE IF NOT EXISTS Marks (" +
                "MarksID INTEGER PRIMARY KEY," +
                "Date DATE," +
                "ClassID INTEGER," +
                "StudentID INTEGER," +
                "SubjectID INTEGER," +
                "Assignment1 FLOAT DEFAULT 0.0," +
                "Assignment2 FLOAT DEFAULT 0.0," +
                "Assignment3 FLOAT DEFAULT 0.0," +
                "Assignment4 FLOAT DEFAULT 0.0," +
                "Midterm FLOAT DEFAULT 0.0," +
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

        db.execSQL("CREATE TABLE IF NOT EXISTS Messages (" +
                "_id INTEGER PRIMARY KEY," +
                "SenderID INTEGER," +
                "SenderType TEXT," +
                "ReceiverID INTEGER," +
                "MessageText TEXT," +
                "isRead INTEGER DEFAULT 0," +
                "Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP);");

        db.execSQL("CREATE TABLE IF NOT EXISTS SemesterGPA (" +
                "SemesterGPAID INTEGER PRIMARY KEY," +
                "GPA FLOAT," +
                "CGPA FLOAT," +
                "Semester INTEGER," +
                "StudentID INTEGER," +
                "ClassID INTEGER," +
                "FOREIGN KEY (StudentID) REFERENCES StudentProfile(StudentID)," +
                "FOREIGN KEY (ClassID) REFERENCES Class(ClassID)" +
                ");");

        db.execSQL("CREATE TABLE IF NOT EXISTS Marksheet (" +
                "MarksheetID INTEGER PRIMARY KEY," +
                "SubjectID INTEGER," +
                "SubjectName TEXT," +
                "Grade TEXT," +
                "Credits FLOAT," +
                "StudentID INTEGER," +
                "ClassID INTEGER," +
                "SemesterGPAID INTEGER," +
                "FOREIGN KEY (StudentID) REFERENCES StudentProfile(StudentID)," +
                "FOREIGN KEY (ClassID) REFERENCES Class(ClassID)," +
                "FOREIGN KEY (SubjectID) REFERENCES Subjects(SubjectID)," +
                "FOREIGN KEY (SemesterGPAID) REFERENCES SemesterGPA(SemesterGPAID)" +
                ");");


        insertDummyData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DatabaseStatus", "Upgrading database from version " + oldVersion + " to " + newVersion);
    }

    private void insertDummyData(SQLiteDatabase db) {
        ContentValues facultyValues1 = new ContentValues();
        facultyValues1.put("Username", "MIT1");
        facultyValues1.put("Password", "12345");
        facultyValues1.put("UserType", "Faculty");
        facultyValues1.put("Name", "Mr. S S Shameem");
        facultyValues1.put("Designation", "Assistant Professor");
        facultyValues1.put("DOB", "1985-01-01");
        facultyValues1.put("Age", 40);
        facultyValues1.put("PhoneNumber", "9188881829");
        facultyValues1.put("Gender", "Male");
        facultyValues1.put("Email", "shameem@learner.manipal.edu");
        facultyValues1.put("Department", "Department of Data Science and Computer Applications");
        facultyValues1.put("AcademicRole", "Coordinator for department-level library activities");
        facultyValues1.put("AreasOfInterest", "Video Compression and Parallel Programming, Programming Languages (C, C++, Java), ERP, Cloud Computing, Software Engineering");
        long facultyId1 = db.insert("FacultyProfile", null, facultyValues1);

        ContentValues facultyValues2 = new ContentValues();
        facultyValues2.put("Username", "MIT2");
        facultyValues2.put("Password", "12345");
        facultyValues2.put("UserType", "Faculty");
        facultyValues2.put("Name", "Mr. Nirmal Kumar Nigam");
        facultyValues2.put("Designation", "Assistant Professor-Senior Scale");
        facultyValues2.put("DOB", "1985-05-15");
        facultyValues2.put("Age", 49);
        facultyValues2.put("PhoneNumber", "9187771829");
        facultyValues2.put("Gender", "Male");
        facultyValues2.put("Email", "nigam@learner.manipal.edu");
        facultyValues2.put("Department", "Department of Data Science and Computer Applications");
        facultyValues2.put("AcademicRole", "ISO Co-coordinator");
        facultyValues2.put("AreasOfInterest", "Computer Networks, Information and Network Security, Data Mining");
        long facultyId2 = db.insert("FacultyProfile", null, facultyValues2);

        ContentValues facultyValues3 = new ContentValues();
        facultyValues3.put("Username", "MIT3");
        facultyValues3.put("Password", "12345");
        facultyValues3.put("UserType", "Faculty");
        facultyValues3.put("Name", "Dr. Dasharathraj K Shetty");
        facultyValues3.put("Designation", "Associate Professor");
        facultyValues3.put("DOB", "1985-05-15");
        facultyValues3.put("Age", 45);
        facultyValues3.put("PhoneNumber", "9122281829");
        facultyValues3.put("Gender", "Male");
        facultyValues3.put("Email", "dasharathraj@learner.manipal.edu");
        facultyValues3.put("Department", "Department of Data Science and Computer Applications");
        facultyValues3.put("AcademicRole", "Digital Coordinator, MIT");
        facultyValues3.put("AreasOfInterest", "AI/ML, Image Processing, Computer Vision, E-Business/E-Commerce, Enterprise Application, Software Life Cycle Management, Software Project Management and Strategic Management");
        long facultyId3 = db.insert("FacultyProfile", null, facultyValues3);

        ContentValues facultyValues4 = new ContentValues();
        facultyValues4.put("Username", "MIT4");
        facultyValues4.put("Password", "12345");
        facultyValues4.put("UserType", "Faculty");
        facultyValues4.put("Name", "Dr. Savitha G");
        facultyValues4.put("Designation", "Assistant Professor");
        facultyValues4.put("DOB", "1985-05-15");
        facultyValues4.put("Age", 40);
        facultyValues4.put("PhoneNumber", "921381829");
        facultyValues4.put("Gender", "Female");
        facultyValues4.put("Email", "savitha@learner.manipal.edu");
        facultyValues4.put("Department", "Department of Data Science and Computer Applications");
        facultyValues4.put("AcademicRole", "Teaching Data Structures & Algorithms to BTech DSE");
        facultyValues4.put("AreasOfInterest", "Image Processing, Machine Learning, Deep learning, Cloud computing");
        long facultyId4 = db.insert("FacultyProfile", null, facultyValues4);

        ContentValues facultyValues5 = new ContentValues();
        facultyValues5.put("Username", "MIT5");
        facultyValues5.put("Password", "12345");
        facultyValues5.put("UserType", "Faculty");
        facultyValues5.put("Name", "Mr. Vinayak M");
        facultyValues5.put("Designation", "Assistant Professor-Senior Scale");
        facultyValues5.put("DOB", "1985-05-15");
        facultyValues5.put("Age", 40);
        facultyValues5.put("PhoneNumber", "9188441829");
        facultyValues5.put("Gender", "Male");
        facultyValues5.put("Email", "vinayak@learner.manipal.edu");
        facultyValues5.put("Department", "Department of Data Science and Computer Applications");
        facultyValues5.put("AcademicRole", "Assistant Professor - Senior Scale in the Department of Information and Communication Technology");
        facultyValues5.put("AreasOfInterest", "Database Management Systems, Image Processing and Network Security");
        long facultyId5 = db.insert("FacultyProfile", null, facultyValues5);

        ContentValues studentValues1 = new ContentValues();
        studentValues1.put("RegistrationNumber", 220970001);
        studentValues1.put("Password", "12345");
        studentValues1.put("UserType", "Student");
        studentValues1.put("Name", "Nibeel");
        studentValues1.put("DOB", "2001-01-01");
        studentValues1.put("Age", 23);
        studentValues1.put("PhoneNumber", "9199911829");
        facultyValues1.put("Gender", "Male");
        studentValues1.put("Email", "nibeel@learner.manipal.edu");
        studentValues1.put("Semester", 3);
        studentValues1.put("Department", "Department of Data Science and Computer Applications");
        studentValues1.put("ClassID", 1);
        long studentId1 = db.insert("StudentProfile", null, studentValues1);

        ContentValues studentValues2 = new ContentValues();
        studentValues2.put("RegistrationNumber", 220970002);
        studentValues2.put("Password", "12345");
        studentValues2.put("UserType", "Student");
        studentValues2.put("Name", "Noel");
        studentValues2.put("DOB", "2001-01-01");
        studentValues2.put("Age", 23);
        studentValues2.put("PhoneNumber", "9181118829");
        facultyValues2.put("Gender", "Male");
        studentValues2.put("Email", "noel@learner.manipal.edu");
        studentValues2.put("Semester", 3);
        studentValues2.put("Department", "Department of Data Science and Computer Applications");
        studentValues2.put("ClassID", 1);
        long studentId2 = db.insert("StudentProfile", null, studentValues2);

        ContentValues studentValues4 = new ContentValues();
        studentValues4.put("RegistrationNumber", 220970003);
        studentValues4.put("Password", "12345");
        studentValues4.put("UserType", "Student");
        studentValues4.put("Name", "Sanjana");
        studentValues4.put("DOB", "2001-01-01");
        studentValues4.put("Age", 23);
        studentValues4.put("PhoneNumber", "9181113829");
        facultyValues4.put("Gender", "Female");
        studentValues4.put("Email", "sanjana@learner.manipal.edu");
        studentValues4.put("Semester", 3);
        studentValues4.put("Department", "Department of DataScience and Computer Applications");
        studentValues4.put("ClassID", 1);
        long studentId4 = db.insert("StudentProfile", null, studentValues4);

        ContentValues studentValues9 = new ContentValues();
        studentValues9.put("RegistrationNumber", 220970004);
        studentValues9.put("Password", "12345");
        studentValues9.put("UserType", "Student");
        studentValues9.put("Name", "Misha");
        studentValues9.put("DOB", "2001-01-01");
        studentValues9.put("Age", 23);
        studentValues9.put("PhoneNumber", "9199102120");
        studentValues9.put("Gender", "Female");
        studentValues9.put("Email", "misha@learner.manipal.edu");
        studentValues9.put("Semester", 3);
        studentValues9.put("Department", "Department of Data Science and Computer Applications");
        studentValues9.put("ClassID", 1);
        long studentId9 = db.insert("StudentProfile", null, studentValues9);

        ContentValues studentValues10 = new ContentValues();
        studentValues10.put("RegistrationNumber", 220970005);
        studentValues10.put("Password", "12345");
        studentValues10.put("UserType", "Student");
        studentValues10.put("Name", "Nitish");
        studentValues10.put("DOB", "2001-01-01");
        studentValues10.put("Age", 23);
        studentValues10.put("PhoneNumber", "9122918829");
        studentValues10.put("Gender", "Male");
        studentValues10.put("Email", "nitish@learner.manipal.edu");
        studentValues10.put("Semester", 3);
        studentValues10.put("Department", "Department of Data Science and Computer Applications");
        studentValues10.put("ClassID", 1);
        long studentId10 = db.insert("StudentProfile", null, studentValues10);

        ContentValues studentValues5 = new ContentValues();
        studentValues5.put("RegistrationNumber", 220970006);
        studentValues5.put("Password", "12345");
        studentValues5.put("UserType", "Student");
        studentValues5.put("Name", "Divya");
        studentValues5.put("DOB", "2001-01-01");
        studentValues5.put("Age", 23);
        studentValues5.put("PhoneNumber", "9110003829");
        studentValues5.put("Gender", "Female");
        studentValues5.put("Email", "divya@learner.manipal.edu");
        studentValues5.put("Semester", 3);
        studentValues5.put("Department", "Department of DataScience and Computer Applications");
        studentValues5.put("ClassID", 1);
<<<<<<< HEAD
        long studentId5 = db.insert("StudentProfile", null, studentValues5);
=======
        long studentId5 = db.insert("StudentProfile", null, studentValues4);
>>>>>>> d398dcf (Completed integration with some additions)

        ContentValues studentValues11 = new ContentValues();
        studentValues11.put("RegistrationNumber", 220970007);
        studentValues11.put("Password", "12345");
        studentValues11.put("UserType", "Student");
        studentValues11.put("Name", "Akshatha");
        studentValues11.put("DOB", "2001-01-01");
        studentValues11.put("Age", 23);
        studentValues11.put("PhoneNumber", "9112123829");
        studentValues11.put("Gender", "Female");
        studentValues11.put("Email", "akshatha@learner.manipal.edu");
        studentValues11.put("Semester", 3);
        studentValues11.put("Department", "Department of Data Science and Computer Applications");
        studentValues11.put("ClassID", 2);
        long studentId11 = db.insert("StudentProfile", null, studentValues11);

        ContentValues studentValues12 = new ContentValues();
        studentValues12.put("RegistrationNumber", 220970008);
        studentValues12.put("Password", "12345");
        studentValues12.put("UserType", "Student");
        studentValues12.put("Name", "Shashikanta");
        studentValues12.put("DOB", "2001-01-01");
        studentValues12.put("Age", 23);
        studentValues12.put("PhoneNumber", "9119923829");
        studentValues12.put("Gender", "Male");
        studentValues12.put("Email", "shashikanta@learner.manipal.edu");
        studentValues12.put("Semester", 3);
        studentValues12.put("Department", "Department of Data Science and Computer Applications");
        studentValues12.put("ClassID", 2);
        long studentId12 = db.insert("StudentProfile", null, studentValues12);

        ContentValues studentValues13 = new ContentValues();
        studentValues13.put("RegistrationNumber", 220970009);
        studentValues13.put("Password", "12345");
        studentValues13.put("UserType", "Student");
        studentValues13.put("Name", "Tanushree");
        studentValues13.put("DOB", "2001-01-01");
        studentValues13.put("Age", 23);
        studentValues13.put("PhoneNumber", "9110001121");
        studentValues13.put("Gender", "Female");
        studentValues13.put("Email", "tanushree@learner.manipal.edu");
        studentValues13.put("Semester", 3);
        studentValues13.put("Department", "Department of Data Science and Computer Applications");
        studentValues13.put("ClassID", 2);
        long studentId13 = db.insert("StudentProfile", null, studentValues13);

        ContentValues studentValues14 = new ContentValues();
        studentValues14.put("RegistrationNumber", 220970010);
        studentValues14.put("Password", "12345");
        studentValues14.put("UserType", "Student");
        studentValues14.put("Name", "Shreya");
        studentValues14.put("DOB", "2001-01-01");
        studentValues14.put("Age", 23);
        studentValues14.put("PhoneNumber", "9110192812");
        studentValues14.put("Gender", "Female");
        studentValues14.put("Email", "shreya@learner.manipal.edu");
        studentValues14.put("Semester", 3);
        studentValues14.put("Department", "Department of Data Science and Computer Applications");
        studentValues14.put("ClassID", 2);
        long studentId14 = db.insert("StudentProfile", null, studentValues14);

        ContentValues studentValues15 = new ContentValues();
        studentValues15.put("RegistrationNumber", 220970011);
        studentValues15.put("Password", "12345");
        studentValues15.put("UserType", "Student");
        studentValues15.put("Name", "Varshith");
        studentValues15.put("DOB", "2001-01-01");
        studentValues15.put("Age", 23);
        studentValues15.put("PhoneNumber", "9110665746");
        studentValues15.put("Gender", "Male");
        studentValues15.put("Email", "varshith@learner.manipal.edu");
        studentValues15.put("Semester", 3);
        studentValues15.put("Department", "Department of Data Science and Computer Applications");
        studentValues15.put("ClassID", 2);
        long studentId15 = db.insert("StudentProfile", null, studentValues15);

        ContentValues studentValues16 = new ContentValues();
        studentValues16.put("RegistrationNumber", 220970012);
        studentValues16.put("Password", "12345");
        studentValues16.put("UserType", "Student");
        studentValues16.put("Name", "Pooja");
        studentValues16.put("DOB", "2001-01-01");
        studentValues16.put("Age", 23);
        studentValues16.put("PhoneNumber", "9111212389");
        studentValues16.put("Gender", "Female");
        studentValues16.put("Email", "pooja@learner.manipal.edu");
        studentValues16.put("Semester", 3);
        studentValues16.put("Department", "Department of Data Science and Computer Applications");
        studentValues16.put("ClassID", 2);
<<<<<<< HEAD
        long studentId16 = db.insert("StudentProfile", null, studentValues16);
=======
        long studentId16 = db.insert("StudentProfile", null, studentValues15);
>>>>>>> d398dcf (Completed integration with some additions)


        ContentValues subjectValues1 = new ContentValues();
        subjectValues1.put("SubjectName", "Machine Learning");
        db.insert("Subjects", null, subjectValues1);

        ContentValues subjectValues2 = new ContentValues();
        subjectValues2.put("SubjectName", "Cloud Computing");
        db.insert("Subjects", null, subjectValues2);

        ContentValues subjectValues3 = new ContentValues();
        subjectValues3.put("SubjectName", "Big Data");
        db.insert("Subjects", null, subjectValues3);

        ContentValues subjectValues4 = new ContentValues();
        subjectValues4.put("SubjectName", "Computer Networks");
        db.insert("Subjects", null, subjectValues4);

        ContentValues subjectValues5 = new ContentValues();
        subjectValues5.put("SubjectName", "Human Resource Management");
        db.insert("Subjects", null, subjectValues5);

        ContentValues classAssignmentValues3 = new ContentValues();
        classAssignmentValues3.put("FacultyID", facultyId1);
        classAssignmentValues3.put("ClassID", 2);
        classAssignmentValues3.put("SubjectID", 1);
        db.insert("ClassAssignment", null, classAssignmentValues3);

        ContentValues classAssignmentValues4 = new ContentValues();
        classAssignmentValues4.put("FacultyID", facultyId2);
        classAssignmentValues4.put("ClassID", 1);
        classAssignmentValues4.put("SubjectID", 1);
        db.insert("ClassAssignment", null, classAssignmentValues4);

        ContentValues classAssignmentValues7 = new ContentValues();
        classAssignmentValues7.put("FacultyID", facultyId2);
        classAssignmentValues7.put("ClassID", 2);
        classAssignmentValues7.put("SubjectID", 4);
        db.insert("ClassAssignment", null, classAssignmentValues7);

        ContentValues classAssignmentValues8 = new ContentValues();
        classAssignmentValues8.put("FacultyID", facultyId3);
        classAssignmentValues8.put("ClassID", 1);
        classAssignmentValues8.put("SubjectID", 5);
        db.insert("ClassAssignment", null, classAssignmentValues8);

        ContentValues classAssignmentValues9 = new ContentValues();
        classAssignmentValues9.put("FacultyID", facultyId3);
        classAssignmentValues9.put("ClassID", 2);
        classAssignmentValues9.put("SubjectID", 2);
        db.insert("ClassAssignment", null, classAssignmentValues9);

        ContentValues classAssignmentValues10 = new ContentValues();
        classAssignmentValues10.put("FacultyID", facultyId4);
        classAssignmentValues10.put("ClassID", 1);
        classAssignmentValues10.put("SubjectID", 2);
        db.insert("ClassAssignment", null, classAssignmentValues10);

        ContentValues classAssignmentValues11 = new ContentValues();
        classAssignmentValues11.put("FacultyID", facultyId5);
        classAssignmentValues11.put("ClassID", 1);
        classAssignmentValues11.put("SubjectID", 4);
        db.insert("ClassAssignment", null, classAssignmentValues11);

        ContentValues classAssignmentValues12 = new ContentValues();
        classAssignmentValues12.put("FacultyID", facultyId3);
        classAssignmentValues12.put("ClassID", 2);
        classAssignmentValues12.put("SubjectID", 5);
        db.insert("ClassAssignment", null, classAssignmentValues12);

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

        ContentValues semesterGPAValues = new ContentValues();
        semesterGPAValues.put("GPA", 9.2);
        semesterGPAValues.put("CGPA", 9.2);
        semesterGPAValues.put("Semester", 3);
        semesterGPAValues.put("StudentID", studentId9);
        semesterGPAValues.put("ClassID", 1);
        long semesterGPAId = db.insert("SemesterGPA", null, semesterGPAValues);

        ContentValues marksheetValues1 = new ContentValues();
        marksheetValues1.put("SubjectID", 1);
        marksheetValues1.put("SubjectName", "Machine Learning");
        marksheetValues1.put("Grade", "A+");
        marksheetValues1.put("Credits", 3.0);
        marksheetValues1.put("StudentID", studentId9);
        marksheetValues1.put("ClassID", 1);
        marksheetValues1.put("SemesterGPAID", semesterGPAId);
        db.insert("Marksheet", null, marksheetValues1);

        ContentValues marksheetValues2 = new ContentValues();
        marksheetValues2.put("SubjectID", 2);
        marksheetValues2.put("SubjectName", "Cloud Computing");
        marksheetValues2.put("Grade", "B");
        marksheetValues2.put("Credits", 3.0);
        marksheetValues2.put("StudentID", studentId9);
        marksheetValues2.put("ClassID", 1);
        marksheetValues2.put("SemesterGPAID", semesterGPAId);
        db.insert("Marksheet", null, marksheetValues2);

        ContentValues marksheetValues4 = new ContentValues();
        marksheetValues4.put("SubjectID", 4);
        marksheetValues4.put("SubjectName", "Computer Networks");
        marksheetValues4.put("Grade", "A+");
        marksheetValues4.put("Credits", 3.0);
        marksheetValues4.put("StudentID", studentId9);
        marksheetValues4.put("ClassID", 1);
        marksheetValues4.put("SemesterGPAID", semesterGPAId);
        db.insert("Marksheet", null, marksheetValues4);

        ContentValues marksheetValues5 = new ContentValues();
        marksheetValues5.put("SubjectID", 5);
        marksheetValues5.put("SubjectName", "Human Resource Management");
        marksheetValues5.put("Grade", "A");
        marksheetValues5.put("Credits", 3.0);
        marksheetValues5.put("StudentID", studentId9);
        marksheetValues5.put("ClassID", 1);
        marksheetValues5.put("SemesterGPAID", semesterGPAId);
        db.insert("Marksheet", null, marksheetValues5);


        ContentValues announcementValues1 = new ContentValues();
        announcementValues1.put("Title", "New Student Orientation");
        announcementValues1.put("Message", "Welcome to our incoming class! New student orientation will take place 31-08-2023. Get ready for a fun and informative introduction to campus life and resources.");
        announcementValues1.put("Date", "2023-08-18");
        announcementValues1.put("FacultyID", 1);
        db.insert("Announcements", null, announcementValues1);

        ContentValues announcementValues2 = new ContentValues();
        announcementValues2.put("Title", "Campus Wi-Fi Upgrades");
        announcementValues2.put("Message", "We are enhancing our campus Wi-Fi network for better connectivity. Expect temporary disruptions in certain areas as we make these improvements over the next week.");
        announcementValues2.put("Date", "2023-09-10");
        announcementValues2.put("FacultyID", 2);
        db.insert("Announcements", null, announcementValues2);

        ContentValues feesValues1 = new ContentValues();
        feesValues1.put("DatePaid", "2023-07-01");
        feesValues1.put("Amount", 186000.0);
        feesValues1.put("Purpose", "Course ");
        feesValues1.put("StudentID", studentId9);
        db.insert("Fees", null, feesValues1);

        ContentValues feesValues2 = new ContentValues();
        feesValues2.put("DatePaid", "2023-07-02");
        feesValues2.put("Amount", 86000.0);
        feesValues2.put("Purpose", "Hostel ");
        feesValues2.put("StudentID", studentId9);
        db.insert("Fees", null, feesValues2);

        ContentValues feesValues4 = new ContentValues();
        feesValues4.put("DatePaid", "2023-06-01");
        feesValues4.put("Amount", 186000.0);
        feesValues4.put("Purpose", "Course");
        feesValues4.put("StudentID", studentId11);
        db.insert("Fees", null, feesValues4);
        ContentValues feesValues3 = new ContentValues();
        feesValues3.put("DatePaid", "2023-06-02");
        feesValues3.put("Amount", 86000.0);
        feesValues3.put("Purpose", "Hostel");
        feesValues3.put("StudentID", studentId11);
        db.insert("Fees", null, feesValues3);
        ContentValues feesValues6 = new ContentValues();
        feesValues6.put("DatePaid", "2023-06-01");
        feesValues6.put("Amount", 186000.0);
        feesValues6.put("Purpose", "Course");
        feesValues6.put("StudentID", studentId10);
        db.insert("Fees", null, feesValues6);
        ContentValues feesValues5 = new ContentValues();
        feesValues5.put("DatePaid", "2023-06-02");
        feesValues5.put("Amount", 86000.0);
        feesValues5.put("Purpose", "Hostel");
        feesValues5.put("StudentID", studentId10);
        db.insert("Fees", null, feesValues5);
    }

    /*LOGIN FUNCTIONS*/
    public Boolean checkEmailPassword(String student_reg_number, String student_password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from StudentProfile where RegistrationNumber = ? and Password = ?", new String[]{student_reg_number, student_password});
        return cursor.getCount() > 0;
    }

    public Boolean checkEmailPassword_for_fac(String fac_user_name, String fac_password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from FacultyProfile where Username = ? and Password = ?", new String[]{fac_user_name, fac_password});
        return cursor.getCount() > 0;
    }

    /*ID FUNCTIONS*/
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
    public int getStudentIdForMarks(String studentName, String studentRollNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT StudentID FROM StudentProfile WHERE Name = ? AND RegistrationNumber = ?", new String[]{studentName, studentRollNumber});

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

    /*PASSWORD FUNCTIONS*/
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

    public Boolean updateStudentPassword(String StudentRegno, String stud_password) {
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

    /*CLASS FUNCTIONS*/
    public Cursor getClassSectionsForFaculty(int facultyId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "SELECT DISTINCT Class.ClassID, Class.ClassName, Class.Section " +
                "FROM ClassAssignment " +
                "INNER JOIN Class ON ClassAssignment.ClassID = Class.ClassID " +
                "WHERE FacultyID = ?";
        String[] selectionArgs = {String.valueOf(facultyId)};
        return db.rawQuery(sqlQuery, selectionArgs);
    }

    /*SUBJECT FUNCTIONS*/
    public Cursor getSubjectsForFaculty(int facultyId, int classId, String section) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "SELECT DISTINCT S.SubjectID AS SubjectID, S.SubjectName AS SubjectName " +
                "FROM ClassAssignment CA " +
                "INNER JOIN Subjects S ON CA.SubjectID = S.SubjectID " +
                "WHERE CA.FacultyID = ? AND CA.ClassID = ?";

        Log.d("ClassGet", "id" + facultyId + "classid" + classId);
        String[] selectionArgs = {String.valueOf(facultyId), String.valueOf(classId)};
        Cursor cursor = db.rawQuery(sqlQuery, selectionArgs);

        int numberOfItems = 0;
        if (cursor != null) {
            numberOfItems = cursor.getCount();
        }
        Log.d("CursorItemCount", "Number of items in cursor: " + numberOfItems);
        return cursor; // Return the cursor without closing it here
    }

    /*MARKS FUNCTIONS*/
    public Cursor getStudentsForFacultyMarks(int facultyId, int selectedClass, String selectedSection, int selectedSubject) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor classCursor = db.rawQuery(
                "SELECT ClassID FROM Class WHERE ClassID = ?",
                new String[]{String.valueOf(selectedClass)}
        );
        int classId = -1;
        if (classCursor != null && classCursor.moveToFirst()) {
            classId = classCursor.getColumnIndex("ClassID");
            if (classId != -1) {
                classId = classCursor.getInt(classId);
                classCursor.close();
            }
        } else {
            Log.e("DatabaseError", "Class not found.");
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
    public boolean updateOrInsertMarks(int subjectId, String date, int studentID, int classId, String assignmentType, float marks) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Date", date);

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

        String whereClause = "SubjectID = ? AND ClassID = ? AND StudentID = ?";
        String[] whereArgs = {String.valueOf(subjectId), String.valueOf(classId), String.valueOf(studentID)};
        Log.d("UpdateMarks", "Sid" + subjectId+classId+studentID);
        int rowsUpdated = db.update("Marks", values, whereClause, whereArgs);

        if (rowsUpdated > 0) {
            Log.d("UpdateMarks", "Marks and date updated successfully for StudentID: " + studentID + ", ClassID: " + classId + ", SubjectID: " + subjectId);

            // Calculate total marks and update in the database
            float totalMarks = calculateTotalMarks(studentID, classId, subjectId, db);
            updateTotalMarks(studentID, classId, subjectId, totalMarks, db);

            db.close();

            return true;
        } else {
            // If no records were updated, it means there was no matching record, so insert a new one.
            values.put("SubjectID", subjectId);
            values.put("ClassID", classId);
            values.put("StudentID", studentID);
            long newRowId = db.insert("Marks", null, values);
            float totalMarks = calculateTotalMarks(studentID, classId, subjectId, db);
            updateTotalMarks(studentID, classId, subjectId, totalMarks, db);
            db.close();

            if (newRowId != -1) {
                Log.d("UpdateMarks", "New record inserted successfully for StudentID: " + studentID + ", ClassID: " + classId + ", SubjectID: " + subjectId);
                return true;
            } else {
                Log.e("UpdateMarks", "Failed to insert a new record for StudentID: " + studentID + ", ClassID: " + classId + ", SubjectID: " + subjectId);
                return false;
            }
        }
    }


    // Method to calculate total marks for a specific student within a class and subject
    private float calculateTotalMarks(int studentID, int classId, int subjectId, SQLiteDatabase db) {
        float totalMarks = 0.0f;

        // Retrieve Assignment1, Assignment2, Assignment3, Assignment4, and Midterm marks from the database for the student
        String[] columns = {"Assignment1", "Assignment2", "Assignment3", "Assignment4", "Midterm"};
        String whereClause = "StudentID = ? AND ClassID = ? AND SubjectID = ?";
        String[] whereArgs = {String.valueOf(studentID), String.valueOf(classId), String.valueOf(subjectId)};

        Cursor cursor = db.query("Marks", columns, whereClause, whereArgs, null, null, null);
        int assignment1Index = cursor.getColumnIndex("Assignment1");
        int assignment2Index = cursor.getColumnIndex("Assignment2");
        int assignment3Index = cursor.getColumnIndex("Assignment3");
        int assignment4Index = cursor.getColumnIndex("Assignment4");
       int midtermIndex = cursor.getColumnIndex("Midterm");
        if (cursor.moveToFirst()) {
            float assignment1 = cursor.getFloat(assignment1Index);
            float assignment2 = cursor.getFloat(assignment2Index);
            float assignment3 = cursor.getFloat(assignment3Index);
            float assignment4 = cursor.getFloat(assignment4Index);
            float midterm = cursor.getFloat(midtermIndex);

            // Calculate total marks based on your criteria
            totalMarks = assignment1 + assignment2 + assignment3 + assignment4 + midterm;
        }

        cursor.close();
        return totalMarks;
    }

    // Method to update total marks for a specific student within a class and subject
    private void updateTotalMarks(int studentID, int classId, int subjectId, float totalMarks, SQLiteDatabase db) {
        ContentValues totalMarksValues = new ContentValues();
        totalMarksValues.put("TotalMarks", totalMarks);

        String totalMarksWhereClause = "StudentID = ? AND ClassID = ? AND SubjectID = ?";
        String[] totalMarksWhereArgs = {String.valueOf(studentID), String.valueOf(classId), String.valueOf(subjectId)};

        db.update("Marks", totalMarksValues, totalMarksWhereClause, totalMarksWhereArgs);
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

        String[] selectionArgs = {String.valueOf(studentId), String.valueOf(semester)};
        Log.d("UpdateMarks", "Semester" + semester);

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

    /*ATTENDANCE FUNCTIONS*/
    public void AddAttendance(Attendance attendance) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("ClassGet", "date" + attendance.AttendanceDate);

        ContentValues attendanceValues = new ContentValues();
        attendanceValues.put("Date", attendance.AttendanceDate);
        attendanceValues.put("ClassID", attendance.ClassID);
        attendanceValues.put("StudentID", attendance.StudentID);
        attendanceValues.put("SubjectID", attendance.SubjectID);

        String[] whereArgs = {
                String.valueOf(attendance.ClassID),
                String.valueOf(attendance.StudentID),
                String.valueOf(attendance.SubjectID)
        };

        Cursor cursor = db.rawQuery("SELECT * FROM Attendance WHERE ClassID=? AND StudentID=? AND SubjectID=?", whereArgs);
        int attendedValueIndex = cursor.getColumnIndex("Attended");
        int missedValueIndex = cursor.getColumnIndex("Missed");
        if (cursor.moveToFirst()) {
            int attendedValue = cursor.getInt(attendedValueIndex);
            int missedValue = cursor.getInt(missedValueIndex);

            if (attendance.Status.equals("present")) {
                attendedValue++;
            } else if (attendance.Status.equals("absent")) {
                missedValue++;
            }

            ContentValues updateValues = new ContentValues();
            updateValues.put("Attended", attendedValue);
            updateValues.put("Missed", missedValue);

            db.update("Attendance", updateValues, "ClassID=? AND StudentID=? AND SubjectID=?", whereArgs);
        } else {
            attendanceValues.put("Date", attendance.AttendanceDate);
            if (attendance.Status.equals("present")) {
                attendanceValues.put("Attended", 1);
                attendanceValues.put("Missed", 0);
            } else if (attendance.Status.equals("absent")) {
                attendanceValues.put("Attended", 0);
                attendanceValues.put("Missed", 1);
            }

            db.insert("Attendance", null, attendanceValues);
        }

        cursor.close();
        db.close();
    }


    public List<SubjectWithAttendance> getSubjectsAndAttendanceForStudent(int studentId, int semester) {
        List<SubjectWithAttendance> subjectsList = new ArrayList<>();
        Log.d("semester", "semester" + semester + "regno" + studentId);

        String classQuery = "SELECT ClassID FROM StudentProfile WHERE RegistrationNumber = ? AND Semester = ?";
        Cursor classCursor = db.rawQuery(classQuery, new String[]{String.valueOf(studentId), String.valueOf(semester)});
        int classIdIndex = classCursor.getColumnIndex("ClassID");
        if (classCursor.moveToFirst()) {
            int classId = classCursor.getInt(classIdIndex);

            String subjectsQuery = "SELECT Subjects.SubjectID, Subjects.SubjectName FROM ClassAssignment " +
                    "JOIN Subjects ON ClassAssignment.SubjectID = Subjects.SubjectID " +
                    "WHERE ClassAssignment.ClassID = ?";
            Cursor subjectsCursor = db.rawQuery(subjectsQuery, new String[]{String.valueOf(classId)});
            int subjectIdIndex = subjectsCursor.getColumnIndex("SubjectID");
            int subjectNameIndex = subjectsCursor.getColumnIndex("SubjectName");
            while (subjectsCursor.moveToNext()) {
                int subjectId = subjectsCursor.getInt(subjectIdIndex);
                String subjectName = subjectsCursor.getString(subjectNameIndex);

                String attendanceQuery = "SELECT SUM(Attended) AS TotalAttended, SUM(Missed) AS TotalMissed FROM Attendance WHERE ClassID = ? AND SubjectID = ? AND StudentID = ?";
                Cursor attendanceCursor = db.rawQuery(attendanceQuery, new String[]{String.valueOf(classId), String.valueOf(subjectId), String.valueOf(studentId)});

                int totalAttended = 0;
                int totalMissed = 0;
                int totalAttendedIndex = attendanceCursor.getColumnIndex("TotalAttended");
                int totalMissedIndex = attendanceCursor.getColumnIndex("TotalMissed");
                if (attendanceCursor.moveToFirst()) {
                    totalAttended = attendanceCursor.getInt(totalAttendedIndex);
                    totalMissed = attendanceCursor.getInt(totalMissedIndex);
                }
                attendanceCursor.close();

                SubjectWithAttendance subjectWithAttendance = new SubjectWithAttendance(subjectName, subjectId, totalAttended, totalMissed);
                subjectsList.add(subjectWithAttendance);
            }
            subjectsCursor.close();
        }
        classCursor.close();

        return subjectsList;
    }

    /*ANNOUNCEMENT FUNCTIONS*/
    public Boolean creatingFacultyAnnouncement(int loggedInFacultyId, String title, String message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues announcementValues = new ContentValues();
        announcementValues.put("Title", title);
        announcementValues.put("Message", message);
        SimpleDateFormat pcDateFormat = new SimpleDateFormat(DateFormat.getBestDateTimePattern(Locale.getDefault(), "yyyy-MM-dd"), Locale.getDefault());
        Date date = new Date();
        String formattedDate = pcDateFormat.format(date);

        announcementValues.put("Date", String.format(formattedDate));
        announcementValues.put("FacultyID", loggedInFacultyId);

        long result = db.insert("Announcements", null, announcementValues);
        return result > 0;
    }

    public Cursor getAnnouncement() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "SELECT Title, Message, Date FROM Announcements ORDER BY Date DESC, AnnouncementID DESC";
        return db.rawQuery(sqlQuery, null);
    }
    public Cursor getAnnouncementForFaculty(int loggedInFacultyId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "SELECT Title, Message, Date FROM Announcements WHERE FacultyId != ? ORDER BY Date DESC, AnnouncementID DESC";
        String[] selectionArgs = {String.valueOf(loggedInFacultyId)};
        return db.rawQuery(sqlQuery, selectionArgs);
    }
    public String getAnnouncementMessage(String title, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT Message FROM Announcements WHERE Title = ? AND Date = ?";
        Cursor cursor = db.rawQuery(query, new String[]{title, date});
        int messageIndex = cursor.getColumnIndex("Message");
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String message = cursor.getString(messageIndex);
                cursor.close();
                return message;
            }
            cursor.close();
        }
        return null;
    }

    public String getCreatedAnnouncementMessage(String title, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT Message FROM Announcements WHERE Title = ? AND Date = ?";
        Cursor cursor = db.rawQuery(query, new String[]{title, date});
        int messageIndex = cursor.getColumnIndex("Message");
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String message = cursor.getString(messageIndex);
                cursor.close();
                return message;
            }
            cursor.close();
        }
        return null;
    }

    public Cursor getFacultyCreatedAnnouncement(int loggedInFacultyId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"Title", "Message", "Date"};
        String selection = "FacultyID = ?";
        String[] selectionArgs = {String.valueOf(loggedInFacultyId)};
        String sortOrder = "Date DESC, AnnouncementID DESC";
        return db.query("Announcements", columns, selection, selectionArgs, null, null, sortOrder);
    }

    public int getAnnouncementIdFaculty(int facultyId, String title) {
        SQLiteDatabase db = this.getReadableDatabase();

        String announcementQuery = "SELECT AnnouncementID FROM Announcements WHERE FacultyID = ? AND Title = ?";
        Cursor announcementCursor = db.rawQuery(announcementQuery, new String[]{String.valueOf(facultyId), title});
        int updateIdIndex = announcementCursor.getColumnIndex("AnnouncementID");

        if (announcementCursor.moveToFirst()) {
            int announcementID = announcementCursor.getInt(updateIdIndex);
            announcementCursor.close();
            return announcementID;
        } else {
            announcementCursor.close();
            return -1;
        }
    }
    public int getAnnouncementFacId(String title, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        int facultyId = -1;

        if (db != null) {
            String[] projection = { "FacultyId" };
            String selection = "Title = ? AND Date = ?";
            String[] selectionArgs = { title, date };

            Cursor cursor = db.query("Announcements", projection, selection, selectionArgs, null, null, null);
            int facultyIdIndex = cursor.getColumnIndex("FacultyID");
            if (cursor.moveToFirst()) {
                facultyId = cursor.getInt(facultyIdIndex);
                cursor.close();
            }

            db.close();
        }

        return facultyId;
    }





    public Cursor updatingFacultyAnnouncement(int getAnnouncementId, String title, String message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues announcementValues = new ContentValues();
        SimpleDateFormat pcDateFormat = new SimpleDateFormat(DateFormat.getBestDateTimePattern(Locale.getDefault(), "yyyy-MM-dd"), Locale.getDefault());
        Date date = new Date();
        String formattedDate = pcDateFormat.format(date);
        announcementValues.put("Title", title);
        announcementValues.put("Message", message);
        announcementValues.put("Date", formattedDate);

        String whereClause = "AnnouncementID = ?";
        String[] whereArgs = {String.valueOf(getAnnouncementId)};

        int result = db.update("Announcements", announcementValues, whereClause, whereArgs);

        if (result > 0) {
            Log.d("UpdateAnnouncement", "Update successful");
        } else {
            Log.e("UpdateAnnouncement", "Update failed");
        }

        String sortOrder = "Date DESC"; // Sort by date in descending order

        Cursor cursor = db.query("Announcements", null, whereClause, whereArgs, null, null, sortOrder);

        return cursor;
    }



    public boolean deletingFacultyAnnouncement(int getAnnouncementId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "AnnouncementID = ?";
        String[] whereArgs = {String.valueOf(getAnnouncementId)};

        int result = db.delete("Announcements", whereClause, whereArgs);

        if (result > 0) {
            Log.d("DeleteAnnouncement", "Deletion successful");
        } else {
            Log.e("DeleteAnnouncement", "Deletion failed");
        }

        return result > 0;
    }



    /*FEES FUNCTIONS*/
    public Cursor getFees(int studentId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "SELECT Purpose, Amount, DatePaid FROM Fees WHERE StudentID=? ORDER BY FeesID DESC";
        String[] selectionArgs = {String.valueOf(studentId)};
        return db.rawQuery(sqlQuery, selectionArgs);
    }

    /*CHAT*/
    public Cursor getStudentClass(int studentId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "SELECT StudentProfile.ClassID, ClassName, StudentProfile.Name AS StudentName FROM StudentProfile, Class WHERE StudentID = ? AND StudentProfile.ClassID = Class.ClassID";
        String[] selectionArgs = {String.valueOf(studentId)};
        return db.rawQuery(sqlQuery, selectionArgs);
    }

    public Cursor getFacultyList(int classId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d("DebugTag", "ClassID: " + classId);

        String query = "SELECT DISTINCT f.Name AS FacultyName, f.FacultyID " +
                "FROM FacultyProfile f " +
                "INNER JOIN ClassAssignment ca ON f.FacultyID = ca.FacultyID " +
                "WHERE ca.ClassID = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(classId)});

        if (cursor != null) {
            return cursor;
        } else {
            return null;
        }
    }
    // Function to insert a chat message into the database
    public long insertChatMessage(int senderId, int receiverId, String messageText, String storedUserType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("SenderID", senderId);
        values.put("ReceiverID", receiverId);
        values.put("MessageText", messageText);
        values.put("SenderType", storedUserType);
        values.put("IsRead", 0);
        return db.insert("Messages", null, values);
    }

    public Cursor getChatMessages(int senderId, int receiverId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Messages WHERE (SenderID = ? AND ReceiverID = ?) OR (SenderID = ? AND ReceiverID = ?) ORDER BY Timestamp ASC";
        String[] selectionArgs = {String.valueOf(senderId), String.valueOf(receiverId), String.valueOf(receiverId), String.valueOf(senderId)};
        return db.rawQuery(query, selectionArgs);
    }
    public Cursor getStudentsWhoMessagedFaculty(int facultyId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT DISTINCT sp.Name as StudentName , sp.StudentID, f.Name AS FacultyName " +
                "FROM StudentProfile sp " +
                "JOIN Messages m ON sp.StudentID = m.SenderID " +
                "LEFT JOIN FacultyProfile f ON m.ReceiverID = f.FacultyID " +
                "WHERE m.ReceiverID = ?";

        String[] selectionArgs = {String.valueOf(facultyId)};
        return db.rawQuery(query, selectionArgs);
    }
    public void markMessagesAsRead(int studentId, int facultyId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("IsRead", 1);
        String selection = "(SenderID = ? AND ReceiverID = ?) OR (SenderID = ? AND ReceiverID = ?)";
        String[] selectionArgs = {String.valueOf(studentId), String.valueOf(facultyId), String.valueOf(facultyId), String.valueOf(studentId)};
        int rowsUpdated = db.update("Messages", values, selection, selectionArgs);

        if (rowsUpdated > 0) {
            Log.d("DatabaseManager", "Marked " + rowsUpdated + " messages as read");
        } else {
            Log.d("DatabaseManager", "No messages were marked as read");
        }
    }

    public int getUnreadMessageCountForFaculty(int facultyId, int studentId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT COUNT(*) FROM Messages WHERE SenderID = ? AND ReceiverID = ? AND IsRead = 0 AND SenderType = 'Faculty'";
        String[] selectionArgs = { String.valueOf(facultyId), String.valueOf(studentId)};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        int unreadMessageCount = 0;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                unreadMessageCount = cursor.getInt(0);
            }
            cursor.close();
        }

        return unreadMessageCount;
    }
    public int getUnreadMessageCountForStudent(int studentId, int facultyId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT COUNT(*) FROM Messages WHERE SenderID = ? AND ReceiverID = ? AND IsRead = 0 AND SenderType = 'Student'";
        String[] selectionArgs = { String.valueOf(studentId), String.valueOf(facultyId)};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        int unreadMessageCount = 0;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                unreadMessageCount = cursor.getInt(0);
            }
            cursor.close();
        }

        return unreadMessageCount;
    }

    public List<SubjectWithMarks> getMarksheetForStudent(int studentId, int semester) {
        List<SubjectWithMarks> marksheetList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT m.SubjectName, m.Grade, m.Credits, sg.GPA, sg.CGPA " +
                "FROM Marksheet m " +
                "INNER JOIN SemesterGPA sg ON m.SemesterGPAID = sg.SemesterGPAID " +
                "WHERE m.StudentID = ? AND sg.Semester = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(studentId), String.valueOf(semester)});
        Log.d("Debug", "Student: " + studentId);
        Log.d("Debug", "Semester: " + semester);
        if (cursor != null && cursor.moveToFirst()) {
            int subjectNameIndex = cursor.getColumnIndex("SubjectName");
            int gradeIndex = cursor.getColumnIndex("Grade");
          int creditsIndex = cursor.getColumnIndex("Credits");
          int gpaIndex = cursor.getColumnIndex("GPA");
          int cgpaIndex = cursor.getColumnIndex("CGPA");
            do {
                String subjectName = cursor.getString(subjectNameIndex);
                String grade = cursor.getString(gradeIndex);
                float credits = cursor.getFloat(creditsIndex);
                float gpa = cursor.getFloat(gpaIndex);
                float cgpa = cursor.getFloat(cgpaIndex);

                SubjectWithMarks subjectWithMarks = new SubjectWithMarks(subjectName, grade, credits, gpa, cgpa);
                marksheetList.add(subjectWithMarks);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return marksheetList;
    }

}



