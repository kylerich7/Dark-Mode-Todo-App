package ui.elements.labels;

import ui.colour.ColourSetter;

import javax.swing.*;
import java.awt.*;

// represents a label for task inputs
public class TaskInputLabel extends JLabel {

    // MODIFIES: this
    // EFFECTS: creates a TaskInputLabel with default style properties and text set to labelText
    public TaskInputLabel(String labelText) {
        setText(labelText);
        setHorizontalAlignment(SwingConstants.CENTER);
        setFont(getFont().deriveFont(getFont().getStyle() | Font.BOLD));
        ColourSetter.setDefaultColours(this);
    }

}
