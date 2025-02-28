package org.app.client;

import org.app.db.BookedRoom;
import org.app.db.Logs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppInterface {
    private final String DB_FILEPATH = System.getProperty("user.dir") + "\\main\\src\\main\\java\\org\\app\\db\\ResidentDB.json";
    private static AppInterface instance;

    private AppInterface(){
        //BookedRoom bookedRoom = new BookedRoom();
        //bookedRoom.removeExpiredData(DB_FILEPATH);
    }

    public static AppInterface getInstance() {
        if (instance == null) {
            instance = new AppInterface();
        }
        return instance;
    }

    private JDialog adminLogin(JFrame parentFrame) {
        Logs logs = Logs.getInstance();
        JDialog loginDialog = new JDialog(parentFrame, "Admin Login", true);
        loginDialog.setSize(300, 200);
        loginDialog.setLocationRelativeTo(parentFrame); // ให้แสดงกลางจอ

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passwordField.getPassword());

                if (username.equals("admin") && password.equals("admin123")) {
                    logs.addToLogs("Admin","LOGIN","","SUCCESS");
                    JOptionPane.showMessageDialog(loginDialog, "Login Success!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loginDialog.dispose();

                    parentFrame.dispose();
                    AdminInterface adminInterface = AdminInterface.getInstance();
                    adminInterface.app();
                } else {
                    logs.addToLogs("Admin","LOGIN","","DENIED");
                    JOptionPane.showMessageDialog(loginDialog, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(userLabel);
        panel.add(userField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);

        loginDialog.add(panel);
        loginDialog.setVisible(true);

        return loginDialog;
    }

    public JFrame run() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JButton user = new JButton("USER");
        user.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserInterface userInterface = UserInterface.getInstance();
                userInterface.run();
                frame.dispose();
            }
        });

        JButton admin = new JButton("Admin");
        admin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminLogin(frame);
            }
        });

        frame.add(user);
        frame.add(admin);

        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        return frame;
    }
}
