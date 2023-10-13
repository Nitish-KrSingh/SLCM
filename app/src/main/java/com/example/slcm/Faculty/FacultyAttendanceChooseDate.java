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
    public TextView selectedDateTextView , selecteddayTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_attendance_choose_date);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Attendance - Select Date");

        Button sbutton_attendaance_date = findViewById(R.id.sbutton_date_for_attendance);
        Button attendaance_date_next = findViewById(R.id.sbutton_date_for_attendance);
        selecteddayTextView = findViewById(R.id.selected_day_for_attendance_textview);
        selectedDateTextView = findViewById(R.id.selected_date_for_attendance_textview);
        datePicker = findViewById(R.id.Fac_Att_DatePicker);
        sbutton_attendaance_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();

                String formattedDate = String.format("%02d-%02d-%04d", day, month, year);

                String dayOfWeek = getDayOfWeek(day, month, year);
                selecteddayTextView.setText(dayOfWeek);

                selectedDateTextView.setText("Selected Date: " + dayOfWeek + ", " + formattedDate);
                Intent intent = new Intent(FacultyAttendanceChooseDate.this , FacultyAttendanceClass.class);
                intent.putExtra("selectedDate", formattedDate);
                startActivity(intent);
            }
        });
    }

    private String getDayOfWeek(int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        String[] dayNames = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayNames[dayOfWeek - 1];
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