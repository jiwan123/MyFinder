package com.designsquad.dataSource;

import com.designsquad.modal.Fuel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bimalpariyar on 5/1/17.
 */

public class MyData {

    public  static List<Fuel>fuels;

    public static List getFuel(){

        fuels = new ArrayList<Fuel>();

        fuels.add(new Fuel("fuel 1","9876543",-37.812131, 144.962356));
        fuels.add(new Fuel("fuel 2","1234567",-37.809139, 144.960918));
        fuels.add(new Fuel("fuel 3","3456785",-37.808952, 144.958053));
        fuels.add(new Fuel("fuel 4","6544563",-37.809961, 144.958300));

        fuels.add(new Fuel("fuel A","9876543",-37.786669, 144.832006));
        fuels.add(new Fuel("fuel B","1234567",-37.786135, 144.830804));
        fuels.add(new Fuel("fuel C","3456785",-37.788950, 144.831673));
        fuels.add(new Fuel("fuel D","6544563",-37.788950, 144.831673));

        return fuels;
    }

    public static Fuel getFuelByID(int i){
        return (Fuel) getFuel().get(i);
    }

}
