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
        Intent intent = new Intent(this, Sum.class);
        startActivity(intent);
    }

    public void toSub(View view) {
        Intent intent = new Intent(this, Sub.class);
        startActivity(intent);
    }

    public void toMult(View view) {
        Intent intent = new Intent(this, Mult.class);
        startActivity(intent);
    }

    public void toDev(View view) {
        Intent intent = new Intent(this, Dev.class);
        startActivity(intent);
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