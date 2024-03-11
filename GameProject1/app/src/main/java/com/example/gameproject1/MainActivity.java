package com.example.gameproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton send_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        send_button = findViewById(R.id.next);
// button to go to login page
        send_button.setOnClickListener(v -> {
            Intent intent = new Intent(this, Login_Register.class);
            startActivity(intent);
        });
    }
}