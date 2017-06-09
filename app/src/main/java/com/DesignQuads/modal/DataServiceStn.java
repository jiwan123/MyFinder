package com.DesignQuads.modal;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by yodhbir singh on 6/9/2017.
 */
@IgnoreExtraProperties
public class DataServiceStn {


    public String PlaceName;
    public String PlaceAddress;
    public String OpeningHrs;
    public String LocationPhone;


    public DataServiceStn(String PlaceName, String PlaceAddress, String OpeningHrs, String LocationPhone) {
        this.PlaceName = PlaceName;
        this.PlaceAddress = PlaceAddress;
        this.OpeningHrs = OpeningHrs;
        this.LocationPhone = LocationPhone;

    }
}
