package org.app.client.pages;

import org.app.client.AppInterface;
import org.app.client.tools.AccessCheck;
import org.app.db.Logs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class RoomSelect {
    // 🔒 อินสแตนซ์เดียวของคลาสนี้
    private static RoomSelect instance;

    // ❌ ป้องกันการสร้างอินสแตนซ์จากภายนอก
    private RoomSelect() {}

    // ✅ ฟังก์ชันเรียกใช้งานอินสแตนซ์เดียว
    public static RoomSelect getInstance() {
        if (instance == null) {
            instance = new RoomSelect();
        }
        return instance;
    }

    // 🚀 สร้างและแสดงผล UI
    public JFrame run(String floor) throws IOException {
        JFrame frame = new JFrame("Room Selector");
        frame.setSize(500, 250);
        frame.setLayout(new FlowLayout(5, 6, 2));
        Logs logs = Logs.getInstance();

        String[] rooms = switch (floor) {
            case "Low" -> new String[] { "A100","A101", "A102", "A103", "A104", "A105", "A106", "A107", "A108", "A109", "A110", "A111", "A112", "A113", "A114", "A115" };
            case "Medium" -> new String[] { "B200","B201", "B202", "B203", "B204", "B205", "B206", "B207", "B208", "B209", "B210", "B211", "B212", "B213", "B214", "B215" };
            case "High" -> new String[] { "C300","C301", "C302", "C303", "C304", "C305", "C306", "C307", "C308", "C309", "C310", "C311", "C312", "C313", "C314", "C315" };
            default -> new String[] {};
        };

        JComboBox<String> roomComboBox = new JComboBox<>(rooms);
        roomComboBox.setPreferredSize(new Dimension(155, 30));

        frame.add(roomComboBox);

        roomComboBox.addActionListener(e -> {
            String selectedRoom = (String) roomComboBox.getSelectedItem();
            System.out.println("Selected room: " + selectedRoom);
        });

        JButton access = new JButton("Access");
        AccessCheck accessCheck = new AccessCheck();
        access.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (accessCheck.checkUserRoom((String) roomComboBox.getSelectedItem())) {
                    AppInterface appInterface = new AppInterface();
                    appInterface.run();
                    logs.addToLogs("User", "Access ROOM " + (String) roomComboBox.getSelectedItem(), accessCheck.getUserID(), "SUCCESS");

                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Access Denied", "Alert", JOptionPane.ERROR_MESSAGE);
                    logs.addToLogs("User", "Access ROOM " + (String) roomComboBox.getSelectedItem(), accessCheck.getUserID(), "DENIED");
                }
            }
        });

        frame.add(access);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        return frame;
    }
}
