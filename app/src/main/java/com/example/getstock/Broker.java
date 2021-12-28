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
    Map<String, Integer> Portfolio; //Symbol - > amount of stocks.
    Double initialMoney;
    final Double buyingCommission = 0.05;
    final Double sellingCommission = 0.02;
    final String userType = "Broker";
    private Double brokerCommission;

    public Broker(Double brokerCommission){
        UsersInvesting = new HashMap<>();
        Portfolio = new HashMap<>();
        initialMoney = 50000.0;
        this.brokerCommission = brokerCommission;
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
     * Add a new user to our portfolio, with given amount.
     */
    public void AddUser(String email, Double amount){
        if(email!=null && amount!=null && amount>0){
            UsersInvesting.put(email, amount);
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

            if (Portfolio.get(Symbol) == null) {
                System.out.println("Added a new stock");
                Portfolio.put(Symbol, quantity);
            } else {
                System.out.println("Add to existing stock");
                Portfolio.put(Symbol, quantity);
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

        if(Portfolio.get(Symbol)==null){
            System.out.println("No such stock exists");
        }
        if(Portfolio.get(Symbol) < quantity){
            System.out.println("Not enough stocks to sell");
        }

        int initialQuantity = Portfolio.get(Symbol);
        Portfolio.put(Symbol,initialQuantity  + quantity );
        initialMoney += sellStockWithCommission;
    }

    /**
     * Return a user their money + after commission.
     * @return
     */
    public Double giveMoneyBackToUser(String email){
        if(UsersInvesting.get(email) == null && email!=null){
            System.out.println("No such user exist");
        }
        initialMoney -= UsersInvesting.get(email);
        return UsersInvesting.get(email) * (1 - brokerCommission);
    }
}
