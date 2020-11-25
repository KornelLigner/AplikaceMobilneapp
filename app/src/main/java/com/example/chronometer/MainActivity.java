package com.example.chronometer;

import android.os.SystemClock;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.widget.Button;
import java.util.Random;
import android.annotation.TargetApi;
import android.os.Build;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    //dla wyswietlania daty
    @TargetApi(Build.VERSION_CODES.O)
    //dla zmieniania koloru tła
    View screenView;
    Button przycisk;
    int[] color;

    private Chronometer czas;
    private boolean isworking;
    private long x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //fragment kodu dla wyświetlania aktualnej daty, godziny oraz strefy
        TextView textView=findViewById(R.id.date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd\n'at' HH:mm:ss\nz");
        String currentDateandTime = sdf.format(new Date());
        textView.setText(currentDateandTime);

        //Wybór możliwych kolorów tła do wyświetlenia
        color = new int[] {Color.CYAN, Color.MAGENTA, Color.GREEN, Color.YELLOW, Color.BLUE};
        screenView =  findViewById(R.id.rView);
        przycisk = (Button) findViewById(R.id.button);
        przycisk.setOnClickListener(new View.OnClickListener() {

            //zmienianie koloru tła za pomocą funkcji random(losowy wybór z wyżej wymienionych)
            @Override
            public void onClick(View view) {
                int aryLength = color.length;
                Random random = new Random();
                int rNum = random.nextInt(aryLength);
                screenView.setBackgroundColor(color[rNum]);
            }
        });

        czas = findViewById(R.id.chronometer);
        czas.setFormat("Czas: %s");
        czas.setBase(SystemClock.elapsedRealtime());
        czas.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {


            @Override
            //wyświetlanie komunikatu o upłyniętej minucie
            public void onChronometerTick(Chronometer chronometer) {
                if ((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 60000) {
                    Toast.makeText(MainActivity.this, "Minęła minuta.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Startowanie czasu
    public void startujOdliczanie(View v) {
        if (!isworking) {
            czas.setBase(SystemClock.elapsedRealtime() - x);
            czas.start();
            isworking = true;
        }
    }

    //Pauzowanie czasu
    public void pausujOdliczanie(View v) {
        if (isworking) {
            czas.stop();
            x = SystemClock.elapsedRealtime() - czas.getBase();
            isworking = false;
        }
    }

    //Resetowanie czasu, ustawianie na 0
    public void resetujOdliczanie(View v) {
        czas.setBase(SystemClock.elapsedRealtime());
        x = 0;
    }
}