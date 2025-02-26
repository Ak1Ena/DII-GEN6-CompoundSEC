package org.app.client.button;

import org.app.client.pages.AddLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Add {

    public JButton add(JFrame frame){
        JButton add = new JButton("Add");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddLayout addLayout = AddLayout.getInstance();
                addLayout.add_display(frame);
            }
        });
        return add;
    }

}
