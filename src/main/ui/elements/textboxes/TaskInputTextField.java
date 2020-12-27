package ui.elements.textboxes;

import ui.colour.Colour;
import ui.colour.ColourSetter;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

// represents a input field for task inputs
public class TaskInputTextField extends JTextField {

    // MODIFIES: this
    // EFFECTS: creates new TaskInputTextField with default style properties
    public TaskInputTextField() {
        super();
        setCaretColor(new Color(204, 204, 204));
        setFont(getFont().deriveFont(getFont().getSize() + 4f));
        setBorder(new BevelBorder(BevelBorder.LOWERED));
        ColourSetter.setColours(this, Colour.LIGHT_GREY, Colour.WHITE);
    }

}
