package org.app.client.pages;

import org.app.db.BookedRoom;
import org.app.server.tools.JSONWriter;
import org.app.server.enceypt.Encryption;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Arrays;

public class AddLayout {
    private String[] data;
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

        BookedRoom bookedRoom = new BookedRoom(String.valueOf(floorSelect.getSelectedItem()));


        //save button
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JSONWriter jsonWriter = new JSONWriter();
                JOptionPane.showMessageDialog(addDialog,"Submitted!");
                for (int i = 0; i < bookedRoom.getData().length; i++) {
                    if (bookedRoom.getData()[i] != null) {
                        jsonWriter.addRoom((String) floorSelect.getSelectedItem(), bookedRoom.getData()[i], "C:\\Users\\User\\Desktop\\DII-GEN6-CompoundSEC\\main\\src\\main\\java\\org\\app\\db\\booked_rooms.json");
                    }
                }
                try {
                    bookedRoom.addData(nameField.getText(), bookedRoom.getData(), (String) floorSelect.getSelectedItem(),Integer.parseInt(numberField.getText()),"C:\\Users\\User\\Desktop\\DII-GEN6-CompoundSEC\\main\\src\\main\\java\\org\\app\\db\\ResidentDB.json");
                }catch (NumberFormatException exception){
                    System.out.println(exception);
                }
                addDialog.setVisible(false);
            }
        });



        //cancel button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDialog.setVisible(false);
            }
        });

        // Panel สำหรับแสดงตาราง
        JPanel tableContainer = new JPanel(new BorderLayout());

        floorSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableContainer.removeAll();
                JPanel table = bookedRoom.getTable((String)floorSelect.getSelectedItem());
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
