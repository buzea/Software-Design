package server.mappers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import client.model.Patient;

public class PatientMapper extends Mapper {
	private static PatientMapper instance;
	private Map<Integer, Patient> buffer;

	private PatientMapper() throws DataSourceException {
		super();
		buffer = new HashMap<Integer, Patient>();

	}

	public static PatientMapper getInstance() throws DataSourceException {
		if (instance == null) {
			instance = new PatientMapper();
		}
		return instance;

	}

	public void create(Patient patient) throws DataSourceException {
		String sql = "INSERT INTO `hospital`.`patient` (`PNC`, `name`, `birtdate`, `address`) VALUES (?, ?, ?, ?);";

		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, patient.getPnc());
			statement.setString(2, patient.getName());
			java.sql.Date sqlDate = new java.sql.Date(patient.getBirtdate().getTime());
			statement.setDate(3, sqlDate);
			statement.setString(4, patient.getAddress());
			statement.executeUpdate();
			buffer.put(patient.getPnc(), patient);

		} catch (SQLException e) {

			throw new DataSourceException(e);
		}
	}

	public void update(Patient patient) throws DataSourceException {
		String sql = "UPDATE `hospital`.`patient` SET `name`=?, `birtdate`=?, `address`=? WHERE `PNC`=?;";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(4, patient.getPnc());
			statement.setString(1, patient.getName());
			java.sql.Date sqlDate = new java.sql.Date(patient.getBirtdate().getTime());
			statement.setDate(2, sqlDate);
			statement.setString(3, patient.getAddress());
			statement.executeUpdate();
			buffer.put(patient.getPnc(), patient);

		} catch (SQLException e) {

			throw new DataSourceException(e);
		}
	}

	public void delete(int id) throws DataSourceException {
		String sql = "DELETE FROM `hospital`.`patient` WHERE `PNC`=?;";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			statement.executeUpdate();
			buffer.remove(id);

		} catch (SQLException e) {

			throw new DataSourceException(e);
		}
	}

	public List<Patient> getAll() throws DataSourceException {
		String sql = "Select * from patient";
		List<Patient> patients = new LinkedList<Patient>();
		try {
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt(1);
				if (buffer.containsKey(id)) {
					patients.add(buffer.get(id));
				} else {
					Patient patient = new Patient();
					patient.setPnc(id);
					patient.setName(rs.getString(2));
					patient.setBirtdate(rs.getDate(3));
					patient.setAddress(rs.getString(4));
					patients.add(patient);
					buffer.put(patient.getPnc(), patient);
				}
			}
		} catch (SQLException e) {
			throw new DataSourceException(e);
		}
		return patients;
	}

	public Patient getPatient(int id) throws DataSourceException {
		if (buffer.containsKey(id)) {
			return buffer.get(id);
		} else {
			String sql = "Select * from patient where PNC=" + id;
			try {
				ResultSet rs = stmt.executeQuery(sql);
				if (rs.next()) {
					Patient patient = new Patient();
					patient.setPnc(rs.getInt(1));
					patient.setName(rs.getString(2));
					patient.setBirtdate(rs.getDate(3));
					patient.setAddress(rs.getString(4));
					return patient;
				}
			} catch (SQLException e) {
				throw new DataSourceException(e);
			}
		}
		return null;
	}
}
