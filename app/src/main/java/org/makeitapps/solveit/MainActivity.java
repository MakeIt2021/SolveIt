package org.makeitapps.solveit;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    ImageButton settings;
    View everything;
    ImageButton thanks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settings = findViewById(R.id.settingsBtn);
        everything = findViewById(R.id.everything);
        thanks = findViewById(R.id.thanks);

        if (getIntent().getBooleanExtra("wasChanged", false) == true) {
            Snackbar snackbar = Snackbar.make(everything,R.string.success, Snackbar.LENGTH_SHORT);
            snackbar.setBackgroundTint(Color.parseColor("#00b86b"));
            snackbar.setTextColor(Color.WHITE);
            snackbar.show();

        }
        Intent intent = new Intent(this, Solving.class);
        Intent intent1 = new Intent(this, SettingsActivity.class);
        Intent intent2 = new Intent(this, ThanksActivity.class);
        everything.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent1);
            }
        });

        thanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent2);
            }
        });

    }
}