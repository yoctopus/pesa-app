package com.octopus.pesa.models.transactions;

import android.content.Context;
import android.widget.Toast;

import com.octopus.pesa.models.TempData;
import com.octopus.pesa.models.AccountInfo;
import com.octopus.pesa.models.Transaction;

/**
 * Created by octopus on 6/23/16.
 */
public class AccountInfoTransaction extends Transaction {

    private AccountInfo info;

    public AccountInfoTransaction(Context context, Context activity) {
        super(context, activity, Transaction.INFO);


    }

    /**
     * @param success the boolean showing if the transaction was successful
     */
    @Override
    public void endTransaction(boolean success) {
        logTransaction("Loading info complete");
        if (success) {
            TempData.account.setInfo(getInfo());
            logTransaction("Loading info for " + info.getName());
            Toast.makeText(getActivityContext(), "info loading success", Toast.LENGTH_SHORT).show();
        } else {
            TempData.account.setInfo(null);
            logTransaction("Account info not loaded");
            Toast.makeText(getActivityContext(), "info loading failed", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * method is called just before the task is started
     */
    @Override
    public void beforeExecuting() {
        logTransaction("Initiating loading account info");
    }

    @Override
    public boolean execute() {
        info = getDb().getAccountInfo();
        if (info != null) {
            logTransaction("info found " + info.getName() +
                    " daily spent " + info.getDailySpent() +
                    " daily limit " + info.getDailyLimit() +
                    " total balance " + info.getTotalBal());
            return true;
        } else {
            logTransaction("info not found");
            return false;
        }

    }

    /**
     * show an updated message here while the task is continuing
     */
    @Override
    public void whileExecuting() {

    }

    public AccountInfo getInfo() {
        return info;
    }

    public void setInfo(AccountInfo info) {
        this.info = info;
    }
}
