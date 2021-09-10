package com.mobdeve.titan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.mobdeve.titan.DatabaseHelpers.UserDatabaseHelper;
import com.mobdeve.titan.Models.UserModel;

public class MainActivity extends AppCompatActivity {

    private Button getStartedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser == null) {
            setupWelcome();
        } else {
            UserDatabaseHelper userDBHelper = new UserDatabaseHelper();
            userDBHelper.getUserByEmail(firebaseUser.getEmail())
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            UserModel user = documentSnapshot.toObject(UserModel.class);

                            if(user.getUserType().equals("host")) {
                                setupHostLoggedIn();
                            } else {
                                setupUserLoggedIn();
                            }
                        }
                    });
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