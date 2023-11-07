package com.example.slcm.Faculty;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.slcm.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class FacultyMarksType extends AppCompatActivity {
    private final Calendar selectedDate = Calendar.getInstance();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private RadioGroup assessmentTypes;
    private TextView maxMarks;
    private TextView marksDate;
    private String selectedAssignmentType = "Assignment1";
    private int marks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_marks_type);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Marks - Select Assessment Type");

        assessmentTypes = findViewById(R.id.assessRadioGroup);
        maxMarks = findViewById(R.id.maxMarksText);
        maxMarks.setText("");
        ImageButton marksCalendarButton = findViewById(R.id.marksCalendarButton);
        int selectedClass = getIntent().getIntExtra("SELECTED_CLASS", -1);
        String selectedSection = getIntent().getStringExtra("SELECTED_SECTION");
        String selectedClassName = getIntent().getStringExtra("SELECTED_CLASSNAME");
        int facultyId = getIntent().getIntExtra("FACULTY_ID", -1);
        TextView details = findViewById(R.id.pagedetails);
        String prevdet="Selected Class: "+selectedClassName+"-"+selectedSection;
        details.setText(prevdet);
        marksDate = findViewById(R.id.marksDateTextView);
        updateDate(selectedDate);
        marksCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        Button nextButton = findViewById(R.id.nextBtn);
        setMaxMarksForAssessmentType(selectedAssignmentType);
        assessmentTypes.check(R.id.internalAssignment1);
        assessmentTypes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = checkedId;
                if (id == R.id.midTerm) {
                    selectedAssignmentType = "Midterm";
                } else if (id == R.id.internalAssignment1) {
                    selectedAssignmentType = "Assignment1";
                } else if (id == R.id.internalAssignment2) {
                    selectedAssignmentType = "Assignment2";
                } else if (id == R.id.internalAssignment3) {
                    selectedAssignmentType = "Assignment3";
                } else if (id == R.id.internalAssignment4) {
                    selectedAssignmentType = "Assignment4";
                }
                setMaxMarksForAssessmentType(selectedAssignmentType);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {
                    Intent intent = new Intent(FacultyMarksType.this, FacultyMarksSubject.class);
                    intent.putExtra("SELECTED_CLASS", selectedClass);
                    intent.putExtra("SELECTED_SECTION", selectedSection);
                    intent.putExtra("SELECTED_CLASSNAME", selectedClassName);
                    String formattedDate = dateFormat.format(selectedDate.getTime());
                    intent.putExtra("SELECTED_DATE", formattedDate);
                    intent.putExtra("MAX_MARKS", marks);
                    intent.putExtra("ASSIGNMENT_TYPE", selectedAssignmentType);
                    intent.putExtra("FACULTY_ID", facultyId);
                    Log.d("DateGet", "Retrieving subjects for faculty: " + facultyId + "class" + selectedClass + "section" + selectedSection + "date" + formattedDate + "ass type" + selectedAssignmentType);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                selectedDate.set(Calendar.YEAR, year);
                selectedDate.set(Calendar.MONTH, month);
                selectedDate.set(Calendar.DAY_OF_MONTH, day);
                updateDate(selectedDate);
            }
        },
                selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    private void updateDate(Calendar calendar) {
        String formattedDate = dateFormat.format(calendar.getTime());
        marksDate.setText(formattedDate);
    }

    private void setMaxMarksForAssessmentType(String assessmentType) {
        marks = 5;
        if ("Midterm".equals(assessmentType)) {
            marks = 30;
            maxMarks.setText(String.format("%d", marks));
        } else {
            maxMarks.setText(String.format("%d", marks));
        }

    }

    private boolean validateInput() {
        int selectedRadioButtonId = assessmentTypes.getCheckedRadioButtonId();
        if (selectedRadioButtonId == -1) {
            showErrorDialog("Please fill in all fields.");
            return false;
        }
        return true;
    }

    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.faculty_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FacultyMenuHandler.handleMenuAction(item, this);
        return super.onOptionsItemSelected(item);
    }
}
