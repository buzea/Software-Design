package presentation;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import domain.Account;
import domain.Admin;
import domain.Client;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdminWindow {

	private final static String            CLIENT_TAB  = "Client";
	private final static String            ACCOUNT_TAB = "Account";
	private final static String            REPORT_TAB  = "Reports";
	private              Admin             admin;
	private              Client            client;
	private              Account           account;
	private              JFrame            frmBankAdmin;
	private              JTextField        idClientTextField;
	private              JTextField        usernameTextField;
	private              JTextField        passwordTextField;
	private              JTextField        firstNameTextField;
	private              JTextField        lastNameTextField;
	private              JTextField        addressTextField;
	private              JTextField        emailTextField;
	private              JTextField        phoneTextField;
	private              JTable            table;
	private              JTextField        idAccountTextField;
	private              JTextField        dateCreatedTextField;
	private              JTextField        ownerIdTextField;
	private              JTextField        startDateTextField;
	private              JTextField        endDateTextField;
	private              JSpinner          balanceSpinner;
	private              JComboBox<String> typeComboBox;
	private              SimpleDateFormat  df          = new SimpleDateFormat("dd-MM-yyy");

	/**
	 * Create the application.
	 */
	public AdminWindow(Admin admin) {
		this.admin = admin;
		initialize();
	}

	/**
	 * Launch the application.
	 */
	public static void main(final Admin admin) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminWindow window = new AdminWindow(admin);
					window.frmBankAdmin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBankAdmin = new JFrame();
		frmBankAdmin.setTitle("Bank Admin");
		frmBankAdmin.setBounds(100, 100, 550, 500);
		frmBankAdmin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frmBankAdmin.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		JMenuItem mntmLogOut = new JMenuItem("Log Out");
		mntmLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ApplicationWindow.main(null);
				frmBankAdmin.dispose();
			}
		});
		mnFile.add(mntmLogOut);
		mnFile.add(mntmExit);

		addComponentToPane(frmBankAdmin.getContentPane());

	}

	public void addComponentToPane(Container pane) {
		JTabbedPane tabbedPane = new JTabbedPane();

		// Create the "cards".
		JPanel card1 = new JPanel();

		JPanel card2 = new JPanel();

		JPanel card3 = new JPanel();

		tabbedPane.addTab(CLIENT_TAB, card1);

		JPanel formPanel = new JPanel();
		formPanel
				.setLayout(new FormLayout(new ColumnSpec[]{
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),}, new RowSpec[]{
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,}));

		JLabel lblClientId = new JLabel("Client ID:");
		formPanel.add(lblClientId, "2, 2");

		idClientTextField = new JTextField();
		formPanel.add(idClientTextField, "6, 2, fill, default");
		idClientTextField.setColumns(10);

		JLabel lblUsername = new JLabel("Username:");
		formPanel.add(lblUsername, "2, 4");

		usernameTextField = new JTextField();
		formPanel.add(usernameTextField, "6, 4, fill, default");
		usernameTextField.setColumns(10);

		JLabel lblPassword = new JLabel("Password:");
		formPanel.add(lblPassword, "2, 6");

		passwordTextField = new JTextField();
		formPanel.add(passwordTextField, "6, 6, fill, default");
		passwordTextField.setColumns(10);

		JLabel lblFirstName = new JLabel("First Name:");
		formPanel.add(lblFirstName, "2, 8");

		firstNameTextField = new JTextField();
		formPanel.add(firstNameTextField, "6, 8, fill, default");
		firstNameTextField.setColumns(10);

		JLabel lblLastname = new JLabel("LastName:");
		formPanel.add(lblLastname, "2, 10");

		lastNameTextField = new JTextField();
		formPanel.add(lastNameTextField, "6, 10, fill, default");
		lastNameTextField.setColumns(10);

		JLabel lblAddress = new JLabel("Address:");
		formPanel.add(lblAddress, "2, 12");

		addressTextField = new JTextField();
		formPanel.add(addressTextField, "6, 12, fill, default");
		addressTextField.setColumns(10);

		JLabel lblEmail = new JLabel("Email:");
		formPanel.add(lblEmail, "2, 14");

		emailTextField = new JTextField();
		formPanel.add(emailTextField, "6, 14, fill, default");
		emailTextField.setColumns(10);

		JLabel lblPhone = new JLabel("Phone:");
		formPanel.add(lblPhone, "2, 16");

		phoneTextField = new JTextField();
		phoneTextField.setText("");
		formPanel.add(phoneTextField, "6, 16, fill, default");
		phoneTextField.setColumns(10);

		JPanel panel = new JPanel();
		panel.setLayout(new FormLayout(
				new ColumnSpec[]{FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,}, new RowSpec[]{
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));

		JButton btnFindClientBy = new JButton("Find Client By ID");
		btnFindClientBy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client = admin.findClientById(idClientTextField.getText());
				if (client == null)
					JOptionPane.showMessageDialog(frmBankAdmin, "Invalid ID!");
				else
					updateFields();

			}
		});
		panel.add(btnFindClientBy, "2, 2");

		JButton btnFindClientBy_1 = new JButton("Find Client By Username");
		btnFindClientBy_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client = admin.findClientByUsername(usernameTextField.getText());
				if (client == null)
					JOptionPane.showMessageDialog(frmBankAdmin,
							"Invalid username!");
				else
					updateFields();
			}
		});
		panel.add(btnFindClientBy_1, "2, 4");

		JButton btnFindClientBy_2 = new JButton("Find Client By Email");
		btnFindClientBy_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client = admin.findClientByEmail(emailTextField.getText());
				if (client == null)
					JOptionPane.showMessageDialog(frmBankAdmin,
							"Invalid email!");
				else
					updateFields();
			}
		});
		panel.add(btnFindClientBy_2, "2, 6");

		JButton btnCreateClient = new JButton("Create Client");
		btnCreateClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (admin.createClient(idClientTextField.getText(),
						addressTextField.getText(), emailTextField.getText(),
						firstNameTextField.getText(),
						lastNameTextField.getText(),
						passwordTextField.getText(), phoneTextField.getText(),
						usernameTextField.getText()))
					JOptionPane.showMessageDialog(frmBankAdmin, "User Created");
				else
					JOptionPane.showMessageDialog(frmBankAdmin,
							"The data provided is not coherent", "ERROR", 0);
			}
		});
		panel.add(btnCreateClient, "2, 8");

		JButton btnUpdateClient = new JButton("Update Client");
		btnUpdateClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (admin.updateClient(idClientTextField.getText(),
						addressTextField.getText(), emailTextField.getText(),
						firstNameTextField.getText(),
						lastNameTextField.getText(),
						passwordTextField.getText(), phoneTextField.getText(),
						usernameTextField.getText()))
					JOptionPane.showMessageDialog(frmBankAdmin, "User Updated");
				else
					JOptionPane.showMessageDialog(frmBankAdmin,
							"The data provided is not coherent", "ERROR", 0);
			}
		});
		panel.add(btnUpdateClient, "2, 10");

		JButton btnDeleteClient = new JButton("Delete Client");
		btnDeleteClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (admin.deleteClient(idClientTextField.getText())) {
					JOptionPane.showMessageDialog(frmBankAdmin, "User Deleted");
					clearFields();
				} else
					JOptionPane.showMessageDialog(frmBankAdmin, "Invalid ID!");

			}
		});
		panel.add(btnDeleteClient, "2, 12");

		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_card1 = new GroupLayout(card1);
		gl_card1.setHorizontalGroup(gl_card1
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_card1.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										gl_card1.createParallelGroup(
												Alignment.LEADING)
												.addGroup(
														gl_card1.createSequentialGroup()
																.addComponent(
																		formPanel,
																		GroupLayout.PREFERRED_SIZE,
																		211,
																		GroupLayout.PREFERRED_SIZE)
																.addGap(28)
																.addComponent(
																		panel,
																		GroupLayout.PREFERRED_SIZE,
																		180,
																		GroupLayout.PREFERRED_SIZE))
												.addComponent(
														scrollPane,
														GroupLayout.DEFAULT_SIZE,
														421, Short.MAX_VALUE))
								.addGap(8)));
		gl_card1.setVerticalGroup(gl_card1.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_card1.createSequentialGroup()
						.addGap(11)
						.addGroup(
								gl_card1.createParallelGroup(
										Alignment.TRAILING, false)
										.addComponent(formPanel,
												Alignment.LEADING,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(panel, Alignment.LEADING,
												GroupLayout.DEFAULT_SIZE, 222,
												Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 60,
								Short.MAX_VALUE).addGap(9)));

		table = new JTable();
		table.setEnabled(false);
		scrollPane.setViewportView(table);
		card1.setLayout(gl_card1);
		tabbedPane.addTab(ACCOUNT_TAB, card2);

		JPanel panel_1 = new JPanel();

		JPanel panel_2 = new JPanel();
		GroupLayout gl_card2 = new GroupLayout(card2);
		gl_card2.setHorizontalGroup(gl_card2.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_card2.createSequentialGroup()
						.addContainerGap()
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 250,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 255,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));
		gl_card2.setVerticalGroup(gl_card2.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_card2.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_card2.createParallelGroup(
										Alignment.TRAILING, false)
										.addComponent(panel_2,
												Alignment.LEADING,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(panel_1,
												Alignment.LEADING,
												GroupLayout.DEFAULT_SIZE, 392,
												Short.MAX_VALUE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));
		panel_2.setLayout(new FormLayout(
				new ColumnSpec[]{FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,}, new RowSpec[]{
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));

		JButton btnGenerateAccountId = new JButton("Generate New Account ID");
		btnGenerateAccountId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				idAccountTextField.setText(admin.generateAccountId());
			}
		});
		panel_2.add(btnGenerateAccountId, "2, 2");

		JButton btnCreateAccount = new JButton("Create Account");
		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (admin.createAccount(idAccountTextField.getText(),
						(Double) balanceSpinner.getValue(),
						ownerIdTextField.getText(),
						(String) typeComboBox.getSelectedItem())) {
					JOptionPane.showMessageDialog(frmBankAdmin,
							"Account Created");

					dateCreatedTextField.setText(df.format(new Date()));
				} else {
					JOptionPane.showMessageDialog(frmBankAdmin,
							"Account Could Not Be Created", "ERROR", 0);
				}
			}
		});
		panel_2.add(btnCreateAccount, "2, 4");

		JButton btnReadAccuntUsing = new JButton("Find Account by ID");
		btnReadAccuntUsing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				account = admin.findAccountById(idAccountTextField.getText());
				if (account != null) {
					updateFields();
				} else {
					JOptionPane.showMessageDialog(frmBankAdmin, "Invalid ID!",
							"ERROR", 0);
				}
			}
		});
		panel_2.add(btnReadAccuntUsing, "2, 6");

		JButton btnUpdateAccount = new JButton("Update Account");
		btnUpdateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date date;
				try {
					date = df.parse(dateCreatedTextField.getText());
					if (admin.updateAccount(idAccountTextField.getText(),
							(Double) balanceSpinner.getValue(),
							(String) typeComboBox.getSelectedItem(), date,
							ownerIdTextField.getText())) {
						JOptionPane.showMessageDialog(frmBankAdmin,
								"Account Updated");
					} else {
						JOptionPane.showMessageDialog(frmBankAdmin,
								"Account Could Not Be Updated", "ERROR", 0);
					}

				} catch (ParseException e1) {
					JOptionPane.showMessageDialog(frmBankAdmin,
							"Date could not be parsed");
				}

			}
		});
		panel_2.add(btnUpdateAccount, "2, 8");

		JButton btnDeleteAccount = new JButton("Delete Account");
		btnDeleteAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (admin.deleteAccount(idAccountTextField.getText())) {
					clearFields();
					JOptionPane.showMessageDialog(frmBankAdmin, "Deleted!");
				} else {
					JOptionPane.showMessageDialog(frmBankAdmin,
							"Account Could Not Be Deleted", "ERROR", 0);
				}
			}
		});
		panel_2.add(btnDeleteAccount, "2, 10");
		panel_1.setLayout(new FormLayout(new ColumnSpec[]{
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),}, new RowSpec[]{
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,}));

		JLabel lblAccountId = new JLabel("Account ID:");
		panel_1.add(lblAccountId, "2, 2");

		idAccountTextField = new JTextField();
		panel_1.add(idAccountTextField, "6, 2, fill, default");
		idAccountTextField.setColumns(10);

		JLabel lblBalance = new JLabel("Balance:");
		panel_1.add(lblBalance, "2, 4");

		balanceSpinner = new JSpinner();
		balanceSpinner.setModel(new SpinnerNumberModel(new Double(0),
				new Double(0), null, new Double(1)));
		panel_1.add(balanceSpinner, "6, 4");

		JLabel lblType = new JLabel("Type:");
		panel_1.add(lblType, "2, 6");

		typeComboBox = new JComboBox<String>();
		typeComboBox.setModel(new DefaultComboBoxModel<String>(new String[]{
				"current", "savings"}));
		panel_1.add(typeComboBox, "6, 6, fill, default");

		JLabel lblDateCreated = new JLabel("Date Created:");
		panel_1.add(lblDateCreated, "2, 8");

		dateCreatedTextField = new JTextField();
		dateCreatedTextField.setEditable(false);
		panel_1.add(dateCreatedTextField, "6, 8, fill, default");
		dateCreatedTextField.setColumns(10);

		JLabel lblOwnerId = new JLabel("Owner ID:");
		panel_1.add(lblOwnerId, "2, 10");

		ownerIdTextField = new JTextField();
		panel_1.add(ownerIdTextField, "6, 10, fill, default");
		ownerIdTextField.setColumns(10);
		card2.setLayout(gl_card2);
		tabbedPane.addTab(REPORT_TAB, card3);

		JPanel panel_3 = new JPanel();

		JScrollPane scrollPane_1 = new JScrollPane();
		GroupLayout gl_card3 = new GroupLayout(card3);
		gl_card3.setHorizontalGroup(gl_card3
				.createParallelGroup(Alignment.TRAILING)
				.addGroup(
						gl_card3.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										gl_card3.createParallelGroup(
												Alignment.TRAILING)
												.addComponent(
														scrollPane_1,
														Alignment.LEADING,
														GroupLayout.DEFAULT_SIZE,
														509, Short.MAX_VALUE)
												.addComponent(
														panel_3,
														Alignment.LEADING,
														GroupLayout.DEFAULT_SIZE,
														509, Short.MAX_VALUE))
								.addContainerGap()));
		gl_card3.setVerticalGroup(gl_card3.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_card3.createSequentialGroup()
						.addContainerGap()
						.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 88,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE,
								292, Short.MAX_VALUE).addContainerGap()));

		final JTextPane reportPane = new JTextPane();
		scrollPane_1.setViewportView(reportPane);
		panel_3.setLayout(new FormLayout(new ColumnSpec[]{
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),}, new RowSpec[]{
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,}));

		JLabel lblStartDate = new JLabel("Start Date:");
		panel_3.add(lblStartDate, "2, 2");

		startDateTextField = new JTextField();
		startDateTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		startDateTextField.setText("01-01-2000");
		panel_3.add(startDateTextField, "6, 2, left, default");
		startDateTextField.setColumns(10);

		JLabel lblEndDate = new JLabel("End Date:");
		panel_3.add(lblEndDate, "2, 4");

		endDateTextField = new JTextField();
		endDateTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		endDateTextField.setText("01-01-2100");
		panel_3.add(endDateTextField, "6, 4, left, default");
		endDateTextField.setColumns(10);

		JButton btnGenerateReport = new JButton("Generate Report");
		btnGenerateReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (client == null) {
					JOptionPane
							.showMessageDialog(
									frmBankAdmin,
									"Please load a Client in the \"Client Tab\" first!",
									"ERROR", 0);
				} else {
					reportPane.setText(client.generateReport(startDateTextField.getText(), endDateTextField.getText()));
				}
			}
		});
		btnGenerateReport
				.setToolTipText("Generates Report if there is a Client loaded in the \"Client Tab\"");
		panel_3.add(btnGenerateReport, "2, 6");

		JLabel lblDateFormat = new JLabel("Date Format: dd-MM-yyyy");
		panel_3.add(lblDateFormat, "6, 6");
		card3.setLayout(gl_card3);
		table.setModel(new DefaultTableModel(new Object[4][4], new String[]{
				"Account Nb", "Balance", "Type", "Data Created"}));

		pane.add(tabbedPane, BorderLayout.CENTER);
	}

	private void updateFields() {
		if (client != null) {
			idClientTextField.setText(client.getIdClient() + "");
			usernameTextField.setText(client.getUsername());
			passwordTextField.setText(client.getPassword());
			firstNameTextField.setText(client.getFirstName());
			lastNameTextField.setText(client.getLastName());
			emailTextField.setText(client.getEmail());
			phoneTextField.setText(client.getPhone());
			addressTextField.setText(client.getAddress());

			table.setModel(new DefaultTableModel(client.getAccountsMatrix(),
					new String[]{"Account Nb", "Balance", "Type",
							"Data Created"}));
		}

		if (account != null) {
			idAccountTextField.setText(account.getIdAccount() + "");
			balanceSpinner.setValue(account.getBalance());
			if (account.getType().equals(Account.SAVINGS)) {
				typeComboBox.setSelectedIndex(1);
			} else {
				typeComboBox.setSelectedIndex(0);
			}
			dateCreatedTextField.setText(df.format(account.getDateCreated()));
			ownerIdTextField.setText(account.getClient().getIdClient() + "");
		}

	}

	private void clearFields() {

		idClientTextField.setText("");
		usernameTextField.setText("");
		passwordTextField.setText("");
		firstNameTextField.setText("");
		lastNameTextField.setText("");
		emailTextField.setText("");
		phoneTextField.setText("");
		addressTextField.setText("");

		table.setModel(new DefaultTableModel(new Object[4][4], new String[]{
				"Account Nb", "Balance", "Type", "Data Created"}));
		// /////////////////////

		idAccountTextField.setText("");
		balanceSpinner.setValue((Double) 0.0);
		typeComboBox.setSelectedIndex(1);

		dateCreatedTextField.setText("");
		ownerIdTextField.setText("");

	}
}
