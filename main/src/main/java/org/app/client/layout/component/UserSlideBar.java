package org.app.client.layout.component;

import javax.swing.*;
import java.awt.*;

public class UserSlideBar {

    public JScrollPane userSlideBar(){
        JPanel user = new JPanel();
        user.setLayout(new BoxLayout(user,BoxLayout.Y_AXIS));

        JScrollPane user_menage = new JScrollPane(user);
        user_menage.setPreferredSize(new Dimension(200,Frame.MAXIMIZED_VERT));
        return user_menage;
    }

}
