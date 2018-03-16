package com.example.android.remindme;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.remindme.data.DateDbHelper;

import java.util.ArrayList;
import java.util.zip.Inflater;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyHolder> {
Context context;
    ArrayList<task_time> t;
    String clicked_date;
    int day;
    int month;
    int year;
    long id=0;
    int hour=0;
    int minute=0;
    int alarm;

    CustomAdapter(Context context, ArrayList<task_time> t,int clicked_day,int month,int year){
    this.context=context;
    this.t=t;
    day=clicked_day;
    this.month=month
    ;
    this.year=year;
}
    public static class MyHolder extends RecyclerView.ViewHolder{
        TextView task;
        TextView time;
        ImageView delete_view;
        public MyHolder(View itemView) {
            super(itemView);
            task=(TextView) itemView.findViewById(R.id.event_text1);
          time  =(TextView) itemView.findViewById(R.id.time_text1);
            delete_view=(ImageView) itemView.findViewById(R.id.delete_view);
        }
    }

    @Override
    public CustomAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.cardview,parent,false);
        MyHolder holder= new MyHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(CustomAdapter.MyHolder holder, final int position) {
holder.task.setText(t.get(position).getTask());
        holder.time.setText(t.get(position).getTime());
     holder.delete_view.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             //Toast.makeText(context,"delete",Toast.LENGTH_SHORT).show();
             Log.e("current time",t.get(position).getTime());
             new AlertDialog.Builder(context)
                     .setTitle("Delete")
                     .setMessage("Confirm delete the task?")
                     .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                             if(t.size()==1) {
                                 Toast.makeText(context,"last item",Toast.LENGTH_SHORT).show();
                             }
                                 else{
                                 task_time tot = t.get(position);
                                 cancleAlarm();
                                 delete_from_database(day,month,year,t.get(position).getTime());
                                 removeItem(tot);
                             }



                         }
                     })
                     .setNegativeButton("No",null)
                     .show();
                      }
     });
    }

    private void removeItem(task_time tot) {
        int position=t.indexOf(tot);
        t.remove(position);
        notifyItemRemoved(position);

    }

    @Override
    public int getItemCount() {

        return t.size();
    }
    public void delete_from_database(int day,int month,int year,String time){
         id=0;
         hour=0;
         minute=0;
        DateDbHelper helper=new DateDbHelper(context);
        SQLiteDatabase db=helper.getReadableDatabase();
        SQLiteDatabase db1=helper.getWritableDatabase();

        Cursor cr=db.rawQuery("select _id from date_table where day=" +day +" and month=" +month +" and year=" +year,null);
        if(cr.moveToFirst()){
            do{
                 id=cr.getLong(cr.getColumnIndex("_id"));
                Log.e("id from adapter","" +id);
            }
            while (cr.moveToNext());
        }
        cr.close();
Cursor cr1=db.rawQuery("select hour,minute,alarm_id from activity_table",null);

        if(cr1.moveToFirst()){
            do {
                int h=cr1.getInt(cr1.getColumnIndex("hour"));
                int m=cr1.getInt(cr1.getColumnIndex("minute"));

                String m_time=h+ ":" +m;
                if(time.equals(m_time)){
                    hour=h;
                    minute=m;
                    //Toast.makeText(context,hour +"," +minute,Toast.LENGTH_SHORT).s
                    Log.e("dapter date",hour +"," +minute+cr1.getInt(cr1.getColumnIndex("alarm_id")));

                }
            }
            while(cr1.moveToNext());
        }
        cr1.close();

       Cursor cr3= db.rawQuery("select id_activity from matcher_table where id_date=" +id,null);
        if(cr3.moveToFirst()){
            do {
                long id_act=cr3.getLong(cr3.getColumnIndex("id_activity"));
                //int id_alarm=cr3.getInt(cr3.getColumnIndex("alarm_id"));
                //cancleAlarm(id_alarm);
                db1.execSQL("delete from activity_table where _id=" +id_act +" and hour=" +hour +" and minute=" +minute);
            }
            while (cr3.moveToNext());
        }
cr3.close();
        Cursor cr5=db.rawQuery("select _id from activity_table where hour=" +hour +" and minute=" +minute,null);

        if(cr5.moveToFirst()){
            do {
                long mid=cr5.getLong(cr5.getColumnIndex("_id"));
                db1.execSQL("delete from matcher_table where id_activity=" +mid +" and id_date=" +id );
            }while (cr5.moveToNext());
        }
        cr5.close();

    }

    public void cancleAlarm(){
        DateDbHelper helper=new DateDbHelper(context);

        SQLiteDatabase db=helper.getReadableDatabase();

        Cursor cr3= db.rawQuery("select id_activity from matcher_table where id_date=" +id,null);
        if(cr3.moveToFirst()){
            do {
                long id_act=cr3.getLong(cr3.getColumnIndex("id_activity"));
                Log.e(" frm matcher of activty" ,""+id_act );
                Cursor cr1=db.rawQuery("select alarm_id from activity table where hour=" +hour +" and minute=" +minute +" and _id=" +id_act,null);
if(cr1.moveToFirst()) {
    do {
        alarm = cr1.getInt(cr1.getColumnIndex("alarm_id"));
        Log.e("alarm","" +alarm);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent mintent = new Intent(context, mAlarmReciever.class);

        PendingIntent intent = PendingIntent.getBroadcast(context, alarm, mintent, PendingIntent.FLAG_UPDATE_CURRENT);
        manager.cancel(intent);
        Toast.makeText(context,"Alarm Cancelled",Toast.LENGTH_SHORT).show();
    }
    while (cr1.moveToNext());
    }
    cr1.close();

                //int id_alarm=cr3.getInt(cr3.getColumnIndex("alarm_id"));
                //cancleAlarm(id_alarm);

                //db1.execSQL("delete from activity_table where _id=" +id_act +" and hour=" +hour +" and minute=" +minute);


            }
            while (cr3.moveToNext());
        }
        cr3.close();

        //AlarmManager manager=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        //PendingIntent pendingintent=PendingIntent.getBroadcast(context,)
    }
}
