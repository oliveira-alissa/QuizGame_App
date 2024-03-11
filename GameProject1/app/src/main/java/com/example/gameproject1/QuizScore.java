package com.example.gameproject1;

public class QuizScore {
    private int quizNumber;
    private int score;

    public QuizScore(int quizNumber, int score) {
        this.quizNumber = quizNumber;
        this.score = score;
    }

    public int getQuizNumber() {
        return quizNumber;
    }

    public void setQuizNumber(int quizNumber) {
        this.quizNumber = quizNumber;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
