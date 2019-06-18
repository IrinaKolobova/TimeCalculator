package com.example.timecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button button_add, button_subtract, button_calculate;
    private TextView textView_result;
    private TimePicker timePicker_startingTime, timePicker_amountToAdd;
    private String date, errorMessage, resultOfHours, resultOfMinutes;
    private int startingHour, startingMin, addHour, addMin, diffOfHours, diffOfMinutes;
    private boolean add, subtract;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_add = findViewById(R.id.button_add);
        button_subtract = findViewById(R.id.button_subtract);
        button_calculate = findViewById(R.id.button_calculate);
        textView_result = findViewById(R.id.textView_result);

        timePicker_startingTime = findViewById(R.id.timePicker_startingTime);
        timePicker_startingTime.setIs24HourView(true);
        timePicker_startingTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                startingHour = hourOfDay;
                startingMin = minute;
            }
        });

        timePicker_amountToAdd = findViewById(R.id.timePicker_amountToAdd);
        timePicker_amountToAdd.setIs24HourView(true);
        timePicker_amountToAdd.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                addHour = hourOfDay;
                addMin = minute;
            }
        });

        add = false;
        subtract = false;

    }

    public void add(View v){
        add = true;
    }

    public void subtract(View v){
        subtract = true;
    }

    public void armyTime(View v){}

    public void amPm(View v){}

    public void calculate (View v){
        date = "Current day";
        errorMessage = "";
        Log.i("Calculate incoming data: " ,"startingHour = " + startingHour + ", startingMin = " + startingMin +
                "\n" + "addHour = " + addHour + ", addMin = " + addMin );

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
        }else if(subtract){
            diffOfHours = startingHour - addHour;
            diffOfMinutes = startingMin - addMin;
            if(diffOfMinutes < 0){
                diffOfHours -= 1;
                diffOfMinutes = 60 + diffOfMinutes;
            }
            if(diffOfHours < 0){
                diffOfHours = 24 + diffOfHours;
                date = "Previous day";
            }
        } else {
            errorMessage = "Please choose add or subtract.";
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

        textView_result.setText(getString(R.string.textView_result, date, resultOfHours, resultOfMinutes));

        add = false;
        subtract = false;


    }
    
}
