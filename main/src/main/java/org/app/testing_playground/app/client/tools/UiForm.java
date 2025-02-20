package org.app.testing_playground.app.client.tools;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UiForm {
    public JButton checkBox(String room){
        JButton customCheckBox = new JButton(room);
        customCheckBox.setBounds(50, 50, 200, 50);
        customCheckBox.setFocusPainted(false); // ซ่อนเส้นขอบโฟกัส
        customCheckBox.setBackground(Color.LIGHT_GRAY); // สีเริ่มต้น
        customCheckBox.setFont(new Font("TH Sarabun New", Font.BOLD, 20));

        customCheckBox.addMouseListener(new MouseAdapter() {
            private boolean isSelected = false;

            @Override
            public void mouseClicked(MouseEvent e) {
                isSelected = !isSelected; // สลับสถานะ
                if (isSelected) {
                    customCheckBox.setBackground(Color.WHITE); // เปลี่ยนสีเมื่อเลือก
                    customCheckBox.setText("Selected");
                } else {
                    customCheckBox.setBackground(Color.LIGHT_GRAY); // สีปกติเมื่อไม่ได้เลือก
                    customCheckBox.setText(room);
                }
            }
        });
        customCheckBox.setLayout(new GridBagLayout());
        customCheckBox.setPreferredSize(new Dimension(5,2));
        return customCheckBox;
    }
    public JTextField TextField(int column,int height,int width){
        JTextField textInput = new JTextField(column);
        textInput.setPreferredSize(new Dimension(width, height));
        return textInput;
    }
    public JSpinner input_number(int start, int min, int max,int increase){
        SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel(start,min,max,increase);
        JSpinner spinner = new JSpinner(spinnerNumberModel);
        return spinner;
    }

    public JFrame frame(String title){
        JFrame frame = new JFrame(title);
        frame.setSize(500, 280);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        return frame;
    }

    /**
     * สร้าง JLabel พร้อมกำหนดข้อความ ขนาด และการจัดตำแหน่ง
     *
     * @param text ข้อความที่จะใช้แสดงใน JLabel
     * @param args การจัดตำแหน่งข้อความใน JLabel (CENTER = 0,LEFT = 2, RIGHT = 4)
     * @param width ความกว้างของ JLabel
     * @param height ความสูงของ JLabel
     * @return JLabel ที่สร้างขึ้นพร้อมกำหนดค่าต่าง ๆ
     */
    public JLabel label(String text, int args, int width, int height) {
        JLabel Text = new JLabel(text); // สร้าง JLabel พร้อมข้อความ
        Text.setHorizontalAlignment(args); // กำหนดการจัดตำแหน่งข้อความ
        Text.setSize(width, height); // กำหนดขนาดของ JLabel
        return Text; // คืนค่า JLabel
    }

    public JPanel box(){
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        return panel;
    }


}
