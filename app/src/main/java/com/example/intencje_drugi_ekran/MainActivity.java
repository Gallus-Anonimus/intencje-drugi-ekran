package com.example.intencje_drugi_ekran;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnNext).setOnClickListener(view -> {
            Intent intent = new Intent(this, SummaryActivity.class);
            intent.putExtra(SummaryActivity.EXTRA_NAME,
                    ((EditText) findViewById(R.id.etName)).getText().toString());
            intent.putExtra(SummaryActivity.EXTRA_EMAIL,
                    ((EditText) findViewById(R.id.etEmail)).getText().toString());
            intent.putExtra(SummaryActivity.EXTRA_COURSE,
                    ((Spinner) findViewById(R.id.spCourse)).getSelectedItem().toString());
            intent.putExtra(SummaryActivity.EXTRA_LEVEL,
                    ((SeekBar) findViewById(R.id.sbLevel)).getProgress());
            intent.putExtra(SummaryActivity.EXTRA_NEWSLETTER,
                    ((CheckBox) findViewById(R.id.cbNewsletter)).isChecked());
            startActivity(intent);
        });
    }
}
