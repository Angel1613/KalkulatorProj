package com.example.angel.kalkulatorx;


import android.provider.BaseColumns;

public class SQLLiteCalculatorContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private SQLLiteCalculatorContract() {}

    /* Inner class that defines the table contents */
    public static class CalculationEntry implements BaseColumns {
        public static final String TABLE_NAME_CALCULATIONS = "calculations_histrory";
        public static final String COLUMN_NAME_CALCULATION = "calculation";
    }
}
