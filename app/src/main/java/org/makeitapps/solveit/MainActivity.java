package org.makeitapps.solveit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.everything);
    }

    public void toSum(View view) {
        Intent intentSum = new Intent(this, Sum.class);
        startActivity(intentSum);
    }

    public void toSub(View view) {
        Snackbar snackbar = Snackbar.make(view, "Опция будет добавлена скоро", Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    public void toMult(View view) {
        Snackbar snackbar = Snackbar.make(view, "Опция будет добавлена скоро", Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    public void toDev(View view) {
        Snackbar snackbar = Snackbar.make(view, "Опция будет добавлена скоро", Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    public void toCustom(View view) {
        // TODO: 9/18/2021 Сделать кастомные настройки!
        Snackbar snackbar = Snackbar.make(view, "Опция будет добавлена скоро", Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    public void toSettings(View view) {
        // TODO: 9/18/2021 Сделать кастомные настройки!
        Snackbar snackbar = Snackbar.make(view, "Опция будет добавлена скоро", Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}