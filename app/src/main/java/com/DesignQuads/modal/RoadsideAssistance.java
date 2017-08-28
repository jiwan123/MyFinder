package com.DesignQuads.modal;

/**
 * Created by yodhbir singh on 8/28/2017.
 */

public class RoadsideAssistance {

    private String name;
    private String phone;
    private double lat;
    private double lng;
    public int distanceInt;

    public RoadsideAssistance() {
    }

    public RoadsideAssistance(String name, String phone, double lat, double lng) {
        this.name = name;
        this.phone = phone;
        this.lat = lat;
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String distance(){
        return (distanceInt < 1000) ? Math.round(distanceInt)+" M" : Math.round(distanceInt/1000)+" KM";
    }
}
