package com.example.slcm.Faculty;

public class Attendance {
    public int ClassID, SubjectID;
    public String AttendanceDate, Status, StudentID;

    public Attendance(String studentID, int classID, int subjectID, String attendanceDate, String status) {
        StudentID = studentID;
        ClassID = classID;
        SubjectID = subjectID;
        AttendanceDate = attendanceDate;
        Status = status;
    }
}
