package com.DesignQuads.AssistanceFinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class FuelStation extends AppCompatActivity {

        private DatabaseReference mDatabase;
        private EditText PlaceName;
        private EditText LocationPhone;
        private Button Address_btn;
        private Button OpeningHrs_btn;
        private Button Submit_btn;



        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_fuel_station);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Fuel Station Form");

            mDatabase = FirebaseDatabase.getInstance().getReference();

            PlaceName = (EditText) findViewById(R.id.edit_PlaceName);
            LocationPhone = (EditText) findViewById(R.id.edit_phone);
            Address_btn = (Button) findViewById(R.id.Address_btn);
            Intent intent = new Intent(FuelStation.this, Address.class);
            startActivity(intent);
            OpeningHrs_btn = (Button) findViewById(R.id.OpeningHrs_btn);


            Submit_btn = (Button) findViewById(R.id.Submit_btn);

            Submit_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (PlaceName.getText().toString().equals("")) {
                        Toast.makeText(FuelStation.this, "Place Name is required", Toast.LENGTH_LONG).show();
                        return;
                    }


                    if (LocationPhone.getText().toString().equals("")) {
                        Toast.makeText(FuelStation.this, "Phone Number is required", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (Address_btn.getText().toString().equals("")) {
                        Toast.makeText(FuelStation.this, "Place Address is required", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (OpeningHrs_btn.getText().toString().equals("")) {
                        Toast.makeText(FuelStation.this, "Opening hours are required", Toast.LENGTH_LONG).show();
                        return;
                    }

                    writeNewFuelPump(PlaceName.getText().toString(), Address_btn.getText().toString(), OpeningHrs_btn.getText().toString(),
                            LocationPhone.getText().toString());

                    PlaceName.setText("");
                    LocationPhone.setText("");
                    Address_btn.setText("");
                    OpeningHrs_btn.setText("");

                    Toast.makeText(FuelStation.this, "Fuel Station is Successfully Added... ", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(FuelStation.this, TagLocation.class);
                    startActivity(intent);
                }
            });

        }

        private void writeNewFuelPump(String PlaceName, String PlaceAddress, String OpeningHrs, String LocationPhone) {
//        FuelPump Fuel = new FuelPump(PlaceName, PlaceAddress, OpeningHrs, LocationPhone);



//        String FuelId = mDatabase.push().getKey();
//        mDatabase.child("FuelPumps").child(FuelId).setValue(Fuel);
    }


}

