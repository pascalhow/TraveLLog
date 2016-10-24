package com.pascalhow.travellog.map;

/**
 * Created by pascal on 24/10/2016.
 */

public class MapStartLocation {

    private double latitude = 0;
    private double longitude = 0;

    public MapStartLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
