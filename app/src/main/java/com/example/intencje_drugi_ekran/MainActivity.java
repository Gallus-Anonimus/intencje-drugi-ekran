package com.example.intencje_drugi_ekran;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnNext).setOnClickListener(view -> {
            Intent intent = new Intent(this, SummaryActivity.class);
            startActivity(intent);
        });
    }
}
