package com.example.week1;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Spinner fromUnitSpinner;
    private Spinner toUnitSpinner;
    private EditText valueEditText;
    private Button convertButton;
    private TextView resultTextView;

    private Map<String, Double> conversionFactors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fromUnitSpinner = findViewById(R.id.fromUnit);
        toUnitSpinner = findViewById(R.id.toUnit);
        valueEditText = findViewById(R.id.editTextNumberDecimal);
        convertButton = findViewById(R.id.button2);
        resultTextView = findViewById(R.id.resultText);

        // Initialize conversion factors
        conversionFactors = new HashMap<>();
        // Length conversions
        conversionFactors.put("Inch to Centimeter", 2.54);
        conversionFactors.put("Inch to Foot", 1 / 12.0);
        conversionFactors.put("Inch to Yard", 1 / 36.0);
        conversionFactors.put("Inch to Mile", 1 / 63360.0);
        conversionFactors.put("Centimeter to Inch", 1/2.54);
        conversionFactors.put("Centimeter to Foot", 0.0328);
        conversionFactors.put("Centimeter to Yard", 0.0109);
        // Weight conversions
        conversionFactors.put("Pound to Kilogram", 0.453592);
        conversionFactors.put("Ounce to Kilogram", 28.3495);
        conversionFactors.put("Ton to Kilogram", 907.185);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertUnits();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.length_units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromUnitSpinner.setAdapter(adapter);
        toUnitSpinner.setAdapter(adapter);
    }

    private void convertUnits() {
        String fromUnit = fromUnitSpinner.getSelectedItem().toString();
        String toUnit = toUnitSpinner.getSelectedItem().toString();
        double inputValue = Double.parseDouble(valueEditText.getText().toString());
        double result;

        if (fromUnit.equals("Celsius") && toUnit.equals("Fahrenheit")) {
            result = (inputValue * 1.8) + 32;
        } else if (fromUnit.equals("Fahrenheit") && toUnit.equals("Celsius")) {
            result = (inputValue - 32) / 1.8;
        } else if (fromUnit.equals("Celsius") && toUnit.equals("Kelvin")) {
            result = inputValue + 273.15;
        } else if (fromUnit.equals("Kelvin") && toUnit.equals("Celsius")) {
            result = inputValue - 273.15;
        }

        else {
            double conversionFactor;
            if (conversionFactors.containsKey(fromUnit + " to " + toUnit)) {
                conversionFactor = conversionFactors.get(fromUnit + " to " + toUnit);
                result = inputValue * conversionFactor;
            } else {
                result = 0;
                resultTextView.setText("Invalid conversion. Units not supported.");
                Toast.makeText(this, "Invalid conversion: Cannot convert between different types of units.", Toast.LENGTH_SHORT).show();
            }
        }

        resultTextView.setText(String.valueOf(result));
    }
}
