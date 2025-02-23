package org.app.server.enceypt;

import java.util.Base64;
/*
public class Encryption {
    public static String encrypt(String name, String[] rooms, String floor, int days) {
        String roomData = String.join(",", rooms);
        String data = name + "-" + roomData + "-" + floor + "-" + days + "-";
        return Base64.getUrlEncoder().encodeToString(data.getBytes());
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

 */

public class Encryption {
    public static String encrypt(String name, String[] rooms, String floor, int days) {
        String roomData = String.join(",", rooms);
        String data = name + "-" + roomData + "-" + floor + "-" + days + "-";

        // ใช้ Base64 URL-safe encoding แล้วตัดบางส่วนเพื่อให้ข้อมูลสั้นลง
        String encodedData = Base64.getUrlEncoder().encodeToString(data.getBytes());

        // ตัดข้อมูลที่เกินความจำเป็นออก
        return encodedData.substring(0, 22);  // ตัดเป็นความยาวสั้นๆ
    }

    public static String[] decrypt(String encryptedData) {
        // จะต้องมีการตัดข้อมูลกลับให้มีความยาวที่ตรงกับข้อมูลที่เข้ารหัส
        String fullData = encryptedData + "==";  // เติมให้ครบ 4 ตัวตามข้อกำหนดของ Base64
        String decodedData = new String(Base64.getUrlDecoder().decode(fullData));

        String[] parts = decodedData.split("-");
        String[] result = new String[4];
        for (int i = 0; i < 4; i++) {
            result[i] = parts[i];
        }
        return result;
    }
}



