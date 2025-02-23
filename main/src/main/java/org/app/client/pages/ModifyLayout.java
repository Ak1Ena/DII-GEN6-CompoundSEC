package org.app.client.pages;

import org.app.db.BookedRoom;
import org.app.server.tools.JSONWriter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
/*
public class ModifyLayout {
    private BookedRoom bookedRoom;
    private JPanel tableContainer;

    public JDialog modifyDisplay(JFrame parent, String existingName, int existingDays, String existingFloor) {
        JDialog modifyDialog = new JDialog(parent, "Modify Management", true);
        modifyDialog.setLayout(new GridBagLayout());
        modifyDialog.setSize(500, 600);
        modifyDialog.setLocationRelativeTo(null);
        modifyDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        JTextField nameField = new JTextField(existingName, 20);
        JComboBox<String> floorSelect = new JComboBox<>(new String[]{"Low", "Medium", "High"});
        floorSelect.setSelectedItem(existingFloor);

        NumberFormat numberFormat = NumberFormat.getIntegerInstance();
        JFormattedTextField numberField = new JFormattedTextField(numberFormat);
        numberField.setColumns(10);
        numberField.setValue(existingDays);

        floorSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableContainer.removeAll();
                bookedRoom = new BookedRoom((String) floorSelect.getSelectedItem());
                JPanel table = bookedRoom.getTable((String) floorSelect.getSelectedItem());
                tableContainer.add(table, BorderLayout.CENTER);
                tableContainer.revalidate();
                tableContainer.repaint();
            }
        });

        // Update button
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JSONWriter jsonWriter = new JSONWriter();
                JOptionPane.showMessageDialog(modifyDialog, "Updated!");
                for (int i = 0; i < bookedRoom.getData().length; i++) {
                    if (bookedRoom.getData()[i] != null) {
                        jsonWriter.updateRoom((String) floorSelect.getSelectedItem(), bookedRoom.getData()[i], "C:\\Users\\User\\Desktop\\DII-GEN6-CompoundSEC\\main\\src\\main\\java\\org\\app\\db\\booked_rooms.json");
                    } else {
                        break;
                    }
                }
                try {
                    bookedRoom.updateData(nameField.getText(), bookedRoom.getData(), (String) floorSelect.getSelectedItem(), Integer.parseInt(numberField.getText()), "C:\\Users\\User\\Desktop\\DII-GEN6-CompoundSEC\\main\\src\\main\\java\\org\\app\\db\\ResidentDB.json");
                } catch (NumberFormatException exception) {
                    System.out.println(exception);
                }
                modifyDialog.setVisible(false);
            }
        });

        // Cancel button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyDialog.setVisible(false);
            }
        });

        bookedRoom = new BookedRoom(existingFloor);
        tableContainer = new JPanel(new BorderLayout());
        JPanel table = bookedRoom.getTable(existingFloor);
        tableContainer.add(table, BorderLayout.CENTER);

        // Arrange Components
        modifyDialog.add(new JLabel("Name:"), gbc);
        gbc.gridy++;
        modifyDialog.add(nameField, gbc);
        gbc.gridy++;
        modifyDialog.add(new JLabel("Number of days:"), gbc);
        gbc.gridy++;
        modifyDialog.add(numberField, gbc);
        gbc.gridy++;
        modifyDialog.add(new JLabel("Select Floor:"), gbc);
        gbc.gridy++;
        modifyDialog.add(floorSelect, gbc);
        gbc.gridy++;

        // Table
        gbc.gridy++;
        modifyDialog.add(tableContainer, gbc);

        // Buttons
        gbc.gridy++;
        gbc.gridwidth = 1;
        modifyDialog.add(updateButton, gbc);
        gbc.gridx = 1;
        modifyDialog.add(cancelButton, gbc);

        modifyDialog.setVisible(true);

        return modifyDialog;
    }
}
*/