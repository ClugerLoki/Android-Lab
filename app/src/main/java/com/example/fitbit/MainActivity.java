package com.example.fitbit;

//import static android.os.Build.VERSION_CODES.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button button1, button2, buttonOpenTextStyle, btnOpenScheduler, openBMICalculator, openCalorieCalculator;
    TextView mLogOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View yourView = findViewById(R.id.startyoga1); // Replace with actual View ID
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.up);
        yourView.startAnimation(animation);

        View yourView2 = findViewById(R.id.startyoga2); // Replace with actual View ID
        Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.up);
        yourView2.startAnimation(animation2);

        View yourView3 = findViewById(R.id.buttonOpenTextStyle); // Replace with actual View ID
        Animation animation3 = AnimationUtils.loadAnimation(this, R.anim.up);
        yourView3.startAnimation(animation3);

        View yourView4 = findViewById(R.id.buttonOpenTextStyle); // Replace with actual View ID
        Animation animation4 = AnimationUtils.loadAnimation(this, R.anim.up);
        yourView4.startAnimation(animation4);

        View yourView5 = findViewById(R.id.yoganotification); // Replace with actual View ID
        Animation animation5 = AnimationUtils.loadAnimation(this, R.anim.up);
        yourView5.startAnimation(animation5);

        View yourView6 = findViewById(R.id.buttonBMI); // Replace with actual View ID
        Animation animation6 = AnimationUtils.loadAnimation(this, R.anim.up);
        yourView6.startAnimation(animation5);


        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        button1 = findViewById(R.id.startyoga1);
        button2 = findViewById(R.id.startyoga2);
        buttonOpenTextStyle = findViewById(R.id.buttonOpenTextStyle);
        openBMICalculator = findViewById(R.id.buttonBMI);
        openCalorieCalculator = findViewById(R.id.buttonCalorie);
        mLogOutBtn = findViewById(R.id.logoutText);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SecondActivity2.class);
                startActivity(intent);
            }
        });

        buttonOpenTextStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TextStyleActivity.class);
                startActivity(intent);
            }
        });

        btnOpenScheduler = findViewById(R.id.yoganotification); // Change to your actual button ID

        btnOpenScheduler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotificationSchedulerActivity.class);
                startActivity(intent);
            }
        });

        openBMICalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalorieCalculatorActivity.class);
                startActivity(intent);
            }
        });

        openCalorieCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalorieActivity.class);
                startActivity(intent);
            }
        });

        mLogOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }

    //private void setSupportActionBar(Toolbar toolbar) {
    //}

    public void helth(View view){
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(intent);
    }

    public void fitness(View view){
        Intent intent = new Intent(MainActivity.this, SecondActivity2.class);
        startActivity(intent);
    }

    public void concentrtion(View view){
        Intent intent = new Intent(MainActivity.this, TextStyleActivity.class);
        startActivity(intent);
    }

    public void notification(View view){
        Intent intent = new Intent(MainActivity.this, NotificationSchedulerActivity.class);
        startActivity(intent);
    }

    public void bmi(View view){
        Intent intent = new Intent(MainActivity.this, CalorieCalculatorActivity.class);
        startActivity(intent);
    }
    public void calorie(View view){
        Intent intent = new Intent(MainActivity.this, CalorieActivity.class);
        startActivity(intent);
    }

    public void mLogOutBtn(View view){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }

}