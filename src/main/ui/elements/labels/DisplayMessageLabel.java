package ui.elements.labels;

import ui.colour.Colour;

import javax.swing.*;
import java.awt.*;

// represents a label for displaying information to user
public class DisplayMessageLabel extends JLabel {

    // MODIFIES: this
    // EFFECTS: creates a DisplayMessageLabel with default style properties
    public DisplayMessageLabel() {
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.TOP);
        Font font = getFont();
        Font newFont = font.deriveFont(font.getStyle() | Font.BOLD, font.getSize() + 4f);
        setFont(newFont);
        setBackground(Colour.DARK_GREY);
    }

    // MODIFIES: this
    // EFFECTS: sets foreground (text color) to red
    public void setErrorColour() {
        setForeground(Color.red);
    }

    // MODIFIES: this
    // EFFECTS: sets foreground (text color) to green
    public void setGreenColour() {
        setForeground(new Color(35, 200, 56));
    }

}
