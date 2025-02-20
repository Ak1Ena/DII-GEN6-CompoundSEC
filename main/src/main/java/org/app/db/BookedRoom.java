package org.app.db;

import org.app.db.tools.CustomLabel;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

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
        try(FileReader reader = new FileReader("booked_rooms.json")){
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

    public JPanel getTable(){
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
                        data[j++] = label.getText();
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
}
