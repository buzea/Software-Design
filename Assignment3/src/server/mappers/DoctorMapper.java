package server.mappers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.model.Doctor;

public class DoctorMapper extends Mapper {
	private static DoctorMapper instance;
	private Map<Integer, Doctor> buffer;

	private DoctorMapper() throws DataSourceException {
		super();
		buffer = new HashMap<Integer, Doctor>();
	}

	public static DoctorMapper getInstance() throws DataSourceException {
		if (instance == null) {
			instance = new DoctorMapper();
		}
		return instance;

	}

	public void create(Doctor doctor) throws DataSourceException {
		String sql = "INSERT INTO `hospital`.`doctor` (`username`, `password`, `name`, `rating`) VALUES (?, ?, ?, ?);";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, doctor.getUsername());
			statement.setString(2, doctor.getPassword());
			statement.setString(3, doctor.getName());
			statement.setInt(4, doctor.getRating());
			statement.executeUpdate();
			// can't add to buffer due to missing ID
		} catch (SQLException e) {
			throw new DataSourceException(e);
		}

	}

	public void update(Doctor doctor) throws DataSourceException {
		String sql = "UPDATE `hospital`.`doctor` SET `username`=?, `password`=?, `name`=?, `rating`=? WHERE `idaccount`=?;";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, doctor.getUsername());
			statement.setString(2, doctor.getPassword());
			statement.setString(3, doctor.getName());
			statement.setInt(4, doctor.getRating());
			statement.setInt(5, doctor.getIdaccount());
			statement.executeUpdate();
			// parameter object should already be referenced by the buffer
			// already
		} catch (SQLException e) {
			throw new DataSourceException(e);
		}

	}

	public void delete(int id) throws DataSourceException {
		String sql = "DELETE FROM `hospital`.`doctor` WHERE `idaccount`=?;";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			statement.executeUpdate();
			buffer.remove(id);
		} catch (SQLException e) {
			throw new DataSourceException(e);
		}
	}

	public Doctor getDoctor(int id) throws DataSourceException {
		if (buffer.containsKey(id)) {
			return buffer.get(id);
		} else {
			String sql = "Select * from doctor where idaccount=" + id;
			try {
				ResultSet rs = stmt.executeQuery(sql);
				if (rs.next()) {
					Doctor doctor = new Doctor();
					doctor.setIdaccount(rs.getInt("idaccount"));
					doctor.setName(rs.getString("name"));
					doctor.setPassword(rs.getString("password"));
					doctor.setRating(rs.getInt("rating"));
					doctor.setUsername(rs.getString("username"));
					return doctor;

				}
			} catch (SQLException e) {
				throw new DataSourceException(e);
			}

			return null;
		}
	}
	public Doctor getDoctor(String username) throws DataSourceException {
		
			String sql = "Select * from doctor where username=?";
			try {
				PreparedStatement statement=conn.prepareStatement(sql);
				statement.setString(1, username);
				ResultSet rs = statement.executeQuery();
				if (rs.next()) {
					int id=rs.getInt("idaccount");
					if(buffer.containsKey(id)){
						return buffer.get(id);
					}
					Doctor doctor = new Doctor();
					doctor.setIdaccount(id);
					doctor.setName(rs.getString("name"));
					doctor.setPassword(rs.getString("password"));
					doctor.setRating(rs.getInt("rating"));
					doctor.setUsername(rs.getString("username"));
					return doctor;

				}
			} catch (SQLException e) {
				throw new DataSourceException(e);
			}

			return null;
		
	}
	
	public List<Doctor> getAll() throws DataSourceException {
		List<Doctor> doctors = new ArrayList<>();
		String sql = "Select * from doctor ";
		try {
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("idaccount");
				if (buffer.containsKey(id)) {
					doctors.add(buffer.get(id));
				} else {
					Doctor doctor = new Doctor();
					doctor.setIdaccount(id);
					doctor.setName(rs.getString("name"));
					doctor.setPassword(rs.getString("password"));
					doctor.setRating(rs.getInt("rating"));
					doctor.setUsername(rs.getString("username"));
					doctors.add(doctor);
					buffer.put(id, doctor);
				}

			}
		} catch (SQLException e) {
			throw new DataSourceException(e);
		}

		return doctors;
	}

}
