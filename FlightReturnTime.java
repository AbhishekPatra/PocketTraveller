package com.example.android.remindme;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * Created by pussyhunter on 03/05/2017.
 */

public class FlightReturnTime extends TimepickerFragment implements TimePickerDialog.OnTimeSetListener {
TextView time;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
       // return super.onCreateDialog(savedInstanceState);
        final Calendar c=Calendar.getInstance();
        int hour=c.get(Calendar.HOUR_OF_DAY);
        int minute=c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(),this,hour,minute, DateFormat.is24HourFormat(getActivity()));

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        //super.onTimeSet(view, hourOfDay, minute);
        time=(TextView) getActivity().findViewById(R.id.f6);
        time.setText(hourOfDay +":" +minute);
        sp=getActivity().getSharedPreferences("mobile", Context.MODE_PRIVATE);
        edit=sp.edit();
        edit.putInt("flightrhour",hourOfDay);
        edit.putInt("flightrminute",minute);
        edit.commit();

    }
}
