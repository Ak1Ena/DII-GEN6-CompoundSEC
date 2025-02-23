package org.app.client.layout;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class NavBar {
    public JPanel navbar(){
        JPanel navbar = new JPanel();
        navbar.setLayout(new GridLayout(3, 1, 0, 20)); // 3 แถว 1 คอลัมน์, มีช่องว่าง 20px ระหว่างปุ่ม

        navbar.add(logs());
        navbar.add(floor_menagement());
        navbar.add(user());

        navbar.setBorder(new MatteBorder(0,20,0,20,Color.white));

        return navbar;
    }

    private JButton user(){
        JButton toUser = new JButton("USER MENAGEMENT");
        toUser.setPreferredSize(new Dimension(200,100));
        toUser.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100)); // กำหนดขนาดสูงสุดของปุ่ม
        return toUser;
    };

    private JButton logs(){
        JButton toLogs = new JButton("LOGS");
        toLogs.setPreferredSize(new Dimension(200,100));
        toLogs.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        return toLogs;
    }

    private JButton floor_menagement(){
        JButton toFloor_menagement = new JButton("FLOOR MENAGEMENT");
        toFloor_menagement.setPreferredSize(new Dimension(200,100));
        toFloor_menagement.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        return toFloor_menagement;
    }
}
