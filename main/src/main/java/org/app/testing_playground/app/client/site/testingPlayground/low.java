package org.app.client.site.testingPlayground;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class low {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Low Level Room Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);


        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 5, 10, 10)); // 5 แถว 5 คอลัมน์ และระยะห่าง 10 px

        CustomLabel[] labels = new CustomLabel[15];
        String prefix = "A";
        int roomNumber = 101;

        for (int i = 0; i < 15; i++) {
            String labelName = prefix + roomNumber; // ตั้งชื่อเช่น A101, A102, ...
            labels[i] = new CustomLabel(labelName);
            labels[i].setOpaque(true);
            labels[i].setBackground(Color.LIGHT_GRAY);
            labels[i].setForeground(Color.BLUE);
            labels[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

            final CustomLabel label = labels[i];


            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (label.isSelected()) {
                        label.setBackground(Color.LIGHT_GRAY); // เปลี่ยนกลับเป็นสีพื้นหลังเริ่มต้น
                        label.setSelected(false);              // อัปเดตสถานะเป็นไม่ถูกเลือก
                        label.setData("");                     // รีเซ็ตค่า
                    } else {
                        label.setBackground(Color.GREEN);     // เปลี่ยนเป็นสีเขียวเมื่อคลิก
                        label.setSelected(true);              // อัปเดตสถานะเป็นถูกเลือก
                        label.setData("Selected");            // ตั้งค่าข้อมูล
                    }
                    System.out.println("Label Name: " + label.getText() + ", Data: " + label.getData());
                }
            });

            panel.add(label);
            roomNumber++;
        }



        frame.add(panel, BorderLayout.CENTER);


        frame.setVisible(true);
    }
}
class CustomLabel extends JLabel {
    private boolean isSelected;
    private String data;

    public CustomLabel(String text) {
        super(text, SwingConstants.CENTER);
        this.isSelected = false;
        this.data = "";
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
