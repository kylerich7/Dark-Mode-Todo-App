package ui.elements.clickable;

import ui.colour.ColourSetter;

import javax.swing.*;

// represents a check box for filter properties
public class FilterPropertyCheckBox extends JCheckBox {

    // MODIFIES: this
    // EFFECTS: creates a new FilterPropertyCheckBox checkbox with default style properties
    public FilterPropertyCheckBox(String name) {
        setFocusable(false);
        setText(name);
        ColourSetter.setDefaultColours(this);
    }

}
