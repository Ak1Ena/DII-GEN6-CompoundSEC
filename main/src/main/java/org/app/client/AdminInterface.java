package org.app.client;

import org.app.client.layout.Sidebar;
import org.app.client.layout.NavBar;
import org.app.db.BookedRoom;
import org.app.db.Logs;

import javax.swing.*;
import java.awt.*;

public class AdminInterface {
    private static AdminInterface instance;  // Static instance of AdminInterface
    private Sidebar sidebar; // Keep reference of Sidebar

    // Public method to provide access to the instance (Singleton)
    public static AdminInterface getInstance() {
        if (instance == null) {
            synchronized (AdminInterface.class) {
                if (instance == null) {
                    instance = new AdminInterface();
                }
            }
        }
        return instance;
    }

    public JFrame app() {
        Logs logs = new Logs();
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.WHITE);

        // NavBar
        NavBar navBar = new NavBar();
        JPanel nav = navBar.navbar(frame);
        nav.setBackground(Color.white);
        frame.add(nav, BorderLayout.EAST);

        // Create Sidebar and add it to JFrame
        sidebar = new Sidebar(frame);
        frame.add(sidebar.getSidebar(), BorderLayout.WEST);

        // Back Button to AppInterface
        JButton backButton = new JButton("Back to AppInterface");
        backButton.addActionListener(e -> {
            logs.addToLogs("Admin", "LOGOUT", "", "SUCCESS");
            AppInterface appInterface = new AppInterface();
            appInterface.run();
            frame.dispose(); // Close AdminInterface
        });

        frame.add(backButton, BorderLayout.SOUTH); // Add back button to the bottom of the frame

        frame.revalidate();
        frame.repaint();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();

        return frame;
    }
}
