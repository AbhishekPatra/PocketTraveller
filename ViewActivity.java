package com.example.android.remindme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.remindme.data.DateDbHelper;
import com.example.android.remindme.data.dataContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewActivity extends AppCompatActivity{
ArrayList<String> date_list=new ArrayList<>();
    ArrayList<task_time> details;
    ListView mlistview,nlistview;
    int count=0;
    long id_date;
    String date;
    RequestQueue queue;
    JsonObjectRequest request;
    String hotel_name;
    ActionBarDrawerToggle toggle;
    View fragmnet;
    RelativeLayout relativeLayout;
     FragmentTransaction transaction;
    FragmentManager manager;
    DrawerLayout drawerLayout;
    TextView view2;
    ArrayList<String> places;
    double longi;
    double lati;
    int day;
    int month;
    int year;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        Bundle bundle=getIntent().getExtras();
        int main=bundle.getInt("flight_id");
        Log.e("flight" ,""+main);
        queue= Volley.newRequestQueue(this);
        setContentView(R.layout.activity_view);
        places=new ArrayList<>();
        //nlistview=(ListView) findViewById(R.id.interest);
        view2=(TextView) findViewById(R.id.hotel_name);


        mlistview=(ListView) findViewById(R.id.list1);
        manager=getSupportFragmentManager();
        //drawerLayout=(DrawerLayout) findViewById(R.id.frame);
        //android.app.Fragment fragment =getFragmentManager().findFragmentById(R.id.details);
//        final android.app.Fragment[] fragment = new android.app.Fragment[1];
        DateDbHelper helper=new DateDbHelper(this);
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor u=db.rawQuery("select hotel,longitude,latitude from flight_table where _id=" +main,null);

        if(u.moveToFirst()){
            do{
                hotel_name=u.getString(u.getColumnIndex("hotel"));
                //longi=Double.parseDouble(u.getString(u.getColumnIndex("longitude")));
                //lati=Double.parseDouble(u.getString(u.getColumnIndex("latitude")));
//                Log.e("nip",hotel_name);
                view2.setText(hotel_name);

            }
            while (u.moveToNext());
        }
        u.close();
        /*String url="https://api.foursquare.com/v2/venues/search?ll=" +lati +"%2C" +longi +"&oauth_token=WC0ZIPEPAEBOZGRM022RPSDKCD2GHH0Z51ITSVEDPVPHC5WE&v=20170504";

request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
    @Override
    public void onResponse(JSONObject response) {
        try {
            Log.e("res",response.toString());
            JSONObject request=response.getJSONObject("response");
            JSONArray array=request.getJSONArray("venues");
            int size=array.length();
            for(int i=0;i<size;i++){
                JSONObject on=array.getJSONObject(i);
                String name=on.getString("name");
                Log.e("name",name);
                places.add(name);
            }
            nlistview.setAdapter(new ArrayAdapter<String>(ViewActivity.this,R.layout.support_simple_spinner_dropdown_item,places));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}, new Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError error) {

    }
});
        queue.add(request);*/

        Cursor mb=db.rawQuery("select date_id,flight_id from matcher_table_2 where flight_id=" +main,null);
        if(mb.moveToFirst()){
            do{

                int x=mb.getInt(mb.getColumnIndex("date_id"));
                //int y=mb.getInt(mb.getColumnIndex("flight_id"));
                Log.e("raghu","" +x );
                Cursor cr=db.rawQuery("SELECT * FROM " + dataContract.DateTable.TABLE_NAME +" where _id=" +x,null);


//        Toast.makeText(this,"" +cr.getCount(),Toast.LENGTH_SHORT).show();



                while(cr.moveToNext()){
                    //Log.e("id" ,"" +cr.getLong(cr.getColumnIndex(dataContract.DateTable.ID_DATE)));

                    int day=cr.getColumnIndex(dataContract.DateTable.DAY);
                    int month=cr.getColumnIndex(dataContract.DateTable.MONTH);
                    int year=cr.getColumnIndex(dataContract.DateTable.YEAR);
                    int  mdate=cr.getInt(day);
                    int mmonth=cr.getInt(month);
                    int myaer=cr.getInt(year);


                    date_list.add(mdate +"/" +mmonth +"/" +myaer);
                    Log.e("new",mdate +"/" +mmonth +"/" +myaer);
                }
                //if(cr!=null && !cr.isClosed())
                cr.close();



            }
            while (mb.moveToNext());
        }
        mb.close();

        //Cursor cr=db.rawQuery("SELECT * FROM " + dataContract.DateTable.TABLE_NAME,null);

//        Toast.makeText(this,"" +cr.getCount(),Toast.LENGTH_SHORT).show();


        //while(cr.moveToNext()){
            //Log.e("id" ,"" +cr.getLong(cr.getColumnIndex(dataContract.DateTable.ID_DATE)));

          //  int day=cr.getColumnIndex(dataContract.DateTable.DAY);
            //int month=cr.getColumnIndex(dataContract.DateTable.MONTH);
            //int year=cr.getColumnIndex(dataContract.DateTable.YEAR);
           //int  mdate=cr.getInt(day);
             //int mmonth=cr.getInt(month);
             //int myaer=cr.getInt(year);


            //date_list.add(mdate +"/" +mmonth +"/" +myaer);
        //}
        //if(cr!=null && !cr.isClosed())
        //cr.close();
  //      Toast.makeText(this,"" +date_list.size(),Toast.LENGTH_SHORT).show();

mlistview.setAdapter(new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,date_list));

        mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                details=new ArrayList<task_time>();
                 date = (String) mlistview.getItemAtPosition(position);
    //            Toast.makeText(ViewActivity.this, "" + date, Toast.LENGTH_SHORT).show();

                DateDbHelper helper1 = new DateDbHelper(ViewActivity.this);
                SQLiteDatabase db = helper1.getReadableDatabase();
                Cursor cr = db.rawQuery("SELECT * FROM " + dataContract.DateTable.TABLE_NAME, null);

                Fragment fragment1;
                while (cr.moveToNext()) {
                    //Log.e("IIid" ,"" +cr.getInt(0));

                    int index_day = cr.getColumnIndex(dataContract.DateTable.DAY);
                    int inex_month = cr.getColumnIndex(dataContract.DateTable.MONTH);
                    int index_year = cr.getColumnIndex(dataContract.DateTable.YEAR);

                     day = cr.getInt(index_day);
                     month = cr.getInt(inex_month);
                     year = cr.getInt(index_year);
                    String mdate = day + "/" + month + "/" + year;
                   // Toast.makeText(ViewActivity.this, "cursor " + mdate, Toast.LENGTH_SHORT).show();

                    if (date.equals(mdate)) {
                        id_date = cr.getLong(cr.getColumnIndex(dataContract.DateTable.ID_DATE));
                        //Toast.makeText(ViewActivity.this,"equal " +id_date,Toast.LENGTH_SHORT).show();
                        Log.e("mdate", "from date table" + id_date);
                        cr.close();
                        break;
                    }
                }

                Cursor cr1 = db.rawQuery("SELECT id_activity FROM " + dataContract.MatcherTable.TABLE_NAME + " WHERE " + dataContract.MatcherTable.ID_DATE + "=" + id_date, null);
                if(cr1.moveToFirst()){
                    do {
                        long y=cr1.getLong(cr1.getColumnIndex(dataContract.MatcherTable.ID_ACTIVITY));
                        Cursor cr2=db.rawQuery("SELECT task,hour,minute FROM " +dataContract.ActivityTable.TABLE_NAME +" WHERE " +dataContract.ActivityTable.ID_ACTIVITY +"=" +y,null);
                    if(cr2.moveToFirst()){
                        do {
                            String message=cr2.getString(cr2.getColumnIndex("task"));
                            int hour=cr2.getInt(cr2.getColumnIndex("hour"));
                            int minute=cr2.getInt(cr2.getColumnIndex("minute"));
                            String time=hour+ ":" +minute;
                            task_time t=new task_time(time,message);
                            details.add(t);
                            Log.e("mdate",date+" "+hour+" "+minute+" "+message);

                     //       Toast.makeText(ViewActivity.this,message,Toast.LENGTH_SHORT).show();
                        }
                        while (cr2.moveToNext());
                    }

                    }
                    while (cr1.moveToNext());}
                Intent intent=new Intent(ViewActivity.this,DetailActivity.class);
                intent.putParcelableArrayListExtra("list",details);
                intent.putExtra("day",day);
                intent.putExtra("month",month);
                intent.putExtra("year",year);
                startActivity(intent);
                //bundle.putParcelableArrayList("list",details);

                //TaskFragment fragment2=new TaskFragment();
                //android.app.FragmentManager manager=getFragmentManager();

                //fragment1 = new TaskFragment1();
                //fragment2.setArguments(bundle);
                //TaskFragment prev= (TaskFragment) manager.findFragmentByTag("tag");
                //android.app.FragmentTransaction transaction=manager.beginTransaction();
                //if(prev!=null){
                  //  transaction.remove(prev);
                }
               // fragment2.show(transaction,"tag");


                //transaction.addToBackStack(null);
                //transaction.commit();

        });
        //fragment = new ViewListFragment();
        //transaction.replace(R.id.container,fragment);
        //transaction.commit();
    }

}

