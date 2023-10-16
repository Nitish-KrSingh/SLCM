package com.example.slcm.Student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.slcm.R;

import java.util.ArrayList;

public class StudentFees_CustomListAdapter extends BaseAdapter {
    private ArrayList<StudentFees_ListItem>listdata;
    private LayoutInflater layoutInflater;
    public StudentFees_CustomListAdapter(Context aContext, ArrayList<StudentFees_ListItem> listdata) {
        this.listdata=listdata;
        layoutInflater=LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listdata.size();
    }

    @Override
    public Object getItem(int position) {
        return listdata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    static  class ViewHolder{
        TextView uCourse_fees;
        TextView uRupees;
        TextView uDate;

    }

    @Override
    public View getView(int position, View v, ViewGroup vg) {
        ViewHolder holder;

        if (v==null){
            v=layoutInflater.inflate(R.layout.studentfeelist_design_row, null);
            holder=new ViewHolder();
            holder.uCourse_fees=(TextView) v.findViewById(R.id.course_fees);
            holder.uRupees=(TextView) v.findViewById(R.id.rupees);
            holder.uDate=(TextView) v.findViewById(R.id.date);
            v.setTag(holder);
        }else{
            holder=(ViewHolder) v.getTag();
        }
        holder.uCourse_fees.setText(listdata.get(position).getName());
        holder.uRupees.setText(listdata.get(position).getDesignation());
        holder.uDate.setText(listdata.get(position).getLocation());
        return v;
    }
}
