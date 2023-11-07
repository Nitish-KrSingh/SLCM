package com.example.slcm.Faculty;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.slcm.R;
import com.example.slcm.Student.Student;

import java.util.ArrayList;

public class CustomStudentEnterMarksListAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<Student> students;
    private final String selectedAssignmentType;
    private int maxmarks;

    public CustomStudentEnterMarksListAdapter(Context context, ArrayList<Student> students, String selectedAssignmentType, int maxmarks) {
        this.context = context;
        this.students = students;
        this.selectedAssignmentType = selectedAssignmentType;
        this.maxmarks=maxmarks;
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int position) {
        return students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_student_marks_list_item, parent, false);
        }

        TextView textViewName = convertView.findViewById(R.id.textViewName);
        TextView textViewRollNumber = convertView.findViewById(R.id.textViewRollNumber);
        EditText editTextMarks = convertView.findViewById(R.id.editTextMarks);

        Student student = students.get(position);

        textViewName.setText(student.getName());
        textViewRollNumber.setText(student.getRollNumber());
        
        if (TextUtils.isEmpty(editTextMarks.getText())) {
            editTextMarks.setBackgroundResource(R.color.red);
        } else {
            float marks = Float.parseFloat(editTextMarks.getText().toString());
            if (!isValidMarks(marks, selectedAssignmentType)) {
                editTextMarks.setBackgroundResource(R.color.red);
                Toast.makeText(context, "Invalid marks (max. marks: "+maxmarks+") for " + student.getName()+":"+student.getRollNumber(), Toast.LENGTH_SHORT).show();
            } else {
                editTextMarks.setBackgroundResource(R.color.green);
            }
        }

        return convertView;
    }

    private boolean isValidMarks(float marks, String assignmentType) {
        if ("Midterm".equals(assignmentType)) {
            return marks >= 0 && marks <= 30;
        } else {
            return marks >= 0 && marks <= 5;
        }
    }
}