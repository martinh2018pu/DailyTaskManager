package com.example.dailytaskmanager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public abstract class DbActivity extends AppCompatActivity {

    protected void InitializeDb() {
        SQLiteDatabase db = null;

        try {
            db = SQLiteDatabase.openOrCreateDatabase(
                    getFilesDir().getPath() + "/DailyTaskManager.db",
                    null
            );

            String q = "CREATE TABLE if not exists TASK( " +
                    "ID integer not null primary key AUTOINCREMENT, " +
                    "DESCRIPTION text not null, " +
                    "DATE text not null " +
                    ");";

            db.execSQL(q);
        } catch (Exception e) {
            ToastShowLong(this, "Initialize database error: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
                db = null;
            }
        }

        //ToastShowLong(this, "Initialize database successful.");
    }

    protected ArrayList<Task> SelectFromDb(String selectQuery, String[] args) {
        SQLiteDatabase db = null;
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            db = SQLiteDatabase.openOrCreateDatabase(
                    getFilesDir().getPath() + "/DailyTaskManager.db",
                    null
            );

            Cursor cursor = db.rawQuery(selectQuery, args);

            while (cursor.moveToNext()) {
//                int taskId = parseInt(cursor.getString(cursor.getColumnIndex("ID")));
                String taskId = cursor.getString(cursor.getColumnIndex("ID"));
                String taskDescription = cursor.getString(cursor.getColumnIndex("DESCRIPTION"));
                String taskDate = cursor.getString(cursor.getColumnIndex("DATE"));

                tasks.add(new Task(taskId, taskDescription, taskDate));
            }

            cursor.close();
        } catch (Exception e) {
            ToastShowLong(this, "Select from database error: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
                db = null;
            }
        }

        //ToastShowLong(this, "Select from database successful.");
        return tasks;
    }

    protected void ExecuteDbQuery(String dbQuery, Object[] args) {
        SQLiteDatabase db = null;

        try {
            db = SQLiteDatabase.openOrCreateDatabase(
                    getFilesDir().getPath() + "/DailyTaskManager.db",
                    null
            );

            db.execSQL(dbQuery, args);
        } catch (Exception e) {
            ToastShowLong(this, "Execute database query error: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
                db = null;
            }
        }

        //ToastShowLong(this, "Execute database query successful.");
    }

    protected void InsertInDb(String taskDescription, String taskDate) {
        SQLiteDatabase db = null;

        try {
            db = SQLiteDatabase.openOrCreateDatabase(
                    getFilesDir().getPath() + "/DailyTaskManager.db",
                    null
            );

            String q = "INSERT INTO TASK(DESCRIPTION, DATE) VALUES(?, ?) ";

            db.execSQL(q, new Object[]{
                    taskDescription, taskDate
            });
        } catch (Exception e) {
            ToastShowLong(this, "Insert in database error: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
                db = null;
            }
        }

        //ToastShowLong(this, "Insert in database successful.");
    }

    protected void ToastShowLong(Context context, CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
}
