package com.octopus.pesa.models.transactions;

import android.content.Context;
import android.widget.Toast;

import com.octopus.pesa.TempData;
import com.octopus.pesa.models.AccountInfo;
import com.octopus.pesa.models.Transaction;

/**
 * Created by octopus on 7/20/16.
 */
public class LoginTransaction extends Transaction {
    private AccountInfo info;
    private int pin;

    public LoginTransaction(Context context, Context activityContext, int pin) {
        super(context, activityContext, Transaction.LOGIN);
        this.setPin(pin);
    }

    /**
     * method is called just before the task is started
     */
    @Override
    public void beforeExecuting() {
        logTransaction("initiating login transaction");
    }

    /**
     * ecexute the main logic here
     */
    @Override
    public boolean execute() {
        setInfo(getDb().getAccountInfo());
        logTransaction("Account found " + getInfo().getName() +
                " daily spent " + getInfo().getDailySpent() +
                " daily limit " + getInfo().getDailyLimit() +
                " total balance " + getInfo().getTotalBal());
        if (this.getPin() == getInfo().getPin()) {
            return true;
        } else {
            logTransaction("inaccurate pin detected");
            return false;
        }
    }

    /**
     * show an updated message here while the task is continuing
     */
    @Override
    public void whileExecuting() {

    }

    /**
     * call this end the transaction
     *
     * @param success the boolean showing if the transaction was successful
     */
    @Override
    public void endTransaction(boolean success) {
        logTransaction("Login complete");
        if (success) {
            TempData.info = getInfo();
            logTransaction("Loading account " + getInfo().getName());
            Toast.makeText(getActivityContext(), "Login success", Toast.LENGTH_SHORT).show();
        } else {
            TempData.info = null;
            logTransaction("Account not loaded");
            Toast.makeText(getActivityContext(), "Login failed", Toast.LENGTH_SHORT).show();
        }
    }

    public AccountInfo getInfo() {
        return info;
    }

    public void setInfo(AccountInfo info) {
        this.info = info;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }
}
