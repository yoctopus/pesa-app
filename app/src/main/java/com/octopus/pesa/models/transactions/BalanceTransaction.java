package com.octopus.pesa.models.transactions;

import android.content.Context;

import com.octopus.pesa.models.Transaction;

/**
 * Created by octopus on 6/23/16.
 */
public class BalanceTransaction extends Transaction {
    private int Bal;

    public BalanceTransaction(Context context, Context activityContext) {
        super(context, activityContext, 0);
    }

    @Override
    public void endTransaction(boolean success) {

    }

    /**
     * method is called just before the task is started
     */
    @Override
    public void beforeExecuting() {

    }

    @Override
    public boolean execute() {

        return false;
    }

    /**
     * show an updated message here while the task is continuing
     */
    @Override
    public void whileExecuting() {

    }

    public int getBal() {
        return Bal;
    }

    public void setBal(int bal) {
        Bal = bal;
    }
}
