package ui.panels;

import ui.colour.Colour;

import javax.swing.*;
import java.awt.*;

// represents a null panel
public class NullPanel extends JPanel {

    // MODIFIES: this
    // EFFECTS: creates a new NullPanel with some default style properties
    public NullPanel() {
        setFocusable(false);
        setLayout(null);
        setBackground(Colour.DARK_GREY);
    }

    // MODIFIES: this
    // EFFECTS: sets the dimensions of this
    protected void setDimensions() {
        Dimension preferredSize = new Dimension();
        for (int i = 0; i < getComponentCount(); i++) {
            Rectangle bounds = getComponent(i).getBounds();
            preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
            preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
        }
        setMinimumSize(preferredSize);
        setPreferredSize(preferredSize);
    }
}
