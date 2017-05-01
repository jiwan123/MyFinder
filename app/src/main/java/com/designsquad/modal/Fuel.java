package com.designsquad.modal;

/**
 * Created by bimalpariyar on 5/1/17.
 */

public class Fuel {
    private String name;
    private String phone;
    private double lat;
    private double lng;

    public Fuel() {
    }

    public Fuel(String name, String phone, double lat, double lng) {
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
}
