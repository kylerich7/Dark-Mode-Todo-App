package ui;

import model.exceptions.InvalidDateException;
import model.exceptions.TaskAlreadyExistsException;
import model.filters.*;
import model.tasks.Category;
import model.tasks.Priority;
import model.tasks.Task;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

// represents the controller that handles all operations of the app, manipulates the data accordingly,
// and updates the window
public class ToDoAppController {
    private final ToDoAppWindow window;
    private final ToDoAppModel model;


    // MODIFIES: this
    // EFFECTS: creates a new ToDoAppController with a window and a model
    public ToDoAppController() {
        window = new ToDoAppWindow();
        model = new ToDoAppModel();
        addListeners();
    }

    // MODIFIES: this: window
    // EFFECTS: adds all action listeners
    private void addListeners() {
        addMenuListeners();
        addAddTaskListeners();
        addTaskEditorListeners();
        addFilterListeners();
    }

    // MODIFIES: this: window
    // EFFECTS: adds all menu action listeners
    private void addMenuListeners() {
        window.menuBar.loadAndSaveMenu.loadButton.addActionListener(this::loadButtonClicked);
        window.menuBar.loadAndSaveMenu.saveButton.addActionListener(this::saveButtonClicked);
        window.menuBar.loadAndSaveMenu.loadButton.addActionListener(this::menuButtonClicked);
        window.menuBar.loadAndSaveMenu.saveButton.addActionListener(this::menuButtonClicked);
        window.menuBar.loadAndSaveMenu.autoSaveCheckBox.addActionListener(this::menuButtonClicked);
    }

    // MODIFIES: this: window
    // EFFECTS: adds add task action listener
    private void addAddTaskListeners() {
        window.addTaskPanel.addTaskButton.addActionListener(this::addTaskButtonClicked);
    }

    // MODIFIES: this: window
    // EFFECTS: adds all add task editor pane action listeners
    private void addTaskEditorListeners() {
        window.taskEditorPane.markCompleteButton.addActionListener(this::markCompleteButtonClicked);
        window.taskEditorPane.deleteButton.addActionListener(this::deleteButtonClicked);
        window.taskEditorPane.clearFiltersButton.addActionListener(this::clearFiltersButtonClicked);
    }

    // MODIFIES: this: window
    // EFFECTS: adds all filter action listeners
    private void addFilterListeners() {
        for (JToggleButton toggle : window.filtersToolBar.toggleButtons) {
            toggle.addItemListener(this::filterCheckBoxStateChanged);
        }
    }

    ////////////////////////////////////////// ACTION EVENT METHODS  ///////////////////////////////////////////////////

    // MODIFIES: this: window, model
    // EFFECTS: resets display text
    //          if model task list already contains task, tells user task already exists, and doesnt add to list
    //          else adds task to task list, auto saves tasks if auto save = true, refreshes table
    //          tells user task has been added, plays sounds according to action
    private void addTaskButtonClicked(ActionEvent e) {
        refreshWindowViewDisplayText();
        addTaskToTaskList();
        refreshTableView();
    }

    // MODIFIES: this: window, model
    // EFFECTS: marks the selected task in the table as complete if not already complete,
    //          resets display text, auto saves tasks if auto save = true, refreshes table
    //          if task already complete, tells user and does not mark complete
    //          plays sounds according to action
    private void markCompleteButtonClicked(ActionEvent e) {
        refreshWindowViewDisplayText();
        int selectedRowIndex = window.taskTableView.taskTable.getSelectedRow();

        if (isSelectedRowValid(selectedRowIndex)) {
            Vector<String> taskInfo = getSelectedTaskInfo(selectedRowIndex);
            String taskDescription = taskInfo.get(2);
            if (isTaskComplete(taskDescription)) {
                playSoundEffect("./src/resources/sfx/errorSound.wav");
                taskAlreadyCompletedMessage();
            } else {
                playSoundEffect("./src/resources/sfx/completeTask.wav");
                model.taskList.completeTask(taskDescription);
                taskMarkedCompleteMessage();
                autoSaveTasks();
                refreshTableView();
            }
            window.taskTableView.taskTable.clearSelection();
        }
    }

    // MODIFIES: this: window, model
    // EFFECTS: deletes task from model task list, resets display text, auto saves tasks if auto save = true,
    //          refreshes table,  plays sounds according to action
    private void deleteButtonClicked(ActionEvent e) {
        refreshWindowViewDisplayText();
        int selectedRowIndex = window.taskTableView.taskTable.getSelectedRow();

        if (isSelectedRowValid(selectedRowIndex)) {
            playSoundEffect("./src/resources/sfx/deleteTask.wav");
            Vector<String> taskInfo = getSelectedTaskInfo(selectedRowIndex);
            String description = taskInfo.get(2);
            model.taskList.deleteTask(description);
            taskDeletedMessage();
            autoSaveTasks();
            refreshTableView();
            window.taskTableView.taskTable.clearSelection();
        }
    }

