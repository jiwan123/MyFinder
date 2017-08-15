package com.DesignQuads.modal;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by yodhbir singh on 6/7/2017.
 */
@IgnoreExtraProperties
public class FuelPump {


    public String PlaceName;
    public String LocationPhone;
    public String userId;

    public FuelPump() {}

    public FuelPump(String PlaceName, String LocationPhone,String UserId) {
        this(PlaceName,LocationPhone);
        this.userId = UserId;
    }

    public FuelPump(String PlaceName, String LocationPhone) {
        this.PlaceName = PlaceName;
        this.LocationPhone = LocationPhone;

    }
}
