package com.example.angel.kalkulatorx;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SQLiteCalculatorDBHelper extends SQLiteOpenHelper {
    // From
    // https://medium.com/@kevalpatel2106/how-to-make-the-perfect-singleton-de6b951dfdb0
    // 2. Lazy initialization:
    private static SQLiteCalculatorDBHelper instance;


    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Calculator.db";

    private SQLiteCalculatorDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + SQLLiteCalculatorContract.CalculationEntry.TABLE_NAME_CALCULATIONS + " (" +
                    SQLLiteCalculatorContract.CalculationEntry._ID + " INTEGER PRIMARY KEY," +
                    SQLLiteCalculatorContract.CalculationEntry.COLUMN_NAME_CALCULATION + " TEXT)";

    private static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + SQLLiteCalculatorContract.CalculationEntry.TABLE_NAME_CALCULATIONS;

    // Variable to hold the database instance
    public static SQLiteDatabase db;

    public static SQLiteCalculatorDBHelper getInstance(Context context) {
        if (instance == null){ //if there is no instance available... create new one
            instance = new SQLiteCalculatorDBHelper(context);
        }

        return instance;
    }

    // This method must be implemented.
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    // This method must be implemented.
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }

    public String getSqlDeleteTable() {
        return this.SQL_DELETE_TABLE;
    }
    // Method to open database
    // !!!
    // According to Save data using SQLite tutorial
    // from
    // https://developer.android.com/training/data-storage/sqlite#java
    //
    // Note: Because they can be long-running, be sure that you call getWritableDatabase()
    // or getReadableDatabase() in a background thread, such as with AsyncTask or IntentService.
    //
    // this should be done in separate thread.
    // !!!
    public boolean open()
    {
        try {
            db = getWritableDatabase();
        } catch (SQLException exception) {
            return false;
        }
        return true;
    }

    public void insert(String text) {
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(SQLLiteCalculatorContract.CalculationEntry.COLUMN_NAME_CALCULATION, text);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(SQLLiteCalculatorContract.CalculationEntry.TABLE_NAME_CALCULATIONS, null, values);
    }

    public List selectAll() {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                SQLLiteCalculatorContract.CalculationEntry._ID,
                SQLLiteCalculatorContract.CalculationEntry.COLUMN_NAME_CALCULATION
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                BaseColumns._ID + " DESC";

        Cursor cursor = db.query(
                SQLLiteCalculatorContract.CalculationEntry.TABLE_NAME_CALCULATIONS,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,              // Group the rows
                null,               // Filter by row groups
                sortOrder               // The sort order
        );

        List items = new ArrayList();
        while(cursor.moveToNext()) {
            String item[] = new String[2];
            item[0] = String.valueOf(cursor.getLong(
                    cursor.getColumnIndexOrThrow(SQLLiteCalculatorContract.CalculationEntry._ID)));
            item[1] = cursor.getString(
                    cursor.getColumnIndexOrThrow(SQLLiteCalculatorContract.CalculationEntry.COLUMN_NAME_CALCULATION));
            items.add(item);
        }
        cursor.close();
        return items;
    }

    // Method to close the Database
    public void close()
    {
        db.close();
    }
}
