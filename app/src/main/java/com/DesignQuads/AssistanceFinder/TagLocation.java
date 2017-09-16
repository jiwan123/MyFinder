package com.DesignQuads.AssistanceFinder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class TagLocation extends AppCompatActivity {
    public SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public Button ServiceStation_btn;
    public Button fuelStation_btn;
    public Button roadSideAssis_btn;
    public Button Hospital_btn;
    public Button logOut_btn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_location);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tag Location");

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        ServiceStation_btn = (Button) findViewById(R.id.ServiceStation_btn);

        ServiceStation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TagLocation.this, ServiceAddress.class);
                startActivity(intent);
            }
        });


        fuelStation_btn = (Button) findViewById(R.id.fuelStation_btn);

        fuelStation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TagLocation.this, Address.class);
                startActivity(intent);
            }
        });


        roadSideAssis_btn = (Button) findViewById(R.id.roadSideAssis_btn);

        roadSideAssis_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TagLocation.this, RoadSideAddress.class);
                startActivity(intent);
            }
        });

        Hospital_btn = (Button) findViewById(R.id.Hospital_btn);

        Hospital_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TagLocation.this, HospitalAddress.class);
                startActivity(intent);
            }
        });



        logOut_btn = (Button) findViewById(R.id.logOut_btn);

        logOut_btn.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View view) {
                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedpreferences.edit();
                editor.clear();
                editor.commit();
                finish();


                Intent intent = new Intent(TagLocation.this, login.class);
                startActivity(intent);

                Toast.makeText(TagLocation.this,"You are now logged out..",Toast.LENGTH_LONG).show();
            }

        });

}
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TagLocation.this, ProfileActivity.class);
        startActivity(intent);

    }


}
