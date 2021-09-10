package com.mobdeve.titan;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mobdeve.titan.DAO.UserDAO;
import com.mobdeve.titan.Models.UserModel;

public class ProfileFragment extends Fragment {

    private TextView usernameTextView, phoneTextView;
    private Button logoutButton;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        this.usernameTextView = view.findViewById(R.id.tv_profile_username);
        this.phoneTextView = view.findViewById(R.id.tv_profile_number);
        this.logoutButton = view.findViewById(R.id.btn_profile_logout);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        UserDAO userDAO = new UserDAO(view.getContext());
        UserModel user = userDAO.getUserByEmail(firebaseUser.getEmail());

        this.usernameTextView.setText(user.getUsername());
        this.phoneTextView.setText(user.getNumber());

        this.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(v.getContext(), SigninActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}