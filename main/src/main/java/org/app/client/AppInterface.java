package org.app.client;

import org.app.client.layout.Sidebar;
import org.app.client.layout.NavBar;
import org.app.client.layout.InfoDisplay;

import javax.swing.*;
import java.awt.*;

public class AppInterface {

    public JFrame app(){
        JFrame frame = new JFrame();
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.WHITE);

        NavBar navBar = new NavBar();
        JPanel nav = navBar.navbar();
        nav.setBackground(Color.white);
        frame.add(nav,BorderLayout.NORTH);

        Sidebar sidebar = new Sidebar();
        frame.add(sidebar.sidebar(frame),BorderLayout.WEST);

        InfoDisplay infoDisplay = new InfoDisplay();
        frame.add(infoDisplay.infoDisplay());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        return frame;
    }

    public static void main(String[] args) {
        AppInterface run = new AppInterface();
        run.app();
    }


}
