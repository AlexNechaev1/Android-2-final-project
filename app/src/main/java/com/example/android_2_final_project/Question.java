package com.example.android_2_final_project;

import java.io.Serializable;

public class Question implements Serializable {

    java.lang.String question;
    java.lang.String answer1;
    java.lang.String answer2;
    java.lang.String answer3;

    public Question() {
    }

    public Question(String question, String answer1, String answer2, String answer3) {
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", answers1='" + answer1 + '\'' +
                ", answers2='" + answer2 + '\'' +
                ", answers3='" + answer3 + '\'' +
                '}';
    }
}
