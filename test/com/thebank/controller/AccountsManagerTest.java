package com.thebank.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.ArrayList;

import com.thebank.model.Account;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AccountsManagerTest {

	private AccountsManager accountsManager;
	private final int CUSTOMER_ID_1 = 1;
	private final int CUSTOMER_ID_2 = 2;


	@Before
	public void setUp() {
		/* ========== Arrange ========== */
		var account1 = new Account(CUSTOMER_ID_1, 0, 0, true);
		var account2 = new Account(CUSTOMER_ID_2, 0, 0, false);

		var accounts = new ArrayList<Account>();
		accounts.add(account1);
		accounts.add(account2);

		accountsManager = new AccountsManager(accounts);
	}

	@After
	public void tearDown() {
		accountsManager.dump();
	}

	@Test
	public void testSelectAccountById() {
		/* ========== Act ========== */
		var selectedAccount = accountsManager.selectAccountById(CUSTOMER_ID_2);

		/* ========== Assert ========== */
		assertThat(selectedAccount.getId(), is(2));
	}

	@Test
	public void testDeleteAccountById() {
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
		/* ========== Act ========== */
		var success = accountsManager.accountIsActive(CUSTOMER_ID_1);

		/* ========== Assert ========== */
		assertTrue(success);
	}

	@Test
	public void testAccountIsActive_NoActive() {
		/* ========== Act ========== */
		var success = accountsManager.accountIsActive(CUSTOMER_ID_2);

		/* ========== Assert ========== */
		assertFalse(success);
	}

	@Test
	public void testTransferAmount() {
		/* ========== Arrange ========== */
		accountsManager.selectAccountById(CUSTOMER_ID_1).setBalance(200);

		/* ========== Act ========== */
		var success = accountsManager.transferAmount(100, CUSTOMER_ID_1, CUSTOMER_ID_2);
		
		/* ========== Assert ========== */
		assertTrue(success);
		assertThat(accountsManager.selectAccountById(CUSTOMER_ID_2).getBalance(), is(100.0));
		assertThat(accountsManager.selectAccountById(CUSTOMER_ID_1).getBalance(), is(100.0));
	}

	@Test
	public void testTransferAmount_UsingSpecialLimit() {
		/* ========== Arrange ========== */
		accountsManager.selectAccountById(CUSTOMER_ID_1).setBalance(100);
		accountsManager.selectAccountById(CUSTOMER_ID_1).setSpecialLimit(100);

		/* ========== Act ========== */
		var success = accountsManager.transferAmount(200, CUSTOMER_ID_1, CUSTOMER_ID_2);
		
		/* ========== Assert ========== */
		assertTrue(success);
		assertThat(accountsManager.selectAccountById(CUSTOMER_ID_1).getBalance(), is(-100.0));
		assertThat(accountsManager.selectAccountById(CUSTOMER_ID_2).getBalance(), is(200.0));
	}

	@Test
	public void testTransferAmount_NegativeBalanceUsingSpecialLimit() {
		/* ========== Arrange ========== */
		accountsManager.selectAccountById(CUSTOMER_ID_1).setBalance(-100);
		accountsManager.selectAccountById(CUSTOMER_ID_1).setSpecialLimit(300);

		/* ========== Act ========== */
		var success = accountsManager.transferAmount(200, CUSTOMER_ID_1, CUSTOMER_ID_2);
		
		/* ========== Assert ========== */
		assertTrue(success);
		assertThat(accountsManager.selectAccountById(CUSTOMER_ID_1).getBalance(), is(-300.0));
		assertThat(accountsManager.selectAccountById(CUSTOMER_ID_2).getBalance(), is(200.0));
	}

	@Test
	public void testTransferAmount_NegativeToNegativeBalance() {
		/* ========== Arrange ========== */
		accountsManager.selectAccountById(CUSTOMER_ID_1).setBalance(-100);
		accountsManager.selectAccountById(CUSTOMER_ID_1).setSpecialLimit(300);
		accountsManager.selectAccountById(CUSTOMER_ID_2).setBalance(-100);

		/* ========== Act ========== */
		var success = accountsManager.transferAmount(200, CUSTOMER_ID_1, CUSTOMER_ID_2);
		
		/* ========== Assert ========== */
		assertTrue(success);
		assertThat(accountsManager.selectAccountById(CUSTOMER_ID_1).getBalance(), is(-300.0));
		assertThat(accountsManager.selectAccountById(CUSTOMER_ID_2).getBalance(), is(100.0));
	}

	@Test
	public void testTransferAmount_Nothing() {
		/* ========== Arrange ========== */
		accountsManager.selectAccountById(CUSTOMER_ID_1).setBalance(-100);
		accountsManager.selectAccountById(CUSTOMER_ID_1).setSpecialLimit(100);
		accountsManager.selectAccountById(CUSTOMER_ID_2).setBalance(-100);

		/* ========== Act ========== */
		var success = accountsManager.transferAmount(0, CUSTOMER_ID_1, CUSTOMER_ID_2);
		
		/* ========== Assert ========== */
		assertTrue(success);
		assertThat(accountsManager.selectAccountById(CUSTOMER_ID_1).getBalance(), is(-100.0));
		assertThat(accountsManager.selectAccountById(CUSTOMER_ID_2).getBalance(), is(-100.0));
	}
	
}
