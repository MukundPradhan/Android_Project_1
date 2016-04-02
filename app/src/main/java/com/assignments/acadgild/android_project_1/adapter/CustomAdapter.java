package com.assignments.acadgild.android_project_1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.assignments.acadgild.android_project_1.R;
import com.assignments.acadgild.android_project_1.model.ToDo;

import java.util.ArrayList;

/**
 * Created by Mukund on 20-03-16.
 */
public class CustomAdapter extends BaseAdapter {
    ArrayList<ToDo> toDoArrayList;
    LayoutInflater inflater;
    Context context;

    public CustomAdapter(Context context, ArrayList<ToDo> toDoArrayList) {
        this.toDoArrayList = toDoArrayList;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {

        return toDoArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return toDoArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int ID = 0;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.todo_list_format, parent, false);
        }
        TextView txtHeaderDate = (TextView) convertView.findViewById(R.id.tv_Header_frmt);
        TextView txtID = (TextView) convertView.findViewById(R.id.tv_ID_frmt);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.tv_Title_frmt);
        TextView txtdesc = (TextView) convertView.findViewById(R.id.tv_Desc_frmt);
        TextView txtdate = (TextView) convertView.findViewById(R.id.tv_Date_frmt);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        TextView headerDate = (TextView) convertView.findViewById(R.id.tv_Header_frmt);


        ToDo toDo = toDoArrayList.get(position);


        txtID.setText(String.valueOf(toDo.getId()));
        //txtID.setText(String.valueOf(position));
        txtHeaderDate.setText(toDo.getDate());
        txtTitle.setText(toDo.getTitle());
        txtdesc.setText(toDo.getDescription());
        txtdate.setText(toDo.getDate());
        headerDate.setText(toDo.getDate());
        txtHeaderDate.setVisibility(View.VISIBLE);

        if (toDo.getStatus().equals(0)) {
            imageView.setImageResource(R.drawable.incomplete);
        } else {
            imageView.setImageResource(R.drawable.complete);
        }

        if (position >= 1) {
            String curDate, prevDate;
            curDate = toDoArrayList.get(position).getDate();
            prevDate = toDoArrayList.get(position - 1).getDate();

            if (curDate.equals(prevDate)) {
                txtHeaderDate.setVisibility(View.INVISIBLE);
            } else {
                txtHeaderDate.setVisibility(View.VISIBLE);
            }
        }


        return convertView;
    }
}
