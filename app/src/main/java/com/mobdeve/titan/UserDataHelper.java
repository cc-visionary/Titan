package com.mobdeve.titan;

import java.util.ArrayList;

public class UserDataHelper {
    public static ArrayList<UserModel> loadUserData() {
        ArrayList<UserModel> data = new ArrayList<UserModel>();

        data.add(new UserModel("admin1", "admin1@gmail.com", "091283912354", "password", "moderator"));
        data.add(new UserModel("admin2", "admin2@gmail.com", "091283912313", "password", "moderator"));
        data.add(new UserModel("user1", "user1@gmail.com", "091231290313", "password", "user"));
        data.add(new UserModel("user2", "user2@gmail.com", "098437212112", "asd", "user"));
        data.add(new UserModel("user3", "user3@gmail.com", "092312341241", "qwe", "user"));
        data.add(new UserModel("user4", "user4@gmail.com", "091283123365", "qwerty", "user"));
        data.add(new UserModel("user5", "user5@gmail.com", "092128371942", "awesome", "user"));

        return data;
    }
}
