package ui.elements.dropdowns;

import ui.colour.ColourSetter;

import javax.swing.*;

// represents a drop down for task information
public class TaskInfoDropDown extends JComboBox<String> {

    // MODIFIES: this
    // EFFECTS: creates a TaskInfoDropDown with default style properties
    public TaskInfoDropDown() {
        setFocusable(false);
        ColourSetter.setDefaultColours(this);
    }
}
