package com.example.slcm.Student;

public class StudentFees_ListItem {
    private String course_fees;
    private String rupees;
    private String date;

    public String getName() {return course_fees;}

    public String getDesignation() {return rupees;}

    public String getLocation() {return date;}

    public void setName(String name) {this.course_fees = name;}

    public void setDesignation(String designation) {this.rupees = designation;}

    public void setLocation(String location) {this.date = location;}
}
