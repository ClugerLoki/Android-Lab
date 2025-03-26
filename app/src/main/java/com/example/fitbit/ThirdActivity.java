package com.example.fitbit;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ThirdActivity extends AppCompatActivity {

    String buttonvalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Intent intent = getIntent();
        buttonvalue = intent.getStringExtra("value");


        int intvalue = Integer.valueOf(buttonvalue);

        switch (intvalue) {
            case 1:
                setContentView(R.layout.activity_tadasana);
                break;
            case 2:
                setContentView(R.layout.activity_vrikshasana);
                break;
            case 3:
                setContentView(R.layout.activity_vajrasana);
                break;
            case 4:
                setContentView(R.layout.activity_uttanapapadasana);
                break;
            case 5:
                setContentView(R.layout.activity_pawanamuktasana);
                break;
            case 6:
                setContentView(R.layout.activity_kapalabharti);
                break;
            case 7:
                setContentView(R.layout.activity_anulomaviloma);
                break;
            case 8:
                setContentView(R.layout.activity_boat_pose);
                break;
        }
    }
}