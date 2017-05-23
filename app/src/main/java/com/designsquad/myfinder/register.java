package com.designsquad.myfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.designsquad.modal.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {


    private DatabaseReference mDatabase;
    public EditText username;
    public EditText password;
    public EditText name;
    public EditText address;
    public Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Register");

        mDatabase = FirebaseDatabase.getInstance().getReference();

        username = (EditText) findViewById(R.id.edit_username);
        password = (EditText) findViewById(R.id.edit_password);
        name = (EditText) findViewById(R.id.edit_name);
        address = (EditText) findViewById(R.id.edit_address);

        btn_register = (Button) findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(name.getText().toString().equals("")){
                    Toast.makeText(register.this,"Name is required",Toast.LENGTH_LONG).show();
                    return;
                }

                if(address.getText().toString().equals("")){
                    Toast.makeText(register.this,"Address is required",Toast.LENGTH_LONG).show();
                    return;
                }

                if(username.getText().toString().equals("")){
                    Toast.makeText(register.this,"Username is required",Toast.LENGTH_LONG).show();
                    return;
                }

                if(password.getText().toString().equals("")){
                    Toast.makeText(register.this,"Password is required",Toast.LENGTH_LONG).show();
                    return;
                }

                writeNewUser(username.getText().toString(),password.getText().toString(),name.getText().toString(),address.getText().toString());

                name.setText("");
                address.setText("");
                username.setText("");
                password.setText("");

                Toast.makeText(register.this,"User register successfully !!!",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void writeNewUser(String username, String password, String name, String address) {
        User user = new User(username, password, name, address);
        String userId = mDatabase.push().getKey();
        mDatabase.child("users").child(userId).setValue(user);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        return true;
    }
}
