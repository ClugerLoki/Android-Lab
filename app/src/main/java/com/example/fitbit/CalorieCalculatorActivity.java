package com.example.fitbit;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CalorieCalculatorActivity extends AppCompatActivity {
    private EditText editTextHeight, editTextWeight;
    private RadioGroup radioGroupGoal;
    private RadioButton radioButtonLoseWeight, radioButtonGainWeight, radioButtonMaintainWeight;
    private Button buttonCalculateBMI;
    private TextView textViewBMIResult, textViewDietPlan, textViewPreviousRecord;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_calculator);

        // Initialize views
        editTextHeight = findViewById(R.id.editTextHeight);
        editTextWeight = findViewById(R.id.editTextWeight);
        radioGroupGoal = findViewById(R.id.radioGroupGoal);
        radioButtonLoseWeight = findViewById(R.id.radioButtonLoseWeight);
        radioButtonGainWeight = findViewById(R.id.radioButtonGainWeight);
        radioButtonMaintainWeight = findViewById(R.id.radioButtonMaintainWeight);
        buttonCalculateBMI = findViewById(R.id.buttonCalculateBMI);
        textViewBMIResult = findViewById(R.id.textViewBMIResult);
        textViewDietPlan = findViewById(R.id.textViewDietPlan);

        // Add a new TextView for previous record
        textViewPreviousRecord = findViewById(R.id.textViewPreviousRecord);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Check and display previous record
        displayPreviousRecord();

        buttonCalculateBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBMIAndDietPlan();
            }
        });
    }

    private void displayPreviousRecord() {
        if (databaseHelper.hasBMIRecords()) {
            Cursor cursor = databaseHelper.getLatestBMIRecord();

            if (cursor.moveToFirst()) {
                // Extract data from the cursor
                double height = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_HEIGHT));
                double weight = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_WEIGHT));
                double bmi = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_BMI));
                String goal = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GOAL));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE));

                // Format previous record text
                String previousRecordText = String.format(
                        "Previous Record:\nDate: %s\nHeight: %.1f cm\nWeight: %.1f kg\nBMI: %.1f\nGoal: %s\n\n",
                        date,
                        height,
                        weight,
                        bmi,
                        goal.replace("_", " ").toUpperCase()
                );

                // Get previous diet plan
                String[] dietPlanDetails = databaseHelper.getDietPlan(goal);
                if (dietPlanDetails[0] != null && dietPlanDetails[1] != null) {
                    previousRecordText += String.format(
                            "Recommended Daily Calories: %s\n\nMeal Plan:\n%s",
                            dietPlanDetails[0],
                            dietPlanDetails[1]
                    );
                }

                textViewPreviousRecord.setText(previousRecordText);
                textViewPreviousRecord.setVisibility(View.VISIBLE);
            }

            cursor.close();
        } else {
            textViewPreviousRecord.setVisibility(View.GONE);
        }
    }

    private void calculateBMIAndDietPlan() {
        // Get height and weight
        String heightStr = editTextHeight.getText().toString();
        String weightStr = editTextWeight.getText().toString();

        // Validate input
        if (heightStr.isEmpty() || weightStr.isEmpty()) {
            Toast.makeText(this, "Please enter height and weight", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if a goal is selected
        int selectedGoalId = radioGroupGoal.getCheckedRadioButtonId();
        if (selectedGoalId == -1) {
            Toast.makeText(this, "Please select a weight goal", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Convert inputs to double
            double height = Double.parseDouble(heightStr);
            double weight = Double.parseDouble(weightStr);

            // Calculate BMI
            double bmi = DatabaseHelper.calculateBMI(height, weight);

            // Determine user-selected weight goal
            String weightGoal;
            if (selectedGoalId == R.id.radioButtonLoseWeight) {
                weightGoal = "lose_weight";
            } else if (selectedGoalId == R.id.radioButtonGainWeight) {
                weightGoal = "gain_weight";
            } else {
                weightGoal = "maintain_weight";
            }

            // Insert BMI record
            long recordId = databaseHelper.insertBMIRecord(height, weight, bmi, weightGoal);

            // Display BMI result
            String bmiResultText = String.format("Your BMI: %.1f\nCategory: %s",
                    bmi,
                    getWeightCategory(bmi));
            textViewBMIResult.setText(bmiResultText);

            // Get diet plan based on user-selected goal
            String[] dietPlanDetails = databaseHelper.getDietPlan(weightGoal);

            // Display diet plan
            if (dietPlanDetails[0] != null && dietPlanDetails[1] != null) {
                String goalText = weightGoal.replace("_", " ").toUpperCase();
                String dietPlanText = String.format("Goal: %s\n\nRecommended Daily Calories: %s\n\nMeal Plan:\n%s",
                        goalText,
                        dietPlanDetails[0],
                        dietPlanDetails[1]);
                textViewDietPlan.setText(dietPlanText);
            }

            // Refresh previous record display
            displayPreviousRecord();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid height or weight", Toast.LENGTH_SHORT).show();
        }
    }

    // Helper method to get weight category
    private String getWeightCategory(double bmi) {
        if (bmi < 18.5) return "Underweight";
        if (bmi >= 18.5 && bmi < 25) return "Normal weight";
        if (bmi >= 25 && bmi < 30) return "Overweight";
        return "Obese";
    }
}