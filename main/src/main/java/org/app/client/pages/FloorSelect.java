package org.app.client.pages;

import org.app.client.tools.AccessCheck;
import org.app.db.Logs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class FloorSelect {
    private static FloorSelect instance;

    private RoomSelect roomSelect;
    private AccessCheck accessCheck;

    private FloorSelect() {
        try {
            accessCheck = AccessCheck.getInstance();
        } catch (IOException e) {
            throw new RuntimeException("Error loading access data", e);
        }
        roomSelect = RoomSelect.getInstance();
    }

    public static FloorSelect getInstance() {
        if (instance == null) {
            instance = new FloorSelect();
        }
        return instance;
    }

    public JFrame run() {
        Logs logs = Logs.getInstance();
        JFrame frame = new JFrame("Floor Selector");
        frame.setSize(500, 250);
        frame.setLayout(new FlowLayout(5, 6, 2));

        // 🔘 ปุ่มสำหรับชั้นต่าง ๆ
        JButton low = createFloorButton("Low", logs, frame);
        JButton medium = createFloorButton("Medium", logs, frame);
        JButton high = createFloorButton("High", logs, frame);

        frame.add(low);
        frame.add(medium);
        frame.add(high);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        return frame;
    }

    private JButton createFloorButton(String floorName, Logs logs, JFrame frame) {
        JButton button = new JButton(floorName);
        button.setPreferredSize(new Dimension(155, frame.getHeight()));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(floorName);
                if (accessCheck.checkUserFloor(floorName)) {
                    try {
                        logs.addToLogs("User", "Access " + floorName.toUpperCase() + " FLOOR", accessCheck.getUserID(), "SUCCESS");
                        roomSelect.run(floorName);
                        frame.dispose();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Access Denied", "Permission Error", JOptionPane.ERROR_MESSAGE);
                    logs.addToLogs("User", "Access " + floorName.toUpperCase() + " FLOOR", accessCheck.getUserID(), "DENIED");
                }
            }
        });

        return button;
    }
}
