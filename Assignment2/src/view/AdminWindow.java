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

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import model.library.Library;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;





public class AdminWindow implements Observer {

	private JFrame frmLibraryAdmin;

	private JTabbedPane tabbedPane;
	private JTextField passwordTextField;
	private JTextField usernameTextField;
	private JTable table;
	private JButton btnInsertBook;
	private JButton btnUpdateBooks;
	private JButton btnDeleteBook;
	private JButton btnReloadTable;
	private JButton btnCreateUser;
	private JButton btnUpdatePassword;
	private JButton btnDeleteUser;
	private JButton btnShowPassword;
	private JButton btnGenerateReport;
	private JButton btnOpenReport;
	private Library library;
	private ActionListener actionListener;
	private JComboBox<String> reportTypeComboBox;

	private static final boolean isDebug = false;
	private JTable userTable;
	private JComboBox<String> userTypeComboBox;


	/**
	 * Create the application.
	 */

	public AdminWindow(Library libray, ActionListener listener) {
		this.library = libray;
		this.actionListener = listener;
		library.addObserver(this);
		initialize();
		frmLibraryAdmin.setVisible(true);

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLibraryAdmin = new JFrame();
		frmLibraryAdmin.setIconImage(Toolkit.getDefaultToolkit().getImage(
				AdminWindow.class.getResource("/javax/swing/plaf/metal/icons/ocean/computer.gif")));
		frmLibraryAdmin.setTitle("Library Admin");
		frmLibraryAdmin.setBounds(100, 100, 600, 350);
		frmLibraryAdmin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frmLibraryAdmin.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.ALT_MASK));
		mnFile.add(mntmExit);
		tabbedPane = new JTabbedPane();
		frmLibraryAdmin.setContentPane(tabbedPane);
		JPanel bookPanel = new JPanel();
		JPanel userPanel = new JPanel();
		JPanel reportPanel = new JPanel();
		tabbedPane.addTab("Book", bookPanel);

		JScrollPane scrollPane = new JScrollPane();

		JPanel panel = new JPanel();
		GroupLayout gl_bookPanel = new GroupLayout(bookPanel);
		gl_bookPanel.setHorizontalGroup(gl_bookPanel.createParallelGroup(Alignment.TRAILING).addGroup(
				gl_bookPanel.createSequentialGroup().addContainerGap()
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE).addGap(8)));
		gl_bookPanel.setVerticalGroup(gl_bookPanel.createParallelGroup(Alignment.LEADING).addGroup(
				gl_bookPanel
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_bookPanel
										.createParallelGroup(Alignment.LEADING)
										.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
										.addComponent(panel, GroupLayout.PREFERRED_SIZE, 186,
												GroupLayout.PREFERRED_SIZE)).addContainerGap()));
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		btnInsertBook = new JButton("Insert Book");
		btnInsertBook.addActionListener(actionListener);
		GridBagConstraints gbc_btnInsertBook = new GridBagConstraints();
		gbc_btnInsertBook.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnInsertBook.insets = new Insets(0, 0, 5, 0);
		gbc_btnInsertBook.gridx = 1;
		gbc_btnInsertBook.gridy = 0;
		panel.add(btnInsertBook, gbc_btnInsertBook);

		btnUpdateBooks = new JButton("Update Books");
		btnUpdateBooks.addActionListener(actionListener);
		GridBagConstraints gbc_btnUpdateBooks = new GridBagConstraints();
		gbc_btnUpdateBooks.insets = new Insets(0, 0, 5, 0);
		gbc_btnUpdateBooks.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnUpdateBooks.gridx = 1;
		gbc_btnUpdateBooks.gridy = 1;
		panel.add(btnUpdateBooks, gbc_btnUpdateBooks);

		btnDeleteBook = new JButton("Delete Book");
		btnDeleteBook.addActionListener(actionListener);
		GridBagConstraints gbc_btnDeleteBook = new GridBagConstraints();
		gbc_btnDeleteBook.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDeleteBook.insets = new Insets(0, 0, 5, 0);
		gbc_btnDeleteBook.gridx = 1;
		gbc_btnDeleteBook.gridy = 2;
		panel.add(btnDeleteBook, gbc_btnDeleteBook);

		btnReloadTable = new JButton("Reload Table");
		btnReloadTable.addActionListener(actionListener);
		GridBagConstraints gbc_btnReloadTable = new GridBagConstraints();
		gbc_btnReloadTable.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnReloadTable.gridx = 1;
		gbc_btnReloadTable.gridy = 3;
		panel.add(btnReloadTable, gbc_btnReloadTable);

		table = new JTable();
		if (isDebug) {
			table.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null, null, null },
					{ null, null, null, null, null, null }, { null, null, null, null, null, null },
					{ null, null, null, null, null, null }, { null, null, null, null, null, null },
					{ null, null, null, null, null, null }, }, new String[] { "Title", "Author", "Genre", "Year",
					"Price", "Stock" }));
		} else {
			table.setModel(new DefaultTableModel(library.toObjectMatrix(), new String[] { "Title", "Author", "Genre",
					"Year", "Price", "Stock" }));
		}
		table.getColumnModel().getColumn(0).setPreferredWidth(80);
		table.getColumnModel().getColumn(1).setPreferredWidth(70);
		table.getColumnModel().getColumn(2).setPreferredWidth(70);
		table.getColumnModel().getColumn(3).setPreferredWidth(45);
		table.getColumnModel().getColumn(4).setPreferredWidth(45);
		table.getColumnModel().getColumn(5).setPreferredWidth(45);
		scrollPane.setViewportView(table);
		bookPanel.setLayout(gl_bookPanel);
		tabbedPane.addTab("User", userPanel);

		JPanel panel_3 = new JPanel();

		JPanel panel_4 = new JPanel();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GroupLayout gl_userPanel = new GroupLayout(userPanel);
		gl_userPanel.setHorizontalGroup(
			gl_userPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_userPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_userPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
						.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
					.addGap(5))
		);
		gl_userPanel.setVerticalGroup(
			gl_userPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_userPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_userPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_userPanel.createSequentialGroup()
							.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE))
						.addComponent(panel_4, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE))
					.addGap(7))
		);
		
		userTable = new JTable();
		userTable.setEnabled(false);
		userTable.setModel(new DefaultTableModel(
			new Object[3][3] ,
			new String[] {
				"Username", "Password", "Type"
			}
		));
		scrollPane_1.setViewportView(userTable);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[] { 0, 0 };
		gbl_panel_4.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel_4.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_panel_4.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_4.setLayout(gbl_panel_4);

		btnCreateUser = new JButton("Create User");
		btnCreateUser.addActionListener(actionListener);
		GridBagConstraints gbc_btnCreateUser = new GridBagConstraints();
		gbc_btnCreateUser.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCreateUser.insets = new Insets(0, 0, 5, 0);
		gbc_btnCreateUser.gridx = 0;
		gbc_btnCreateUser.gridy = 0;
		panel_4.add(btnCreateUser, gbc_btnCreateUser);

		btnUpdatePassword = new JButton("Update Password");
		btnUpdatePassword.addActionListener(actionListener);
		GridBagConstraints gbc_btnUpdatePassword = new GridBagConstraints();
		gbc_btnUpdatePassword.insets = new Insets(0, 0, 5, 0);
		gbc_btnUpdatePassword.gridx = 0;
		gbc_btnUpdatePassword.gridy = 1;
		panel_4.add(btnUpdatePassword, gbc_btnUpdatePassword);

		btnDeleteUser = new JButton("Delete User");
		btnDeleteUser.addActionListener(actionListener);
		GridBagConstraints gbc_btnDeleteUser = new GridBagConstraints();
		gbc_btnDeleteUser.insets = new Insets(0, 0, 5, 0);
		gbc_btnDeleteUser.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDeleteUser.gridx = 0;
		gbc_btnDeleteUser.gridy = 2;
		panel_4.add(btnDeleteUser, gbc_btnDeleteUser);

		btnShowPassword = new JButton("List Users");
		btnShowPassword.addActionListener(actionListener);
		GridBagConstraints gbc_btnShowPassword = new GridBagConstraints();
		gbc_btnShowPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnShowPassword.gridx = 0;
		gbc_btnShowPassword.gridy = 3;
		panel_4.add(btnShowPassword, gbc_btnShowPassword);
		panel_3.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), },
				new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, }));

		JLabel lblUsername = new JLabel("Username:");
		panel_3.add(lblUsername, "2, 2, right, default");

		usernameTextField = new JTextField();
		panel_3.add(usernameTextField, "4, 2, fill, default");
		usernameTextField.setColumns(10);

		JLabel lblPassword = new JLabel("Password:");
		panel_3.add(lblPassword, "2, 4, right, default");

		passwordTextField = new JTextField();
		panel_3.add(passwordTextField, "4, 4, fill, default");
		passwordTextField.setColumns(10);

		JLabel lblType_1 = new JLabel("Type:");
		panel_3.add(lblType_1, "2, 6, right, default");

		userTypeComboBox = new JComboBox<String>();
		userTypeComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Employee", "Admin" }));
		userTypeComboBox.setSelectedIndex(0);
		panel_3.add(userTypeComboBox, "4, 6, fill, default");
		userPanel.setLayout(gl_userPanel);
		tabbedPane.addTab("Report", reportPanel);

		JPanel panel_2 = new JPanel();
		GroupLayout gl_reportPanel = new GroupLayout(reportPanel);
		gl_reportPanel.setHorizontalGroup(gl_reportPanel.createParallelGroup(Alignment.LEADING).addGroup(
				gl_reportPanel.createSequentialGroup().addContainerGap()
						.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(208, Short.MAX_VALUE)));
		gl_reportPanel.setVerticalGroup(gl_reportPanel.createParallelGroup(Alignment.LEADING).addGroup(
				gl_reportPanel.createSequentialGroup().addContainerGap()
						.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 191, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_2.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), },
				new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, }));

		JLabel lblType = new JLabel("Type:");
		panel_2.add(lblType, "2, 2, right, default");

		reportTypeComboBox = new JComboBox<String>();
		reportTypeComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Text", "XML" }));
		panel_2.add(reportTypeComboBox, "4, 2, fill, default");

		btnGenerateReport = new JButton("Generate Report");
		btnGenerateReport.addActionListener(actionListener);
		panel_2.add(btnGenerateReport, "4, 4");

		btnOpenReport = new JButton("Open Report");
		btnOpenReport.addActionListener(actionListener);
		panel_2.add(btnOpenReport, "4, 6");
		reportPanel.setLayout(gl_reportPanel);


	}

	@Override
	public void update(Observable o, Object arg) {
		table.setModel(new DefaultTableModel(library.toObjectMatrix(), new String[] { "Title", "Author", "Genre",
			"Year", "Price", "Stock" }));
		table.getColumnModel().getColumn(0).setPreferredWidth(80);
		table.getColumnModel().getColumn(1).setPreferredWidth(70);
		table.getColumnModel().getColumn(2).setPreferredWidth(70);
		table.getColumnModel().getColumn(3).setPreferredWidth(45);
		table.getColumnModel().getColumn(4).setPreferredWidth(45);
		table.getColumnModel().getColumn(5).setPreferredWidth(45);

	}

	public JFrame getFrmLibraryAdmin() {
		return frmLibraryAdmin;
	}

	public void setFrmLibraryAdmin(JFrame frmLibraryAdmin) {
		this.frmLibraryAdmin = frmLibraryAdmin;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

	public JTextField getPasswordTextField() {
		return passwordTextField;
	}

	public void setPasswordTextField(JTextField passwordTextField) {
		this.passwordTextField = passwordTextField;
	}

	public JTextField getUsernameTextField() {
		return usernameTextField;
	}

	public void setUsernameTextField(JTextField usernameTextField) {
		this.usernameTextField = usernameTextField;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public JButton getBtnInsertBook() {
		return btnInsertBook;
	}

	public void setBtnInsertBook(JButton btnInsertBook) {
		this.btnInsertBook = btnInsertBook;
	}

	public JButton getBtnUpdateBooks() {
		return btnUpdateBooks;
	}

	public void setBtnUpdateBooks(JButton btnUpdateBooks) {
		this.btnUpdateBooks = btnUpdateBooks;
	}

	public JButton getBtnDeleteBook() {
		return btnDeleteBook;
	}

	public void setBtnDeleteBook(JButton btnDeleteBook) {
		this.btnDeleteBook = btnDeleteBook;
	}

	public JButton getBtnReloadTable() {
		return btnReloadTable;
	}

	public void setBtnReloadTable(JButton btnReloadTable) {
		this.btnReloadTable = btnReloadTable;
	}

	public JButton getBtnCreateUser() {
		return btnCreateUser;
	}

	public void setBtnCreateUser(JButton btnCreateUser) {
		this.btnCreateUser = btnCreateUser;
	}

	public JButton getBtnUpdatePassword() {
		return btnUpdatePassword;
	}

	public void setBtnUpdatePassword(JButton btnUpdatePassword) {
		this.btnUpdatePassword = btnUpdatePassword;
	}

	public JButton getBtnDeleteUser() {
		return btnDeleteUser;
	}

	public void setBtnDeleteUser(JButton btnDeleteUser) {
		this.btnDeleteUser = btnDeleteUser;
	}

	public JButton getBtnShowPassword() {
		return btnShowPassword;
	}

	public void setBtnShowPassword(JButton btnShowPassword) {
		this.btnShowPassword = btnShowPassword;
	}

	public JButton getBtnGenerateReport() {
		return btnGenerateReport;
	}

	public void setBtnGenerateReport(JButton btnGenerateReport) {
		this.btnGenerateReport = btnGenerateReport;
	}

	public JButton getBtnOpenReport() {
		return btnOpenReport;
	}

	public void setBtnOpenReport(JButton btnOpenReport) {
		this.btnOpenReport = btnOpenReport;
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

	public JComboBox<String> getReportTypeComboBox() {
		return reportTypeComboBox;
	}

	public void setReportTypeComboBox(JComboBox<String> reportTypeComboBox) {
		this.reportTypeComboBox = reportTypeComboBox;
	}

	public JTable getUserTable() {
		return userTable;
	}

	public void setUserTable(JTable userTable) {
		this.userTable = userTable;
	}

	public JComboBox<String> getUserTypeComboBox() {
		return userTypeComboBox;
	}

	public void setUserTypeComboBox(JComboBox<String> userTypeComboBox) {
		this.userTypeComboBox = userTypeComboBox;
	}

}
