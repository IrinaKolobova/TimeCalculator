package com.example.timecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button button_add, button_subtract, button_calculate;
    private TextView textView_result;
    private TimePicker timePicker_startingTime, timePicker_amountToAdd;
    private String startingTime, amountToAdd, resultOfHours, resultOfMinutes, date;
    private long diff;
    private int startingHour, startingMin, addHour, addMin;
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

    public void calculate (View v){
        date = "current day";
        Toast.makeText(getApplicationContext(), "startingHour = " + startingHour + " startingMin = " + startingMin +
                "\n" + "addHour = " + addHour + " addMin = " + addMin , Toast.LENGTH_SHORT).show();

    }


}
