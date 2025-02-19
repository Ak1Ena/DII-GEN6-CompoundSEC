package org.app.testing_playground.app.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.app.testing_playground.app.client.site.*;
import org.app.testing_playground.app.client.site.Add;

public class Home {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Management Controller");
        frame.setSize(200, 280);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("Management Controller");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setSize(150, 50); // Adjust size as needed

        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new FlowLayout());

        JButton add = new JButton("ADD");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Add();
            }
        });

        JButton modify = new JButton("MODIFY");
        modify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Modify();

            }
        });

        JButton revoke = new JButton("REVOKE");
        revoke.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Revoke();
            }
        });

        buttonContainer.add(add);
        buttonContainer.add(modify);
        buttonContainer.add(revoke);

        frame.setLayout(new BorderLayout());
        frame.add(title, BorderLayout.NORTH);
        frame.add(buttonContainer);

        // Make Frame Visible
        frame.setVisible(true);
    }
}
