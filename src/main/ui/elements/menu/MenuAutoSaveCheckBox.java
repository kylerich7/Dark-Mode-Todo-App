package ui.elements.menu;

import ui.colour.ColourSetter;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

// represents a check box for auto-save within the menu
public class MenuAutoSaveCheckBox extends JCheckBoxMenuItem {

    // MODIFIES: this
    // EFFECTS: creates a MenuAutoSaveCheckBox with default style properties;
    public MenuAutoSaveCheckBox() {
        setText("Auto Save");
        setBorder(new EtchedBorder());
        ColourSetter.setDefaultColours(this);
    }
}
