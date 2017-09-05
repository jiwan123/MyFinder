package com.DesignQuads.modal;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by yodhbir singh on 6/9/2017.
 */
@IgnoreExtraProperties
public class DataHospital {

    public String PlaceName;
    public String LocationPhone;
    public String userId;

    public DataHospital() {}

    public DataHospital(String PlaceName, String LocationPhone,String UserId) {
        this(PlaceName,LocationPhone);
        this.userId = UserId;
    }

    public DataHospital(String PlaceName, String LocationPhone) {
        this.PlaceName = PlaceName;
        this.LocationPhone = LocationPhone;

    }
}
