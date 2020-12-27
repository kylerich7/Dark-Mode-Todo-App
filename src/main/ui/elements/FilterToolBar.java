package ui.elements;

import ui.colour.ColourSetter;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

// represents a toolbar for the filter radio buttons and check boxes to live within
public class FilterToolBar extends JToolBar {
    private static final int DEFAULT_WIDTH = 200;
    private static final int DEFAULT_HEIGHT = 400;

    // MODIFIES: this
    // EFFECTS: creates a new FilterToolBar with width and height, and some certain default style properties
    public FilterToolBar(int width, int height) {
        setMinimumSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
        setOrientation(SwingConstants.VERTICAL);
        setBorder(new EtchedBorder());
        ColourSetter.setDefaultColours(this);
        setFocusable(false);
    }

    // MODIFIES: this
    // EFFECTS: creates a new FilterToolBar with DEFAULT_WIDTH and DEFAULT_HEIGHT, and some default style properties
    public FilterToolBar() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
}
