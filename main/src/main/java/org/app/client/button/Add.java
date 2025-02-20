package org.app.client.button;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Add {

    public JButton add(){
        JButton add = new JButton("Add");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("TEST");
            }
        });
        return add;
    }

}
