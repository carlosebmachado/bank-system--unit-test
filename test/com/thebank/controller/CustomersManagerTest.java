package com.thebank.controller;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;

import com.thebank.model.Customer;
import com.thebank.util.NotAllowedAgeException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CustomersManagerTest {

	private CustomersManager customersManager;
	private final int CUSTOMER_ID_1 = 1;
	private final int CUSTOMER_ID_2 = 2;
	
	@Before
	public void setUp() {
		/* ========== Arrange ========== */
		var customer1 = new Customer(CUSTOMER_ID_1, "Jose Machado", 22, "mail@mail.com", 1, true);
		var customer2 = new Customer(CUSTOMER_ID_2, "Joao Cardoso", 24, "mail@mail.com", 2, false);
		
		var customers = new ArrayList<Customer>();
		customers.add(customer1);
		customers.add(customer2);
		
		customersManager = new CustomersManager(customers);
	}

	@After
	public void tearDown() {
		customersManager.dump();
	}

	@Test
	public void testSelectCustomerById() {
		/* ========== Act ========== */
		var customer = customersManager.selectCustomerById(CUSTOMER_ID_1);
		
		/* ========== Assert ========== */
		assertThat(customer.getId(), is(CUSTOMER_ID_1));
	}

	@Test
	public void testSelectCustomerById_Nonexistent() {
		/* ========== Act ========== */
		var customer = customersManager.selectCustomerById(10);
		
		/* ========== Assert ========== */
		assertNull(customer);
	}

	@Test
	public void testDeleteCustomerById() {
		/* ========== Act ========== */
		var wasRemoved = customersManager.deleteCustomerById(CUSTOMER_ID_2);
		
		/* ========== Assert ========== */
		assertThat(wasRemoved, is(true));
		assertThat(customersManager.countCustomers(), is(1));
		assertNull(customersManager.selectCustomerById(CUSTOMER_ID_2));
	}

	@Test
	public void testCustomerIsActive_IsActive() {
		/* ========== Act ========== */
		var isActive = customersManager.customerIsActive(CUSTOMER_ID_1);

		/* ========== Assert ========== */
		assertThat(isActive, is(true));
	}

	@Test
	public void testCustomerIsActive_NoActive() {
		/* ========== Act ========== */
		var isActive = customersManager.customerIsActive(CUSTOMER_ID_2);

		/* ========== Assert ========== */
		assertThat(isActive, is(false));
	}

	@Test
	public void testDeleteCustomerById_Nonexistent() {
		/* ========== Act ========== */
		var wasRemoved = customersManager.deleteCustomerById(10);
		
		/* ========== Assert ========== */
		assertThat(wasRemoved, is(false));
		assertThat(customersManager.countCustomers(), is(2));
	}

	@Test
	public void testValidateAge() throws NotAllowedAgeException {
		/* ========== Arrange ========== */		
		var customer = new Customer(1, "Jose Machado", 25, "mail@mail.com", 1, true);
		
		/* ========== Act ========== */
		var valid = customersManager.validateAge(customer.getAge());
		
		/* ========== Assert ========== */
		assertTrue(valid);
	}

	@Test
	public void testValidateAge_Min() throws NotAllowedAgeException {
		/* ========== Arrange ========== */		
		var customer = new Customer(1, "Jose Machado", 18, "mail@mail.com", 1, true);
		
		/* ========== Act ========== */
		var valid = customersManager.validateAge(customer.getAge());
		
		/* ========== Assert ========== */
		assertTrue(valid);
	}
	
	@Test
	public void testValidateAge_Max() throws NotAllowedAgeException {
		/* ========== Arrange ========== */		
		var customer = new Customer(1, "Jose Machado", 65, "mail@mail.com", 1, true);
		
		/* ========== Act ========== */
		var valid = customersManager.validateAge(customer.getAge());
		
		/* ========== Assert ========== */
		assertTrue(valid);
	}

	@Test
	public void testValidateAge_Lesser() throws NotAllowedAgeException {
		/* ========== Arrange ========== */		
		var customer = new Customer(1, "Jose Machado", 17, "mail@mail.com", 1, true);

		/* ========== Act ========== */
		try {
			customersManager.validateAge(customer.getAge());
			fail();
		} catch (Exception e) {
			/* ========== Assert ========== */
			assertThat(e.getMessage(), is(NotAllowedAgeException.INVALID_AGE_MESSAGE));
		}	
	}

	@Test
	public void testValidateAge_Greater() throws NotAllowedAgeException {
		/* ========== Arrange ========== */		
		var customer = new Customer(1, "Jose Machado", 66, "mail@mail.com", 1, true);
		/* ========== Act ========== */
		try {
			customersManager.validateAge(customer.getAge());
			fail();
		} catch (Exception e) {
			/* ========== Assert ========== */
			assertThat(e.getMessage(), is(NotAllowedAgeException.INVALID_AGE_MESSAGE));
		}	
	}
	
}
