package org.app.server.enceypt;

import java.util.Base64;

public class Encryption {
    public static String encrypt(String name,int room,int floor,int days){
        String data = name+"-"+room+"-"+floor+"-"+days+"-";
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


    //test
    /*
    public static void main(String[] args) {
        String encrypted = encrypt(20,1,3);
        System.out.println("Encrypted data: " + encrypted);
        int[] decrypted = decrypt(encrypted);
        System.out.println("Decrypted data: Room: " + decrypted[0] + ", Floor: " + decrypted[1] + ", Days: " + decrypted[2]);

    }
     */
}
