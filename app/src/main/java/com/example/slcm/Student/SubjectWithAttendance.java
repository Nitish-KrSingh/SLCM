package com.example.slcm.Student;

public class SubjectWithAttendance {
    private final String subjectName;
    private int totalClasses;
    private final int subjectId;
    private final int attendedClasses;
    private final int missedClasses;
    private int total;

    public SubjectWithAttendance(String subjectName, int subjectId, int totalAttended, int totalMissed) {
        this.subjectName = subjectName;
        this.subjectId = subjectId;
        this.attendedClasses = totalAttended;
        this.missedClasses = totalMissed;
    }


    public String getSubjectName() {
        return subjectName;
    }

    public int getSubjectID() {
        return subjectId;
    }

    public int getTotalClasses() {
        total = attendedClasses + missedClasses;
        return total;
    }

    public int getAttendedClasses() {
        return attendedClasses;
    }

    public int getMissedClasses() {
        return missedClasses;
    }

    public int getPercentage() {
        total = attendedClasses + missedClasses;
        if (total == 0) {
            return 0;
        }
        return (attendedClasses * 100) / total;
    }
}