
package com.example.app.Activities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper4 extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "user.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_USER_PROFILE = "user_profile";
    public static final String COLUMN_PROFILE_ID = "profile_id";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PROFILE_IMAGE = "profile_image";
    public static final String COLUMN_BIO = "bio";

    public DBHelper4(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_USER_PROFILE_TABLE = "CREATE TABLE " + TABLE_USER_PROFILE +
                "(" +
                COLUMN_PROFILE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_USER_ID + " INTEGER," +
                COLUMN_USERNAME + " TEXT," +
                COLUMN_PROFILE_IMAGE + " BLOB," +
                COLUMN_BIO + " TEXT," +
                "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + DBHelper.TABLE_USERS + "(" + DBHelper.COLUMN_ID + ")" +
                ")";
        db.execSQL(CREATE_USER_PROFILE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_PROFILE);


        onCreate(db);
    }


    public Cursor getUserProfileByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_USERNAME, COLUMN_BIO};
        String selection = COLUMN_USER_ID + "=(SELECT " + DBHelper.COLUMN_ID + " FROM " + DBHelper.TABLE_USERS + " WHERE " + DBHelper.COLUMN_EMAIL + "=?)";
        String[] selectionArgs = {email};
        return db.query(TABLE_USER_PROFILE, projection, selection, selectionArgs, null, null, null);
    }


    public void updateUserProfile(String username, String bio, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_BIO, bio);
        String whereClause = COLUMN_USER_ID + "=(SELECT " + DBHelper.COLUMN_ID + " FROM " + DBHelper.TABLE_USERS + " WHERE " + DBHelper.COLUMN_EMAIL + "=?)";
        String[] whereArgs = {email};
        db.update(TABLE_USER_PROFILE, values, whereClause, whereArgs);
    }
}
