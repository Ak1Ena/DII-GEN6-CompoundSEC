package org.app.server.enceypt;

import java.util.Base64;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Encryption {

    // ฟังก์ชันเข้ารหัส
    public static String encrypt(String name, String[] rooms, String floor, int days) {
        StringBuilder data = new StringBuilder(name);
        for (String room : rooms) {
            if (room != null) {
                data.append("-").append(room);
            }
        }
        data.append("-").append(floor);
        data.append("-").append(days);
        data.append("-").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        return Base64.getUrlEncoder().encodeToString(data.toString().getBytes());
    }

    // ฟังก์ชันถอดรหัส
    public static String[] decrypt(String encryptedData) {
        String decodedData = new String(Base64.getUrlDecoder().decode(encryptedData));
        return decodedData.split("-");
    }

    public static void main(String[] args) {

        String[] decrypted = decrypt("QUpBTiBLSUQtQzMwMC1IaWdoLTItMjAyNS0wMi0yOA==");
        System.out.println("Decrypted: " + decrypted[0]);  // แสดงข้อมูลทั้งหมด
    }
}
