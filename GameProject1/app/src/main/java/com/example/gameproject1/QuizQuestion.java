package com.example.gameproject1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class QuizQuestion extends AppCompatActivity {

    TextView totalQuestionsTextView;
    TextView questionTextView;
    Button ansA, ansB, ansC, ansD;
    Button nextBtn;
    int currentQuestionIndex = 0;
    int totalQuestions;
    int score = 0;

    List<Button> selectedButtons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_question);

        totalQuestionsTextView = findViewById(R.id.totalQuestion);
        questionTextView = findViewById(R.id.question);
        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        ansC = findViewById(R.id.ans_C);
        ansD = findViewById(R.id.ans_D);
        nextBtn = findViewById(R.id.SubmitBtn);

        totalQuestions = QuestionAnswer.questions.size();
        displayQuestion();

        View.OnClickListener answerClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button clickedButton = (Button) v;

                if (selectedButtons.contains(clickedButton)) {
                    selectedButtons.remove(clickedButton);
                    clickedButton.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                } else {
                    selectedButtons.add(clickedButton);
                    clickedButton.setBackgroundTintList(getResources().getColorStateList(R.color.dark_green));
                }
            }
        };

        ansA.setOnClickListener(answerClickListener);
        ansB.setOnClickListener(answerClickListener);
        ansC.setOnClickListener(answerClickListener);
        ansD.setOnClickListener(answerClickListener);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectedButtons.isEmpty()) {
                    // Check the answers and show result
                    checkAnswersConfirmation();
                } else {
                    // Inform user to select an answer
                    showMessage("Missing Answer", "Please select an answer before proceeding to the next question.");
                }
            }
        });
    }

    private void displayQuestion() {
        QuestionAnswer.Question question = QuestionAnswer.questions.get(currentQuestionIndex);
        totalQuestionsTextView.setText("Question " + (currentQuestionIndex + 1) + "/" + totalQuestions);
        questionTextView.setText(question.getQuestion());
        List<String> choices = question.getChoices();
        ansA.setText(choices.get(0));
        ansB.setText(choices.get(1));
        ansC.setText(choices.get(2));
        ansD.setText(choices.get(3));
    }

    private void checkAnswersConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Answer Submission");
        builder.setMessage("Are you sure you want to submit your answer?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // Proceed to check the answers and show the result
                checkAnswers();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // Do nothing or allow the user to change their answer
            }
        });
        builder.setCancelable(false);
        builder.show();
    }


    private void checkAnswers() {
        // Continue with checking the answers and showing the result
        QuestionAnswer.Question question = QuestionAnswer.questions.get(currentQuestionIndex);
        List<String> selectedAnswers = new ArrayList<>();
        List<String> correctAnswers = question.getCorrectAnswers();

        // Get the selected answers
        for (Button button : selectedButtons) {
            selectedAnswers.add(button.getText().toString());
        }

        // Store the selected answers in SharedPreferences
        saveSelectedAnswers(currentQuestionIndex, selectedAnswers);

        // Continue with checking the correctness of the answers
        boolean allCorrect = true;
        for (String selectedAnswer : selectedAnswers) {
            if (!correctAnswers.contains(selectedAnswer)) {
                allCorrect = false;
                break;
            }
        }

        // If all selected answers are correct, increment the score
        if (allCorrect && selectedAnswers.size() == correctAnswers.size()) {
            showMessage("Correct", "Your answer is correct!");
            score++;
        } else {
            showMessage("Incorrect", "The correct answer is: " + TextUtils.join(", ", correctAnswers));
        }

        // Proceed to the next question or finish the quiz
        moveToNextQuestion();
    }

    private void saveSelectedAnswers(int questionIndex, List<String> selectedAnswers) {
        // Save the selected answers to SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("SelectedAnswers", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Question_" + questionIndex, TextUtils.join(",", selectedAnswers));
        editor.apply();
    }

    private void moveToNextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex < totalQuestions) {
            displayQuestion();
            resetButtonColors();
        } else {
            // End of the quiz, show final score
            showFinalScoreDialog();
        }
    }

    private void resetButtonColors() {
        ansA.setBackgroundTintList(getResources().getColorStateList(R.color.white));
        ansB.setBackgroundTintList(getResources().getColorStateList(R.color.white));
        ansC.setBackgroundTintList(getResources().getColorStateList(R.color.white));
        ansD.setBackgroundTintList(getResources().getColorStateList(R.color.white));

        selectedButtons.clear();
    }

    private void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    private void showFinalScoreDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Quiz Finished");
        builder.setMessage("Your final score is: " + score);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveFinalScore(score);
                finish();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    private void saveFinalScore(int score) {
        // Get the current user's email
        SharedPreferences userPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        String userEmail = userPreferences.getString("email", "");

        // Save the final score to SharedPreferences with the user's email as part of the key
        SharedPreferences sharedPreferences = getSharedPreferences("QuizScores_" + userEmail, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("LastScore", score);
        editor.apply();
    }
}
