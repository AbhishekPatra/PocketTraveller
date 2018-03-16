package com.example.android.remindme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.util.LogWriter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.remindme.data.DateDbHelper;

import java.util.ArrayList;
import java.util.List;

public class TripActivity extends AppCompatActivity {
ArrayList<trip> trip;
    DateDbHelper heleper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        trip=new ArrayList<>();
        ListView listView=(ListView) findViewById(R.id.trip_list);
        heleper=new DateDbHelper(this);
        db=heleper.getReadableDatabase();

        Cursor cr=db.rawQuery("select destination,source from flight_table",null);

        if(cr.moveToFirst()){
            do{
                String destination=cr.getString(cr.getColumnIndex("destination"));
                String sourece=cr.getString(cr.getColumnIndex("source"));
                Log.e("name",destination +sourece);
                trip trip1=new trip(destination,sourece);
                trip.add(trip1);


            }
            while (cr.moveToNext());
        }

        cr.close();
        tripAdapter tripAdapter=new tripAdapter(this,trip);
        Log.e("size","" +trip.size());
listView.setAdapter(tripAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView=(TextView) view.findViewById(R.id.s);
               String source= textView.getText().toString();
                Log.e("name",source);

                TextView textView1=(TextView) view.findViewById(R.id.d);
                String dest=textView1.getText().toString();

                Cursor cr1=db.rawQuery("select _id from flight_table where source='" +source +"' and destination='" +dest +"'",null );

                if(cr1.moveToFirst()){
                    do{
                        int main=(int) cr1.getLong(cr1.getColumnIndex("_id"));
                        Log.e("paytm","" +main);
                        Intent intent=new Intent(TripActivity.this,ViewActivity.class);

                        intent.putExtra("flight_id",main);
                        startActivity(intent);

                    }
                    while (cr1.moveToNext());
                }
                cr1.close();

            }
        });




    }
}
