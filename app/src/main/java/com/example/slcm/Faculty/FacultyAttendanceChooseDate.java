package com.example.slcm.Faculty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.slcm.R;

import java.util.Calendar;
import java.util.Objects;

public class FacultyAttendanceChooseDate extends AppCompatActivity {
    private DatePicker datePicker;
    public TextView selectedDateTextView ;

    public  Button next_page_selectclass;
    String formattedDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_attendance_choose_date);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Attendance - Select Date");

        selectedDateTextView = (TextView) findViewById(R.id.selected_date_for_attendance_textview);
        datePicker = findViewById(R.id.Fac_Att_DatePicker);
        next_page_selectclass = findViewById(R.id.sbutton_date_for_attendance);
        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int years = datePicker.getYear();

                selectedDateTextView.setText(calendar.getTime().toString());
                formattedDate = String.format("%02d-%02d-%04d", day, month, years);
            }
        });


        next_page_selectclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FacultyAttendanceChooseDate.this, FacultyAttendanceClass.class);
                intent.putExtra("formattedDate", formattedDate);
                startActivity(intent);
            }
        });

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