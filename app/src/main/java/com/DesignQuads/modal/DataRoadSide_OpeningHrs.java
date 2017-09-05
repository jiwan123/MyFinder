package com.DesignQuads.modal;

/**
 * Created by yodhbir singh on 9/5/2017.
 */

public class DataRoadSide_OpeningHrs {

    public String WeekDaysAM;
    public String WeekDaysPM;
    public String WeekEndsAM;
    public String WeekEndsPM;

    public String FuelID;

    public DataRoadSide_OpeningHrs(){}

    public DataRoadSide_OpeningHrs(String FuelID,String WeekDaysAM, String WeekDaysPM, String WeekEndsAM, String WeekEndsPM) {
        this.FuelID = FuelID;
        this.WeekDaysAM = WeekDaysAM;
        this.WeekDaysPM = WeekDaysPM;
        this.WeekEndsAM = WeekEndsAM;
        this.WeekEndsPM = WeekEndsPM;

    }
}
