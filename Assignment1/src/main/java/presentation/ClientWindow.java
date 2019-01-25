package presentation;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import domain.Account;
import domain.Client;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ClientWindow {

	public static final  String[]           companies       = new String[]{"Electrica", "E-ON", "Digi", "UPC", "RADET"};
	private final static String             MAIN_CARD       = "Panel0";
	private final static String             VIEW_ACCOUNTS   = "Panel1";
	private final static String             TRANSER_MONEY   = "Panel2";
	private final static String             PAY_BILLS       = "Panel3";
	private final static String             GENERATE_REPORT = "Panel4";
	private              JFrame             frame;
	private              Client             client;
	private              JPanel             cards;
	private              JTable             table;
	private              JTextField         textField_transfer;
	private              JComboBox<Account> comboBox_transfer;
	private              JComboBox<Account> comboBox_3;
	private              JComboBox<String>  comboBox_2;
	private              JTextPane          reportPane;
	private              JTextField         textField_startDate;
	private              JTextField         textField_endDate;
	;

	/**
	 * Create the application.
	 */
	public ClientWindow(Client client) {
		this.client = client;
		initialize();
	}

	/**
	 * Launch the application.
	 */
	public static void main(final Client client) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientWindow window = new ClientWindow(client);
					window.frame.setVisible(true);
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
		frame = new JFrame();
		frame.setTitle("Bank");
		frame.setBounds(100, 100, 550, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel lblHello = new JLabel("Hello " + client.getFirstName());
		lblHello.setFont(lblHello.getFont().deriveFont(lblHello.getFont().getStyle() | Font.BOLD, lblHello.getFont().getSize() + 5f));

		cards = new JPanel(new CardLayout());

		JPanel card0 = new JPanel();
		JButton btnViewAccounts = new JButton("View Accounts");
		btnViewAccounts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (cards.getLayout());
				table.setModel(new DefaultTableModel(
						client.getAccountsMatrix(),
						new String[]{
								"Account Nb", "Balance", "Type", "Data Created"
						}
				));

				cl.show(cards, VIEW_ACCOUNTS);

			}
		});

		JButton btnTransferMoneyBetween = new JButton("Transfer Money Between Accounts");
		btnTransferMoneyBetween.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (cards.getLayout());
				cl.show(cards, TRANSER_MONEY);
			}
		});

		JButton btnPayUtilitiesBills = new JButton("Pay Utilities Bills");
		btnPayUtilitiesBills.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (cards.getLayout());
				cl.show(cards, PAY_BILLS);
			}
		});

		JButton btnGeneratePeriodicalReport = new JButton("Generate Periodical Report");
		btnGeneratePeriodicalReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reportPane.setText(client.generateReport());
				CardLayout cl = (CardLayout) (cards.getLayout());
				cl.show(cards, GENERATE_REPORT);
			}
		});
		GroupLayout gl_card0 = new GroupLayout(card0);
		gl_card0.setHorizontalGroup(
				gl_card0.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_card0.createSequentialGroup()
								.addContainerGap()
								.addGroup(gl_card0.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(btnGeneratePeriodicalReport, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btnPayUtilitiesBills, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btnViewAccounts, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btnTransferMoneyBetween, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addContainerGap(206, Short.MAX_VALUE))
		);
		gl_card0.setVerticalGroup(
				gl_card0.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_card0.createSequentialGroup()
								.addGap(5)
								.addComponent(btnViewAccounts)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnTransferMoneyBetween)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnPayUtilitiesBills)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnGeneratePeriodicalReport)
								.addContainerGap(102, Short.MAX_VALUE))
		);
		card0.setLayout(gl_card0);


		JPanel card1 = new JPanel();
		JPanel card2 = new JPanel();
		JPanel card3 = new JPanel();
		JPanel card4 = new JPanel();

		cards.add(card0, MAIN_CARD);
		cards.add(card1, VIEW_ACCOUNTS);

		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_card1 = new GroupLayout(card1);
		gl_card1.setHorizontalGroup(
				gl_card1.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE)
		);
		gl_card1.setVerticalGroup(
				gl_card1.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
		);

		table = new JTable();
		table.setModel(new DefaultTableModel(
				client.getAccountsMatrix(),
				new String[]{
						"Account Nb", "Balance", "Type", "Data Created"
				}
		));
		table.setFillsViewportHeight(true);
		table.setEnabled(false);
		scrollPane.setViewportView(table);
		card1.setLayout(gl_card1);
		cards.add(card2, TRANSER_MONEY);

		JLabel lblSource = new JLabel("Source:");

		JLabel lblDestination = new JLabel("Destination:");

		JLabel lblAmount = new JLabel("Amount:");

		final JSpinner spinner_transfer = new JSpinner();
		spinner_transfer.setModel(new SpinnerNumberModel(new Double(1), new Double(0), null, new Double(1)));

		JButton btnTransfer = new JButton("Transfer");

		//TODO
		comboBox_transfer = new JComboBox<Account>(client.getAccountsArray());
		//comboBox_transfer = new JComboBox<Account>();


		textField_transfer = new JTextField();
		textField_transfer.setColumns(10);

		btnTransfer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (client.transfer((Account) comboBox_transfer.getSelectedItem(), textField_transfer.getText(), (Double) spinner_transfer.getValue())) {
					JOptionPane.showMessageDialog(frame, "Transfer Successful!");
					refresh();
				} else {
					JOptionPane.showMessageDialog(frame, "The transaction could not be performed", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		GroupLayout gl_card2 = new GroupLayout(card2);
		gl_card2.setHorizontalGroup(
				gl_card2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_card2.createSequentialGroup()
								.addContainerGap()
								.addGroup(gl_card2.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_card2.createSequentialGroup()
												.addGroup(gl_card2.createParallelGroup(Alignment.TRAILING, false)
														.addComponent(lblAmount, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(lblSource, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(lblDestination, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
												.addGap(18)
												.addGroup(gl_card2.createParallelGroup(Alignment.TRAILING)
														.addGroup(gl_card2.createParallelGroup(Alignment.TRAILING, false)
																.addComponent(spinner_transfer, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
																.addComponent(comboBox_transfer, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE))
														.addComponent(textField_transfer, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)))
										.addComponent(btnTransfer))
								.addContainerGap(227, Short.MAX_VALUE))
		);
		gl_card2.setVerticalGroup(
				gl_card2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_card2.createSequentialGroup()
								.addContainerGap()
								.addGroup(gl_card2.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblSource)
										.addComponent(comboBox_transfer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(gl_card2.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblDestination)
										.addComponent(textField_transfer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(gl_card2.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblAmount)
										.addComponent(spinner_transfer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(btnTransfer)
								.addContainerGap(90, Short.MAX_VALUE))
		);
		card2.setLayout(gl_card2);
		cards.add(card3, PAY_BILLS);

		JPanel panel = new JPanel();

		GroupLayout gl_card3 = new GroupLayout(card3);
		gl_card3.setHorizontalGroup(
				gl_card3.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_card3.createSequentialGroup()
								.addContainerGap()
								.addComponent(panel, GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE))
		);
		gl_card3.setVerticalGroup(
				gl_card3.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_card3.createSequentialGroup()
								.addContainerGap()
								.addComponent(panel, GroupLayout.PREFERRED_SIZE, 225, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(144, Short.MAX_VALUE))
		);
		panel.setLayout(new FormLayout(new ColumnSpec[]{
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
				new RowSpec[]{
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,}));

		JLabel lblCompany = new JLabel("Company:");
		panel.add(lblCompany, "2, 2");


		JLabel lblAmount_1 = new JLabel("Amount:");
		panel.add(lblAmount_1, "2, 4");


		final JSpinner spinner_1 = new JSpinner();
		panel.add(spinner_1, "6, 4");
		spinner_1.setModel(new SpinnerNumberModel(new Double(1), new Double(0), null, new Double(1)));

		JLabel lblSourceAccount = new JLabel("Source Account:");
		panel.add(lblSourceAccount, "2, 6");


		//TODO
		comboBox_2 = new JComboBox<String>(companies);
		comboBox_3 = new JComboBox<Account>(client.getAccountsArray());

		//test values
		//comboBox_3=new JComboBox<>();
		//comboBox_2 = new JComboBox<String>();
		panel.add(comboBox_2, "6, 2");
		panel.add(comboBox_3, "6, 6");

		JButton btnPayBill = new JButton("Pay Bill");
		panel.add(btnPayBill, "2, 8");
		btnPayBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (client.payUtilities((String) comboBox_2.getSelectedItem(), (Account) comboBox_3.getSelectedItem(), (Double) spinner_1.getValue())) {
					JOptionPane.showMessageDialog(frame, "Payment Successful!");
				} else {
					JOptionPane.showMessageDialog(frame, "Insufficient funds!");
				}

			}
		});
		card3.setLayout(gl_card3);
		cards.add(card4, GENERATE_REPORT);

		JScrollPane scrollPane_1 = new JScrollPane();

		JLabel lblStartDate = new JLabel("Start Date");
		lblStartDate.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel lblEndDate = new JLabel(" End Date");
		lblEndDate.setHorizontalAlignment(SwingConstants.RIGHT);

		textField_startDate = new JTextField();
		textField_startDate.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_startDate.setText("01-01-2000");
		textField_startDate.setColumns(10);

		textField_endDate = new JTextField();
		textField_endDate.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_endDate.setText("01-01-2100");
		textField_endDate.setColumns(10);

		JLabel lblDateFormatDdmmyyyy = new JLabel("Date Format dd-MM-yyyy");

		JButton btnGenerateReport = new JButton("Generate Report");
		btnGenerateReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reportPane.setText(client.generateReport(textField_startDate.getText(), textField_endDate.getText()));
			}
		});
		GroupLayout gl_card4 = new GroupLayout(card4);
		gl_card4.setHorizontalGroup(
				gl_card4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_card4.createSequentialGroup()
								.addGroup(gl_card4.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_card4.createSequentialGroup()
												.addGroup(gl_card4.createParallelGroup(Alignment.LEADING)
														.addComponent(lblStartDate)
														.addComponent(lblEndDate))
												.addGap(18)
												.addGroup(gl_card4.createParallelGroup(Alignment.LEADING)
														.addGroup(gl_card4.createSequentialGroup()
																.addComponent(textField_endDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(ComponentPlacement.UNRELATED)
																.addComponent(btnGenerateReport))
														.addComponent(textField_startDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
										.addComponent(lblDateFormatDdmmyyyy, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
										.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE))
								.addContainerGap())
		);
		gl_card4.setVerticalGroup(
				gl_card4.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_card4.createSequentialGroup()
								.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(gl_card4.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblStartDate)
										.addComponent(textField_startDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_card4.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblEndDate)
										.addComponent(textField_endDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnGenerateReport))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblDateFormatDdmmyyyy))
		);

		reportPane = new JTextPane();
		reportPane.setEditable(false);
		reportPane.setText("Test String");
		scrollPane_1.setViewportView(reportPane);
		card4.setLayout(gl_card4);


		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addContainerGap()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(cards, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblHello, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE))
								.addContainerGap())
		);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addContainerGap()
								.addComponent(lblHello, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(cards, GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
								.addContainerGap())
		);
		frame.getContentPane().setLayout(groupLayout);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

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
				frame.dispose();
			}
		});
		mnFile.add(mntmLogOut);


		mnFile.add(mntmExit);

		JMenu mnNavigation = new JMenu("Navigation");
		menuBar.add(mnNavigation);

		JMenuItem mntmHome = new JMenuItem("Home");
		mntmHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh();
				CardLayout cl = (CardLayout) (cards.getLayout());
				cl.show(cards, MAIN_CARD);
			}
		});
		mnNavigation.add(mntmHome);

		JMenuItem mntmViewAccounts = new JMenuItem("Accounts");
		mntmViewAccounts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (cards.getLayout());
				table.setModel(new DefaultTableModel(
						client.getAccountsMatrix(),
						new String[]{
								"Account Nb", "Balance", "Type", "Data Created"
						}
				));

				cl.show(cards, VIEW_ACCOUNTS);
			}
		});
		mnNavigation.add(mntmViewAccounts);

		JMenuItem mntmTransaction = new JMenuItem("Transaction");
		mntmTransaction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (cards.getLayout());
				cl.show(cards, TRANSER_MONEY);
			}
		});
		mnNavigation.add(mntmTransaction);

		JMenuItem mntmBills = new JMenuItem("Bills");
		mntmBills.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (cards.getLayout());
				cl.show(cards, PAY_BILLS);
			}
		});
		mnNavigation.add(mntmBills);

		JMenuItem mntmReport = new JMenuItem("Report");
		mntmReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reportPane.setText(client.generateReport());
				CardLayout cl = (CardLayout) (cards.getLayout());
				cl.show(cards, GENERATE_REPORT);
			}
		});
		mnNavigation.add(mntmReport);

	}

	private void refresh() {
		client.refresh();
		List<Account> acc = client.getAccounts();
		//comboBox_3 = new JComboBox<Account>(client.getAccountsArray());
		//comboBox_transfer = new JComboBox<Account>(client.getAccountsArray());
		comboBox_3.removeAllItems();
		comboBox_transfer.removeAllItems();
		for (Account i : acc) {
			comboBox_transfer.addItem(i);
			comboBox_3.addItem(i);
		}

	}
}
