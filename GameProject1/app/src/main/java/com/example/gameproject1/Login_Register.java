package com.example.gameproject1;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login_Register extends AppCompatActivity {
    Button register, login;
    EditText usernameEditText, passwordEditText;
    TextView message;
    String successMsg;

    // Define SharedPreferences keys
    private static final String PREF_NAME = "user_info";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_NAME = "name";

    // SharedPreferences object
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        register = findViewById(R.id.registerBtn);
        login = findViewById(R.id.login_button);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        message = findViewById(R.id.msg);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        // Retrieve success message from intent
        successMsg = getIntent().getStringExtra("msg");
        message.setText(successMsg);

        // Apply underline to Register Button text
        SpannableString mSpannableString = new SpannableString(register.getText());
        mSpannableString.setSpan(new UnderlineSpan(), 0, mSpannableString.length(), 0);
        register.setText(mSpannableString);

        // Set click listeners for register and login buttons
        register.setOnClickListener(v -> {
            Intent intent = new Intent(this, Register.class);
            startActivity(intent);
        });

        login.setOnClickListener(v -> login());
    }

    // Method to handle login
    private void login() {
        String enteredUsername = usernameEditText.getText().toString().trim();
        String enteredPassword = passwordEditText.getText().toString().trim();

        // Retrieve user information from SharedPreferences
        String registeredEmail = sharedPreferences.getString(KEY_EMAIL, "");
        String registeredPassword = sharedPreferences.getString(KEY_PASSWORD, "");
        String registeredName = sharedPreferences.getString(KEY_NAME, "");

        if (enteredUsername.equals(registeredEmail) && enteredPassword.equals(registeredPassword)) {
            // If username and password match the registered ones, navigate to Home activity
            Intent intent = new Intent(this, HomePage.class);
            intent.putExtra("name", registeredName);

            // Pass the user's email as an extra to HomePage
            intent.putExtra("email", enteredUsername);
            startActivity(intent);
        } else {
            // If username or password is incorrect, display error messages
            usernameEditText.setError("Check if Username is correct");
            passwordEditText.setError("Check if Password is correct");
        }
    }

    // Method to save quiz score for the logged-in user
    public void saveQuizScore(String userEmail, int score) {
        // Save the quiz score to SharedPreferences associated with the user's email
        SharedPreferences userSharedPreferences = getSharedPreferences(userEmail + "_QuizScores", MODE_PRIVATE);
        SharedPreferences.Editor editor = userSharedPreferences.edit();
        // Generate a unique key for each quiz attempt
        String key = "QuizScore_" + System.currentTimeMillis();
        editor.putInt(key, score);
        editor.apply();
    }
}
