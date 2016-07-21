package com.octopus.pesa.models.transactions;

import android.content.Context;

import com.octopus.pesa.TempData;
import com.octopus.pesa.models.Item;
import com.octopus.pesa.models.Record;
import com.octopus.pesa.models.Transaction;

import java.util.ArrayList;

/**
 * Created by octopus on 6/23/16.
 */
public class RecordsTransaction extends Transaction {
    private ArrayList<Record> records;
    private ArrayList<Item> items;

    public RecordsTransaction(Context context, Context activity) {
        super(context, activity, Transaction.RECORDS);
        records = new ArrayList<>();
        items = new ArrayList<>();
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
        TempData.records = records;
        TempData.items = items;
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
        records = organizeRecords(getDb().getRecords());
        items = getDb().getItems();
        if (!records.isEmpty()) {
            for (Record r : records) {
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
