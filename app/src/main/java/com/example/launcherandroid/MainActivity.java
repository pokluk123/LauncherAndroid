package com.example.launcherandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView chrome = findViewById(R.id.chromeButton);
        chrome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.android.chrome");
                startActivity(launchIntent);
            }
        });

        ImageView gmail = findViewById(R.id.gmailButton);
        gmail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.android.gmail");
                startActivity(launchIntent);
            }
        });

        ImageView calculator = findViewById(R.id.calculatorButton);
        calculator.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.android.calculator");
                startActivity(launchIntent);
            }
        });

    }
}