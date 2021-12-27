package com.example.getstock;

import java.util.Date;
import java.util.Random;

/**
 * Represents a post made by the user.
 */
public class UserPost implements GeneralUser{
    private String fullName,age,email;
    private String desc;
    private String postTitle;
    private String userScore;
    private long dateAdded;

    public UserPost(){

    }
    public String addRandomPosts(){
        String [] RandomPosts = new String[5];
        RandomPosts[0] = "Investing in New stocks";
        RandomPosts[1] = "Building new Rules for Investing";
        RandomPosts[2] = "APL.US buying and selling";
        RandomPosts[3] = "Increase in market cap";
        RandomPosts[4] = "Stock recap of 2019";

        Random r = new Random();
        int low = 0;
        int high = 4;
        int result = r.nextInt(high-low) + low;

        return RandomPosts[result];
    }
    public UserPost(String fullName, String age, String email) {
        this.fullName = fullName;
        this.age = age;
        this.email = email;
        this.postTitle = addRandomPosts();
        this.desc = "BLABLABL LBABL BALBL SLADSLADSALDASD";
        Date date = new Date();
        dateAdded = date.getTime();
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getAge() {
        return age;
    }

    public String getDesc() {
        return desc;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getUserScore(){
        return userScore;
    }
}
