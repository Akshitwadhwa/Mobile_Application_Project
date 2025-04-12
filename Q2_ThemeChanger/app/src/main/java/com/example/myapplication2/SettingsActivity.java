package com.example.myapplication2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SettingsActivity extends AppCompatActivity {

    // UI components for theme selection and saving preference
    private RadioGroup themeGroup;
    private RadioButton lightThemeButton, darkThemeButton;
    private Button saveButton;

    // SharedPreferences to store theme selection
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize UI components
        themeGroup = findViewById(R.id.themeGroup);
        lightThemeButton = findViewById(R.id.lightThemeButton);
        darkThemeButton = findViewById(R.id.darkThemeButton);
        saveButton = findViewById(R.id.saveButton);

        // Initialize SharedPreferences to store and retrieve theme selection
        sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);

        // Retrieve the currently saved theme mode
        int currentTheme = sharedPreferences.getInt("theme_mode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

        // Set the selected radio button based on the saved theme mode
        if (currentTheme == AppCompatDelegate.MODE_NIGHT_YES) {
            darkThemeButton.setChecked(true); // Dark theme is selected
        } else {
            lightThemeButton.setChecked(true); // Light theme is selected
        }

        // Set up save button click listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveThemeSelection(); // Call function to save the selected theme
            }
        });
    }

    // Function to save the selected theme
    private void saveThemeSelection() {
        int themeMode;

        // Determine which theme was selected by the user
        if (darkThemeButton.isChecked()) {
            themeMode = AppCompatDelegate.MODE_NIGHT_YES; // Set dark mode
        } else {
            themeMode = AppCompatDelegate.MODE_NIGHT_NO; // Set light mode
        }

        // Store the selected theme mode in SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("theme_mode", themeMode);
        editor.apply();

        // Apply the selected theme globally
        AppCompatDelegate.setDefaultNightMode(themeMode);

        // Recreate the activity to apply the theme change immediately
        recreate();
    }
}
