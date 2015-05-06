package server.mappers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import client.model.Staff;

public class StaffMapper extends Mapper {
	private static StaffMapper instance;
	private Map<Integer, Staff> buffer;

	private StaffMapper() throws DataSourceException {
		super();
		buffer = new HashMap<Integer, Staff>();
	}

	public static StaffMapper getInstance() throws DataSourceException {
		if (instance == null) {
			instance = new StaffMapper();
		}
		return instance;

	}

	public void create(Staff staff) throws DataSourceException {
		String sql = "INSERT INTO `hospital`.`staff` (`username`, `password`, `role`) VALUES (?, ?, ?);";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, staff.getUsername());
			statement.setString(2, staff.getPassword());
			if (staff.getRole() == Staff.Type.ADMIN)
				statement.setString(3, "admin");
			else
				statement.setString(3, "secretary");
			statement.executeUpdate();
			// can't add to buffer yet
		} catch (SQLException e) {
			throw new DataSourceException(e);
		}
	}

	public void update(Staff staff) throws DataSourceException {
		String sql = "UPDATE `hospital`.`staff` SET `username`=?, `password`=?, `role`=? WHERE `id`=?;";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, staff.getUsername());
			statement.setString(2, staff.getPassword());
			if (staff.getRole() == Staff.Type.ADMIN)
				statement.setString(3, "admin");
			else
				statement.setString(3, "secretary");
			statement.setInt(4, staff.getId());
			statement.executeUpdate();
			// can't add to buffer yet
		} catch (SQLException e) {
			throw new DataSourceException(e);
		}
	}

	public void delete(int id) throws DataSourceException {
		String sql = "DELETE FROM `hospital`.`staff` WHERE `id`=?;";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			statement.executeUpdate();
			buffer.remove(id);
		} catch (SQLException e) {
			throw new DataSourceException(e);
		}
	}

	public List<Staff> getAll() throws DataSourceException {
		List<Staff> staffs = new LinkedList<Staff>();
		String sql = "select * from staff";
		try {
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				if (buffer.containsKey(id)) {
					staffs.add(buffer.get(id));
				} else {
					Staff staff = new Staff();
					staff.setId(id);
					staff.setUsername(rs.getString("username"));
					staff.setPassword(rs.getString("password"));
					if (rs.getString("role").equals("admin"))
						staff.setRole(Staff.Type.ADMIN);
					else
						staff.setRole(Staff.Type.SECRETARY);
					staffs.add(staff);
					buffer.put(id, staff);
				}
			}
		} catch (SQLException e) {
			throw new DataSourceException(e);
		}

		return staffs;
	}

	public Staff getStaff(String username) throws DataSourceException {
		String sql = "select * from staff where username= ?";
		try {
			PreparedStatement statemen=conn.prepareStatement(sql);
			statemen.setString(1, username);
			ResultSet rs = statemen.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("id");
				if (buffer.containsKey(id)) {
					return buffer.get(id);
				} else {
					Staff staff = new Staff();
					staff.setId(id);
					staff.setUsername(rs.getString("username"));
					staff.setPassword(rs.getString("password"));
					if (rs.getString("role").equals("admin"))
						staff.setRole(Staff.Type.ADMIN);
					else
						staff.setRole(Staff.Type.SECRETARY);
					return staff;
				}
			}
		} catch (SQLException e) {
			throw new DataSourceException(e);
		}

		return null;
	}

	public Staff getStaff(int id) throws DataSourceException {
		if (buffer.containsKey(id)) {
			return buffer.get(id);
		} else {
			String sql = "SELECT * FROM staff where id = ? ";
			try {
				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setInt(1, id);
				ResultSet rs = statement.executeQuery();
				if (rs.next()) {

					Staff staff = new Staff();
					staff.setId(id);
					staff.setUsername(rs.getString("username"));
					staff.setPassword(rs.getString("password"));
					if (rs.getString("role").equals("admin"))
						staff.setRole(Staff.Type.ADMIN);
					else
						staff.setRole(Staff.Type.SECRETARY);
					return staff;

				}
			} catch (SQLException e) {
				throw new DataSourceException(e);
			}
		}

		return null;
	}
	
	public boolean login(String username,String password){
		try {
			Staff staff = getStaff(username);
			if(staff.getPassword().equals(password))
				return true;
		} catch (DataSourceException e) {
			
		}
		
		return false;
	}
}
