package org.app.client.layout;

import org.app.client.pages.LogViewer;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavBar {
    public JPanel navbar(JFrame frame){
        JPanel navbar = new JPanel();
        navbar.setLayout(new GridLayout(3, 1, 0, 20)); // 3 แถว 1 คอลัมน์, มีช่องว่าง 20px ระหว่างปุ่ม

        navbar.add(logs(frame));

        navbar.setBorder(new MatteBorder(0,20,0,20,Color.white));

        return navbar;
    }

    private JButton user(){
        JButton toUser = new JButton("USER MENAGEMENT");
        toUser.setPreferredSize(new Dimension(200,100));
        toUser.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100)); // กำหนดขนาดสูงสุดของปุ่ม
        return toUser;
    };

    private JButton logs(JFrame frame){
        JButton toLogs = new JButton("LOGS");
        toLogs.setPreferredSize(new Dimension(200,100));
        toLogs.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        toLogs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LogViewer logViewer = LogViewer.getInstance(frame);
                logViewer.setVisible(true);
            }
        });

        return toLogs;
    }

    private JButton floor_menagement(){
        JButton toFloor_menagement = new JButton("FLOOR MENAGEMENT");
        toFloor_menagement.setPreferredSize(new Dimension(200,100));
        toFloor_menagement.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        return toFloor_menagement;
    }
}
