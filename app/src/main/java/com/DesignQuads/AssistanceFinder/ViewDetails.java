package com.DesignQuads.AssistanceFinder;

import android.content.Intent;
import android.location.*;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.DesignQuads.modal.DataAddress;
import com.DesignQuads.modal.DataFuelOpeningHrs;
import com.DesignQuads.modal.DataRoadAssis;
import com.DesignQuads.modal.FuelPump;
import com.DesignQuads.modal.RoadsideAssistance;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;

public class ViewDetails extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FuelPump _fuelPump;
    private DataRoadAssis _ra;
    private DataAddress _address;
    private DataFuelOpeningHrs _openingHours;
    public String edit_id,type,addressString;
    public TextView placeName;
    public TextView locationPhone,addressTxt,openingHoursWD,openingHoursWE,servies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);
        addressString = "";
        mDatabase = FirebaseDatabase.getInstance().getReference();
        placeName = (TextView) findViewById(R.id.placeName);
        locationPhone = (TextView) findViewById(R.id.placeNumber);
        addressTxt = (TextView) findViewById(R.id.addressTxt);
        openingHoursWD = (TextView) findViewById(R.id.openingHoursWD);
        openingHoursWE = (TextView) findViewById(R.id.openingHoursWE);
        servies = (TextView) findViewById(R.id.servies);

        edit_id = getIntent().getStringExtra("EXTRA_FUEL_ID");
        type = getIntent().getStringExtra("EXTRA_FUEL_TYPE");

        if(edit_id == null){
            edit_id = "";
        }

        if(edit_id != ""){

            if(type.equalsIgnoreCase("FuelPumps")) {
                mDatabase.child("FuelPumps").orderByKey().equalTo(edit_id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            _fuelPump = child.getValue(FuelPump.class);
                            placeName.setText(_fuelPump.PlaceName);
                            locationPhone.setText(_fuelPump.LocationPhone);
                            servies.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }else if(type.equalsIgnoreCase("RoadSide_Assistance")){
                mDatabase.child("RoadSide_Assistance").orderByKey().equalTo(edit_id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            _ra = child.getValue(DataRoadAssis.class);
                            placeName.setText(_ra.PlaceName);
                            locationPhone.setText(_ra.LocationPhone);
                            servies.setVisibility(View.VISIBLE);
                            servies.setText(_ra.services);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            mDatabase.child("Address").orderByChild("FuelID").equalTo(edit_id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        _address = child.getValue(DataAddress.class);
                        addressTxt.setText(_address.unit_house_number+" "+_address.street_name+" "+_address.suburb_name+" "+_address.post_code+" "+_address.state);
                        addressString = _address.unit_house_number + ", " + _address.street_name + ", " + _address.suburb_name + " " +
                                "" + _address.state + " " + _address.post_code;
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            mDatabase.child("OpeningHrs").orderByChild("FuelID").equalTo(edit_id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        _openingHours = child.getValue(DataFuelOpeningHrs.class);
                        openingHoursWD.setText(_openingHours.WeekDaysAM.split(" ")[0]+" "+_openingHours.WeekDaysAM.split(" ")[1]+" - "+_openingHours.WeekDaysPM.split(" ")[0]+" "+_openingHours.WeekDaysPM.split(" ")[1]);
                        openingHoursWE.setText(_openingHours.WeekEndsAM.split(" ")[0]+" "+_openingHours.WeekEndsAM.split(" ")[1]+" - "+_openingHours.WeekEndsPM.split(" ")[0]+" "+_openingHours.WeekEndsPM.split(" ")[1]);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

        Button navButton = (Button) findViewById(R.id.navigationBtn);
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr="+getLatLongFromAddress(addressString)[0]+","+getLatLongFromAddress(addressString)[1]));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_LAUNCHER );
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        });

    }

    private double[] getLatLongFromAddress(String address) {
        double lat = 0.0, lng = 0.0;

        Geocoder geoCoder = new Geocoder(this.getApplicationContext(), Locale.getDefault());
        try {
            List<android.location.Address> addresses = geoCoder.getFromLocationName(address, 1);
            if (addresses.size() > 0) {

                lat = addresses.get(0).getLatitude();
                lng = addresses.get(0).getLongitude();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new double[]{lat, lng};
    }
}
