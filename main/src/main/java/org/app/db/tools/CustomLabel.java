package org.app.db.tools;
import javax.swing.*;

public class CustomLabel extends JLabel {
    private boolean isSelected;
    private String data;

    public CustomLabel(String text){
        super(text, SwingConstants.CENTER);
        this.isSelected = false;
        this.data = "";
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
