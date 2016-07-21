package com.octopus.pesa.models;

import com.octopus.pesa.models.db.Statement;

/**
 * Created by octopus on 7/2/16.
 */
public class Item {
    public static String INCOME_TYPE = Statement.Types.INCOME_TYPE;
    public static String EXPENSE_TYPE = Statement.Types.EXPENSE_TYPE;
    private String type;
    private String name;

    public Item(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
