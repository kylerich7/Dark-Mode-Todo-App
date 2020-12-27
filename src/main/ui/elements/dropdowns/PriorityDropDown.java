package ui.elements.dropdowns;

// represents a dropdown for a user to select a priority
public class PriorityDropDown extends TaskInfoDropDown {

    // MODIFIES: this
    // EFFECTS: creates a new PriorityDropDown with default style properties and priorities
    public PriorityDropDown() {
        addItem("Low");
        addItem("Medium");
        addItem("High");
        setSelectedItem("Low");
    }

}
