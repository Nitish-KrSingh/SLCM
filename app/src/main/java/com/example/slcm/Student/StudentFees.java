package com.example.slcm.Student;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.slcm.DatabaseManager;
import com.example.slcm.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class StudentFees extends AppCompatActivity {

    private ArrayList<String> studentFeesList;
    private ArrayAdapter<String> adapter;
    private int studentId;
    private boolean feesRetrieved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_fees);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Fees");

        ListView studentFeesListView = findViewById(R.id.fees_ListView);
        studentFeesList = new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE);
        studentId = sharedPreferences.getInt("STUDENT_ID", -1);

        adapter = new ArrayAdapter<String>(this, R.layout.activity_student_fees_list_item, R.id.FeeType, studentFeesList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.activity_student_fees_list_item, parent, false);
                }

                TextView feeTypeTextView = convertView.findViewById(R.id.FeeType);
                TextView feeAmountTextView = convertView.findViewById(R.id.FeeAmount);
                TextView feePaidDateTextView = convertView.findViewById(R.id.FeePaidDate);

                String feeItem = studentFeesList.get(position);
                String[] components = feeItem.split("\n");

                if (components.length >= 1) {
                    feeTypeTextView.setText(components[0]);
                }
                if (components.length >= 2) {
                    feeAmountTextView.setText("₹" + components[1]);
                }
                if (components.length >= 3) {
                    feePaidDateTextView.setText("Paid on " + components[2]);
                }

                return convertView;
            }

        };

        studentFeesListView.setAdapter(adapter);
        studentFeesListView.setAdapter(adapter);

        if (!feesRetrieved) {
            retrieveFeesForStudent();
            feesRetrieved = true; // Set the flag to true once data is retrieved
        }
        FloatingActionButton fabMessage = findViewById(R.id.fabMessage);
        fabMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event to open the message activity.
                Intent intent = new Intent(StudentFees.this, StudentChatViewFaculty.class);
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

    private void retrieveFeesForStudent() {
        DatabaseManager databaseManager = new DatabaseManager(this);
        Cursor cursor = databaseManager.getFees(studentId);

        if (cursor != null) {
            int feeTypeIndex = cursor.getColumnIndex("Purpose");
            int feeAmountIndex = cursor.getColumnIndex("Amount");
            int feePaidDateIndex = cursor.getColumnIndex("DatePaid");

            if (feeTypeIndex == -1 || feeAmountIndex == -1 || feePaidDateIndex == -1) {
                Toast.makeText(this, "One or more columns not found in cursor", Toast.LENGTH_SHORT).show();
            } else {
                while (cursor.moveToNext()) {
                    String feeType = cursor.getString(feeTypeIndex);
                    double feeAmount = cursor.getDouble(feeAmountIndex);
                    String feePaidDate = cursor.getString(feePaidDateIndex);

                    String feeItem = feeType + "\n" + feeAmount + "\n" + feePaidDate;
                    studentFeesList.add(feeItem);
                }

                cursor.close();
            }
        } else {
            Toast.makeText(this, "Cursor is null.", Toast.LENGTH_SHORT).show();
        }

        // Notify the adapter that the data set has changed
        adapter.notifyDataSetChanged();
    }

}
