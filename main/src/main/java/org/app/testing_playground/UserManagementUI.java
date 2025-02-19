package org.app.testing_playground;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UserManagementUI {
    private JFrame frame;
    private JPanel userPanel, controlPanel;
    private JButton addButton;
    private ArrayList<JPanel> userCards;

    public UserManagementUI() {
        frame = new JFrame("User Management");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        userPanel = new JPanel();
        userPanel.setLayout(new GridLayout(0, 3, 10, 10));
        JScrollPane scrollPane = new JScrollPane(userPanel);
        frame.add(scrollPane, BorderLayout.CENTER);

        controlPanel = new JPanel();
        addButton = new JButton("Add New User");
        controlPanel.add(addButton);
        frame.add(controlPanel, BorderLayout.SOUTH);

        userCards = new ArrayList<>();

        addButton.addActionListener(e -> openAddUserDialog());

        frame.setVisible(true);
    }

    private void openAddUserDialog() {
        JDialog dialog = new JDialog(frame, "Add New User", true);
        dialog.setSize(400, 500);
        dialog.setLayout(new GridLayout(0, 1));

        JTextField nameField = new JTextField();
        JComboBox<String> floorSelect = new JComboBox<>(new String[]{"Low Floor", "Medium Floor", "High Floor"});
        JPanel roomSelectionPanel = createRoomSelectionPanel();
        JComboBox<String> roleSelect = new JComboBox<>(new String[]{"Visitor", "Resident", "Staff"});
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        dialog.add(new JLabel("Name:"));
        dialog.add(nameField);
        dialog.add(new JLabel("Select Floor:"));
        dialog.add(floorSelect);
        dialog.add(new JLabel("Select Room:"));
        dialog.add(roomSelectionPanel);
        dialog.add(new JLabel("Role:"));
        dialog.add(roleSelect);
        dialog.add(saveButton);
        dialog.add(cancelButton);

        cancelButton.addActionListener(e -> dialog.dispose());
        saveButton.addActionListener(e -> {
            addUserCard(nameField.getText(), (String) floorSelect.getSelectedItem());
            dialog.dispose();
        });

        dialog.setVisible(true);
    }

    private JPanel createRoomSelectionPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 6, 5, 5));
        for (int i = 101; i <= 315; i++) {
            if (i % 100 >= 16) continue;
            JButton roomButton = new JButton(String.valueOf(i));
            roomButton.addActionListener(e -> roomButton.setBackground(Color.GRAY));
            panel.add(roomButton);
        }
        return panel;
    }

    private void addUserCard(String name, String floor) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        card.add(new JLabel(name + " - " + floor), BorderLayout.CENTER);
        JButton modifyButton = new JButton("Modify");
        JButton revokeButton = new JButton("...");
        modifyButton.addActionListener(e -> modifyUser(card, name, floor));
        revokeButton.addActionListener(e -> revokeUser(card));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(modifyButton);
        buttonPanel.add(revokeButton);
        card.add(buttonPanel, BorderLayout.SOUTH);

        userPanel.add(card);
        userPanel.revalidate();
        userPanel.repaint();
        userCards.add(card);
    }

    private void modifyUser(JPanel card, String name, String floor) {
        JDialog dialog = new JDialog(frame, "Modify User", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(0, 1));

        JTextField nameField = new JTextField(name);
        JComboBox<String> floorSelect = new JComboBox<>(new String[]{"Low Floor", "Medium Floor", "High Floor"});
        floorSelect.setSelectedItem(floor);
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        dialog.add(new JLabel("Name:"));
        dialog.add(nameField);
        dialog.add(new JLabel("Floor:"));
        dialog.add(floorSelect);
        dialog.add(saveButton);
        dialog.add(cancelButton);

        cancelButton.addActionListener(e -> dialog.dispose());
        saveButton.addActionListener(e -> {
            ((JLabel) card.getComponent(0)).setText(nameField.getText() + " - " + floorSelect.getSelectedItem());
            dialog.dispose();
        });

        dialog.setVisible(true);
    }

    private void revokeUser(JPanel card) {
        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to revoke this user?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            userPanel.remove(card);
            userPanel.revalidate();
            userPanel.repaint();
            userCards.remove(card);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UserManagementUI::new);
    }
}
