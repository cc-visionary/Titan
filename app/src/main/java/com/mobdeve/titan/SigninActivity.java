package com.mobdeve.titan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SigninActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private TextView signupTextView;
    private Button signinButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        this.usernameEditText = findViewById(R.id.et_signin_username);
        this.passwordEditText = findViewById(R.id.et_signin_password);
        this.signupTextView = findViewById(R.id.tv_signup);
        this.signinButton = findViewById(R.id.btn_signin);

        this.signupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SignupActivity.class);
                startActivity(intent);
            }
        });

        this.signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AdminHomeActivity.class);
                // TODO: signin back-end login
                startActivity(intent);
            }
        });
    }
}