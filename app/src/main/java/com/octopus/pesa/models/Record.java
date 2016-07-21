package com.octopus.pesa.models;

import com.octopus.pesa.models.db.Statement;
import com.octopus.pesa.models.math.Calc;

import java.util.Date;

/**
 * Created by octopus on 6/18/16.
 */
public class Record {
    public static String Income = Statement.Types.INCOME_TYPE;
    public static String Expense = Statement.Types.EXPENSE_TYPE;
    public int id;
    public String Type;
    public String NameItem;
    public int Amount;
    public long datemillis;
    public Date date;

    public Record(int id, Item item, int amount, long datemillis) {
        this(id, item.getType(), item.getName(), amount, datemillis);
    }

    public Record(int id, String type, String name, int amount, long datemillis) {
        this.id = id;
        this.Type = type;
        this.NameItem = name;
        this.Amount = amount;
        this.datemillis = datemillis;
    }

    public Record(Item item, int amount) {
        Type = item.getType();
        NameItem = item.getName();
        Amount = amount;
        calculateDate();
        this.datemillis = System.currentTimeMillis();
    }

    public void calculateDate() {
        this.date = Calc.getDate(System.currentTimeMillis());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getNameItem() {
        return NameItem;
    }

    public void setNameItem(String nameItem) {
        NameItem = nameItem;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public long getDatemillis() {
        return datemillis;
    }

    public void setDatemillis(int datemillis) {
        this.datemillis = datemillis;
    }
}
