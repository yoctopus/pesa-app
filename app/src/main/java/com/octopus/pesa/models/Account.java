package com.octopus.pesa.models;

import android.content.Context;
import android.util.Log;

import com.octopus.pesa.models.transactions.AccountInfoTransaction;
import com.octopus.pesa.models.transactions.ExpenseTransaction;
import com.octopus.pesa.models.transactions.IncomeTransaction;
import com.octopus.pesa.models.transactions.InitAppTransaction;
import com.octopus.pesa.models.transactions.RecordsTransaction;

import java.util.ArrayList;

/**
 * Created by octopus on 6/18/16.
 */
public class Account {
    private final String TAG = "Account";
    //the general account information for storing balance, pin and account name
    private AccountInfo info;
    //the general account records for holding records currently present in this account
    private ArrayList<Record> records;
    //items for every transaction
    private ArrayList<Item> items;
    //the object for curring out transactions like data storage and retrieval
    private Transaction tx;
    //the context of the whole application
    private Context context;
    //the context for the current activity using the account
    private Context activityContext;

    public Account(Context context) {
        this.setContext(context);
        this.records = new ArrayList<>();
        this.items = new ArrayList<>();
        this.setInfo(null);
        setTx(null);
    }

    public void refresh() {
        requestRecords();
    }

    public void requestRecords() {
        setTx(new RecordsTransaction(getContext(), getActivityContext()));
        Log.i(TAG, "Fetching records");
        getTx().executeNow();
    }

    public void refreshAccountInfo() {
        setTx(new AccountInfoTransaction(getContext(), getActivityContext()));
        Log.i(TAG, "refreshing info");
        getTx().executeNow();
    }

    public void InitAccount() {
        tx = new InitAppTransaction(getContext(), null);
        Log.i(TAG, "Account init");
        tx.executeNow();
    }

    public int getIncomeTotal() {
        int sum = 0;
        for (Record r : getIncomeRecords()) {
            sum += r.getAmount();
        }
        return sum;
    }

    public int getExpenseTotal() {
        int sum = 0;
        for (Record r : getExpenseRecords()) {
            sum += r.getAmount();
        }
        return sum;
    }

    public ArrayList<String> getIncomeItems() {
        ArrayList<String> items = new ArrayList<>();
        items.add("Salary");
        items.add("Loan");
        items.add("Mpesa");
        items.addAll(getItems_of_Type(Item.INCOME_TYPE));
        return items;
    }

    public ArrayList<String> getExpenseItems() {
        ArrayList<String> items = new ArrayList<>();
        items.add("Airtime");
        items.add("Food");
        items.addAll(getItems_of_Type(Item.EXPENSE_TYPE));
        return items;
    }

    public ArrayList<String> getItems_of_Type(String type) {
        ArrayList<String> strings = new ArrayList<>();
        for (Item item : getItems()) {
            if (item.getType() == type) {
                strings.add(item.getName());
            }
        }
        return strings;
    }

    public ArrayList<Record> getIncomeRecords() {
        return getRecords_of_Type(Record.Income);
    }

    public ArrayList<Record> getExpenseRecords() {
        return getRecords_of_Type(Record.Expense);
    }

    private ArrayList<Record> getRecords_of_Type(String type) {
        ArrayList<Record> record = new ArrayList<>();
        for (Record r : getRecords()) {
            if (r.getType() == type) {
                record.add(r);
            }
        }
        return record;
    }

    public boolean transactIncome(String item, int amount) {
        Item itm = new Item(Item.INCOME_TYPE, item);
        Record r = new Record(itm, amount);
        getInfo().setTotalBal(getInfo().getTotalBal() + amount);
        setTx(new IncomeTransaction(getContext(), getActivityContext(), r));
        getTx().executeNow();
        return true;
    }

    public boolean transactExpense(String item, int amount) {
        Item itm = new Item(Item.EXPENSE_TYPE, item);
        Record r = new Record(itm, amount);
        getInfo().setDailySpent(getInfo().getDailySpent() + amount);
        getInfo().setTotalBal(getInfo().getTotalBal() - amount);
        setTx(new ExpenseTransaction(getContext(), getActivityContext(), r));
        getTx().executeNow();
        return true;
    }

    public Context getActivityContext() {
        return activityContext;
    }

    public void setActivityContext(Context activityContext) {
        this.activityContext = activityContext;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public AccountInfo getInfo() {
        return info;
    }

    public void setInfo(AccountInfo info) {
        this.info = info;
    }

    public void setOnTransactionCompleteListener(Transaction.TransactionCompleteListener listener) {
        getTx().setOnTransactionCompleteListener(listener);
    }

    public ArrayList<Record> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<Record> records) {
        this.records = records;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public Transaction getTx() {
        return tx;
    }

    public void setTx(Transaction tx) {
        this.tx = tx;
    }
}
