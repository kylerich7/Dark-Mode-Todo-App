package ui.panels;

import ui.elements.FilterToolBar;
import ui.elements.clickable.FilterPropertyCheckBox;
import ui.elements.clickable.FilterRadioButton;

import javax.swing.*;
import java.util.ArrayList;

// represents the entire toolbar related to task filters
public class FiltersToolBar extends FilterToolBar {
    public FilterToolBar filterPropertyToolBar;
    public FilterToolBar statusFilterToolBar;
    public FilterToolBar daysUntilDueFilterToolBar;
    public FilterToolBar categoryFilterToolBar;
    public FilterToolBar priorityFilterToolBar;
    public FilterPropertyCheckBox activateFilterCheckBox;
    public FilterPropertyCheckBox stackFilterCheckBox;
    public FilterRadioButton completeFilterCheckBox;
    public FilterRadioButton currentFilterCheckBox;
    public FilterRadioButton highPriorityFilterCheckBox;
    public FilterRadioButton mediumPriorityFilterCheckBox;
    public FilterRadioButton lowPriorityFilterCheckBox;
    public FilterRadioButton overdueFilterCheckBox;
    public FilterRadioButton dueTodayFilterCheckBox;
    public FilterRadioButton dueWeekFilterCheckBox;
    public FilterRadioButton dueMonthFilterCheckBox;
    public FilterRadioButton personalFilterCheckBox;
    public FilterRadioButton healthFilterCheckBox;
    public FilterRadioButton schoolFilterCheckBox;
    public FilterRadioButton workFilterCheckBox;
    public FilterRadioButton otherFilterCheckBox;
    public ArrayList<JToggleButton> toggleButtons;

    // MODIFIES: this
    // EFFECTS: creates a new FiltersToolBar with some default style properties and elements
    public FiltersToolBar() {
        super();
        initFields();
        setupToolBars();
        addToolBars();
    }

    // MODIFIES: this
    // EFFECTS: initializes all fields
    private void initFields() {
        filterPropertyToolBar = new FilterToolBar();
        statusFilterToolBar = new FilterToolBar();
        priorityFilterToolBar = new FilterToolBar();
        categoryFilterToolBar = new FilterToolBar();
        daysUntilDueFilterToolBar = new FilterToolBar();

        activateFilterCheckBox = new FilterPropertyCheckBox("Activate Filters");
        stackFilterCheckBox = new FilterPropertyCheckBox("Stack Filters");

        completeFilterCheckBox = new FilterRadioButton("Complete");
        currentFilterCheckBox = new FilterRadioButton("Current");
        highPriorityFilterCheckBox = new FilterRadioButton("High Priority");
        mediumPriorityFilterCheckBox = new FilterRadioButton("Medium Priority");
        lowPriorityFilterCheckBox = new FilterRadioButton("Low Priority");

        overdueFilterCheckBox = new FilterRadioButton("Overdue");
        dueTodayFilterCheckBox = new FilterRadioButton("Due Today");
        dueWeekFilterCheckBox = new FilterRadioButton("Due Within Week");
        dueMonthFilterCheckBox = new FilterRadioButton("Due Within Month");
        personalFilterCheckBox = new FilterRadioButton("Personal");
        healthFilterCheckBox = new FilterRadioButton("Health");
        schoolFilterCheckBox = new FilterRadioButton("School");
        workFilterCheckBox = new FilterRadioButton("Work");
        otherFilterCheckBox = new FilterRadioButton("Other");
        addTogglesToList();
    }

    // MODIFIES: this
    // EFFECTS: adds all toggles to the toggleButtons list
    private void addTogglesToList() {
        toggleButtons = new ArrayList<>();
        toggleButtons.add(completeFilterCheckBox);
        toggleButtons.add(currentFilterCheckBox);
        toggleButtons.add(highPriorityFilterCheckBox);
        toggleButtons.add(mediumPriorityFilterCheckBox);
        toggleButtons.add(lowPriorityFilterCheckBox);
        toggleButtons.add(overdueFilterCheckBox);
        toggleButtons.add(dueTodayFilterCheckBox);
        toggleButtons.add(dueWeekFilterCheckBox);
        toggleButtons.add(dueMonthFilterCheckBox);
        toggleButtons.add(personalFilterCheckBox);
        toggleButtons.add(healthFilterCheckBox);
        toggleButtons.add(workFilterCheckBox);
        toggleButtons.add(otherFilterCheckBox);
        toggleButtons.add(schoolFilterCheckBox);
        toggleButtons.add(stackFilterCheckBox);
        toggleButtons.add(activateFilterCheckBox);
    }

    // MODIFIES: this
    // EFFECTS: adds all the elements to their respective FilterToolBar
    private void setupToolBars() {
        setupFilterPropertyToolBar();
        setupStatusFilterToolBar();
        setupPriorityFilterToolBar();
        setupDaysUntilDueFilterToolBar();
        setupCategoryFilterToolBar();
    }

    // MODIFIES: this, (FilterPropertyToolBar field)
    // EFFECTS: adds all the elements to FilterPropertyToolBar
    private void setupFilterPropertyToolBar() {
        filterPropertyToolBar.add(activateFilterCheckBox);
        filterPropertyToolBar.add(stackFilterCheckBox);
    }

    // MODIFIES: this, (StatusFilterToolBar field)
    // EFFECTS: adds all the elements to StatusFilterToolBar
    private void setupStatusFilterToolBar() {
        statusFilterToolBar.add(completeFilterCheckBox);
        statusFilterToolBar.add(currentFilterCheckBox);
    }

    // MODIFIES: this, (PriorityFilterToolBar field)
    // EFFECTS: adds all the elements to PriorityFilterToolBar
    private void setupPriorityFilterToolBar() {
        priorityFilterToolBar.add(highPriorityFilterCheckBox);
        priorityFilterToolBar.add(mediumPriorityFilterCheckBox);
        priorityFilterToolBar.add(lowPriorityFilterCheckBox);
    }

    // MODIFIES: this, (DaysUntilDueFilterToolBar field)
    // EFFECTS: adds all the elements to DaysUntilDueFilterToolBar
    private void setupDaysUntilDueFilterToolBar() {
        daysUntilDueFilterToolBar.add(overdueFilterCheckBox);
        daysUntilDueFilterToolBar.add(dueTodayFilterCheckBox);
        daysUntilDueFilterToolBar.add(dueWeekFilterCheckBox);
        daysUntilDueFilterToolBar.add(dueMonthFilterCheckBox);
    }

    // MODIFIES: this, (CategoryFilterToolBar field)
    // EFFECTS: adds all the elements to CategoryFilterToolBar
    private void setupCategoryFilterToolBar() {
        categoryFilterToolBar.add(personalFilterCheckBox);
        categoryFilterToolBar.add(healthFilterCheckBox);
        categoryFilterToolBar.add(schoolFilterCheckBox);
        categoryFilterToolBar.add(workFilterCheckBox);
        categoryFilterToolBar.add(otherFilterCheckBox);
    }

    // MODIFIES: this
    // EFFECTS: adds all toolbars to this
    private void addToolBars() {
        add(filterPropertyToolBar);
        add(statusFilterToolBar);
        add(priorityFilterToolBar);
        add(daysUntilDueFilterToolBar);
        add(categoryFilterToolBar);
    }
}
