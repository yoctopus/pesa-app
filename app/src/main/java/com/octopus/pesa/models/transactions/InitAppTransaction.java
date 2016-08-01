package com.octopus.pesa.models.transactions;

import android.content.Context;

import com.octopus.pesa.models.TempData;
import com.octopus.pesa.models.Account;
import com.octopus.pesa.models.Item;
import com.octopus.pesa.models.Record;
import com.octopus.pesa.models.Transaction;

import java.util.ArrayList;

/**
 * Created by octopus on 7/21/16.
 */
public class InitAppTransaction extends Transaction {
    private Account account;
    private ArrayList<Record> records;
    private ArrayList<Item> items;
    public InitAppTransaction(Context context, Context activityContext) {
        super(context, activityContext, Transaction.INIT);
        account = null;
        records = new ArrayList<>();
        items = new ArrayList<>();
    }

    /**
     * method is called just before the task is started
     */
    @Override
    public void beforeExecuting() {
        logTransaction("Initialising Pesa app");
    }

    /**
     * ecexute the main logic here
     */
    @Override
    public boolean execute() {
        account = new Account(getContext());
        logTransaction("loading records");
        records = getDb().getRecords();
        logTransaction("loading items");
        items = getDb().getItems();
        account.setRecords(records);
        account.setItems(items);
        logTransaction("loading info");
        account.setInfo(getDb().getAccountInfo());
        logTransaction("info loaded\n"+account.getInfo().getName()+ account.getInfo().getPin());
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
        logTransaction("saving account");
        TempData.account = account;
    }

}
