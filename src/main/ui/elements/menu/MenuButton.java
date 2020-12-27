package ui.elements.menu;

import ui.colour.ColourSetter;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

// represents a button within the menu
public class MenuButton extends JMenuItem {

    // MODIFIES: this
    // EFFECTS: creates a MenuButton with default style properties
    public MenuButton(String menuButtonText) {
        setText(menuButtonText);
        setBorder(new EtchedBorder());
        ColourSetter.setDefaultColours(this);
    }
}
