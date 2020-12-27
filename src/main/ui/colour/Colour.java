package ui.colour;

import java.awt.*;

// represents a class containing colour fields
public final class Colour {
    public static final Color DARK_GREY = new Color(31, 31, 31);
    public static final Color WHITE = SystemColor.text;
    public static final Color LIGHT_GREY = new Color(50, 50, 50);
    public static final Color DARK_RED = new Color(226, 83, 72);

    // can not be called
    private Colour() {
    }
}
