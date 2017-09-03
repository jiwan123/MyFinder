package com.DesignQuads.modal;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by yodhbir singh on 9/1/2017.
 */
@IgnoreExtraProperties

public class DataFuelOpeningHrs {
    public String WeekDaysAM;
    public String WeekDaysPM;
    public String WeekEndsAM;
    public String WeekEndsPM;

    public String FuelID;

    public DataFuelOpeningHrs(){}

    public DataFuelOpeningHrs(String FuelID,String WeekDaysAM, String WeekDaysPM, String WeekEndsAM, String WeekEndsPM) {
        this.FuelID = FuelID;
        this.WeekDaysAM = WeekDaysAM;
        this.WeekDaysPM = WeekDaysPM;
        this.WeekEndsAM = WeekEndsAM;
        this.WeekEndsPM = WeekEndsPM;

    }
}
