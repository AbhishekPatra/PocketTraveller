package com.example.android.remindme;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.service.voice.VoiceInteractionSession;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.remindme.data.DateDbHelper;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class FlightActivity extends AppCompatActivity {

    TextView t1, t2, t3, t4, t5, t6, t7, t8;
    RadioGroup group;
    RadioButton Rbut;
    String destination;
    String source;
    RequestQueue queue;
    String hotels;
    String longitude;
    String latitude;
    JsonObjectRequest request;
    Button but, startActivity, viewActivity;
    SharedPreferences sp;
    ArrayList<String> hotel;
    private RadioGroup mFlightRadioGroup;
    private RadioGroup mTrainRadioGroup;
    private RadioGroup mBusRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight);
        final Bundle bundle = getIntent().getExtras();
        destination = bundle.getString("destination");
        source = bundle.getString("sorce");
        longitude = String.valueOf(bundle.getDouble("long"));
        latitude = String.valueOf(bundle.getDouble("lat"));
        mFlightRadioGroup = (RadioGroup) findViewById(R.id.flight_radio_group);
        mTrainRadioGroup = (RadioGroup) findViewById(R.id.train_radio_group);
        mBusRadioGroup = (RadioGroup) findViewById(R.id.bus_radio_group);

        hotel = bundle.getStringArrayList("hotel");
        ListView listView = (ListView) findViewById(R.id.list1);
        TextView textView = (TextView) findViewById(R.id.mera);
        if (hotel.size() == 0) {

            listView.setVisibility(View.INVISIBLE);
            textView.setText("NO HOTELS TO SHOW!!");
        } else {
            ArrayAdapter<String> myadapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, hotel);
            //myadapter.notifyDataSetChanged();

            listView.setAdapter(myadapter);
            listView.setSelector(R.color.colorPrimary);
            listView.setItemChecked(0, true);
        }
        queue = Volley.newRequestQueue(this);
        group = (RadioGroup) findViewById(R.id.group);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.train: {
                        mTrainRadioGroup.setVisibility(View.VISIBLE);
                        mBusRadioGroup.setVisibility(View.GONE);
                        mFlightRadioGroup.setVisibility(View.GONE);
                        break;
                    }
                    case R.id.bus: {
                        mBusRadioGroup.setVisibility(View.VISIBLE);
                        mTrainRadioGroup.setVisibility(View.GONE);
                        mFlightRadioGroup.setVisibility(View.GONE);
                        break;
                    }
                    case R.id.flight: {
                        mBusRadioGroup.setVisibility(View.GONE);
                        mTrainRadioGroup.setVisibility(View.GONE);
                        mFlightRadioGroup.setVisibility(View.VISIBLE);
                        break;
                    }
                }
            }
        });

        startActivity = (Button) findViewById(R.id.button_create);
        viewActivity = (Button) findViewById(R.id.button_view);
        t1 = (TextView) findViewById(R.id.f1);
        t2 = (TextView) findViewById(R.id.f2);
        t3 = (TextView) findViewById(R.id.f3);
        t4 = (TextView) findViewById(R.id.f4);
        t5 = (TextView) findViewById(R.id.f5);
        t6 = (TextView) findViewById(R.id.f6);
        t7 = (TextView) findViewById(R.id.f7);
        t8 = (TextView) findViewById(R.id.f8);


        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlightTime time = new FlightTime();
                time.show(getFragmentManager(), "allow");
            }
        });

        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlightDate date = new FlightDate();
                date.show(getFragmentManager(), "loo");
            }
        });
        t6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlightReturnTime time = new FlightReturnTime();
                time.show(getFragmentManager(), "gty");
            }
        });

        t8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FligthReturnDate date = new FligthReturnDate();
                date.show(getFragmentManager(), "ui");
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hotels = hotel.get(position);
                // view.setBackgroundColor(Color.GREEN);
                Toast.makeText(FlightActivity.this, hotels + " Selected", Toast.LENGTH_SHORT).show();
            }
        });

        but = (Button) findViewById(R.id.but);

        but.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                DateDbHelper helper = new DateDbHelper(FlightActivity.this);
                SQLiteDatabase db = helper.getWritableDatabase();
                int id = group.getCheckedRadioButtonId();
                Rbut = (RadioButton) findViewById(id);
                Rbut.getText();
                Toast.makeText(FlightActivity.this, Rbut.getText().toString(), Toast.LENGTH_SHORT).show();
