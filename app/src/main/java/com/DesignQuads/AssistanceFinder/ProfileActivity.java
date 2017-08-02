package com.DesignQuads.AssistanceFinder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class ProfileActivity extends AppCompatActivity {

    private TextView tvUserName;
    public SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public Button Tag_btn;
    public Button editTag_btn;
    public Button unTag_btn;
    public Button logOut_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("User Profile");

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvUserName.setText(sharedpreferences.getString("username",""));

        if(sharedpreferences.getString("username","") == ""){
            Toast.makeText(ProfileActivity.this,"Please login",Toast.LENGTH_LONG).show();

        }


        Tag_btn = (Button) findViewById(R.id.tag_btn);

        Tag_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, TagLocation.class);
                startActivity(intent);
            }
        });



        editTag_btn = (Button) findViewById(R.id.editTag_btn);

        editTag_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, TagLocation.class);
                startActivity(intent);
            }
        });


        unTag_btn = (Button) findViewById(R.id.unTag_btn);

        unTag_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, TagLocation.class);
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

                Intent intent = new Intent(ProfileActivity.this, login.class);
                startActivity(intent);

                Toast.makeText(ProfileActivity.this,"You are now logged out..",Toast.LENGTH_LONG).show();
            }

        });


    }
}

