package com.DesignQuads.modal;
import com.google.firebase.database.IgnoreExtraProperties;
/**
 * Created by yodhbir singh on 6/7/2017.
 */

public class FuelPump {


    public String PlaceName;
    public String PlaceAddress;
    public String OpeningHrs;
    public String LocationPhone;


    public FuelPump(String PlaceName, String PlaceAddress, String OpeningHrs, String LocationPhone) {
        this.PlaceName = PlaceName;
        this.PlaceAddress = PlaceAddress;
        this.OpeningHrs = OpeningHrs;
        this.LocationPhone = LocationPhone;

    }
}
