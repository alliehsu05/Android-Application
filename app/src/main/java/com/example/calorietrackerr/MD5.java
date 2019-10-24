package com.example.calorietrackerr;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    public static String getMD5(String input) {
        try {
            //MD5 hashing
            MessageDigest md = MessageDigest.getInstance("MD5");
            //digest() method is called to calculate message digest of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());
            //convert byte array into signum representation
            BigInteger number = new BigInteger(1, messageDigest);
            //conver message digest into hex value
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
