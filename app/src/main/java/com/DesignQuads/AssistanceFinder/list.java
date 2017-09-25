package com.DesignQuads.AssistanceFinder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.DesignQuads.adapters.CustomBaseAdapter;
import com.DesignQuads.adapters.HospitalCallAdapter;
import com.DesignQuads.adapters.RoadsideBaseAdapter;
import com.DesignQuads.adapters.StationBaseAdapter;
import com.DesignQuads.modal.DataAddress;
import com.DesignQuads.modal.DataHospital;
import com.DesignQuads.modal.DataRoadAssis;
import com.DesignQuads.modal.DataServiceStn;
import com.DesignQuads.modal.Fuel;
import com.DesignQuads.modal.FuelPump;
import com.DesignQuads.modal.HospitalModel;
import com.DesignQuads.modal.RoadsideAssistance;
import com.DesignQuads.modal.Station;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class list extends AppCompatActivity {

    ListView listView;
    List<Fuel> rowItems;

    public DatabaseReference mDatabase;

    public SharedPreferences sharedpreferences;

    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("List");

        String lat;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                lat= null;
            } else {
                lat= extras.getString("LAT");
            }
        } else {
            lat= (String) savedInstanceState.getSerializable("LAT");
        }

        String lng;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                lng= null;
            } else {
                lng= extras.getString("LNG");
            }
        } else {
            lng= (String) savedInstanceState.getSerializable("LNG");
        }
        final LatLng currentLocation = new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));

        listView = (ListView) findViewById(R.id.listview);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        final List<Fuel> allFuels = new ArrayList<>();
        final List<Station> allStations = new ArrayList<>();
        final List<RoadsideAssistance> allRoadsideAssistances = new ArrayList<>();
        final List<HospitalModel> allHospitalModel = new ArrayList<>();

        SharedPreferences shared = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        final String tab_clicked = (shared.getString("tab_clicked", ""));


        mDatabase.child(tab_clicked).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot :dataSnapshot.getChildren()) {
                    if(tab_clicked == "FuelPumps"){


                        final Fuel fuel = new Fuel();
                        final FuelPump fp = postSnapshot.getValue(FuelPump.class);
                        final String key = postSnapshot.getKey();
                        fuel.setName(fp.PlaceName);
                        fuel.setPhone(fp.LocationPhone);

                        mDatabase.child("Address").orderByChild("FuelID")
                                .startAt(postSnapshot.getKey())
                                .endAt(postSnapshot.getKey()).addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot2) {

                                for (DataSnapshot postSnapshot2 :dataSnapshot2.getChildren()) {
                                    DataAddress ad = postSnapshot2.getValue(DataAddress.class);
                                    String addressString = ad.unit_house_number+", "+ad.street_name+", "+ad.suburb_name+" "+ad.state+" "+ad.post_code;
                                    double[] cords = getLatLongFromAddress(addressString);
//                                MyData.this.fuels.add(new Fuel(fp.PlaceName,fp.LocationPhone,cords[0],cords[1]));
                                    fuel.setLat(cords[0]);
                                    fuel.setLng(cords[1]);
                                    fuel.FuelId = key;

                                    Location loc1 = new Location("");
                                    loc1.setLatitude(currentLocation.latitude);
                                    loc1.setLongitude(currentLocation.longitude);

                                    Location loc2 = new Location("");
                                    loc2.setLatitude(cords[0]);
                                    loc2.setLongitude(cords[1]);

                                    fuel.distanceInt = Math.round(loc1.distanceTo(loc2));

                                    allFuels.add(fuel);

                                    CustomBaseAdapter adapter = new CustomBaseAdapter(list.this, allFuels);
                                    listView.setAdapter(adapter);

                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            Intent intent = new Intent(list.this, ViewDetails.class);
                                            intent.putExtra("EXTRA_FUEL_TYPE", tab_clicked);
                                            intent.putExtra("EXTRA_FUEL_ID", allFuels.get(i).FuelId);
                                            list.this.startActivity(intent);
                                        }
                                    });

                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError2) {

                            }
                        });


                    }

                    else if(tab_clicked == "Service_Stations")
                    {

                        final Station station = new Station();
                        final DataServiceStn ss = postSnapshot.getValue(DataServiceStn.class);

                        final String key = postSnapshot.getKey();
                        station.setName(ss.PlaceName);
                        station.setPhone(ss.LocationPhone);

                        mDatabase.child("Address").orderByChild("FuelID")
                                .startAt(postSnapshot.getKey())
                                .endAt(postSnapshot.getKey()).addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot2) {

                                for (DataSnapshot postSnapshot2 :dataSnapshot2.getChildren()) {
                                    DataAddress ad = postSnapshot2.getValue(DataAddress.class);
                                    String addressString = ad.unit_house_number+", "+ad.street_name+", "+ad.suburb_name+" "+ad.state+" "+ad.post_code;
                                    double[] cords = getLatLongFromAddress(addressString);

                                    station.setLat(cords[0]);
                                    station.setLng(cords[1]);
                                    station.FuelId = key;

                                    Location loc1 = new Location("");
                                    loc1.setLatitude(currentLocation.latitude);
                                    loc1.setLongitude(currentLocation.longitude);

                                    Location loc2 = new Location("");
                                    loc2.setLatitude(cords[0]);
                                    loc2.setLongitude(cords[1]);

                                    station.distanceInt = Math.round(loc1.distanceTo(loc2));

                                    allStations.add(station);

                                    Log.v("bbb",allStations.size()+"");

                                    StationBaseAdapter adapter = new StationBaseAdapter(list.this, allStations);
                                    listView.setAdapter(adapter);

                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            Intent intent = new Intent(list.this, ViewDetails.class);
                                            intent.putExtra("EXTRA_FUEL_TYPE", tab_clicked);
                                            intent.putExtra("EXTRA_FUEL_ID", allStations.get(i).FuelId);
                                            list.this.startActivity(intent);
                                        }
                                    });

                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError2) {

                            }
                        });


                    }


                    else if(tab_clicked == "RoadSide_Assistance")
                    {

                        final RoadsideAssistance roadsideassistance= new RoadsideAssistance();
                        final DataRoadAssis ra = postSnapshot.getValue(DataRoadAssis.class);
                        final String key = postSnapshot.getKey();
                        roadsideassistance.setName(ra.PlaceName);
                        roadsideassistance.setPhone(ra.LocationPhone);

                        mDatabase.child("Address").orderByChild("FuelID")
                                .startAt(postSnapshot.getKey())
                                .endAt(postSnapshot.getKey()).addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot2) {

                                for (DataSnapshot postSnapshot2 :dataSnapshot2.getChildren()) {
                                    DataAddress ad = postSnapshot2.getValue(DataAddress.class);
                                    String addressString = ad.unit_house_number+", "+ad.street_name+", "+ad.suburb_name+" "+ad.state+" "+ad.post_code;
                                    double[] cords = getLatLongFromAddress(addressString);

                                    roadsideassistance.setLat(cords[0]);
                                    roadsideassistance.setLng(cords[1]);
                                    roadsideassistance.FuelId = key;

                                    Location loc1 = new Location("");
                                    loc1.setLatitude(currentLocation.latitude);
                                    loc1.setLongitude(currentLocation.longitude);

                                    Location loc2 = new Location("");
                                    loc2.setLatitude(cords[0]);
                                    loc2.setLongitude(cords[1]);

                                    roadsideassistance.distanceInt = Math.round(loc1.distanceTo(loc2));

                                    allRoadsideAssistances.add(roadsideassistance);

                                    Log.v("bbb",allRoadsideAssistances.size()+"");

                                    RoadsideBaseAdapter adapter = new RoadsideBaseAdapter(list.this, allRoadsideAssistances);
                                    listView.setAdapter(adapter);

                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            Intent intent = new Intent(list.this, ViewDetails.class);
                                            intent.putExtra("EXTRA_FUEL_TYPE", tab_clicked);
                                            intent.putExtra("EXTRA_FUEL_ID", allRoadsideAssistances.get(i).FuelId);
                                            list.this.startActivity(intent);
                                        }
                                    });

                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError2) {

                            }
                        });


                    }


                    else if(tab_clicked == "Hospitals")
                    {


                        final HospitalModel hospitalModel= new HospitalModel();
                        final DataHospital hs = postSnapshot.getValue(DataHospital.class);

                        final String key = postSnapshot.getKey();
                        hospitalModel.setName(hs.PlaceName);
                        hospitalModel.setPhone(hs.LocationPhone);


                        mDatabase.child("Address").orderByChild("FuelID")
                                .startAt(postSnapshot.getKey())
                                .endAt(postSnapshot.getKey()).addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot2) {

                                for (DataSnapshot postSnapshot2 :dataSnapshot2.getChildren()) {
                                    DataAddress ad = postSnapshot2.getValue(DataAddress.class);
                                    String addressString = ad.unit_house_number+", "+ad.street_name+", "+ad.suburb_name+" "+ad.state+" "+ad.post_code;
                                    double[] cords = getLatLongFromAddress(addressString);

                                    hospitalModel.setLat(cords[0]);
                                    hospitalModel.setLng(cords[1]);
                                    hospitalModel.FuelId = key;

                                    Location loc1 = new Location("");
                                    loc1.setLatitude(currentLocation.latitude);
                                    loc1.setLongitude(currentLocation.longitude);

                                    Location loc2 = new Location("");
                                    loc2.setLatitude(cords[0]);
                                    loc2.setLongitude(cords[1]);

                                    hospitalModel.distanceInt = Math.round(loc1.distanceTo(loc2));

                                    allHospitalModel.add(hospitalModel);

                                    Log.v("bbb",allHospitalModel.size()+"");

                                    HospitalCallAdapter adapter = new HospitalCallAdapter(list.this,allHospitalModel);
                                    listView.setAdapter(adapter);

                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            Intent intent = new Intent(list.this, ViewDetails.class);
                                            intent.putExtra("EXTRA_FUEL_TYPE", tab_clicked);
                                            intent.putExtra("EXTRA_FUEL_ID", allHospitalModel.get(i).FuelId);
                                            list.this.startActivity(intent);
                                        }
                                    });


                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError2) {

                            }
                        });


                    }



                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        return true;
    }

    public class CustomComparator implements Comparator<Fuel> {
        @Override
        public int compare(Fuel o1, Fuel o2) {
            return (o1.distanceInt < o2.distanceInt) ? -1 : 1;
        }
    }

    private double[] getLatLongFromAddress(String address)
    {
        double lat= 0.0, lng= 0.0;

        Geocoder geoCoder = new Geocoder(this.getApplicationContext(), Locale.getDefault());
        try
        {
            List<android.location.Address> addresses = geoCoder.getFromLocationName(address , 1);
            if (addresses.size() > 0)
            {

                lat=addresses.get(0).getLatitude();
                lng=addresses.get(0).getLongitude();

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return new double[]{lat,lng};
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(list.this, MainActivity.class);
        startActivity(intent);

    }

}
