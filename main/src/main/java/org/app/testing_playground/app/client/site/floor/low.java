package org.app.testing_playground.app.client.site.floor;

//import org.app.testing_playground.app.client.tools.*;

import org.app.client.tools.CustomLabel;
import org.app.testing_playground.app.client.tools.UiForm;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class low {
    private String[] data = new String[15];
    private int j = 0;
    private JSONArray bookedRooms;

    public low() {
        loadBookedRooms();
    }

    private void loadBookedRooms() {
        try (FileReader reader = new FileReader("src\\main\\java\\org\\app\\client\\site\\floor\\booked_rooms.json");
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line);
            }

            JSONObject json = new JSONObject(content.toString());
            JSONObject lowSection = json.getJSONObject("Low");
            bookedRooms = lowSection.getJSONArray("room");

        } catch (FileNotFoundException e) {
            bookedRooms = new JSONArray(); // หากไม่มีไฟล์ ให้สร้างอาร์เรย์เปล่า
            System.err.println("Booked rooms file not found. Created an empty array.");
        } catch (Exception e) {
            bookedRooms = new JSONArray(); // หากเกิดข้อผิดพลาดอื่น ๆ ให้สร้างอาร์เรย์เปล่า
            System.err.println("Error loading booked rooms: " + e.getMessage());
        }
    }

    public JPanel getTable() {
        UiForm low = new UiForm();
        JPanel box = low.box();
        box.setLayout(new GridLayout(5, 5, 10, 10));
        CustomLabel[] labels = new CustomLabel[15];
        String prefix = "A";
        int roomNumber = 101;

        for (int i = 0; i < 15; i++) {
            String room = prefix + roomNumber;
            labels[i] = new CustomLabel(room);
            labels[i].setOpaque(true);

            boolean isBooked = bookedRooms.toList().contains(room);

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

            box.add(labels[i]);
            roomNumber++;
        }
        return box;
    }

    public String[] getData() {
        return data;
    }
}
