package ui.elements.clickable;

import ui.colour.ColourSetter;

import javax.swing.*;

// represents a radio button for filters
public class FilterRadioButton extends JRadioButton {

    // MODIFIES: this
    // EFFECTS: creates a new FilterRadioButton with default style properties
    public FilterRadioButton(String name) {
        setFocusable(false);
        setText(name);
        ColourSetter.setDefaultColours(this);
    }
}
