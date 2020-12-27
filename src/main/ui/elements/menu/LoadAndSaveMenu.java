package ui.elements.menu;

import ui.colour.Colour;
import ui.colour.ColourSetter;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

// represents a load and save menu
public class LoadAndSaveMenu extends JMenu {
    public MenuButton loadButton;
    public MenuButton saveButton;
    public MenuAutoSaveCheckBox autoSaveCheckBox;

    // MODIFIES: this
    // EFFECTS: creates a new LoadAndSaveMenu with some default style properties and menu elements
    public LoadAndSaveMenu() {
        initFields();
        setup();
        addElements();
    }

    // MODIFIES: this
    // EFFECTS: initializes fields
    private void initFields() {
        loadButton = new MenuButton("Load");
        saveButton = new MenuButton("Save");
        autoSaveCheckBox = new MenuAutoSaveCheckBox();
    }

    // MODIFIES: this
    // EFFECTS: sets style properties
    private void setup() {
        setText("Menu");
        ColourSetter.setColours(this, Colour.DARK_RED, Colour.WHITE);
        setBorder(new EtchedBorder());
        setHorizontalTextPosition(SwingConstants.CENTER);
        setFocusable(false);
    }

    // MODIFIES: this
    // EFFECTS: adds all field elements to this
    private void addElements() {
        add(loadButton);
        add(saveButton);
        add(autoSaveCheckBox);
    }
}
