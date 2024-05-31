package com.example.task41;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TaskManagerDB extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "tasks";
    private static final String DB_NAME = "newDB2.db";
    public TaskManagerDB(Context context) { super(context, DB_NAME, null, 1);}


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("Table", "Inside oncreate");

        String sqlDB = "CREATE TABLE tasks (id TEXT PRIMARY KEY, title TEXT, description TEXT, dueDate TEXT)";
        db.execSQL(sqlDB);
//        Log.e("Table", "Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public Boolean addTask(Task task) {
        SQLiteDatabase sql_DB = getWritableDatabase();
        ContentValues cal = new ContentValues();
        cal.put("id", task.getId());
        cal.put("title", task.getTitle());
        cal.put("description", task.getDescription());
        cal.put("dueDate", task.getDueDate());

        long rowId = sql_DB.insert(TABLE_NAME, null, cal);
        sql_DB.close();
        if (rowId > -1){
            System.out.println("Task added " + rowId);
            return true;
        } else {
            System.out.println("Insert failed | ERROR");
            return false;
        }
    }

    public Task getTask(String id) {
        SQLiteDatabase sql_DB = this.getReadableDatabase();
        Cursor query = sql_DB.query(TABLE_NAME, new String[] {"id", "title", "description", "dueDate"},
                "id=?", new String[]{id}, null, null, null, null);
        if (query != null) {
            query.moveToFirst();
        }
        Task task = new Task(query.getString(0), query.getString(1), query.getString(2), query.getString(3));
        query.close();
        sql_DB.close();
        return task;
    }

    public List<Task> getAllTasks() {
        SQLiteDatabase sql_DB = getReadableDatabase();
        Log.e("testing get all tasks", "sql_DB.toString()");
        Cursor query = sql_DB.query(TABLE_NAME, null, null,null,null,null,null);
        List<Task> result = new ArrayList<>();

        while (query.moveToNext()) {
            result.add(new Task(query.getString(0), query.getString(1), query.getString(2), query.getString(3)));
        }
        query.close();
        sql_DB.close();
        return result;
    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "id=?", new String[] {row_id});
        if(result == -1){
            Log.e("Delete", "Failed");
        }else{
            Log.e("Delete", "Passed");
        }
    }
}
