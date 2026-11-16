
package com.example.intencje_drugi_ekran;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private final ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null
                        && result.getData().getBooleanExtra(ResultActivity.EXTRA_PLAY_AGAIN, false)) {
                    showRandomQuestion();
                } else if (result.getResultCode() == RESULT_CANCELED) {
                    Snackbar.make(findViewById(R.id.main), R.string.quiz_cancelled,
                            Snackbar.LENGTH_SHORT).show();
                }
            });

    private final ArrayList<Question> questions = new ArrayList<>();
    private final ArrayList<Integer> questionOrder = new ArrayList<>();
    private TextView tvQuestionNumber;
    private TextView tvQuestion;
    private RadioGroup rgAnswers;
    private RadioButton rbAnswerA;
    private RadioButton rbAnswerB;
    private RadioButton rbAnswerC;
    private RadioButton rbAnswerD;
    private int questionPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate");

        tvQuestionNumber = findViewById(R.id.tvQuestionNumber);
        tvQuestion = findViewById(R.id.tvQuestion);
        rgAnswers = findViewById(R.id.rgAnswers);
        rbAnswerA = findViewById(R.id.rbAnswerA);
        rbAnswerB = findViewById(R.id.rbAnswerB);
        rbAnswerC = findViewById(R.id.rbAnswerC);
        rbAnswerD = findViewById(R.id.rbAnswerD);

        if (loadQuestions()) {
            showRandomQuestion();
        } else {
            findViewById(R.id.btnCheck).setEnabled(false);
            Snackbar.make(findViewById(R.id.main), R.string.error_questions,
                    Snackbar.LENGTH_INDEFINITE).show();
        }

        findViewById(R.id.btnCheck).setOnClickListener(view -> checkAnswer());

        findViewById(R.id.btnAbout).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    private boolean loadQuestions() {
        try (InputStream stream = getResources().openRawResource(R.raw.questions)) {
            String json = readJson(stream);
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.getJSONObject(i);
                questions.add(new Question(item.getString("q"), item.getString("a"),
                        item.getString("b"), item.getString("c"), item.getString("d"),
                        item.getString("correct_answer")));
                questionOrder.add(i);
            }
            Collections.shuffle(questionOrder);
            return !questions.isEmpty();
        } catch (Exception exception) {
            return false;
        }
    }

    private String readJson(InputStream stream) throws Exception {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int read;
        while ((read = stream.read(buffer)) != -1) {
            output.write(buffer, 0, read);
        }
        return new String(output.toByteArray(), StandardCharsets.UTF_8);
    }

    private void showRandomQuestion() {
        if (questionPosition == questionOrder.size()) {
            Collections.shuffle(questionOrder);
            questionPosition = 0;
        }
        Question question = questions.get(questionOrder.get(questionPosition++));
        tvQuestionNumber.setText(getString(R.string.question_number, questionPosition));
        tvQuestion.setText(question.text);
        setAnswer(rbAnswerA, question.answerA);
        setAnswer(rbAnswerB, question.answerB);
        setAnswer(rbAnswerC, question.answerC);
        setAnswer(rbAnswerD, question.answerD);
        rgAnswers.clearCheck();
        tvQuestion.setTag(question);
    }

    private void setAnswer(RadioButton answerView, String answer) {
        answerView.setText(answer);
        answerView.setVisibility(answer.isEmpty() ? View.GONE : View.VISIBLE);
    }

    private void checkAnswer() {
        int checkedId = rgAnswers.getCheckedRadioButtonId();
        if (checkedId == -1) {
            Snackbar.make(findViewById(R.id.main), R.string.error_no_answer,
                    Snackbar.LENGTH_SHORT).show();
            return;
        }
        Question question = (Question) tvQuestion.getTag();
        String selectedAnswer = ((RadioButton) findViewById(checkedId))
                .getText().toString();
        boolean isCorrect = question.isCorrect(selectedAnswer, checkedId);

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(ResultActivity.EXTRA_QUESTION, question.text);
        intent.putExtra(ResultActivity.EXTRA_SELECTED_ANSWER, selectedAnswer);
        intent.putExtra(ResultActivity.EXTRA_CORRECT_ANSWER, question.getCorrectAnswerText());
        intent.putExtra(ResultActivity.EXTRA_POINTS, isCorrect ? 1 : 0);
        intent.putExtra(ResultActivity.EXTRA_IS_CORRECT, isCorrect);
        resultLauncher.launch(intent);
    }

    private static class Question {
        private final String text;
        private final String answerA;
        private final String answerB;
        private final String answerC;
        private final String answerD;
        private final String correctAnswer;

        private Question(String text, String answerA, String answerB,
                         String answerC, String answerD, String correctAnswer) {
            this.text = text;
            this.answerA = answerA;
            this.answerB = answerB;
            this.answerC = answerC;
            this.answerD = answerD;
            this.correctAnswer = correctAnswer;
        }

        private boolean isCorrect(String selectedAnswer, int checkedId) {
            String answerLetter;
            if (checkedId == R.id.rbAnswerA) {
                answerLetter = "a";
            } else if (checkedId == R.id.rbAnswerB) {
                answerLetter = "b";
            } else if (checkedId == R.id.rbAnswerC) {
                answerLetter = "c";
            } else {
                answerLetter = "d";
            }
            return correctAnswer.trim().equalsIgnoreCase(answerLetter)
                    || correctAnswer.trim().equalsIgnoreCase(selectedAnswer.trim());
        }

        private String getCorrectAnswerText() {
            switch (correctAnswer.trim().toLowerCase()) {
                case "a": return answerA;
                case "b": return answerB;
                case "c": return answerC;
                case "d": return answerD;
                default: return correctAnswer;
            }
        }
    }
}
