package com.octopus.pesa.models.transactions;

import android.content.Context;

import com.octopus.pesa.models.Item;
import com.octopus.pesa.models.Transaction;

import java.util.ArrayList;

/**
 * Created by octopus on 7/2/16.
 */
public class ItemsSaveTransaction extends Transaction {
    private ArrayList<Item> items;

    public ItemsSaveTransaction(Context context, Context activityContext, ArrayList<Item> items) {
        super(context, activityContext, Transaction.ITEMS);
        this.items = items;
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
        boolean addall = true;
        for (Item item : items) {
            boolean add = getDb().addItem(item);
            addall = addall && add;
        }
        return addall;
    }

    /**
     * show an updated message here while the task is continuing
     */
    @Override
    public void whileExecuting() {

    }
}
