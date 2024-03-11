package com.example.gameproject1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.Map;

public class HistoryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        TextView historyDisplay = view.findViewById(R.id.historyDisplay);
        Button goBack = view.findViewById(R.id.goBackButton);

        // Retrieve the current user's email from SharedPreferences
        SharedPreferences userPreferences = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String userEmail = userPreferences.getString("email", "");

        // Retrieve scores associated with the current user's email from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("QuizScores_" + userEmail, Context.MODE_PRIVATE);
        StringBuilder historyText = new StringBuilder();
        int count = 1;
        for (Map.Entry<String, ?> entry : sharedPreferences.getAll().entrySet()) {
            historyText.append("Last Quiz ").append(" Score: ").append(entry.getValue()).append("\n");
            count++;
        }
        historyDisplay.setText(historyText.toString());

        // Set onClickListener for the goBack button
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the home page
                getActivity().onBackPressed();
            }
        });

        return view;
    }
}
