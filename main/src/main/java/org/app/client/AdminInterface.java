package org.app.client;

import org.app.client.layout.Sidebar;
import org.app.client.layout.NavBar;
import org.app.db.Logs;

import javax.swing.*;
import java.awt.*;

public class AdminInterface {
    private Sidebar sidebar; // เก็บ reference ของ Sidebar

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

        // สร้าง Sidebar และเพิ่มลงใน JFrame
        sidebar = new Sidebar(frame);
        frame.add(sidebar.getSidebar(), BorderLayout.WEST);

        // ปุ่มย้อนกลับไปหน้า AppInterface
        JButton backButton = new JButton("Back to AppInterface");
        backButton.addActionListener(e -> {
            logs.addToLogs("Admin","LOGOUT","","SUCCESS");
            AppInterface appInterface = new AppInterface();
            appInterface.run();
            frame.dispose(); // ปิดหน้าจอ AdminInterface
        });

        frame.add(backButton, BorderLayout.SOUTH); // เพิ่มปุ่มในตำแหน่งใต้สุดของ frame

        frame.revalidate();
        frame.repaint();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();

        return frame;
    }

}
