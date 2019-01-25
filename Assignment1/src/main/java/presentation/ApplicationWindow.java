package presentation;

import dataSource.AdminMapper;
import dataSource.ClientMapper;
import domain.Admin;
import domain.Client;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class ApplicationWindow {

	private JFrame         frmBankLogin;
	private JTextField     textField;
	private JPasswordField passwordField;
	private JLabel         lblInvalidLoginCredentials;
	private JButton        btnLogin;

	/**
	 * Create the application.
	 */
	public ApplicationWindow() {
		initialize();
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApplicationWindow window = new ApplicationWindow();
					window.frmBankLogin.setVisible(true);
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
		frmBankLogin = new JFrame();
		frmBankLogin.setTitle("Bank Login");
		frmBankLogin.setBounds(100, 100, 450, 300);
		frmBankLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel lblUsername = new JLabel("Username");

		JLabel lblPassword = new JLabel("Password");

		textField = new JTextField("vlad");
		textField.setColumns(10);

		passwordField = new JPasswordField("1234");

		btnLogin = new JButton("Login");
		btnLogin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					btnLogin.doClick();
				}

			}
		});
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = textField.getText();
				String password = new String(passwordField.getPassword());
				if (login(username, password))
					frmBankLogin.dispose();
				else
					lblInvalidLoginCredentials.setVisible(true);


			}
		});

		lblInvalidLoginCredentials = new JLabel("Invalid Login Credentials!");
		lblInvalidLoginCredentials.setForeground(Color.RED);
		lblInvalidLoginCredentials.setVisible(false);

		GroupLayout groupLayout = new GroupLayout(frmBankLogin.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
								.addContainerGap()
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addGroup(groupLayout.createSequentialGroup()
												.addComponent(lblInvalidLoginCredentials, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(btnLogin))
										.addGroup(groupLayout.createSequentialGroup()
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
														.addComponent(lblPassword)
														.addComponent(lblUsername))
												.addGap(18)
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
														.addComponent(passwordField, GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
														.addComponent(textField, GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE))))
								.addGap(128))
		);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addGap(75)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblUsername)
										.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblPassword)
										.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnLogin)
										.addComponent(lblInvalidLoginCredentials))
								.addContainerGap(102, Short.MAX_VALUE))
		);
		frmBankLogin.getContentPane().setLayout(groupLayout);
	}

	public JFrame getFrmBankLogin() {
		return frmBankLogin;
	}

	public void setFrmBankLogin(JFrame frmBankLogin) {
		this.frmBankLogin = frmBankLogin;
	}

	public JTextField getTextField() {
		return textField;
	}

	public void setTextField(JTextField textField) {
		this.textField = textField;
	}

	public JPasswordField getPasswordField() {
		return passwordField;
	}

	public void setPasswordField(JPasswordField passwordField) {
		this.passwordField = passwordField;
	}

	public JButton getBtnLogin() {
		return btnLogin;
	}

	public void setBtnLogin(JButton btnLogin) {
		this.btnLogin = btnLogin;
	}

	public void dispose() {
		frmBankLogin.dispose();
	}

	private boolean login(String username, String password) {
		AdminMapper adminMapper = new AdminMapper();
		try {
			Admin admin = adminMapper.login(username, password);
			if (admin != null) {
				AdminWindow.main(admin);
				return true;

			} else {
				ClientMapper clientMapper = new ClientMapper();
				Client client = clientMapper.login(username, password);
				if (client != null) {
					ClientWindow.main(client);
					return true;
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(frmBankLogin, "Please verify connection to Database", "Unable to Login", 0);

		}

		return false;
	}
}
