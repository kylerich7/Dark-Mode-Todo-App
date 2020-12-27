package ui.elements.dropdowns;

// represents a drop down for user to select category
public class CategoryDropDown extends TaskInfoDropDown {

    // MODIFIES: this
    // EFFECTS: creates a new CategoryDropDown with default style properties and categories
    public CategoryDropDown() {
        addItem("Personal");
        addItem("Health");
        addItem("Work");
        addItem("School");
        addItem("Other");
        setSelectedItem("Personal");
    }
}
