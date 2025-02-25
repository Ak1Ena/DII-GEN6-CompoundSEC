package org.app.db;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logs {
    private final String LOG_PATH = System.getProperty("user.dir") + "\\main\\src\\main\\java\\org\\app\\db\\Logs.txt";

    public void addToLogs(String position, String operator, String userID, String status) {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);

        String text = "[ " + formattedTime + " ] USER ID: " + userID + " POSITION: " + position +
                " OPERATOR: " + operator + " STATUS: " + status;

        try (FileWriter writer = new FileWriter(LOG_PATH, true);
             PrintWriter pw = new PrintWriter(writer)) {
            pw.println(text);
        } catch (IOException e) {
            System.err.println("เกิดข้อผิดพลาดในการเขียนไฟล์: " + e.getMessage());
        }
    }

}
