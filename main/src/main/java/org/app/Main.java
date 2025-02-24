package org.app;
import org.app.client.AppInterface;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AppInterface appInterface = new AppInterface();
                appInterface.run();
            }
        });
    }
}
