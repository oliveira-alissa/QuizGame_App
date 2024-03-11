package com.example.gameproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    private ImageButton backBtn;
    private Button registerBtn;
    private EditText first_name, last_name, dob, password, email;

    // Define keys for SharedPreferences
    private static final String PREF_NAME = "user_info";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_FIRST_NAME = "first_name";
    private static final String KEY_LAST_NAME = "last_name";
    private static final String KEY_DOB = "dob";

    // SharedPreferences object
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerBtn = findViewById(R.id.register);
        backBtn = findViewById(R.id.goBack);
        first_name = findViewById(R.id.nameEntry);
        last_name = findViewById(R.id.LastNameEntry);
        dob = findViewById(R.id.BirthDate);
        password = findViewById(R.id.PasswordEntry);
        email = findViewById(R.id.emailEntry);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, Login_Register.class);
            startActivity(intent);
        });

        registerBtn.setOnClickListener(v -> registration());
    }

    private void registration() {
        String firstName = first_name.getText().toString().trim();
        String lastName = last_name.getText().toString().trim();
        String dateBirth = dob.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String emailAddress = email.getText().toString().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || dateBirth.isEmpty() || pass.isEmpty() || emailAddress.isEmpty()) {
            // Set error message for each empty field
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if first name length is valid
        if (firstName.length() < 3 || firstName.length() > 30) {
            first_name.setError("First name must be between 3-30 characters long");
            return;
        }

        // Check if email address is valid
        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            email.setError("Enter a valid Email Address!");
            return;
        }

        // Check if date of birth is in MM/DD/YYYY format
        if (!isValidDateFormat(dateBirth)) {
            dob.setError("Enter Date of Birth in MM/DD/YYYY format!");
            return;
        }

        // Save user information to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL, emailAddress);
        editor.putString(KEY_PASSWORD, pass);
        editor.putString(KEY_FIRST_NAME, firstName);
        editor.putString(KEY_LAST_NAME, lastName);
        editor.putString(KEY_DOB, dateBirth);
        editor.apply();

        // Proceed to login activity
        Intent intent = new Intent(this, Login_Register.class);
        intent.putExtra("msg", "Account Created. Log In Now!");
        startActivity(intent);
    }

    // Method to validate date format
    private boolean isValidDateFormat(String date) {
        // Regular expression for MM/DD/YYYY format
        String dateFormatRegex = "(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/\\d{4}";
        return date.matches(dateFormatRegex);
    }
}