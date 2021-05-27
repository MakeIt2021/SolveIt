package org.makeitapps.solveit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class Stats extends AppCompatActivity {
TextView correctPerTxt, correctTxt, timeTxt;
int time;
float amount, correctAmount;
View everything;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);


        correctTxt = findViewById(R.id.totalCorrect);
        timeTxt = findViewById(R.id.totalSeconds);
        correctPerTxt = findViewById(R.id.totalPercent);
        everything = findViewById(R.id.everything);


        correctAmount = (getIntent().getIntExtra("correctAmount", 0));
        time = getIntent().getIntExtra("time", -1);
        amount = getIntent().getIntExtra("amount", 0);

        correctTxt.setText(String.valueOf((int) correctAmount));
        timeTxt.setText(String.valueOf(time));
        correctPerTxt.setText((int) (100 / amount * correctAmount) + " %");

        //Определение цвета для количества
        if (100 / amount * correctAmount >= 70) {
            correctTxt.setTextColor(Color.parseColor("#00FF38"));
            correctPerTxt.setTextColor(Color.parseColor("#00FF38"));
        }

        if (100 / amount * correctAmount < 70 && 100 / amount * correctAmount >= 40) {
            correctTxt.setTextColor(Color.parseColor("#FFE710"));
            correctPerTxt.setTextColor(Color.parseColor("#FFE710"));
        }
        if (100 / amount * correctAmount < 40){
            correctTxt.setTextColor(Color.parseColor("#FF0000"));
            correctPerTxt.setTextColor(Color.parseColor("#FF0000"));
        }

        //Определение цвета для секунд
        if (time / amount < 3)
            timeTxt.setTextColor(Color.parseColor("#00FF38"));
        if (time / amount >= 3 && time / amount <= 8)
            timeTxt.setTextColor(Color.parseColor("#FFE710"));
        if (time / amount > 8 || time == 0)
            timeTxt.setTextColor(Color.parseColor("#FF0000"));

    }


    //Транспорт детальных данных
    public void details(View view) {
        Intent intent = new Intent(this, Detailed.class);
        intent.putExtra("sumTime", getIntent().getIntExtra("sumTimeTransp", -1));
        intent.putExtra("subTime", getIntent().getIntExtra("subTimeTransp", -1));
        intent.putExtra("multTime", getIntent().getIntExtra("multTimeTransp", -1));
        intent.putExtra("devTime", getIntent().getIntExtra("devTimeTransp", -1));

        intent.putExtra("sumCorrect", getIntent().getIntExtra("sumCorrectTransp", 0));
        intent.putExtra("subCorrect", getIntent().getIntExtra("subCorrectTransp", 0));
        intent.putExtra("multCorrect", getIntent().getIntExtra("multCorrectTransp", 0));
        intent.putExtra("devCorrect", getIntent().getIntExtra("devCorrectTransp", 0));

        intent.putExtra("sumCounter", getIntent().getIntExtra("sumCounterTransp", 0));
        intent.putExtra("subCounter", getIntent().getIntExtra("subCounterTransp", 0));
        intent.putExtra("multCounter", getIntent().getIntExtra("multCounterTransp", 0));
        intent.putExtra("devCounter", getIntent().getIntExtra("devCounterTransp", 0));
        startActivity(intent);
        finish();
    }
}