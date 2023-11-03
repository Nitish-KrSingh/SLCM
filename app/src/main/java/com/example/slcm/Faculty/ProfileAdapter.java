package com.example.slcm.Faculty;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.slcm.R;

public class ProfileAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final Cursor cursor;

    public ProfileAdapter(Context context, Cursor cursor) {
        super(context, R.layout.activity_faculty_profile_list_item);
        this.context = context;
        this.cursor = cursor;
    }

    @Override
    public int getCount() {
        return cursor.getColumnCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_faculty_profile_list_item, parent, false);
        }

        String columnName = cursor.getColumnName(position);

        if (!cursor.moveToFirst()) {
            return convertView;
        }

        String value = cursor.getString(cursor.getColumnIndexOrThrow(columnName));
        TextView labelTextView = convertView.findViewById(R.id.labelTextView);
        TextView colonTextView = convertView.findViewById(R.id.colon);
        TextView valueTextView = convertView.findViewById(R.id.valueTextView);
        if(columnName.equals("DOB"))
        {
            columnName="Date of Birth";
        }
        if(columnName.equals("PhoneNumber"))
        {
            columnName="Phone Number";
        }
        if(columnName.equals("AcademicRole"))
        {
            columnName="Academic Role";
        }
        if(columnName.equals("AreasOfInterest"))
        {
            columnName="Areas of Interest";
        }
        if(columnName.equals("RegistrationNumber"))
        {
            columnName="Registration Number";
        }
        labelTextView.setText(columnName);
        colonTextView.setText("   :   ");
        valueTextView.setText(value);

        return convertView;
    }

}
