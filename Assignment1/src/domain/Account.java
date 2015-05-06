package domain;

import java.io.Serializable;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the account database table.
 * 
 */
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String SAVINGS = "savings";
	public static final String CURRENT = "current";

	private int idAccount;

	private double balance;


	private Date dateCreated;

	private String type;

	private Client client;

	//bi-directional many-to-one association to Transaction
	private List<Transaction> transactions1;

	//bi-directional many-to-one association to Transaction
	private List<Transaction> transactions2;

	public Account() {
		this.transactions1= new ArrayList<Transaction>();
		this.setTransactions2(new ArrayList<Transaction>());
	}

	public Account(int idAccount, double balance, Date dateCreated,
			String type, Client client) {
		super();
		this.idAccount = idAccount;
		this.balance = balance;
		this.dateCreated = dateCreated;
		this.type = type;
		this.client = client;
		this.transactions1= new ArrayList<Transaction>();
		this.setTransactions2(new ArrayList<Transaction>());
	}

	public int getIdAccount() {
		return this.idAccount;
	}

	public void setIdAccount(int idAccount) {
		this.idAccount = idAccount;
	}

	public double getBalance() {
		return this.balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Client getClient() {
		return this.client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<Transaction> getTransactions1() {
		return this.transactions1;
	}

	public void setTransactions1(List<Transaction> transactions1) {
		this.transactions1 = transactions1;
	}

	@Override
	public String toString() {
		return idAccount+" ("+balance+"$)";
	}

	/**
	 * @return the transactions2
	 */
	public List<Transaction> getTransactions2() {
		return transactions2;
	}

	/**
	 * @param transactions2 the transactions2 to set
	 */
	public void setTransactions2(List<Transaction> transactions2) {
		this.transactions2 = transactions2;
	}

	

}