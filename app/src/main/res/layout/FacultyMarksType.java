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
    private TextView marksDate,intentheading;
    private String selectedAssignmentType = "Assignment1"; // Default assessment type

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_marks_type);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Marks - Select Assessment Type");

        assessmentTypes = findViewById(R.id.assessRadioGroup);
        maxMarks = findViewById(R.id.maxMarksText);
        intentheading = findViewById(R.id.intentheading);
        Intent i=getIntent();
        String classname=i.getStringExtra("selected_class");
        String section=i.getStringExtra("SELECTED_SECTION");
        intentheading.setText("Selecting: "+classname+"-"+section);

        maxMarks.setText("");
        ImageButton marksCalendarButton = findViewById(R.id.marksCalendarButton);
        int selectedClass = getIntent().getIntExtra("SELECTED_CLASS", -1);
        String selectedSection = getIntent().getStringExtra("SELECTED_SECTION");
        int facultyId = getIntent().getIntExtra("FACULTY_ID", -1);
        marksDate = findViewById(R.id.marksDateTextView);
        updateDate(selectedDate);
        marksCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        Button nextButton = findViewById(R.id.nextBtn);

        // Set an initial value based on the default assessment type (Midterm in this case)
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

                // Update the max marks TextView when the assessment type changes
                setMaxMarksForAssessmentType(selectedAssignmentType);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {
                    Intent intent = new Intent(FacultyMarksType.this, FacultyMarksSubject.class);
                    //intent.putExtra("SELECTED_CLASS", selectedClass);
                    intent.putExtra("SELECTED_CLASS", selectedClass);
                    intent.putExtra("SELECTED_SECTION", selectedSection);
                    String formattedDate = dateFormat.format(selectedDate.getTime());
                    intent.putExtra("SELECTED_DATE", formattedDate);
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
        int marks = 5;
        if ("Midterm".equals(assessmentType)) {
            marks = 30;
            maxMarks.setText(String.format("%d", marks));
        } else {
            marks = 5;
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
