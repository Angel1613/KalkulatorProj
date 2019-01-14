package com.example.angel.kalkulatorx;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    SQLiteCalculatorDBHelper dBHelper = null;

    //Database database = new Database(getBaseContext());
    //FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(getContext());
    //FeedReaderContract FeedEntry = new FeedReaderContract();


    private TextView screen;
    private String display = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dBHelper = SQLiteCalculatorDBHelper.getInstance(getApplicationContext());
// BEGIN ALTERNATIVE
        // Do it either with this
        new OpenDBAsyncTask().execute();

        // or this
        /*
        Boolean status = dBHelper.open();

        if (status) {
            dBHelper.insert("1 * 2 = 2");
            dBHelper.insert("3 + 4 = 7");

            List calculations = dBHelper.selectAll();

            for (Object item: calculations) {
                String i[] = (String [])item;

                System.out.println(i[0]+": "+i[1]);
            }
        }*/
        // END ALTERNATIVE


        screen = (TextView)findViewById(R.id.textView);
        screen.setText(display);
    }
    @Override
    protected void onDestroy() {
        if(dBHelper != null) {
            dBHelper.close();
        }
        super.onDestroy();
    }
    private void updateScreen() {
        screen.setText(display);
    }

    protected void onClickNumber(View v) {
        Button b = (Button) v;
        display += b.getText();
        updateScreen();
    }

    protected void onClickOperator(View v) {
        Button b = (Button) v;
        display += b.getText();
        updateScreen();
    }

    protected void onClickEqual(View v) {
        if(display.length()!=0) {
            JexlEngine tst = new JexlBuilder().cache(512).strict(true).silent(false).create();
            JexlExpression e = tst.createExpression(display);
            JexlContext context = new MapContext();

            String s = e.evaluate(context).toString();
            screen.setText(s);

            dBHelper.insert(display + " = " + s);
            //AddData(display);


            clear();
        }
    }

    public void onClickHistory(View v) {
        Intent intent = new Intent(MainActivity.this, MainHistoryActivity.class);
        startActivity(intent);
    }

    private void clear() {
        display = "";
    }

    protected  void onClickClear(View v) {
        clear();
        updateScreen();
    }


    // Some examples about async:
    // https://www.journaldev.com/9708/android-asynctask-example-tutorial
    // https://www.dev2qa.com/android-asynctask-example/
    private class OpenDBAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            dBHelper.open();
            return null;
        }

        // onPostExecute() is used to update UI component and show the result after async task execute.
        @Override
        protected void onPostExecute(Void result) {
            List calculations = dBHelper.selectAll();

            for (Object item: calculations) {
                String i[] = (String[]) item;

                System.out.println(i[0] + ": " + i[1]);
            }
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

/*
    public void addData(){
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_TITLE, title);
        values.put(FeedEntry.COLUMN_NAME_SUBTITLE, subtitle);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(FeedEntry.TABLE_NAME, null, values);
    }*/

    public void onBtnClearHistoryClick(View view) {
        dBHelper = SQLiteCalculatorDBHelper.getInstance(getApplicationContext());
        dBHelper.onUpgrade(SQLiteCalculatorDBHelper.db, 1, 1);

    }
}
