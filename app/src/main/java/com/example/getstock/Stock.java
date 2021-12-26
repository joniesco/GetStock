package com.example.getstock;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * This class represents a stock.
 */
public class Stock {
    String Symbol;
    String desc;
    String market;
    String percentChange;
    boolean percentIsPositive;

    public Stock(String Symbol, String desc, String market){
        this.Symbol = Symbol;
        this.desc = desc;
        this.market = market;
        percentChange = generateRandomPercent();
    }

    public String getSymbol() {
        return Symbol;
    }

    public String getDesc() {
        return desc;
    }

    public String getMarket() {
        return market;
    }

    /**
     * Generate a random percent between -1 and 1.
     * @return
     */
    public String generateRandomPercent(){
        DecimalFormat df = new DecimalFormat("#.##");

        double start = -1;
        double end = 1;
        double random = new Random().nextDouble();
        double result = start + (random * (end - start));

        if(result>0){
           percentIsPositive = true;
        }
        else
            percentIsPositive = false;

        double finalResult = new Double(df.format(result));

        return "% "+finalResult;
    }
}
