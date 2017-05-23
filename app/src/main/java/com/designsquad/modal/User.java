package com.designsquad.modal;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by bimalpariyar on 5/23/17.
 */

@IgnoreExtraProperties
public class User {

    public String username;
    public String password;
    public String name;
    public String address;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String password, String name, String address) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.address = address;
    }
}
