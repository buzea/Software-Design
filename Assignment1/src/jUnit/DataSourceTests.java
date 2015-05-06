package jUnit;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import dataSource.*;
import domain.*;

public class DataSourceTests {

	@Test
	public void testTransactionMapper() {
		try {
			Transaction transaction = new Transaction();
			TransactionMapper transactionMapper = new TransactionMapper();

			transaction.setAmount(1.0);
			transaction.setDate(new Date());
			transaction.setIdtransaction(4);
			Account account = new Account(2, 0, new Date(), "savings",
					new Client());
			Account account2 = new Account(3, 0, new Date(), "savings",
					new Client());
			transaction.setSourceAccount(account);
			transaction.setDestinationAccount(account2);
			transactionMapper.create(transaction);

			transactionMapper.delete(transaction);
		} catch (DataSourceException e) {
			fail("Fail");
		}
	}

	@Test
	public void testAdminMapper() {
		try {
			AdminMapper adminMapper = new AdminMapper();
			if (null==adminMapper.login("root", "poweruser"))
				fail("login failed");
		} catch (DataSourceException e) {
			fail("Exception Fail");
		}
	}

	@Test
	public void testClientMapper() {

		ClientMapper clientMapper = new ClientMapper();
		try {
			Client client = clientMapper.findById(1);
			System.out.println(client);
		} catch (DataSourceException e) {
			fail("DSE");
		}

	}

	@Test
	public void testAccountMapper() {
		AccountMapper accountMapper = new AccountMapper();
		try {
			System.out.println(accountMapper.findById(2));
		} catch (DataSourceException e) {
			fail("DSE");
		}
		

	}

}
