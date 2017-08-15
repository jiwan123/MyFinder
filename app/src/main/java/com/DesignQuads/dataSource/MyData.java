package com.DesignQuads.dataSource;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.widget.Toast;

import com.DesignQuads.modal.DataAddress;
import com.DesignQuads.modal.Fuel;
import com.DesignQuads.modal.FuelPump;
import com.DesignQuads.modal.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MyData {

    public  List<Fuel>fuels;
    public  DatabaseReference mDatabase;
    private Context context;

    public MyData(Context context){
        this.context = context;

        fuels = new ArrayList<Fuel>();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("FuelPumps").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot :dataSnapshot.getChildren()) {
                    final FuelPump fp = postSnapshot.getValue(FuelPump.class);

                    mDatabase.child("Address").orderByChild("FuelID")
                            .startAt(postSnapshot.getKey())
                            .endAt(postSnapshot.getKey()).addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot2) {

                            for (DataSnapshot postSnapshot2 :dataSnapshot2.getChildren()) {
                                DataAddress ad = postSnapshot2.getValue(DataAddress.class);
                                String addressString = ad.unit_house_number+", "+ad.street_name+", "+ad.suburb_name+" "+ad.state+" "+ad.post_code;
                                double[] cords = getLatLongFromAddress(addressString);
//                                Log.v("ttt",fp.PlaceName+", address-> "+addressString);
                                MyData.this.fuels.add(new Fuel(fp.PlaceName,fp.LocationPhone,cords[0],cords[1]));
                                Log.v("ttt", fuels.size()+", size");
                            }

//                            Log.v("ttt", "done");

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError2) {

                        }
                    });
                }

                Log.v("ttt", "done");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Log.v("ttt", fuels.size()+", size");

    }

    public List getFuel(){
        return this.fuels;
    }

    public Fuel getFuelByID(int i){
        return (Fuel) this.fuels.get(i);
    }

    private double[] getLatLongFromAddress(String address)
    {
        double lat= 0.0, lng= 0.0;

        Geocoder geoCoder = new Geocoder(this.context, Locale.getDefault());
        try
        {
            List<Address> addresses = geoCoder.getFromLocationName(address , 1);
            if (addresses.size() > 0)
            {

                lat=addresses.get(0).getLatitude();
                lng=addresses.get(0).getLongitude();

//                Log.v("ttt", ""+lat);
//                Log.v("ttt", ""+lng);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return new double[]{lat,lng};
    }

}
