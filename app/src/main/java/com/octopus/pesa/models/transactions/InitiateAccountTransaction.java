package com.octopus.pesa.models.transactions;

import android.content.Context;

import com.octopus.pesa.TempData;
import com.octopus.pesa.models.Account;
import com.octopus.pesa.models.Transaction;

/**
 * Created by octopus on 7/20/16.
 */
public class InitiateAccountTransaction extends Transaction {
    private Account account;

    public InitiateAccountTransaction(Context context, Context activityContext) {
        super(context, activityContext, TempData.AccountInitiationID);
    }

    /**
     * method is called just before the task is started
     */
    @Override
    public void beforeExecuting() {
        logTransaction("Initiating user account");
    }

    /**
     * ecexute the main logic here
     */
    @Override
    public boolean execute() {
        account = new Account(getContext());
        if (account != null) {
            account.setActivityContext(getActivityContext());
            logTransaction("account initiated");
            return true;
        }
        logTransaction("account not initiated");
        return false;
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
        if (success)
            TempData.account = account;

    }
}
