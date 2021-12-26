package com.example.getstock;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a broker that can
 * invest for you.
 *
 */
public class Broker implements GeneralUser{
    Map<String, Double> UsersInvesting; // a table containing User Id -> How much money did he give the broker.

    public Broker(){
        UsersInvesting = new HashMap<>();
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

    /**
     * Get a users money back.
     * @param email
     * @return
     */
    public Double ReturnMoneyToUser(String email){
        if(email!=null && UsersInvesting.get(email)!=null) {
            return UsersInvesting.get(email);
        }
        else {
            System.out.println("No such user in banking portfolio");
            return 0.0;
        }
    }

    /**
     * Add a new user to our portfolio, with given amount.
     */
    public void AddUser(String email, Double amount){
        if(email!=null && amount!=null && amount>0){
            UsersInvesting.put(email, amount);
        }
    }
}
