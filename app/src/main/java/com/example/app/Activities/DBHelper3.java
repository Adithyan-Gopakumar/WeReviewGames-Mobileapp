package com.example.app.Activities;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper3 extends SQLiteOpenHelper {

    public DBHelper3(Context context) {
        super(context, "user.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_SESSION_TABLE = "CREATE TABLE session (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER," +
                "is_active INTEGER," +
                "FOREIGN KEY (user_id) REFERENCES " + DBHelper.TABLE_USERS + "(" + DBHelper.COLUMN_ID + ")" +
                ")";
        db.execSQL(CREATE_SESSION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS session");


        onCreate(db);
    }

    public boolean saveSession(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("is_active", 1);
        long result = db.insert("session", null, values);

        return result != -1;
    }
}
