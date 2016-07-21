package com.octopus.pesa;

import com.octopus.pesa.models.Account;
import com.octopus.pesa.models.AccountInfo;
import com.octopus.pesa.models.Item;
import com.octopus.pesa.models.Record;

import java.util.ArrayList;

/**
 * Created by octopus on 6/19/16.
 */
public class TempData {
    public static final String FRAGMENTID = "fragmentID";
    public static final int incomeChoice = 0;
    public static final int expenseChoice = 1;
    public static final int balanceChoice = 2;
    //transaction ids
    public static final int AccInfoTransactionID = 0;
    public static final int RecordsTransactionID = 1;
    public static final int IncomeTransactionID = 2;
    public static final int ExpenseTransactionID = 3;
    public static final int ItemTransactionID = 4;
    public static final int LoginTransactionID = 5;
    public static final int InitTransactionID = 6;
    public static final int AccountInitiationID = 10;
    //temporary data
    public static Account account = null;
    public static ArrayList<Record> records = null;
    public static AccountInfo info = null;
    public static ArrayList<Item> items = null;


}
