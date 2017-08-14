package com.DesignQuads.AssistanceFinder;


import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.DesignQuads.modal.DataAddress;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_station);


        mDatabase = FirebaseDatabase.getInstance().getReference();


        Button mAddress_btn = (Button) findViewById(R.id.Address_save);
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


                        writeNewDataAddress(unit_house_number.getText().toString(),street_name.getText().toString(),
                                suburb_name.getText().toString(), post_code.getText().toString(),state.getText().toString());

                        unit_house_number.setText("");
                        street_name.setText("");
                        suburb_name.setText("");
                        post_code.setText("");
                        state.setText("");

                        Toast.makeText(Address.this,"You Have Successfully saved.... ",Toast.LENGTH_LONG).show();

                    }

                });

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();

            }
        });

        Address_save = (Button) findViewById(R.id.Address_save);
    }
    private void writeNewDataAddress(String unit_house_number , String street_name, String suburb_name, String post_code, String state)
    {
        DataAddress address = new DataAddress(unit_house_number, street_name, suburb_name, post_code,state);



        String addressId = mDatabase.push().getKey();
        mDatabase.child("Address").child(addressId).setValue(address);
    }

}