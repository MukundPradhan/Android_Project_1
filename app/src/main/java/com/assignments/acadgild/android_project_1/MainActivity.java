package com.assignments.acadgild.android_project_1;

import android.app.AlertDialog;
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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.assignments.acadgild.android_project_1.adapter.CustomAdapter;
import com.assignments.acadgild.android_project_1.dbhelper.DBHelper;
import com.assignments.acadgild.android_project_1.model.ToDo;

import java.util.ArrayList;

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

                       final TextView popUpID =(TextView) view1.findViewById(R.id.tv_ID_frmt);
                        final EditText popUpTitle = (EditText) view1.findViewById(R.id.etTitle);
                        final EditText popUpDesc = (EditText) view1.findViewById(R.id.etDesc);
                        final DatePicker popUpDtpicker = (DatePicker) view1.findViewById(R.id.datePicker);
                        final Button popUpBtnSave = (Button) view1.findViewById(R.id.btnSave);
                        final Button popUpBtnCancel = (Button) view1.findViewById(R.id.btnCancel);

                       //popUpID.setText(toDoArrayList.get(Integer.parseInt(id).getId()));



                        popUpID.setText(String.valueOf(toDoArrayList.get(position).getId()).toString());
                        popUpDesc.setText(toDoArrayList.get(position).getDescription());
                        Toast.makeText(MainActivity.this, ""+ popUpDesc.getText().toString(), Toast.LENGTH_SHORT).show();

                        popUpBtnSave.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {


                                        popUpDialog.dismiss();
                                    }
                                }
                        );
                       // popUpDialog.setView(view1);
                      //  popUpDialog.show();
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
            final RadioGroup radioGroup=(RadioGroup) view.findViewById(R.id.radioGroup);
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
