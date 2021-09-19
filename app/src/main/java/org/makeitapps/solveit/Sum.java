package org.makeitapps.solveit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class Sum extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum);
    }

    public void toSumBook(View view) {
        Snackbar snackbar = Snackbar.make(view, "Опция будет добавлена скоро", Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    public void toTrains(View view) {
        Snackbar snackbar = Snackbar.make(view, "Опция будет добавлена скоро", Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}