package com.assignments.acadgild.android_project_1;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.assignments.acadgild.android_project_1.adapter.CustomAdapter;
import com.assignments.acadgild.android_project_1.dbhelper.DBHelper;
import com.assignments.acadgild.android_project_1.model.ToDo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener, View.OnClickListener, View.OnFocusChangeListener {


    ListView listView;
    CustomAdapter customAdapter;
    DBHelper dbHelper;
    ArrayList<ToDo> toDoArrayList;
    LayoutInflater inflater;


    AlertDialog.Builder alertDialogBuilder, popUpDialogBuilder;
    AlertDialog alertDialog, popUpDialog;
    View alertDialogView, popUpDialogView;

    //Variable Declaration for Alert Dialog Box (ADD) & popUp
    EditText aTitle, pTitle;
    EditText aDesc, pDesc;
    EditText pDate;
    RadioGroup radioGroup, pRadioGroup;
    RadioButton aRadioComplete, pRadioComplete;
    RadioButton aRadioInComplete, pRadioInComplete;
    DatePicker datePicker;
    Button aBtnSave, pBtnUpdate;
    Button aBtnCancel, pBtnCancel;
    int pID, pStatus = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(getApplicationContext());

        listView = (ListView) findViewById(R.id.listViewToDo);

        UpdateListView();

        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
    }

    public void UpdateListView() {
        toDoArrayList = dbHelper.getAllToDoList();
        customAdapter = new CustomAdapter(getApplicationContext(), toDoArrayList);
        listView.setAdapter(customAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.mnuCreate) {
            alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialog = alertDialogBuilder.create();
            inflater = LayoutInflater.from(this);
            alertDialogView = inflater.inflate(R.layout.activity_add__todo, null);

            aTitle = (EditText) alertDialogView.findViewById(R.id.etTitle);
            aDesc = (EditText) alertDialogView.findViewById(R.id.etDesc);
            radioGroup = (RadioGroup) alertDialogView.findViewById(R.id.radioGroup);
            aRadioComplete = (RadioButton) alertDialogView.findViewById(R.id.radioComplete);
            aRadioInComplete = (RadioButton) alertDialogView.findViewById(R.id.radioInComplete);
            datePicker = (DatePicker) alertDialogView.findViewById(R.id.datePicker);
            aBtnSave = (Button) alertDialogView.findViewById(R.id.btnSave);
            aBtnCancel = (Button) alertDialogView.findViewById(R.id.btnCancel);

            radioGroup.setVisibility(View.GONE);
            aBtnSave.setOnClickListener(this);
            aBtnCancel.setOnClickListener(this);

            aTitle.setOnFocusChangeListener(this);
            aDesc.setOnFocusChangeListener(this);

            alertDialog.setView(alertDialogView);
            alertDialog.show();

            //     Toast.makeText(MainActivity.this, "flag : " + flag, Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.mnuComplete) {
            Intent intent = new Intent(MainActivity.this, Completed.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        popUpDialogBuilder = new AlertDialog.Builder(this);
        popUpDialog = popUpDialogBuilder.create();
        inflater = LayoutInflater.from(this);
        popUpDialogView = inflater.inflate(R.layout.activity_update_todo, null);


        pTitle = (EditText) popUpDialogView.findViewById(R.id.etTitle1);
        pDesc = (EditText) popUpDialogView.findViewById(R.id.etDesc1);
        pDate = (EditText) popUpDialogView.findViewById(R.id.etDate1);
        pRadioGroup = (RadioGroup) popUpDialogView.findViewById(R.id.radioGroup1);
        pRadioComplete = (RadioButton) popUpDialogView.findViewById(R.id.radioComplete1);
        pRadioInComplete = (RadioButton) view.findViewById(R.id.radioInComplete1);
        pBtnUpdate = (Button) popUpDialogView.findViewById(R.id.btnUpdate);
        pBtnCancel = (Button) popUpDialogView.findViewById(R.id.btnCancel1);

        pID = toDoArrayList.get(position).getId();
        pTitle.setText(toDoArrayList.get(position).getTitle());
        pDesc.setText(toDoArrayList.get(position).getDescription());
        pDate.setText(toDoArrayList.get(position).getDate());
        pStatus = toDoArrayList.get(position).getStatus();

        // retrieve the status form dab & update to insert to db
        if (pStatus == 0) {
            pRadioGroup.check(R.id.radioInComplete1);
        } else if (pStatus == 1) {
            pRadioGroup.check(R.id.radioComplete1);
        }

        pBtnUpdate.setOnClickListener(this);
        pBtnCancel.setOnClickListener(this);

        popUpDialog.setView(popUpDialogView);
        popUpDialog.show();

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        String task;
        int statusValue;
        statusValue = toDoArrayList.get(position).getStatus();
        if (statusValue == 1) {
            statusValue = 0;
            task = "Uncompleted";
        } else {
            statusValue = 1;
            task = "Completed";
        }

        dbHelper.UpdateStatus(toDoArrayList.get(position).getId(), statusValue);
        UpdateListView();
        Toast.makeText(MainActivity.this, "Your to-do task is updated as : " + task, Toast.LENGTH_SHORT).show();

        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnSave:   // Save Button of Alert Dialog Box

                String selectDate, mDay, mMonth, mYear;

                if (datePicker.getDayOfMonth() <= 9) {
                    mDay = "0" + String.valueOf(datePicker.getDayOfMonth());
                } else {
                    mDay = String.valueOf(datePicker.getDayOfMonth());
                }

                if (datePicker.getMonth() < 9) {
                    mMonth = "0" + String.valueOf(datePicker.getMonth() + 1);
                } else {
                    mMonth = "" + String.valueOf(datePicker.getMonth() + 1);
                }
                mYear = "" + datePicker.getYear();

                selectDate = mDay + "/" + mMonth + "/" + mYear;

                dbHelper.insertToDo(aTitle.getText().toString(), aDesc.getText().toString(), selectDate, 0);
                aTitle.setText("");
                aDesc.setText("");
                aTitle.requestFocus();

                alertDialog.dismiss();
                UpdateListView();
                break;

            case R.id.btnUpdate:


                if (pRadioGroup.getCheckedRadioButtonId() == R.id.radioComplete1) {
                    pStatus = 1;
                } else if (pRadioGroup.getCheckedRadioButtonId() == R.id.radioInComplete1) {
                    pStatus = 0;
                }

                dbHelper.UpdateToDo(pID, pTitle.getText().toString(), pDesc.getText().toString(),
                        pDate.getText().toString(), pStatus);

                Toast.makeText(this, "Data Updated Succefully", Toast.LENGTH_SHORT).show();
                UpdateListView();
                popUpDialog.dismiss();

                break;

            case R.id.btnCancel:   // cancel Button of Dialog Box

                alertDialog.dismiss();
                Toast.makeText(MainActivity.this, "You Cancel the Dialog Box ", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnCancel1:   // cancel Button of Dialog Box

                popUpDialog.dismiss();
                Toast.makeText(MainActivity.this, "You Cancel the Dialog Box ", Toast.LENGTH_SHORT).show();
                break;
        }


    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (view.getId() == R.id.etTitle) {
            if (hasFocus) {
                if (aTitle.getText().toString().trim().length() < 1) {
                    aTitle.setError("Minimum 1 Char");
                }
            }
        } else if (view.getId() == R.id.etDesc) {
            if (hasFocus) {
                if (aDesc.getText().toString().trim().length() < 1) {
                    aDesc.setError("Minimun 1 Char");
                }
            }
        }
    }
}





