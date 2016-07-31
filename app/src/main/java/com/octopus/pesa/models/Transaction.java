package com.octopus.pesa.models;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.octopus.pesa.models.db.AccountDatabase;
import com.octopus.pesa.models.notifications.Notification;

/**
 * Created by octopus on 6/23/16.
 */
public abstract class Transaction {
    public static final int LOGIN = TempData.AccInfoTransactionID;
    public static final int RECORDS = TempData.RecordsTransactionID;
    public static final int INCOME = TempData.IncomeTransactionID;
    public static final int EXPENSE = TempData.ExpenseTransactionID;
    public static final int ITEMS = TempData.ItemTransactionID;
    public static final int INFO = TempData.AccInfoTransactionID;
    public static final int INIT = TempData.InitTransactionID;
    public TransactionCompleteListener txCompleteListener = null;
    private Context context;
    private Context activityContext;
    private int ID;
    private AccountDatabase db;

    public Transaction(Context context, Context activityContext, int id) {
        setDb(new AccountDatabase(context));
        this.setContext(context);
        this.setActivityContext(activityContext);
        this.ID = id;
    }

    public void executeNow() {
        RunnerTask task = new RunnerTask();
        task.execute();
    }

    public void logTransaction(String message) {
        Log.i("Transaction", message);
    }

    public AccountDatabase getDb() {
        return db;
    }

    public void setDb(AccountDatabase db) {
        this.db = db;
    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    public Context getActivityContext() {
        return activityContext;
    }

    public void setActivityContext(Context activityContext) {
        this.activityContext = activityContext;
    }

    public TransactionCompleteListener getTransactionCompleteListener() {
        return txCompleteListener;
    }

    public void setOnTransactionCompleteListener(TransactionCompleteListener txCompleteListener) {
        this.txCompleteListener = txCompleteListener;
    }

    public void finalize(int id, boolean success) {
        if (getTransactionCompleteListener() != null) {
            getTransactionCompleteListener().onTransactionComplete(id, success);
        }
    }

    /**
     * method is called just before the task is started
     */
    public abstract void beforeExecuting();

    /**
     * ecexute the main logic here
     */
    public abstract boolean execute();

    /**
     * show an updated message here while the task is continuing
     */
    public abstract void whileExecuting();

    /**
     * call this end the transaction
     *
     * @param success the boolean showing if the transaction was successful
     */
    public abstract void endTransaction(boolean success);

    public interface TransactionCompleteListener {
        public void onTransactionComplete(int id, boolean success);
    }

    private class RunnerTask extends AsyncTask<Boolean, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            beforeExecuting();
        }

        @Override
        protected Boolean doInBackground(Boolean... booleens) {
            return Transaction.this.execute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            whileExecuting();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            endTransaction(result);
            Transaction.this.finalize(ID, result);
        }
    }

}
