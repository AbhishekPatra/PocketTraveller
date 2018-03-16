package com.example.android.remindme;

import android.app.Fragment;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
Button startActivity;
    Button viewActivity;
    RelativeLayout lay;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        startActivity=(Button) findViewById(R.id.button_create);
        viewActivity=(Button) findViewById(R.id.button_view);
        long time=System.currentTimeMillis()+19800000;
        GregorianCalendar cal= (GregorianCalendar) Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        cal.setTimeInMillis(time);
        lay=(RelativeLayout) findViewById(R.id.container);

        int day=cal.get(Calendar.DAY_OF_MONTH);
        int month=cal.get(Calendar.MONTH);
        int year=cal.get(Calendar.YEAR);
        int hour=cal.get(Calendar.HOUR_OF_DAY);
        int minute=cal.get(Calendar.MINUTE);
        String date=day+"/" +month+"/"+year;
        String mtime=hour +":" +minute;
        //Toast.makeText(this,"" +date +mtime,Toast.LENGTH_SHORT).show();

        //  SimpleDateFormat format=new SimpleDateFormat();
        //Date date=new Date(time);
        //date.getDate();

        startActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,HotelActivity.class);
                startActivity(intent);
                //FlightDetailFragment fragment=new FlightDetailFragment();
                //android.app.FragmentTransaction transaction=getFragmentManager().beginTransaction();
                //transaction.setCustomAnimations(R.animator.from_right,R.animator.to_left);
                //transaction.add(R.id.cont,fragment);
                //transaction.addToBackStack(null);
                //transaction.commit();
               // startActivity.setVisibility(View.INVISIBLE);
               // viewActivity.setVisibility(View.INVISIBLE);


            }
        });

        viewActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,TripActivity.class);
                startActivity(intent);

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        startActivity.setVisibility(View.VISIBLE);
        viewActivity.setVisibility(View.VISIBLE);

    }
}
