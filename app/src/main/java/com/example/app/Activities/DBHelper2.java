package com.example.app.Activities;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper2 extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "game_details.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_GAME_DETAILS = "gamedetails";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_GAME_NAME = "game_name";
    public static final String COLUMN_GAME_DESCRIPTION = "game_description";
    public static final String COLUMN_PLATFORM = "platform";
    public static final String COLUMN_RELEASE_DATE = "release_date"; // Changed to DATE
    public static final String COLUMN_GAME_IMAGE = "game_image";

    public DBHelper2(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_GAME_DETAILS_TABLE = "CREATE TABLE " + TABLE_GAME_DETAILS +
                "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_GAME_NAME + " TEXT," +
                COLUMN_GAME_DESCRIPTION + " TEXT," +
                COLUMN_PLATFORM + " TEXT," +
                COLUMN_RELEASE_DATE + " DATE," +
                COLUMN_GAME_IMAGE + " BLOB" +
                ")";
        db.execSQL(CREATE_GAME_DETAILS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAME_DETAILS);


        onCreate(db);
    }


    public boolean addGameDetails(String gameName, String gameDescription, String platform, String releaseDate, byte[] gameImage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_GAME_NAME, gameName);
        values.put(COLUMN_GAME_DESCRIPTION, gameDescription);
        values.put(COLUMN_PLATFORM, platform);
        values.put(COLUMN_RELEASE_DATE, releaseDate);
        values.put(COLUMN_GAME_IMAGE, gameImage);
        long result = db.insert(TABLE_GAME_DETAILS, null, values);
        return result != -1;
    }


}
