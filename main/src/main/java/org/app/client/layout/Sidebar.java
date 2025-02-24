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

    public Sidebar(JFrame frame) {
        this.frame = frame;
        this.sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(500, Frame.MAXIMIZED_VERT));
        sidebar.setBackground(Color.red);
        sidebar.setLayout(new BorderLayout());
        sidebar.setBorder(BorderFactory.createLineBorder(Color.white, 20));

        setupSidebar();
    }

    public JPanel getSidebar() {
        return sidebar;
    }

    private void setupSidebar() {
        sidebar.removeAll();

        Add addButton = new Add();
        sidebar.add(addButton.add(frame), BorderLayout.NORTH);

        UserSlideBar slideBar = new UserSlideBar();
        slideBar.addUserCardsFromJson();
        sidebar.add(slideBar.userSlideBar(), BorderLayout.CENTER);

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

    public void refreshSidebar() {
        SwingUtilities.invokeLater(() -> {
            setupSidebar(); // โหลดข้อมูลใหม่
            System.out.println("Sidebar updated!"); // Debugging log
        });
    }
}
