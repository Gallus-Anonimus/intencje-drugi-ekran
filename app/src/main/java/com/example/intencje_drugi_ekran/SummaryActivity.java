package com.example.intencje_drugi_ekran;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class SummaryActivity extends AppCompatActivity {
    public static final String EXTRA_NAME = "com.example.intencje_drugi_ekran.NAME";
    public static final String EXTRA_EMAIL = "com.example.intencje_drugi_ekran.EMAIL";
    public static final String EXTRA_COURSE = "com.example.intencje_drugi_ekran.COURSE";
    public static final String EXTRA_LEVEL = "com.example.intencje_drugi_ekran.LEVEL";
    public static final String EXTRA_NEWSLETTER = "com.example.intencje_drugi_ekran.NEWSLETTER";
    public static final String EXTRA_NUMBER = "com.example.intencje_drugi_ekran.NUMBER";
    private TextView tvSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        tvSummary = findViewById(R.id.tvSummary);
        Intent intent = getIntent();
        String newsletter = intent.getBooleanExtra(EXTRA_NEWSLETTER, false)
                ? getString(R.string.yes_short) : getString(R.string.no_short);
        tvSummary.setText(getString(R.string.summary_format,
                intent.getStringExtra(EXTRA_NAME),
                intent.getStringExtra(EXTRA_EMAIL),
                intent.getStringExtra(EXTRA_COURSE),
                intent.getIntExtra(EXTRA_LEVEL, 0), newsletter));
        findViewById(R.id.btnEdit).setOnClickListener(view -> finish());
        findViewById(R.id.btnConfirm).setOnClickListener(view -> confirm());
    }

    private void confirm() {
        Intent result = new Intent();
        result.putExtra(EXTRA_NUMBER, "ZG/" + (1000 + new Random().nextInt(9000)));
        setResult(RESULT_OK, result);
        finish();
    }
}
