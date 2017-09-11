package com.DesignQuads.AssistanceFinder;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.DesignQuads.modal.DataAddress;
import com.DesignQuads.modal.DataFuelOpeningHrs;
import com.DesignQuads.modal.FuelPump;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by yodhbir singh on 8/10/2017.
 */

public class Address extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private EditText unit_house_number;
    private EditText street_name;
    private EditText suburb_name;
    private EditText post_code;
    private EditText state;
    private Button Address_save;
    private EditText PlaceName;
    private EditText LocationPhone;
    private Button Address_btn;
    private Button OpeningHrs_btn;
    private Button Submit_btn;
    private boolean saveaddress = false;
    private String FuelId;
    private FuelPump _fuelPump;
    private DataAddress _address;
    private DataFuelOpeningHrs _openingHours;
    private String _ohoursId;
    private String _addressId;
    private String edit_id;
    public SharedPreferences sharedpreferences;

    public static final String MyPREFERENCES = "MyPrefs" ;
    private boolean saveOpeningHrs = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_station);

        edit_id = getIntent().getStringExtra("EXTRA_FUEL_ID");

        if(edit_id == null){
            edit_id = "";
        }


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        PlaceName = (EditText) findViewById(R.id.edit_PlaceName);
        LocationPhone = (EditText) findViewById(R.id.edit_phone);
        Address_btn = (Button) findViewById(R.id.Address_btn);

        mDatabase = FirebaseDatabase.getInstance().getReference();


        if(edit_id != ""){

            FuelId = edit_id;

            mDatabase.child("FuelPumps").orderByKey().equalTo(edit_id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        _fuelPump = child.getValue(FuelPump.class);
                        PlaceName.setText(_fuelPump.PlaceName);
                        LocationPhone.setText(_fuelPump.LocationPhone);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }else{
            FuelId = mDatabase.push().getKey();
        }



        Button mOpeningHrs_btn = (Button) findViewById(R.id.OpeningHrs_btn);
        mOpeningHrs_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Address.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_opening_hrs__dialog, null);
                final EditText mWDamOpening = (EditText) mView.findViewById(R.id.WDamOpening);
                final EditText mWDpmClosing = (EditText) mView.findViewById(R.id.WDpmClosing);
                final EditText mWEamOpening = (EditText) mView.findViewById(R.id.WEamOpening);
                final EditText mWEpmClosing = (EditText) mView.findViewById(R.id.WEpmClosing);
                final Spinner sp1 = (Spinner) mView.findViewById(R.id.spinner1);
                final Spinner sp2 = (Spinner) mView.findViewById(R.id.spinner2);
                final Spinner sp3 = (Spinner) mView.findViewById(R.id.spinner3);
                final Spinner sp4 = (Spinner) mView.findViewById(R.id.spinner4);
                Button mHoursSave = (Button) mView.findViewById(R.id.Hours_save);


                if(edit_id != ""){

                    mDatabase.child("OpeningHrs").orderByChild("FuelID").equalTo(edit_id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                _ohoursId = child.getKey();
                                _openingHours = child.getValue(DataFuelOpeningHrs.class);
                                mWDamOpening.setText(_openingHours.WeekDaysAM.split(" ")[0]);
                                sp1.setSelection((_openingHours.WeekDaysAM.split(" ")[1].equalsIgnoreCase("AM")) ? 0 : 1);
                                mWDpmClosing.setText(_openingHours.WeekDaysPM.split(" ")[0]);
                                sp2.setSelection((_openingHours.WeekDaysPM.split(" ")[1].equalsIgnoreCase("AM")) ? 0 : 1);

                                mWEamOpening.setText(_openingHours.WeekEndsAM.split(" ")[0]);
                                sp3.setSelection((_openingHours.WeekEndsAM.split(" ")[1].equalsIgnoreCase("AM")) ? 0 : 1);
                                mWEpmClosing.setText(_openingHours.WeekEndsPM.split(" ")[0]);
                                sp4.setSelection((_openingHours.WeekEndsPM.split(" ")[1].equalsIgnoreCase("AM")) ? 0 : 1);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                mHoursSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mWDamOpening.getText().toString().equals("")) {
                            Toast.makeText(Address.this, R.string.WDamOpeningErrMsg, Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (mWDpmClosing.getText().toString().equals("")) {
                            Toast.makeText(Address.this, R.string.WDpmClosingErrMsg, Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (mWEamOpening.getText().toString().equals("")) {
                            Toast.makeText(Address.this, R.string.WEamOpeningErrMsg, Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (mWEpmClosing.getText().toString().equals("")) {
                            Toast.makeText(Address.this, R.string.WEamClosingErrMsg, Toast.LENGTH_LONG).show();
                            return;
                        }


                        WriteDataFuelOpeningHrs(mWDamOpening.getText().toString(),  String.valueOf(sp1.getSelectedItem()),
                                mWDpmClosing.getText().toString(), String.valueOf(sp2.getSelectedItem()),
                                mWEamOpening.getText().toString(), String.valueOf(sp3.getSelectedItem()),
                                mWEpmClosing.getText().toString(), String.valueOf(sp4.getSelectedItem()));

                        mWDamOpening.setText("");
                        mWDpmClosing.setText("");
                        mWEamOpening.setText("");
                        mWEpmClosing.setText("");

                        Toast.makeText(Address.this, "You Have Successfully saved.... ", Toast.LENGTH_LONG).show();
                        dialog.cancel();

                    }


                });

            }
        });


        Button mAddress_btn = (Button) findViewById(R.id.Address_btn);
        mAddress_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Address.this);
                View mView = getLayoutInflater().inflate(R.layout.address_layout, null);
                final EditText mHouseNumber = (EditText) mView.findViewById(R.id.HouseNumber);
                final EditText mStreet = (EditText) mView.findViewById(R.id.Street);
                final EditText mSuburb = (EditText) mView.findViewById(R.id.Suburb);
                final EditText mPostcode = (EditText) mView.findViewById(R.id.Postcode);
                final EditText mState = (EditText) mView.findViewById(R.id.State);
                Button mSave = (Button) mView.findViewById(R.id.Address_save);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                if(edit_id != "") {

                    mDatabase.child("Address").orderByChild("FuelID").equalTo(edit_id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                _addressId = child.getKey();
                                _address = child.getValue(DataAddress.class);
                                mHouseNumber.setText(_address.unit_house_number);
                                mStreet.setText(_address.street_name);
                                mSuburb.setText(_address.suburb_name);
                                mPostcode.setText(_address.post_code);
                                mState.setText(_address.state);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

                mSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mHouseNumber.getText().toString().equals("")) {
                            Toast.makeText(Address.this, R.string.HouseErrMsg, Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (mStreet.getText().toString().equals("")) {
                            Toast.makeText(Address.this, R.string.StreetErrMsg, Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (mSuburb.getText().toString().equals("")) {
                            Toast.makeText(Address.this, R.string.SuburbErrMsg, Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (mPostcode.getText().toString().equals("")) {
                            Toast.makeText(Address.this, R.string.PostcodeErrMsg, Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (mState.getText().toString().equals("")) {
                            Toast.makeText(Address.this, R.string.StateErrMsg, Toast.LENGTH_LONG).show();
                            return;
                        }


                        writeNewDataAddress(mHouseNumber.getText().toString(),mStreet.getText().toString(),
                                mSuburb.getText().toString(), mPostcode.getText().toString(),mState.getText().toString());

                        mHouseNumber.setText("");
                        mState.setText("");
                        mSuburb.setText("");
                        mPostcode.setText("");
                        mStreet.setText("");

                        Toast.makeText(Address.this,"You Have Successfully saved.... ",Toast.LENGTH_LONG).show();
                        dialog.cancel();

                    }

                });

            }
        });

        Address_save = (Button) findViewById(R.id.Address_save);

        Submit_btn = (Button) findViewById(R.id.Submit_btn);

        Submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (PlaceName.getText().toString().equals("")) {
                    Toast.makeText(Address.this, "Place Name is required", Toast.LENGTH_LONG).show();
                    return;
                }


                if (LocationPhone.getText().toString().equals("")) {
                    Toast.makeText(Address.this, "Phone Number is required", Toast.LENGTH_LONG).show();
                    return;
                }

                if (edit_id == "") {

                    if (!saveaddress) {
                        Toast.makeText(Address.this, "Address is required", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (!saveOpeningHrs) {
                        Toast.makeText(Address.this, "Opening hours is required", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                writeNewFuelPump(PlaceName.getText().toString(),LocationPhone.getText().toString());

                if (edit_id != "") {

                    Toast.makeText(Address.this, "Fuel Station is Successfully Updated... ", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Address.this, EditList.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);


                } else {

                    PlaceName.setText("");
                    LocationPhone.setText("");

                    Toast.makeText(Address.this, "Fuel Station is Successfully Added... ", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private void WriteDataFuelOpeningHrs(String WDamOpening ,String WDam_pm_opening, String WDpmClosing,String WDam_pm_closing,
                                         String WEamOpening ,String WEam_pm_opening, String WEpmClosing,String WEam_pm_closing) {
        DataFuelOpeningHrs OpenHours = new DataFuelOpeningHrs(FuelId, WDamOpening + " " + WDam_pm_opening,
                WDpmClosing + " " + WDam_pm_closing, WEamOpening + " " + WEam_pm_opening, WEpmClosing + " " + WEam_pm_closing);

        if (edit_id != "") {

            mDatabase.child("OpeningHrs").child(_ohoursId).setValue(OpenHours);

        } else {
            String openingHrsId = mDatabase.push().getKey();
            mDatabase.child("OpeningHrs").child(openingHrsId).setValue(OpenHours);
            saveOpeningHrs = true;
        }

    }



    private void writeNewFuelPump(String PlaceName, String LocationPhone) {
        SharedPreferences shared = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String user_id = (shared.getString("user_id", ""));

        if (edit_id != "") {

            FuelPump Fuel = new FuelPump(PlaceName, LocationPhone, user_id);
            mDatabase.child("FuelPumps").child(edit_id).setValue(Fuel);

        } else {

            FuelPump Fuel = new FuelPump(PlaceName, LocationPhone, user_id);
            mDatabase.child("FuelPumps").child(FuelId).setValue(Fuel);

            FuelId = mDatabase.push().getKey();

        }
    }

    private void writeNewDataAddress(String unit_house_number , String street_name, String suburb_name, String post_code, String state)
    {
        DataAddress address = new DataAddress(FuelId, unit_house_number, street_name, suburb_name, post_code, state);

        if (edit_id != "") {

            mDatabase.child("Address").child(_addressId).setValue(address);

        } else {

            String addressId = mDatabase.push().getKey();
            mDatabase.child("Address").child(addressId).setValue(address);
            saveaddress = true;

        }
    }
}