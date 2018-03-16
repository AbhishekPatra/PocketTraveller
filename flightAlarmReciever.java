package com.example.android.remindme;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

public class flightAlarmReciever extends BroadcastReceiver {
    Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
this.context=context;
        Toast.makeText(context,"got",Toast.LENGTH_SHORT).show();
        String mode=intent.getStringExtra("mode");
      //  String t=intent.getDataString();
       // String u=intent.getAction();
        //Toast.makeText(context,mode,Toast.LENGTH_SHORT).show();
       // Log.e("yeahhh",mode);
        //Log.e("yeahhhtttty",t +u);

        Notify("Time for your " +mode +"...");



    }

    public void Notify(String message){
        NotificationCompat.Builder build= new NotificationCompat.Builder(context);
        build.setContentText(message);
        build.setContentTitle("Reminder!");
        build.setTicker("Event!");
        build.setSmallIcon(R.drawable.ic_event_black_24dp);

        Intent intent=new Intent(context,TripActivity.class);
        TaskStackBuilder builder= TaskStackBuilder.create(context);
        builder.addParentStack(TripActivity.class);
        builder.addNextIntent(intent);
        int x=(int ) System.currentTimeMillis();

        PendingIntent pendingIntent=builder.getPendingIntent(x,PendingIntent.FLAG_UPDATE_CURRENT);

        build.setContentIntent(pendingIntent);

        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int cota=(int) System.currentTimeMillis();
        mNotifyMgr.notify(cota,build.build());


    }
}
