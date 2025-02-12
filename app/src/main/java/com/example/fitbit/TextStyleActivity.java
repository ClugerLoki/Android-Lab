package com.example.fitbit;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TextStyleActivity extends AppCompatActivity {

    private EditText editText;
    private TextView label;
    private Spinner colorSpinner, fontSpinner;
    private SeekBar sizeSeekBar;
    private Button applyButton;

    private final String[] colors = {"Black", "Red", "Green", "Blue", "Purple", "Orange"};
    private final String[] fonts = {"DEFAULT", "MONOSPACE", "SERIF", "SANS_SERIF"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_style);

        editText = findViewById(R.id.editText);
        label = findViewById(R.id.label);
        colorSpinner = findViewById(R.id.colorSpinner);
        fontSpinner = findViewById(R.id.fontSpinner);
        sizeSeekBar = findViewById(R.id.sizeSeekBar);
        applyButton = findViewById(R.id.applyButton);

        ArrayAdapter<String> colorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, colors);
        colorSpinner.setAdapter(colorAdapter);

        ArrayAdapter<String> fontAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, fonts);
        fontSpinner.setAdapter(fontAdapter);

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyTextStyle();
            }
        });
    }

    private void applyTextStyle() {
        String text = editText.getText().toString();
        String selectedColor = colorSpinner.getSelectedItem().toString();
        String selectedFont = fontSpinner.getSelectedItem().toString();
        int textSize = sizeSeekBar.getProgress() + 12; // Minimum size 12sp

        label.setText(text);
        label.setTextSize(textSize);

        switch (selectedColor) {
            case "Red":
                label.setTextColor(Color.RED);
                break;
            case "Green":
                label.setTextColor(Color.GREEN);
                break;
            case "Blue":
                label.setTextColor(Color.BLUE);
                break;
            case "Purple":
                label.setTextColor(Color.MAGENTA);
                break;
            case "Orange":
                label.setTextColor(Color.rgb(255, 165, 0));
                break;
            default:
                label.setTextColor(Color.BLACK);
        }

        switch (selectedFont) {
            case "MONOSPACE":
                label.setTypeface(Typeface.MONOSPACE);
                break;
            case "SERIF":
                label.setTypeface(Typeface.SERIF);
                break;
            case "SANS_SERIF":
                label.setTypeface(Typeface.SANS_SERIF);
                break;
            default:
                label.setTypeface(Typeface.DEFAULT);
        }
    }
}