package com.example.slcm.Student;

public class SubjectWithMarks {
    private int subjectId;
    private String subjectName;
    private Double assignment1;
    private Double assignment2;
    private Double assignment3;
    private Double assignment4;
    private Double midterm;

    public SubjectWithMarks(int subjectId, String subjectName, Double assignment1, Double assignment2, Double assignment3, Double assignment4, Double midterm) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.assignment1 = assignment1;
        this.assignment2 = assignment2;
        this.assignment3 = assignment3;
        this.assignment4 = assignment4;
        this.midterm = midterm;
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
}
