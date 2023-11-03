package com.example.slcm.Faculty;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MenuItem;

import com.example.slcm.FacultyLogin;
import com.example.slcm.R;

import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatActivity;

public class FacultyMenuHandler extends AppCompatActivity {
    public static void handleMenuAction(MenuItem item, Context context) {
        int id = item.getItemId();

        if (id == R.id.fac_menu_profile) {
            openActivity(context, FacultyProfileDashboard.class);
        }
        else if (id == R.id.fac_menu_chat) {
            openActivity(context, FacultyChatViewStudent.class);
        }
        else if (id == R.id.fac_menu_create_announcements) {
            openActivity(context, FacultyCreateAnnouncement.class);
        } else if (id == R.id.fac_menu_view_announcements) {
            openActivity(context, FacultyAnnouncements.class);
        } else if (id == R.id.fac_menu_timetable) {
            openActivity(context, FacultyTimetable.class);
        } else if (id == R.id.fac_menu_attendance) {
            openActivity(context, FacultyAttendanceChooseDate.class);
        } else if (id == R.id.fac_menu_internal_marks) {
            openActivity(context, FacultyMarksClass.class);
        } else if (id == R.id.fac_menu_logout) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("login_state", Context.MODE_PRIVATE);
            sharedPreferences.edit().putString("LOGIN_USER", "").apply();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("Logout");
            alertDialogBuilder.setMessage("Are you sure you want to logout?");
            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    openActivity(context, FacultyLogin.class);
                    dialog.dismiss();
                }
            });
            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    private static void openActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }
}

