package org.makeitapps.solveit;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class Sub extends AppCompatActivity {
View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        view = findViewById(R.id.everything);
    }

    public void toSubBook(View view) {
        Snackbar snackbar = Snackbar.make(view, "Опция будет добавлена скоро", Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    public void toTrains(View view) {
        Snackbar snackbar = Snackbar.make(view, "Опция будет добавлена скоро", Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}