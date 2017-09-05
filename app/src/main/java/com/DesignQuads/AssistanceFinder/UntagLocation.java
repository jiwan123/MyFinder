package com.DesignQuads.AssistanceFinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.DesignQuads.adapters.CustomBaseAdapter;
import com.DesignQuads.adapters.UnTagAdapter;
import com.DesignQuads.modal.AbstractAddress;
import com.DesignQuads.modal.DataAddress;
import com.DesignQuads.modal.FuelPump;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UntagLocation extends AppCompatActivity {

    ListView untaglist;
    ArrayList<AbstractAddress> allPlaces;
    UnTagAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_untag_location);

        untaglist = (ListView) findViewById(R.id.untaglist);

        allPlaces = new ArrayList<>();

        adapter = new UnTagAdapter(UntagLocation.this, allPlaces);

        FirebaseDatabase.getInstance().getReference().child("FuelPumps").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (final DataSnapshot postSnapshot :dataSnapshot.getChildren()) {
                    final AbstractAddress aa = postSnapshot.getValue(AbstractAddress.class);

                    FirebaseDatabase.getInstance().getReference().child("Address").orderByChild("FuelID")
                            .startAt(postSnapshot.getKey())
                            .endAt(postSnapshot.getKey()).addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot2) {

                            for (DataSnapshot postSnapshot2 :dataSnapshot2.getChildren()) {
                                AbstractAddress aa2 = postSnapshot2.getValue(AbstractAddress.class);
                                allPlaces.add(new AbstractAddress(postSnapshot.getKey(),aa.PlaceName,aa.LocationPhone,aa2.
                                        unit_house_number,aa2.street_name,aa2.suburb_name,aa2.post_code,aa2.state));
                                adapter = new UnTagAdapter(UntagLocation.this, allPlaces);
                                untaglist.setAdapter(adapter);
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
}
