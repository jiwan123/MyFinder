package com.DesignQuads.AssistanceFinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.DesignQuads.modal.DataRoadAssis;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RoadSide extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private EditText PlaceName;
    private EditText PlaceAddress;
    private EditText OpeningHrs;
    private EditText LocationPhone;
    private Button Submit_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_road_side);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Roadside Assistance Form");

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
                    Toast.makeText(RoadSide.this, "Place Name is required", Toast.LENGTH_LONG).show();
                    return;
                }

                if (PlaceAddress.getText().toString().equals("")) {
                    Toast.makeText(RoadSide.this, "Place Address is required", Toast.LENGTH_LONG).show();
                    return;
                }

                if (OpeningHrs.getText().toString().equals("")) {
                    Toast.makeText(RoadSide.this, "Opening hours are required", Toast.LENGTH_LONG).show();
                    return;
                }

                if (LocationPhone.getText().toString().equals("")) {
                    Toast.makeText(RoadSide.this, "Phone Number is required", Toast.LENGTH_LONG).show();
                    return;
                }

                writeNewDataRoadAssis(PlaceName.getText().toString(), PlaceAddress.getText().toString(), OpeningHrs.getText().toString(),
                        LocationPhone.getText().toString());

                PlaceName.setText("");
                PlaceAddress.setText("");
                OpeningHrs.setText("");
                LocationPhone.setText("");

                Toast.makeText(RoadSide.this, "Roadside Assistance is Successfully Added... ", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(RoadSide.this, TagLocation.class);
                startActivity(intent);
            }
        });

    }

    private void writeNewDataRoadAssis(String PlaceName, String PlaceAddress, String OpeningHrs, String LocationPhone) {
        DataRoadAssis RoadAssistance = new DataRoadAssis(PlaceName, PlaceAddress, OpeningHrs, LocationPhone);



        String RoadAssisId = mDatabase.push().getKey();
        mDatabase.child("RoadSide_Assistance").child(RoadAssisId).setValue(RoadAssistance);


    }
}
