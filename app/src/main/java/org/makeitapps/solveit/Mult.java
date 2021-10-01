package org.makeitapps.solveit;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class Mult extends AppCompatActivity {
View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mult);
        view = findViewById(R.id.everything);
    }

    public void toMultBook(View view) {
        Snackbar snackbar = Snackbar.make(view, "Опция будет добавлена скоро", Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    public void toTrains(View view) {
        Snackbar snackbar = Snackbar.make(view, "Опция будет добавлена скоро", Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}