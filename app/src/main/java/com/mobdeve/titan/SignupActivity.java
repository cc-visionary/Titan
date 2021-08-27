package com.mobdeve.titan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;

public class SignupActivity extends AppCompatActivity {

    private EditText etUsername, etEmail, etContact, etPassword, etConfirmPassword;
    private TextView signinTextView;
    private Button signupButton;
    private ProgressBar pbSignup;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        this.etUsername = findViewById(R.id.et_signup_username);
        this.etEmail = findViewById(R.id.et_signup_email);
        this.etContact = findViewById(R.id.et_signup_contact);
        this.etPassword = findViewById(R.id.et_signup_password);
        this.etConfirmPassword = findViewById(R.id.et_signup_confirm_password);
        this.signinTextView = findViewById(R.id.tv_signin);
        this.signupButton = findViewById(R.id.btn_signup);
        this.pbSignup = findViewById(R.id.pb_signup);

        mAuth = FirebaseAuth.getInstance();

        this.signinTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SigninActivity.class);
                startActivity(intent);
                finish();
            }
        });

        this.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String email = etEmail.getText().toString();
                String contact = etContact.getText().toString();
                String password = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();
                String userType = true ? "admin" : "user";

                if(validateFields(username, email, contact, password, confirmPassword)) {
                    storeUser(new UserModel(username, email, contact, password, userType));
                }
            }

        });
    }

    public boolean validateFields(String username, String email, String contact, String password, String confirmPassword) {
        boolean hasError = false;
        if(username.isEmpty()) {
            this.etUsername.setError("Username required");
            this.etUsername.requestFocus();
            hasError = true;
        }
        if(email.isEmpty()) {
            this.etEmail.setError("Email required");
            this.etEmail.requestFocus();
            hasError = true;
        }
        if(contact.isEmpty()) {
            this.etContact.setError("Contact required");
            this.etContact.requestFocus();
            hasError = true;
        }
        if(password.isEmpty()) {
            this.etPassword.setError("Password required");
            hasError = true;
        }
        if(confirmPassword.isEmpty()) {
            this.etConfirmPassword.setError("Confirm Password required");
            this.etConfirmPassword.requestFocus();
            hasError = true;
        }
        if(!password.equals(confirmPassword)) {
            this.etConfirmPassword.setError("Password and confirm password has to be the same");
            this.etConfirmPassword.setText("");
            this.etPassword.requestFocus();
            hasError = true;
        }
        return !hasError;
    }

    public void storeUser(UserModel user) {
        this.pbSignup.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            success();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            fail();
                        }
                    }
                });
    }

    public void success() {
        this.pbSignup.setVisibility(View.GONE);
        Toast.makeText(this, "Signup was successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, UserHomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void fail() {
        this.pbSignup.setVisibility(View.GONE);
        Toast.makeText(this, "Signup failed", Toast.LENGTH_SHORT).show();
    }
}