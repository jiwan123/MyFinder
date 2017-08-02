package com.DesignQuads.AssistanceFinder;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.DesignQuads.adapters.CustomBaseAdapter;
import com.DesignQuads.dataSource.MyData;
import com.DesignQuads.modal.Fuel;
import com.google.android.gms.maps.model.LatLng;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class list extends AppCompatActivity {

    ListView listView;
    List<Fuel> rowItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

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
        LatLng currentLocation = new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));

        listView = (ListView) findViewById(R.id.listview);

        List<Fuel> allFuels = MyData.getFuel();

        for (int  i = 0; i<allFuels.size(); i++){
            Location loc1 = new Location("");
            loc1.setLatitude(currentLocation.latitude);
            loc1.setLongitude(currentLocation.longitude);

            Location loc2 = new Location("");
            loc2.setLatitude(allFuels.get(i).getLat());
            loc2.setLongitude(allFuels.get(i).getLng());

            allFuels.get(i).distanceInt = Math.round(loc1.distanceTo(loc2));

        }

        Collections.sort(allFuels, (Comparator<? super Fuel>) new CustomComparator());

        CustomBaseAdapter adapter = new CustomBaseAdapter(this, allFuels);

        listView.setAdapter(adapter);

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

}
