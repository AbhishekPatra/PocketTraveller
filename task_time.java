package com.example.android.remindme;

import android.os.Parcel;
import android.os.Parcelable;


public class task_time implements Parcelable {
    String time;
    String task;

    public task_time(String time,String task){
        this.time=time;
        this.task=task;
    }
    public task_time(Parcel in){
        time=in.readString();
        task=in.readString();
    }

    public String getTask() {
        return task;
    }

    public String getTime() {
        return time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
dest.writeString(time);
        dest.writeString (task);
    }
    public static final Parcelable.Creator<task_time> CREATOR = new Parcelable.Creator<task_time>() {

        public task_time createFromParcel(Parcel in) {
            return new task_time(in);
        }

        public task_time[] newArray(int size) {
            return new task_time[size];
        }

    };
}
