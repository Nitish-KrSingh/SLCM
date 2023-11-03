package com.example.slcm.Student;

public class SubjectWithMarks {
    private float gpa= 0.0F;
    private float cgpa=0.0F;
    private int subjectId=0;
    private final String subjectName;
    private Double assignment1=0.0;
    private Double assignment2=0.0;
    private Double assignment3=0.0;
    private Double assignment4=0.0;
    private Double midterm=0.0;
    private float credits= 0.0F;
    private String grade= String.valueOf('A');

    public SubjectWithMarks(int subjectId, String subjectName, Double assignment1, Double assignment2, Double assignment3, Double assignment4, Double midterm) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.assignment1 = assignment1;
        this.assignment2 = assignment2;
        this.assignment3 = assignment3;
        this.assignment4 = assignment4;
        this.midterm = midterm;
    }

    public SubjectWithMarks(String subjectName, String grade, float credits, float gpa, float cgpa) {
        this.subjectName = subjectName;
        this.grade = grade;
        this.credits= credits;
        this.gpa= gpa;
        this.cgpa= cgpa;

    }

    public int getSubjectId() {
        return subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public Double getAssignment1() {
        return assignment1;
    }

    public Double getAssignment2() {
        return assignment2;
    }

    public Double getAssignment3() {
        return assignment3;
    }

    public Double getAssignment4() {
        return assignment4;
    }

    public Double getMidterm() {
        return midterm;
    }

    public String getGrade() {
        return grade;
    }
    public float getCredits() {
        return credits;
    }

    public float getGPA() { return gpa;
    }
    public float getCGPA() {
        return  cgpa;
    }
}
