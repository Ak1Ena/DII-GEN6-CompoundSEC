package org.app.client.pages;


import org.app.db.BookedRoom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

public class AddLayout {

    private static AddLayout instance;
    private final String DB_FILEPATH = System.getProperty("user.dir") + "\\main\\src\\main\\java\\org\\app\\db\\ResidentDB.json";
    private final String BR_FILEPATH = System.getProperty("user.dir") + "\\main\\src\\main\\java\\org\\app\\db\\booked_rooms.json";

    private BookedRoom bookedRoom;
    private JPanel tableContainer;

    public static AddLayout getInstance(){
        if (instance == null) {
            synchronized (AddLayout.class) {
                if (instance == null) {
                    instance = new AddLayout();
                }
            }
        }
        return instance;
    }
    private AddLayout(){}
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
        saveButton.setEnabled(false); // ปิดใช้งานปุ่มตอนแรก

        floorSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableContainer.removeAll();
                bookedRoom = new BookedRoom((String) floorSelect.getSelectedItem());
                JPanel table = bookedRoom.getTable((String) floorSelect.getSelectedItem());
                tableContainer.add(table, BorderLayout.CENTER);
                tableContainer.revalidate();
                tableContainer.repaint();
                saveButton.setEnabled(true); // เปิดใช้งานปุ่มเมื่อเลือกชั้นแล้ว
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nameField.getText().isEmpty() || numberField.getText().isEmpty() || Integer.parseInt(numberField.getText()) <= 0) {
                    JOptionPane.showMessageDialog(addDialog, "Please enter correctly!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(addDialog, "Submitted!");
                    try {
                        bookedRoom.addData(nameField.getText(), bookedRoom.getData(), (String) floorSelect.getSelectedItem(), Integer.parseInt(numberField.getText()), DB_FILEPATH);
                    } catch (NumberFormatException exception) {
                        System.out.println(exception);
                    }
                    addDialog.setVisible(false);
                }
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> addDialog.setVisible(false));

        tableContainer = new JPanel(new BorderLayout());

        addDialog.add(new JLabel("Name:"), gbc);
        gbc.gridy++;
        addDialog.add(nameField, gbc);
        gbc.gridy++;
        addDialog.add(new JLabel("Number of days to stay:"), gbc);
        gbc.gridy++;
        addDialog.add(numberField, gbc);
        gbc.gridy++;
        addDialog.add(new JLabel("Select Floor:"), gbc);
        gbc.gridy++;
        addDialog.add(floorSelect, gbc);
        gbc.gridy++;
        addDialog.add(tableContainer, gbc);
        gbc.gridy++;
        gbc.gridwidth = 1;
        addDialog.add(saveButton, gbc);
        gbc.gridx = 1;
        addDialog.add(cancelButton, gbc);

        addDialog.setVisible(true);
        return addDialog;
    }
}
