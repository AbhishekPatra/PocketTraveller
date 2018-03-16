package com.example.android.remindme;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.DatePicker;
import android.widget.TextView;

/**
 * Created by pussyhunter on 03/05/2017.
 */

public class FligthReturnDate extends DatepickerFragment implements DatePickerDialog.OnDateSetListener {
    TextView date;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
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
        //super.onDateSet(view, year, month, dayOfMonth);
        date=(TextView) getActivity().findViewById(R.id.f8);
        date.setText(dayOfMonth +"/" +month +"/" +year);
        sp=getActivity().getSharedPreferences("mobile" , Context.MODE_PRIVATE);
        edit=sp.edit();
        edit.putInt("flightrday" ,dayOfMonth);
        edit.putInt("flightrmonth",month);
        edit.putInt("flightryear",year);
        edit.commit();
    }
}
