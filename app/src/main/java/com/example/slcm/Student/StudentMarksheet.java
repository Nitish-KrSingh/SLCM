package com.example.slcm.Student;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

public class StudentMarksheet extends AppCompatActivity {
    ListView listView;
    String studentRegNo;
    DatabaseManager databaseManager;
    MyAdapter myAdapter;
    int studentId;
    Spinner dropdown;
    RomanToNumber converter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_marksheet);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Mark Sheet");

        dropdown = findViewById(R.id.semester_select_spinner);
        listView = findViewById(R.id.listViewMarkSheet);
        SharedPreferences sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE);
        studentRegNo = sharedPreferences.getString("LOGIN_USER", "");
        studentId = sharedPreferences.getInt("STUDENT_ID", -1);

        databaseManager = new DatabaseManager(this);

        setupSpinner();

        loadMarksheetForSelectedSemester();
        FloatingActionButton fabMessage = findViewById(R.id.fabMessage);
        fabMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event to open the message activity.
                Intent intent = new Intent(StudentMarksheet.this, StudentChatViewFaculty.class);
                startActivity(intent);
            }
        });
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
    private void setupSpinner() {
        dropdown = findViewById(R.id.semester_select_spinner);
        converter = new RomanToNumber();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.semesters, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
        DatabaseManager dbHelper = new DatabaseManager(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor nameCursor = db.rawQuery("SELECT c.Semester FROM StudentProfile s JOIN Class c ON c.ClassID = s.ClassID WHERE StudentID = ?", new String[]{String.valueOf(studentId)});
        int semester = 2;
        if (nameCursor.moveToFirst()) {
            semester = nameCursor.getInt(nameCursor.getColumnIndexOrThrow("Semester"));
        }
        dropdown.setSelection(semester-1);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                loadMarksheetForSelectedSemester();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing when nothing is selected
            }
        });
    }

    private void loadMarksheetForSelectedSemester() {
        String selectedSemester = dropdown.getSelectedItem().toString();
        int selectedValue = converter.romanToNumber(selectedSemester);
        int regNo = Integer.parseInt(studentRegNo);
        List<SubjectWithMarks> marksheetList = databaseManager.getMarksheetForStudent(studentId, selectedValue);
        Log.d("Debug", "Selected Semester: " + selectedSemester);
        Log.d("Debug", "Student Reg No: " + studentId);
        Log.d("Debug", "Semester Value: " + selectedValue);

        if (marksheetList != null) {
            Log.d("Debug", "Mark Sheet Size: " + marksheetList.size());
        }
        myAdapter = new MyAdapter(marksheetList);
        listView.setAdapter(myAdapter);
    }

    private class MyAdapter extends BaseAdapter {
        private final List<SubjectWithMarks> marksheetList;

        public MyAdapter(List<SubjectWithMarks> marksheetList) {
            this.marksheetList = marksheetList;
        }

        @Override
        public int getCount() {
            return marksheetList.size();
        }

        @Override
        public Object getItem(int position) {
            return marksheetList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View myView = layoutInflater.inflate(R.layout.activity_student_marksheet_list_item, parent, false);

            SubjectWithMarks subjectWithMarks = marksheetList.get(position);

            TextView subjectText = myView.findViewById(R.id.textViewSubject);
            TextView gradeText = myView.findViewById(R.id.textViewGrade);
            TextView creditsText = myView.findViewById(R.id.textViewCredits);
            TextView gpa = findViewById(R.id.gpa);
            TextView cgpa = findViewById(R.id.cgpa);

            subjectText.setText(subjectWithMarks.getSubjectName());
            gradeText.setText(subjectWithMarks.getGrade());
            creditsText.setText(String.valueOf(subjectWithMarks.getCredits()));
            gpa.setText(String.valueOf(subjectWithMarks.getGPA()));
            cgpa.setText(String.valueOf(subjectWithMarks.getCGPA()));

            return myView;
        }
    }
}
