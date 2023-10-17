package com.example.slcm.Student;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MenuItem;

import com.example.slcm.R;
import com.example.slcm.StudentLogin;

public class StudentMenuHandler {
    public static void handleMenuAction(MenuItem item, Context context) {
        int id = item.getItemId();

        if (id == R.id.menu_profile) {
            openActivity(context, StudentProfileDashboard.class);
        } else if (id == R.id.menu_announcements) {
            openActivity(context, StudentAnnouncements.class);
        } else if (id == R.id.menu_timetable) {
            openActivity(context, StudentTimetable.class);
        } else if (id == R.id.menu_attendance) {
            openActivity(context, StudentAttendance.class);
        } else if (id == R.id.menu_internal_marks) {
            openActivity(context, StudentInternalMarks.class);
        } else if (id == R.id.menu_marksheet) {
            openActivity(context, StudentMarksheet.class);
        } else if (id == R.id.menu_fees) {
            openActivity(context, StudentFees.class);
        } else if (id == R.id.menu_logout) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("login_state", Context.MODE_PRIVATE);
            sharedPreferences.edit().putString("LOGIN_USER", "").apply();
            openActivity(context, StudentLogin.class);

        }
    }


    private static void openActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }
}
