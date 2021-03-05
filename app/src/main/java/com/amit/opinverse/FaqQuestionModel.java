package com.amit.opinverse;

public class FaqQuestionModel {
    String question, answer;
    FaqQuestionModel(String question, String answer){
        this.question = question;
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }
}
