package view;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import model.library.Library;


public class EmployeeWindow implements Observer {

	private JFrame frmLibraryEmployee;
	private JTable table;
	private JTextField textField;
	private JButton btnListAllBooks;
	private JButton btnSearchByTitle;
	private JButton btnSearchByAuthor;
	private JButton btnSearchByYear;
	private JButton btnSell;
	private Library library;
	private ActionListener actionListener;
	private static final boolean isDebug = false;
	private JSpinner spinner;



	/**
	 * Create the application.
	 */

	public EmployeeWindow(Library library, ActionListener listener) {
		this.library = library;
		this.actionListener = listener;
		this.library.addObserver(this);
		initialize();
		frmLibraryEmployee.setVisible(true);

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLibraryEmployee = new JFrame();
		frmLibraryEmployee.setIconImage(Toolkit.getDefaultToolkit().getImage(
				EmployeeWindow.class.getResource("/javax/swing/plaf/metal/icons/ocean/computer.gif")));
		frmLibraryEmployee.setTitle("Library Employee");
		frmLibraryEmployee.setBounds(100, 100, 600, 350);
		frmLibraryEmployee.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JScrollPane scrollPane = new JScrollPane();

		JPanel commandPanel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(frmLibraryEmployee.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(
				Alignment.LEADING,
				groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(commandPanel, GroupLayout.PREFERRED_SIZE, 191, GroupLayout.PREFERRED_SIZE)
						.addGap(11)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(commandPanel, GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
										.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
						.addContainerGap()));
		GridBagLayout gbl_commandPanel = new GridBagLayout();
		gbl_commandPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_commandPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_commandPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_commandPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		commandPanel.setLayout(gbl_commandPanel);

		btnListAllBooks = new JButton("List All Books");
		btnListAllBooks.addActionListener(actionListener);
		GridBagConstraints gbc_btnListAllBooks = new GridBagConstraints();
		gbc_btnListAllBooks.insets = new Insets(0, 0, 5, 0);
		gbc_btnListAllBooks.gridx = 1;
		gbc_btnListAllBooks.gridy = 0;
		commandPanel.add(btnListAllBooks, gbc_btnListAllBooks);

		JLabel lblInput = new JLabel("Input:");
		lblInput.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblInput = new GridBagConstraints();
		gbc_lblInput.insets = new Insets(0, 0, 5, 5);
		gbc_lblInput.anchor = GridBagConstraints.EAST;
		gbc_lblInput.gridx = 0;
		gbc_lblInput.gridy = 1;
		commandPanel.add(lblInput, gbc_lblInput);

		textField = new JTextField();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 0, 5, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 1;
		commandPanel.add(textField, gbc);
		textField.setColumns(10);

		btnSearchByTitle = new JButton("Search By Title");
		btnSearchByTitle.addActionListener(actionListener);
		GridBagConstraints gbc_btnSearchByTitle = new GridBagConstraints();
		gbc_btnSearchByTitle.insets = new Insets(0, 0, 5, 0);
		gbc_btnSearchByTitle.gridx = 1;
		gbc_btnSearchByTitle.gridy = 2;
		commandPanel.add(btnSearchByTitle, gbc_btnSearchByTitle);

		btnSearchByAuthor = new JButton("Search By Author");
		btnSearchByAuthor.addActionListener(actionListener);
		GridBagConstraints gbc_btnSearchByAuthor = new GridBagConstraints();
		gbc_btnSearchByAuthor.insets = new Insets(0, 0, 5, 0);
		gbc_btnSearchByAuthor.gridx = 1;
		gbc_btnSearchByAuthor.gridy = 3;
		commandPanel.add(btnSearchByAuthor, gbc_btnSearchByAuthor);

		btnSearchByYear = new JButton("Search By Year");
		btnSearchByYear.addActionListener(actionListener);
		GridBagConstraints gbc_btnSearchByYear = new GridBagConstraints();
		gbc_btnSearchByYear.insets = new Insets(0, 0, 5, 0);
		gbc_btnSearchByYear.gridx = 1;
		gbc_btnSearchByYear.gridy = 4;
		commandPanel.add(btnSearchByYear, gbc_btnSearchByYear);

		JLabel lblQuantity = new JLabel("Quantity:");
		lblQuantity.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblQuantity = new GridBagConstraints();
		gbc_lblQuantity.insets = new Insets(0, 0, 5, 5);
		gbc_lblQuantity.gridx = 0;
		gbc_lblQuantity.gridy = 5;
		commandPanel.add(lblQuantity, gbc_lblQuantity);

		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner.insets = new Insets(0, 0, 5, 0);
		gbc_spinner.gridx = 1;
		gbc_spinner.gridy = 5;
		commandPanel.add(spinner, gbc_spinner);

		btnSell = new JButton("Sell");
		btnSell.addActionListener(actionListener);
		GridBagConstraints gbc_btnSell = new GridBagConstraints();
		gbc_btnSell.gridx = 1;
		gbc_btnSell.gridy = 6;
		commandPanel.add(btnSell, gbc_btnSell);

		table = new JTable();
		if (isDebug) {
			table.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null, null, null },
					{ null, null, null, null, null, null }, { null, null, null, null, null, null },
					{ null, null, null, null, null, null }, { null, null, null, null, null, null },
					{ null, null, null, null, null, null }, }, new String[] { "Title", "Author", "Genre", "Year",
					"Price", "Stock" }));
		} else {
			table.setModel(new DefaultTableModel(library.toObjectMatrix(), new String[] { "Title", "Author", "Genre",
				"Year", "Price", "Stock" }) {
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

		}

		table.getColumnModel().getColumn(0).setPreferredWidth(80);
		table.getColumnModel().getColumn(1).setPreferredWidth(70);
		table.getColumnModel().getColumn(2).setPreferredWidth(70);
		table.getColumnModel().getColumn(3).setPreferredWidth(45);
		table.getColumnModel().getColumn(4).setPreferredWidth(45);
		table.getColumnModel().getColumn(5).setPreferredWidth(45);
		scrollPane.setViewportView(table);
		frmLibraryEmployee.getContentPane().setLayout(groupLayout);

		JMenuBar menuBar = new JMenuBar();
		frmLibraryEmployee.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		mnFile.setMnemonic('F');
		menuBar.add(mnFile);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.ALT_MASK));
		mnFile.add(mntmExit);

	}

	@Override
	public void update(Observable o, Object arg) {
		table.setModel(new DefaultTableModel(library.toObjectMatrix(), new String[] { "Title", "Author", "Genre",
				"Year", "Price", "Stock" }) {
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
		table.getColumnModel().getColumn(0).setPreferredWidth(80);
		table.getColumnModel().getColumn(1).setPreferredWidth(70);
		table.getColumnModel().getColumn(2).setPreferredWidth(70);
		table.getColumnModel().getColumn(3).setPreferredWidth(45);
		table.getColumnModel().getColumn(4).setPreferredWidth(45);
		table.getColumnModel().getColumn(5).setPreferredWidth(45);

	}

	public JFrame getFrmLibraryEmployee() {
		return frmLibraryEmployee;
	}

	public void setFrmLibraryEmployee(JFrame frmLibraryEmployee) {
		this.frmLibraryEmployee = frmLibraryEmployee;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable mTable) {
		this.table = mTable;		
		table.getColumnModel().getColumn(0).setPreferredWidth(80);
		table.getColumnModel().getColumn(1).setPreferredWidth(70);
		table.getColumnModel().getColumn(2).setPreferredWidth(70);
		table.getColumnModel().getColumn(3).setPreferredWidth(45);
		table.getColumnModel().getColumn(4).setPreferredWidth(45);
		table.getColumnModel().getColumn(5).setPreferredWidth(45);
	}

	public JTextField getTextField() {
		return textField;
	}

	public void setTextField(JTextField textField) {
		this.textField = textField;
	}

	public JButton getBtnListAllBooks() {
		return btnListAllBooks;
	}

	public void setBtnListAllBooks(JButton btnListAllBooks) {
		this.btnListAllBooks = btnListAllBooks;
	}

	public JButton getBtnSearchByTitle() {
		return btnSearchByTitle;
	}

	public void setBtnSearchByTitle(JButton btnSearchByTitle) {
		this.btnSearchByTitle = btnSearchByTitle;
	}

	public JButton getBtnSearchByAuthor() {
		return btnSearchByAuthor;
	}

	public void setBtnSearchByAuthor(JButton btnSearchByAuthor) {
		this.btnSearchByAuthor = btnSearchByAuthor;
	}

	public JButton getBtnSearchByYear() {
		return btnSearchByYear;
	}

	public void setBtnSearchByYear(JButton btnSearchByYear) {
		this.btnSearchByYear = btnSearchByYear;
	}

	public JButton getBtnSell() {
		return btnSell;
	}

	public void setBtnSell(JButton btnSell) {
		this.btnSell = btnSell;
	}

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}

	public ActionListener getActionListener() {
		return actionListener;
	}

	public void setActionListener(ActionListener actionListener) {
		this.actionListener = actionListener;
	}

	public JSpinner getSpinner() {
		return spinner;
	}

	public void setSpinner(JSpinner spinner) {
		this.spinner = spinner;
	}

}
