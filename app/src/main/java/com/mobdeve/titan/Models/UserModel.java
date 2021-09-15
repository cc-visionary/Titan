package com.mobdeve.titan.Models;

public class UserModel {
    private String email, number, password, userType;

    public UserModel(String email, String number, String password, String userType) {
        this.email = email;
        this.number = number;
        this.password = password;
        this.userType = userType;
    }

    public UserModel() {

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
