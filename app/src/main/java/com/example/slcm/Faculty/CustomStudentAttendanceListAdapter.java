package com.example.slcm.Faculty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.slcm.R;
import com.example.slcm.Student.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomStudentAttendanceListAdapter extends ArrayAdapter<Student> {

    private static final String ABSENT = "absent", PRESENT = "present";
    private final Context context;
    private final ArrayList<Student> students;

    private final Map<String, String> presentMap = new HashMap<String, String>();

    public CustomStudentAttendanceListAdapter(Context context, ArrayList<Student> studentList) {
        super(context, 0, studentList);
        this.context = context;
        this.students = studentList;
        System.out.println(studentList);

        for (Student student : this.students) {
            presentMap.put(student.getRollNumber(), ABSENT);
        }
        System.out.println(presentMap);

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_faculty_student_list_name, parent, false);
        }

        TextView textViewName = convertView.findViewById(R.id.ViewName);
        TextView textViewRollNumber = convertView.findViewById(R.id.ViewRollNumber);

        Student student = students.get(position);

        textViewName.setText(student.getName());
        textViewRollNumber.setText(student.getRollNumber());

        RelativeLayout rel = convertView.findViewById(R.id.attendence_single_list_item);

        rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String presentStatus = presentMap.get(student.getRollNumber());

                if (presentStatus.equals(ABSENT)) {
                    presentMap.put(student.getRollNumber(), PRESENT);
                    rel.setBackgroundColor(context.getResources().getColor(R.color.green));
                } else {
                    presentMap.put(student.getRollNumber(), ABSENT);
                    rel.setBackgroundColor(context.getResources().getColor(R.color.red));
                }
            }
        });

        if (presentMap.get(student.getRollNumber()).equals(ABSENT)) {
            rel.setBackgroundColor(context.getResources().getColor(R.color.red));
        } else {
            rel.setBackgroundColor(context.getResources().getColor(R.color.green));
        }

        return convertView;
    }

    public void SetAllPresent() {
        for (Student student : this.students) {
            presentMap.put(student.getRollNumber(), PRESENT);
        }
        notifyDataSetChanged();
    }

    public void SetAllAbsent() {
        for (Student student : this.students) {
            presentMap.put(student.getRollNumber(), ABSENT);
        }
        notifyDataSetChanged();

    }

    public Map<String, String> GetPresentMap() {
        return presentMap;
    }
}
