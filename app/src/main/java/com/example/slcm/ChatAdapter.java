package com.example.slcm;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChatAdapter extends CursorAdapter {
    String sendername=null, receivername=null;
    public ChatAdapter(Context context, Cursor cursor, String sendername, String receivername) {
        super(context, cursor, 0);
        this.sendername=sendername;
        this.receivername=receivername;


    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.activity_student_chat_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textViewMessage = view.findViewById(R.id.textViewMessage);
        TextView textViewTimestamp = view.findViewById(R.id.textViewTimestamp);
        TextView textViewUser = view.findViewById(R.id.textViewUser);
        LinearLayout msglayout = view.findViewById(R.id.msglayout);

        int messageTextIndex = cursor.getColumnIndex("MessageText");
        int timestampIndex = cursor.getColumnIndex("Timestamp");
        int SenderIndex = cursor.getColumnIndex("SenderType");
        if(messageTextIndex==-1||timestampIndex==-1||SenderIndex==-1)
        {
            Log.e("CursorError", "One or more columns not found in cursor");
            Toast.makeText(context.getApplicationContext(), "One or more columns not found in cursor", Toast.LENGTH_SHORT).show();
        }
        else {
            String messageText = cursor.getString(messageTextIndex);
            String timestamp = cursor.getString(timestampIndex);
            String sender= cursor.getString(SenderIndex);
            textViewMessage.setText(messageText);
            textViewTimestamp.setText(timestamp);
            Log.d("ClassGet", "Name: " + sendername);
            if(sender.equals("Faculty")) {
                msglayout.setBackgroundResource(R.color.light_purple);
                textViewUser.setText(sendername);
            }
            else {
                msglayout.setBackgroundResource(R.color.light_gray);
                textViewUser.setText(receivername);

            }
        }
    }
}

