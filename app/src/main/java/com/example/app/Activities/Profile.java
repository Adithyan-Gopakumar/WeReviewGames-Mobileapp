package com.example.app.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.app.R;

public class Profile extends AppCompatActivity {

    private TextView textUsername, textFirstName, textLastName, textEmail, textBio;
    private EditText editUsername, editFirstName, editLastName, editBio;
    private Button btnEditProfile, btnSave, btnChangePassword, btnDeleteProfile, btnLogout, btnAddImage;
    private DBHelper dbHelper;
    private DBHelper4 dbHelper4;
    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dbHelper = new DBHelper(this);
        dbHelper4 = new DBHelper4(this);

        textUsername = findViewById(R.id.text_username);
        textFirstName = findViewById(R.id.text_first_name);
        textLastName = findViewById(R.id.text_last_name);
        textEmail = findViewById(R.id.text_email);
        textBio = findViewById(R.id.text_bio);
        editUsername = findViewById(R.id.edit_username);
        editFirstName = findViewById(R.id.edit_first_name);
        editLastName = findViewById(R.id.edit_last_name);
        editBio = findViewById(R.id.edit_bio);
        btnEditProfile = findViewById(R.id.btn_edit_profile);
        btnSave = findViewById(R.id.btn_save);
        btnChangePassword = findViewById(R.id.btn_change_password);
        btnDeleteProfile = findViewById(R.id.btn_delete_profile);
        btnLogout = findViewById(R.id.btn_logout);
        btnAddImage = findViewById(R.id.btn_add_image);

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleEditMode();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfileChanges();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        loadUserData();
    }

    private void loadUserData() {
        String email = "adithyan28g@gmail.com";
        Cursor cursor = dbHelper.getUserByEmail(email);
        if (cursor != null && cursor.moveToFirst()) {
            int firstNameIndex = cursor.getColumnIndexOrThrow(DBHelper.COLUMN_FIRST_NAME);
            int lastNameIndex = cursor.getColumnIndexOrThrow(DBHelper.COLUMN_LAST_NAME);
            int emailIndex = cursor.getColumnIndexOrThrow(DBHelper.COLUMN_EMAIL);

            if (firstNameIndex != -1 && lastNameIndex != -1 && emailIndex != -1) {
                String firstName = cursor.getString(firstNameIndex);
                String lastName = cursor.getString(lastNameIndex);
                String userEmail = cursor.getString(emailIndex);

                textUsername.setText(userEmail);
                textFirstName.setText(firstName);
                textLastName.setText(lastName);
                textEmail.setText(userEmail);
            }
        }
        if (cursor != null) {
            cursor.close();
        }
    }

    private void toggleEditMode() {
        isEditMode = !isEditMode;
        if (isEditMode) {
            textUsername.setVisibility(View.GONE);
            textFirstName.setVisibility(View.GONE);
            textLastName.setVisibility(View.GONE);
            textBio.setVisibility(View.GONE);
            textEmail.setVisibility(View.GONE);
            editUsername.setVisibility(View.VISIBLE);
            editFirstName.setVisibility(View.VISIBLE);
            editLastName.setVisibility(View.VISIBLE);
            editBio.setVisibility(View.VISIBLE);
            btnSave.setVisibility(View.VISIBLE);
            btnAddImage.setVisibility(View.VISIBLE);
            editUsername.setText(textUsername.getText());
            editFirstName.setText(textFirstName.getText());
            editLastName.setText(textLastName.getText());
            editBio.setText(textBio.getText());
            btnEditProfile.setVisibility(View.GONE);
        } else {
            textUsername.setVisibility(View.VISIBLE);
            textFirstName.setVisibility(View.VISIBLE);
            textLastName.setVisibility(View.VISIBLE);
            textBio.setVisibility(View.VISIBLE);
            textEmail.setVisibility(View.VISIBLE);
            editUsername.setVisibility(View.GONE);
            editFirstName.setVisibility(View.GONE);
            editLastName.setVisibility(View.GONE);
            editBio.setVisibility(View.GONE);
            btnSave.setVisibility(View.GONE);
            btnAddImage.setVisibility(View.GONE);
            btnEditProfile.setVisibility(View.VISIBLE);
        }
    }

    private void saveProfileChanges() {
        textUsername.setText(editUsername.getText());
        textFirstName.setText(editFirstName.getText());
        textLastName.setText(editLastName.getText());
        textBio.setText(editBio.getText());

        textUsername.setVisibility(View.VISIBLE);
        textFirstName.setVisibility(View.VISIBLE);
        textLastName.setVisibility(View.VISIBLE);
        textBio.setVisibility(View.VISIBLE);
        textEmail.setVisibility(View.VISIBLE);
        editUsername.setVisibility(View.GONE);
        editFirstName.setVisibility(View.GONE);
        editLastName.setVisibility(View.GONE);
        editBio.setVisibility(View.GONE);
        btnSave.setVisibility(View.GONE);
        btnAddImage.setVisibility(View.GONE);

        btnEditProfile.setVisibility(View.VISIBLE);

        String email = "adithyan28g@gmail.com";
        dbHelper4.updateUserProfile(editUsername.getText().toString(), editBio.getText().toString(), email);
    }

    private void logoutUser() {
        Intent intent = new Intent(Profile.this, Login.class);
        startActivity(intent);
        finish();
    }
}
