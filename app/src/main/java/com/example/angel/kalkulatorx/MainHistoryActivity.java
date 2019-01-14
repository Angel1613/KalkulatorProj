package com.example.angel.kalkulatorx;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainHistoryActivity extends AppCompatActivity {

    private static final String TAG = "MainHistoryActivity";

    SQLiteCalculatorDBHelper database;

    private ListView hereListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_history);


        hereListView = (ListView) findViewById(R.id.listView);
        //database = new SQLiteCalculatorDBHelper(this);

        populateListView();


    }

    private void populateListView(){
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        SQLiteCalculatorDBHelper dBHelper = SQLiteCalculatorDBHelper.getInstance(getApplicationContext());

        List calculations = dBHelper.selectAll();
        List calculationsWithoutId = new ArrayList();

        for (Object item: calculations) {
            String i[] = (String [])item;
            calculationsWithoutId.add(i[1]);
            Log.d(TAG,(i[0]+": "+i[1]));
        }





        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, calculationsWithoutId);
        hereListView.setAdapter(adapter);

        /*List data = database.selectAll();

        for (Object item: data) {
            String i[] = (String[]) item;

            System.out.println(i[0] + ": " + i[1]);
        }*/
    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
