package org.app.client;

import org.app.client.layout.NavBar;
import org.app.client.layout.Sidebar;
import org.app.client.pages.FloorSelect;
import org.app.client.tools.AccessCheck;
import org.app.db.BookedRoom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class UserInterface {
    // 🔒 อินสแตนซ์เดียวของคลาสนี้
    private static UserInterface instance;

    private JFrame frame;

    // ❌ ป้องกันการสร้างอินสแตนซ์จากภายนอก (private constructor)
    private UserInterface() {
    }

    // ✅ ฟังก์ชันเรียกใช้งานอินสแตนซ์เดียว (Singleton Accessor)
    public static UserInterface getInstance() {
        if (instance == null) {
            instance = new UserInterface();
        }
        return instance;
    }

    // 🚀 สร้างและแสดงผล UI
    public JFrame run() {
        frame = new JFrame("User Login");
        frame.setLayout(new FlowLayout());
        frame.setSize(500, 250);

        frame.add(new JLabel("Username:"));
        JTextField username = new JTextField();
        username.setPreferredSize(new Dimension(frame.getWidth() - 20, 30));
        frame.add(username);

        frame.add(new JLabel("Password"));
        JTextField psw = new JTextField();
        psw.setPreferredSize(new Dimension(frame.getWidth() - 20, 30));
        frame.add(psw);

        JButton login = new JButton("Login");
        login.setPreferredSize(new Dimension(frame.getWidth() - 20, 40));

        // 🛡️ ตรวจสอบการเข้าสู่ระบบ
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    AccessCheck accessCheck = new AccessCheck();
                    if (accessCheck.checkUser(username.getText(), psw.getText())) {
                        FloorSelect floorSelect = FloorSelect.getInstance();
                        floorSelect.run();
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Access Denied", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        frame.add(login);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        return frame;
    }
}
