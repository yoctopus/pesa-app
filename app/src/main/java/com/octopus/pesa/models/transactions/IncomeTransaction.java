package com.octopus.pesa.models.transactions;

import android.content.Context;

import com.octopus.pesa.models.AccountInfo;
import com.octopus.pesa.models.Record;
import com.octopus.pesa.models.Transaction;

/**
 * Created by octopus on 6/23/16.
 */
public class IncomeTransaction extends Transaction {
    private Record record;

    public IncomeTransaction(Context context, Context activity, Record r) {
        super(context, activity, Transaction.INCOME);
        this.record = r;
    }

    /**
     * finalize the transaction normally, no data sent back to the activity
     *
     * @param success
     **/
    @Override
    public void endTransaction(boolean success) {
        logTransaction("Adding income end");
    }

    /**
     * method is called just before the task is started
     */
    @Override
    public void beforeExecuting() {
        logTransaction("Adding income begin");
    }

    /**
     * add the current record to the records table
     * update the total column of the account table incrementing with the new record amount
     **/
    @Override
    public boolean execute() {
        boolean addRecord = getDb().addRecord(record);
        logTransaction("Adding record " + record.getNameItem() + " amount " + record.getAmount() + " " + addRecord);
        AccountInfo info = getDb().getAccountInfo();
        logTransaction("Getting current account info " + info.getName());
        int totalBal = info.getTotalBal();
        boolean addTotal = getDb().UpdateAccountTotalBal(totalBal + record.getAmount());
        logTransaction("updating current total balance " + addTotal);
        if (addRecord && addTotal) {
            logTransaction("Transaction successful");
            return true;
        }
        logTransaction("Transaction unsuccessful");
        return false;
    }

    /**
     * show an updated message here while the task is continuing
     */
    @Override
    public void whileExecuting() {

    }
}
