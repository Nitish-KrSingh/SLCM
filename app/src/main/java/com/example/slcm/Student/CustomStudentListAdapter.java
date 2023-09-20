package com.example.slcm.Student;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.slcm.R;
import com.example.slcm.Student.Student;

public class CustomStudentListAdapter extends BaseAdapter {
    private Context context;
    private Student[] students;

    public CustomStudentListAdapter(Context context, Student[] students) {
        this.context = context;
        this.students = students;
    }

    @Override
    public int getCount() {
        return students.length;
    }

    @Override
    public Object getItem(int position) {
        return students[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_list_item_student, parent, false);
        }

        TextView textViewName = convertView.findViewById(R.id.textViewName);
        TextView textViewRollNumber = convertView.findViewById(R.id.textViewRollNumber);
        EditText editTextRollNumber = convertView.findViewById(R.id.editTextMarks);

        Student student = students[position];

        textViewName.setText(student.getName());
        textViewRollNumber.setText(student.getRollNumber());

        // Set the background color of EditText based on input validation
        if (TextUtils.isEmpty(editTextRollNumber.getText())) {
            editTextRollNumber.setBackgroundResource(R.color.red);
        } else {
            editTextRollNumber.setBackgroundResource(R.color.green);
        }

        return convertView;
    }
}
