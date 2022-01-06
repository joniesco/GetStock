package com.example.getstock;

import java.io.Serializable;
import java.util.Map;

public class ClientPortofolio implements Serializable {

    public Map<String, Double> symbolToAmount;
    public Map<String,Double> symbolToBuyValue;
    public Double depositMoney;

    public ClientPortofolio(){}

    public ClientPortofolio(Map<String, Double> symbolToAmount, Map<String, Double> symbolToBuyValue, Double depositMoney) {
        this.symbolToAmount = symbolToAmount;
        this.symbolToBuyValue = symbolToBuyValue;
        this.depositMoney = depositMoney;
    }

    public Map<String, Double> getSymbolToAmount() {
        return symbolToAmount;
    }

    public Map<String, Double> getSymbolToBuyValue() {
        return symbolToBuyValue;
    }

    public Double getDepositMoney() {
        return depositMoney;
    }

    public void setSymbolToAmount(Map<String, Double> symbolToAmount) {
        this.symbolToAmount = symbolToAmount;
    }

    public void setSymbolToBuyValue(Map<String, Double> symbolToBuyValue) {
        this.symbolToBuyValue = symbolToBuyValue;
    }

    public void setDepositMoney(Double depositMoney) {
        this.depositMoney = depositMoney;
    }
}
