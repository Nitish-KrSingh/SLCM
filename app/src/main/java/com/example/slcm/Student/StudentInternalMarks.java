package com.example.slcm.Student;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.slcm.DatabaseManager;
import com.example.slcm.R;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Objects;

public class StudentInternalMarks extends AppCompatActivity {
    ListView listView;
    int studentId;
    DatabaseManager databaseManager;
    MyAdapter myAdapter;
    Spinner dropdown;
    RomanToNumber converter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_internal_marks);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Internal Marks");
        dropdown = findViewById(R.id.semester_select_spinner);
        listView = findViewById(R.id.listView);
        SharedPreferences sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE);
        studentId = sharedPreferences.getInt("STUDENT_ID", -1);

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
        List<SubjectWithMarks> subjectsList = databaseManager.getSubjectsAndMarksForStudent(studentId, selectedValue);

        myAdapter = new MyAdapter(subjectsList);
        listView.setAdapter(myAdapter);
    }

    private class MyAdapter extends BaseAdapter {
        private List<SubjectWithMarks> subjectsList;

        public MyAdapter(List<SubjectWithMarks> subjectsList) {
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
            View myView = layoutInflater.inflate(R.layout.activity_student_internalmarks_subject_list_item, parent, false);

            TextView subjectTextView = myView.findViewById(R.id.SubjectText);
            RelativeLayout itemClicked = myView.findViewById(R.id.itemClicked);
            ImageView arrowImg = myView.findViewById(R.id.arrowImg);
            LinearLayout discLayout = myView.findViewById(R.id.discLayout);

            SubjectWithMarks subject = subjectsList.get(position);

            subjectTextView.setText(subject.getSubjectName());

            itemClicked.setOnClickListener(v -> {
                if (discLayout.getVisibility() == View.GONE) {
                    TransitionManager.beginDelayedTransition(itemClicked, new AutoTransition());
                    discLayout.setVisibility(View.VISIBLE);
                    arrowImg.setImageResource(R.drawable.ic_up_arrow);

                    displayMarks(discLayout, subject.getAssignment1(), subject.getAssignment2(), subject.getAssignment3(), subject.getAssignment4(), subject.getMidterm());
                } else {
                    TransitionManager.beginDelayedTransition(itemClicked, new AutoTransition());
                    discLayout.setVisibility(View.GONE);
                    arrowImg.setImageResource(R.drawable.ic_down_arrow);
                }
            });

            return myView;
        }

        private void displayMarks(LinearLayout discLayout, Double assignment1, Double assignment2, Double assignment3, Double assignment4, Double midterm) {
            TableLayout tableLayout = discLayout.findViewById(R.id.discLayout);
            addRowToTable(tableLayout, "Assignment 1", assignment1, 5.0); // Total marks for Assignment 1 is 5.0
            addRowToTable(tableLayout, "Assignment 2", assignment2, 5.0); // Total marks for Assignment 2 is 5.0
            addRowToTable(tableLayout, "Assignment 3", assignment3, 5.0); // Total marks for Assignment 3 is 5.0
            addRowToTable(tableLayout, "Assignment 4", assignment4, 5.0); // Total marks for Assignment 4 is 5.0
            addRowToTable(tableLayout, "Midterm", midterm, 30.0); // Total marks for Midterm is 30.0
        }

        private void addRowToTable(TableLayout tableLayout, String assignmentName, Double mark, double totalMarks) {
            if (mark != null) {
                TableRow row = new TableRow(getApplicationContext());

                TextView assignmentTypeTextView = new TextView(getApplicationContext());
                assignmentTypeTextView.setText(assignmentName);
                assignmentTypeTextView.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                ));
                assignmentTypeTextView.setPadding(5, 5, 5, 5);
                assignmentTypeTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                assignmentTypeTextView.setTextSize(18);
                assignmentTypeTextView.setTextColor(getResources().getColor(R.color.blue));

                TextView marksTextView = new TextView(getApplicationContext());
                String formattedMarks = String.format("%.1f/%.1f", mark, totalMarks);
                marksTextView.setText(formattedMarks);
                marksTextView.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                ));
                marksTextView.setPadding(5, 5, 5, 5);
                marksTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                marksTextView.setTextSize(18);
                marksTextView.setTextColor(getResources().getColor(R.color.blue));

                row.addView(assignmentTypeTextView);
                row.addView(marksTextView);

                tableLayout.addView(row);
            }
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
