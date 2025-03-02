package org.app.server.enceypt;

import java.util.Base64;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Encryption extends EncrptionAbstract{


    @Override
    public String encrypt(String name, String[] rooms, String floor, int days) {
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

}
