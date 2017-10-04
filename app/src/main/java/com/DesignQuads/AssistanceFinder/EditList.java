package com.DesignQuads.AssistanceFinder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.DesignQuads.adapters.EditListAdapter;
import com.DesignQuads.modal.AbstractAddress;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class EditList extends AppCompatActivity {

    ListView editList;
    ArrayList<AbstractAddress> allPlaces;
    EditListAdapter adapter;
    public SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    static EditList activityA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit Services");

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        activityA = this;

        editList = (ListView) findViewById(R.id.editList);

        allPlaces = new ArrayList<>();

        adapter = new EditListAdapter(EditList.this, allPlaces);

        SharedPreferences shared = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String user_id = (shared.getString("user_id", ""));


        //Edit for Fuel Stations
        FirebaseDatabase.getInstance().getReference().child("FuelPumps").orderByChild("userId")
                .startAt(user_id).endAt(user_id).addValueEventListener(new ValueEventListener() {
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
                                allPlaces.add(new AbstractAddress(postSnapshot.getKey(),"FuelPumps",aa.PlaceName,aa.LocationPhone,aa2.
                                        unit_house_number,aa2.street_name,aa2.suburb_name,aa2.post_code,aa2.state));
                                adapter = new EditListAdapter(EditList.this, allPlaces);
                                editList.setAdapter(adapter);
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


        //Edit for Service stations
        FirebaseDatabase.getInstance().getReference().child("Service_Stations").orderByChild("userId")
                .startAt(user_id).endAt(user_id).addValueEventListener(new ValueEventListener() {
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
                                allPlaces.add(new AbstractAddress(postSnapshot.getKey(),"Service_Stations",aa.PlaceName,aa.LocationPhone,aa2.
                                        unit_house_number,aa2.street_name,aa2.suburb_name,aa2.post_code,aa2.state));
                                adapter = new EditListAdapter(EditList.this, allPlaces);
                                editList.setAdapter(adapter);
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

        //Edit for Roadside
        FirebaseDatabase.getInstance().getReference().child("RoadSide_Assistance").orderByChild("userId")
                .startAt(user_id).endAt(user_id).addValueEventListener(new ValueEventListener() {
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
                                allPlaces.add(new AbstractAddress(postSnapshot.getKey(),"RoadSide_Assistance",aa.PlaceName,aa.LocationPhone,aa2.
                                        unit_house_number,aa2.street_name,aa2.suburb_name,aa2.post_code,aa2.state));
                                adapter = new EditListAdapter(EditList.this, allPlaces);
                                editList.setAdapter(adapter);
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

        //Edit for Hospitals
        FirebaseDatabase.getInstance().getReference().child("Hospitals").orderByChild("userId")
                .startAt(user_id).endAt(user_id).addValueEventListener(new ValueEventListener() {
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
                                allPlaces.add(new AbstractAddress(postSnapshot.getKey(),"Hospitals",aa.PlaceName,aa.LocationPhone,aa2.
                                        unit_house_number,aa2.street_name,aa2.suburb_name,aa2.post_code,aa2.state));
                                adapter = new EditListAdapter(EditList.this, allPlaces);
                                editList.setAdapter(adapter);
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

    public static EditList getInstance(){
        return  activityA;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EditList.this, ProfileActivity.class);
        startActivity(intent);

    }
}
