package com.example.getstock;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Observable;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class SearchModel extends Observable {

    //User settings.
    int userType; //Set user type to display the correct view.
    Bundle args;
    User user;
    Broker broker;
    String userId;
    String stockSymbol;
    boolean isQueryGood;

    public SearchModel(Bundle args){
        this.args = args;
        userType = args.getInt("userType");
        userId = args.getString("userId");

        if(userType == 1){

            broker = (Broker) args.getSerializable("broker");
        }
        if(userType == 2){
            user = (User) args.getSerializable("user");
        }

    }

    /**
     * Create a new bundle.
     * @return
     */
    public Bundle getBundle() {
        return args;
    }

    public void addToBundle(String key, String item){
        args.putString(key, item);
    }
    /**
     * Check if given stock exists.
     */
    public void CheckStock(String stockSymbol) throws IOException {

        Stock myStock;

        new AsyncTask<Void, Void, Void>() {
            boolean valid;
            BigDecimal full;
            Stock myStock;

            @Override
            protected void onPostExecute(Void unused) {
                if(valid && full!=null){
                    isQueryGood = true;
                }
                else {
                    isQueryGood = false;
                }


            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    valid = YahooFinance.get(stockSymbol).isValid();
                    full = YahooFinance.get(stockSymbol).getQuote().getOpen();
                } catch (IOException e) {
                }
                return null;
            }

        }.execute();


    }


}
