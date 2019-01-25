package gui;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import model.Doctor;
import model.Patient;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;

public class SecretaryWindow implements Observer {

	private JFrame             frmHospitalSecretary;
	private JTextField         nameTextField;
	private JTextField         addressTextField;
	private JTable             table;
	private DefaultTableModel  tableModel;
	private ObjectOutputStream output;
	private String             username;
	private JSpinner           spinnerPNC;
	private JSpinner           spinnerBirthdate;
	private SimpleDateFormat   df = new SimpleDateFormat("dd-MM-yyyy");
	private JComboBox<Patient> patientComboBox;
	private JComboBox<Doctor>  doctorComboBox;
	private JSpinner           spinnerTime;

	/**
	 * Create the application.
	 */
	public SecretaryWindow(Socket socketClient, ObjectOutputStream output, String username) {

		Socket socket = socketClient;
		this.output = output;
		this.username = username;
		tableModel = new DefaultTableModel(new Object[7][7], new String[]{"ID", "Doctor", "Patient", "Time",
				"Observations", "Diagnosis", "Prescription"}) {
			/**
			 *
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[]{false, false, false, false, true, true, true};

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		initialize();
		try {
			this.output.writeObject("viewConsults\nsecretary\n" + this.username + "\n");
			this.output.writeObject("getPatients\n");
			this.output.writeObject("getDoctors\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		frmHospitalSecretary.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmHospitalSecretary = new JFrame();
		frmHospitalSecretary.setTitle("Hospital Secretary");
		frmHospitalSecretary.setBounds(100, 100, 600, 300);
		frmHospitalSecretary.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frmHospitalSecretary.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		JTabbedPane tabbedPane = new JTabbedPane();
		frmHospitalSecretary.setContentPane(tabbedPane);
		JPanel patientsPanel = new JPanel();
		JPanel consultationsPanel = new JPanel();
		JPanel addConsultPanel = new JPanel();

		tabbedPane.addTab("Patients", patientsPanel);
		tabbedPane.addTab("Consultations", consultationsPanel);

		JScrollPane scrollPane = new JScrollPane();

		JPanel panel = new JPanel();
		GroupLayout gl_consultationsPanel = new GroupLayout(consultationsPanel);
		gl_consultationsPanel.setHorizontalGroup(
				gl_consultationsPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_consultationsPanel.createSequentialGroup()
								.addContainerGap()
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(panel, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
								.addGap(7))
		);
		gl_consultationsPanel.setVerticalGroup(
				gl_consultationsPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_consultationsPanel.createSequentialGroup()
								.addContainerGap()
								.addGroup(gl_consultationsPanel.createParallelGroup(Alignment.TRAILING)
										.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE))
								.addGap(0))
		);

		table = new JTable();
		table.setModel(tableModel);
		table.getColumnModel().getColumn(0).setPreferredWidth(35);
		table.getColumnModel().getColumn(0).setMaxWidth(55);
		table.getColumnModel().getColumn(4).setPreferredWidth(125);

		scrollPane.setViewportView(table);
		panel.setLayout(new FormLayout(
				new ColumnSpec[]{FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,}, new RowSpec[]{
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,}));

		JButton btnUpdate = new JButton("Update ");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int row = table.getSelectedRow();
					int id = (int) table.getValueAt(row, 0);
					String prescription = (String) table.getValueAt(row, 6);
					String diagnosis = (String) table.getValueAt(row, 5);
					String observations = (String) table.getValueAt(row, 4);

					output.writeObject("updateConsult\n" + id + "\n" + prescription + "\n" + diagnosis + "\n" + observations + "\n");
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(frmHospitalSecretary, "Unable to perform update", "ERROR", 0);
				}
			}
		});
		panel.add(btnUpdate, "2, 2");

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int row = table.getSelectedRow();
					int id = (int) table.getValueAt(row, 0);

					output.writeObject("deleteConsult\n" + id + "\n");
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(frmHospitalSecretary, "Unable to perform update", "ERROR", 0);
				}
			}
		});
		panel.add(btnDelete, "2, 4");

		JButton btnNotifyDoctor = new JButton("Notify Doctor");
		btnNotifyDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int row = table.getSelectedRow();
					int id = (int) table.getValueAt(row, 0);

					output.writeObject("notifyDoctor\n" + id + "\n");
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(frmHospitalSecretary, "Unable to perform update", "ERROR", 0);
				}


			}
		});
		panel.add(btnNotifyDoctor, "2, 6");
		consultationsPanel.setLayout(gl_consultationsPanel);
		tabbedPane.addTab("Add Consult", addConsultPanel);

		JPanel panel_1 = new JPanel();
		GroupLayout gl_addConsultPanel = new GroupLayout(addConsultPanel);
		gl_addConsultPanel.setHorizontalGroup(gl_addConsultPanel.createParallelGroup(Alignment.LEADING).addGroup(
				Alignment.TRAILING,
				gl_addConsultPanel.createSequentialGroup().addGap(73)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE).addGap(95)));
		gl_addConsultPanel.setVerticalGroup(gl_addConsultPanel.createParallelGroup(Alignment.LEADING).addGroup(
				gl_addConsultPanel.createSequentialGroup().addContainerGap()
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_1.setLayout(new FormLayout(new ColumnSpec[]{FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),}, new RowSpec[]{
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,}));

		JLabel lblPatient = new JLabel("Patient:");
		panel_1.add(lblPatient, "2, 2, right, default");

		patientComboBox = new JComboBox<Patient>();
		panel_1.add(patientComboBox, "6, 2, fill, default");

		JLabel lblDoctor = new JLabel("Doctor:");
		panel_1.add(lblDoctor, "2, 4, right, default");

		doctorComboBox = new JComboBox<Doctor>();
		panel_1.add(doctorComboBox, "6, 4, fill, default");

		JLabel lblTime = new JLabel("Time:");
		panel_1.add(lblTime, "2, 6, right, default");

		spinnerTime = new JSpinner();
		Date datenow = Calendar.getInstance().getTime();
		SpinnerDateModel smb2 = new SpinnerDateModel(datenow, null, null, Calendar.HOUR_OF_DAY);
		spinnerTime.setModel(smb2);
		JSpinner.DateEditor de_spinnerTime = new JSpinner.DateEditor(spinnerTime, "dd-MM-yyyy hh:mm");
		spinnerTime.setEditor(de_spinnerTime);
		panel_1.add(spinnerTime, "6, 6");

		JButton btnAddConsultation = new JButton("Add Consultation");
		btnAddConsultation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Patient p = (Patient) patientComboBox.getSelectedItem();
				Doctor d = (Doctor) doctorComboBox.getSelectedItem();
				Date time = (Date) spinnerTime.getValue();
				SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				try {
					output.writeObject("createConsultation\n" + p.getPnc() + "\n" + d.getIdaccount() + "\n" + dft.format(time) + "\n");
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		});
		panel_1.add(btnAddConsultation, "2, 8");
		addConsultPanel.setLayout(gl_addConsultPanel);

		JPanel panel1 = new JPanel();
		panel1.setToolTipText("Personal Numerical Code");

		JPanel panel2 = new JPanel();
		GroupLayout gl_patientsPanel = new GroupLayout(patientsPanel);
		gl_patientsPanel.setHorizontalGroup(gl_patientsPanel.createParallelGroup(Alignment.LEADING).addGroup(
				Alignment.TRAILING,
				gl_patientsPanel.createSequentialGroup().addContainerGap()
						.addComponent(panel1, GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(panel2, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE).addGap(8)));
		gl_patientsPanel.setVerticalGroup(gl_patientsPanel.createParallelGroup(Alignment.LEADING).addGroup(
				gl_patientsPanel
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_patientsPanel
										.createParallelGroup(Alignment.LEADING)
										.addComponent(panel1, GroupLayout.PREFERRED_SIZE, 218,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(panel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)).addContainerGap()));
		panel2.setLayout(new FormLayout(new ColumnSpec[]{FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,},
				new RowSpec[]{FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,}));

		JButton btnAddPatient = new JButton("Add Patient");
		btnAddPatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int pnc = (int) spinnerPNC.getValue();
					String name = nameTextField.getText();
					Date date = (Date) spinnerBirthdate.getValue();
					String birthdate = df.format(date);
					String address = addressTextField.getText();
					output.writeObject("createPatient\n" + username + "\n" + pnc + "\n" + name + "\n" + birthdate
							+ "\n" + address + "\n");
					output.writeObject("getPatients\n");
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(frmHospitalSecretary, "Invalid Data entered!", "ERROR", 0);

				}

			}
		});
		panel2.add(btnAddPatient, "4, 2");

		JButton btnUpdatePatientInfo = new JButton("Update Patient Info");
		btnUpdatePatientInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int pnc = (int) spinnerPNC.getValue();
					String name = nameTextField.getText();
					Date date = (Date) spinnerBirthdate.getValue();
					String birthdate = df.format(date);
					String address = addressTextField.getText();
					output.writeObject("updatePatient\n" + username + "\n" + pnc + "\n" + name + "\n" + birthdate
							+ "\n" + address + "\n");
					output.writeObject("getPatients\n");
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(frmHospitalSecretary, "Invalid Data entered!", "ERROR", 0);

				}
			}
		});
		panel2.add(btnUpdatePatientInfo, "4, 4");
		panel1.setLayout(new FormLayout(new ColumnSpec[]{FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),},
				new RowSpec[]{FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,}));

		JLabel lblPersonalNumericalCode = new JLabel("PNC:");
		panel1.add(lblPersonalNumericalCode, "2, 2, right, default");

		spinnerPNC = new JSpinner();
		spinnerPNC.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		panel1.add(spinnerPNC, "4, 2");

		JLabel lblName = new JLabel("Name:");
		panel1.add(lblName, "2, 4, right, default");

		nameTextField = new JTextField();
		nameTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		panel1.add(nameTextField, "4, 4, fill, default");
		nameTextField.setColumns(10);

		JLabel lblBirthday = new JLabel("Birthdate:");
		panel1.add(lblBirthday, "2, 6, right, default");

		spinnerBirthdate = new JSpinner();
		panel1.add(spinnerBirthdate, "4, 6, fill, fill");
		SpinnerDateModel smb = new SpinnerDateModel(datenow, null, null, Calendar.HOUR_OF_DAY);
		spinnerBirthdate.setModel(smb);
		JSpinner.DateEditor de_spinnerBirthdate = new JSpinner.DateEditor(spinnerBirthdate, "dd-MM-yyyy");
		de_spinnerBirthdate.getTextField().setHorizontalAlignment(JTextField.RIGHT);
		spinnerBirthdate.setEditor(de_spinnerBirthdate);

		JLabel lblAddress = new JLabel("Address:");
		panel1.add(lblAddress, "2, 8, right, default");

		addressTextField = new JTextField();
		addressTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		panel1.add(addressTextField, "4, 8, fill, default");
		addressTextField.setColumns(10);
		patientsPanel.setLayout(gl_patientsPanel);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable o, Object arg) {
		if (arg != null) {
			if (arg instanceof String) {
				String message = (String) arg;
				switch (message) {
					case "PatientCreated":
						JOptionPane.showMessageDialog(frmHospitalSecretary, "Patient Created");
						break;
					case "PatientNotCreated":
						JOptionPane.showMessageDialog(frmHospitalSecretary, "Patient NOT Created", "ERROR", 0);
						break;
					case "PatientUpdated":
						JOptionPane.showMessageDialog(frmHospitalSecretary, "Patient Updated");
						break;
					case "PatientNotUpdated":
						JOptionPane.showMessageDialog(frmHospitalSecretary, "Patient NOT Updated", "ERROR", 0);
						break;
					case "ConsultDeleted":
						JOptionPane.showMessageDialog(frmHospitalSecretary, "Consult Deleted");
						try {
							this.output.writeObject("viewConsults\nsecretary\n" + this.username + "\n");
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;
					case "ConsultNotDeleted":
						JOptionPane.showMessageDialog(frmHospitalSecretary, "Consult Not Deleted", "ERROR", 0);
						break;

					case "ConsultCreated":
						JOptionPane.showMessageDialog(frmHospitalSecretary, "Consult Created");
						try {
							this.output.writeObject("viewConsults\nsecretary\n" + this.username + "\n");
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;
					case "ConsultNotCreated":
						JOptionPane.showMessageDialog(frmHospitalSecretary, "Consult Not Created", "ERROR", 0);
						break;

				}
			}
			if (arg instanceof Object[][]) {
				updateTable((Object[][]) arg);
			}
			if (arg instanceof List<?>) {
				List<?> list = (List<?>) arg;
				if (list.get(0) instanceof Patient) {
					List<Patient> patients = (List<Patient>) list;
					DefaultComboBoxModel<Patient> dcm = new DefaultComboBoxModel<Patient>();
					for (Patient p : patients) {
						dcm.addElement(p);
					}
					patientComboBox.setModel(dcm);

				}

				if (list.get(0) instanceof Doctor) {
					List<Doctor> doctors = (List<Doctor>) list;
					DefaultComboBoxModel<Doctor> dcm = new DefaultComboBoxModel<Doctor>();
					for (Doctor p : doctors) {
						dcm.addElement(p);
					}
					doctorComboBox.setModel(dcm);

				}


			}
		}

	}

	private void updateTable(Object[][] arg1) {
		tableModel = new DefaultTableModel(arg1, new String[]{"ID", "Doctor", "Patient", "Time", "Observations",
				"Diagnosis", "Prescription"}) {
			/**
			 *
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[]{false, false, false, false, true, true, true};

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}

		};
		table.setModel(tableModel);
		table.getColumnModel().getColumn(0).setPreferredWidth(35);
		table.getColumnModel().getColumn(0).setMaxWidth(55);
		table.getColumnModel().getColumn(4).setPreferredWidth(125);

	}
}
