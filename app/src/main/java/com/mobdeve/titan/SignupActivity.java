package com.mobdeve.titan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignupActivity extends AppCompatActivity {

    private EditText usernameEditText, emailEditText, contactEditText, passwordEditText, confirmPasswordEditText;
    private TextView signinTextView;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        this.usernameEditText = findViewById(R.id.et_signup_username);
        this.emailEditText = findViewById(R.id.et_signup_email);
        this.contactEditText = findViewById(R.id.et_signup_contact);
        this.passwordEditText = findViewById(R.id.et_signup_password);
        this.confirmPasswordEditText = findViewById(R.id.et_signup_confirm_password);
        this.signinTextView = findViewById(R.id.tv_signin);
        this.signupButton = findViewById(R.id.btn_signup);

        this.signinTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SigninActivity.class);
                startActivity(intent);
            }
        });

        this.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UserHomeActivity.class);
                startActivity(intent);
            }
        });
    }
}