package org.app.client.pages;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LogViewer extends JDialog {
    private final String LOG_PATH = System.getProperty("user.dir") + "\\main\\src\\main\\java\\org\\app\\db\\Logs.txt";;
    private JTextArea logArea;

    // 1. สร้าง instance ของ LogViewer แบบ static
    private static LogViewer instance;

    // 2. สร้าง constructor แบบ private เพื่อไม่ให้มีการสร้าง object จากภายนอก
    private LogViewer(Frame parent) {
        super(parent, "Log Viewer", true); // true: modal dialog
        setSize(600, 400);
        setLocationRelativeTo(parent); // ทำให้ JDialog อยู่ตรงกลางของ JFrame ที่เรียก

        JPanel panel = new JPanel(new BorderLayout());

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(logArea);

        JButton refreshButton = new JButton("Reload Logs");
        refreshButton.addActionListener(e -> loadLogs());

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(refreshButton, BorderLayout.SOUTH);

        add(panel);

        loadLogs();
    }

    // 3. สร้าง method เพื่อให้เข้าถึง instance เดียวของ LogViewer
    public static LogViewer getInstance(Frame parent) {
        if (instance == null) {
            instance = new LogViewer(parent); // ถ้ายังไม่มี instance ให้สร้างใหม่
        }
        return instance;
    }

    private void loadLogs() {
        logArea.setText("");
        List<String> logs = getLogs();
        for (String log : logs) {
            logArea.append(log + "\n");
        }
    }

    public List<String> getLogs() {
        List<String> logList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(LOG_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                logList.add(line);
            }
        } catch (IOException e) {
            logList.add("เกิดข้อผิดพลาดในการอ่านไฟล์ Logs: " + e.getMessage());
        }
        return logList;
    }

}
