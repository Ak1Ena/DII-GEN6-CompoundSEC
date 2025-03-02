package org.app.server.enceypt;

import java.util.Base64;

public abstract class EncrptionAbstract {

    public abstract String encrypt(String name, String[] rooms, String floor, int days);
    public static String[] decrypt(String encryptedData) {
        String decodedData = new String(Base64.getUrlDecoder().decode(encryptedData));
        return decodedData.split("-");
    }

}
