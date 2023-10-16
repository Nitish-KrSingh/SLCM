package com.example.slcm.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.slcm.R;

import java.util.ArrayList;
import java.util.Objects;

public class StudentFeesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_fees);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Fees");
        ArrayList userList=getListData();
        final ListView lv=(ListView) findViewById(R.id.listview1);
        lv.setAdapter(new StudentFees_CustomListAdapter(this,userList));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StudentFees_ListItem user=(StudentFees_ListItem) lv.getItemAtPosition(position);
                // Toast.makeText(fees.this, "Selected :"+" "+user.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ArrayList getListData() {
        ArrayList<StudentFees_ListItem>results= new ArrayList<>();
        StudentFees_ListItem user1=new StudentFees_ListItem();
        user1.setName(("Course Fee"));
        user1.setDesignation("Rs: 186,000");
        user1.setLocation("Paid Date -01-12-2023");
        results.add(user1);
        StudentFees_ListItem user2=new StudentFees_ListItem();
        user2.setName(("Hostel Fee"));
        user2.setDesignation("Rs: 96,000");
        user2.setLocation("Paid Date -01-12-2023");
        results.add(user2);
        StudentFees_ListItem user3=new StudentFees_ListItem();
        user3.setName(("Course Fee"));
        user3.setDesignation("Rs: 196,000");
        user3.setLocation("Paid Date -01-11-2023");
        results.add(user3);
        StudentFees_ListItem user4=new StudentFees_ListItem();
        user4.setName(("Hostel Fee"));
        user4.setDesignation("Rs: 90,000");
        user4.setLocation("Paid Date -01-11-2023");
        results.add(user4);
        return results;
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
}