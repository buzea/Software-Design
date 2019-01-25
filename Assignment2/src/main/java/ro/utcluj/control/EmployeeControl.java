package ro.utcluj.control;

import ro.utcluj.model.library.Library;
import ro.utcluj.view.EmployeeWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

class EmployeeControl {

	private       EmployeeWindow window;
	private final Library        library;

	public EmployeeControl() {
		library = Library.getInstance();
		window = new EmployeeWindow(library, e -> {
			if (e.getSource().equals(window.getBtnSell())) {
				try {
					JTable table = window.getTable();
					int row = table.getSelectedRow();
					String title = (String) table.getValueAt(row, 0);
					String author = (String) table.getValueAt(row, 1);
					int year = ((int) table.getValueAt(row, 3));

					int quantity = (int) window.getSpinner().getValue();

					if (!library.sell(title, author, year, quantity))
						JOptionPane.showMessageDialog(window.getFrmLibraryEmployee(),
								"An error occured while processing the command!", "Error!",
								JOptionPane.ERROR_MESSAGE);

				} catch (Exception ee) {
					JOptionPane.showMessageDialog(window.getFrmLibraryEmployee(), "Invalid Selection", "Warning",
							JOptionPane.WARNING_MESSAGE);
				}

				return;
			}
			if (e.getSource().equals(window.getBtnListAllBooks())) {
				JTable table = window.getTable();
				table.setModel(new DefaultTableModel(library.toObjectMatrix(), new String[]{"Title", "Author",
						"Genre", "Year", "Price", "Stock"}) {
					/**
					 *
					 */
					private static final long serialVersionUID = 5101341905717017745L;

					@Override
					public boolean isCellEditable(int row, int column) {
						// all cells false
						return false;
					}
				});
				window.setTable(table);

				return;
			}
			if (e.getSource().equals(window.getBtnSearchByAuthor())) {
				JTable table = window.getTable();
				String author = window.getTextField().getText();
				table.setModel(new DefaultTableModel(library.getBooksByAuthor(author), new String[]{"Title",
						"Author", "Genre", "Year", "Price", "Stock"}) {
					/**
					 *
					 */
					private static final long serialVersionUID = 5101341905717017745L;

					@Override
					public boolean isCellEditable(int row, int column) {
						// all cells false
						return false;
					}
				});
				window.setTable(table);

				return;
			}
			if (e.getSource().equals(window.getBtnSearchByTitle())) {
				JTable table = window.getTable();
				String title = window.getTextField().getText();
				table.setModel(new DefaultTableModel(library.getBooksByTitle(title), new String[]{"Title",
						"Author", "Genre", "Year", "Price", "Stock"}) {
					/**
					 *
					 */
					private static final long serialVersionUID = 5101341905717017745L;

					@Override
					public boolean isCellEditable(int row, int column) {
						// all cells false
						return false;
					}
				});
				window.setTable(table);

				return;
			}
			if (e.getSource().equals(window.getBtnSearchByYear())) {
				JTable table = window.getTable();
				try {
					int year = Integer.parseInt(window.getTextField().getText());
					table.setModel(new DefaultTableModel(library.getBooksByYear(year), new String[]{"Title",
							"Author", "Genre", "Year", "Price", "Stock"}) {
						/**
						 *
						 */
						private static final long serialVersionUID = 5101341905717017745L;

						@Override
						public boolean isCellEditable(int row, int column) {
							// all cells false
							return false;
						}
					});
					window.setTable(table);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(window.getFrmLibraryEmployee(), "Input must be integer!",
							"Year Error!", 0);
				}

				return;
			}

		});

		window.getFrmLibraryEmployee().setVisible(true);
	}

}
