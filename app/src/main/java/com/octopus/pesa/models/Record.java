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
    private int id;
    private String Type;
    private String NameItem;
    private int Amount;
    private long datemillis;
    private Date date;

    public Record(int id, Item item, int amount, long datemillis) {
        this(id, item.getType(), item.getName(), amount, datemillis);
    }

    public Record(int id, String type, String name, int amount, long datemillis) {
        this.setId(id);
        this.setType(type);
        this.setNameItem(name);
        this.setAmount(amount);
        this.setDatemillis(datemillis);
        calculateDate();
    }

    public Record(Item item, int amount) {
        setType(item.getType());
        setNameItem(item.getName());
        setAmount(amount);
        this.setDatemillis(System.currentTimeMillis());
        calculateDate();
    }

    public String getDateString() {
        return Calc.getDateString(new Date(), datemillis);
    }

    public void calculateDate() {
        this.setDate(Calc.getDate(getDatemillis()));
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

    public void setDatemillis(long datemillis) {
        this.datemillis = datemillis;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
