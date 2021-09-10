package com.mobdeve.titan.Models;

public class UserModel {
    private String username, email, number, password, userType;

    public UserModel(String username, String email, String number, String password, String userType) {
        this.username = username;
        this.email = email;
        this.number = number;
        this.password = password;
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getNumber() {
        return number;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }

    public boolean verifyPassword(String password) {
        return this.password.equals(password);
    }
}
