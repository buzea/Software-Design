package ro.utcluj.control;

import ro.utcluj.model.library.Library;
import ro.utcluj.view.EmployeeWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

class EmployeeControl {

    private final EmployeeWindow window;
    private final Library        library;

    public EmployeeControl() {
        library = Library.getInstance();
        window = new EmployeeWindow(library);

        window.getBtnSell().addActionListener(event -> sell());
        window.getBtnListAllBooks().addActionListener(e -> listAllBooks());
        window.getBtnSearchByAuthor().addActionListener(e -> searchByAuthor());
        window.getBtnSearchByTitle().addActionListener(e -> searchByTitle());
        window.getBtnSearchByYear().addActionListener(e -> searchByYear());

        window.getFrmLibraryEmployee().setVisible(true);
    }

    private void searchByYear() {
        JTable table = window.getTable();
        try {
            int year = Integer.parseInt(window.getTextField().getText());
            table.setModel(createTableModel(library.getBooksByYear(year)));
            window.setTable(table);
        } catch (Exception e1) {
            JOptionPane.showMessageDialog(window.getFrmLibraryEmployee(), "Input must be integer!", "Year Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchByTitle() {
        JTable table = window.getTable();
        String title = window.getTextField().getText();
        table.setModel(createTableModel(library.getBooksByTitle(title)));
        window.setTable(table);
    }

    private void searchByAuthor() {
        JTable table = window.getTable();
        String author = window.getTextField().getText();
        table.setModel(createTableModel(library.getBooksByAuthor(author)));
        window.setTable(table);
    }

    private void listAllBooks() {
        JTable table = window.getTable();
        table.setModel(createTableModel(library.toObjectMatrix()));
        window.setTable(table);
    }

    private void sell() {
        try {
            JTable table = window.getTable();
            int row = table.getSelectedRow();
            String title = (String) table.getValueAt(row, 0);
            String author = (String) table.getValueAt(row, 1);
            int year = ((int) table.getValueAt(row, 3));

            int quantity = (int) window.getSpinner().getValue();

            if (!library.sell(title, author, year, quantity)) {
                JOptionPane.showMessageDialog(window.getFrmLibraryEmployee(), "An error occurred while processing the command!", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ee) {
            JOptionPane.showMessageDialog(window.getFrmLibraryEmployee(), "Invalid Selection", "Warning", JOptionPane.WARNING_MESSAGE);
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
