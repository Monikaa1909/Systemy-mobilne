package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PromptActivity extends AppCompatActivity {
    private boolean correctAnswer;
    private String promptQuestion;

    private TextView promptTextView;
    private Button showCorrectAnswerButton;
    private TextView promptText;

    public static final String KEY_EXTRA_ANSWER_SHOWN = "answerShown";

    private void setAnswerShownResult(boolean answerWasShown) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_EXTRA_ANSWER_SHOWN, answerWasShown);
        setResult(RESULT_OK, resultIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt);

        promptTextView = findViewById(R.id.prompt_text_view);
        showCorrectAnswerButton = findViewById(R.id.yes_button);
        promptText = findViewById(R.id.prompt_text);

        correctAnswer = getIntent().getBooleanExtra(MainActivity.KEY_EXTRA_ANSWER, true);
        promptQuestion = getIntent().getStringExtra(MainActivity.KEY_EXTRA_PROMPT);

        showCorrectAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int answer = correctAnswer ? R.string.button_true : R.string.button_false;
                promptText.setText(promptQuestion);
                setAnswerShownResult(true);
            }
        });
    }
}