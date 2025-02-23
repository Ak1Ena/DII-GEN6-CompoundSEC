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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

public class BookedRoom {

    private String[] data = new String[15];
    private int j=0;
    private JSONArray bookedRooms;
    private String floor;
    private int roomCount = 15;

    public BookedRoom(String floor){
        this.floor = floor;
        loadBookedRooms(floor);
    }

    private void loadBookedRooms(String floor){
        try(FileReader reader = new FileReader("C:\\Users\\User\\Desktop\\DII-GEN6-CompoundSEC\\main\\src\\main\\java\\org\\app\\db\\booked_rooms.json")){
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine())!=null){
                content.append(line);
            }

            JSONObject json = new JSONObject(content.toString());
            JSONObject section = json.getJSONObject(floor);
            bookedRooms = section.getJSONArray("room");

        }catch (FileNotFoundException e){
            bookedRooms = new JSONArray();
            System.err.println("Booked rooms file not found. Created an empty array.");
        } catch (Exception e) {
            bookedRooms = new JSONArray();
            System.err.println("Error loading booked rooms: " + e.getMessage());
        }
    }

    public JPanel getTable(String floor){
        JPanel table = new JPanel();
        table.setLayout(new GridLayout(5,5,10,10));
        CustomLabel[] labels = new CustomLabel[15];

        String prefix = switch (floor){
            case "High" -> "C";
            case "Medium" -> "B";
            case "Low" -> "A";
            default -> "ERROR!";
        };
        int roomNumber = Integer.parseInt(
                switch (floor){
                    case "High" -> "3";
                    case "Medium" -> "2";
                    case "Low" -> "1";
                    default -> "0";
                }+"01"
        );

        for (int i = 0; i < roomCount; i++) {
            String room = prefix+roomNumber;
            labels[i] = new CustomLabel(room);
            labels[i].setOpaque(true);

            boolean isBooked = bookedRooms.toList().contains(room);

            if (isBooked){
                labels[i].setBackground(Color.GRAY);
                labels[i].setForeground(Color.RED);
                labels[i].setEnabled(false);
            }else {
                labels[i].setBackground(Color.LIGHT_GRAY);
                labels[i].setForeground(Color.WHITE);

                CustomLabel label = labels[i];

                label.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (label.isSelected()) {
                            label.setBackground(Color.LIGHT_GRAY);
                            label.setSelected(false);
                            label.setData("");
                        } else {
                            label.setBackground(Color.WHITE);
                            label.setForeground(Color.GREEN);
                            label.setSelected(true);
                            label.setData("Selected");
                        }
                        System.out.println("Label Name: " + label.getText());
                        data[j] = label.getText();
                        j++;
                    }
                });

            }

            table.add(labels[i]);
            roomNumber++;

        }
        return table;
    }

    public String[] getData() {
        return data;
    }

    public void addData(String name, String[] room, String floor, int days, String FILE_PATH){

        try {
            File file = new File(FILE_PATH);
            JSONObject root = new JSONObject();
            JSONArray dataArray = new JSONArray();
            JSONArray roomArray = new JSONArray();
            if (room != null) {
                roomArray = new JSONArray(Arrays.stream(room)
                        .filter(r -> r != null && !r.isEmpty()) // กรองค่า null และค่าว่าง
                        .collect(Collectors.toList()));
            }

            if (file.exists() && file.length()>0) {
                String content = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
                root = new JSONObject(content);
                dataArray = root.getJSONArray("data");
            }else {
                root = new JSONObject();
                dataArray = new JSONArray();
                root.put("data", dataArray);
            }


            LocalDate today = LocalDate.now();
            JSONObject newData = new JSONObject();
            newData.put("ID", Encryption.encrypt(name,room,floor,days));
            newData.put("Password",PasswordGenerator.generatePassword(6));
            newData.put("Name",name);
            newData.put("Room",roomArray);
            newData.put("Floor",floor);
            newData.put("Expire date",today.plusDays(days));

            dataArray.put(newData);

            try(FileWriter writer = new FileWriter(FILE_PATH)){
                writer.write(root.toString(2));
                writer.flush();
                System.out.println("บันทึกข้อมูลเสร็จสิ้น!!!!");
            }

        }catch (IOException e){
            e.printStackTrace();
        }


    }

    public void setData(String[] data) {
        j = 0;
        this.data = data;
    }
}
