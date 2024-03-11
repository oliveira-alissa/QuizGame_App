package com.example.gameproject1;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class QuizHistory {

    private Context context;
    private SharedPreferences sharedPreferences;

    public QuizHistory(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("QuizScores", Context.MODE_PRIVATE);
    }

    // Method to save score for a specific quiz
    public void saveScoreForQuiz(String userEmail, int quizNumber, int score) {
        String key = "QuizScores_" + userEmail + "_Quiz" + quizNumber;
        sharedPreferences.edit().putInt(key, score).apply();
    }

    // Method to retrieve score for a specific quiz
    public int getScoreForQuiz(String userEmail, int quizNumber) {
        String key = "QuizScores_" + userEmail + "_Quiz" + quizNumber;
        return sharedPreferences.getInt(key, 0); // Default value is 0 if key not found
    }

    // Method to format history for display
    public String getFormattedHistory(String userEmail) {
        StringBuilder stringBuilder = new StringBuilder();
        int count = 1;
        while (true) {
            String key = "QuizScores_" + userEmail + "_Quiz" + count;
            if (sharedPreferences.contains(key)) {
                int score = sharedPreferences.getInt(key, 0);
                stringBuilder.append("Quiz ").append(count).append(" Score: ").append(score).append("\n");
                count++;
            } else {
                break;
            }
        }
        return stringBuilder.toString();
    }
}
