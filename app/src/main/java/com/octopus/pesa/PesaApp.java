package com.octopus.pesa;

import android.app.Activity;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.octopus.pesa.models.Account;
import com.octopus.pesa.models.TempData;
import com.octopus.pesa.models.Transaction;
import com.octopus.pesa.models.transactions.InitAppTransaction;
import com.octopus.pesa.services.PesaService;

import java.io.File;

/**
 * Created by octopus on 7/18/16.
 */
public class PesaApp extends MultiDexApplication implements Transaction.TransactionCompleteListener{
    private Context pesaContext;
    private Activity currentActivity;
    private PesaService pesaService;
    private Account account;
    private Transaction tx;

    private static boolean deleteDir(File file) {
        return false;
    }

    public void clearAppData() {

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        pesaContext = getApplicationContext();
        tx = null;
        fetAccountData();

    }

    private void fetAccountData() {
        tx = new InitAppTransaction(getPesaContext(), null);
        Log.i("App", "Account init");
        tx.setOnTransactionCompleteListener(this);
        tx.executeNow();
    }

    public Context getPesaContext() {
        return pesaContext;
    }

    public void setPesaContext(Context pesaContext) {
        this.pesaContext = pesaContext;
    }

    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }

    public PesaService getPesaService() {
        return pesaService;
    }

    public void setPesaService(PesaService pesaService) {
        this.pesaService = pesaService;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public void onTransactionComplete(int id, boolean success) {
        switch (id) {
            case Transaction.INIT : {
                if (success)
                    account = TempData.account;
                else
                    account = null;
                break;
            }
        }
    }
}
