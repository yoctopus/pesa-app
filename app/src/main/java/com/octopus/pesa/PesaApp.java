package com.octopus.pesa;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.octopus.pesa.models.Account;
import com.octopus.pesa.models.Transaction;
import com.octopus.pesa.services.PesaService;

import java.io.File;

/**
 * Created by octopus on 7/18/16.
 */
public class PesaApp extends Application implements Transaction.TransactionCompleteListener{
    private Context pesaContext;
    private Activity currentActivity;
    private PesaService pesaService;
    private Account account;

    private static boolean deleteDir(File file) {
        return false;
    }

    public void clearAppData() {

    }


    @Override
    public void onCreate() {
        super.onCreate();
        pesaContext = getApplicationContext();
        //fetAccountData();

    }

    private void fetAccountData() {
        account = new Account(pesaContext);
        account.setOnTransactionCompleteListener(this);
        account.InitAccount();
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
                account = TempData.account;
                break;
            }
        }
    }
}
