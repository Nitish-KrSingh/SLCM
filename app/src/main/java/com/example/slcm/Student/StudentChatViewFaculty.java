package com.example.slcm.Student;

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

public class StudentChatViewFaculty extends AppCompatActivity {
    private ArrayList<String> facultyList;
    private ArrayAdapter<String> adapter;
    private int studentId;
    private String studName;
    private Cursor cursor2, cursor;
    DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Your Messages");
        SharedPreferences sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE);
        studentId = sharedPreferences.getInt("STUDENT_ID", -1);
        ListView faculty_ListView = findViewById(R.id.chat_ListView);
        facultyList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.activity_student_chat_faculty_list_item, R.id.textViewFacultyName, facultyList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                String facultyItem = getItem(position);
                String[] parts = facultyItem.split(" \\(");
                if (parts.length >= 2) {
                    TextView textView = view.findViewById(R.id.textViewFacultyName);
                    if (textView != null) {
                        int unreadMessageCount = Integer.parseInt(parts[1].replaceAll("[^0-9]", ""));
                        if (unreadMessageCount > 0) {
                            textView.setBackgroundColor(getResources().getColor(R.color.red));
                        }
                    }
                }

                return view;
            }
        };
        faculty_ListView.setAdapter(adapter);
        retrieveFacultyListForStudent();
        faculty_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int selectedFacultyId = getFacIdFromCursor(position);
                String selectedFacultyName = getFacNameFromCursor(position);
                String sName = getStudNameFromCursor(position);
                databaseManager.markMessagesAsRead(studentId, selectedFacultyId);
                Intent intent = new Intent(StudentChatViewFaculty.this, StudentChat.class);
                intent.putExtra("FacName", selectedFacultyName);
                intent.putExtra("FacId", selectedFacultyId);
                intent.putExtra("StudName", studName);
                intent.putExtra("UserType", "Student");
                startActivity(intent);
            }

            private int getFacIdFromCursor(int position) {
                if (cursor2 != null && cursor2.moveToPosition(position)) {
                    int facIDIndex = cursor2.getColumnIndex("FacultyID");
                    if (facIDIndex != -1) {
                        return cursor2.getInt(facIDIndex);
                    }
                }
                return -1;
            }

            private String getFacNameFromCursor(int position) {
                if (cursor2 != null && cursor2.moveToPosition(position)) {
                    int facIndex = cursor2.getColumnIndex("FacultyName");
                    if (facIndex != -1) {
                        return cursor2.getString(facIndex);
                    }
                }
                return null;
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

    private void retrieveFacultyListForStudent() {
        databaseManager = new DatabaseManager(this);
        Cursor cursor = databaseManager.getStudentClass(studentId);
        if (cursor != null) {
            int classIndex = cursor.getColumnIndex("ClassID");
            int classNameIndex = cursor.getColumnIndex("ClassName");
            int studIndex = cursor.getColumnIndex("StudentName");
            if (classIndex == -1 || classNameIndex == -1) {
                Log.e("CursorError", "One or more columns not found in cursor");
                Toast.makeText(this, "One or more columns not found in cursor", Toast.LENGTH_SHORT).show();
            } else {
                while (cursor.moveToNext()) {
                    String className = cursor.getString(classNameIndex);
                    int classId = cursor.getInt(classIndex);
                    studName = cursor.getString(studIndex);
                    cursor2 = databaseManager.getFacultyList(classId);
                    if (cursor2 != null) {
                        if (cursor2.moveToFirst()) {
                            int facultyIndex = cursor2.getColumnIndex("FacultyName");
                            int facultyIDIndex = cursor2.getColumnIndex("FacultyID");
                            if (facultyIndex != -1 && facultyIDIndex != -1 || studIndex == -1) {
                                do {
                                    String facultyName = cursor2.getString(facultyIndex);
                                    int facultyId = cursor2.getInt(facultyIDIndex);
                                    int unreadMessageCount = databaseManager.getUnreadMessageCountForFaculty(facultyId, studentId);
                                    facultyList.add(facultyName + " (" + unreadMessageCount + " unread)");
                                    Log.d("DebugTag", "Added faculty: " + facultyName + ", ID: " + facultyId);
                                    adapter.notifyDataSetChanged();
                                } while (cursor2.moveToNext());
                            } else {
                                Log.e("CursorError", "One or more columns not found in cursor2");
                                Toast.makeText(this, "One or more columns not found in cursor2", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.d("DebugTag", "Cursor2 is empty.");
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.d("DebugTag", "Inner cursor (cursor2) is null.");
                    }
                }
            }
        } else {
            Log.d("DebugTag", "Outer cursor is null.");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the cursor when the activity is destroyed
        if (cursor2 != null && !cursor2.isClosed()) {
            cursor2.close();
        }
    }
}
