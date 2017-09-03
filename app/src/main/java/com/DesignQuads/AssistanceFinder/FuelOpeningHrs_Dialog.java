package com.DesignQuads.AssistanceFinder;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



import com.DesignQuads.modal.DataFuelOpeningHrs;
import com.DesignQuads.modal.FuelPump;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class FuelOpeningHrs_Dialog extends AppCompatActivity {



    private Button OpeningHrs_btn;
    private DatabaseReference mDatabase;
    private String FuelId;
    private boolean saveOpeningHrs = false;
    private EditText PlaceName;
    private EditText LocationPhone;
    private Button Hours_save;
    private Button Submit_btn;

    public SharedPreferences sharedpreferences;

    public static final String MyPREFERENCES = "MyPrefs" ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_station);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        PlaceName = (EditText) findViewById(R.id.edit_PlaceName);
        LocationPhone = (EditText) findViewById(R.id.edit_phone);
        OpeningHrs_btn = (Button) findViewById(R.id.OpeningHrs_btn);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        FuelId = mDatabase.push().getKey();

        Button mOpeningHrs_btn = (Button) findViewById(R.id.OpeningHrs_btn);
        mOpeningHrs_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(FuelOpeningHrs_Dialog.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_opening_hrs__dialog, null);
                final EditText mWDamOpening = (EditText) mView.findViewById(R.id.WDamOpening);
                final EditText mWDpmClosing = (EditText) mView.findViewById(R.id.WDpmClosing);
                final EditText mWEamOpening = (EditText) mView.findViewById(R.id.WEamOpening);
                final EditText mWEamClosing = (EditText) mView.findViewById(R.id.WEamClosing);
                Button mHoursSave = (Button) mView.findViewById(R.id.Hours_save);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                mHoursSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mWDamOpening.getText().toString().equals("")) {
                            Toast.makeText(FuelOpeningHrs_Dialog.this, R.string.WDamOpeningErrMsg, Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (mWDpmClosing.getText().toString().equals("")) {
                            Toast.makeText(FuelOpeningHrs_Dialog.this, R.string.WDpmClosingErrMsg, Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (mWEamOpening.getText().toString().equals("")) {
                            Toast.makeText(FuelOpeningHrs_Dialog.this, R.string.WEamOpeningErrMsg, Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (mWEamClosing.getText().toString().equals("")) {
                            Toast.makeText(FuelOpeningHrs_Dialog.this, R.string.WEamClosingErrMsg, Toast.LENGTH_LONG).show();
                            return;
                        }


                        WriteDataFuelOpeningHrs(mWDamOpening.getText().toString(), mWDpmClosing.getText().toString(),
                                mWEamOpening.getText().toString(), mWEamClosing.getText().toString());

                        mWDamOpening.setText("");
                        mWDpmClosing.setText("");
                        mWEamOpening.setText("");
                        mWEamClosing.setText("");

                        Toast.makeText(FuelOpeningHrs_Dialog.this, "You Have Successfully saved.... ", Toast.LENGTH_LONG).show();
                        dialog.cancel();

                    }


                });

            }
        });

    }

       /* Submit_btn = (Button) findViewById(R.id.Submit_btn);

        Submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (PlaceName.getText().toString().equals("")) {
                    Toast.makeText(FuelOpeningHrs_Dialog.this, "Place Name is required", Toast.LENGTH_LONG).show();
                    return;
                }


                if (LocationPhone.getText().toString().equals("")) {
                    Toast.makeText(FuelOpeningHrs_Dialog.this, "Phone Number is required", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!saveOpeningHrs) {
                    Toast.makeText(FuelOpeningHrs_Dialog.this, "Opening Hours is required", Toast.LENGTH_LONG).show();
                    return;
                }

                writeNewFuelPump(PlaceName.getText().toString(),LocationPhone.getText().toString());

                PlaceName.setText("");
                LocationPhone.setText("");

                Toast.makeText(FuelOpeningHrs_Dialog.this, "Fuel Station is Successfully Added... ", Toast.LENGTH_LONG).show();

            }
        });




    private void writeNewFuelPump(String PlaceName, String LocationPhone) {
        SharedPreferences shared = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String user_id = (shared.getString("user_id", ""));

        FuelPump Fuel = new FuelPump(PlaceName,LocationPhone,user_id);
        mDatabase.child("FuelPumps").child(FuelId).setValue(Fuel);

        FuelId = mDatabase.push().getKey();
    }
    */




    private void WriteDataFuelOpeningHrs(String WDamOpening , String WDpmClosing, String WEamOpening, String WEamClosing)
    {
        DataFuelOpeningHrs OpenHours = new DataFuelOpeningHrs(FuelId,WDamOpening, WDpmClosing, WEamOpening, WEamClosing);

        String openingHrsId = mDatabase.push().getKey();
        mDatabase.child("OpeningHrs").child(openingHrsId).setValue(OpenHours);
        saveOpeningHrs = true;
    }

}


