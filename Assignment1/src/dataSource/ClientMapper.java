package dataSource;

import domain.Client;

import java.sql.*;

public class ClientMapper extends Mapper {

	public ClientMapper() {
		super();
	}

	public void create(Client client) throws DataSourceException {
		String insertTableSQL = "INSERT INTO `bank`.`client`"
				+ " (`idClient`, `username`, `password`, `firstName`, `lastName`, `address`, `phone`, `email`)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
		try{
		PreparedStatement preparedStatement = conn
				.prepareStatement(insertTableSQL);
		preparedStatement.setInt(1, client.getIdClient());
		preparedStatement.setString(2, client.getUsername());
		preparedStatement.setString(3, client.getPassword());
		preparedStatement.setString(4, client.getFirstName());
		preparedStatement.setString(5, client.getLastName());
		preparedStatement.setString(6, client.getAddress());
		preparedStatement.setString(7, client.getPhone());
		preparedStatement.setString(8, client.getEmail());

		// execute insert SQL stetement
		preparedStatement.executeUpdate();
		}catch(SQLException e){
			throw new DataSourceException();
		}
	}

	public Client login(String username, String password) throws DataSourceException {
		
		Client client = null;
		try{
		String sql = "SELECT * FROM Client where username = '" + username + "'";
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next()) {
			String retrieved = rs.getString("password");
			if (password.equals(retrieved))
				client = new Client(rs.getInt("idClient"),
						rs.getString("address"), rs.getString("email"),
						rs.getString("firstName"), rs.getString("lastName"),
						rs.getString("password"), rs.getString("phone"),
						rs.getString("username"));

		}
		}catch(SQLException e){
			throw new DataSourceException();
		}
		return client;
	}

	public void update(Client client) throws DataSourceException {
		String sql = "UPDATE client SET `username`=?, `password`=?, `firstName`=?, `lastName`=?, `address`=?, `phone`=?, `email`=? WHERE `idClient`=?;";
		
		try{
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setString(1, client.getUsername());
		preparedStatement.setString(2, client.getPassword());
		preparedStatement.setString(3, client.getFirstName());
		preparedStatement.setString(4, client.getLastName());
		preparedStatement.setString(5, client.getAddress());
		preparedStatement.setString(6, client.getPhone());
		preparedStatement.setString(7, client.getEmail());
		preparedStatement.setInt(8, client.getIdClient());

		preparedStatement.executeUpdate();
		}catch(SQLException e){
			throw new DataSourceException();
		}

	}

	public void delete(Client client) throws DataSourceException {
		String sql = "DELETE FROM client WHERE `idClient`=?;";
		try{
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, client.getIdClient());
		preparedStatement.executeUpdate();
		}catch(SQLException e){
			throw new DataSourceException();
		}
	}

	public Client findById(int id) throws DataSourceException {
		Client client = null;
		String sql = "SELECT * FROM Client where idClient = '" + id + "'";
		try{
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next()) {

			client = new Client(rs.getInt("idClient"), rs.getString("address"),
					rs.getString("email"), rs.getString("firstName"),
					rs.getString("lastName"), rs.getString("password"),
					rs.getString("phone"), rs.getString("username"));

		}
		}catch(SQLException e){
			throw new DataSourceException();
		}
		return client;

	}
	
	public Client findByUsername(String username) throws DataSourceException {
		Client client = null;
		String sql = "SELECT * FROM Client where username = '" + username + "'";
		try{
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next()) {

			client = new Client(rs.getInt("idClient"), rs.getString("address"),
					rs.getString("email"), rs.getString("firstName"),
					rs.getString("lastName"), rs.getString("password"),
					rs.getString("phone"), rs.getString("username"));

		}
		}catch(SQLException e){
			throw new DataSourceException();
		}
		return client;

	}

	public Client findByEmail(String email) throws DataSourceException {
		Client client = null;
		String sql = "SELECT * FROM Client where email = '" + email + "'";
		try{
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next()) {

			client = new Client(rs.getInt("idClient"), rs.getString("address"),
					rs.getString("email"), rs.getString("firstName"),
					rs.getString("lastName"), rs.getString("password"),
					rs.getString("phone"), rs.getString("username"));

		}
		}catch(SQLException e){
			throw new DataSourceException();
		}
		return client;
	}
	
	
	

}
