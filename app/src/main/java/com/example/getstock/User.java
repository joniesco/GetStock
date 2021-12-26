package com.example.getstock;

/**
 * This class represents a general user.
 */
public class User implements GeneralUser{
    public String fullName,age,email;
    public String [] UserFavorites;
    public String initialMoney;

    public User(){

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
