package com.DesignQuads.AssistanceFinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.DesignQuads.modal.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {


    private DatabaseReference mDatabase;
    public EditText username;
    public EditText Email;
    public EditText phone;
    public EditText password;
    public Button btn_register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Registration");

        mDatabase = FirebaseDatabase.getInstance().getReference();

        username = (EditText) findViewById(R.id.edit_username);
        password = (EditText) findViewById(R.id.edit_password);
        phone = (EditText) findViewById(R.id.edit_phone);
        Email = (EditText) findViewById(R.id.edit_email);


        btn_register = (Button) findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(username.getText().toString().equals("")){
                    Toast.makeText(register.this,"Name is required",Toast.LENGTH_LONG).show();
                    return;
                }

                if(Email.getText().toString().equals("")){
                    Toast.makeText(register.this,"Email Address is required",Toast.LENGTH_LONG).show();
                    return;
                }

                if(phone.getText().toString().equals("")){
                    Toast.makeText(register.this,"Phone Number is required",Toast.LENGTH_LONG).show();
                    return;
                }

                if(password.getText().toString().equals("")){
                    Toast.makeText(register.this,"Password is required",Toast.LENGTH_LONG).show();
                    return;
                }

                writeNewUser(username.getText().toString(),Email.getText().toString(),phone.getText().toString(),password.getText().
                        toString());

                username.setText("");
                Email.setText("");
                phone.setText("");
                password.setText("");

                Toast.makeText(register.this,"You Have Registered Successfully.... ",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(register.this, login.class);
                startActivity(intent);
            }
        });

    }

    private void writeNewUser(String username, String Email, String phone, String password) {
        User user = new User(username, Email, phone, password);



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
