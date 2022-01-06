package com.example.getstock;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents a broker that can
 * invest for you.
 *
 */
public class Broker implements Serializable {

    public Map<String, Double> usersInvesting; // a table containing User Id -> How much money did he give the broker.
    public Map<String, Integer> portfolio; //Symbol - > amount of stocks.
    public Double initialMoney;
    public Double buyingCommission ;
    public Double sellingCommission ;
    public String userType ;
    public Double brokerCommission;
    public String fullName,age,email;
    public List<String> favorites;
    public Map<String, Double> userRequests;
    public List<String> notifications;
    public Map<String, ClientPortofolio> usersInvestmentFile;//Show where the broker invests each users money.
    public Map<String, String> IdsToNames;

    public Broker(){}

    public Broker(  String fullName,String age,String email, Double brokerCommission) {
        usersInvesting = new HashMap<String, Double>();
        portfolio = new HashMap<String, Integer>();
        favorites = new ArrayList<>();
        userRequests = new HashMap<>();
        notifications = new ArrayList<>();
        usersInvestmentFile = new HashMap<>();
        IdsToNames = new HashMap<>();

        this.age=age;
        this.fullName = fullName;
        this.email= email;

        this.initialMoney = 0.0;
        this.buyingCommission = 0.05;
        this.sellingCommission = 0.02;
        this.userType = "Broker";
        this.brokerCommission = brokerCommission;
    }

    @Override
    public String toString() {
        return "Broker{" +
                "usersInvesting=" + usersInvesting +
                ", portfolio=" + portfolio +
                ", initialMoney=" + initialMoney +
                ", buyingCommission=" + buyingCommission +
                ", sellingCommission=" + sellingCommission +
                ", userType='" + userType + '\'' +
                ", brokerCommission=" + brokerCommission +
                ", fullName='" + fullName + '\'' +
                ", age='" + age + '\'' +
                ", email='" + email + '\'' +
                ", favorites=" + favorites +
                ", userRequests=" + userRequests +
                ", notifications=" + notifications +
                ", usersInvestmentFile=" + usersInvestmentFile +
                ", IdsToNames=" + IdsToNames +
                '}';
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
    public void BuyStock(String Symbol, int quantity, double stockPrice, String clientId){

        Double withdraw = stockPrice*quantity;

        if(usersInvestmentFile.get(clientId).depositMoney < withdraw ){
            return;
        }

            usersInvestmentFile.get(clientId).symbolToBuyValue.put(Symbol,stockPrice);
            usersInvestmentFile.get(clientId).symbolToAmount.put(Symbol,Double.valueOf(quantity));

            usersInvestmentFile.get(clientId).depositMoney -= withdraw;

    }

    /**
     * Sell a stock
     * @param Symbol
     * @param quantity
     * @param stockPrice
     */
    public void sellStock(String Symbol, int quantity, double stockPrice, String clientId){
        double sellStockWithCommission = stockPrice * quantity * sellingCommission;

        if(portfolio.get(Symbol)!=null){
            System.out.println("No such stock exists");

            if(portfolio.get(Symbol) < quantity){
                System.out.println("Not enough stocks to sell");
            }
            else {
                int initialQuantity = portfolio.get(Symbol);
                portfolio.put(Symbol,initialQuantity  + quantity );
                initialMoney += sellStockWithCommission;
            }

        }


    }

    public Map<String, Double> getUserRequests() {
        return userRequests;
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
     * Accept new client.
     * @param clientId
     * @param clientEmail
     */
    public void acceptClient(String clientId, String clientEmail){
        Double depositAmount  = userRequests.get(clientId);
        userRequests.remove(clientId);
        usersInvestmentFile.put(clientId, new ClientPortofolio(new HashMap<>(),new HashMap<>(),depositAmount));
        IdsToNames.put(clientId, clientEmail);
    }

    /**
     * Get client id.
     * @param clientEmail
     * @return
     */
    public String clientMailToId(String clientEmail){
        Log.d("", IdsToNames + "");
        for(String s: IdsToNames.keySet()){
            if(IdsToNames.get(s).equals(clientEmail)){
                return s;
            }
        }
        return null;
    }

        public void setUsersInvesting(Map<String, Double> usersInvesting) {
            this.usersInvesting = usersInvesting;
        }

        public void setPortfolio(Map<String, Integer> portfolio) {
            this.portfolio = portfolio;
        }

        public void setInitialMoney(Double initialMoney) {
            this.initialMoney = initialMoney;
        }

        public void setBuyingCommission(Double buyingCommission) {
            this.buyingCommission = buyingCommission;
        }

        public void setSellingCommission(Double sellingCommission) {
            this.sellingCommission = sellingCommission;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public void setBrokerCommission(Double brokerCommission) {
            this.brokerCommission = brokerCommission;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setFavorites(List<String> favorites) {
            this.favorites = favorites;
        }

        public void setUserRequests(Map<String, Double> userRequests) {
            this.userRequests = userRequests;
        }

        public void setNotifications(List<String> notifications) {
            this.notifications = notifications;
        }

//        public void setUsersInvestmentFile(Map<String, Map<String, String>> usersInvestmentFile) {
//            this.usersInvestmentFile = usersInvestmentFile;
//        }
}