package com.example.android_2_final_project;

public class Question {

    String question;
    String answersOne;
    String answersTwo;
    String answersThree;

    public Question(String question, String answersOne, String answersTwo, String answersThree) {
        this.question = question;
        this.answersOne = answersOne;
        this.answersTwo = answersTwo;
        this.answersThree = answersThree;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswersOne() {
        return answersOne;
    }

    public void setAnswersOne(String answersOne) {
        this.answersOne = answersOne;
    }

    public String getAnswersTwo() {
        return answersTwo;
    }

    public void setAnswersTwo(String answersTwo) {
        this.answersTwo = answersTwo;
    }

    public String getAnswersThree() {
        return answersThree;
    }

    public void setAnswersThree(String answersThree) {
        this.answersThree = answersThree;
    }
}
