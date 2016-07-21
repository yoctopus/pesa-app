package com.octopus.pesa.models.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.octopus.pesa.models.AccountInfo;
import com.octopus.pesa.models.Item;
import com.octopus.pesa.models.Record;

import java.util.ArrayList;

/**
 * Created by octopus on 6/22/16.
 */
public class AccountDatabase extends SQLiteOpenHelper {
    public String AssertTag = "Db";

    public AccountDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public AccountDatabase(Context context) {
        this(context, Statement.DBInfo.DB_NAME, null, Statement.DBInfo.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        database.execSQL(Statement.CREATE_TABLE_ACCOUNT_SQL);
        database.execSQL(Statement.CREATE_TABLE_RECORDS_SQL);
        database.execSQL(Statement.CREATE_TABLE_ITEMS_SQL);
        populateDefaults(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {

        Log.d(AssertTag, "This upgrade process will delete all your user info");
        database.execSQL(Statement.DROP_TABLE_ACCOUNT_SQL);
        database.execSQL(Statement.DROP_TABLE_RECORDS_SQL);
        database.execSQL(Statement.DROP_TABLE_ITEMS_SQL);
        onCreate(database);
    }

    private void populateDefaults(SQLiteDatabase db) {
        db.execSQL(Statement.INSERT_DEFAULT_ACCOUNT_SQL);
    }

    //pesa manager database functions
    //functions related to account table
    public AccountInfo getAccountInfo() {
        //// TODO: 6/23/16 code completion
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(Statement.SELECT_TABLE_ACCOUNT_SQL, null);
        c.moveToFirst();
        String name = c.getString(0);
        int pin = c.getInt(1);
        int dailySpent = c.getInt(2);
        int dailyLimit = c.getInt(3);
        int totalBal = c.getInt(4);
        db.close();
        return new AccountInfo(name, pin, dailySpent, dailyLimit, totalBal);
    }

    public boolean UpdateAccountName(String name) {
        //// TODO: 6/23/16 add update name logic
        return UpDateAccountTable(Statement.ColumnNames.ACCOUNT_NAME_COLUMN_NAME, name);
    }

    public boolean UpdateAccountPin(int pin) {
        //// TODO: 6/23/16 add update pin logic
        return UpDateAcoountTable(Statement.ColumnNames.ACCOUNT_PIN_COLUMN_NAME, pin);
    }

    public boolean UpdateAccountDailySpent(int dailySpent) {
        //// TODO: 6/23/16 add update daily balance logic
        return UpDateAcoountTable(Statement.ColumnNames.ACCOUNT_DAILY_SPEND_COLUMN_NAME, dailySpent);
    }

    public boolean UpdateAccountDailyLimit(int dailyLimit) {
        //// TODO: 6/23/16 add update daily balance logic
        return UpDateAcoountTable(Statement.ColumnNames.ACCOUNT_DAILY_LIMIT_COLUMN_NAME, dailyLimit);
    }

    public boolean UpdateAccountTotalBal(int totalBal) {
        //// TODO: 6/23/16 add update total balance
        return UpDateAcoountTable(Statement.ColumnNames.ACCOUNT_TOTALBAL_COLUMN_NAME, totalBal);
    }

    public boolean UpDateAcoountTable(String column, int data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(column, data);
        db.update(Statement.DBInfo.ACCOUNT_TABLE_NAME, values, null, null);
        db.close();
        return true;
    }

    public boolean UpDateAccountTable(String column, String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(column, data);
        db.update(Statement.DBInfo.ACCOUNT_TABLE_NAME, values, null, null);
        db.close();
        return true;
    }

    //functions related to records table
    public ArrayList<Record> getRecords() {
        ArrayList<Record> records = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(Statement.SELECT_TABLE_RECORDS_SQL, null);
        c.moveToFirst();
        while (c.moveToNext()) {
            int id = c.getInt(Statement.ColumnIndex.records_id_column_index);
            String type = c.getString(Statement.ColumnIndex.records_type_column_index);
            String name = c.getString(Statement.ColumnIndex.records_name_column_index);
            int amount = c.getInt(Statement.ColumnIndex.records_amount_column_index);
            int date = c.getInt(Statement.ColumnIndex.records_date_column_index);

            Record r = new Record(id, type, name, amount, date);
            records.add(r);
        }
        db.close();
        return records;
    }

    public boolean addRecord(Record r) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Statement.ColumnNames.RECORDS_TYPE_COLUMN_NAME, r.getType());
        values.put(Statement.ColumnNames.RECORDS_NAME_COLUMN_NAME, r.getNameItem());
        values.put(Statement.ColumnNames.RECORDS_AMOUNT_COLUMN_NAME, r.getAmount());

        values.put(Statement.ColumnNames.RECORDS_DATE_COLUMN_NAME, r.getDatemillis());
        db.insert(Statement.DBInfo.RECORDS_TABLE_NAME, null, values);
        db.close();
        return true;
    }

    public boolean deleteRecord(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Statement.DBInfo.RECORDS_TABLE_NAME, Statement.ColumnNames.RECORDS_ID_COLUMN_NAME + " = " + id, null);
        db.close();
        return true;
    }

    public boolean deleteAllRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.rawQuery(Statement.DELETE_TABLE_RECORDS_SQL, null);
        db.close();
        return true;
    }

    //functions for table items
    public ArrayList<Item> getItems() {
        ArrayList<Item> items = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(Statement.SELECT_TABLE_ITEMS_SQL, null);
        c.moveToFirst();
        while (c.moveToNext()) {
            String type = c.getString(Statement.ColumnIndex.items_type_column_index);
            String name = c.getString(Statement.ColumnIndex.items_name_column_index);
            Item item = new Item(type, name);
            items.add(item);
        }
        return items;
    }

    public boolean addItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Statement.ColumnNames.ITEM_TYPE_COLUMN_NAME, item.getType());
        values.put(Statement.ColumnNames.ITEM_NAME_COLUMN_NAME, item.getName());
        db.insert(Statement.DBInfo.ITEMS_TABLE_NAME, null, values);
        db.close();
        return true;
    }
}
