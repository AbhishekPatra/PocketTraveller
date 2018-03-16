package com.example.android.remindme;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.android.remindme.data.DateDbHelper;
import com.example.android.remindme.data.dataContract;

import org.w3c.dom.Text;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.PRINT_SERVICE;

/**
 * Created by pussyhunter on 04/04/2017.
 */

public class TimepickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
int Hour;
    int minute;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    //android.app.FragmentManager fragmentManager;
    //android.app.FragmentTransaction transaction;
    Activity activity;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        activity=getActivity();

        final Calendar c=Calendar.getInstance();
        int hour=c.get(Calendar.HOUR_OF_DAY);
        int minute=c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(),this,hour,minute, DateFormat.is24HourFormat(getActivity()));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        sp=this.getActivity().getSharedPreferences("mobile",MODE_PRIVATE);
        edit= sp.edit();
        edit.putInt("hour",hourOfDay);
        edit.putInt("minute",minute);
        edit.apply();

        TextView m=(TextView) getActivity().findViewById(R.id.time_text);
        m.setText(hourOfDay +":" +minute);
        TextView h=(TextView) getActivity().findViewById(R.id.m);
        //EditText edit=(EditText) getActivity().findViewById(R.id.edit);
        ListView list=(ListView) getActivity().findViewById(R.id.edit);
        h.setVisibility(View.VISIBLE);
        FloatingActionButton s=(FloatingActionButton) getActivity().findViewById(R.id.add);
        s.setVisibility(View.VISIBLE);
        list.setVisibility(View.VISIBLE);

    }

}
