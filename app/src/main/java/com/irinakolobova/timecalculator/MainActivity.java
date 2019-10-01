package com.irinakolobova.timecalculator;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout constraint_layout;
    private Button button_add, button_subtract, button_calculate, button_amPm, button_armyTime;
    private TextView textView_result;
    private TimePicker timePicker_startingTime, timePicker_amountToAdd;
    private String date, errorMessage, resultOfHours, resultOfMinutes;
    private int startingHour, startingMin, addHour, addMin, diffOfHours, diffOfMinutes;
    private boolean add, subtract, ampmFormat, armyTimeFormat;


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
        constraint_layout.setBackground(getDrawable(R.drawable.ampm_background));

        timePicker_startingTime = findViewById(R.id.timePicker_startingTime);
        timePicker_startingTime.setIs24HourView(false);
        startingHour = timePicker_startingTime.getHour();
        startingMin = timePicker_startingTime.getMinute();
        timePicker_startingTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                startingHour = hourOfDay;
                startingMin = minute;
                unselectAllButtons();
            }
        });

        timePicker_amountToAdd = findViewById(R.id.timePicker_amountToAdd);
        timePicker_amountToAdd.setIs24HourView(true);
        timePicker_amountToAdd.setHour(0);
        timePicker_amountToAdd.setMinute(0);
        timePicker_amountToAdd.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                addHour = hourOfDay;
                addMin = minute;
                button_calculate.setBackground(getDrawable(R.drawable.button_unselected));
                textView_result.setText(getString(R.string.textView_clear));
            }
        });
        unselectAllButtons();
        ampmFormat = true;

    }

    public void add(View v){
        selectButton(button_add);
        add = true;
    }

    public void subtract(View v){
        selectButton(button_subtract);
        subtract = true;
    }

    public void armyTime(View v){
        constraint_layout.setBackground(getDrawable(R.drawable.armytime_background));
        timePicker_startingTime.setIs24HourView(true);
        ampmFormat = false;
        unselectAllButtons();
    }

    public void amPm(View v){
        constraint_layout.setBackground(getDrawable(R.drawable.ampm_background));
        timePicker_startingTime.setIs24HourView(false);
        ampmFormat = true;
        unselectAllButtons();
    }

    public void calculate (View v){
        button_calculate.setBackground(getDrawable(R.drawable.button_selected));
        date = getResources().getString(R.string.date_current);
        errorMessage = "";

        Log.i("Calculate incoming data: " ,"startingHour = " + startingHour + ", startingMin = " + startingMin +
                "\n" + "addHour = " + addHour + ", addMin = " + addMin );

        //if add or subtract was chosen we can calculate
        //otherwise show error
        if(add || subtract){

            //addition
            if(add){
                diffOfHours = startingHour + addHour;
                diffOfMinutes = startingMin + addMin;
                //convert minutes to hours if more than 60 minutes
                if(diffOfMinutes >= 60){
                    diffOfMinutes = diffOfMinutes - 60;
                    diffOfHours += 1;
                }
                //change date to the next one if more than 24 hours
                if(diffOfHours >= 24){
                    date = getResources().getString(R.string.date_next);
                    diffOfHours = diffOfHours - 24;
                }

            //subtraction
            } else {
                diffOfHours = startingHour - addHour;
                diffOfMinutes = startingMin - addMin;
                //if difference of minutes is negative subtract one hour and convert it to minutes
                if (diffOfMinutes < 0) {
                    diffOfHours -= 1;
                    diffOfMinutes = 60 + diffOfMinutes;
                }
                //if difference of hours is negative change date to the previous one
                if (diffOfHours < 0) {
                    diffOfHours = 24 + diffOfHours;
                    date = getResources().getString(R.string.date_prev);
                }
            }
            resultOfHours = String.valueOf(diffOfHours);
            resultOfMinutes = String.valueOf(diffOfMinutes);
            //formatting output text
            if(diffOfHours < 10){
                resultOfHours = "0" + resultOfHours;
            }
            if(diffOfMinutes < 10){
                resultOfMinutes = "0" + resultOfMinutes;
            }

            Log.i("Calculate result data: ", "resultOfHours = " + resultOfHours + ", resultOfMinutes = " + resultOfMinutes);

            //converting to 12-hour format
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
        String amOrPM;
        String am = getResources().getString(R.string.am);
        String pm = getResources().getString(R.string.pm);

        int amPmHours = diffOfHours - 12;

        //it's PM if value amPmHours is positive
        if(amPmHours > 0) {
            resultOfHours = String.valueOf(amPmHours);
            amOrPM = pm;
        //adjust 00pm to 12pm
        } else if (amPmHours == 0) {
            amPmHours = 12;
            resultOfHours = String.valueOf(amPmHours);
            amOrPM = pm;
        //it's AM if value amPmHours is negative
        //adjust 00am to 12am
        } else if (amPmHours == -12) {
            amPmHours = 12;
            resultOfHours = String.valueOf(amPmHours);
            amOrPM = am;
        } else {
            resultOfHours = String.valueOf(diffOfHours);
            amOrPM = am;
        }

        textView_result.setText(getString(R.string.textView_result, date, resultOfHours, resultOfMinutes, amOrPM));

        Log.i("Calculate result data: ", "resultOfHours = " + resultOfHours + ", resultOfMinutes = " + resultOfMinutes + " " + amOrPM);

    }

    private void unselectAllButtons(){
        button_add.setBackground(getDrawable(R.drawable.button_unselected));
        button_subtract.setBackground(getDrawable(R.drawable.button_unselected));
        button_calculate.setBackground(getDrawable(R.drawable.button_unselected));
        add = false;
        subtract = false;
        textView_result.setText(getString(R.string.textView_clear));
    }

    private void selectButton(Button buttonToSelect){
        unselectAllButtons();
        buttonToSelect.setBackground(getDrawable(R.drawable.button_selected));
    }

}



