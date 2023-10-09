package com.example.lstransky.sew_7a_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This class is the main activity for the app and reacts to events
 * @author Leonhard Stransky
 * @version 2023-10-09
 */

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    /**
     * This Function gets executed when the app gets created
     * @param savedInstanceState When the activity got closed this is the saved state.
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView erg = findViewById(R.id.textView_output);
        erg.setOnTouchListener((View view, MotionEvent event) ->{
            TextView erg2 = findViewById(R.id.textView_output);
            erg2.setText("0");
            return true;
        });
    }

    /**
     * Calulates the result of the given values and the selected operation
     * @param view the view
     */
    public void calculate(View view) {
        // extract values
        EditText value1 = findViewById(R.id.value1);
        double value1Double = Double.parseDouble(value1.getText().toString());
        EditText value2 = findViewById(R.id.value2);
        double value2Double = Double.parseDouble(value2.getText().toString());
        RadioGroup rg = findViewById(R.id.radioGroup);
        int id = rg.getCheckedRadioButtonId();
        String tag = findViewById(id).getTag().toString();
        // write log
        Log.d(LOG_TAG, "Value1: " + value1Double);
        Log.d(LOG_TAG, "Value2: " + value2Double);
        Log.d(LOG_TAG, "Radiobutton: " + tag);
        // calculate
        if (id == -1) {
            switch(tag) {
                case "+":
                    value1Double += value2Double;
                    break;
                case "-":
                    value1Double -= value2Double;
                    break;
                case "*":
                    value1Double *= value2Double;
                    break;
                case "/":
                    value1Double /= value2Double;
                    break;
            }
            Log.d(LOG_TAG, "Result: " + value1Double);
        }
        else {
            Log.d(LOG_TAG, "No Radiobutton selected");
        }
        // display result
        TextView output = findViewById(R.id.textView_output);
        if(String.valueOf(value1Double).length() >9)
            output.setText(String.valueOf(value1Double).substring(0,9)+"...");
        else
            output.setText(String.valueOf(value1Double));
        // Set colors
        if(value1Double<0)
            output.setTextColor(Color.parseColor("#D32F2F"));
        else{
            if(value1Double == 0)
                output.setTextColor(Color.parseColor("#FFFFFF"));
            else
                output.setTextColor(Color.parseColor("#000000"));
        }
    }

    // Extra Functionality 1 (NR. 13)

    /**
     * Activate the RadioButtons
     */
    @Override
    public void onStart(){
        super.onStart();

        /*
        RadioButton rb = findViewById(R.id.radioButton_plus);
        rb.setClickable(true);
        rb = findViewById(R.id.radioButton_minus);
        rb.setClickable(true);
        rb = findViewById(R.id.radioButton_multiplication);
        rb.setClickable(true);
        rb = findViewById(R.id.radioButton_division);
        rb.setClickable(true);
        */

        RadioGroup rg = findViewById(R.id.radioGroup);
        for (int i = 0; i < rg.getChildCount(); i++) {
            rg.getChildAt(i).setEnabled(true);
            rg.getChildAt(i).setClickable(true);
        }
    }

    // Extra Functionality 2 (NR. 13)

    /**
     * Stores the result in memory
     * @param view the view
     */
    public void memoryStore(View view) {
        SharedPreferences sh = getSharedPreferences("MS", MODE_PRIVATE);
        SharedPreferences.Editor e = sh.edit();
        EditText value1 = findViewById(R.id.value1);
        EditText value2 = findViewById(R.id.value2);
        RadioGroup rg = findViewById(R.id.radioGroup);
        int id = rg.getCheckedRadioButtonId();
        if (value1.getText().toString().equals("") || value2.getText().toString().equals("") || id == -1) {
            Toast.makeText(this, "Error. EditText empty or no Radio Button selected!", Toast.LENGTH_SHORT).show();
            return;
        }
        e.putString("value1", value1.getText().toString());
        e.putString("value2", value2.getText().toString());
        e.putInt("id", id);
        Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * Reads the result from memory
     * @param view the view
     */
    public void memoryRead(View view) {
        SharedPreferences sh = getSharedPreferences("MS", MODE_PRIVATE);
        EditText value1 = findViewById(R.id.value1);
        EditText value2 = findViewById(R.id.value2);
        RadioGroup rg = findViewById(R.id.radioGroup);
        value1.setText(sh.getString("value1", ""));
        value2.setText(sh.getString("value2", ""));
        rg.check(sh.getInt("id", -1));
        Toast.makeText(this, "Data Loaded", Toast.LENGTH_SHORT).show();
    }
}