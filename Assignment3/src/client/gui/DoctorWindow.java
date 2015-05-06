package client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import client.listener.DoctorNotification;
import client.model.Doctor;
import client.model.Patient;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.JTextArea;

public class DoctorWindow implements Observer {

	private JFrame frmHospitalDoctor;
	private JTabbedPane tabbedPane;
	private JTable table;
	private DefaultTableModel tableModel;
	private JLabel lblPnc;
	private JLabel lblName;
	private JLabel lblBirthdate;
	private JLabel lblAddress;
	@SuppressWarnings("unused")
	private Socket socket;
	private ObjectOutputStream output;
	private Doctor doctor;
	private JTextArea textArea;



	
	public DoctorWindow(Socket socketClient, ObjectOutputStream output,Doctor doctor) throws IOException {
		socket = socketClient;
		this.output = output;
		this.doctor= doctor;
		tableModel = new DefaultTableModel(new Object[7][7], new String[] { "ID", "Doctor", "Patient", "Time",
				"Observations", "Diagnosis", "Prescription" }) {
			/**
				 * 
				 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, false, false, true, true, true };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}

		};
		initialize();
		this.output.writeObject("viewConsults\ndoctor\n"+this.doctor.getUsername()+"\n");
		frmHospitalDoctor.setVisible(true);
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmHospitalDoctor = new JFrame();
		frmHospitalDoctor.setTitle("Hospital Doctor");
		frmHospitalDoctor.setBounds(100, 100, 600, 300);
		frmHospitalDoctor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frmHospitalDoctor.setJMenuBar(menuBar);

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
		frmHospitalDoctor.setContentPane(tabbedPane);
		JPanel patientPanel = new JPanel();
		JPanel consultationsPanel = new JPanel();
		tabbedPane.addTab("Patient", patientPanel);
		
		JPanel panel_1 = new JPanel();
		
		textArea = new JTextArea();
		GroupLayout gl_patientPanel = new GroupLayout(patientPanel);
		gl_patientPanel.setHorizontalGroup(
			gl_patientPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_patientPanel.createSequentialGroup()
					.addGap(2)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 225, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(textArea, GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_patientPanel.setVerticalGroup(
			gl_patientPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_patientPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_patientPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
						.addComponent(textArea, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		JButton btnLoadCurrentPatient = new JButton("Load Latest Patient");
		btnLoadCurrentPatient.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					output.writeObject("getCurrentPatient\n"+doctor.getUsername()+"\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
		
		lblPnc = new JLabel("PNC:");
		
				lblName = new JLabel("Name:");
		
				lblBirthdate = new JLabel("Birthdate:");
		
				lblAddress = new JLabel("Address:");
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addComponent(btnLoadCurrentPatient)
					.addGap(100))
				.addComponent(lblPnc, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
				.addComponent(lblName, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
				.addComponent(lblBirthdate, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
				.addComponent(lblAddress, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addComponent(btnLoadCurrentPatient)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblPnc)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblName)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblBirthdate)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblAddress, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(47, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		patientPanel.setLayout(gl_patientPanel);
		tabbedPane.addTab("Consultations", consultationsPanel);

		JPanel panel = new JPanel();

		JScrollPane scrollPane = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(consultationsPanel);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(
				groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				Alignment.TRAILING,
				groupLayout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.TRAILING)
										.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 194,
												Short.MAX_VALUE)
										.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 194,
												Short.MAX_VALUE)).addGap(8)));

		table = new JTable();
		table.setModel(tableModel);
		scrollPane.setViewportView(table);
		panel.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int row = table.getSelectedRow();
					int id=(int) table.getValueAt(row, 0);
					String prescription = (String) table.getValueAt(row, 6);
					String diagnosis= (String) table.getValueAt(row, 5);
					String observations= (String) table.getValueAt(row, 4);
					
					output.writeObject("updateConsult\n"+id+"\n"+prescription+"\n"+diagnosis+"\n"+observations+"\n");
					output.writeObject("getCurrentPatient\n"+doctor.getUsername()+"\n");
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(frmHospitalDoctor, "Unable to perform update", "ERROR", 0);
				}
			}
		});
		panel.add(btnUpdate, "2, 2");
		consultationsPanel.setLayout(groupLayout);
		// tabbedPane.addTab("New tab", null, scrollPane, null);

	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1!=null)
		if(arg1 instanceof Object[][]){
			updateTable((Object[][]) arg1);
		}else{
			if(arg1 instanceof Patient){
				updateLabels((Patient)arg1);
			}else{
				if(arg1 instanceof String){
					String value = (String)arg1;
					switch(value){
					case "Reload Consults":
						try {
							output.writeObject("viewConsults\ndoctor\n"+this.doctor.getUsername()+"\n");
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;
					
					}
				}else{
					if(arg1 instanceof DoctorNotification){
						DoctorNotification dn= (DoctorNotification)arg1;
						if(dn.getUsername().equals(doctor.getUsername())){
							JOptionPane.showMessageDialog(frmHospitalDoctor, "Patient has arrived for consultation");
						}
					}
				}
			}
		}

	}

	private void updateLabels(Patient p) {
		
		lblPnc.setText("PNC: "+p.getPnc());
		lblName.setText("Name: "+p.getName());
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		lblBirthdate.setText("Birthdate: "+df.format(p.getBirtdate()));
		lblAddress.setText("Address: "+p.getAddress());
		textArea.setText(p.getMedicalHistory());
		
		
		
	}

	private void updateTable(Object[][] arg1) {
		tableModel = new DefaultTableModel(arg1, new String[] { "ID", "Doctor", "Patient", "Time",
				"Observations", "Diagnosis", "Prescription" }) {
			/**
				 * 
				 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, false, false, true, true, true };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}

		};
		table.setModel(tableModel);
		
	}
}
