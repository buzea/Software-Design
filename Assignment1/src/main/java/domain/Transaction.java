package domain;

import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the transaction database table.
 */
public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;


	private int idtransaction;

	private double amount;

	private Date date;


	private Account destinationAccount;


	private Account sourceAccount;

	public Transaction() {
	}

	public Transaction(int idtransaction, double amount, Date date,
					   Account account1, Account account2) {
		super();
		this.idtransaction = idtransaction;
		this.amount = amount;
		this.date = date;
		this.destinationAccount = account1;
		this.sourceAccount = account2;
	}

	public int getIdtransaction() {
		return this.idtransaction;
	}

	public void setIdtransaction(int idtransaction) {
		this.idtransaction = idtransaction;
	}

	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Account getDestinationAccount() {
		return this.destinationAccount;
	}

	public void setDestinationAccount(Account account1) {
		this.destinationAccount = account1;
	}

	public Account getSourceAccount() {
		return this.sourceAccount;
	}

	public void setSourceAccount(Account account2) {
		this.sourceAccount = account2;
	}

	@Override
	public String toString() {
		return "Transaction [idtransaction=" + idtransaction + ", amount="
				+ amount + ", date=" + date + ", destinationAccount="
				+ destinationAccount + ", sourceAccount=" + sourceAccount + "]";
	}

}