package com.example.slcm;

import android.content.Context;
<<<<<<< HEAD
import android.content.Intent;
=======
>>>>>>> d398dcf (Completed integration with some additions)
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.slcm.ChatAdapter;
import com.example.slcm.DatabaseManager;
<<<<<<< HEAD
import com.example.slcm.Faculty.FacultyChatViewStudent;
import com.example.slcm.R;
import com.example.slcm.Student.StudentChatViewFaculty;
=======
import com.example.slcm.R;
>>>>>>> d398dcf (Completed integration with some additions)
import com.example.slcm.Student.StudentMenuHandler;

import java.util.Objects;

public class StudentChat extends AppCompatActivity {
    private int senderId;
    private int receiverId;
    private ChatAdapter chatAdapter;
    private EditText messageEditText;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_chat);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ListView chatListView = findViewById(R.id.chatListView);
        messageEditText = findViewById(R.id.messageEditText);
        SharedPreferences sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE);

     String senderName = null, receiverName = null;
        String storedUserType = sharedPreferences.getString("LOGIN_TYPE", "");
        if(storedUserType.equals("Faculty")) {
            receiverName= getIntent().getStringExtra("StudName");
            senderName= getIntent().getStringExtra("FacName");
            senderId = sharedPreferences.getInt("FACULTY_ID", -1);
            getSupportActionBar().setTitle("Chatting with " + receiverName);
            receiverId= getIntent().getIntExtra("StudId", -1);
        }
        else if (storedUserType.equals("Student")) {
            senderName= getIntent().getStringExtra("StudName");
            receiverName= getIntent().getStringExtra("FacName");
            senderId = sharedPreferences.getInt("STUDENT_ID", -1);
            getSupportActionBar().setTitle("Chatting with " + receiverName);
            receiverId= getIntent().getIntExtra("FacId", -1);
        }
        databaseManager = new DatabaseManager(this);
        Log.d("sender", "Name: " + receiverName + senderName);
        if(storedUserType.equals("Faculty")) {
            chatAdapter = new ChatAdapter(this, null, senderName, receiverName);
        }
        else{
            chatAdapter = new ChatAdapter(this, null,  receiverName, senderName);
        }
        chatListView.setAdapter(chatAdapter);

        retrieveAndDisplayChatMessages();
        findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageEditText.getText().toString().trim();
                if (!messageText.isEmpty()) {
                    databaseManager.insertChatMessage(senderId, receiverId, messageText, storedUserType);
                    messageEditText.setText("");
                    retrieveAndDisplayChatMessages();
                }
            }
        });

    }
<<<<<<< HEAD




=======
>>>>>>> d398dcf (Completed integration with some additions)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.student_menu, menu);
        return true;
    }
<<<<<<< HEAD
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {// Handle the Up button click here
            onBackPressed(); // This will mimic the default back button behavior
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String userType = getIntent().getStringExtra("UserType");
        Log.d("Back", "Going back");
        if ("Faculty".equals(userType)) {
            Intent intent = new Intent(this, FacultyChatViewStudent.class);
            startActivity(intent);
        } else if ("Student".equals(userType)) {
            Intent intent = new Intent(this, StudentChatViewFaculty.class);
            startActivity(intent);
        }
        finish();
    }
=======

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        StudentMenuHandler.handleMenuAction(item, this);
        return super.onOptionsItemSelected(item);
    }
>>>>>>> d398dcf (Completed integration with some additions)

    private void retrieveAndDisplayChatMessages() {
        Cursor cursor = databaseManager.getChatMessages(senderId, receiverId);
        chatAdapter.swapCursor(cursor);
        chatAdapter.notifyDataSetChanged();
    }
}
