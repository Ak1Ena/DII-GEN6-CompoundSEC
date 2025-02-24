package org.app.client.pages;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LogViewer extends JDialog {
    private final String LOG_PATH = "C:\\Users\\User\\Desktop\\DII-GEN6-CompoundSEC\\main\\src\\main\\java\\org\\app\\db\\Logs.txt";
    private JTextArea logArea;

    public LogViewer(Frame parent) {
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

    public static void main(String[] args) {
        // เรียก LogViewer ในรูปแบบ JDialog
        SwingUtilities.invokeLater(() -> {
            JFrame parentFrame = new JFrame();  // สร้าง JFrame เป็น parent
            parentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            parentFrame.setSize(200, 200); // ขนาดเล็ก ๆ ของ parent frame
            parentFrame.setVisible(true);

            LogViewer logViewer = new LogViewer(parentFrame); // ส่ง parent JFrame ให้กับ LogViewer
            logViewer.setVisible(true); // แสดง LogViewer (JDialog)
        });
    }
}
