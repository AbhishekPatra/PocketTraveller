package com.example.android.remindme.data;

import android.provider.BaseColumns;

/**
 * Created by pussyhunter on 04/04/2017.
 */

public class dataContract {
    
    public  class DateTable implements BaseColumns{
        public static final String ID_DATE=BaseColumns._ID;
        public static final String TABLE_NAME="date_table";
        public static final String DAY= "day";
        public static final String MONTH="month";
        public static final String YEAR="year";

        
    }
    public class ActivityTable implements BaseColumns{
        public static final String ID_ACTIVITY=BaseColumns._ID;
        public static final String TABLE_NAME="activity_table";
        public static final String HOUR="hour";
        public static final String MINUTE="minute";
        public static final String TASK="task";
       // public static final String ALARM_ID="alarm_id";
    }

    public class MatcherTable implements  BaseColumns{
        public static final String ID_MATCHER=BaseColumns._ID;
        public static final String TABLE_NAME="matcher_table";
        public static final String ID_DATE="id_date";
        public static final String ID_ACTIVITY="id_activity";

    }

}
