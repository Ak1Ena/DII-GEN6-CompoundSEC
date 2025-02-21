package org.app.client.layout;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class NavBar {
    public JPanel navbar(){
        JPanel navbar = new JPanel();
        navbar.setLayout(new FlowLayout(FlowLayout.RIGHT));

        navbar.add(user());
        navbar.add(floor_menagement());
        navbar.add(logs());

        navbar.setBorder(new MatteBorder(0,20,0,20,Color.white));

        return navbar;
    }

    private JButton user(){
        JButton toUser = new JButton("USER MENAGEMENT");
        toUser.setPreferredSize(new Dimension(200,100));

        return toUser;
    };

    private JButton logs(){
        JButton toLogs = new JButton("LOGS");
        toLogs.setPreferredSize(new Dimension(200,100));
        return toLogs;
    }

    private JButton floor_menagement(){
        JButton toFloor_menagement = new JButton("FLOOR MENAGEMENT");
        toFloor_menagement.setPreferredSize(new Dimension(200,100));

        return toFloor_menagement;
    }


}
