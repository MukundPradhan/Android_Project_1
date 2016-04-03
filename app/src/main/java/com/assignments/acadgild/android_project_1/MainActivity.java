package com.assignments.acadgild.android_project_1;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.TextView;
import android.widget.Toast;

import com.assignments.acadgild.android_project_1.adapter.CustomAdapter;
import com.assignments.acadgild.android_project_1.dbhelper.DBHelper;
import com.assignments.acadgild.android_project_1.model.ToDo;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    CustomAdapter customAdapter;
    DBHelper dbHelper;
    ArrayList<ToDo> toDoArrayList;
    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayListView(); // update the list view items from table.

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final AlertDialog.Builder popUpDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                        final AlertDialog popUpDialog = popUpDialogBuilder.create();
                        inflater = LayoutInflater.from(MainActivity.this);

                        View view1 = inflater.inflate(R.layout.activity_add__todo, null);

                        final int dbID, dbStatus;
                        //int finalStatus=dbStatus;
                        final EditText popUpTitle = (EditText) view1.findViewById(R.id.etTitle);
                        final EditText popUpDesc = (EditText) view1.findViewById(R.id.etDesc);
                        final EditText popUpDate = (EditText) view1.findViewById(R.id.etDate);
                        final DatePicker dtp = (DatePicker) view1.findViewById(R.id.datePicker);
                        final RadioButton radioComplete = (RadioButton) view1.findViewById(R.id.radioComplete);
                        final RadioButton radioInComplete = (RadioButton) view1.findViewById(R.id.radioInComplete);
                        final Button popUpBtnSave = (Button) view1.findViewById(R.id.btnSave);
                        final Button popUpBtnCancel = (Button) view1.findViewById(R.id.btnCancel);

                        dbID = toDoArrayList.get(position).getId();
                        popUpTitle.setText(toDoArrayList.get(position).getTitle());
                        popUpDesc.setText(toDoArrayList.get(position).getDescription());
                        dtp.setVisibility(View.GONE);
                        popUpDate.setText(toDoArrayList.get(position).getDate());

                        // retrive the status form dab & update to insert to db
                        dbStatus = toDoArrayList.get(position).getStatus();
                        if (dbStatus == 0) {
                            radioInComplete.setChecked(true);
                        } else {
                            radioComplete.setChecked(true);
                        }

                        popUpBtnSave.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        int finalStatus = 0;
                                        if (radioComplete.isChecked()) {
                                            finalStatus = 1;
                                        } else if (radioInComplete.isChecked()) {
                                            finalStatus = 0;
                                        }
                                        // dbStatus=finalStatus;
                                        dbHelper.UpdateToDo(dbID, popUpTitle.getText().toString(), popUpDesc.getText().toString(),
                                                popUpDate.getText().toString(), dbStatus);


                                        DisplayListView();
                                        popUpDialog.dismiss();
                                        Toast.makeText(MainActivity.this, "Data Updated Succefully", Toast.LENGTH_SHORT).show();
                                    }
                                }
                        );

                        popUpBtnCancel.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        popUpDialog.dismiss();
                                        Toast.makeText(MainActivity.this, "You Cancelled the Dialog", Toast.LENGTH_SHORT).show();
                                    }
                                }
                        );
                        popUpDialog.setView(view1);
                        popUpDialog.show();
                    }
                }
        );
        listView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        int statusValue;
                        statusValue=toDoArrayList.get(position).getStatus();
                        if(statusValue==1){
                            statusValue=0;
                        }else{
                            statusValue=1;
                        }

                        dbHelper.UpdateStatus(toDoArrayList.get(position).getId(),statusValue);
                        DisplayListView();
                        Toast.makeText(MainActivity.this, "Your Entry is updated as Completed", Toast.LENGTH_SHORT).show();

                        return false;
                    }
                }
        );
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
            // This code is running when we clicked the + Action Button from ActionBar
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            final AlertDialog alertDialog = alertDialogBuilder.create();
            inflater = LayoutInflater.from(this);

            View view = inflater.inflate(R.layout.activity_add__todo, null);

            final EditText title = (EditText) view.findViewById(R.id.etTitle);
            final EditText desc = (EditText) view.findViewById(R.id.etDesc);
            final RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
            final DatePicker dtpicker = (DatePicker) view.findViewById(R.id.datePicker);
            Button btnSave = (Button) view.findViewById(R.id.btnSave);
            final Button btnCancel = (Button) view.findViewById(R.id.btnCancel);

            radioGroup.setVisibility(View.GONE);

            btnSave.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String selectDate, mDay, mMonth, mYear;

                            if (dtpicker.getDayOfMonth() <= 9) {
                                mDay = "0" + String.valueOf(dtpicker.getDayOfMonth());
                            } else {
                                mDay = String.valueOf(dtpicker.getDayOfMonth());
                            }

                            if (dtpicker.getMonth() < 9) {
                                mMonth = "0" + String.valueOf(dtpicker.getMonth() + 1);
                            } else {
                                mMonth = "" + String.valueOf(dtpicker.getMonth() + 1);
                            }
                            mYear = "" + dtpicker.getYear();

                            selectDate = mDay + "/" + mMonth + "/" + mYear;
                            Toast.makeText(MainActivity.this, "" + selectDate, Toast.LENGTH_SHORT).show();

                            dbHelper.insertToDo(title.getText().toString(), desc.getText().toString(), selectDate, 0);
                            title.setText("");
                            desc.setText("");
                            title.requestFocus();

                            DisplayListView(); // update the list view after insert
//                            Toast.makeText(getApplicationContext(), "Record Saved successfully", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                        }
                    }
            );
            btnCancel.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                        }
                    }
            );


            title.setOnFocusChangeListener(
                    new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (hasFocus) {
                                if (title.getText().toString().trim().length() < 1) {
                                    title.setError("Minimum 1 Char");
                                }
                            }
                        }
                    }
            );

            desc.setOnFocusChangeListener(
                    new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (hasFocus) {
                                if (desc.getText().toString().trim().length() < 1) {
                                    desc.setError("Minimun 1 Char");
                                }
                            }
                        }
                    }
            );

            alertDialog.setView(view);
            alertDialog.show();

        } else if (item.getItemId() == R.id.mnuComplete) {
//             Here the All todo complete code to be write
            Intent intent=new Intent(MainActivity.this,Completed.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    private void DisplayListView() {
        dbHelper = new DBHelper(getApplicationContext());

        listView = (ListView) findViewById(R.id.listViewToDo);

        toDoArrayList = dbHelper.getAllToDoList();

        customAdapter = new CustomAdapter(getApplicationContext(), toDoArrayList);

        listView.setAdapter(customAdapter);
    }


}
