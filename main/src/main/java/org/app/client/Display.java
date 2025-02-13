package org.app.client;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;


public class Display {
    public static void main(String[] args) {
        int maxColumn = 10;


        JFrame frame = new JFrame("Controller");
        frame.setSize(1200,800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(300, frame.getHeight()));
        sidebar.setBackground(Color.red);
        sidebar.setLayout(new BoxLayout(sidebar,BoxLayout.Y_AXIS));
        sidebar.setLayout(new BorderLayout());

        JPanel user = new JPanel();
        user.setLayout(new BoxLayout(user,BoxLayout.Y_AXIS));


        for (int i = 0; i < 30; i++) {
            user.add(new JButton("Button " + i),BorderLayout.CENTER);  // Example components
        }



        JScrollPane user_menage = new JScrollPane(user);
        user_menage.setPreferredSize(new Dimension(290, 550));

        JButton add = new JButton("+");


        sidebar.add(add,BorderLayout.NORTH);
        sidebar.add(user_menage);

        frame.add(sidebar,BorderLayout.WEST);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
