package com.DesignQuads.modal;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by yodhbir singh on 6/7/2017.
 */
@IgnoreExtraProperties
public class FuelPump {


    public String PlaceName;
    public String Address_btn;
    public String OpeningHrs;
    public String LocationPhone;


    public FuelPump(String PlaceName, String PlaceAddress, String OpeningHrs, String LocationPhone) {
        this.PlaceName = PlaceName;
        this.Address_btn = PlaceAddress;
        this.OpeningHrs = OpeningHrs;
        this.LocationPhone = LocationPhone;

    }
}
