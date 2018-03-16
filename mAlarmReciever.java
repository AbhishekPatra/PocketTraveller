package com.example.android.remindme;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import com.example.android.remindme.data.DateDbHelper;
import com.example.android.remindme.data.dataContract;

import java.util.ArrayList;
import java.util.Locale;

public class mAlarmReciever extends BroadcastReceiver{
    long id_date;
    ArrayList<Long> y;
    Context context;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context=context;
        String message=null;
        //Cursor cr = null,cr1=null;
      //  Toast.makeText(context,"yeaaaah",Toast.LENGTH_SHORT).show();
        long time=System.currentTimeMillis()+19800000;
        y=new ArrayList<>();
        Calendar cal=Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        cal.setTimeInMillis(time);

        int day=cal.get(Calendar.DAY_OF_MONTH);
        int month=cal.get(Calendar.MONTH);
        int year=cal.get(Calendar.YEAR);
        int hour=cal.get(Calendar.HOUR_OF_DAY);
        int minute=cal.get(Calendar.MINUTE);

        getTask(day,month,year,hour,minute);

        /*DateDbHelper helper=new DateDbHelper(context);
        SQLiteDatabase db=helper.getReadableDatabase();
        String alldata="select * from activity_table";
        String r_query="select task from date_table,matcher_table,activity_table where day=" +day +" and month=" +month +" and hour=" +hour +" and date_table._id=matcher_table._id and matcher_table._id=activity_table._id and minute=" +minute;

        String date=day+"/" +month+"/"+year;
        String mtime=hour +":" +minute;
        //Toast.makeText(context,"" +date + "," +mtime,Toast.LENGTH_SHORT).show();
         cr=db.rawQuery(r_query,null);
        cr1=db.rawQuery(alldata,null);

        if(cr1.moveToFirst())
        {
            do {
                Log.e("cursor", cr1.getString(cr1.getColumnIndex("task"))+" "+cr1.getString(cr1.getColumnIndex("hour"))
                +" "+cr1.getString(cr1.getColumnIndex("minute")));
         //       Mnotify(cr1.getString(cr1.getColumnIndex("task")));
            }
            while (cr1.moveToNext());
        }
        cr1.close();

        Log.e("cursor", r_query);

        if(cr!=null)
        {
            if(cr.moveToFirst())
            {
                Log.e("cursor", cr.getString(0));
                do{
                //    Mnotify(cr.getString(cr.getColumnIndex("task")));
                    Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                }
                while (cr.moveToNext());
            }
            else
                Log.e("cursor","no record");
        }
        else
            Log.e("cursor","no value");
        //Mnotify(message +"ks");

        cr.close();
        //db.close();

        String [] columns={dataContract.ActivityTable.TASK};
        String Where_clause="date_table._id=? and matcher_table._id=? and activity_table._id=? and day=? and month=? and year=? and hour=? and minute=?";
        String [] Where_coloumns={"matcher_table._id","activity_table._id","date_table._id", String.valueOf(day), String.valueOf(month), String.valueOf(year), String.valueOf(hour), String.valueOf(minute)};
         Cursor cr3=db.query("activity_table",columns,Where_clause,Where_coloumns,null,null,null);
     //    cr3.moveToFirst();
        if(cr3.moveToFirst())
        {
            do {
              //  Log.e("cursor", cr1.getString(cr1.getColumnIndex("task"))+" "+cr1.getString(cr1.getColumnIndex("hour"))
                //        +" "+cr1.getString(cr1.getColumnIndex("minute")));
                Log.e("heloo",cr3.getString(cr3.getColumnIndex("task")));
        //               Mnotify(cr3.getString(cr3.getColumnIndex("task")));
            }
            while (cr3.moveToNext());
        }
        cr3.close();
        //Mnotify(cr3.getString(cr3.getColumnIndex("task")));
        //cr3.close();


        /*DateDbHelper helper=new DateDbHelper(context);
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cr = db.rawQuery("SELECT * FROM " + dataContract.DateTable.TABLE_NAME, null);

        //Fragment fragment1;
        while (cr.moveToNext()) {
            //Log.e("IIid" ,"" +cr.getInt(0));

            int index_day = cr.getColumnIndex(dataContract.DateTable.DAY);
            int inex_month = cr.getColumnIndex(dataContract.DateTable.MONTH);
            int index_year = cr.getColumnIndex(dataContract.DateTable.YEAR);

            int mday = cr.getInt(index_day);
            int mmonth = cr.getInt(inex_month);
            int myear = cr.getInt(index_year);
            String mdate = mday + "/" + mmonth + "/" + myear;
            Toast.makeText(context, "cursor " + mdate, Toast.LENGTH_SHORT).show();

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
                 long h=cr1.getLong(cr1.getColumnIndex(dataContract.MatcherTable.ID_ACTIVITY));
                y.add(h);
                //Cursor cr2=db.rawQuery("SELECT task,hour,minute FROM " +dataContract.ActivityTable.TABLE_NAME +" WHERE " +dataContract.ActivityTable.ID_ACTIVITY +"=" +y,null);
                //if(cr2.moveToFirst()){
                  //  do {
                    //    String message=cr2.getString(cr2.getColumnIndex("task"));
                        //int hour=cr2.getInt(cr2.getColumnIndex("hour"));
                        //int minute=cr2.getInt(cr2.getColumnIndex("minute"));
                        //String time=hour+ ":" +minute;
                        //task_time t=new task_time(time,message);
                        //details.add(t);

                      //  Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                   // }
                   // while (cr2.moveToNext());
               // }

            }
            while (cr1.moveToNext());}
*/}

    public void Mnotify(String message){
        NotificationCompat.Builder build= new NotificationCompat.Builder(context);
        build.setContentText(message);
        build.setContentTitle("Reminder!!!!");
        build.setTicker("Event!!!!");
        build.setSmallIcon(R.drawable.ic_event_black_24dp);

        Intent intent=new Intent(context,ViewActivity.class);
        TaskStackBuilder builder= TaskStackBuilder.create(context);
        builder.addParentStack(ViewActivity.class);
        builder.addNextIntent(intent);
int x=(int ) System.currentTimeMillis();

        PendingIntent pendingIntent=builder.getPendingIntent(x,PendingIntent.FLAG_UPDATE_CURRENT);

        build.setContentIntent(pendingIntent);

        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
int cota=(int) System.currentTimeMillis();
        mNotifyMgr.notify(cota,build.build());
    }

    public void getTask(int day,int month,int year,int hour,int minute){
        long id_date=0;
        DateDbHelper helper=new DateDbHelper(context);
        SQLiteDatabase db=helper.getReadableDatabase();

        Cursor cr=db.rawQuery("select _id from date_table where day=" +day +" and month=" +month +" and year=" +year,null);
        if(cr.moveToFirst()){
            do {
                 id_date=cr.getLong(cr.getColumnIndex("_id"));
                Log.e("date_id","" +id_date);
            }
            while(cr.moveToNext());
        }
        cr.close();

        Cursor cr2=db.rawQuery("select id_activity from matcher_table where id_date=" +id_date,null);
        if(cr2.moveToFirst()){
            do{
                long id_activity=cr2.getLong(cr2.getColumnIndex("id_activity"));
                Log.e("id_activity","" +id_activity);
                Cursor cr3=db.rawQuery("select task from activity_table where " +id_activity +"=_id and hour=" +hour +" and minute=" +minute,null);
                if (cr3.moveToFirst()){
                    do {
                        Log.e("task",cr3.getString(cr3.getColumnIndex("task")));
                        Mnotify(cr3.getString(cr3.getColumnIndex("task")));
                    }
                    while(cr3.moveToNext());
                }
                cr3.close();


            }
            while(cr2.moveToNext());
        }
        cr2.close();



    }
}
