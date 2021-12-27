package com.example.getstock;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a general user.
 */
public class User implements GeneralUser{
    public String fullName,age,email;
    public String [] UserFavorites; //User preferences.
    public String initialMoney; //The initial money the user starts with.
    private Map<String, Double> brokerMap; //Will use to hold all our brokers, and amount invested.

    public User(){
        brokerMap = new HashMap<>();
    }

    public User(String fullName, String age, String email) {
        this.fullName = fullName;
        this.age = age;
        this.email = email;
    }

    @Override
    public String getFullName() {
        return null;
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public String getAge() {
        return null;
    }

    @Override
    public String getUserScore() {
        return null;
    }
}
