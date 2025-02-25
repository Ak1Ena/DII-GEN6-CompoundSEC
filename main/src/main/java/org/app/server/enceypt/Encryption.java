package org.app.server.enceypt;

import java.util.Base64;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Encryption {
    public static String encrypt(String name, String[] rooms, String floor, int days) {
        String roomData = String.join(",", rooms);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = now.format(formatter);
        String data = name + "-" + roomData + "-" + floor + "-" + days + "-" + timestamp;
        String encodedData = Base64.getUrlEncoder().encodeToString(data.getBytes());

        return encodedData.substring(0, 22);
    }

    public static String[] decrypt(String encryptedData) {
        // จะต้องมีการตัดข้อมูลกลับให้มีความยาวที่ตรงกับข้อมูลที่เข้ารหัส
        String fullData = encryptedData + "==";  // เติมให้ครบ 4 ตัวตามข้อกำหนดของ Base64
        String decodedData = new String(Base64.getUrlDecoder().decode(fullData));

        String[] parts = decodedData.split("-");
        String[] result = new String[5];
        for (int i = 0; i < 5; i++) {
            result[i] = parts[i];
        }
        return result;
    }
}