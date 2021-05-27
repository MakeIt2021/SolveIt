package org.makeitapps.solveit;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class Detailed extends AppCompatActivity {
    TextView sumTimeTxt, subTimeTxt, multTimeTxt, devTimeTxt, sumPerTxt, subPerTxt, multPerTxt, devPerTxt, sumCorrectTxt, subCorrectTxt, multCorrectTxt, devCorrectTxt, sumTxt, subTxt, multTxt, devTxt;
    int sumTime, subTime, multTime, devTime, counter, correct, sumPer, subPer, multPer, devPer, sumCorrect, subCorrect, multCorrect, devCorrect, sumCounter, subCounter, multCounter, devCounter, amount;
    View everything;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        //Нахождение всех TextView
        sumTimeTxt = findViewById(R.id.sumSec);
        subTimeTxt = findViewById(R.id.subSec);
        multTimeTxt = findViewById(R.id.multSec);
        devTimeTxt = findViewById(R.id.devSec);
        sumPerTxt = findViewById(R.id.sumPer);
        subPerTxt = findViewById(R.id.subPer);
        multPerTxt = findViewById(R.id.multPer);
        devPerTxt = findViewById(R.id.devPer);
        sumCorrectTxt = findViewById(R.id.sumCorrect);
        subCorrectTxt = findViewById(R.id.subCorrect);
        multCorrectTxt = findViewById(R.id.multCorrect);
        devCorrectTxt = findViewById(R.id.devCorrect);
        sumTxt = findViewById(R.id.sumTxt);
        subTxt = findViewById(R.id.subTxt);
        multTxt = findViewById(R.id.multTxt);
        devTxt = findViewById(R.id.devTxt);
        everything = findViewById(R.id.everything);



        //Получение значений из Solving.java
        sumTime = getIntent().getIntExtra("sumTime", 0);
        subTime = getIntent().getIntExtra("subTime", 0);
        multTime = getIntent().getIntExtra("multTime", 0);
        devTime = getIntent().getIntExtra("devTime", 0);
        sumCorrect = getIntent().getIntExtra("sumCorrect", 0);
        subCorrect = getIntent().getIntExtra("subCorrect", 0);
        multCorrect = getIntent().getIntExtra("multCorrect", 0);
        devCorrect = getIntent().getIntExtra("devCorrect", 0);
        sumCounter = getIntent().getIntExtra("sumCounter", 0);
        subCounter = getIntent().getIntExtra("subCounter", 0);
        multCounter = getIntent().getIntExtra("multCounter", 0);
        devCounter = getIntent().getIntExtra("devCounter", 0);
        counter = getIntent().getIntExtra("counter", 0);
        correct = getIntent().getIntExtra("correct", 0);
        amount = getIntent().getIntExtra("amount", 0);

        try {
            if (sumCounter == sumCorrect && sumCounter != 0) {
                sumPer = 100;
            } else{
                sumPer = 100 / sumCounter * sumCorrect;
            }
        } catch (ArithmeticException e) {}

        try {
            if (subCounter == subCorrect && subCounter != 0) {
                subPer = 100;
            } else {
                subPer = 100 / subCounter * subCorrect;
            }
        } catch (ArithmeticException e) {}

        try {
            if (multCounter == multCorrect && multCounter != 0) {
                multPer = 100;
            } else{
                multPer = 100 / multCounter * multCorrect;
            }
        } catch (ArithmeticException e) {}

        try {
            if (devCounter == devCorrect && devCounter != 0) {
                devPer = 100;
            } else{
                devPer = 100 / devCounter * devCorrect;
            }
        } catch (ArithmeticException e) {}

        sumTimeTxt.setText(String.valueOf(sumTime));
        subTimeTxt.setText(String.valueOf(subTime));
        multTimeTxt.setText(String.valueOf(multTime));
        devTimeTxt.setText(String.valueOf(devTime));


        //Отображение количества правильных
        sumCorrectTxt.setText(String.valueOf(sumCorrect));
        subCorrectTxt.setText(String.valueOf(subCorrect));
        multCorrectTxt.setText(String.valueOf(multCorrect));
        devCorrectTxt.setText(String.valueOf(devCorrect));

        //Отображение процентов
        sumPerTxt.setText(String.valueOf(sumPer));
        subPerTxt.setText(String.valueOf(subPer));
        multPerTxt.setText(String.valueOf(multPer));
        devPerTxt.setText(String.valueOf(devPer));

        if (((Integer.parseInt((String) (sumCorrectTxt.getText())) > 0 && Integer.parseInt((String) (sumPerTxt.getText())) == 0) || (Integer.parseInt((String) (subCorrectTxt.getText())) > 0 && Integer.parseInt((String) (subPerTxt.getText())) == 0) || (Integer.parseInt((String) (multCorrectTxt.getText())) > 0 && Integer.parseInt((String) (multPerTxt.getText())) == 0) || (Integer.parseInt((String) (devCorrectTxt.getText())) > 0 && Integer.parseInt((String) (devPerTxt.getText())) == 0))) {
            Snackbar snackbar = Snackbar.make(everything,R.string.sorry, Snackbar.LENGTH_LONG);
            snackbar.setBackgroundTint(Color.parseColor("#b00020"));
            snackbar.setTextColor(Color.parseColor("#d98c07"));
            snackbar.show();
        }

        //Отображение общего количества примеров на действие
        sumTxt.setText(String.valueOf(sumCounter));
        subTxt.setText(String.valueOf(subCounter));
        multTxt.setText(String.valueOf(multCounter));
        devTxt.setText(String.valueOf(devCounter));
    }
}