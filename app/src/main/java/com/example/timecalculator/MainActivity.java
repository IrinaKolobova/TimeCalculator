package com.example.timecalculator;

import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    EditText editText_startingTime, editText_amountToAdd;
    Button button_add, button_subtract, button_calculate;
    TextView textView_result;
    String startingTime, amountToAdd, resultOfHours, resultOfMinutes, date;
    long diff;
    boolean add, substract;
    DateFormat formatter = new SimpleDateFormat("HH:mm");


    //https://www.youtube.com/watch?v=Vxiy_h5hNII
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText_startingTime = findViewById(R.id.editText_startingTime);
        editText_amountToAdd = findViewById(R.id.editText_amountToAdd);
        button_add = findViewById(R.id.button_add);
        button_subtract = findViewById(R.id.button_subtract);
        button_calculate = findViewById(R.id.button_calculate);
        textView_result = findViewById(R.id.textView_result);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        add = false;
        substract = false;

    }

    public void add(View v){
        add = true;
    }

    public void subtract(View v){
        substract = true;
    }

    public void calculate (View v){
        startingTime = String.valueOf(editText_startingTime.getText());
        amountToAdd = String.valueOf(editText_amountToAdd.getText());
        date = "current day";

        try {
            Date date_startingTime = formatter.parse(startingTime);
            Date date_amountToAdd = formatter.parse(amountToAdd);
            if(add){
                diff = date_startingTime.getTime() + date_amountToAdd.getTime();
            } else if(substract){
                diff = date_startingTime.getTime() - date_amountToAdd.getTime();
            }

            if(diff>=86400000){
                date = "next day";
            }

            if(diff<0)
            {
                Date dateMax = formatter.parse("24:00");
                Date dateMin = formatter.parse("00:00");
                long dateMaxLong = dateMax.getTime();
                diff = dateMaxLong + diff;
                date = "previous day";
                Log.i("Test","dateMaxLong: " + dateMaxLong + ", diff: " + diff);
            }

            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffMins = diff  / (60 * 1000) % 60;
            Log.i("Test","Hours: "+diffHours+", Min: " + diffMins);
            if(diffHours<10){
                resultOfHours = "0" + String.valueOf(diffHours);
            } else{
                resultOfHours = String.valueOf(diffHours);
            }
            if(diffMins<10){
                resultOfMinutes = "0" + String.valueOf(diffMins);
            } else {
                resultOfMinutes = String.valueOf(diffMins);
            }
            add = false;
            substract = false;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        textView_result.setText(date +"\n" + resultOfHours + ":" + resultOfMinutes);

    }
}
