package client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import client.model.Doctor;
import client.model.Patient;
import client.model.Staff;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class AdminWindow implements Observer {

	private JFrame frmHospitalAdmin;
	private JTabbedPane tabbedPane;
	private JTextField patientNameTextField;
	private JTextField addressTextField;
	private JTextField nameTextField;
	private JLabel lblName_1;
	private JTextField passwordTextField;
	private JTextField usernameTextField;
	@SuppressWarnings("unused")
	private Socket socket;
	private ObjectOutputStream output;
	private String username;
	private JSpinner spinnerBirthdate;
	private JSpinner spinnerPNC;
	private SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	private JLabel lblIdAccount;
	private JComboBox<String> comboBox;

	/**
	 * Create the application.
	 * 
	 * @param string
	 * @param output
	 * @param socketClient
	 */
	public AdminWindow(Socket socketClient, ObjectOutputStream output, String username) {

		this.socket = socketClient;
		this.output = output;
		this.username = username;
		initialize();
		frmHospitalAdmin.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmHospitalAdmin = new JFrame();
		frmHospitalAdmin.setTitle("Hospital Admin");
		frmHospitalAdmin.setBounds(100, 100, 600, 300);
		frmHospitalAdmin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frmHospitalAdmin.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);

		tabbedPane = new JTabbedPane();
		frmHospitalAdmin.setContentPane(tabbedPane);
		JPanel patientPanel = new JPanel();
		JPanel accountPanel = new JPanel();
		tabbedPane.addTab("Patient", patientPanel);

		JPanel panel = new JPanel();

		JPanel panel_2 = new JPanel();
		GroupLayout gl_patientPanel = new GroupLayout(patientPanel);
		gl_patientPanel.setHorizontalGroup(gl_patientPanel.createParallelGroup(Alignment.TRAILING).addGroup(
				gl_patientPanel.createSequentialGroup().addContainerGap()
						.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE).addGap(14)));
		gl_patientPanel.setVerticalGroup(gl_patientPanel.createParallelGroup(Alignment.TRAILING).addGroup(
				gl_patientPanel
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_patientPanel.createParallelGroup(Alignment.TRAILING)
										.addComponent(panel, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
										.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE))
						.addGap(6)));
		panel_2.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

		JLabel lblPnc = new JLabel("PNC:");
		panel_2.add(lblPnc, "4, 2, right, default");

		spinnerPNC = new JSpinner();
		spinnerPNC.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		panel_2.add(spinnerPNC, "6, 2");

		JLabel lblName = new JLabel("Name:");
		panel_2.add(lblName, "4, 4, right, default");

		patientNameTextField = new JTextField();
		patientNameTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_2.add(patientNameTextField, "6, 4, fill, default");
		patientNameTextField.setColumns(10);

		JLabel lblBirthdate = new JLabel("Birthdate:");
		panel_2.add(lblBirthdate, "4, 6, right, default");

		spinnerBirthdate = new JSpinner();
		Date datenow = Calendar.getInstance().getTime();
		SpinnerDateModel smb2 = new SpinnerDateModel(datenow, null, null, Calendar.HOUR_OF_DAY);
		spinnerBirthdate.setModel(smb2);
		JSpinner.DateEditor de_spinnerBirthdate = new JSpinner.DateEditor(spinnerBirthdate, "dd-MM-yyyy");
		de_spinnerBirthdate.getTextField().setHorizontalAlignment(JTextField.RIGHT);
		spinnerBirthdate.setEditor(de_spinnerBirthdate);
		panel_2.add(spinnerBirthdate, "6, 6");

		JLabel lblAddress = new JLabel("Address:");
		panel_2.add(lblAddress, "4, 8, right, default");

		addressTextField = new JTextField();
		addressTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_2.add(addressTextField, "6, 8, fill, default");
		addressTextField.setColumns(10);
		panel.setLayout(new FormLayout(
				new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, }, new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, }));

		JButton btnCreate = new JButton("Create Patient");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					int pnc = (int) spinnerPNC.getValue();
					String name = patientNameTextField.getText();
					Date date = (Date) spinnerBirthdate.getValue();
					String birthdate = df.format(date);
					String address = addressTextField.getText();
					output.writeObject("createPatient\n" + username + "\n" + pnc + "\n" + name + "\n" + birthdate
							+ "\n" + address + "\n");
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(frmHospitalAdmin, "Invalid Data entered!", "ERROR", 0);

				}
			}
		});
		panel.add(btnCreate, "2, 2, fill, default");

		JButton btnLoadPatientUsing = new JButton("Load Patient using PNC");
		btnLoadPatientUsing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int pnc = (int) spinnerPNC.getValue();
					output.writeObject("loadPatient\n" + pnc + "\n");
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(frmHospitalAdmin, "Invalid Data entered!", "ERROR", 0);

				}
			}
		});
		panel.add(btnLoadPatientUsing, "2, 4, fill, default");

		JButton btnUpdatePatient = new JButton("Update Patient");
		btnUpdatePatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int pnc = (int) spinnerPNC.getValue();
					String name = patientNameTextField.getText();
					Date date = (Date) spinnerBirthdate.getValue();
					String birthdate = df.format(date);
					String address = addressTextField.getText();
					output.writeObject("updatePatient\n" + username + "\n" + pnc + "\n" + name + "\n" + birthdate
							+ "\n" + address + "\n");
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(frmHospitalAdmin, "Invalid Data entered!", "ERROR", 0);

				}
			}
		});
		panel.add(btnUpdatePatient, "2, 6, fill, default");

		JButton btnDeletePatient = new JButton("Delete Patient");
		btnDeletePatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int pnc = (int) spinnerPNC.getValue();
					output.writeObject("deletePatient\n" + pnc + "\n");
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(frmHospitalAdmin, "Invalid PNC entered!", "ERROR", 0);

				}

			}
		});
		panel.add(btnDeletePatient, "2, 8, fill, default");
		patientPanel.setLayout(gl_patientPanel);
		tabbedPane.addTab("Account", accountPanel);

		JPanel panel_1 = new JPanel();

		JPanel panel_3 = new JPanel();
		GroupLayout gl_accountPanel = new GroupLayout(accountPanel);
		gl_accountPanel.setHorizontalGroup(gl_accountPanel.createParallelGroup(Alignment.TRAILING).addGroup(
				gl_accountPanel.createSequentialGroup().addContainerGap()
						.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE).addGap(10)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE).addContainerGap()));
		gl_accountPanel.setVerticalGroup(gl_accountPanel.createParallelGroup(Alignment.TRAILING).addGroup(
				gl_accountPanel
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_accountPanel.createParallelGroup(Alignment.TRAILING)
										.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
										.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE))
						.addGap(9)));
		panel_3.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC, }));

		JLabel lblId = new JLabel("ID:");
		panel_3.add(lblId, "4, 2, right, default");

		lblIdAccount = new JLabel("ID:");
		lblIdAccount.setVisible(false);
		panel_3.add(lblIdAccount, "6, 2, center, default");

		JLabel lblUsername = new JLabel("Username:");
		panel_3.add(lblUsername, "4, 4, right, default");

		usernameTextField = new JTextField();
		panel_3.add(usernameTextField, "6, 4, fill, default");
		usernameTextField.setColumns(10);

		JLabel lblPassword = new JLabel("Password:");
		panel_3.add(lblPassword, "4, 6, right, default");

		passwordTextField = new JTextField();
		panel_3.add(passwordTextField, "6, 6, fill, default");
		passwordTextField.setColumns(10);

		JLabel lblType = new JLabel("Type:");
		panel_3.add(lblType, "4, 8, right, default");

		comboBox = new JComboBox<String>();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (comboBox.getSelectedIndex() == 2) {
					lblName_1.setVisible(true);
					nameTextField.setVisible(true);

				} else {
					lblName_1.setVisible(false);
					nameTextField.setVisible(false);

				}
			}
		});
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Secretary", "Admin", "Doctor" }));
		panel_3.add(comboBox, "6, 8, fill, default");

		lblName_1 = new JLabel("Name:");
		panel_3.add(lblName_1, "4, 10, right, default");
		lblName_1.setVisible(false);

		nameTextField = new JTextField();
		panel_3.add(nameTextField, "6, 10, fill, default");
		nameTextField.setColumns(10);
		nameTextField.setVisible(false);
		panel_1.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC, }, new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC, }));

		JButton btnCreateAccount = new JButton("Create Account");
		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String username = usernameTextField.getText();
					String password = passwordTextField.getText();
					String type = (String) comboBox.getSelectedItem();
					if (nameTextField.isVisible()) {
						String name = nameTextField.getText();
						output.writeObject("createDoctor\n" + username + "\n" + password + "\n" + name + "\n");
					} else {
						output.writeObject("createStaff\n" + username + "\n" + password + "\n" + type + "\n");
					}

				} catch (Exception e1) {
					JOptionPane.showMessageDialog(frmHospitalAdmin, "Invalid Data entered!", "ERROR", 0);

				}
			}
		});
		panel_1.add(btnCreateAccount, "2, 2");

		JButton btnFindAccountUsing_1 = new JButton("Find Account by username");
		btnFindAccountUsing_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = usernameTextField.getText();
				try {
					output.writeObject("findAccount\n" + username + "\n");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		panel_1.add(btnFindAccountUsing_1, "2, 4");

		JButton btnUpdateAccount = new JButton("Update Account");
		btnUpdateAccount.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					String id = lblIdAccount.getText();
					String username = usernameTextField.getText();
					String password = passwordTextField.getText();
					String type = (String) comboBox.getSelectedItem();
					if (nameTextField.isVisible()) {
						String name = nameTextField.getText();
						output.writeObject("updateDoctor\n" + id + "\n" + username + "\n" + password + "\n" + name
								+ "\n");
					} else {
						output.writeObject("updateStaff\n" + id + "\n" + username + "\n" + password + "\n" + type
								+ "\n");
					}

				} catch (Exception e1) {
					JOptionPane.showMessageDialog(frmHospitalAdmin, "Invalid Data entered!", "ERROR", 0);

				}
			}
		});
		panel_1.add(btnUpdateAccount, "2, 6");

		JButton btnDeleteAccount = new JButton("Delete Account");
		btnDeleteAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					String id = lblIdAccount.getText();
					int type = comboBox.getSelectedIndex();
						output.writeObject("deleteAccount\n" + id + "\n" + type + "\n");
					

				} catch (Exception e1) {
					JOptionPane.showMessageDialog(frmHospitalAdmin, "Invalid Data entered!", "ERROR", 0);

				}
			}
		});
		panel_1.add(btnDeleteAccount, "2, 8");
		accountPanel.setLayout(gl_accountPanel);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 != null) {
			if (arg1 instanceof Patient) {
				updateFields((Patient) arg1);
			}
			if (arg1 instanceof Doctor) {
				JOptionPane.showMessageDialog(frmHospitalAdmin, "Success");
				updateFields((Doctor) arg1);
			}
			if (arg1 instanceof Staff) {
				JOptionPane.showMessageDialog(frmHospitalAdmin, "Success");
				updateFields((Staff) arg1);
			}
			if (arg1 instanceof String) {
				String message = (String) arg1;
				switch (message) {
				case "PatientCreated":
					JOptionPane.showMessageDialog(frmHospitalAdmin, "Patient Created");
					break;
				case "PatientNotCreated":
					JOptionPane.showMessageDialog(frmHospitalAdmin, "Patient NOT Created", "ERROR", 0);
					break;
				case "PatientUpdated":
					JOptionPane.showMessageDialog(frmHospitalAdmin, "Patient Updated");
					break;
				case "PatientNotUpdated":
					JOptionPane.showMessageDialog(frmHospitalAdmin, "Patient NOT Updated", "ERROR", 0);
					break;
				case "PatientDeleted":
					JOptionPane.showMessageDialog(frmHospitalAdmin, "Patient Deleted");
					clearFields();
					break;
				case "PatientNotDeleted":
					JOptionPane.showMessageDialog(frmHospitalAdmin, "Patient NOT Deleted", "ERROR", 0);
					break;
				case "NotCreated":
					JOptionPane.showMessageDialog(frmHospitalAdmin, "Account NOT Created", "ERROR", 0);
					break;
				case "accountUpdated":
					JOptionPane.showMessageDialog(frmHospitalAdmin, "Account Updated");
					break;
				case "accountNotUpdated":
					JOptionPane.showMessageDialog(frmHospitalAdmin, "Account NOT Updated", "ERROR", 0);
					break;
				case "accountDeleted":
					JOptionPane.showMessageDialog(frmHospitalAdmin, "Account Deleted");
					clearFields();
					break;
				case "accountNotDeleted":
					JOptionPane.showMessageDialog(frmHospitalAdmin, "Account NOT Deleted", "ERROR", 0);
					break;
				case "NotFound":
					JOptionPane.showMessageDialog(frmHospitalAdmin, "Account NOT Found");
					break;

				}
			}
		}

	}

	private void updateFields(Staff staff) {
		lblIdAccount.setText("" + staff.getId());
		usernameTextField.setText(staff.getUsername());
		passwordTextField.setText(staff.getPassword());
		if (staff.getRole().equals(Staff.Type.ADMIN)) {
			comboBox.setSelectedIndex(1);
		} else {
			comboBox.setSelectedIndex(0);
		}
		lblIdAccount.setVisible(true);

	}

	private void updateFields(Doctor doc) {
		lblIdAccount.setText("" + doc.getIdaccount());
		usernameTextField.setText(doc.getUsername());
		passwordTextField.setText(doc.getPassword());
		nameTextField.setText(doc.getName());
		comboBox.setSelectedIndex(2);
		lblIdAccount.setVisible(true);

	}

	private void clearFields() {
		spinnerPNC.setValue(1);
		patientNameTextField.setText("");
		spinnerBirthdate.setValue(new Date());
		addressTextField.setText("");
		lblIdAccount.setText("");
		usernameTextField.setText("");
		passwordTextField.setText("");
		nameTextField.setText("");
		comboBox.setSelectedIndex(0);
		lblIdAccount.setVisible(false);
		

	}

	private void updateFields(Patient patient) {
		spinnerPNC.setValue(patient.getPnc());
		patientNameTextField.setText(patient.getName());
		spinnerBirthdate.setValue(patient.getBirtdate());
		addressTextField.setText(patient.getAddress());

	}
}
