package com.DesignQuads.AssistanceFinder;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;

import com.DesignQuads.adapters.UnTagAdapter;
import com.DesignQuads.dataSource.MyData;
import com.DesignQuads.modal.DataAddress;
import com.DesignQuads.modal.DataHospital;
import com.DesignQuads.modal.DataHospitalAddress;
import com.DesignQuads.modal.DataRoadAddress;
import com.DesignQuads.modal.DataRoadAssis;
import com.DesignQuads.modal.DataServiceAddress;
import com.DesignQuads.modal.DataServiceStn;
import com.DesignQuads.modal.Fuel;
import com.DesignQuads.modal.FuelPump;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private static final long LOCATION_REFRESH_TIME = 100;
    private static final float LOCATION_REFRESH_DISTANCE = 100;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    private GoogleMap mMap;
    private LocationManager mLocationManager;
    Geocoder geocoder = null;
    double currentLongitude, currentLatitude;
    public ImageButton btn_service;
    public ImageButton btn_fuel;
    public ImageButton btn_pickup;
    public ImageButton btn_road;
    public Button btn_list;

    public SharedPreferences sharedpreferences;

    public static final String MyPREFERENCES = "MyPrefs";

    private GoogleApiClient googleApiClient;
    final static int REQUEST_LOCATION = 199;


    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        geocoder = new Geocoder(this, Locale.getDefault());


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        btn_service = (ImageButton) findViewById(R.id.btn_service);
        btn_fuel = (ImageButton) findViewById(R.id.btn_fuel);
        btn_pickup = (ImageButton) findViewById(R.id.btn_pickup);
        btn_road = (ImageButton) findViewById(R.id.btn_road);
        btn_list = (Button) findViewById(R.id.btn_list);


        final MyData mydata = new MyData(this.getApplicationContext());

        btn_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_service();
            }
        });
        btn_fuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_fuel();
            }
        });
        btn_pickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_roadside();
            }
        });
        btn_road.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_roadside();
            }
        });

        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, list.class);
                intent.putExtra("LAT", currentLatitude + "");
                intent.putExtra("LNG", currentLongitude + "");
                startActivity(intent);

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Intent intent = new Intent(this, list.class);
        intent.putExtra("LAT", currentLatitude + "");
        intent.putExtra("LNG", currentLongitude + "");
        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }

    public void show_service() {
        googleMap.animateCamera( CameraUpdateFactory.zoomTo( 9.0f ) );

        btn_service.setVisibility(View.INVISIBLE);
        btn_fuel.setVisibility(View.INVISIBLE);
        btn_pickup.setVisibility(View.INVISIBLE);
        btn_road.setVisibility(View.INVISIBLE);

        btn_list.setVisibility(View.VISIBLE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("tab_clicked", "Service_Stations");

        editor.commit();

        FirebaseDatabase.getInstance().getReference().child("Service_Stations").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    final DataServiceStn ss = postSnapshot.getValue(DataServiceStn.class);

                    FirebaseDatabase.getInstance().getReference().child("Address").orderByChild("FuelID")
                            .startAt(postSnapshot.getKey())
                            .endAt(postSnapshot.getKey()).addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot2) {

                            for (DataSnapshot postSnapshot2 : dataSnapshot2.getChildren()) {
                                DataServiceAddress ad = postSnapshot2.getValue(DataServiceAddress.class);
                                String addressString = ad.unit_house_number + ", " + ad.street_name + ", " + ad.suburb_name + " " +
                                        "" + ad.state + " " + ad.post_code;
                                double[] cords = getLatLongFromAddress(addressString);
                                googleMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(cords[0], cords[1]))
                                        .title(ss.PlaceName));
                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError2) {

                        }
                    });
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void show_fuel() {

        googleMap.animateCamera( CameraUpdateFactory.zoomTo( 9.0f ) );

        btn_service.setVisibility(View.INVISIBLE);
        btn_fuel.setVisibility(View.INVISIBLE);
        btn_pickup.setVisibility(View.INVISIBLE);
        btn_road.setVisibility(View.INVISIBLE);

        btn_list.setVisibility(View.VISIBLE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("tab_clicked", "FuelPumps");

        editor.commit();

        FirebaseDatabase.getInstance().getReference().child("FuelPumps").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    final FuelPump fp = postSnapshot.getValue(FuelPump.class);

                    FirebaseDatabase.getInstance().getReference().child("Address").orderByChild("FuelID")
                            .startAt(postSnapshot.getKey())
                            .endAt(postSnapshot.getKey()).addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot2) {

                            for (DataSnapshot postSnapshot2 : dataSnapshot2.getChildren()) {
                                DataAddress ad = postSnapshot2.getValue(DataAddress.class);
                                String addressString = ad.unit_house_number + ", " + ad.street_name + ", " + ad.suburb_name + " " +
                                        "" + ad.state + " " + ad.post_code;

                                double[] cords = getLatLongFromAddress(addressString);
                                googleMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(cords[0], cords[1]))
                                        .title(fp.PlaceName));
                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError2) {

                        }
                    });
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void show_roadside() {
        googleMap.animateCamera( CameraUpdateFactory.zoomTo( 9.0f ) );

        btn_service.setVisibility(View.INVISIBLE);
        btn_fuel.setVisibility(View.INVISIBLE);
        btn_pickup.setVisibility(View.INVISIBLE);
        btn_road.setVisibility(View.INVISIBLE);

        btn_list.setVisibility(View.VISIBLE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("tab_clicked", "RoadSide_Assistance");

        editor.commit();

        FirebaseDatabase.getInstance().getReference().child("RoadSide_Assistance").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    final DataRoadAssis ra = postSnapshot.getValue(DataRoadAssis.class);

                    FirebaseDatabase.getInstance().getReference().child("Address").orderByChild("FuelID")
                            .startAt(postSnapshot.getKey())
                            .endAt(postSnapshot.getKey()).addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot2) {

                            for (DataSnapshot postSnapshot2 : dataSnapshot2.getChildren()) {
                                DataRoadAddress ad = postSnapshot2.getValue(DataRoadAddress.class);
                                String addressString = ad.unit_house_number + ", " + ad.street_name + ", " + ad.suburb_name + " " +
                                        "" + ad.state + " " + ad.post_code;
                                double[] cords = getLatLongFromAddress(addressString);
                                googleMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(cords[0], cords[1]))
                                        .title(ra.PlaceName));
                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError2) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void show_hospital() {
        googleMap.animateCamera( CameraUpdateFactory.zoomTo( 9.0f ) );

        btn_service.setVisibility(View.INVISIBLE);
        btn_fuel.setVisibility(View.INVISIBLE);
        btn_pickup.setVisibility(View.INVISIBLE);
        btn_road.setVisibility(View.INVISIBLE);

        btn_list.setVisibility(View.VISIBLE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("tab_clicked", "Hospitals");

        editor.commit();


        FirebaseDatabase.getInstance().getReference().child("Hospitals").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    final DataHospital hs = postSnapshot.getValue(DataHospital.class);

                    FirebaseDatabase.getInstance().getReference().child("Address").orderByChild("FuelID")
                            .startAt(postSnapshot.getKey())
                            .endAt(postSnapshot.getKey()).addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot2) {

                            for (DataSnapshot postSnapshot2 : dataSnapshot2.getChildren()) {
                                DataHospitalAddress ad = postSnapshot2.getValue(DataHospitalAddress.class);
                                String addressString = ad.unit_house_number + ", " + ad.street_name + ", " + ad.suburb_name + " " +
                                        "" + ad.state + " " + ad.post_code;
                                double[] cords = getLatLongFromAddress(addressString);
                                googleMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(cords[0], cords[1]))
                                        .title(hs.PlaceName));
                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError2) {

                        }
                    });
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_service) {
            googleMap.clear();
            show_service();
        } else if (id == R.id.nav_fuel) {
            googleMap.clear();
            show_fuel();
        } else if (id == R.id.nav_pickup) {
            googleMap.clear();
            show_roadside();
        } else if (id == R.id.nav_hospital) {
            googleMap.clear();
            show_hospital();

        } else if (id == R.id.nav_road) {
            googleMap.clear();
            show_roadside();

        } else if (id == R.id.nav_login) {
            Intent intent = new Intent(this, login.class);
            startActivity(intent);
        } else if (id == R.id.nav_register) {
            Intent intent = new Intent(this, register.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_about_us) {
            Intent intent = new Intent(this, AboutUs.class);
            startActivity(intent);}

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;

        mMap = googleMap;


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);


        }else{


            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                    LOCATION_REFRESH_DISTANCE, mLocationListener);

            LocationManager locationManager = (LocationManager) getSystemService
                    (Context.LOCATION_SERVICE);
            Location getLastLocation = locationManager.getLastKnownLocation
                    (LocationManager.PASSIVE_PROVIDER);

            if(getLastLocation == null){
                enableLoc();
                return;
            }

            mMap.setMyLocationEnabled(true);

            currentLongitude = getLastLocation.getLongitude();
            currentLatitude = getLastLocation.getLatitude();

            LatLng currentLocation = new LatLng(currentLatitude, currentLongitude);

            List<Address> addresses = null;

            try {
                addresses = geocoder.getFromLocation(
                        currentLatitude,
                        currentLongitude,
                        1);
            } catch (IOException ioException) {
            } catch (IllegalArgumentException illegalArgumentException) {
            }

            String txtAddress = "";

            // Handle case where no address was found.
            if (addresses == null || addresses.size() == 0) {
                txtAddress = "Not found";
            } else {
                Address address = addresses.get(0);
                ArrayList<String> addressFragments = new ArrayList<String>();

                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    addressFragments.add(address.getAddressLine(i));
                }

                txtAddress = TextUtils.join(System.getProperty("line.separator"),
                        addressFragments);
            }

            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(currentLatitude, currentLongitude))
                    .title(txtAddress));

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLatitude, currentLongitude), 18));



        }


    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    MainActivity.this.onMapReady(googleMap);

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
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

    private void enableLoc() {

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(MainActivity.this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {

                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            googleApiClient.connect();
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {

                            Log.d("Location error","Location error " + connectionResult.getErrorCode());
                        }
                    }).build();
            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(MainActivity.this, REQUEST_LOCATION);

                                finish();
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                    }
                }
            });
        }
    }


    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Exit Assistance Finder.")
        .setMessage("Are you sure.")
                .setPositiveButton("Exit",new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        finish();
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel",null);
        AlertDialog alert=builder.create();
        alert.show();

    }
}


