package org.app.client.pages;

import org.app.db.BookedRoom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

public class AddLayout {

    public JDialog add_display(JFrame parent) {
        JDialog addDialog = new JDialog(parent, "Add Management", true);
        addDialog.setLayout(new GridBagLayout());
        addDialog.setSize(500, 600);
        addDialog.setLocationRelativeTo(null);
        addDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        JTextField nameField = new JTextField(20);
        JComboBox<String> floorSelect = new JComboBox<>(new String[]{"Low", "Medium", "High"});

        NumberFormat numberFormat = NumberFormat.getIntegerInstance();
        JFormattedTextField numberField = new JFormattedTextField(numberFormat);
        numberField.setColumns(10);

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        // Panel สำหรับแสดงตาราง
        JPanel tableContainer = new JPanel(new BorderLayout());

        // ActionListener สำหรับ JComboBox
        floorSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableContainer.removeAll(); // ลบตารางเดิมออก
                BookedRoom room = new BookedRoom(String.valueOf(floorSelect.getSelectedItem()));
                JPanel table = room.getTable();
                tableContainer.add(table, BorderLayout.CENTER);
                tableContainer.revalidate();
                tableContainer.repaint();
            }
        });

        // จัดเรียง Components ใน Layout
        addDialog.add(new JLabel("Name:"), gbc);
        gbc.gridy++;
        addDialog.add(nameField, gbc);
        gbc.gridy++;
        addDialog.add(new JLabel("Number of days:"), gbc);
        gbc.gridy++;
        addDialog.add(numberField, gbc);
        gbc.gridy++;
        addDialog.add(new JLabel("Select Floor:"), gbc);
        gbc.gridy++;
        addDialog.add(floorSelect, gbc);
        gbc.gridy++;

        // ตำแหน่งของ tableContainer (อยู่ใต้ JComboBox)
        gbc.gridy++;
        addDialog.add(tableContainer, gbc);

        // ปุ่ม Save / Cancel
        gbc.gridy++;
        gbc.gridwidth = 1;
        addDialog.add(saveButton, gbc);
        gbc.gridx = 1;
        addDialog.add(cancelButton, gbc);

        addDialog.setVisible(true);

        return addDialog;
    }

}
