package com.example.android.quakereportwithhttp;

/**
 * Created by 118168 on 6/14/2017.
 */

public class Location {
    float magnitude;
    String location;
     long date;
    String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Location(float magnitude, String location, long date, String url){
        this.magnitude=magnitude;
        this.location=location;
        this.date=date;
        this.url= url;
    }

    public float getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(float magnitude) {
        this.magnitude = magnitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;


    }
}
