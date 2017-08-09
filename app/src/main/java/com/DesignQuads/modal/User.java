package com.DesignQuads.modal;
import com.google.firebase.database.IgnoreExtraProperties;

import java.security.NoSuchAlgorithmException;


@IgnoreExtraProperties
public class User {

    public String username;
    public String Email;
    public String phone;
    public String password;

    public User(){}

    public User(String username, String Email, String phone, String password) {
        this.username = username;
        this.Email = Email;
        this.phone = phone;
        this.password = User.getHash(password);
    }

    public static String getHash(String x){
        try {
            java.security.MessageDigest d = null;
            d = java.security.MessageDigest.getInstance("SHA-1");
            d.reset();
            d.update(x.getBytes());
            return convertToHex(d.digest());
        }catch (Exception ex){
            return "";
        }
    }

    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        }
        return buf.toString();
    }

}

