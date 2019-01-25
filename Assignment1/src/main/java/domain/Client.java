package domain;

import dataSource.AccountMapper;
import dataSource.DataSourceException;
import dataSource.TransactionMapper;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the client database table.
 */

public class Client implements Serializable {

	private static final long serialVersionUID = 1L;

	private int idClient;

	private String address;

	private String email;

	private String firstName;

	private String lastName;

	private String password;

	private String phone;

	private String username;

	// bi-directional many-to-one association to Account
	private List<Account> accounts;

	private AccountMapper accountMapper;

	// private ClientMapper clientMapper;

	private TransactionMapper transactionMapper;

	public Client() {
		this.accounts = new ArrayList<Account>();
		accountMapper = new AccountMapper();
		// clientMapper = new ClientMapper();
		transactionMapper = new TransactionMapper();
	}

	public Client(int idClient, String address, String email, String firstName,
				  String lastName, String password, String phone, String username) {
		super();
		this.idClient = idClient;
		this.address = address;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.phone = phone;
		this.username = username;
		accountMapper = new AccountMapper();
		transactionMapper = new TransactionMapper();
		try {
			this.accounts = accountMapper.findClientAccounts(this);
		} catch (DataSourceException e) {
			accounts = new ArrayList<Account>();
		}
	}

	public int getIdClient() {
		return this.idClient;
	}

	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Account> getAccounts() {
		return this.accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public Account addAccount(Account account) {
		getAccounts().add(account);
		account.setClient(this);

		return account;
	}

	public Account removeAccount(Account account) {
		getAccounts().remove(account);
		account.setClient(null);

		return account;
	}

	@Override
	public String toString() {
		return "Client [idClient=" + idClient + ", address=" + address
				+ ", email=" + email + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", password=" + password
				+ ", phone=" + phone + ", username=" + username + ", accounts="
				+ accounts + "]";
	}

	public Object[][] getAccountsMatrix() {
		// "Account Nb", "Balance", "Type", "Data Created"
		Object[][] matrix = new Object[accounts.size()][4];
		int row = 0;
		for (Account i : accounts) {
			matrix[row][0] = i.getIdAccount();
			matrix[row][3] = i.getDateCreated();
			matrix[row][1] = i.getBalance();
			matrix[row][2] = i.getType();
			row++;
		}

		return matrix;
	}

	public Account[] getAccountsArray() {
		// "Account Nb", "Balance", "Type", "Data Created"
		Account[] array = new Account[accounts.size()];
		return accounts.toArray(array);

	}

	public boolean payUtilities(String utility, Account source, double amount) {
		double balance = source.getBalance();
		if (balance >= amount) {
			source.setBalance(balance - amount);

			try {
				accountMapper.update(source);
				return true;
			} catch (DataSourceException e) {
				System.err.println("Account persistance error");
				e.printStackTrace();
			}

		}

		return false;
	}

	public boolean transfer(Account source, String destionationId, double amount) {
		double balance = source.getBalance();
		if (balance > amount) {
			try {
				int dId = Integer.parseInt(destionationId);
				Account destination = accountMapper.findById(dId);
				Transaction transaction = new Transaction();
				transaction.setAmount(amount);
				transaction.setDate(new Date());
				transaction.setDestinationAccount(destination);
				transaction.setSourceAccount(source);
				source.setBalance(source.getBalance() - amount);
				destination.setBalance(destination.getBalance() + amount);
				accountMapper.update(source);
				accountMapper.update(destination);
				transactionMapper.create(transaction);
				return true;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	public void refresh() {
		try {
			this.accounts = accountMapper.findClientAccounts(this);
		} catch (DataSourceException e) {
			accounts = new ArrayList<Account>();
		}
	}

	public String generateReport(String start, String end) {
		String result = "";
		SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
		try {
			Date begin = dt.parse(start);
			Date finish = dt.parse(end);
			List<Transaction> list = transactionMapper.getTransactionsBetween(
					begin, finish, accounts);
			for (Transaction t : list) {
				result += t.toString();
				result += "\r\n";
			}

		} catch (ParseException e) {
			e.printStackTrace();
		} catch (DataSourceException e) {
			e.printStackTrace();
		}

		return result;
	}

	public String generateReport() {
		return generateReport("01-01-1000", "01-01-3000");
	}
}