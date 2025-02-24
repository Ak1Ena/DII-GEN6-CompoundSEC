package org.app.client;

import org.app.client.layout.Sidebar;
import org.app.client.layout.NavBar;

import javax.swing.*;
import java.awt.*;

public class AdminInterface {
    private Sidebar sidebar; // เก็บ reference ของ Sidebar

    public JFrame app() {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.WHITE);

        // NavBar
        NavBar navBar = new NavBar();
        JPanel nav = navBar.navbar(frame);
        nav.setBackground(Color.white);
        frame.add(nav, BorderLayout.EAST);

        // สร้าง Sidebar และเพิ่มลงใน JFrame
        sidebar = new Sidebar(frame);
        frame.add(sidebar.getSidebar(), BorderLayout.WEST);

        /*
        InfoDisplay infoDisplay = new InfoDisplay();
        frame.add(infoDisplay.infoDisplay());
        */

        frame.revalidate();
        frame.repaint();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();

        return frame;
    }

    public static void main(String[] args) {
        AdminInterface run = new AdminInterface();
        run.app();
    }
}