    // MODIFIES: taskList.json
    // EFFECTS: writes all tasks in model task list to json file
    private void saveButtonClicked(ActionEvent e) {
        refreshWindowViewDisplayText();
        try {
            model.jsonSaver.save(model.taskList);
            tasksSavedMessage();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    // MODIFIES: this: window, model
    // EFFECTS: loads all tasks in json file to model task list
    //          and resets display text, refreshes table
    private void loadButtonClicked(ActionEvent e) {
        refreshWindowViewDisplayText();
        try {
            model.taskList = model.jsonLoader.load();
            refreshTableView();
            tasksLoadedMessage();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    // MODIFIES: this: window
    // EFFECTS: resets display text, plays sound
    private void menuButtonClicked(ActionEvent event) {
        refreshWindowViewDisplayText();
        playSoundEffect("./src/resources/sfx/filters.wav");
    }

    // MODIFIES: this: window, model MultipleFilters
    // EFFECTS: resets display text, refreshes table according to new filters, plays sound
    private void filterCheckBoxStateChanged(ItemEvent e) {
        playSoundEffect("./src/resources/sfx/filters.wav");
        refreshWindowViewDisplayText();
        refreshTableView();
    }

    // MODIFIES: this: window, model MultipleFilters
    // EFFECTS: resets display text, refreshes table, clears filters, plays sound, un-toggles all filter toggles
    private void clearFiltersButtonClicked(ActionEvent e) {
        playSoundEffect("./src/resources/sfx/removeFilters.wav");
        refreshWindowViewDisplayText();
        refreshTableView();
        for (JToggleButton toggle : window.filtersToolBar.toggleButtons) {
            toggle.setSelected(false);
        }
        resetFiltersMessage();
    }


    ////////////////////////////////////////////////  ACTION EVENT HELPERS  ////////////////////////////////////////////

    // EFFECTS: returns the vector info of the selected task
    private Vector<String> getSelectedTaskInfo(int selectedRowIndex) {
        return window.taskTableView.taskTable.getRow(selectedRowIndex);
    }


    // EFFECTS: returns true if model task assigned to description is already complete
    private boolean isTaskComplete(String description) {
        return model.taskList.getTask(description).getCompletionStatus();
    }

    // EFFECTS: returns true if row selected is a valid row
    private boolean isSelectedRowValid(int selectedRowIndex) {
        return selectedRowIndex != -1 && selectedRowIndex < window.taskTableView.taskTable.getRowCount();
    }

    // MODIFIES: this: window, model
    // EFFECTS: plays sound effect
    //          if task description already exists within model task list, tells user, resets description text field
    //          if task date is invalid, tells user, resets date text field
    //          else adds task to task list
    private void addTaskToTaskList() {
        try {
            Task newTask = getNewTask();
            model.taskList.addTask(newTask);
            playSoundEffect("./src/resources/sfx/addTask.wav");
            autoSaveTasks();
            taskAddedMessage();
            resetAddTaskArea();
        } catch (InvalidDateException dateException) {
            playSoundEffect("./src/resources/sfx/errorSound.wav");
            window.addTaskPanel.dateInputTextField.setText("");
            invalidDateMessage();
        } catch (TaskAlreadyExistsException taskAlreadyExistsException) {
            playSoundEffect("./src/resources/sfx/errorSound.wav");
            window.addTaskPanel.descriptionInputTextField.setText("");
            taskAlreadyExistsMessage();
        }
    }

    // EFFECTS: pulls all information from task text fields and dropdowns in order to create a task
    //          returns task
    private Task getNewTask() {
        String dueDate = window.addTaskPanel.dateInputTextField.getText();
        String description = window.addTaskPanel.descriptionInputTextField.getText();
        String categoryStr = window.addTaskPanel.categoryDropDown.getSelectedItem().toString().toUpperCase();
        String priorityStr = window.addTaskPanel.priorityDropDown.getSelectedItem().toString().toUpperCase();
        Category category = Category.valueOf(categoryStr);
        Priority priority = Priority.valueOf(priorityStr);
        return new Task(dueDate, description, priority, category);
    }

    // MODIFIES: this: window, model MultipleFilters
    // EFFECTS: adds all filters that are currently selected to model MultipleFilters
    //          refreshes the table based on filters (if no filters selected, all tasks are displayed)
    private void refreshTableView() {
        resetFilters();
        addActiveFilters();
        refreshTableData();
        window.taskTableView.setViewportView(window.taskTableView.taskTable);
    }

    // MODIFIES: this: window, model MultipleFilters
    // EFFECTS: refreshes the data within the task table according to the model task list
    private void refreshTableData() {
        window.taskTableView.taskTable.clearData();
        ArrayList<Task> tasks = model.taskList.filterBy(model.multipleFilter);
        Collections.sort(tasks);
        for (Task task : tasks) {
            Vector<String> taskVector = getNewTaskVector(task);
            window.taskTableView.taskTable.addRow(taskVector);
        }
    }

    // MODIFIES: this: model MultipleFilters
    // EFFECTS: clears the filters in the model MultipleFilters
    private void resetFilters() {
        model.multipleFilter.clearFilters();
    }

    // EFFECTS: returns task in the form of a vector containing all its information
    private Vector<String> getNewTaskVector(Task task) {
        Vector<String> taskVector = new Vector<>();
        taskVector.add(task.getDueDate());
        taskVector.add(String.valueOf(task.daysUntilDue()));
        taskVector.add(task.getDescription());
        taskVector.add(task.getCategory().toString());
        taskVector.add(task.getPriority().toString());
        taskVector.add(String.valueOf(task.getCompletionStatus()));
        return taskVector;
    }

    // MODIFIES: taskList.json
    // EFFECTS: writes all tasks to json file if auto save is selected
    private void autoSaveTasks() {
        if (window.menuBar.loadAndSaveMenu.autoSaveCheckBox.isSelected()) {
            try {
                model.jsonSaver.save(model.taskList);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }
    }

    // MODIFIES: this: window
    // EFFECTS: resets the add task fields to default values
    private void resetAddTaskArea() {
        window.addTaskPanel.dateInputTextField.setText("");
        window.addTaskPanel.descriptionInputTextField.setText("");
        window.addTaskPanel.priorityDropDown.setSelectedItem("Low");
        window.addTaskPanel.categoryDropDown.setSelectedItem("Personal");
    }

    // EFFECTS: plays sound effect from soundFilePath
    private void playSoundEffect(String soundFilePath) {
        try {
            File soundFile = new File(soundFilePath);
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(soundFile));
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /////////////////////////////////////////////// MESSAGE METHODS  ///////////////////////////////////////////////////

    // MODIFIES: this: window
    // EFFECTS: resets the displayText
    private void refreshWindowViewDisplayText() {
        setDisplayText("");
    }

    // MODIFIES: this: window
    // EFFECTS: updates the displayText color to red if error message, green otherwise
    private void updateDisplayTextColor(boolean isErrorMessage) {
        if (isErrorMessage) {
            window.addTaskPanel.displayText.setErrorColour();
        } else {
            window.addTaskPanel.displayText.setGreenColour();
        }
    }

    // MODIFIES: this: window
    // EFFECTS: sets the displayText to tell user invalid date has been inputted
    private void invalidDateMessage() {
        updateDisplayTextColor(true);
        setDisplayText("Invalid Date! Please enter date in form YYYY-MM-DD");
    }

    // MODIFIES: this: window
    // EFFECTS: sets the displayText to tell user invalid description has been inputted
    private void taskAlreadyExistsMessage() {
        updateDisplayTextColor(true);
        setDisplayText("That task already exists! Please enter a different task description.");
    }

    // MODIFIES: this: window
    // EFFECTS: sets the displayText to tell user filters have been reset
    private void resetFiltersMessage() {
        updateDisplayTextColor(false);
        setDisplayText("Filters cleared!");
    }

    // MODIFIES: this: window
    // EFFECTS: sets the displayText to tell user task is already completed
    private void taskAlreadyCompletedMessage() {
        updateDisplayTextColor(true);
        setDisplayText("That task is already complete!");
    }

    // MODIFIES: this: window
    // EFFECTS: sets the displayText to tell user task has been marked complete
    private void taskMarkedCompleteMessage() {
        updateDisplayTextColor(false);
        setDisplayText("Task marked complete!");
    }

    // MODIFIES: this: window
    // EFFECTS: sets the displayText to tell user task has been deleted
    private void taskDeletedMessage() {
        updateDisplayTextColor(false);
        setDisplayText("Task deleted!");
    }

    // MODIFIES: this: window
    // EFFECTS: sets the displayText to tell user task has been added
    private void taskAddedMessage() {
        updateDisplayTextColor(false);
        setDisplayText("Task added!");
    }

    // MODIFIES: this: window
    // EFFECTS: sets the displayText to tell user tasks have been saved
    private void tasksSavedMessage() {
        updateDisplayTextColor(false);
        setDisplayText("Tasks Saved!");
    }

    // MODIFIES: this: window
    // EFFECTS: sets the displayText to tell user tasks have been loaded
    private void tasksLoadedMessage() {
        updateDisplayTextColor(false);
        setDisplayText("Tasks Loaded!");
    }

    // MODIFIES: this: window
    // EFFECTS: sets the displayText to tell user message
    private void setDisplayText(String message) {
        window.addTaskPanel.displayText.setText(message);
    }

    //////////////////////////////////////////// ADD FILTER METHODS  ///////////////////////////////////////////////////

    // MODIFIES: this: model MultipleFilters
    // EFFECTS: adds all the filters that are currently selected to multipleFilter
    //          if none selected, adds TrueFilter
    private void addActiveFilters() {
        if (isFiltersActiveSelected()) {
            model.multipleFilter.setSatisfiesAll(isSatisfiesAllSelected());
            addStatusFilters();
            addPriorityFilters();
            addDateFilters();
            addCategoryFilters();
        }
        if (model.multipleFilter.getFilters().isEmpty()) {
            model.multipleFilter.addFilter(new TrueFilter());
        }
    }

    // EFFECTS: returns true if stack filters check box is selected, else false
    private boolean isSatisfiesAllSelected() {
        return window.filtersToolBar.stackFilterCheckBox.isSelected();
    }

    // EFFECTS: returns true if activate filters check box is selected, else false
    private boolean isFiltersActiveSelected() {
        return window.filtersToolBar.activateFilterCheckBox.isSelected();
    }

    // MODIFIES: this: model MultipleFilters
    // EFFECTS: adds all the category filters that are currently selected to model multipleFilter
    //          if none selected, does nothing
    private void addCategoryFilters() {
        if (window.filtersToolBar.personalFilterCheckBox.isSelected()) {
            model.multipleFilter.addFilter(new CategoryFilter(Category.PERSONAL));
        }
        if (window.filtersToolBar.healthFilterCheckBox.isSelected()) {
            model.multipleFilter.addFilter(new CategoryFilter(Category.HEALTH));
        }
        if (window.filtersToolBar.schoolFilterCheckBox.isSelected()) {
            model.multipleFilter.addFilter(new CategoryFilter(Category.SCHOOL));
        }
        if (window.filtersToolBar.workFilterCheckBox.isSelected()) {
            model.multipleFilter.addFilter(new CategoryFilter(Category.WORK));
        }
        if (window.filtersToolBar.otherFilterCheckBox.isSelected()) {
            model.multipleFilter.addFilter(new CategoryFilter(Category.OTHER));
        }
    }

    // MODIFIES: this: model MultipleFilters
    // EFFECTS: adds all the date filters that are currently selected to model multipleFilter
    //          if none selected, does nothing
    private void addDateFilters() {
        if (window.filtersToolBar.overdueFilterCheckBox.isSelected()) {
            model.multipleFilter.addFilter(new OverdueFilter());
        }
        if (window.filtersToolBar.dueTodayFilterCheckBox.isSelected()) {
            model.multipleFilter.addFilter(new DueWithinDaysFilter(0));
        }
        if (window.filtersToolBar.dueWeekFilterCheckBox.isSelected()) {
            model.multipleFilter.addFilter(new DueWithinDaysFilter(7));
        }
        if (window.filtersToolBar.dueMonthFilterCheckBox.isSelected()) {
            model.multipleFilter.addFilter(new DueWithinDaysFilter(31));
        }
    }

    // MODIFIES: this: model MultipleFilters
    // EFFECTS: adds all the priority filters that are currently selected to model multipleFilter
    //          if none selected, does nothing
    private void addPriorityFilters() {
        if (window.filtersToolBar.highPriorityFilterCheckBox.isSelected()) {
            model.multipleFilter.addFilter(new PriorityFilter(Priority.HIGH));
        }
        if (window.filtersToolBar.mediumPriorityFilterCheckBox.isSelected()) {
            model.multipleFilter.addFilter(new PriorityFilter(Priority.MEDIUM));
        }
        if (window.filtersToolBar.lowPriorityFilterCheckBox.isSelected()) {
            model.multipleFilter.addFilter(new PriorityFilter(Priority.LOW));
        }
    }

    // MODIFIES: this: model MultipleFilters
    // EFFECTS: adds all the status filters that are currently selected to model multipleFilter
    //          if none selected, does nothing
    private void addStatusFilters() {
        if (window.filtersToolBar.currentFilterCheckBox.isSelected()) {
            model.multipleFilter.addFilter(new StatusFilter(false));
        }
        if (window.filtersToolBar.completeFilterCheckBox.isSelected()) {
            model.multipleFilter.addFilter(new StatusFilter(true));
        }
    }
}