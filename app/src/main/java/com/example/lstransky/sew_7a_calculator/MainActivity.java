package com.example.lstransky.sew_7a_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This class is the main activity for the app and reacts to events
 * @author Leonhard Stransky
 * @version 2023-10-09
 */

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, AdapterView.OnItemSelectedListener {

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

        // Added Spinner creation
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.operations_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
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
        String tag;
        // Chooses between RadioButtons and Spinner
        if (rg.getVisibility() == View.VISIBLE) {
            int id = rg.getCheckedRadioButtonId();
            tag = findViewById(id).getTag().toString();
        } else {
            Spinner spinner = findViewById(R.id.spinner);
            tag = spinner.getSelectedItem().toString();
        }
        // write log
        Log.d(LOG_TAG, "Value1: " + value1Double);
        Log.d(LOG_TAG, "Value2: " + value2Double);
        Log.d(LOG_TAG, "Tag: " + tag);
        // calculate
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
            default:
                Log.d(LOG_TAG, "Error: No Operator selected!");
                Toast.makeText(this, "Error: No Operator selected!", Toast.LENGTH_SHORT).show();
                return;
        }
        Log.d(LOG_TAG, "Result: " + value1Double);

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
        e.apply();
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

    // EKv:

    /**
     * Shows the popup menu when Options button is clicked
     * @param view the view
     */
    public void showPopup(View view){
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(this);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.main_menu, popup.getMenu());
        popup.show();
    }

    /**
     * Rersets all values.
     */
    public void resetValues(){
        Log.d(LOG_TAG, "In resetValues function.");
        EditText value1 = findViewById(R.id.value1);
        value1.setText("");
        EditText value2 = findViewById(R.id.value2);
        value2.setText("");
        TextView output = findViewById(R.id.textView_output);
        output.setText("0");
        output.setTextColor(Color.parseColor("#FFFFFF"));
        RadioGroup rg = findViewById(R.id.radioGroup);
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setSelection(0);
        rg.clearCheck();
    }

    /**
     * When an item is selected in the spinner
     * @param adapterView the adapterView
     * @param view the view
     * @param i the position
     * @param l the id
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d(LOG_TAG, "onItemSelected executed.");
        Log.d(LOG_TAG, "Item selected: " + adapterView.getItemAtPosition(i).toString());
        Log.d(LOG_TAG, "View: " + adapterView.getSelectedItem().toString());
        Log.d(LOG_TAG, "Id: " + adapterView.getId());
    }

    /**
     * When nothing is selected in the spinner
     * @param adapterView the adapterView
     */
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.d(LOG_TAG, "onNothingSelected executed.");
    }

    /**
     * When an item is clicked in the popup menu
     * @param menuItem the menuItem
     * @return true if clicked
     */
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        Log.d(LOG_TAG, "onMenuItemClick executed.");
        String title = menuItem.getTitle().toString();
        Log.d(LOG_TAG, "Title: " + title);
        switch (title) {
            case "Reset":
                resetValues();
                return true;
            case "About":
                Log.d(LOG_TAG, "In about");
                Toast.makeText(this, "© Leonhard Stransky; 30.12.2023", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }
}