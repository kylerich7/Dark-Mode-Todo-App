package ui;

import ui.panels.*;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

// represents the window of which all panels and elements are contained within
public class ToDoAppWindow extends JFrame {
    public LoadSaveMenuBar menuBar;
    public AddTaskPanel addTaskPanel;
    public TaskTableView taskTableView;
    public EditPanel taskEditorPane;
    public FiltersToolBar filtersToolBar;

    private final Dimension size = new Dimension(1165, 755);

    // MODIFIES: this
    // EFFECTS: creates a new ToDoAppWindow with some default style properties and elements
    public ToDoAppWindow() {
        initSwingComponents();
        setupWindowStyle();
        addElementsToWindow();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes all fields
    private void initSwingComponents() {
        initMenuBar();
        initAddTaskPanel();
        initTaskTable();
        initTaskEditorPane();
        initFilterToolBar();
    }

    // MODIFIES: this
    // EFFECTS: initializes menu bar
    private void initMenuBar() {
        menuBar = new LoadSaveMenuBar();
        setJMenuBar(menuBar);
    }

    // MODIFIES: this
    // EFFECTS: initializes add task panel
    private void initAddTaskPanel() {
        addTaskPanel = new AddTaskPanel();
    }

    // MODIFIES: this
    // EFFECTS: initializes task table view and task table
    private void initTaskTable() {
        taskTableView = new TaskTableView(new TaskTable(new Vector<>()));
    }

    // MODIFIES: this
    // EFFECTS: initializes task editor pane
    private void initTaskEditorPane() {
        taskEditorPane = new EditPanel();
    }

    // MODIFIES: this
    // EFFECTS: initializes filter tool bar
    private void initFilterToolBar() {
        filtersToolBar = new FiltersToolBar();
    }

    // MODIFIES: this
    // EFFECTS: sets some default style properties of this
    private void setupWindowStyle() {
        setPreferredSize(size);
        setMinimumSize(size);
        setResizable(false);
        setIconImage(new ImageIcon("./src/resources/icon.png").getImage());
        addElementsToWindow();
        setLocationRelativeTo(getOwner());
    }

    // MODIFIES: this
    // EFFECTS: adds panel elements to this
    private void addElementsToWindow() {
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(addTaskPanel, BorderLayout.NORTH);
        contentPane.add(taskTableView, BorderLayout.CENTER);
        contentPane.add(taskEditorPane, BorderLayout.SOUTH);
        contentPane.add(filtersToolBar, BorderLayout.WEST);
    }
}
