package dataSource;

import domain.Account;
import domain.Transaction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionMapper extends Mapper {

	public TransactionMapper() {
		super();
	}

	public void create(Transaction transaction) throws DataSourceException {
		String sql = "INSERT INTO transaction (`idtransaction`, `sourceAccount`, `destiantionAccount`, `amount`, `date`) VALUES (?, ?, ?, ?, ?);";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			int id = transaction.getIdtransaction();
			if (id == 0)
				transaction.setIdtransaction(generateId());

			preparedStatement.setInt(1, transaction.getIdtransaction());
			int idSource = transaction.getSourceAccount().getIdAccount();
			int idDestination = transaction.getDestinationAccount()
					.getIdAccount();
			if (idSource == idDestination) {
				throw new SQLException();
			}
			preparedStatement.setInt(2, idSource);
			preparedStatement.setInt(3, idDestination);
			preparedStatement.setDouble(4, transaction.getAmount());
			preparedStatement.setTimestamp(5, new Timestamp(transaction
					.getDate().getTime()));

			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			throw new DataSourceException(e);
		}

	}

	private int generateId() throws SQLException {
		String sql = "SELECT max(idtransaction) FROM transaction;";
		ResultSet rs = stmt.executeQuery(sql);
		int id = 0;
		if (rs.next()) {
			id = rs.getInt(1) + 1;
		}
		return id;

	}

	public void delete(Transaction transaction) throws DataSourceException {
		String sql = "DELETE FROM transaction WHERE `idtransaction`=?;";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, transaction.getIdtransaction());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			DataSourceException up = new DataSourceException();
			throw up;
		}

	}

	public List<Transaction> getTransactionsBetween(Date start, Date end,
													List<Account> accounts) throws DataSourceException {
		StringBuilder builder = new StringBuilder();
		int size = accounts.size();

		for (int i = 0; i < size; i++) {
			builder.append("?,");
		}

		String params = builder.deleteCharAt(builder.length() - 1).toString();
		String sql = "Select * from transaction where date between ? and ? and (sourceAccount in ( "
				+ params + " ) or destiantionAccount in ( " + params + " ));;";
		PreparedStatement preparedStatement;
		List<Transaction> result = new ArrayList<Transaction>();
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setTimestamp(1, new Timestamp(start.getTime()));
			preparedStatement.setTimestamp(2, new Timestamp(end.getTime()));
			int index = 1;
			for (Account a : accounts) {
				preparedStatement.setInt(2 + index, a.getIdAccount());
				preparedStatement.setInt(2 + index + size, a.getIdAccount());
				index++;
			}

			ResultSet rs = preparedStatement.executeQuery();
			AccountMapper accountMapper = new AccountMapper();

			while (rs.next()) {
				Transaction transaction = new Transaction(
						rs.getInt("idtransaction"), rs.getDouble("amount"),
						rs.getDate("date"), accountMapper.findById(rs
						.getInt("sourceAccount")),
						accountMapper.findById(rs.getInt("destiantionAccount")));
				result.add(transaction);

			}

		} catch (SQLException e) {
			e.printStackTrace();
			DataSourceException up = new DataSourceException();
			throw up;
		}
		return result;

	}
}
