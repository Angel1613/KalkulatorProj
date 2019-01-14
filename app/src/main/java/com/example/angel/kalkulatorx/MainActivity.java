package com.example.angel.kalkulatorx;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    SQLiteCalculatorDBHelper dBHelper = null;

    private TextView screen;
    private String display = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dBHelper = SQLiteCalculatorDBHelper.getInstance(getApplicationContext());

        new OpenDBAsyncTask().execute();

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
            Calculator calc = new Calculator();
            Number n = calc.calculate(display);
            String s = n.toString();
            Log.d("Debugger", "s: " + s);
            screen.setText(s);
            dBHelper.insert(display + " = " + s);
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

    private class OpenDBAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            dBHelper.open();
            return null;
        }

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

    public void onBtnClearHistoryClick(View view) {
        dBHelper = SQLiteCalculatorDBHelper.getInstance(getApplicationContext());
        dBHelper.onUpgrade(SQLiteCalculatorDBHelper.db, 1, 1);

    }
}
