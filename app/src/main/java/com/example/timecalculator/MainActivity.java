package com.example.timecalculator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout constraint_layout;
    private Button button_add, button_subtract, button_calculate, button_amPm, button_armyTime;
    private TextView textView_result;
    private TimePicker timePicker_startingTime, timePicker_amountToAdd;
    private String date, errorMessage, resultOfHours, resultOfMinutes;
    private int startingHour, startingMin, addHour, addMin, diffOfHours, diffOfMinutes;
    private boolean add, subtract, ampmFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        constraint_layout = findViewById(R.id.constraint_layout);
        button_add = findViewById(R.id.button_add);
        button_subtract = findViewById(R.id.button_subtract);
        button_calculate = findViewById(R.id.button_calculate);
        button_amPm = findViewById(R.id.button_amPm);
        button_armyTime = findViewById(R.id.button_armyTime);
        textView_result = findViewById(R.id.textView_result);

        timePicker_startingTime = findViewById(R.id.timePicker_startingTime);
        timePicker_startingTime.setIs24HourView(true);
        startingHour = timePicker_startingTime.getHour();
        startingMin = timePicker_startingTime.getMinute();
        timePicker_startingTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                startingHour = hourOfDay;
                startingMin = minute;
            }
        });

        timePicker_amountToAdd = findViewById(R.id.timePicker_amountToAdd);
        timePicker_amountToAdd.setIs24HourView(true);
        timePicker_amountToAdd.setHour(0);
        timePicker_amountToAdd.setMinute(0);
        startingHour = timePicker_startingTime.getHour();
        startingMin = timePicker_startingTime.getMinute();
        timePicker_amountToAdd.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                addHour = hourOfDay;
                addMin = minute;
            }
        });
        button_add.setBackground(getDrawable(R.drawable.button_unselected));
        button_subtract.setBackground(getDrawable(R.drawable.button_unselected));
        add = false;
        subtract = false;
        ampmFormat = false;

    }

    public void add(View v){
        button_add.setBackground(getDrawable(R.drawable.button_selected));
        button_subtract.setBackground(getDrawable(R.drawable.button_unselected));
        add = true;
        subtract = false;
        textView_result.setText(getString(R.string.textView_clear));
    }

    public void subtract(View v){
        button_subtract.setBackground(getDrawable(R.drawable.button_selected));
        button_add.setBackground(getDrawable(R.drawable.button_unselected));
        subtract = true;
        add = false;
        textView_result.setText(getString(R.string.textView_clear));
    }

    public void armyTime(View v){
        constraint_layout.setBackground(getDrawable(R.drawable.armytime_background));
        timePicker_startingTime.setIs24HourView(true);
        ampmFormat = false;
        textView_result.setText(getString(R.string.textView_clear));
        button_add.setBackground(getDrawable(R.drawable.button_unselected));
        button_subtract.setBackground(getDrawable(R.drawable.button_unselected));
        add = false;
        subtract = false;
    }

    public void amPm(View v){
        constraint_layout.setBackground(getDrawable(R.drawable.ampm_background));
        timePicker_startingTime.setIs24HourView(false);
        ampmFormat = true;
        textView_result.setText(getString(R.string.textView_clear));
        button_add.setBackground(getDrawable(R.drawable.button_unselected));
        button_subtract.setBackground(getDrawable(R.drawable.button_unselected));
        add = false;
        subtract = false;
    }

    public void calculate (View v){
        date = "Current day";
        errorMessage = "";
        Log.i("Calculate incoming data: " ,"startingHour = " + startingHour + ", startingMin = " + startingMin +
                "\n" + "addHour = " + addHour + ", addMin = " + addMin );

        if(add || subtract){
            if(add){
                diffOfHours = startingHour + addHour;
                diffOfMinutes = startingMin + addMin;
                if(diffOfMinutes >= 60){
                    diffOfMinutes = diffOfMinutes - 60;
                    diffOfHours += 1;
                }
                if(diffOfHours >= 24){
                    date = "Next day";
                    diffOfHours = diffOfHours - 24;
                }
            } else {
                diffOfHours = startingHour - addHour;
                diffOfMinutes = startingMin - addMin;
                if (diffOfMinutes < 0) {
                    diffOfHours -= 1;
                    diffOfMinutes = 60 + diffOfMinutes;
                }
                if (diffOfHours < 0) {
                    diffOfHours = 24 + diffOfHours;
                    date = "Previous day";
                }
            }
            resultOfHours = String.valueOf(diffOfHours);
            resultOfMinutes = String.valueOf(diffOfMinutes);

            if(diffOfHours < 10){
                resultOfHours = "0" + resultOfHours;
            }
            if(diffOfMinutes < 10){
                resultOfMinutes = "0" + resultOfMinutes;
            }

            Log.i("Calculate result data: ", "resultOfHours = " + resultOfHours + ", resultOfMinutes = " + resultOfMinutes);

            if(ampmFormat){
                calculateAmPM(diffOfHours);
            } else {
                textView_result.setText(getString(R.string.textView_result, date, resultOfHours, resultOfMinutes, ""));
            }

        } else {
            errorMessage = "Please choose add or subtract.";
            Log.i("Calculate result data: ", "Error");
            textView_result.setText(getString(R.string.textView_err, errorMessage));
        }
    }

    private void calculateAmPM(int diffOfHours) {
        int amPmHours = diffOfHours - 12;
        String amOrPM;
        if(amPmHours >= 0 ){
            //&& amPmHours != 12
            resultOfHours = String.valueOf(amPmHours);
            amOrPM = "PM";
        } else if (amPmHours == -12) {
            amPmHours = 12;
            resultOfHours = String.valueOf(amPmHours);
            amOrPM = "AM";
        } else {
            resultOfHours = String.valueOf(diffOfHours);
            amOrPM = "AM";
        }

        textView_result.setText(getString(R.string.textView_result, date, resultOfHours, resultOfMinutes, amOrPM));

        Log.i("Calculate result data: ", "resultOfHours = " + resultOfHours + ", resultOfMinutes = " + resultOfMinutes + " " + amOrPM);

    }

}



