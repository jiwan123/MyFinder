package com.DesignQuads.modal;

/**
 * Created by yodhbir singh on 9/3/2017.
 */

public class DataHospitalAddress {
    public String unit_house_number;
    public String street_name;
    public String suburb_name;
    public String post_code;
    public String state;
    public String SerStationID;

    public DataHospitalAddress(){}

    public DataHospitalAddress(String SerStationID,String unit_house_number, String street_name, String suburb_name, String post_code,String state) {
        this.SerStationID = SerStationID;
        this.unit_house_number = unit_house_number;
        this.street_name = street_name;
        this.suburb_name = suburb_name;
        this.post_code = post_code;
        this.state = state;

    }
}
