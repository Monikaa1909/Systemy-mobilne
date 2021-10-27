package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private boolean answerWasShown;

    private static final String QUIZ_TAG = "Main_Activity";
    private static final String KEY_CURRENT_INDEX = "currentIndex";
    public static final String KEY_EXTRA_ANSWER = "com.example.quiz.correctAnswer";
    public static final String KEY_EXTRA_PROMPT = "com.example.quiz.promptQuestion";
    private static final int REQUEST_CODE_PROMPT = 0;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(QUIZ_TAG, "Wywołana metoda: onSaveInstanceState()");
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(QUIZ_TAG, "Wywołana metoda cyklu życia: onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(QUIZ_TAG, "Wywołana metoda cyklu życia: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(QUIZ_TAG, "Wywołana metoda cyklu życia: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(QUIZ_TAG, "Wywołana metoda cyklu życia: onResumeStop()");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(QUIZ_TAG, "Wywołana metoda cyklu życia: onActivityResult()");
        Log.d(QUIZ_TAG, "resultCode = " + resultCode + " RESULT_OK = " + RESULT_OK);
        Log.d(QUIZ_TAG, "REQUES_CODE_PROMPT = " + REQUEST_CODE_PROMPT);
        if (resultCode != RESULT_OK) { return; }
        if (resultCode == REQUEST_CODE_PROMPT-1) {
            if (data == null) {
                Log.d(QUIZ_TAG, "Data == null");
                return; }
            Log.d(QUIZ_TAG, "Prompt shown? " + data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false));
            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(QUIZ_TAG, "Wywołana metoda cyklu życia: onDestroy()");
    }

    private Button trueButton;
    private Button falseButton;
    private Button promptButton;
    private Button nextButton;
    private TextView questionTextView;

    private Question[] questions = new Question[] {
            new Question(R.string.q_1, true, "Rzut karny potocznie nazywany jest jedenastką"),
            new Question(R.string.q_2, false, "Po pokazaniu piątej czerwonej kartki dla tej samej drużyny, sędzia będzie musiał przerwać spotkanie"),
            new Question(R.string.q_3, false, "Nierozwaga (przeważnie) karana jest napomnieniem"),
            new Question(R.string.q_4, false, "Zawodnik dobrowolnie opuszczający pole gry bez zgody sędziego zostaje ukarany napomnieniem"),
            new Question(R.string.q_5, true, "Przy rzucie sędziowskim, w odległości niewiększej niż 4 metry, może znajdować się tylko wykonawca rzutu")
    };

    private int currentIndex = 0;

    private void checkAnswerCorrectness(boolean userAnswer) {
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        String promptQuestion = questions[currentIndex].getPromptToQuestion();

        int resultMessageId = 0;

        if (answerWasShown) {
            resultMessageId = R.string.answer_was_shown;
        }
        else {
            if (userAnswer == correctAnswer) {
                resultMessageId = R.string.correct_answer;
            } else {
                resultMessageId = R.string.incorrect_answer;
            }
        }
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(QUIZ_TAG, "Wywołana metoda cyklu życia: onCreate()");
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        promptButton = findViewById(R.id.propmt_button);
        nextButton = findViewById(R.id.next_button);
        questionTextView = findViewById(R.id.question_text_view);

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(true);
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(false);
            }
        });

        promptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PromptActivity.class);
                boolean correctAnswer = questions[currentIndex].isTrueAnswer();
                String promptQuestion = questions[currentIndex].getPromptToQuestion();
//                intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);
                intent.putExtra(KEY_EXTRA_PROMPT, promptQuestion);
                startActivityForResult(intent, REQUEST_CODE_PROMPT);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex + 1)%questions.length;
                answerWasShown = false;
                setNextQuestion();
            }
        });
        setNextQuestion();
    }

    private void setNextQuestion() {
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }

}

