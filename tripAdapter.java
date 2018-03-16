package com.example.android.remindme;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by pussyhunter on 03/05/2017.
 */

public class tripAdapter extends ArrayAdapter<trip> {
    ArrayList<trip> trip;


    public tripAdapter(@NonNull Context context, ArrayList<trip> trip) {
        super(context, R.layout.trip);
        this.trip=trip;
    }

    @Override
    public int getCount() {
        return trip.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        trip trip1=trip.get(position);
       Log.e("yessh",trip1.getDestination()) ;
        View v=convertView;
        if(v==null){
            v= LayoutInflater.from(getContext()).inflate(R.layout.trip,null);
        }
        TextView destination=(TextView) v.findViewById(R.id.d);
        TextView source=(TextView) v.findViewById(R.id.s);

        destination.setText(trip1.getDestination());
        source.setText(trip1.getSource());

return v;
    }
}
