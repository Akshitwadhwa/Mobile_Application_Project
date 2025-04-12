package com.example.unitconvertor;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText inputValue;
    Spinner inputUnitSpinner, outputUnitSpinner;
    TextView resultText;
    Button convertButton;

    String[] inputUnits = {"Select source unit", "Feet", "Inches", "Centimeters", "Meters", "Yards"};
    String[] outputUnits = {"Select target unit", "Feet", "Inches", "Centimeters", "Meters", "Yards"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputValue = findViewById(R.id.input_the_value);
        inputUnitSpinner = findViewById(R.id.inputUnitSpinner);
        outputUnitSpinner = findViewById(R.id.outputvalue);
        resultText = findViewById(R.id.result);
        convertButton = findViewById(R.id.finalbutton);


        ArrayAdapter<String> inputAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, inputUnits) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0; // Disable first item
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTextColor(position == 0 ? Color.GRAY : Color.BLACK);
                return view;
            }
        };

        ArrayAdapter<String> outputAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, outputUnits) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTextColor(position == 0 ? Color.GRAY : Color.BLACK);
                return view;
            }
        };

        inputAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        outputAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputUnitSpinner.setAdapter(inputAdapter);
        outputUnitSpinner.setAdapter(outputAdapter);

        // Convert button logic
        convertButton.setOnClickListener(v -> convert());
    }

    void convert() {
        String inputStr = inputValue.getText().toString().trim();
        if (inputStr.isEmpty()) {
            resultText.setText("Result: ");
            return;
        }

        String fromUnit = inputUnitSpinner.getSelectedItem().toString();
        String toUnit = outputUnitSpinner.getSelectedItem().toString();

        // Check if user selected valid units
        if (fromUnit.startsWith("Select") || toUnit.startsWith("Select")) {
            resultText.setText("Please select valid units.");
            return;
        }

        double input = Double.parseDouble(inputStr);
        double meters = toMeters(input, fromUnit);
        double result = fromMeters(meters, toUnit);

        resultText.setText("Result: " + String.format("%.4f", result) + " " + toUnit);
    }

    double toMeters(double value, String unit) {
        switch (unit) {
            case "Feet": return value * 0.3048;
            case "Inches": return value * 0.0254;
            case "Centimeters": return value / 100.0;
            case "Meters": return value;
            case "Yards": return value * 0.9144;
            default: return 0;
        }
    }

    double fromMeters(double meters, String unit) {
        switch (unit) {
            case "Feet": return meters / 0.3048;
            case "Inches": return meters / 0.0254;
            case "Centimeters": return meters * 100;
            case "Meters": return meters;
            case "Yards": return meters / 0.9144;
            default: return 0;
        }
    }
}