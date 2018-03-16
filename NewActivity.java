package com.example.android.remindme;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.remindme.data.DateDbHelper;
import com.example.android.remindme.data.dataContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class NewActivity extends AppCompatActivity {
    FloatingActionButton add, new_;
    int Day;
    int Year;
    int month;
    int hour;
    int minute;
    int count;
    String seletesd_place;
    int pending_flag;
    long date_id;
    long time_id;
    int alarm_id;
    ImageView date, time;
    TextView m, date_text;
    ListView listView;
    double longi, lati;
    RequestQueue queue;
    ArrayList<String> places;
    SharedPreferences sp;
    SharedPreferences.Editor medit;
    // EditText edit;
    DatepickerFragment datepicker;
    int flight;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        flight = bundle.getInt("flight");
        queue = Volley.newRequestQueue(this);
        setContentView(R.layout.activity_new);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        listView = (ListView) findViewById(R.id.edit);
        date_text = (TextView) findViewById(R.id.date_text);
        sp = getSharedPreferences("mobile", MODE_PRIVATE);
        medit = sp.edit();
        add = (FloatingActionButton) findViewById(R.id.add);
        m = (TextView) findViewById(R.id.m);
        new_ = (FloatingActionButton) findViewById(R.id.new_);
        add.setVisibility(View.INVISIBLE);
        new_.setVisibility(View.INVISIBLE);
        //listView.setAdapter(new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,places));

        //   edit=(EditText) findViewById(R.id.edit);
        listView.setVisibility(View.INVISIBLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                seletesd_place=places.get(position);
                Toast.makeText(NewActivity.this,seletesd_place,Toast.LENGTH_SHORT).show();
            }
        });

        DateDbHelper helper = new DateDbHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor u = db.rawQuery("select hotel,longitude,latitude from flight_table where _id=" + flight, null);

        if (u.moveToFirst()) {
            do {
                //hotel_name=u.getString(u.getColumnIndex("hotel"));
                longi = Double.parseDouble(u.getString(u.getColumnIndex("longitude")));
                lati = Double.parseDouble(u.getString(u.getColumnIndex("latitude")));
//                Log.e("nip",hotel_name);
                //view2.setText(hotel_name);

            }
            while (u.moveToNext());
        }
        u.close();
        makeList();

        m.setVisibility(View.INVISIBLE);
        date = (ImageView) findViewById(R.id.date);
        time = (ImageView) findViewById(R.id.time);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepicker = new DatepickerFragment();
                datepicker.show(getFragmentManager(), "Date Picker");

            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimepickerFragment timefragment = new TimepickerFragment();
                timefragment.show(getFragmentManager(), "Time Picker");
                Log.e("activity", "" + Day + Year + month + hour + minute);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Day = sp.getInt("day", 5);
                Year = sp.getInt("year", 0);
                month = sp.getInt("month", 0);
                hour = sp.getInt("hour", 0);
                minute = sp.getInt("minute", 0);
                //pending_flag=pending_flag++;
                medit.putInt("flag", pending_flag);
                alarm_id = setAlarm(Day, month, Year, hour, minute);

                if (count == 0) {
                    date_id = insert_in_date_table(Day, month, Year);
                    Log.e("curo", "" + date_id);
                    insert_in_matcher_flight(flight, date_id);
                    count = 1;

                }
                time_id = insert_in_time_table(hour, minute, seletesd_place);
                Toast.makeText(NewActivity.this,seletesd_place,Toast.LENGTH_SHORT).show();
                insert_in_matcher_table(date_id, time_id);
                //insert_in_matcher_flight(flight,date_id);


                //Toast.makeText(NewActivity.this,Day +"," +Year +"," +month +"," +hour +"," +minute +"," +edit.getText().toString(),Toast.LENGTH_LONG).show();
                TextView time_text = (TextView) findViewById(R.id.time_text);
                time_text.setText("");

                medit.putInt("hour", 0);
                medit.putInt("minute", 0);
                medit.apply();
                listView.setVisibility(View.INVISIBLE);
                m.setVisibility(View.INVISIBLE);
                new_.setVisibility(View.VISIBLE);
                add.setVisibility(View.INVISIBLE);

            }
        });

        new_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView date_text = (TextView) findViewById(R.id.date_text);
                date_text.setText("");
                medit.putInt("day", 0);
                medit.putInt("month", 0);
                medit.putInt("year", 0);
                medit.apply();
                date.setClickable(true);
                date_text.setText("Enter New Date");
                new_.setVisibility(View.INVISIBLE);
                count = 0;
            }
        });

    }

    public long insert_in_time_table(int hour, int minute, String task) {
        DateDbHelper helper = new DateDbHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dataContract.ActivityTable.HOUR, hour);
        values.put(dataContract.ActivityTable.MINUTE, minute);
        values.put(dataContract.ActivityTable.TASK, task);
        values.put("alarm_id", alarm_id);
        long id = db.insert(dataContract.ActivityTable.TABLE_NAME, null, values);

        //Toast.makeText(NewActivity.this,"id time" +id,Toast.LENGTH_SHORT).show();
        return id;

    }

    public long insert_in_date_table(int day, int month, int year) {
        DateDbHelper helper = new DateDbHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(dataContract.DateTable.DAY, day);
        values.put(dataContract.DateTable.MONTH, month);
        values.put(dataContract.DateTable.YEAR, year);
        long id = db.insert(dataContract.DateTable.TABLE_NAME, null, values);
        //Toast.makeText(NewActivity.this,"id date" +id,Toast.LENGTH_SHORT).show();
        return id;
    }

    public void insert_in_matcher_table(long i, long j) {
        DateDbHelper helper = new DateDbHelper(NewActivity.this);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dataContract.MatcherTable.ID_DATE, i);
        values.put(dataContract.MatcherTable.ID_ACTIVITY, j);
        db.insert(dataContract.MatcherTable.TABLE_NAME, null, values);
    }

    public void insert_in_matcher_flight(long i, long j) {
        DateDbHelper helper = new DateDbHelper(NewActivity.this);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("flight_id", i);
        values.put("date_id", j);
        db.insert("matcher_table_2", null, values);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public int setAlarm(int day, int month, int year, int hour, int minute) {
        //Toast.makeText(NewActivity.this,"in manger",Toast.LENGTH_SHORT).show();
        GregorianCalendar cal;//=//Calendar.getInstance();
        cal = (GregorianCalendar) GregorianCalendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        //cal.setTimeInMillis(System.currentTimeMillis()+19800000);
        // cal.clear();
        cal.set(Calendar.MONTH, month);

        cal.set(Calendar.DAY_OF_MONTH, day);

        cal.set(Calendar.YEAR, year);

        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);

        //int y= cal.get(Calendar.HOUR_OF_DAY);
        //int u=cal.get(Calendar.MINUTE);
        //int o=cal.get(Calendar.DAY_OF_MONTH);
        //int r=cal.get(Calendar.YEAR);
        //int j=cal.get(Calendar.MONTH);
        int pending_count = (int) System.currentTimeMillis();

        Intent intent = new Intent(NewActivity.this, mAlarmReciever.class);

        PendingIntent pintent = PendingIntent.getBroadcast(NewActivity.this, pending_count, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager manager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        manager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis() - 19800000, pintent);

        // Toast.makeText(NewActivity.this,"time in alarm" +o+r ,Toast.LENGTH_SHORT).show();
        return pending_count;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(NewActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void makeList() {
        places=new ArrayList<>();
        String url = "https://api.foursquare.com/v2/venues/search?ll=" + lati + "%2C" + longi + "&oauth_token=WC0ZIPEPAEBOZGRM022RPSDKCD2GHH0Z51ITSVEDPVPHC5WE&v=20170504";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.e("res", response.toString());
                    JSONObject request = response.getJSONObject("response");
                    JSONArray array = request.getJSONArray("venues");
                    int size = array.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject on = array.getJSONObject(i);
                        String name = on.getString("name");
                        Log.e("name", name);
                        places.add(name);
                    }
                    Toast.makeText(NewActivity.this,   "" +places.size(),Toast.LENGTH_SHORT).show();
                    listView.setAdapter(new ArrayAdapter<String>(NewActivity.this, R.layout.support_simple_spinner_dropdown_item, places));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(request);

    }
}




