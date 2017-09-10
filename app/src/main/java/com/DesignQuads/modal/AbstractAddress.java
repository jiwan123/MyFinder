package com.DesignQuads.modal;
import com.google.firebase.database.IgnoreExtraProperties;


/**
 * Created by yodhbir singh on 8/10/2017.
 */
@IgnoreExtraProperties
public class AbstractAddress {

    public String PlaceName;
    public String LocationPhone;
    public String unit_house_number;
    public String street_name;
    public String suburb_name;
    public String post_code;
    public String state;
    public String FuelID;
    public String type;

    public AbstractAddress(){}

    public AbstractAddress(String FuelID, String type, String name, String phone, String unit_house_number, String street_name, String suburb_name, String post_code, String state) {
        this.FuelID = FuelID;
        this.type = type;
        this.PlaceName = name;
        this.LocationPhone = phone;
        this.unit_house_number = unit_house_number;
        this.street_name = street_name;
        this.suburb_name = suburb_name;
        this.post_code = post_code;
        this.state = state;
    }
}
