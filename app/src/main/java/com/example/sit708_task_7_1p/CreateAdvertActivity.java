package com.example.sit708_task_7_1p;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CreateAdvertActivity extends Activity {

    private RadioButton radioLost;
    private EditText editTextName, editTextPhone, editTextDescription, editTextDate, editTextLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert);

        RadioGroup radioPostType = findViewById(R.id.radioGroupPostType);
        radioLost = findViewById(R.id.radioLost);
        RadioButton radioFound = findViewById(R.id.radioFound);
        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextDate = findViewById(R.id.editTextDate);
        editTextLocation = findViewById(R.id.editTextLocation);
        Button buttonSave = findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(v -> {
            if (!validateInputs()) {
                return;
            }

            String postType = radioLost.isChecked() ? "Lost" : "Found";
            String name = editTextName.getText().toString();
            String phone = editTextPhone.getText().toString();
            String description = editTextDescription.getText().toString();
            String date = editTextDate.getText().toString();
            String location = editTextLocation.getText().toString();

            LostFoundItem item = new LostFoundItem(postType + " " + name, description, phone, date, location);
            saveItem(item);
            finish();
        });
    }

    private boolean validateInputs() {
        String name = editTextName.getText().toString();
        String phone = editTextPhone.getText().toString();
        String description = editTextDescription.getText().toString();
        String date = editTextDate.getText().toString();
        String location = editTextLocation.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(description)
                || TextUtils.isEmpty(date) || TextUtils.isEmpty(location)) {
            Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!phone.matches("\\d+")) {
            Toast.makeText(this, "Invalid phone number. Please use only digits.", Toast.LENGTH_SHORT).show();
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        sdf.setLenient(false);
        try {
            sdf.parse(date);
        } catch (ParseException e) {
            Toast.makeText(this, "Invalid date format. Please use YYYY-MM-DD.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void saveItem(LostFoundItem item) {
        SharedPreferences sharedPreferences = getSharedPreferences("LostFoundPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String itemsJson = sharedPreferences.getString("items", "[]");
        Type type = new TypeToken<ArrayList<LostFoundItem>>(){}.getType();
        ArrayList<LostFoundItem> itemList = new Gson().fromJson(itemsJson, type);
        itemList.add(item);
        itemsJson = new Gson().toJson(itemList);
        editor.putString("items", itemsJson);
        editor.apply();
    }
}
