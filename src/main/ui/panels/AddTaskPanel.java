package ui.panels;

import ui.elements.clickable.TodoAppButton;
import ui.elements.dropdowns.CategoryDropDown;
import ui.elements.dropdowns.PriorityDropDown;
import ui.elements.labels.DisplayMessageLabel;
import ui.elements.labels.TaskInputLabel;
import ui.elements.textboxes.DateTextField;
import ui.elements.textboxes.TaskInputTextField;

// represents the entire panel related to adding tasks
public class AddTaskPanel extends NullPanel {
    public PriorityDropDown priorityDropDown;
    public CategoryDropDown categoryDropDown;
    public TodoAppButton addTaskButton;
    public TaskInputTextField descriptionInputTextField;
    public DateTextField dateInputTextField;
    public TaskInputLabel dateLabel;
    public TaskInputLabel descriptionLabel;
    public TaskInputLabel categoryLabel;
    public TaskInputLabel priorityLabel;
    public DisplayMessageLabel displayText;

    // MODIFIES: this
    // EFFECTS: creates a new AddTaskPanel with some default style properties and elements
    public AddTaskPanel() {
        initFields();
        setElementBounds();
        addElements();
        setDimensions();
    }

    // MODIFIES: this
    // EFFECTS: initializes all fields
    private void initFields() {
        addTaskButton = new TodoAppButton("Add Task");
        priorityDropDown = new PriorityDropDown();
        categoryDropDown = new CategoryDropDown();
        descriptionInputTextField = new TaskInputTextField();
        dateInputTextField = new DateTextField();
        dateLabel = new TaskInputLabel("Date (YYYY-MM-DD)");
        descriptionLabel = new TaskInputLabel("Description");
        categoryLabel = new TaskInputLabel("Category");
        priorityLabel = new TaskInputLabel("Priority");
        displayText = new DisplayMessageLabel();
    }

    // MODIFIES: this
    // EFFECTS: sets all fields bounds
    private void setElementBounds() {
        addTaskButton.setBounds(1035, 30, 100, 30);
        priorityDropDown.setBounds(935, 32, 80, 30);
        descriptionInputTextField.setBounds(205, 30, 595, 35);
        dateInputTextField.setBounds(35, 30, 145, 35);
        categoryDropDown.setBounds(825, 32, 90, 30);
        dateLabel.setBounds(50, 10, 120, 25);
        descriptionLabel.setBounds(210, 10, 545, 25);
        categoryLabel.setBounds(830, 10, 79, 25);
        priorityLabel.setBounds(935, 10, 79, 25);
        displayText.setBounds(260, 75, 700, 30);
    }

    // MODIFIES: this
    // EFFECTS: adds all field elements to this
    private void addElements() {
        add(addTaskButton);
        add(priorityDropDown);
        add(descriptionInputTextField);
        add(dateInputTextField);
        add(categoryDropDown);
        add(dateLabel);
        add(descriptionLabel);
        add(categoryLabel);
        add(priorityLabel);
        add(displayText);
    }
}
