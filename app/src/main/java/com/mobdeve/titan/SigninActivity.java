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
import com.mobdeve.titan.DAO.UserDAO;
import com.mobdeve.titan.Models.UserModel;

import static android.content.ContentValues.TAG;

public class SigninActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private TextView signupTextView;
    private Button signinButton;
    private ProgressBar pbSignin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        this.etEmail = findViewById(R.id.et_signin_email);
        this.etPassword = findViewById(R.id.et_signin_password);
        this.signupTextView = findViewById(R.id.tv_signup);
        this.signinButton = findViewById(R.id.btn_signin);
        this.pbSignin = findViewById(R.id.pb_signin);

        mAuth = FirebaseAuth.getInstance();

        this.signupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });

        this.signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                if(validateFields(email, password)) {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        UserDAO userDAO = new UserDAO(v.getContext());
                                        UserModel user = userDAO.getUserByEmail(email);
                                        success(user.getUserType());
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());

                                        fail();
                                    }
                                }
                            });
                }
            }
        });
    }

    public boolean validateFields(String email, String password) {
        boolean hasError = false;
        if(email.isEmpty()) {
            this.etEmail.setError("Email required");
            this.etEmail.requestFocus();
            hasError = true;
        }
        if(password.isEmpty()) {
            this.etPassword.setError("Password required");
            hasError = true;
        }
        return !hasError;
    }

    public void success(String userType) {
        this.pbSignin.setVisibility(View.GONE);
        if(userType.equals("host")) {
            Intent intent = new Intent(this, AdminHomeActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, UserHomeActivity.class);
            startActivity(intent);
        }
        Toast.makeText(this, "Sign in was successful", Toast.LENGTH_SHORT).show();
    }

    public void fail() {
        this.pbSignin.setVisibility(View.GONE);
        Toast.makeText(this, "Sign in failed", Toast.LENGTH_SHORT).show();
    }
}