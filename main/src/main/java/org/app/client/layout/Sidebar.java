package org.app.client.layout;

import org.app.client.button.Add;
import org.app.client.layout.component.UserSlideBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Sidebar {
    private JPanel sidebar;
    private JFrame frame;
    private JTextField searchField;
    private UserSlideBar slideBar;

    public Sidebar(JFrame frame) {
        this.frame = frame;
        this.sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(500, Frame.MAXIMIZED_VERT));
        sidebar.setBackground(Color.WHITE);
        sidebar.setLayout(new BorderLayout());
        sidebar.setBorder(BorderFactory.createLineBorder(Color.white, 20));

        setupSidebar();
    }

    public JPanel getSidebar() {
        return sidebar;
    }

    private void setupSidebar() {
        sidebar.removeAll();

        // Top Panel: Add button + Search bar
        JPanel topPanel = new JPanel(new BorderLayout());

        // Add Button
        Add addButton = new Add();
        topPanel.add(addButton.add(frame), BorderLayout.WEST);

        // Search Panel
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        JButton searchButton = new JButton("Search");

        searchButton.addActionListener(e -> searchRoom());
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        topPanel.add(searchPanel, BorderLayout.CENTER);

        sidebar.add(topPanel, BorderLayout.NORTH);

        // UserSlideBar
        slideBar = new UserSlideBar();
        slideBar.addUserCardsFromJson();
        sidebar.add(slideBar.userSlideBar(), BorderLayout.CENTER);

        // Refresh Button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshSidebar();
            }
        });
        sidebar.add(refreshButton, BorderLayout.SOUTH);

        sidebar.revalidate();
        sidebar.repaint();
    }

    // Function for Searching by Room Number
    private void searchRoom() {
        String roomNumber = searchField.getText().trim();
        if (!roomNumber.isEmpty()) {
            slideBar.filterUserCardsByRoom(roomNumber); // Call the filtering method
        } else {
            slideBar.addUserCardsFromJson(); // Reload all cards if search field is empty
        }
        sidebar.revalidate();
        sidebar.repaint();
    }

    public void refreshSidebar() {
        SwingUtilities.invokeLater(() -> {
            setupSidebar(); // Reload all data
            System.out.println("Sidebar updated!"); // Debugging log
        });
    }
}
