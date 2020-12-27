package ui.panels;

import ui.elements.clickable.TodoAppButton;

import java.awt.*;

// represents the entire panel related to editing tasks
public class EditPanel extends NullPanel {
    public TodoAppButton markCompleteButton;
    public TodoAppButton deleteButton;
    public TodoAppButton clearFiltersButton;

    // MODIFIES: this
    // EFFECTS: creates a new EditPanel with some default style properties and elements
    public EditPanel() {
        initFields();
        setButtonBounds();
        addButtons();
        setDimensions();
    }

    // MODIFIES: this
    // EFFECTS: initializes all fields
    private void initFields() {
        markCompleteButton = new TodoAppButton("Mark Selected Task Complete");
        deleteButton = new TodoAppButton("Delete Selected Task");
        clearFiltersButton = new TodoAppButton("Clear Filters");
    }

    // MODIFIES: this
    // EFFECTS: sets all fields bounds
    private void setButtonBounds() {
        markCompleteButton.setBounds(315, 20, 280, 35);
        deleteButton.setBounds(715, 20, 280, 35);
        clearFiltersButton.setBounds(0, 0, 130, 69);
    }

    // MODIFIES: this
    // EFFECTS: adds all field elements to this
    private void addButtons() {
        add(clearFiltersButton);
        add(markCompleteButton);
        add(deleteButton);
    }
}
