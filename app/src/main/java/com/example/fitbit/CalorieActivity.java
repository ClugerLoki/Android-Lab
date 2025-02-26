package com.example.fitbit;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import java.util.Map;

public class CalorieActivity extends AppCompatActivity {

    private Spinner foodSpinner;
    private EditText quantityInput;
    private Button addButton, calculateButton;
    private TextView resultTextView, recommendationTextView;
    private RadioGroup goalGroup;

    // Food calorie data (calories per 100g)
    private final Map<String, Integer> foodCalories = new HashMap<>();
    private int totalCalories = 0; // To track total intake

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie);

        // Initializing UI elements
        foodSpinner = findViewById(R.id.foodSpinner);
        quantityInput = findViewById(R.id.quantityInput);
        addButton = findViewById(R.id.addButton);
        calculateButton = findViewById(R.id.calculateButton);
        resultTextView = findViewById(R.id.resultTextView);
        recommendationTextView = findViewById(R.id.recommendationTextView);
        goalGroup = findViewById(R.id.goalGroup);

        // Adding food items and their calories per 100g
        foodCalories.put("Apple", 52);
        foodCalories.put("Banana", 89);
        foodCalories.put("Rice", 130);
        foodCalories.put("Chicken", 239);
        foodCalories.put("Egg", 155);
        foodCalories.put("Milk", 42);
        foodCalories.put("Bread", 265);
        foodCalories.put("Cheese", 402);
        foodCalories.put("Butter", 717);
        foodCalories.put("Oats", 389);
        foodCalories.put("Potato", 77);
        foodCalories.put("Tomato", 18);
        foodCalories.put("Carrot", 41);
        foodCalories.put("Onion", 40);
        foodCalories.put("Fish (Salmon)", 208);
        foodCalories.put("Fish (Tuna)", 132);
        foodCalories.put("Almonds", 579);
        foodCalories.put("Peanuts", 567);
        foodCalories.put("Walnuts", 654);
        foodCalories.put("Avocado", 160);
        foodCalories.put("Broccoli", 55);
        foodCalories.put("Cucumber", 16);
        foodCalories.put("Spinach", 23);
        foodCalories.put("Mango", 60);
        foodCalories.put("Pineapple", 50);
        foodCalories.put("Strawberry", 32);
        foodCalories.put("Yogurt", 59);
        foodCalories.put("Lentils", 116);
        foodCalories.put("Pasta", 131);
        foodCalories.put("Corn", 96);
        foodCalories.put("Dark Chocolate", 546);
        foodCalories.put("Honey", 304);
        foodCalories.put("Ice Cream", 207);
        foodCalories.put("Coca Cola", 42);
        foodCalories.put("Orange", 47);
        foodCalories.put("Grapes", 69);
        foodCalories.put("Cashew", 553);
        foodCalories.put("Coconut", 354);
        foodCalories.put("Peas", 81);
        foodCalories.put("Mushrooms", 22);
        foodCalories.put("Soybeans", 446);
        foodCalories.put("Tofu", 144);

// North Indian Foods
        foodCalories.put("Chapati", 297);
        foodCalories.put("Paratha", 322);
        foodCalories.put("Aloo Paratha", 322);
        foodCalories.put("Puri", 350);
        foodCalories.put("Bhature", 412);
        foodCalories.put("Paneer Butter Masala", 320);
        foodCalories.put("Dal Makhani", 305);
        foodCalories.put("Chole (Chickpea Curry)", 180);
        foodCalories.put("Rajma (Kidney Bean Curry)", 140);
        foodCalories.put("Butter Chicken", 490);
        foodCalories.put("Tandoori Chicken", 250);
        foodCalories.put("Matar Paneer", 260);
        foodCalories.put("Palak Paneer", 250);
        foodCalories.put("Kadhi Pakora", 170);
        foodCalories.put("Baingan Bharta", 120);
        foodCalories.put("Lassi", 150);
        foodCalories.put("Gulab Jamun", 175);
        foodCalories.put("Jalebi", 150);
        foodCalories.put("Kheer", 120);
        foodCalories.put("Samosa", 262);
        foodCalories.put("Pakora", 260);
        foodCalories.put("Dhokla", 160);

// South Indian Foods
        foodCalories.put("Idli", 39);
        foodCalories.put("Dosa", 168);
        foodCalories.put("Masala Dosa", 250);
        foodCalories.put("Uttapam", 150);
        foodCalories.put("Pongal", 222);
        foodCalories.put("Upma", 200);
        foodCalories.put("Rava Idli", 110);
        foodCalories.put("Vada", 250);
        foodCalories.put("Sambar", 60);
        foodCalories.put("Rasam", 30);
        foodCalories.put("Curd Rice", 130);
        foodCalories.put("Lemon Rice", 180);
        foodCalories.put("Tomato Rice", 190);
        foodCalories.put("Vegetable Biryani", 220);
        foodCalories.put("Chicken Biryani", 290);
        foodCalories.put("Mutton Biryani", 320);
        foodCalories.put("Fish Curry", 180);
        foodCalories.put("Prawn Curry", 150);
        foodCalories.put("Coconut Chutney", 150);
        foodCalories.put("Peanut Chutney", 160);
        foodCalories.put("Banana Chips", 540);
        foodCalories.put("Mysore Pak", 400);
        foodCalories.put("Payasam", 120);
        foodCalories.put("Filter Coffee", 40);


        // Populate the Spinner with food items
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, foodCalories.keySet().toArray(new String[0]));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        foodSpinner.setAdapter(adapter);

        // Add food button click listener
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFood();
            }
        });

        // Calculate button click listener
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCalories();
            }
        });
    }

    private void addFood() {
        String selectedFood = foodSpinner.getSelectedItem().toString();
        String quantityStr = quantityInput.getText().toString();

        if (!quantityStr.isEmpty()) {
            int quantity = Integer.parseInt(quantityStr);
            int caloriesPer100g = foodCalories.get(selectedFood);
            int calories = (quantity * caloriesPer100g) / 100; // Calculate total calories for entered weight
            totalCalories += calories;
            resultTextView.append(selectedFood + " (" + quantity + "g): " + calories + " kcal\n");
        } else {
            Toast.makeText(this, "Please enter a valid quantity", Toast.LENGTH_SHORT).show();
        }
    }

    private void calculateCalories() {
        if (totalCalories == 0) {
            Toast.makeText(this, "Please add some food items first!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get selected goal
        int selectedGoal = goalGroup.getCheckedRadioButtonId();
        String recommendation;

        if (selectedGoal == R.id.loseWeight) {
            recommendation = "To lose weight, aim for 1500-1800 kcal/day.";
        } else if (selectedGoal == R.id.gainWeight) {
            recommendation = "To gain weight, aim for 2500-3000 kcal/day.";
        } else {
            recommendation = "For maintenance, aim for around 2000-2200 kcal/day.";
        }

        recommendationTextView.setText("Total Calories: " + totalCalories + " kcal\n" + recommendation);
    }
}
