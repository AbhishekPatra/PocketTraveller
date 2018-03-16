package com.example.android.remindme.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.widget.Toast;

/**
 * Created by pussyhunter on 04/04/2017.
 */

public class DateDbHelper extends SQLiteOpenHelper {
    private static final String NAME="tripplanner";
    private static final int VERSION =1;
    Context context;


   final private String createTable1=        //"CREATE TABLE pets(_id INTEGER, day INTEGER, month INTEGER, year INTEGER);";

    "CREATE TABLE IF NOT EXISTS " +dataContract.DateTable.TABLE_NAME
           +"(" +dataContract.DateTable.ID_DATE +" INTEGER PRIMARY KEY,"
           + dataContract.DateTable.DAY +" INTEGER,"
                +dataContract.DateTable.MONTH +" INTEGER,"
                +dataContract.DateTable.YEAR +" INTEGER);";

    final private String createTable="CREATE TABLE IF NOT EXISTS " +dataContract.ActivityTable.TABLE_NAME
            +"(" +dataContract.ActivityTable.ID_ACTIVITY +" INTEGER PRIMARY KEY,"
            + dataContract.ActivityTable.HOUR +" INTEGER,"
            +dataContract.ActivityTable.MINUTE +" INTEGER,"
            +dataContract.ActivityTable.TASK +" TEXT,alarm_id INTEGER);";

    final private String createTable2=        //"CREATE TABLE pets(_id INTEGER, day INTEGER, month INTEGER, year INTEGER);";

            "CREATE TABLE IF NOT EXISTS " +dataContract.MatcherTable.TABLE_NAME
                    +"(" +dataContract.MatcherTable.ID_MATCHER +" INTEGER PRIMARY KEY,"
                    + dataContract.MatcherTable.ID_DATE +" LONG,"
                    +dataContract.MatcherTable.ID_ACTIVITY +" LONG);";



    public DateDbHelper(Context context) {
        super(context, NAME, null, VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable1);
        db.execSQL(createTable);
        db.execSQL(createTable2);
        db.execSQL("create table if not exists flight_table(" + BaseColumns._ID +" integer primary key,source varchar,destination varchar,src_hour integer,src_minute integer,src_day integer,src_month integer,src_year integer,dest_hour integer,dest_minute integer,dest_day integer,dest_month integer,dest_year integer,mode varchar,hotel varchar,longitude varchar,latitude varchar);");
db.execSQL("create table if not exists matcher_table_2(_id integer primary key,flight_id integer,date_id integer);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//db.execSQL("DROP TABLE IF EXISTS " + dataContract.ActivityTable.TABLE_NAME);
  //      db.execSQL("DROP TABLE IF EXISTS " + dataContract.DateTable.TABLE_NAME);
    //    onCreate(db);
    }
}
