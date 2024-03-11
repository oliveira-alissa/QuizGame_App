package com.example.gameproject1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class HomePage extends AppCompatActivity {

    Button playBtn;
    Button logoutBtn;
    Button historyBtn;
    ImageButton rulesBtn;
    TextView userName;
    private static final int REQUEST_CODE_QUIZ = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        playBtn = findViewById(R.id.playGame);
        logoutBtn = findViewById(R.id.logOut);
        historyBtn = findViewById(R.id.viewHistory);
        rulesBtn= findViewById(R.id.rulesBtn);
        userName= findViewById(R.id.name);

        // Get the saved first name from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        String firstName = sharedPreferences.getString("first_name", "");

        // Set the text of the userName TextView to the retrieved first name
        userName.setText(firstName);

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, QuizQuestion.class);
                startActivityForResult(intent, REQUEST_CODE_QUIZ);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform logout actions
                Intent intent = new Intent(HomePage.this, Login_Register.class);
                startActivity(intent);
                finish();
            }
        });

        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the HistoryFragment
                Fragment fragment = new HistoryFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        // Set onClickListener for the rulesBtn button
        rulesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new instance of the RulesFragment
                Fragment rulesFragment = new RulesFragment();

                // Begin a FragmentTransaction
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, rulesFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_QUIZ && resultCode == RESULT_OK) {
            int score = data.getIntExtra("score", 0);
            // Pass the score to the HistoryFragment
            Bundle bundle = new Bundle();
            bundle.putInt("score", score);
            Fragment fragment = new HistoryFragment();
            fragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
