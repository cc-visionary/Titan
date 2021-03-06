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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mobdeve.titan.DatabaseHelpers.UserDatabaseHelper;
import com.mobdeve.titan.Models.UserModel;

import static android.content.ContentValues.TAG;

public class SignupActivity extends AppCompatActivity {

    private EditText etEmail, etContact, etPassword, etConfirmPassword;
    private Switch userTypeSwitch;
    private TextView signinTextView;
    private Button signupButton;
    private ProgressBar pbSignup;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        this.etEmail = findViewById(R.id.et_signup_email);
        this.etContact = findViewById(R.id.et_signup_contact);
        this.etPassword = findViewById(R.id.et_signup_password);
        this.etConfirmPassword = findViewById(R.id.et_signup_confirm_password);
        this.userTypeSwitch = findViewById(R.id.sw_user_type);
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
                String email = etEmail.getText().toString();
                String contact = etContact.getText().toString();
                String password = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();
                String userType = userTypeSwitch.isChecked() ? "host" : "user";

                if(validateFields(email, contact, password, confirmPassword)) {
                    UserModel user = new UserModel(email, contact, password, userType);
                    storeUser(user);
                }
            }

        });
    }

    public boolean validateFields(String email, String contact, String password, String confirmPassword) {
        boolean hasError = false;
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
        if(contact.length() != 11) {
            this.etContact.setError("Contact must be of length 11");
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
                            success(user);
                        } else {
                            fail(task.getException().getMessage());
                        }
                    }
                });
    }

    public void success(UserModel user) {
        this.pbSignup.setVisibility(View.GONE);

        UserDatabaseHelper userDBHelper = new UserDatabaseHelper();
        userDBHelper.addUser(user);
        if(user.getUserType().equals("host")) {
            Intent intent = new Intent(this, AdminHomeActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, UserHomeActivity.class);
            startActivity(intent);
        }
        Toast.makeText(this, "Signup was successful", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void fail(String errorMessage) {
        this.pbSignup.setVisibility(View.GONE);
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}