/*                GregorianCalendar cal;//=//Calendar.getInstance();
                cal= (GregorianCalendar) GregorianCalendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
                cal.set(Calendar.HOUR_OF_DAY,sp.getInt("flighthour",7999));
                cal.set(Calendar.MONTH,sp.getInt("flightmonth",7999));
                cal.set(Calendar.YEAR,sp.getInt("flightyear",7999));
                cal.set(Calendar.DAY_OF_MONTH,sp.getInt("flightday",7999));
                cal.set(Calendar.MINUTE,sp.getInt("flightminute",7999));
                AlarmManager manager=(AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                Intent intent =new Intent(getActivity(),flightAlarmReciever.class);
                PendingIntent pint=PendingIntent.getBroadcast(getActivity(), (int)System.currentTimeMillis(),intent,PendingIntent.FLAG_UPDATE_CURRENT);
                manager.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis()-19800000,pint);*/
                sp = getSharedPreferences("mobile", Context.MODE_PRIVATE);
                int src_hour = sp.getInt("flighthour", 0);
                //String src_name =text1.getText().toString();
                // String dest_name=text2.getText().toString();
                int src_minute = sp.getInt("flightminute", 0);
                int src_day = sp.getInt("flightday", 0);
                int src_month = sp.getInt("flightmonth", 0);
                int src_year = sp.getInt("flightyear", 0);
                int dest_hour = sp.getInt("flightrhour", 0);
                int dest_minute = sp.getInt("flightrminute", 0);
                int dest_day = sp.getInt("flightrday", 0);
                int dest_month = sp.getInt("flightrmonth", 0);
                int dest_year = sp.getInt("flightryear", 0);
                ContentValues values = new ContentValues();
                values.put("source", source);
                values.put("destination", destination);
                values.put("src_day", src_day);
                values.put("src_month", src_month);
                values.put("src_year", src_year);
                values.put("src_minute", src_minute);
                values.put("src_hour", src_hour);
                values.put("dest_day", dest_day);
                values.put("dest_month", dest_month);
                values.put("dest_year", dest_year);
                values.put("dest_minute", dest_minute);
                values.put("dest_hour", dest_hour);
                values.put("hotel", hotels);
                values.put("mode", Rbut.getText().toString());
                values.put("latitude", latitude);
                values.put("longitude", longitude);

                int flight = (int) db.insert("flight_table", null, values);
                GregorianCalendar cal;//=//Calendar.getInstance();
                cal = (GregorianCalendar) GregorianCalendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
                cal.set(Calendar.HOUR_OF_DAY, sp.getInt("flighthour", 0));
                cal.set(Calendar.MONTH, sp.getInt("flightmonth", 0));
                cal.set(Calendar.YEAR, sp.getInt("flightyear", 0));
                cal.set(Calendar.DAY_OF_MONTH, sp.getInt("flightday", 0));
                cal.set(Calendar.MINUTE, sp.getInt("flightminute", 0));
                AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(FlightActivity.this, flightAlarmReciever.class);
                Bundle bundle1 = new Bundle();
                //bundle.putString();
                intent.putExtra("mode", Rbut.getText().toString());
                Log.e("uuuy", Rbut.getText().toString());
                PendingIntent pint = PendingIntent.getBroadcast(FlightActivity.this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
                manager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis() - 19800000, pint);

                GregorianCalendar cal2;//=//Calendar.getInstance();
                cal2 = (GregorianCalendar) GregorianCalendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
                cal2.set(Calendar.HOUR_OF_DAY, sp.getInt("flightrhour", 0));
                cal2.set(Calendar.MONTH, sp.getInt("flightrmonth", 0));
                cal2.set(Calendar.YEAR, sp.getInt("flightryear", 0));
                cal2.set(Calendar.DAY_OF_MONTH, sp.getInt("flightrday", 0));
                cal2.set(Calendar.MINUTE, sp.getInt("flightrminute", 0));

                PendingIntent pint2 = PendingIntent.getBroadcast(FlightActivity.this, (int) System.currentTimeMillis() + 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                manager.set(AlarmManager.RTC_WAKEUP, cal2.getTimeInMillis() - 19800000, pint2);

                //Log.e("yahi h", src_name +src_day+src_hour+src_hour+src_minute+src_month+dest_name+dest_hour+dest_month);


                Intent intent1 = new Intent(FlightActivity.this, NewActivity.class);
                intent1.putExtra("flight", flight);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent1);
            }
        });


    }
}
