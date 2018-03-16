package com.example.android.remindme;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.remindme.data.DateDbHelper;
import com.example.android.remindme.data.dataContract;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by pussyhunter on 04/04/2017.
 */

public class DatepickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    SQLiteDatabase database;
    //int mday;
    //int myear;
    //int mmonth;
    SharedPreferences sp;
    //Intent intent;
    SharedPreferences.Editor edit;
    int day;
    int mmonth;
    int myear;



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //sp=thi.getSharedPreferences("mobile",MODE_PRIVATE);
        sp=this.getActivity().getSharedPreferences("mobile",MODE_PRIVATE);
       edit=sp.edit();
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day
        );
    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        DateDbHelper helper=new DateDbHelper(getActivity());

        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cr=db.rawQuery("select day,month,year from " +dataContract.DateTable.TABLE_NAME +" where day=" +dayOfMonth +" and year=" +year +" and month=" +month +";",null);
        if(cr.moveToFirst()){
            do {
                 day=cr.getInt(cr.getColumnIndex("day"));
                 mmonth=cr.getInt(cr.getColumnIndex("month"));
                 myear=cr.getInt(cr.getColumnIndex("year"));

            }
            while (cr.moveToNext());
        }

        if(day!=0 && mmonth!=0 && myear!=0){
            Toast.makeText(getActivity(),"Already Exists!",Toast.LENGTH_SHORT).show();
        }

        else{edit.putInt("year",year);
            edit.putInt("month",month);
            edit.putInt("day",dayOfMonth);
            edit.apply();
            month=month+1;

            TextView m=(TextView) getActivity().findViewById(R.id.date_text);
            m.setText(dayOfMonth +"/" +month +"/" +year );
            ImageView v=(ImageView) getActivity().findViewById(R.id.date);
            v.setClickable(false);}
    }


}