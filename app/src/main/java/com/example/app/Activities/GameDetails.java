package com.example.app.Activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.app.R;

public class GameDetails extends AppCompatActivity {

    TextView textViewGameName, textViewGameDescription, textViewPlatform, textViewReleaseDate;
    ImageView imageViewGame;
    DBHelper2 dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);

        textViewGameName = findViewById(R.id.textViewGameNameValue);
        textViewGameDescription = findViewById(R.id.textViewGameDescriptionValue);
        textViewPlatform = findViewById(R.id.textViewPlatformValue);
        textViewReleaseDate = findViewById(R.id.textViewReleaseDateValue);
        imageViewGame = findViewById(R.id.imageViewGame);
        dbHelper = new DBHelper2(this);

        retrieveGameData();
    }

    private void retrieveGameData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                DBHelper2.COLUMN_GAME_NAME,
                DBHelper2.COLUMN_GAME_DESCRIPTION,
                DBHelper2.COLUMN_PLATFORM,
                DBHelper2.COLUMN_RELEASE_DATE
        };
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = null;

        Cursor cursor = db.query(
                DBHelper2.TABLE_GAME_DETAILS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        if (cursor != null && cursor.moveToFirst()) {
            String gameName = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.COLUMN_GAME_NAME));
            String gameDescription = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.COLUMN_GAME_DESCRIPTION));
            String platform = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.COLUMN_PLATFORM));
            String releaseDate = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.COLUMN_RELEASE_DATE));

            textViewGameName.setText(gameName);
            textViewGameDescription.setText(gameDescription);
            textViewPlatform.setText(platform);
            textViewReleaseDate.setText(releaseDate);

            cursor.close();
        } else {
            Toast.makeText(this, "No game details found.", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }
}
