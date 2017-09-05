package com.DesignQuads.modal;

/**
 * Created by yodhbir singh on 9/5/2017.
 */

public class DataServiceOpeningHrs {

    public String WeekDaysAM;
    public String WeekDaysPM;
    public String WeekEndsAM;
    public String WeekEndsPM;

    public String FuelID;

    public DataServiceOpeningHrs(){}

    public DataServiceOpeningHrs(String FuelID,String WeekDaysAM, String WeekDaysPM, String WeekEndsAM, String WeekEndsPM) {
        this.FuelID = FuelID;
        this.WeekDaysAM = WeekDaysAM;
        this.WeekDaysPM = WeekDaysPM;
        this.WeekEndsAM = WeekEndsAM;
        this.WeekEndsPM = WeekEndsPM;

    }
}
