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

        fuels.add(new Fuel("fuel 1","9876543",-37.77505678,144.85198975));
        fuels.add(new Fuel("fuel 2","1234567",-35.77505678,145.85198975));
        fuels.add(new Fuel("fuel 3","3456785",-36.77505678,146.85198975));
        fuels.add(new Fuel("fuel 4","6544563",-36.77505678,143.85198975));

        return fuels;
    }

}
