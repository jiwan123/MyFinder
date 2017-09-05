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
import com.DesignQuads.modal.DataHospital;
import com.DesignQuads.modal.DataServiceStn;
import com.DesignQuads.modal.FuelPump;
import com.DesignQuads.modal.Station;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by yodhbir singh on 8/23/2017.
 */

public class HospitalAddress extends AppCompatActivity {
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
    private String HospitalID;

    public SharedPreferences sharedpreferences;

    public static final String MyPREFERENCES = "MyPrefs" ;
    private boolean saveOpeningHrs = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        PlaceName = (EditText) findViewById(R.id.edit_PlaceName);
        LocationPhone = (EditText) findViewById(R.id.edit_phone);
        Address_btn = (Button) findViewById(R.id.Address_btn);


        mDatabase = FirebaseDatabase.getInstance().getReference();

        HospitalID = mDatabase.push().getKey();

        Button mOpeningHrs_btn = (Button) findViewById(R.id.OpeningHrs_btn);
        mOpeningHrs_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(HospitalAddress.this);
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

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                mHoursSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mWDamOpening.getText().toString().equals("")) {
                            Toast.makeText(HospitalAddress.this, R.string.WDamOpeningErrMsg, Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (mWDpmClosing.getText().toString().equals("")) {
                            Toast.makeText(HospitalAddress.this, R.string.WDpmClosingErrMsg, Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (mWEamOpening.getText().toString().equals("")) {
                            Toast.makeText(HospitalAddress.this, R.string.WEamOpeningErrMsg, Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (mWEpmClosing.getText().toString().equals("")) {
                            Toast.makeText(HospitalAddress.this, R.string.WEamClosingErrMsg, Toast.LENGTH_LONG).show();
                            return;
                        }


                        WriteDataHospital_OpeningHrs(mWDamOpening.getText().toString(),  String.valueOf(sp1.getSelectedItem()),
                                mWDpmClosing.getText().toString(), String.valueOf(sp2.getSelectedItem()),
                                mWEamOpening.getText().toString(), String.valueOf(sp3.getSelectedItem()),
                                mWEpmClosing.getText().toString(), String.valueOf(sp4.getSelectedItem()));

                        mWDamOpening.setText("");
                        mWDpmClosing.setText("");
                        mWEamOpening.setText("");
                        mWEpmClosing.setText("");

                        Toast.makeText(HospitalAddress.this, "You Have Successfully saved.... ", Toast.LENGTH_LONG).show();
                        dialog.cancel();

                    }


                });

            }
        });


        Button mAddress_btn = (Button) findViewById(R.id.Address_btn);
        mAddress_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(HospitalAddress.this);
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


                mSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mHouseNumber.getText().toString().equals("")) {
                            Toast.makeText(HospitalAddress.this, R.string.HouseErrMsg, Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (mStreet.getText().toString().equals("")) {
                            Toast.makeText(HospitalAddress.this, R.string.StreetErrMsg, Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (mSuburb.getText().toString().equals("")) {
                            Toast.makeText(HospitalAddress.this, R.string.SuburbErrMsg, Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (mPostcode.getText().toString().equals("")) {
                            Toast.makeText(HospitalAddress.this, R.string.PostcodeErrMsg, Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (mState.getText().toString().equals("")) {
                            Toast.makeText(HospitalAddress.this, R.string.StateErrMsg, Toast.LENGTH_LONG).show();
                            return;
                        }


                        writeNewDataAddress(mHouseNumber.getText().toString(),mStreet.getText().toString(),
                                mSuburb.getText().toString(), mPostcode.getText().toString(),mState.getText().toString());

                        mHouseNumber.setText("");
                        mState.setText("");
                        mSuburb.setText("");
                        mPostcode.setText("");
                        mStreet.setText("");

                        Toast.makeText(HospitalAddress.this,"You Have Successfully saved.... ",Toast.LENGTH_LONG).show();
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
                    Toast.makeText(HospitalAddress.this, "Place Name is required", Toast.LENGTH_LONG).show();
                    return;
                }


                if (LocationPhone.getText().toString().equals("")) {
                    Toast.makeText(HospitalAddress.this, "Phone Number is required", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!saveaddress) {
                    Toast.makeText(HospitalAddress.this, "Address is required", Toast.LENGTH_LONG).show();
                    return;
                }

                writeNewHospital(PlaceName.getText().toString(),LocationPhone.getText().toString());

                PlaceName.setText("");
                LocationPhone.setText("");

                Toast.makeText(HospitalAddress.this, "Hospital is Successfully Added... ", Toast.LENGTH_LONG).show();

            }
        });

    }

    private void WriteDataHospital_OpeningHrs(String WDamOpening ,String WDam_pm_opening, String WDpmClosing,String WDam_pm_closing,
                                         String WEamOpening ,String WEam_pm_opening, String WEpmClosing,String WEam_pm_closing)
    {
        Log.v("bbb",WDamOpening+" "+WDam_pm_opening);
        DataFuelOpeningHrs OpenHours = new DataFuelOpeningHrs(HospitalID,WDamOpening+" "+WDam_pm_opening,
                WDpmClosing+" " +WDam_pm_closing, WEamOpening+" "+WEam_pm_opening, WEpmClosing+" "+WEam_pm_closing);

        String openingHrsId = mDatabase.push().getKey();
        mDatabase.child("OpeningHrs").child(openingHrsId).setValue(OpenHours);
        saveOpeningHrs = true;
    }


    private void writeNewHospital(String PlaceName, String LocationPhone) {
        SharedPreferences shared = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String user_id = (shared.getString("user_id", ""));

        DataHospital station = new DataHospital(PlaceName,LocationPhone,user_id);
        mDatabase.child("Hospitals").child(HospitalID).setValue(station);

        HospitalID = mDatabase.push().getKey();
    }


    private void writeNewDataAddress(String unit_house_number , String street_name, String suburb_name, String post_code, String state)
    {
        DataAddress address = new DataAddress(HospitalID,unit_house_number, street_name, suburb_name, post_code,state);

        String addressId = mDatabase.push().getKey();
        mDatabase.child("Address").child(addressId).setValue(address);
        saveaddress = true;
    }

}