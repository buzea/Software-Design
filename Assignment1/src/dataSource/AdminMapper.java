package dataSource;

import java.sql.ResultSet;
import java.sql.SQLException;

import domain.Admin;

public class AdminMapper extends Mapper {

	public AdminMapper() {
		super();
	}

	public Admin login(String username, String password)
			throws DataSourceException {
		String sql = "SELECT * FROM admin where username = '" + username + "'";
		try {
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				String retrieved = rs.getString("password");
				if (retrieved.equals("password"))
					;
				return new Admin(rs.getInt("idadmin"), password, username);

			}
		} catch (SQLException e) {
			throw new DataSourceException();
		}
		return null;
	}

}
