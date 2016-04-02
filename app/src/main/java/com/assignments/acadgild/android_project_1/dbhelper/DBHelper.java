package com.assignments.acadgild.android_project_1.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.assignments.acadgild.android_project_1.model.ToDo;

import java.util.ArrayList;

/**
 * Created by Mukund on 20-03-16.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "DB_TODO";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "TBL_TODO";
    private static final String KEY_ID = "ID";
    private static final String KEY_TITLE = "TITLE";
    private static final String KEY_DESCRIPTION = "DESC";
    private static final String KEY_DATE = "DATE";
    private static final String KEY_STATUS = "STATUS";

    SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "CREATE TABLE " + TABLE_NAME + " (" +
                KEY_ID + " INTEGER PRIMARY KEY, " + " " +
                KEY_TITLE + " TEXT, " +
                KEY_DESCRIPTION + " TEXT, " +
                KEY_DATE + " TEXT, " +
                KEY_STATUS + "  INTEGER DEFAULT 0)";
        db.execSQL(create_table);


    }

    public void insertToDo(String title, String desc, String date, int status) {
        db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TITLE, title);
        values.put(KEY_DESCRIPTION, desc);
        values.put(KEY_DATE, date);
        values.put(KEY_STATUS, status);
        try {
            db.insert(TABLE_NAME, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<ToDo> getAllToDoList() {
        db = getReadableDatabase();

        ArrayList<ToDo> toDoArrayList = new ArrayList<>();
        String select_all = "SELECT * FROM " + TABLE_NAME + "  ORDER BY " + KEY_DATE + " ASC;";

        Cursor cursor = db.rawQuery(select_all, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String desc = cursor.getString(2);
                String date = cursor.getString(3);
                int status = cursor.getInt(4);

                ToDo toDo = new ToDo(id, title, desc, date, status);

                toDoArrayList.add(toDo);

            } while ((cursor.moveToNext()));
        }

        return toDoArrayList;

    }
}

