package ro.utcluj.control;

import ro.utcluj.model.library.Library;
import ro.utcluj.view.EmployeeWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

class EmployeeControl {

    private final EmployeeWindow view;
    private final Library        library;

    public EmployeeControl() {
        library = Library.getInstance();
        view = new EmployeeWindow();

        view.update(library, null); // set initial data
        library.addObserver(view);

        bindButtons();
        view.getFrmLibraryEmployee().setVisible(true);
    }

    private void bindButtons() {
        view.getBtnSell().addActionListener(event -> sell());
        view.getBtnListAllBooks().addActionListener(e -> listAllBooks());
        view.getBtnSearchByAuthor().addActionListener(e -> searchByAuthor());
        view.getBtnSearchByTitle().addActionListener(e -> searchByTitle());
        view.getBtnSearchByYear().addActionListener(e -> searchByYear());
    }

    private void searchByYear() {
        JTable table = view.getTable();
        try {
            int year = Integer.parseInt(view.getTextField().getText());
            table.setModel(createTableModel(library.getBooksByYear(year)));
            view.setTable(table);
        } catch (Exception e1) {
            JOptionPane.showMessageDialog(view.getFrmLibraryEmployee(), "Input must be integer!", "Year Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchByTitle() {
        JTable table = view.getTable();
        String title = view.getTextField().getText();
        table.setModel(createTableModel(library.getBooksByTitle(title)));
        view.setTable(table);
    }

    private void searchByAuthor() {
        JTable table = view.getTable();
        String author = view.getTextField().getText();
        table.setModel(createTableModel(library.getBooksByAuthor(author)));
        view.setTable(table);
    }

    private void listAllBooks() {
        JTable table = view.getTable();
        table.setModel(createTableModel(library.toObjectMatrix()));
        view.setTable(table);
    }

    private void sell() {
        try {
            JTable table = view.getTable();
            int row = table.getSelectedRow();
            String title = (String) table.getValueAt(row, 0);
            String author = (String) table.getValueAt(row, 1);
            int year = ((int) table.getValueAt(row, 3));

            int quantity = (int) view.getSpinner().getValue();

            if (!library.sell(title, author, year, quantity)) {
                JOptionPane.showMessageDialog(view.getFrmLibraryEmployee(), "An error occurred while processing the command!", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ee) {
            JOptionPane.showMessageDialog(view.getFrmLibraryEmployee(), "Invalid Selection", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private DefaultTableModel createTableModel(final Object[][] data) {
        return new DefaultTableModel(data, EmployeeWindow.COLUMN_NAMES) {

            @Override
            public boolean isCellEditable(int row, int column) {
                // all cells false
                return false;
            }
        };
    }
}
