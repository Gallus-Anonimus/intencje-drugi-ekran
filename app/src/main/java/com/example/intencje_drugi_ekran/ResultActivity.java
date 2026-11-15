package com.example.intencje_drugi_ekran;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    public static final String EXTRA_QUESTION = "com.example.intencje_drugi_ekran.QUESTION";
    public static final String EXTRA_SELECTED_ANSWER = "com.example.intencje_drugi_ekran.SELECTED_ANSWER";
    public static final String EXTRA_CORRECT_ANSWER = "com.example.intencje_drugi_ekran.CORRECT_ANSWER";
    public static final String EXTRA_POINTS = "com.example.intencje_drugi_ekran.POINTS";
    public static final String EXTRA_IS_CORRECT = "com.example.intencje_drugi_ekran.IS_CORRECT";
    public static final String EXTRA_PLAY_AGAIN = "com.example.intencje_drugi_ekran.PLAY_AGAIN";

    private String resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        Intent intent = getIntent();
        boolean isCorrect = intent.getBooleanExtra(EXTRA_IS_CORRECT, false);
        int points = intent.getIntExtra(EXTRA_POINTS, 0);
        String correctAnswer = intent.getStringExtra(EXTRA_CORRECT_ANSWER);
        resultText = isCorrect ? getString(R.string.answer_correct, points) : getString(R.string.answer_wrong);

        ((TextView) findViewById(R.id.tvResult)).setText(resultText);
        ((TextView) findViewById(R.id.tvCorrectAnswer)).setText(
                getString(R.string.correct_answer, correctAnswer));
        findViewById(R.id.btnPlayAgain).setOnClickListener(view -> playAgain());
    }

    private void playAgain() {
        Intent result = new Intent();
        result.putExtra(EXTRA_PLAY_AGAIN, true);
        setResult(RESULT_OK, result);
        finish();
    }

}
