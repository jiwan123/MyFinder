package com.DesignQuads.AssistanceFinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.DesignQuads.modal.DataHospital;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Hospital extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private EditText PlaceName;
    private EditText PlaceAddress;
    private EditText OpeningHrs;
    private EditText LocationPhone;
    private Button Submit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Hospital Form");

        mDatabase = FirebaseDatabase.getInstance().getReference();

        PlaceName = (EditText) findViewById(R.id.edit_PlaceName);
        PlaceAddress = (EditText) findViewById(R.id.edit_PlaceAddress);
        OpeningHrs = (EditText) findViewById(R.id.OpeningTimes);
        LocationPhone = (EditText) findViewById(R.id.edit_phone);


        Submit_btn = (Button) findViewById(R.id.Submit_btn);

        Submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (PlaceName.getText().toString().equals("")) {
                    Toast.makeText(Hospital.this, "Place Name is required", Toast.LENGTH_LONG).show();
                    return;
                }

                if (PlaceAddress.getText().toString().equals("")) {
                    Toast.makeText(Hospital.this, "Place Address is required", Toast.LENGTH_LONG).show();
                    return;
                }

                if (OpeningHrs.getText().toString().equals("")) {
                    Toast.makeText(Hospital.this, "Opening hours are required", Toast.LENGTH_LONG).show();
                    return;
                }

                if (LocationPhone.getText().toString().equals("")) {
                    Toast.makeText(Hospital.this, "Phone Number is required", Toast.LENGTH_LONG).show();
                    return;
                }

                writeNewDataHospital(PlaceName.getText().toString(), PlaceAddress.getText().toString(), OpeningHrs.getText().toString(),
                        LocationPhone.getText().toString());

                PlaceName.setText("");
                PlaceAddress.setText("");
                OpeningHrs.setText("");
                LocationPhone.setText("");

                Toast.makeText(Hospital.this, "Service Station is Successfully Added... ", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(Hospital.this, TagLocation.class);
                startActivity(intent);
            }
        });

    }

    private void writeNewDataHospital(String PlaceName, String PlaceAddress, String OpeningHrs, String LocationPhone) {
        DataHospital HospitalCall = new DataHospital(PlaceName, PlaceAddress, OpeningHrs, LocationPhone);



        String HospitalId = mDatabase.push().getKey();
        mDatabase.child("Hospitals").child(HospitalId).setValue(HospitalCall);

    }
}
