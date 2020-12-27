package ui.colour;

import javax.swing.*;
import java.awt.*;

// represents a ColourSetter utility class that sets the colours of JComponents
public final class ColourSetter {

    // cannot be called
    private ColourSetter() {
    }

    // MODIFIES: element
    // EFFECTS: sets element background to bgColor, foreground to fgColor
    public static void setColours(JComponent element, Color bgColor, Color fgColor) {
        element.setBackground(bgColor);
        element.setForeground(fgColor);
    }

    // MODIFIES: element
    // EFFECTS: sets element background to Colour.DARK_GREY, foreground to Colour.WHITE
    public static void setDefaultColours(JComponent element) {
        setColours(element, Colour.DARK_GREY, Colour.WHITE);
    }


}
