package com.DesignQuads.modal;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by yodhbir singh on 6/9/2017.
 */
@IgnoreExtraProperties
public class DataServiceStn {


    public String PlaceName;
    public String LocationPhone;
    public String userId;
    public String item;

    public DataServiceStn() {}

    public DataServiceStn(String PlaceName, String LocationPhone,String item,String UserId) {
        this(PlaceName,LocationPhone);
        this.userId = UserId;
        this.item = item;
    }

    public DataServiceStn(String PlaceName, String LocationPhone) {
        this.PlaceName = PlaceName;
        this.LocationPhone = LocationPhone;

    }

}
