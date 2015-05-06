package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.library.Book;
import model.library.Library;
import model.users.Admin;
import view.AdminWindow;

public class AdminControl {
	private AdminWindow window;
	private Library library;
	private Admin admin;

	public AdminControl(Admin mAdmin) {
		this.admin = mAdmin;
		library = Library.getInstance();
		window = new AdminWindow(library, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// user
				if (e.getSource().equals(window.getBtnCreateUser())) {
					String username = window.getUsernameTextField().getText();
					String password = window.getPasswordTextField().getText();
					int type = window.getUserTypeComboBox().getSelectedIndex();
					if(admin.createUser(username, password, type))
						JOptionPane.showMessageDialog(window.getFrmLibraryAdmin(), "User Created");
					else
						JOptionPane.showMessageDialog(window.getFrmLibraryAdmin(), "User NOT Created","ERROR",0);
					window.getBtnShowPassword().doClick();
					return;
				}
				if (e.getSource().equals(window.getBtnDeleteUser())) {
					String username = window.getUsernameTextField().getText();
					String password = window.getPasswordTextField().getText();
					if(admin.deleteUser(username, password))
						JOptionPane.showMessageDialog(window.getFrmLibraryAdmin(), "User Deleted");
					else
						JOptionPane.showMessageDialog(window.getFrmLibraryAdmin(), "User Could NOT Be Deleted!","ERROR",0);
					window.getBtnShowPassword().doClick();
					return;
				}
				if (e.getSource().equals(window.getBtnUpdatePassword())) {
					String username = window.getUsernameTextField().getText();
					String password = window.getPasswordTextField().getText();
					if(admin.updatePassword(username, password))
						JOptionPane.showMessageDialog(window.getFrmLibraryAdmin(), "Password Updated");
					else
						JOptionPane.showMessageDialog(window.getFrmLibraryAdmin(), "Password Could NOT Be Updated!","ERROR",0);
					window.getBtnShowPassword().doClick();
					return;
				}
				if (e.getSource().equals(window.getBtnShowPassword())) {
					JTable table = window.getUserTable();
					table.setModel(new DefaultTableModel(admin.getUserMatrix(), new String[] { "Username",
							"Password", "Type" }));
					return;
				}
				// book
				if (e.getSource().equals(window.getBtnInsertBook())) {
					JTable table = window.getTable();
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					model.addRow(new Object[] { "Title", "Author", "Genre", (int) 2000, (double) 0.0, (int) 0 });
					JOptionPane.showMessageDialog(window.getFrmLibraryAdmin(),
							"Please fill in the data in the blank row and then select \"Update Books\"");
					return;
				}
				if (e.getSource().equals(window.getBtnDeleteBook())) {
					JTable table = window.getTable();
					int row = table.getSelectedRow();
					String title = (String) table.getValueAt(row, 0);
					String author = (String) table.getValueAt(row, 1);
					int year = (int) table.getValueAt(row, 3);
					Book b = library.findBook(title, author, year);
					library.removeBook(b);
					return;
				}
				if (e.getSource().equals(window.getBtnUpdateBooks())) {
					List<Book> books = new LinkedList<Book>();
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
									"Please Update Generic Fields In The Table!", "Row not Updated", 0);
						} else {
							Book temp = new Book(title, author, genre, year, stock, price);
							books.add(temp);
						}
					}
					library.setBooks(books);

					return;
				}
				if (e.getSource().equals(window.getBtnReloadTable())) {
					window.update(library, null);
					return;
				}

				// report
				if (e.getSource().equals(window.getBtnGenerateReport())) {
					int type = window.getReportTypeComboBox().getSelectedIndex();
					if (admin.generateReport(type)) {
						JOptionPane.showMessageDialog(window.getFrmLibraryAdmin(), "Report Generated");
					} else {
						JOptionPane.showMessageDialog(window.getFrmLibraryAdmin(), "Report NOT Generated", "Error", 0);
					}
					return;
				}
				if (e.getSource().equals(window.getBtnOpenReport())) {
					int type = window.getReportTypeComboBox().getSelectedIndex();
					if (!admin.openReport(type)) {
						JOptionPane.showMessageDialog(window.getFrmLibraryAdmin(), "Cannot open Report!", "Error", 0);
					}
					return;
				}

			}
		});

	}

}
