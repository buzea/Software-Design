package dataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import domain.Account;
import domain.Client;

public class AccountMapper extends Mapper {

	public AccountMapper() {
		super();
	}

	public void create(Account account) throws DataSourceException {
		DateFormat dateFormatMDY = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = dateFormatMDY.format(account.getDateCreated());
		java.sql.Date arg = java.sql.Date.valueOf(dateString);

		String sql = "INSERT INTO `account` (`idAccount`, `balance`, `dateCreated`, `type`, `idClient`) VALUES (?, ?, ?, ?, ?);";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, account.getIdAccount());
			preparedStatement.setDouble(2, account.getBalance());
			preparedStatement.setDate(3, arg);
			if (account.getType().equals(Account.SAVINGS))
				preparedStatement.setString(4, Account.SAVINGS);
			else
				preparedStatement.setString(4, Account.CURRENT);
			preparedStatement.setInt(5, account.getClient().getIdClient());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DataSourceException();
		}
	}

	public void update(Account account) throws DataSourceException {
		DateFormat dateFormatMDY = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = dateFormatMDY.format(account.getDateCreated());
		java.sql.Date arg = java.sql.Date.valueOf(dateString);

		String sql = "UPDATE account SET  `balance`=?, `dateCreated`=?, `type`=?, `idClient`=? WHERE `idAccount`=?;";

		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);

			preparedStatement.setDouble(1, account.getBalance());
			preparedStatement.setDate(2, arg);
			if (account.getType().equals(Account.SAVINGS))
				preparedStatement.setString(3, Account.SAVINGS);
			else
				preparedStatement.setString(3, Account.CURRENT);
			preparedStatement.setInt(4, account.getClient().getIdClient());
			preparedStatement.setInt(5, account.getIdAccount());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DataSourceException();
		}

	}

	public void delete(Account account) throws DataSourceException {
		String sql = "DELETE FROM account WHERE `idAccount`=?;";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, account.getIdAccount());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DataSourceException();
		}

	}

	public Account findById(int id) throws DataSourceException {
		Account account = null;
		String sql = "Select * from account where idAccount=" + id + ";";
		try {
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				account = new Account();
				account.setIdAccount(rs.getInt("idAccount"));
				account.setDateCreated(rs.getDate("dateCreated"));
				account.setBalance(rs.getDouble("balance"));
				account.setType(rs.getString("type"));
				account.setClient((new ClientMapper()).findById(rs
						.getInt("idClient")));

			}
		} catch (SQLException e) {
			throw new DataSourceException(e);
		}

		return account;
	}

	public List<Account> findClientAccounts(Client client)
			throws DataSourceException {
		Account account = null;
		List<Account> list = new ArrayList<Account>();
		String sql = "Select * from account where idClient = "
				+ client.getIdClient() + ";";
		try {
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				account = new Account();
				account.setIdAccount(rs.getInt("idAccount"));
				account.setDateCreated(rs.getDate("dateCreated"));
				account.setBalance(rs.getDouble("balance"));
				account.setType(rs.getString("type"));
				account.setClient(client);
				list.add(account);

			}
		} catch (SQLException e) {
			throw new DataSourceException(e);
		}

		return list;

	}

	public int generateId() {
		String sql = "SELECT max(idaccount) FROM account;";
		try {
			ResultSet rs = stmt.executeQuery(sql);
			int id = 0;
			if (rs.next()) {
				id = rs.getInt(1) + 1;
			}
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
