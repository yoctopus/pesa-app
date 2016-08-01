package com.octopus.pesa.models.transactions;

import android.content.Context;

import com.octopus.pesa.models.AccountInfo;
import com.octopus.pesa.models.Transaction;

/**
 * Created by octopus on 8/1/16.
 */
public class SaveInfoTransaction extends Transaction {
    private AccountInfo info;
    public SaveInfoTransaction(Context context, Context activityContext, AccountInfo info) {
        super(context, activityContext, Transaction.SAVEINFO);
        this.info = info;
    }

    /**
     * method is called just before the task is started
     */
    @Override
    public void beforeExecuting() {
        logTransaction("Saving new account information");
    }

    /**
     * ecexute the main logic here
     */
    @Override
    public boolean execute() {
        getDb().UpdateAccountName(info.getName());
        getDb().UpdateAccountPin(info.getPin());
        getDb().UpdateAccountDailyLimit(info.getDailyLimit());
        return true;
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

    }
}
