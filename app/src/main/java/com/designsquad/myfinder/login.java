package com.designsquad.myfinder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {

    private DatabaseReference mDatabase;
    public EditText username;
    public EditText password;
    public Button btn_login;
    public SharedPreferences sharedpreferences;

    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Login");

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        username = (EditText) findViewById(R.id.edit_username);
        password = (EditText) findViewById(R.id.edit_password);

        btn_login = (Button) findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(username.getText().toString().equals("")){
                    Toast.makeText(login.this,"Username is required",Toast.LENGTH_LONG).show();
                    return;
                }

                if(password.getText().toString().equals("")){
                    Toast.makeText(login.this,"Password is required",Toast.LENGTH_LONG).show();
                    return;
                }

                mDatabase.child("users").orderByChild("username").startAt(username.getText().toString()).endAt(username.getText().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot!=null && dataSnapshot.getChildren()!=null &&
                                dataSnapshot.getChildren().iterator().hasNext()){


                            SharedPreferences.Editor editor = sharedpreferences.edit();

                            editor.putString("username", username.getText().toString());
                            editor.commit();

                            Intent intent = new Intent(login.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);


                        }else {
                            //Username Does Not Exist
                            Toast.makeText(login.this,"Invalid Username or Password",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError firebaseError) {
                        //Username Does Not Exist
                        Toast.makeText(login.this,"Error Please try again",Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        return true;
    }
}
