package com.erikriosetiawan.stopwatch;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView textViewCounter;
    Button buttonStart, buttonPause, buttonReset, buttonSave;
    long milliSecondTime, startTime, timeBuff, updateTime = 0L;
    Handler handler;
    int seconds, minutes, milliSeconds;
    ListView listViewSave;
    String[] listElements = new String[]{};
    List<String> listElementsArrayList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewCounter = findViewById(R.id.text_view_counter);
        buttonStart = findViewById(R.id.button_start);
        buttonPause = findViewById(R.id.button_stop);
        buttonReset = findViewById(R.id.button_reset);
        buttonSave = findViewById(R.id.button_save);
        listViewSave = findViewById(R.id.list_view_save);

        handler = new Handler();

        listElementsArrayList = new ArrayList<String>(Arrays.asList(listElements));

        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, listElementsArrayList);

        listViewSave.setAdapter(adapter);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                buttonReset.setEnabled(false);
            }
        });

        buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeBuff += milliSecondTime;
                handler.removeCallbacks(runnable);
                buttonReset.setEnabled(true);
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                milliSecondTime = 0L;
                startTime = 0L;
                timeBuff = 0L;
                updateTime = 0L;
                seconds = 0;
                minutes = 0;
                milliSeconds = 0;

                textViewCounter.setText("00:00:00");

                listElementsArrayList.clear();

                adapter.notifyDataSetChanged();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listElementsArrayList.add(textViewCounter.getText().toString());

                adapter.notifyDataSetChanged();
            }
        });

    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            milliSecondTime = SystemClock.uptimeMillis() - startTime;

            updateTime = timeBuff + milliSecondTime;

            seconds = (int) (updateTime / 1000);
            minutes = seconds / 60;
            seconds = seconds % 60;
            milliSeconds = (int) (updateTime % 1000);

            textViewCounter.setText("" + minutes + ":" +
                    String.format("%02d", seconds) + ":"
                    + String.format("%03d", milliSeconds));

            handler.postDelayed(this, 0);
        }
    };
}
