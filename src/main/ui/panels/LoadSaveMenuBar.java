package ui.panels;

import ui.elements.menu.LoadAndSaveMenu;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;


// represents the load and save menu bar
public class LoadSaveMenuBar extends JMenuBar {
    public LoadAndSaveMenu loadAndSaveMenu;

    // MODIFIES: this
    // EFFECTS: creates a new LoadSaveMenuBar with some default style properties a menu element
    public LoadSaveMenuBar() {
        loadAndSaveMenu = new LoadAndSaveMenu();
        setup();
        add(loadAndSaveMenu);
    }

    // MODIFIES: this
    // EFFECTS: sets style properties for this
    private void setup() {
        setBorder(new BevelBorder(BevelBorder.LOWERED));
        setMinimumSize(new Dimension(47, 40));
        setPreferredSize(new Dimension(47, 30));
        setFocusable(false);
        setBackground(new Color(45, 45, 45));
    }

}
