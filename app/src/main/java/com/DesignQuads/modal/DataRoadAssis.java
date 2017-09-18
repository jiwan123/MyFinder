package com.DesignQuads.modal;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by yodhbir singh on 6/9/2017.
 */

@IgnoreExtraProperties
public class DataRoadAssis {

    public String PlaceName;
    public String LocationPhone;
    public String userId;
    public String services;

    public DataRoadAssis() {}

    public DataRoadAssis(String PlaceName, String LocationPhone,String services,String UserId) {
        this(PlaceName,LocationPhone);
        this.userId = UserId;
        this.services = services;
    }

    public DataRoadAssis(String PlaceName, String LocationPhone) {
        this.PlaceName = PlaceName;
        this.LocationPhone = LocationPhone;

    }
}
