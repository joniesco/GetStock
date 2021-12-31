package com.example.getstock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class represents a broker that can
 * invest for you.
 *
 */
public class Broker implements Serializable {

    public Map<String, Double> usersInvesting; // a table containing User Id -> How much money did he give the broker.
    public Map<String, Integer> portfolio; //Symbol - > amount of stocks.
    public List<String> favoriteStocks;
    public Double initialMoney;
    public Double buyingCommission ;
    public Double sellingCommission ;
    public String userType ;
    public Double brokerCommission;
    public String fullName,age,email;

    public Broker(){}

    public Broker(  String fullName,String age,String email, Double brokerCommission) {
        usersInvesting = new HashMap<String, Double>();
        portfolio = new HashMap<String, Integer>();
        favoriteStocks = new ArrayList<>();

        this.age=age;
        this.fullName = fullName;
        this.email= email;

        this.initialMoney = 0.0;
        this.buyingCommission = 0.05;
        this.sellingCommission = 0.02;
        this.userType = "Broker";
        this.brokerCommission = brokerCommission;
    }


    /**
     * Add a new user to our portfolio, with given amount.
     */
    public void AddUser(String email, Double amount){
        if(email!=null && amount!=null && amount>0){
            usersInvesting.put(email, amount);
        }
        initialMoney += amount;
    }

    /**
     * Buy a stock,  a simple way to buy a stock
     * Don't forget stock buying,selling commission
     * and the brokers commission.
     * @param Symbol
     * @param quantity
     * @param stockPrice
     */
    public void BuyStock(String Symbol, int quantity, double stockPrice){
        double buyStockWithCommission = stockPrice *quantity*buyingCommission;
        if(buyStockWithCommission > initialMoney){
            System.out.println("Insufficient funds");
        }
        else {

            if (portfolio.get(Symbol) == null) {
                System.out.println("Added a new stock");
                portfolio.put(Symbol, quantity);
            } else {
                System.out.println("Add to existing stock");
                portfolio.put(Symbol, quantity);
            }
        }
        initialMoney -= buyStockWithCommission;
    }

    /**
     * Sell a stock
     * @param Symbol
     * @param quantity
     * @param stockPrice
     */
    public void sellStock(String Symbol, int quantity, double stockPrice){
        double sellStockWithCommission = stockPrice *quantity*sellingCommission;

        if(portfolio.get(Symbol)==null){
            System.out.println("No such stock exists");
        }
        if(portfolio.get(Symbol) < quantity){
            System.out.println("Not enough stocks to sell");
        }

        int initialQuantity = portfolio.get(Symbol);
        portfolio.put(Symbol,initialQuantity  + quantity );
        initialMoney += sellStockWithCommission;
    }

    /**
     * Return a user their money + after commission.
     * @return
     */
    public Double giveMoneyBackToUser(String email){
        if(usersInvesting.get(email) == null && email!=null){
            System.out.println("No such user exist");
        }
        initialMoney -= usersInvesting.get(email);
        return usersInvesting.get(email) * (1 - brokerCommission);
    }

    public Map<String, Integer> getUsersInvesting() {
        return portfolio;
    }
    public Map<String, Integer> getPortfolio() {
        return portfolio;
    }

    public Double getInitialMoney() {
        return initialMoney;
    }

    public Double getBuyingCommission() {
        return buyingCommission;
    }

    public Double getSellingCommission() {
        return sellingCommission;
    }

    public String getUserType() {
        return userType;
    }

    public Double getBrokerCommission() {
        return brokerCommission;
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

    /**
     * Get a list of all users.
     * @return
     */
    public Set<String> getUserList(){
        return usersInvesting.keySet();
    }
}
