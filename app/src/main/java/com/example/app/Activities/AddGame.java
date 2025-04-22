package com.example.app.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app.R;

import java.io.ByteArrayOutputStream;

public class AddGame extends AppCompatActivity {

    private static final int REQUEST_PICK_IMAGE = 102;

    private ImageView imageViewGame;
    private EditText editTextGameName;
    private EditText editTextGameDescription;
    private EditText editTextPlatform;
    private EditText editTextReleaseDate;
    private Button buttonAddImage;
    private Button buttonSave;
    private ImageView imageViewProfile;
    private ImageView imageViewHome;

    private byte[] gameImageByteArray;

    private DBHelper2 dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        dbHelper = new DBHelper2(this);

        imageViewGame = findViewById(R.id.imageViewGame);
        editTextGameName = findViewById(R.id.editTextGameName);
        editTextGameDescription = findViewById(R.id.editTextGameDescription);
        editTextPlatform = findViewById(R.id.editTextPlatform);
        editTextReleaseDate = findViewById(R.id.editTextReleaseDate);
        buttonAddImage = findViewById(R.id.buttonAddImage);
        buttonSave = findViewById(R.id.buttonSubmit);
        imageViewProfile = findViewById(R.id.imageProfile);
        imageViewHome = findViewById(R.id.imageHome);

        buttonAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromGallery();
            }
        });

        imageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToProfileActivity();
            }
        });

        imageViewHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGameDetails();
            }
        });
    }

    private void pickImageFromGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, REQUEST_PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            try {
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                imageViewGame.setImageBitmap(imageBitmap);
                gameImageByteArray = getByteArrayFromBitmap(imageBitmap);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private byte[] getByteArrayFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void saveGameDetails() {
        String gameName = editTextGameName.getText().toString().trim();
        String gameDescription = editTextGameDescription.getText().toString().trim();
        String platform = editTextPlatform.getText().toString().trim();
        String releaseDate = editTextReleaseDate.getText().toString().trim();

        if (gameName.isEmpty() || gameDescription.isEmpty() || platform.isEmpty() || releaseDate.isEmpty() || gameImageByteArray == null) {
            Toast.makeText(this, "Please fill in all the fields and add an image", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isInserted = dbHelper.addGameDetails(gameName, gameDescription, platform, releaseDate, gameImageByteArray);
        if (isInserted) {
            Toast.makeText(this, "Game details saved successfully", Toast.LENGTH_SHORT).show();
            finish(); // Finish the activity and return to the home page
        } else {
            Toast.makeText(this, "Failed to save game details", Toast.LENGTH_SHORT).show();
        }
    }

    private void goToProfileActivity() {
        Intent intent = new Intent(AddGame.this, Profile.class);
        startActivity(intent);
    }

    private void goToMainActivity() {
        Intent intent = new Intent(AddGame.this, MainActivity.class);
        startActivity(intent);
    }
}
