package org.app.testing_playground.app.client.site;

import org.app.client.tools.Encrypt;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import org.app.testing_playground.app.client.Home;
import org.app.testing_playground.app.client.tools.JSONWriter;
import org.app.testing_playground.app.client.tools.UiForm;
import org.app.testing_playground.app.client.site.floor.low;
import java.awt.*;
import java.io.FileNotFoundException;

public class Add extends JFrame {
    private String name;
    private String room;
    private String[] floor = {"LOW","MEDIUM","HIGH"};

    private String accessibility;
        public Add() {
            // สร้าง UiForm instance
            UiForm add = new UiForm();

            setTitle("Add Management");
            setLayout(new BorderLayout()); // ใช้ BorderLayout สำหรับ Layout
            setSize(500, 600); // ขนาดหน้าต่าง
            setLocationRelativeTo(null); // จัด Frame ให้อยู่กลางหน้าจอ
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // ปิดเฉพาะหน้าต่างนี้

            JPanel inputContainer = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);

// Label "Name :"
            JLabel name = add.label("Name :", SwingConstants.LEFT, 50, 25);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.weightx = 0; // ไม่ต้องการขยาย Label
            gbc.weighty = 0;
            gbc.fill = GridBagConstraints.NONE;
            inputContainer.add(name, gbc);

// TextArea with ScrollPane
            JTextArea textArea = new JTextArea(5, 20);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            JScrollPane scrollPane = new JScrollPane(textArea);
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.weightx = 1.0; // ขยาย TextArea ตามแนวนอน
            gbc.weighty = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            inputContainer.add(scrollPane, gbc);
// Number of people
            JLabel number = add.label("Numebr of people :",SwingConstants.LEFT,20,25);
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.weightx = 0; // ไม่ต้องการขยาย Label
            gbc.weighty = 0;
            gbc.fill = GridBagConstraints.NONE;
            inputContainer.add(number,gbc);
            JTextArea numberPeople = new JTextArea(3, 1);
            gbc.gridx = 1;
            gbc.gridy = 1;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.weightx = 1.0;
            gbc.weighty = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            inputContainer.add(numberPeople,gbc);
            //Floor Lavel
            JLabel Floor = add.label("Floor Level :",SwingConstants.LEFT,20,25);
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.weightx = 0; // ไม่ต้องการขยาย Label
            gbc.weighty = 0;
            gbc.fill = GridBagConstraints.NONE;
            inputContainer.add(Floor,gbc);
            //select bar
            JComboBox<String> select = new JComboBox<>(floor);
            gbc.gridx = 1;
            gbc.gridy = 2;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.weightx = 1.0; // ไม่ต้องการขยาย Label
            gbc.weighty = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            inputContainer.add(select,gbc);

            JPanel LOW = new JPanel(new BorderLayout());
            low low = new low();
            LOW.add(low.getTable(), BorderLayout.CENTER);
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 2; // ให้ Table ครอบคลุมทั้งความกว้าง
            gbc.weightx = 1.0; // ขยาย Table ตามแนวนอน
            gbc.weighty = 1.0; // ขยาย Table ตามแนวตั้ง
            gbc.fill = GridBagConstraints.BOTH; // เติมเต็มทั้งพื้นที่
            LOW.setVisible(false);
            inputContainer.add(LOW, gbc);
            select.addActionListener(e -> {
                String selectedValue = (String) select.getSelectedItem();
                if ("LOW".equals(selectedValue)) {
                    LOW.setVisible(true); // แสดง Table Panel
                } else {
                    LOW.setVisible(false); // ซ่อน Table Panel
                }
            });
            TitledBorder titledBorder = BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(Color.BLACK),
                    "ADD Data Management",
                    TitledBorder.LEFT,
                    TitledBorder.TOP
            );
            inputContainer.setBorder(titledBorder);

            // เพิ่ม Label กับ TextField ลงใน Panel

            //low low = new low();
            //inputContainer.add(low.getTable());

            // เพิ่มปุ่ม
            JButton submitButton = new JButton("Submit");
            submitButton.addActionListener(e -> {
                // ดึงข้อมูลจากฟิลด์
                //String name = nameField.getText();

                /*
                // แสดงผลข้อมูลใน Console หรือสามารถนำไปใช้ต่อได้
                System.out.println("Name: " + name);
                System.out.println("Room: " + room);
                System.out.println("Accessibility: " + accessibility);
*/
                // แสดงข้อความแจ้งเตือน

                JSONWriter jsonWriter = new JSONWriter();
                JOptionPane.showMessageDialog(this, "Data Submitted Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                for (int i = 0; i < low.getData().length; i++) {
                    if (low.getData()[i] != null) {
                        jsonWriter.addRoom("Low",low.getData()[i],"src\\main\\java\\org\\app\\client\\site\\floor\\booked_rooms.json");
                    }
                }
                dispose();
            });

            // เพิ่ม Components ลงใน JFrame
            add(inputContainer, BorderLayout.CENTER);
            add(submitButton, BorderLayout.SOUTH);

            // แสดงผล
            setVisible(true);
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(Add::new);

        }
    }

