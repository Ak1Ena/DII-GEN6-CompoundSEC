package org.app.authenticator;

import java.util.Base64;


public class Encryption {

    public String encrypt(String data){
        return Base64.getEncoder().encodeToString(data.getBytes());
    }

    public String decrypt(String encrypt){
        byte[] decodeBytes = Base64.getDecoder().decode(encrypt);
        return new String(decodeBytes);
    }

}
