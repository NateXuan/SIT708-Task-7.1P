package com.example.sit708_task_7_1p;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CreateAdvertActivity extends Activity {

    private RadioGroup radioPostType;
    private RadioButton radioLost, radioFound;
    private EditText editTextName, editTextPhone, editTextDescription, editTextDate, editTextLocation;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert);

        radioPostType = findViewById(R.id.radioGroupPostType);
        radioLost = findViewById(R.id.radioLost);
        radioFound = findViewById(R.id.radioFound);
        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextDate = findViewById(R.id.editTextDate);
        editTextLocation = findViewById(R.id.editTextLocation);
        buttonSave = findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postType = radioLost.isChecked() ? "Lost" : "Found";
                String name = editTextName.getText().toString();
                String phone = editTextPhone.getText().toString();
                String description = editTextDescription.getText().toString();
                String date = editTextDate.getText().toString();
                String location = editTextLocation.getText().toString();

                // Ensure all fields are filled out
                if (name.isEmpty() || phone.isEmpty() || description.isEmpty() || date.isEmpty() || location.isEmpty()) {
                    // Notify user to fill out all fields
                    Toast.makeText(CreateAdvertActivity.this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create a LostFoundItem object
                LostFoundItem item = new LostFoundItem(postType + " " + name, description, phone, date, location);

                // Save the item
                saveItem(item);

                // Finish the activity
                finish();
            }
        });
    }

    private void saveItem(LostFoundItem item) {
        SharedPreferences sharedPreferences = getSharedPreferences("LostFoundPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Retrieve the existing items
        String itemsJson = sharedPreferences.getString("items", "[]");
        Type type = new TypeToken<ArrayList<LostFoundItem>>(){}.getType();
        ArrayList<LostFoundItem> itemList = new Gson().fromJson(itemsJson, type);

        // Add the new item
        itemList.add(item);

        // Save the updated list
        itemsJson = new Gson().toJson(itemList);
        editor.putString("items", itemsJson);
        editor.apply();
    }
}