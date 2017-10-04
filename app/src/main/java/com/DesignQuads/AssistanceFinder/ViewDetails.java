package com.DesignQuads.AssistanceFinder;

import android.*;
import android.Manifest;
import android.app.Notification;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.*;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.DesignQuads.adapters.CustomBaseAdapter;
import com.DesignQuads.modal.DataAddress;
import com.DesignQuads.modal.DataFuelOpeningHrs;
import com.DesignQuads.modal.DataHospital;
import com.DesignQuads.modal.DataRoadAssis;
import com.DesignQuads.modal.DataServiceStn;
import com.DesignQuads.modal.Fuel;
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
    private DataServiceStn _sss;
    private DataHospital _hsp;
    private Button callBtn;
    private DataAddress _address;
    private DataFuelOpeningHrs _openingHours;
    public String edit_id,type,addressString,callString;
    public TextView placeName;
    public TextView locationPhone,addressTxt,openingHoursWD,openingHoursWE,servies,item;
    public RelativeLayout item_holder;
    public RelativeLayout services_holder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Details view");

        addressString = "";
        callString="";
        mDatabase = FirebaseDatabase.getInstance().getReference();
        placeName = (TextView) findViewById(R.id.placeName);
        locationPhone = (TextView) findViewById(R.id.placeNumber);
        addressTxt = (TextView) findViewById(R.id.addressTxt);
        openingHoursWD = (TextView) findViewById(R.id.openingHoursWD);
        openingHoursWE = (TextView) findViewById(R.id.openingHoursWE);
        servies = (TextView) findViewById(R.id.servies);
        item = (TextView) findViewById(R.id.item);
        item_holder = (RelativeLayout) findViewById(R.id.item_holder);
        services_holder = (RelativeLayout) findViewById(R.id.services_holder);



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
                            callString=_fuelPump.LocationPhone;

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
                            callString=_ra.LocationPhone;

                            services_holder.setVisibility(View.VISIBLE);
                            servies.setVisibility(View.VISIBLE);
                            servies.setText(_ra.services);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }else if(type.equalsIgnoreCase("Service_Stations")){
                mDatabase.child("Service_Stations").orderByKey().equalTo(edit_id).addListenerForSingleValueEvent
                        (new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            _sss = child.getValue(DataServiceStn.class);
                            placeName.setText(_sss.PlaceName);
                            locationPhone.setText(_sss.LocationPhone);
                            callString=_sss.LocationPhone;

                            item_holder.setVisibility(View.VISIBLE);
                            item.setVisibility(View.VISIBLE);
                            item.setText(_sss.item);
                            servies.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
            else if(type.equalsIgnoreCase("Hospitals")){
                mDatabase.child("Hospitals").orderByKey().equalTo(edit_id).addListenerForSingleValueEvent
                        (new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    _hsp = child.getValue(DataHospital.class);
                                    placeName.setText(_hsp.PlaceName);
                                    locationPhone.setText(_hsp.LocationPhone);
                                    callString=_hsp.LocationPhone;

                                    servies.setVisibility(View.INVISIBLE);


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



        Button btn = (Button) findViewById(R.id.callBtn);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel: "+ViewDetails.this.callString));

                if (ActivityCompat.checkSelfPermission(ViewDetails.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        });




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








    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ViewDetails.this, MainActivity.class);
        startActivity(intent);

    }



}
