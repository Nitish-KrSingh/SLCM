package com.example.slcm.Faculty;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.slcm.DatabaseManager;
import com.example.slcm.R;
import com.example.slcm.StudentChat;

import java.util.ArrayList;
import java.util.Objects;

public class FacultyChatViewStudent extends AppCompatActivity {
    private ArrayList<String> studentList;
    private ArrayAdapter<String> adapter;
    private int facultyId;
    private Cursor cursor;
    DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Your Messages");
        SharedPreferences sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE);
        facultyId = sharedPreferences.getInt("FACULTY_ID", -1);
        ListView studentListView = findViewById(R.id.chat_ListView);
        studentList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.activity_student_chat_faculty_list_item, R.id.textViewFacultyName, studentList){
       @Override
            public View getView(int position, View convertView, ViewGroup parent) {
           View view = super.getView(position, convertView, parent);
                String studentItem = getItem(position);
                String[] parts = studentItem.split(" \\(");
                if (parts.length >= 2) {
                    TextView textView = view.findViewById(R.id.textViewFacultyName);
                    int unreadMessageCount = Integer.parseInt(parts[1].replaceAll("[^0-9]", ""));
                    if (unreadMessageCount > 0) {
                        textView.setBackgroundColor(getResources().getColor(R.color.red));
                    }
                    else{
                        textView.setBackgroundColor(getResources().getColor(R.color.light_purple));
                    }
                }

                return view;
            }
        };
        studentListView.setAdapter(adapter);
        retrieveStudentsWhoMessagedFaculty();
        studentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int selectedStudentId = getStudIdFromCursor(position);
                String selectedStudentName = getStudNameFromCursor(position);
                String fname= getFacNameFromCursor(position);
                databaseManager.markMessagesAsRead(facultyId, selectedStudentId);
                Log.d("DebugTag", "Student Name"+selectedStudentName+selectedStudentId);
                Intent intent = new Intent(FacultyChatViewStudent.this, StudentChat.class);
                intent.putExtra("StudName", selectedStudentName);
                intent.putExtra("FacName", fname);
                intent.putExtra("StudId", selectedStudentId);
                intent.putExtra("UserType", "Faculty");
                startActivity(intent);
            }
            private int getStudIdFromCursor(int position) {
                if (cursor != null && cursor.moveToPosition(position)) {
                    int studIDIndex = cursor.getColumnIndex("StudentID");
                    if (studIDIndex != -1) {
                        return cursor.getInt(studIDIndex);
                    }
                }
                return -1;
            }

            private String getStudNameFromCursor(int position) {
                if (cursor != null && cursor.moveToPosition(position)) {
                    int studIndex = cursor.getColumnIndex("StudentName");
                    if (studIndex != -1) {
                        return cursor.getString(studIndex);
                    }
                }
                return null;
            }
            private String getFacNameFromCursor(int position) {
                if (cursor != null && cursor.moveToPosition(position)) {
                    int facIndex = cursor.getColumnIndex("FacultyName");
                    if (facIndex != -1) {
                        return cursor.getString(facIndex);
                    }
                }
                return null;
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

    private void retrieveStudentsWhoMessagedFaculty() {
        databaseManager = new DatabaseManager(this);
        cursor = databaseManager.getStudentsWhoMessagedFaculty(facultyId);
        if (cursor != null && cursor.moveToFirst()) {
            int studentNameIndex = cursor.getColumnIndex("StudentName");
            int studentIDIndex = cursor.getColumnIndex("StudentID");
            int FacnameIndex = cursor.getColumnIndex("FacultyName");

            if (studentIDIndex==-1||studentNameIndex == -1 ) {
                Log.e("CursorError", "StudentName column not found in cursor");
            } else {
                boolean noMessagesFound = true;
                do {
                    String studentName = cursor.getString(studentNameIndex);
                    int studentID = cursor.getInt(studentIDIndex);
                    String FacName = cursor.getString(FacnameIndex);
                    int unreadMessageCount = databaseManager.getUnreadMessageCountForStudent(studentID, facultyId);
                    studentList.add(studentName + " (" + unreadMessageCount + " unread)");
                    Log.d("DebugTag", "Added student: " + studentName);
                    Log.d("DebugTag", "Fac id: " + facultyId);
                    Log.d("DebugTag", "Unread " + unreadMessageCount);
                    if(unreadMessageCount>0){noMessagesFound = false;}
                    adapter.notifyDataSetChanged();
                } while (cursor.moveToNext());
                if (noMessagesFound) {
                    Toast.makeText(this, "No New Messages.", Toast.LENGTH_SHORT).show();
                }
            }
            adapter.notifyDataSetChanged();
        }
        else {
            Toast.makeText(this, "No Messages found.", Toast.LENGTH_SHORT).show();
            Log.d("DebugTag", "Cursor is null.");
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the cursor when the activity is destroyed
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }
}
