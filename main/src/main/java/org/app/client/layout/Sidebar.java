package org.app.client.layout;

import org.app.client.button.Add;
import org.app.client.layout.component.UserSlideBar;

import javax.swing.*;
import java.awt.*;

public class Sidebar {

    public JPanel sidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(500, Frame.MAXIMIZED_VERT));
        sidebar.setBackground(Color.red);
        sidebar.setLayout(new BorderLayout());

        Add addButton = new Add();
        sidebar.add(addButton.add(), BorderLayout.NORTH);

        UserSlideBar slideBar = new UserSlideBar();
        sidebar.add(slideBar.userSlideBar(), BorderLayout.CENTER);

        sidebar.setBorder(BorderFactory.createLineBorder(Color.white,20));

        return sidebar;
    }




}
