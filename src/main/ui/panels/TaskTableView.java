package ui.panels;

import ui.colour.Colour;

import javax.swing.*;

// represents the scroll pane that contains the task table
public class TaskTableView extends JScrollPane {
    public TaskTable taskTable;

    // EFFECTS: creates a new TaskTableView with some default style properties and a taskTable
    public TaskTableView(TaskTable taskTable) {
        this.taskTable = taskTable;
        getViewport().setBackground(Colour.DARK_GREY);
        setViewportView(taskTable);
    }

}
