package org.app.client.layout;

import javax.swing.*;
import java.awt.*;

public class InfoDisplay {

    public JPanel infoDisplay(){
        JPanel infoDisplay = new JPanel();
        infoDisplay.setPreferredSize(new Dimension(Frame.MAXIMIZED_HORIZ,Frame.MAXIMIZED_VERT));
        infoDisplay.setBackground(Color.LIGHT_GRAY);
        infoDisplay.setBorder(BorderFactory.createLineBorder(Color.white,20));


        return infoDisplay;
    }

}
