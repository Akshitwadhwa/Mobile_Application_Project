package com.example.myapplication2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends AppCompatActivity {

    // UI elements for user input, selection, and displaying results
    EditText inputValue;
    Spinner fromUnit, toUnit;
    TextView resultText;
    Button convertButton;

    // Array of measurement units available for conversion
    String[] units = {"Feet", "Inches", "Centimeters", "Meters", "Yards"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Apply the saved theme before setting content view
        applyTheme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Linking UI elements to their XML components
        inputValue = findViewById(R.id.inputValue);
        fromUnit = findViewById(R.id.fromUnit);
        toUnit = findViewById(R.id.toUnit);
        resultText = findViewById(R.id.resultText);
        convertButton = findViewById(R.id.convertButton);

        // Setting up the dropdown menus (spinners) with unit options
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, units);
        fromUnit.setAdapter(adapter);
        toUnit.setAdapter(adapter);

        // Setting up the conversion button's click event
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertUnits(); // Call function to perform conversion
            }
        });
    }

    // Apply the saved theme setting
    private void applyTheme() {
        SharedPreferences sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);
        int themeMode = sharedPreferences.getInt("theme_mode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        AppCompatDelegate.setDefaultNightMode(themeMode);
    }

    // Create options menu with settings option
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // Handle menu item selection
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_settings) {
            // Navigate to settings activity
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Function to handle unit conversion
    private void convertUnits() {
        String input = inputValue.getText().toString(); // Get user input

        // Check if input is empty and show a message if needed
        if (input.isEmpty()) {
            Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();
            return;
        }

        double value = Double.parseDouble(input); // Convert input to a numeric value
        String from = fromUnit.getSelectedItem().toString(); // Get selected 'from' unit
        String to = toUnit.getSelectedItem().toString(); // Get selected 'to' unit

        // Convert input value to a base unit (centimeters)
        double baseValue = convertToBaseUnit(value, from);

        // Convert base value to the target unit
        double convertedValue = convertFromBaseUnit(baseValue, to);

        // Display the result
        resultText.setText("Converted Value: " + convertedValue + " " + to);
    }

    // Function to convert input value to a base unit (centimeters)
    private double convertToBaseUnit(double value, String unit) {
        switch (unit) {
            case "Feet":
                return value * 30.48; // 1 Foot = 30.48 cm
            case "Inches":
                return value * 2.54; // 1 Inch = 2.54 cm
            case "Centimeters":
                return value; // Already in cm
            case "Meters":
                return value * 100; // 1 Meter = 100 cm
            case "Yards":
                return value * 91.44; // 1 Yard = 91.44 cm
            default:
                return 0; // Default case (should not happen)
        }
    }

    // Function to convert base unit (centimeters) to the desired unit
    private double convertFromBaseUnit(double baseValue, String unit) {
        switch (unit) {
            case "Feet":
                return baseValue / 30.48; // Convert from cm to feet
            case "Inches":
                return baseValue / 2.54; // Convert from cm to inches
            case "Centimeters":
                return baseValue; // Already in cm
            case "Meters":
                return baseValue / 100; // Convert from cm to meters
            case "Yards":
                return baseValue / 91.44; // Convert from cm to yards
            default:
                return 0; // Default case (should not happen)
        }
    }
}
