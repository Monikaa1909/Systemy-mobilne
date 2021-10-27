package com.example.quiz;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Question {
    private int questionId;
    private boolean trueAnswer;
    private String promptToQuestion;

    public Question(int questionId, boolean trueAnswer, String promptToQuestion) {
        this.questionId = questionId;
        this.trueAnswer = trueAnswer;
        this.promptToQuestion = promptToQuestion;
    }
}
