package com.assignments.acadgild.android_project_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.assignments.acadgild.android_project_1.adapter.CustomAdapter;
import com.assignments.acadgild.android_project_1.dbhelper.DBHelper;
import com.assignments.acadgild.android_project_1.model.ToDo;

import java.util.ArrayList;

public class Completed extends AppCompatActivity {
    ListView completedListView;
    DBHelper dbHelper;
    ArrayList<ToDo> toDoArrayList;
    CustomAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed);

        completedListView = (ListView) findViewById(R.id.completedListView);
        dbHelper = new DBHelper(this);
        toDoArrayList = dbHelper.getAllCompleteToDo();
        adapter = new CustomAdapter(this, toDoArrayList);

        completedListView.setAdapter(adapter);
    }
}
