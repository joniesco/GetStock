package com.example.getstock;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a general user.
 */
public class User implements Serializable {

    public String userType;
    public String fullName,age,email;

//    public String [] UserFavorites = new String[10]; //User preferences.

    public String initialMoney ; //The initial money the user starts with.
    public Map<String, Double> brokerMap  ; //Will use to hold all our brokers, and amount invested.

    public User(){
//        brokerMap = new HashMap<>();
    }

    public User(String fullName, String age, String email, String userType, String Dollar, Map<String, Double> brokerMap) {
        this.fullName = fullName;
        this.age = age;
        this.email = email;
        this.userType = userType;
        this.initialMoney = Dollar;
        this.brokerMap = brokerMap;
    }

    public String getUserType() {
        return userType;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getInitialMoney() {
        return initialMoney;
    }

    public Map<String, Double> getBrokerMap() {
        return brokerMap;
    }
}