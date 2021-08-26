package com.mobdeve.titan;

public class UserModel {
    private int userId;
    private String username, email, number, password, userType;

    public UserModel(int userId, String username, String email, String number, String password, String userType) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.number = number;
        this.password = password;
        this.userType = userType;
    }

    public int getUserId() {
        return userId;
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

    public String getUserType() {
        return userType;
    }

    public boolean verifyPassword(String password) {
        return this.password.equals(password);
    }
}
