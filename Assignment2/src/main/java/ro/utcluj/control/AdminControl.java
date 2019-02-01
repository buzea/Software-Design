package ro.utcluj.control;

import ro.utcluj.model.library.Book;
import ro.utcluj.model.library.Library;
import ro.utcluj.model.users.Admin;
import ro.utcluj.view.AdminWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.LinkedList;
import java.util.List;

class AdminControl {
    private final AdminWindow window;
    private final Library     library;
    private final Admin       admin;

    public AdminControl(Admin mAdmin) {
        this.admin = mAdmin;
        library = Library.getInstance();
        window = new AdminWindow(library);

        //user
        window.getBtnCreateUser().addActionListener(e -> createUser());
        window.getBtnDeleteUser().addActionListener(e -> deleteUser());
        window.getBtnUpdatePassword().addActionListener(e -> updatePassword());
        window.getBtnShowUsers().addActionListener(e -> showUsers());

        // book
        window.getBtnInsertBook().addActionListener(e -> insertBook());
        window.getBtnDeleteBook().addActionListener(e -> deleteBook());
        window.getBtnUpdateBooks().addActionListener(e -> updateBooks());
        window.getBtnReloadTable().addActionListener(e -> window.update(library, null));

        // report
        window.getBtnGenerateReport().addActionListener(e -> generateReport());
        window.getBtnOpenReport().addActionListener(e -> openReport());
    }

    private void openReport() {
        int type = window.getReportTypeComboBox().getSelectedIndex();
        if (!admin.openReport(type)) {
            JOptionPane.showMessageDialog(window.getFrmLibraryAdmin(), "Cannot open Report!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateReport() {
        int type = window.getReportTypeComboBox().getSelectedIndex();
        if (admin.generateReport(type)) {
            JOptionPane.showMessageDialog(window.getFrmLibraryAdmin(), "Report Generated");
        } else {
            JOptionPane.showMessageDialog(window.getFrmLibraryAdmin(), "Report NOT Generated", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateBooks() {
        List<Book> books = new LinkedList<>();
        JTable table = window.getTable();

        table.clearSelection();
        int size = table.getRowCount();
        for (int row = 0; row < size; row++) {
            String title = (String) table.getValueAt(row, 0);
            String author = (String) table.getValueAt(row, 1);
            String genre = (String) table.getValueAt(row, 2);
            int year, stock;
            double price;
            if (table.getValueAt(row, 3) instanceof Integer) {
                year = (int) table.getValueAt(row, 3);
            } else {
                year = Integer.parseInt((String) table.getValueAt(row, 3));
            }

            if (table.getValueAt(row, 5) instanceof Integer) {
                stock = ((Integer) table.getValueAt(row, 5));
            } else {
                stock = Integer.parseInt((String) table.getValueAt(row, 5));
            }

            if (table.getValueAt(row, 4) instanceof Double) {
                price = ((Double) table.getValueAt(row, 4));
            } else {
                price = Double.parseDouble((String) table.getValueAt(row, 4));
            }

            if (title.equalsIgnoreCase("Tile") || author.equalsIgnoreCase("Author")
                    || genre.equalsIgnoreCase("Genre")) {
                JOptionPane.showMessageDialog(window.getFrmLibraryAdmin(),
                                              "Please Update Generic Fields In The Table!", "Row not Updated", JOptionPane.ERROR_MESSAGE);
            } else {
                Book temp = new Book(title, author, genre, year, stock, price);
                books.add(temp);
            }
        }
        library.setBooks(books);
    }

    private void deleteBook() {
        JTable table = window.getTable();
        int row = table.getSelectedRow();
        String title = (String) table.getValueAt(row, 0);
        String author = (String) table.getValueAt(row, 1);
        int year = (int) table.getValueAt(row, 3);
        Book b = library.findBook(title, author, year);
        library.removeBook(b);
    }

    private void insertBook() {
        JTable table = window.getTable();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Object[]{"Title", "Author", "Genre", 2000, 0.0, 0});
        JOptionPane.showMessageDialog(window.getFrmLibraryAdmin(),
                                      "Please fill in the data in the blank row and then select \"Update Books\"");
    }

    private void showUsers() {
        JTable table = window.getUserTable();
        table.setModel(new DefaultTableModel(admin.getUserMatrix(), new String[]{"Username",
                "Password", "Type"}));
    }

    private void updatePassword() {
        String username = window.getUsernameTextField().getText();
        String password = window.getPasswordTextField().getText();
        if (admin.updatePassword(username, password))
            JOptionPane.showMessageDialog(window.getFrmLibraryAdmin(), "Password Updated");
        else
            JOptionPane.showMessageDialog(window.getFrmLibraryAdmin(), "Password Could NOT Be Updated!", "ERROR", JOptionPane.ERROR_MESSAGE);
        // LOL This is a horrible solution
        window.getBtnShowUsers().doClick();
    }

    private void deleteUser() {
        String username = window.getUsernameTextField().getText();
        String password = window.getPasswordTextField().getText();
        if (admin.deleteUser(username, password))
            JOptionPane.showMessageDialog(window.getFrmLibraryAdmin(), "User Deleted");
        else
            JOptionPane.showMessageDialog(window.getFrmLibraryAdmin(), "User Could NOT Be Deleted!", "ERROR", JOptionPane.ERROR_MESSAGE);
        // LOL This is a horrible solution
        window.getBtnShowUsers().doClick();
    }

    private void createUser() {
        String username = window.getUsernameTextField().getText();
        String password = window.getPasswordTextField().getText();
        int type = window.getUserTypeComboBox().getSelectedIndex();
        if (admin.createUser(username, password, type))
            JOptionPane.showMessageDialog(window.getFrmLibraryAdmin(), "User Created");
        else
            JOptionPane.showMessageDialog(window.getFrmLibraryAdmin(), "User NOT Created", "ERROR", JOptionPane.ERROR_MESSAGE);
        // LOL This is a horrible solution
        window.getBtnShowUsers().doClick();
    }
}
