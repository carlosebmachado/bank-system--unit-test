package com.thebank.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.ArrayList;

import com.thebank.model.Account;
import org.junit.Test;

public class AccountsManagerTest {

	private AccountsManager accountsManager;
	private final int CUSTOMER_ID_1 = 1;
	private final int CUSTOMER_ID_2 = 2;

	@Test
	public void testSelectAccountById() {
		/* ========== Arrange ========== */
		var account1 = new Account(CUSTOMER_ID_1, 200, 0, true);
		var account2 = new Account(CUSTOMER_ID_2, 0, 0, true);

		var accounts = new ArrayList<Account>();
		accounts.add(account1);
		accounts.add(account2);

		accountsManager = new AccountsManager(accounts);

		/* ========== Act ========== */
		var selectedAccount = accountsManager.selectAccountById(CUSTOMER_ID_2);

		/* ========== Assert ========== */
		assertThat(selectedAccount.getId(), is(2));
	}

	@Test
	public void testDeleteAccountById() {
		/* ========== Arrange ========== */
		var account1 = new Account(CUSTOMER_ID_1, 200, 0, true);
		var account2 = new Account(CUSTOMER_ID_2, 0, 0, true);

		var accounts = new ArrayList<Account>();
		accounts.add(account1);
		accounts.add(account2);

		accountsManager = new AccountsManager(accounts);

		/* ========== Act ========== */
		var success = accountsManager.deleteAccountById(CUSTOMER_ID_2);
		var selectedAccount1 = accountsManager.selectAccountById(CUSTOMER_ID_1);
		var selectedAccount2 = accountsManager.selectAccountById(CUSTOMER_ID_2);

		/* ========== Assert ========== */
		assertTrue(success);
		assertThat(accountsManager.countAccounts(), is(1));
		assertThat(selectedAccount1.getId(), is(1));
		assertNull(selectedAccount2);
	}

	@Test
	public void testAccountIsActive_IsActive() {
		/* ========== Arrange ========== */
		var account1 = new Account(CUSTOMER_ID_1, 200, 0, false);
		var account2 = new Account(CUSTOMER_ID_2, 0, 0, true);

		var accounts = new ArrayList<Account>();
		accounts.add(account1);
		accounts.add(account2);

		accountsManager = new AccountsManager(accounts);

		/* ========== Act ========== */
		var success = accountsManager.accountIsActive(CUSTOMER_ID_2);

		/* ========== Assert ========== */
		assertTrue(success);
	}

	@Test
	public void testAccountIsActive_NoActive() {
		/* ========== Arrange ========== */
		var account1 = new Account(CUSTOMER_ID_1, 200, 0, true);
		var account2 = new Account(CUSTOMER_ID_2, 0, 0, false);

		var accounts = new ArrayList<Account>();
		accounts.add(account1);
		accounts.add(account2);

		accountsManager = new AccountsManager(accounts);

		/* ========== Act ========== */
		var success = accountsManager.accountIsActive(CUSTOMER_ID_2);

		/* ========== Assert ========== */
		assertFalse(success);
	}

	@Test
	public void testTransferAmount() {
		/* ========== Arrange ========== */
		var account1 = new Account(CUSTOMER_ID_1, 200, 0, true);
		var account2 = new Account(CUSTOMER_ID_2, 0, 0, true);
		
		var accounts = new ArrayList<Account>();
		accounts.add(account1);
		accounts.add(account2);
		
		accountsManager = new AccountsManager(accounts);

		/* ========== Act ========== */
		var success = accountsManager.transferAmount(100, CUSTOMER_ID_1, CUSTOMER_ID_2);
		
		/* ========== Assert ========== */
		assertTrue(success);
		assertThat(account2.getBalance(), is(100.0));
		assertThat(account1.getBalance(), is(100.0));
	}

	@Test
	public void testTransferAmount_Insufficient() {
		/* ========== Arrange ========== */
		var account1 = new Account(CUSTOMER_ID_1, 100, 100, true);
		var account2 = new Account(CUSTOMER_ID_2, 0, 0, true);
		
		var accounts = new ArrayList<Account>();
		accounts.add(account1);
		accounts.add(account2);
		
		accountsManager = new AccountsManager(accounts);

		/* ========== Act ========== */
		var success = accountsManager.transferAmount(200, CUSTOMER_ID_1, CUSTOMER_ID_2);
		
		/* ========== Assert ========== */
		assertTrue(success);
		assertThat(account1.getBalance(), is(-100.0));
		assertThat(account2.getBalance(), is(200.0));
	}

	@Test
	public void testTransferAmount_NegativeBalance() {
		/* ========== Arrange ========== */
		var account1 = new Account(CUSTOMER_ID_1, -100, 300, true);
		var account2 = new Account(CUSTOMER_ID_2, 0, 0, true);
		
		var accounts = new ArrayList<Account>();
		accounts.add(account1);
		accounts.add(account2);
		
		accountsManager = new AccountsManager(accounts);

		/* ========== Act ========== */
		var success = accountsManager.transferAmount(200, CUSTOMER_ID_1, CUSTOMER_ID_2);
		
		/* ========== Assert ========== */
		assertTrue(success);
		assertThat(account1.getBalance(), is(-300.0));
		assertThat(account2.getBalance(), is(200.0));
	}

	@Test
	public void testTransferAmount_NegativeToNegativeBalance() {
		/* ========== Arrange ========== */
		var account1 = new Account(CUSTOMER_ID_1, -100, 300, true);
		var account2 = new Account(CUSTOMER_ID_2, -100, 0, true);
		
		var accounts = new ArrayList<Account>();
		accounts.add(account1);
		accounts.add(account2);
		
		accountsManager = new AccountsManager(accounts);

		/* ========== Act ========== */
		var success = accountsManager.transferAmount(200, CUSTOMER_ID_1, CUSTOMER_ID_2);
		
		/* ========== Assert ========== */
		assertTrue(success);
		assertThat(account1.getBalance(), is(-300.0));
		assertThat(account2.getBalance(), is(100.0));
	}

	@Test
	public void testTransferAmount_Nothing() {
		/* ========== Arrange ========== */
		var account1 = new Account(CUSTOMER_ID_1, -100, 100, true);
		var account2 = new Account(CUSTOMER_ID_2, -100, 0, true);
		
		var accounts = new ArrayList<Account>();
		accounts.add(account1);
		accounts.add(account2);
		
		accountsManager = new AccountsManager(accounts);

		/* ========== Act ========== */
		var success = accountsManager.transferAmount(0, CUSTOMER_ID_1, CUSTOMER_ID_2);
		
		/* ========== Assert ========== */
		assertTrue(success);
		assertThat(account1.getBalance(), is(-100.0));
		assertThat(account2.getBalance(), is(-100.0));
	}
	
}
