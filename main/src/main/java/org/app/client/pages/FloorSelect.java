package org.app.client.pages;

import org.app.client.tools.AccessCheck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class FloorSelect {

    private RoomSelect roomSelect;
    private AccessCheck accessCheck;

    public JFrame run() {
        JFrame frame = new JFrame("Floor Selector");
        frame.setSize(500, 250);
        frame.setLayout(new FlowLayout(5, 6, 2));

        try {
            accessCheck = new AccessCheck(); // Create AccessCheck once
        } catch (IOException e) {
            throw new RuntimeException("Error loading access data", e);
        }

        roomSelect = new RoomSelect(); // Initialize roomSelect here

        JButton low = new JButton("Low");
        low.setPreferredSize(new Dimension(155, frame.getHeight()));
        low.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(low.getText());
                if (accessCheck.checkUserFloor(low.getText())) {
                    try {
                        roomSelect.run(low.getText());
                        frame.dispose();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Denied", "Alert", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton medium = new JButton("Medium");
        medium.setPreferredSize(new Dimension(155, frame.getHeight()));
        medium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(medium.getText());
                if (accessCheck.checkUserFloor(medium.getText())) {
                    try {
                        roomSelect.run(medium.getText());
                        frame.dispose();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Denied", "Alert", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton high = new JButton("High");
        high.setPreferredSize(new Dimension(155, frame.getHeight()));
        high.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(high.getText());
                if (accessCheck.checkUserFloor(high.getText())) {
                    try {
                        roomSelect.run(high.getText());
                        frame.dispose();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Denied", "Alert", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.add(low);
        frame.add(medium);
        frame.add(high);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        return frame;
    }
}
