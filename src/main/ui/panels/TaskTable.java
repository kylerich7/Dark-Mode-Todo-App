package ui.panels;

import ui.colour.Colour;
import ui.colour.ColourSetter;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.Vector;

// represents a table for all tasks
public class TaskTable extends JTable {
    private final Vector<Vector<String>> rows;

    // MODIFIES: this
    // EFFECTS: creates a new TaskTable with some default properties and rows
    public TaskTable(Vector<Vector<String>> rows) {
        super(rows, getCols());
        this.rows = rows;
        setupTaskTable();
    }

    // MODIFIES: this (rows)
    // EFFECTS: clears rows
    public void clearData() {
        rows.clear();
    }

    // MODIFIES: this (rows)
    // EFFECTS: adds a row to rows
    public void addRow(Vector<String> newRow) {
        rows.add(newRow);
    }

    // MODIFIES: this (rows)
    // EFFECTS: returns row at index from rows
    public Vector<String> getRow(int index) {
        return rows.get(index);
    }

    // MODIFIES: this
    // EFFECTS: sets columns to the respective task information titles
    private static Vector<String> getCols() {
        Vector<String> cols = new Vector<>();
        cols.add("Date");
        cols.add("Days Until Due");
        cols.add("Description");
        cols.add("Category");
        cols.add("Priority");
        cols.add("Completion");
        return cols;
    }

    // MODIFIES: this
    // EFFECTS: sets style properties for this
    private void setupTaskTable() {
        setOpaque(false);
        setFocusable(false);
        ColourSetter.setColours(this, Colour.DARK_GREY, Colour.DARK_RED);
        setGridColor(new Color(60, 60, 60));
        setSelectionBackground(Colour.LIGHT_GREY);
        setSelectionForeground(Colour.WHITE);
        setFont(getFont().deriveFont(getFont().getSize() + 4f));
        setRowHeight(25);
        setFocusable(false);
        setupTableHeader();
        setupTableColumns();
    }

    // MODIFIES: this
    // EFFECTS: sets style properties for columns
    private void setupTableColumns() {
        getColumnModel().getColumn(0).setPreferredWidth(90);
        getColumnModel().getColumn(1).setPreferredWidth(110);
        getColumnModel().getColumn(2).setPreferredWidth(560);
        getColumnModel().getColumn(3).setPreferredWidth(90);
        getColumnModel().getColumn(4).setPreferredWidth(70);
        getColumnModel().getColumn(5).setPreferredWidth(90);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < 6; i++) {
            if (i != 2) {
                getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: sets style properties for header
    private void setupTableHeader() {
        JTableHeader header = getTableHeader();
        header.setFont(header.getFont().deriveFont(header.getFont().getSize() + 3f));
        header.setFont(header.getFont().deriveFont(header.getFont().getStyle() | Font.BOLD));
        ColourSetter.setColours(header, Colour.DARK_RED, Colour.WHITE);
    }

    @Override
    // EFFECTS: removes ability to edit cells (always returns false for isCellEditable)
    public boolean isCellEditable(int row, int column) {
        return false;
    }

}
