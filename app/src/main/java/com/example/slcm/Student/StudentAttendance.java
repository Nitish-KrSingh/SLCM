package com.example.slcm.Student;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.slcm.DatabaseManager;
import com.example.slcm.R;

import java.util.List;
import java.util.Objects;

public class StudentAttendance extends AppCompatActivity {
    ListView listView;
    String studentRegNo;
    DatabaseManager databaseManager;
    MyAdapter myAdapter;
    Spinner dropdown;
    RomanToNumber converter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Attendance");

        dropdown = findViewById(R.id.semester_select_spinner);
        listView = findViewById(R.id.listView);
        SharedPreferences sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE);
        studentRegNo = sharedPreferences.getString("LOGIN_USER", "");

        databaseManager = new DatabaseManager(this);

        setupSpinner();

        loadSubjectsForSelectedSemester();
    }

    private void setupSpinner() {
        dropdown = findViewById(R.id.semester_select_spinner);
        converter = new RomanToNumber();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.semesters, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                loadSubjectsForSelectedSemester();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing when nothing is selected
            }
        });
    }

    private void loadSubjectsForSelectedSemester() {
        String selectedSemester = dropdown.getSelectedItem().toString();
        int selectedValue = converter.romanToNumber(selectedSemester);
        int regNo= Integer.parseInt(studentRegNo);
        List<SubjectWithAttendance> subjectsList = databaseManager. getSubjectsAndAttendanceForStudent(regNo, selectedValue);

        myAdapter = new MyAdapter(subjectsList);
        listView.setAdapter(myAdapter);
    }

    private class MyAdapter extends BaseAdapter {
        private List<SubjectWithAttendance> subjectsList;

        public MyAdapter(List<SubjectWithAttendance> subjectsList) {
            this.subjectsList = subjectsList;
        }

        @Override
        public int getCount() {
            return subjectsList.size();
        }

        @Override
        public Object getItem(int position) {
            return subjectsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View myView = layoutInflater.inflate(R.layout.activity_student_attendance_list_item, parent, false);

            SubjectWithAttendance subjectWithAttendance = subjectsList.get(position);

            TextView subjectText = myView.findViewById(R.id.SubjectText);
            TextView percent = myView.findViewById(R.id.Percent);
            TextView totalVal = myView.findViewById(R.id.totalval);
            TextView attendedVal = myView.findViewById(R.id.attendedval);
            TextView missedVal = myView.findViewById(R.id.missedval);

            subjectText.setText(subjectWithAttendance.getSubjectName());
            percent.setText(String.format("%d%%", subjectWithAttendance.getPercentage()));
            totalVal.setText(String.valueOf(subjectWithAttendance.getTotalClasses()));
            attendedVal.setText(String.valueOf(subjectWithAttendance.getAttendedClasses()));
            missedVal.setText(String.valueOf(subjectWithAttendance.getMissedClasses()));

            return myView;
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.student_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        StudentMenuHandler.handleMenuAction(item, this);
        return super.onOptionsItemSelected(item);
    }
}
