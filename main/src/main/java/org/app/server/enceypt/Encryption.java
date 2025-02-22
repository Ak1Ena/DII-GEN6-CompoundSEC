package org.app.server.enceypt;

import java.util.Base64;

public class Encryption {
    public static String encrypt(String name, String[] rooms, String floor, int days) {
        String roomData = String.join(",", rooms);
        String data = name + "-" + roomData + "-" + floor + "-" + days + "-";
        return Base64.getEncoder().encodeToString(data.getBytes());
    }

    public static String[] decrypt(String encryptedData) {
        String decodedData = new String(Base64.getDecoder().decode(encryptedData));
        String[] parts = decodedData.split("-");
        String[] result = new String[4];
        for (int i = 0; i < 4; i++) {
            result[i] = parts[i];
        }
        return result;
    }
}


