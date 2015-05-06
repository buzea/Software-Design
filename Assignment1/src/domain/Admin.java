package domain;

import java.io.Serializable;
import java.util.Date;

import dataSource.AccountMapper;
import dataSource.ClientMapper;
import dataSource.DataSourceException;

;

/**
 * The persistent class for the admin database table.
 * 
 */
public class Admin implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idadmin;

	private String password;

	private String username;

	private ClientMapper clientMapper;

	private AccountMapper accountMapper;

	public Admin() {
		clientMapper = new ClientMapper();
		accountMapper = new AccountMapper();
	}

	public Admin(int idadmin, String password, String username) {
		super();
		this.idadmin = idadmin;
		this.password = password;
		this.username = username;
		clientMapper = new ClientMapper();
		accountMapper = new AccountMapper();
	}

	public int getIdadmin() {
		return this.idadmin;
	}

	public void setIdadmin(int idadmin) {
		this.idadmin = idadmin;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "Admin [idadmin=" + idadmin + ", password=" + password
				+ ", username=" + username + "]";
	}

	public Client findClientById(String id) {
		try {
			return clientMapper.findById(Integer.parseInt(id));
		} catch (DataSourceException e) {
			e.printStackTrace();
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		}

		return null;
	}

	public Client findClientByUsername(String username) {
		try {
			return clientMapper.findByUsername(username);
		} catch (DataSourceException e) {
			e.printStackTrace();
		}

		return null;
	}

	public Client findClientByEmail(String email) {
		try {
			return clientMapper.findByEmail(email);
		} catch (DataSourceException e) {
			e.printStackTrace();
		}

		return null;
	}

	public Account findAccountById(String id) {
		try {
			return accountMapper.findById(Integer.parseInt(id));
		} catch (DataSourceException e) {
			e.printStackTrace();
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	public boolean createClient(String idClient, String address, String email,
			String firstName, String lastName, String password, String phone,
			String username) {
		if (idClient.equals("") || address.equals("") || email.equals("")
				|| firstName.equals("") || lastName.equals("")
				|| password.equals("") || phone.equals("")
				|| username.equals(""))
			return false;

		try {
			Client client = new Client(Integer.parseInt(idClient), address,
					email, firstName, lastName, password, phone, username);
			clientMapper.create(client);
			return true;
		} catch (DataSourceException e) {
			e.printStackTrace();
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		}
		return false;
	}

	public boolean updateClient(String idClient, String address, String email,
			String firstName, String lastName, String password, String phone,
			String username) {
		if (idClient.equals("") || address.equals("") || email.equals("")
				|| firstName.equals("") || lastName.equals("")
				|| password.equals("") || phone.equals("")
				|| username.equals(""))
			return false;

		try {
			Client client = findClientById(idClient);
			if (client != null) {
				client.setAddress(address);
				client.setEmail(email);
				client.setFirstName(firstName);
				client.setLastName(lastName);
				client.setPassword(password);
				client.setPhone(phone);
				client.setUsername(username);
				clientMapper.update(client);
				return true;
			}
		} catch (NumberFormatException e1) {
			return false;

		} catch (DataSourceException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteClient(String idClient) {

		try {
			clientMapper.delete(findClientById(idClient));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public String generateAccountId() {
		int id = accountMapper.generateId();
		return id + "";
	}

	public boolean createAccount(String id, Double balance, String ownerId,
			String type) {
		try {
			Client client = clientMapper.findById(Integer.parseInt(ownerId));
			Account account = new Account(Integer.parseInt(id), balance,
					new Date(), type, client);
			accountMapper.create(account);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean updateAccount(String id, Double value, String type,
			Date date, String ownerId) {
		try {
			Client client = clientMapper.findById(Integer.parseInt(ownerId));
			Account account = new Account(Integer.parseInt(id), value, date,
					type, client);
			accountMapper.update(account);
			return true;
		} catch (Exception e) {

		}

		return false;

	}

	public boolean deleteAccount(String id) {
		try {
			Account account = accountMapper.findById(Integer.parseInt(id));
			accountMapper.delete(account);
			return true;
		} catch (Exception e) {

			e.printStackTrace();
		}

		return false;

	}
}