package com.example.slcm.Faculty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.slcm.R;

public class CustomListAdapter extends BaseAdapter {
    private Context context;
    private String[] rollNumbers;

    private boolean[] itemStates; // Track the state of each item

    public CustomListAdapter(Context context, String[] rollNumbers) {
        this.context = context;
        this.rollNumbers = rollNumbers;
        this.itemStates = new boolean[rollNumbers.length]; // Initialize all items as not selected
    }

    @Override
    public int getCount() {
        return rollNumbers.length;
    }

    @Override
    public Object getItem(int position) {
        return rollNumbers[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_list_item, parent, false);
        }

        TextView textViewRollNumber = convertView.findViewById(R.id.textViewRollNumber);

        // Set the text based on the data
        textViewRollNumber.setText(rollNumbers[position]);

        // Set the background color based on the item's state
        if (itemStates[position]) {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.red));
        } else {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.green));
        }

        // Toggle the item's state when clicked
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemStates[position] = !itemStates[position];
                notifyDataSetChanged(); // Refresh the list to update the view
            }
        });

        return convertView;
    }
}