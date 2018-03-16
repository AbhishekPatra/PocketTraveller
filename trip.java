package com.example.android.remindme;

/**
 * Created by pussyhunter on 03/05/2017.
 */

public class trip {

    String destination;
    String source;

    trip(String destination,String source){
        this.destination=destination;
        this.source=source;
    }

    public String getDestination() {
        return destination;
    }

    public String getSource() {
        return source;
    }
}
