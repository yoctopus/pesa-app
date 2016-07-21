package com.octopus.pesa.models;

/**
 * Created by octopus on 6/22/16.
 */
public class AccountInfo {
    public String Name;
    public int pin;
    public int dailySpent;
    public int dailyLimit;
    public int totalBal;

    public AccountInfo(String name, int pin, int dailySpent, int dailyLimit, int totalBal) {
        Name = name;
        this.pin = pin;
        this.dailySpent = dailySpent;
        this.dailyLimit = dailyLimit;
        this.totalBal = totalBal;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getDailySpent() {
        return dailySpent;
    }

    public void setDailySpent(int dailySpent) {
        this.dailySpent = dailySpent;
    }

    public int getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(int dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public int getTotalBal() {
        return totalBal;
    }

    public void setTotalBal(int totalBal) {
        this.totalBal = totalBal;
    }
}
