package gui;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import listener.ServerListener;
import model.Doctor;
import model.Staff;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class LoginWindow implements Observer {

	private JFrame             frmHospitalLogin;
	private JTextField         usernameText;
	private Socket             socketClient;
	private ObjectOutputStream output;
	private JPasswordField     passwordField;
	private ServerListener     listener;

	/**
	 * Create the application.
	 */
	public LoginWindow() {

		initialize();
		try {
			socketClient = new Socket("localhost", 9990);
			output = new ObjectOutputStream(socketClient.getOutputStream());
			ObjectInputStream input = new ObjectInputStream(socketClient.getInputStream());
			listener = new ServerListener(input, this);
			listener.start();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(frmHospitalLogin, "Server Unreachable", "ERROR", 0);
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				LoginWindow window = new LoginWindow();
				window.frmHospitalLogin.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmHospitalLogin = new JFrame();
		frmHospitalLogin.setTitle("Hospital Login");
		frmHospitalLogin.setBounds(100, 100, 450, 300);
		frmHospitalLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(frmHospitalLogin.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(
				Alignment.LEADING,
				groupLayout.createSequentialGroup().addGap(93)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 240, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(101, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 239, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(12, Short.MAX_VALUE)));
		panel.setLayout(new FormLayout(new ColumnSpec[]{FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),},
				new RowSpec[]{FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,}));

		JLabel lblUsername = new JLabel("Username:");
		panel.add(lblUsername, "6, 8, right, default");

		usernameText = new JTextField();
		panel.add(usernameText, "8, 8, fill, default");
		usernameText.setColumns(10);

		JLabel lblPassword = new JLabel("Password:");
		panel.add(lblPassword, "6, 10, right, default");

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String username = usernameText.getText();
					String password = new String(passwordField.getPassword());
					output.writeObject("login\n" + username + "\n" + password);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(frmHospitalLogin, "Connection Error", "ERROR", 0);
				}
			}
		});

		passwordField = new JPasswordField();
		panel.add(passwordField, "8, 10, fill, default");
		panel.add(btnLogin, "8, 12");
		frmHospitalLogin.getContentPane().setLayout(groupLayout);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg == null) {
			JOptionPane.showMessageDialog(frmHospitalLogin, "Invalid Credentials");
		} else {
			if (arg instanceof Doctor) {
				try {
					Doctor doctor = (Doctor) arg;
					DoctorWindow w = new DoctorWindow(socketClient, output, doctor);
					listener.setView(w);
					frmHospitalLogin.dispose();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(frmHospitalLogin, "Connection Lost", "ERROE", 0);
				}
			} else {
				if (arg instanceof Staff) {
					Staff staff = (Staff) arg;
					if (staff.getRole() == Staff.Type.ADMIN) {
						AdminWindow w = new AdminWindow(socketClient, output, staff.getUsername());
						listener.setView(w);
						frmHospitalLogin.dispose();

					} else {
						gui.SecretaryWindow w = new gui.SecretaryWindow(socketClient, output, staff.getUsername());
						listener.setView(w);
						frmHospitalLogin.dispose();
					}
				}
			}
		}

	}
}
