package com.example.app.Activities;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper5 extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "user_comments.db";
    public static final int DATABASE_VERSION = 1;


    public static final String TABLE_COMMENTS = "comments";
    public static final String COLUMN_COMMENT_ID = "comment_id";
    public static final String COLUMN_GAME_ID = "game_id"; // References game_details table
    public static final String COLUMN_USER_ID = "user_id"; // References user_profile table
    public static final String COLUMN_COMMENT_TEXT = "comment_text";

    public DBHelper5(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create comments table
        String CREATE_COMMENTS_TABLE = "CREATE TABLE " + TABLE_COMMENTS +
                "(" +
                COLUMN_COMMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_GAME_ID + " INTEGER," +
                COLUMN_USER_ID + " INTEGER," +
                COLUMN_COMMENT_TEXT + " TEXT," +
                "FOREIGN KEY (" + COLUMN_GAME_ID + ") REFERENCES " + DBHelper2.TABLE_GAME_DETAILS + "(" + DBHelper2.COLUMN_ID + ")," +
                "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + DBHelper4.TABLE_USER_PROFILE + "(" + DBHelper4.COLUMN_PROFILE_ID + ")" +
                ")";
        db.execSQL(CREATE_COMMENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);


        onCreate(db);
    }


    public boolean addComment(int gameId, int userId, String commentText) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_GAME_ID, gameId);
        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_COMMENT_TEXT, commentText);
        long result = db.insert(TABLE_COMMENTS, null, values);
        return result != -1;
    }


}
