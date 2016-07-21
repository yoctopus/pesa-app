package com.octopus.pesa.models.db;

/**
 * Created by octopus on 6/22/16.
 */
public class Statement {

    //table records sql statements
    public static String CREATE_TABLE_RECORDS_SQL = "CREATE TABLE IF NOT EXISTS `Records` (" +
            "`id`INTEGER PRIMARY KEY AUTOINCREMENT," +
            "`Type`TEXT NOT NULL," +
            "`Name`TEXT NOT NULL," +
            "`Amount`INTEGER NOT NULL," +
            "`Date`INTEGER NOT NULL" +
            ");";
    public static String SELECT_TABLE_RECORDS_SQL = "SELECT * FROM Records;";
    public static String DELETE_TABLE_RECORDS_SQL = "DELETE FROM Records;";
    public static String DROP_TABLE_RECORDS_SQL = "DROP TABLE `Records`;";
    //table account sql statements
    public static String CREATE_TABLE_ACCOUNT_SQL = "CREATE TABLE IF NOT EXISTS `Account` (" +
            "`Name`TEXT NOT NULL DEFAULT 'User'," +
            "`Pin`INTEGER NOT NULL DEFAULT 0000," +
            "`DailySpent`INTEGER NOT NULL DEFAULT 0000," +
            "`DailyLimit`INTEGER NOT NULL DEFAULT 0000," +
            "`TotalBal`INTEGER NOT NULL DEFAULT 0000" +
            ");";
    public static String SELECT_TABLE_ACCOUNT_SQL = "SELECT * FROM Account;";
    public static String INSERT_DEFAULT_ACCOUNT_SQL = "INSERT INTO `Account`(Name, Pin)  VALUES(\"USER\", 0);";
    public static String DELETE_TABLE_ACCOUNT_SQL = "DELETE FROM `Account`;";
    public static String DROP_TABLE_ACCOUNT_SQL = "DROP TABLE `Account`;";
    //table items
    public static String CREATE_TABLE_ITEMS_SQL = "CREATE TABLE IF NOT EXISTS `Items` (" +
            "`Type`TEXT NOT NULL," +
            "`Name`TEXT NOT NULL" +
            ");";
    public static String SELECT_TABLE_ITEMS_SQL = "SELECT * FROM Items;";
    public static String DELETE_TABLE_ITEMS_SQL = "DELETE FROM `Items`;";
    public static String DROP_TABLE_ITEMS_SQL = "DROP TABLE `Items`;";

    //database info
    public static class DBInfo {
        public static String DB_NAME = "Pesa";
        public static int DB_VERSION = 1;
        public static String ACCOUNT_TABLE_NAME = "Account";
        public static String RECORDS_TABLE_NAME = "Records";
        public static String ITEMS_TABLE_NAME = "Items";
    }

    //table column names
    public static class ColumnNames {
        //columns for table records
        public static String RECORDS_ID_COLUMN_NAME = "id";
        public static String RECORDS_TYPE_COLUMN_NAME = "Type";
        public static String RECORDS_NAME_COLUMN_NAME = "Name";
        public static String RECORDS_AMOUNT_COLUMN_NAME = "Amount";
        public static String RECORDS_DATE_COLUMN_NAME = "Date";
        //columns for table account
        public static String ACCOUNT_NAME_COLUMN_NAME = "`Name`";
        public static String ACCOUNT_PIN_COLUMN_NAME = "Pin";
        public static String ACCOUNT_DAILY_SPEND_COLUMN_NAME = "DailySpent";
        public static String ACCOUNT_DAILY_LIMIT_COLUMN_NAME = "DailyLimit";
        public static String ACCOUNT_TOTALBAL_COLUMN_NAME = "TotalBal";
        //columns for table items
        public static String ITEM_TYPE_COLUMN_NAME = "Type";
        public static String ITEM_NAME_COLUMN_NAME = "Name";

    }

    public static class ColumnIndex {
        //table account
        public static int account_name_column_index = 0;
        public static int account_pin_column_index = 1;
        public static int account_dailyspent_column_index = 2;
        public static int account_dailylimit_column_index = 3;
        public static int account_totalbal_column_index = 4;
        //table records
        public static int records_id_column_index = 0;
        public static int records_type_column_index = 1;
        public static int records_name_column_index = 2;
        public static int records_amount_column_index = 3;
        public static int records_date_column_index = 4;
        //table items
        public static int items_type_column_index = 0;
        public static int items_name_column_index = 1;
    }

    public static class Types {
        public static String INCOME_TYPE = "I";
        public static String EXPENSE_TYPE = "E";
    }


}
