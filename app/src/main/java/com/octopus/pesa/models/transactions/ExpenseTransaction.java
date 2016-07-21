package com.octopus.pesa.models.transactions;

import android.content.Context;
import android.widget.Toast;

import com.octopus.pesa.models.AccountInfo;
import com.octopus.pesa.models.Record;
import com.octopus.pesa.models.Transaction;

/**
 * Created by octopus on 6/23/16.
 */
public class ExpenseTransaction extends Transaction {

    private Record record;

    public ExpenseTransaction(Context context, Context activity, Record r) {
        super(context, activity, Transaction.EXPENSE);
        this.record = r;
    }

    /**
     * finalize with expense transaction id
     *
     * @param success
     **/
    @Override
    public void endTransaction(boolean success) {
        logTransaction("Adding expense end");
        Toast.makeText(getActivityContext(), "Expense transacted", Toast.LENGTH_SHORT).show();
    }

    /**
     * method is called just before the task is started
     */
    @Override
    public void beforeExecuting() {
        logTransaction("Adding expense begin");
    }

    /**
     * add the record to the database
     * increment the daily spent amount
     * decrement the account total balance
     **/
    @Override
    public boolean execute() {
        boolean addRecord = getDb().addRecord(record);
        logTransaction("Adding record " + record.getNameItem() + "of amount " + record.getAmount() + " " + addRecord);
        AccountInfo info = getDb().getAccountInfo();
        logTransaction("Getting current account info" + info.getName());
        int dailyspent = info.getDailySpent();
        int totalBal = info.getTotalBal();
        boolean addSpent = getDb().UpdateAccountDailySpent(dailyspent + record.getAmount());
        logTransaction("Updating current daily spent amount" + addSpent);
        boolean minusTotal = getDb().UpdateAccountTotalBal(totalBal - record.getAmount());
        logTransaction("updating account total balance " + minusTotal);
        if (addRecord && addSpent && minusTotal) {
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
