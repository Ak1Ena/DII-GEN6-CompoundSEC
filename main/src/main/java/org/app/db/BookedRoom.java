package org.app.db;

import org.app.db.tools.CustomLabel;
import org.app.server.enceypt.Encryption;
import org.json.JSONArray;
import org.json.JSONObject;
import org.app.client.tools.PasswordGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

public class BookedRoom {

    private String[] data = new String[15];
    private int j = 0;
    private JSONArray bookedRooms;
    private String floor;
    private int roomCount = 15;
    private RoomHandler roomHandler; // Polymorphism
    private final String DB_FILEPATH = System.getProperty("user.dir") + "\\main\\src\\main\\java\\org\\app\\db\\ResidentDB.json";
    private final String BR_FILEPATH = System.getProperty("user.dir") + "\\main\\src\\main\\java\\org\\app\\db\\booked_rooms.json";


    public BookedRoom(){
        removeExpiredData(DB_FILEPATH);
    }

    public BookedRoom(String floor) {
        this.floor = floor;
        loadBookedRooms(floor);
        setRoomHandler();
    }

    private void loadBookedRooms(String floor) {
        try (FileReader reader = new FileReader(BR_FILEPATH)) {
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line);
            }

            JSONObject json = new JSONObject(content.toString());
            JSONObject section = json.getJSONObject(floor);
            bookedRooms = section.getJSONArray("room");

        } catch (FileNotFoundException e) {
            bookedRooms = new JSONArray();
            System.err.println("Booked rooms file not found. Created an empty array.");
        } catch (Exception e) {
            bookedRooms = new JSONArray();
            System.err.println("Error loading booked rooms: " + e.getMessage());
        }
    }

    private void setRoomHandler() {
        switch (floor) {
            case "High":
                roomHandler = new HighFloorRoomHandler(bookedRooms);
                break;
            case "Medium":
                roomHandler = new MediumFloorRoomHandler(bookedRooms);
                break;
            case "Low":
                roomHandler = new LowFloorRoomHandler(bookedRooms);
                break;
            default:
                throw new IllegalArgumentException("Unknown floor: " + floor);
        }
    }

    public JPanel getTable(String floor) {
        JPanel table = new JPanel();
        table.setLayout(new GridLayout(5, 5, 10, 10));
        CustomLabel[] labels = new CustomLabel[15];

        String prefix = roomHandler.getPrefix(floor);
        int roomNumber = roomHandler.getRoomNumber(floor);

        for (int i = 0; i < roomCount; i++) {
            String room = prefix + roomNumber;
            labels[i] = new CustomLabel(room);
            labels[i].setOpaque(true);

            boolean isBooked = roomHandler.isRoomBooked(room);

            if (isBooked) {
                labels[i].setBackground(Color.GRAY);
                labels[i].setForeground(Color.RED);
                labels[i].setEnabled(false);
            } else {
                labels[i].setBackground(Color.LIGHT_GRAY);
                labels[i].setForeground(Color.WHITE);

                CustomLabel label = labels[i];
                label.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (label.isSelected()) {
                            // ถ้าเลือกห้องอยู่แล้ว ให้ยกเลิกการเลือก
                            label.setBackground(Color.LIGHT_GRAY);
                            label.setSelected(false);
                            label.setData("");
                            // ลบข้อมูลห้องที่ถูกเลือกออกจากตัวแปร data
                            removeSelectedRoom(label.getText());
                        } else {
                            // ถ้ายังไม่ถูกเลือก ให้เลือกห้อง
                            label.setBackground(Color.GRAY);
                            label.setSelected(true);
                            label.setData("Selected");
                            // เพิ่มข้อมูลห้องที่ถูกเลือกเข้าไปในตัวแปร data
                            addSelectedRoom(label.getText());
                        }
                        System.out.println("Label Name: " + label.getText());
                    }
                });
            }

            table.add(labels[i]);
            roomNumber++;
        }

        return table;
    }

    // ฟังก์ชันสำหรับเพิ่มห้องที่ถูกเลือกลงใน data
    private void addSelectedRoom(String room) {
        // เช็คว่า data เต็มหรือไม่ ก่อนที่จะเพิ่มห้อง
        if (j < data.length) {
            data[j] = room;
            j++;
        }
    }

    // ฟังก์ชันสำหรับลบห้องที่ถูกเลือกออกจาก data
    private void removeSelectedRoom(String room) {
        for (int i = 0; i < j; i++) {
            if (data[i].equals(room)) {
                // เมื่อพบห้องที่เลือกไว้ ให้ย้ายข้อมูลที่เหลือมาทางซ้าย
                for (int k = i; k < j - 1; k++) {
                    data[k] = data[k + 1];
                }
                data[j - 1] = null; // ลบห้องสุดท้าย
                j--; // ลดจำนวนห้องที่เลือก
                break;
            }
        }
    }

    public String[] getData() {
        return data;
    }

    public void addData(String name, String[] room, String floor, int days, String FILE_PATH) {
        try {
            File file = new File(FILE_PATH);
            JSONObject root = new JSONObject();
            JSONArray dataArray = new JSONArray();
            JSONArray roomArray = new JSONArray();
            if (room != null) {
                roomArray = new JSONArray(Arrays.stream(room)
                        .filter(r -> r != null && !r.isEmpty())
                        .collect(Collectors.toList()));
            }

            if (file.exists() && file.length() > 0) {
                String content = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
                root = new JSONObject(content);
                dataArray = root.getJSONArray("data");
            } else {
                root = new JSONObject();
                dataArray = new JSONArray();
                root.put("data", dataArray);
            }

            // Use Timebase-Encryption for user ID
            String userID = Encryption.encrypt(name, room, floor, days);

            LocalDate today = LocalDate.now();
            JSONObject newData = new JSONObject();
            newData.put("ID", userID);
            newData.put("Password", PasswordGenerator.generatePassword(6));
            newData.put("Name", name);
            newData.put("Room", roomArray);
            newData.put("Floor", floor);
            newData.put("Expire date", today.plusDays(days).toString());

            dataArray.put(newData);

            Logs logs = Logs.getInstance();
            logs.addToLogs("Admin", "Add", userID, "SUCCESS");

            try (FileWriter writer = new FileWriter(FILE_PATH)) {
                writer.write(root.toString(2));
                writer.flush();
                System.out.println("บันทึกข้อมูลเสร็จสิ้น!!!!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeExpiredData(String filePath) {
        try {
            File file = new File(filePath);
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONObject root = new JSONObject(content);
            JSONArray dataArray = root.getJSONArray("data");

            JSONObject bookedRoomsJson = new JSONObject(new String(Files.readAllBytes(Paths.get(BR_FILEPATH))));

            LocalDate today = LocalDate.now();

            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject entry = dataArray.getJSONObject(i);
                String expireDateStr = entry.getString("Expire date");
                LocalDate expireDate = LocalDate.parse(expireDateStr);

                if (expireDate.isBefore(today)) {
                    String[] rooms = new String[entry.getJSONArray("Room").length()];
                    for (int j = 0; j < rooms.length; j++) {
                        rooms[j] = entry.getJSONArray("Room").getString(j);
                    }

                    for (String room : rooms) {
                        removeRoomFromBookedRooms(bookedRoomsJson, room);
                    }

                    dataArray.remove(i);
                    i--;
                }
            }

            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(root.toString(2));
                writer.flush();
            }

            try (FileWriter writer = new FileWriter(BR_FILEPATH)) {
                writer.write(bookedRoomsJson.toString(2));
                writer.flush();
            }

        } catch (IOException e) {
            System.err.println("Error while removing expired data: " + e.getMessage());
        }
    }

    private void removeRoomFromBookedRooms(JSONObject bookedRoomsJson, String room) {
        // ค้นหาห้องในแต่ละ section (High, Medium, Low)
        for (String section : bookedRoomsJson.keySet()) {
            JSONObject sectionObj = bookedRoomsJson.getJSONObject(section);
            JSONArray roomArray = sectionObj.getJSONArray("room");

            // ลบห้องที่ตรงกัน
            for (int i = 0; i < roomArray.length(); i++) {
                if (roomArray.getString(i).equals(room)) {
                    roomArray.remove(i);
                    System.out.println("Removed room: " + room);
                    return;
                }
            }
        }
    }

}
