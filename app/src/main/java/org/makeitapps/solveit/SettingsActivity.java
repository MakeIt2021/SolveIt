package org.makeitapps.solveit;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class SettingsActivity extends AppCompatActivity {
    File file = new File("/data/data/com.makeit.solveit/preferences.txt");

    EditText minEdt;
    EditText maxEdt;
    EditText secondsEdt;
    EditText amountEdt;

    CheckBox sumCheck;
    CheckBox subCheck;
    CheckBox multCheck;
    CheckBox devCheck;

    int min;
    int max;
    int seconds;
    int amount;

    boolean sum;
    boolean sub;
    boolean mult;
    boolean dev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        minEdt = findViewById(R.id.min);
        maxEdt = findViewById(R.id.max);
        sumCheck = findViewById(R.id.checkBoxSum);
        subCheck = findViewById(R.id.checkBoxSub);
        multCheck = findViewById(R.id.checkBoxMult);
        devCheck = findViewById(R.id.checkBoxDev);
        secondsEdt = findViewById(R.id.seconds);
        amountEdt = findViewById(R.id.amount);

        sumCheck.setPadding(40, 0, 0, 0);
        subCheck.setPadding(40, 0, 0, 0);
        multCheck.setPadding(40, 0, 0, 0);
        devCheck.setPadding(40, 0, 0, 0);


        getFromFileAndSetTicks();
    }


    @RequiresApi(api = Build.VERSION_CODES.P)
    public void saveToFile(View view) {
        PrintWriter pw = null;
        boolean check1 = true;
        boolean check2 = true;
        boolean check3 = true;
        boolean check4 = true;
        boolean check5 = true;
        minEdt.clearFocus();
        maxEdt.clearFocus();
        secondsEdt.clearFocus();
        amountEdt.clearFocus();
        ////////////////////Защита от дурака////////////////////////
        try {
            if (sumCheck.isChecked() || subCheck.isChecked() || multCheck.isChecked() || devCheck.isChecked()) {
                // Если минимум пусто
                if (minEdt.getText().length() == 0) {
                    Snackbar snackbar = Snackbar.make(view, R.string.minNull, Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.parseColor("#b00020"));
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                    check3 = false;
                    minEdt.requestFocus();
                }
                // Если максимум пусто
                if (maxEdt.getText().length() == 0) {
                    Snackbar snackbar = Snackbar.make(view, R.string.maxNull, Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.parseColor("#b00020"));
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                    check3 = false;
                    maxEdt.requestFocus();
                }
                // Если минимум больше максимума
                if (check3 && Integer.parseInt(String.valueOf(minEdt.getText())) >= Integer.parseInt(String.valueOf(maxEdt.getText()))) {
                    Snackbar snackbar = Snackbar.make(view, R.string.minMaxErr, Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.parseColor("#b00020"));
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                    check1 = false;
                    maxEdt.requestFocus();
                }
                // Если секунды пусто
                if (secondsEdt.getText().length() == 0) {
                    Snackbar snackbar = Snackbar.make(view, R.string.secNull, Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.parseColor("#b00020"));
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                    check4 = false;
                    secondsEdt.requestFocus();
                }
                // Если секунд 0
                if (check4 && Integer.parseInt(String.valueOf(secondsEdt.getText())) == 0) {
                    Snackbar snackbar = Snackbar.make(view, R.string.secErr, Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.parseColor("#b00020"));
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                    check2 = false;
                    secondsEdt.requestFocus();
                }
                // Если кол-во примеров пусто
                if (amountEdt.getText().length() == 0) {
                    Snackbar snackbar = Snackbar.make(view, R.string.amountNull, Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.parseColor("#b00020"));
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                    check2 = false;
                    amountEdt.requestFocus();
                }
                // Если кол-во примеров 0
                if (check2 && Integer.parseInt(String.valueOf(amountEdt.getText())) == 0) {
                    Snackbar snackbar = Snackbar.make(view, R.string.amountErr, Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.parseColor("#b00020"));
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                    check5 = false;
                    amountEdt.requestFocus();
                }
                if (check1 && check3 && check2 && check4 && check5) {
                    pw = new PrintWriter(file);
                    pw.println(sumCheck.isChecked());
                    pw.println(subCheck.isChecked());
                    pw.println(multCheck.isChecked());
                    pw.println(devCheck.isChecked());
                    pw.println(Integer.parseInt(String.valueOf(minEdt.getText())));
                    pw.println(Integer.parseInt(String.valueOf(maxEdt.getText())));
                    pw.println(Integer.parseInt(String.valueOf(secondsEdt.getText())));
                    pw.println(Integer.parseInt(String.valueOf(amountEdt.getText())));
                    pw.close();

                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("wasChanged", true);
                    startActivity(intent);

                    finish();
                }
            } else {
                // Если ни одна галочка не выбрана
                Snackbar snackbar = Snackbar.make(view, R.string.ticksErr, Snackbar.LENGTH_SHORT);
                snackbar.setBackgroundTint(Color.parseColor("#b00020"));
                snackbar.setTextColor(Color.WHITE);
                snackbar.show();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ////////////////////Защита от дурака////////////////////////
    }

    void getFromFileAndSetTicks() {
        ArrayList arrayList = arrayList = new ArrayList();
        try {
            Scanner getter = new Scanner(file);
            while (getter.hasNext()) {
                arrayList.add(getter.next());
            }

            sum = Boolean.parseBoolean(String.valueOf(arrayList.get(0)));
            sub = Boolean.parseBoolean(String.valueOf(arrayList.get(1)));
            mult = Boolean.parseBoolean(String.valueOf(arrayList.get(2)));
            dev = Boolean.parseBoolean(String.valueOf(arrayList.get(3)));
            min = Integer.parseInt((String) arrayList.get(4));
            max = Integer.parseInt((String) arrayList.get(5));
            seconds = Integer.parseInt((String) arrayList.get(6));
            amount = Integer.parseInt((String) arrayList.get(7));
            getter.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (sum)
            sumCheck.setChecked(true);
        else
            sumCheck.setChecked(false);

        if (sub)
            subCheck.setChecked(true);
        else
            subCheck.setChecked(false);

        if (mult)
            multCheck.setChecked(true);
        else
            multCheck.setChecked(false);

        if (dev)
            devCheck.setChecked(true);
        else
            devCheck.setChecked(false);

        minEdt.setText(String.valueOf(min));
        maxEdt.setText(String.valueOf(max));
        secondsEdt.setText(String.valueOf(seconds));
        amountEdt.setText(String.valueOf(amount));
    }
}
