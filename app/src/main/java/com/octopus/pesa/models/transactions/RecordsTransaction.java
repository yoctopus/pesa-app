package com.octopus.pesa.models.transactions;

import android.content.Context;

import com.octopus.pesa.models.Account;
import com.octopus.pesa.models.TempData;
import com.octopus.pesa.models.Item;
import com.octopus.pesa.models.Record;
import com.octopus.pesa.models.Transaction;

import java.util.ArrayList;

/**
 * Created by octopus on 6/23/16.
 */
public class RecordsTransaction extends Transaction {
    private Account account;

    public RecordsTransaction(Context context, Context activity, Account account) {
        super(context, activity, Transaction.RECORDS);
        this.account = account;
    }

    /**
     * save the records from the database
     * save the items from the database
     *
     * @param success
     **/
    @Override
    public void endTransaction(boolean success) {
        logTransaction("Fetch of records complete");
        TempData.account = account;
    }

    /**
     * method is called just before the task is started
     */
    @Override
    public void beforeExecuting() {
        logTransaction("Initiating fetch of records");
    }

    /**
     * get the records from the database
     * get the items from the database
     **/
    @Override
    public boolean execute() {
        account.setRecords(organizeRecords(getDb().getRecords()));
        account.setItems(getDb().getItems());
        if (!account.getRecords().isEmpty()) {
            for (Record r : account.getRecords()) {
                logTransaction("Record " + r.getNameItem() + " amount " + r.getAmount());
            }
            return true;
        }
        return false;
    }
    private ArrayList<Record> organizeRecords(ArrayList<Record> records) {
        ArrayList<Record> records1 = new ArrayList<>();
        for(int i = records.size() - 1; i >= 0 ; i--) {
            records1.add(records.get(i));
        }
        return records1;
    }

    /**
     * show an updated message here while the task is continuing
     */
    @Override
    public void whileExecuting() {

    }
}
