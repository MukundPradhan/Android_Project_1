package com.assignments.acadgild.android_project_1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.assignments.acadgild.android_project_1.dbhelper.DBHelper;

import java.util.Calendar;

public class Add_Todo_Activity extends AppCompatActivity {
   /* EditText txtTitle, txtDesc;
    DatePicker datePicker;
    Button aBtnSave, aBtnCancel;
    DBHelper dbHelper;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__todo);

    }
}
