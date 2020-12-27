package ui.elements.clickable;

import ui.colour.Colour;
import ui.colour.ColourSetter;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

// represents a button for the to-do app
public class TodoAppButton extends JButton {

    // MODIFIES: this
    // EFFECTS: creates a new TodoAppButton with default style properties and text set to buttonText
    public TodoAppButton(String buttonText) {
        setFont(getFont().deriveFont(getFont().getStyle() | Font.BOLD));
        setText(buttonText);
        ColourSetter.setColours(this, Colour.LIGHT_GREY, Colour.DARK_RED);
        setBorder(new EtchedBorder());
        setFocusable(false);
    }
}
