package com.mobdeve.titan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.mobdeve.titan.DAO.UserDAO;
import com.mobdeve.titan.Models.UserModel;

public class MainActivity extends AppCompatActivity {

    private Button getStartedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserDAO userDAO = new UserDAO(MainActivity.this);

        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        if(current_user == null) {
            setupWelcome();
        } else {
            UserModel user = userDAO.getUserByEmail(current_user.getEmail());
            System.out.println(user.getUserType());
            if(user.getUserType().equals("host")) {
                setupHostLoggedIn();
            } else {
                setupUserLoggedIn();
            }
        }
    }

    private void setupWelcome() {
        setContentView(R.layout.activity_main);

        this.getStartedButton = findViewById(R.id.btn_start);

        this.getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SigninActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setupUserLoggedIn() {
        Intent intent = new Intent(this, UserHomeActivity.class);
        startActivity(intent);
        finish();
    }


    private void setupHostLoggedIn() {
        Intent intent = new Intent(this, AdminHomeActivity.class);
        startActivity(intent);
        finish();
    }
}