package com.thebank.view;

import com.thebank.controller.CustomersManager;
import com.thebank.controller.AccountsManager;
import com.thebank.model.Customer;
import com.thebank.model.Account;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static CustomersManager customersManager;
    static AccountsManager accountsManager;

    public static void main(String[] args) {
        var running = true;

        initBankSystem();

        var sc = new Scanner(System.in);

        while (running) {

            printMenu();
            System.out.print("Option: ");
            int option = sc.nextInt();

            switch (option) {
                case 1 -> {
                    System.out.print("Type customer ID: ");
                    int idCliente = sc.nextInt();
                    Customer customer = customersManager.selectCustomerById(idCliente);
                    if (customer != null)
                        System.out.println(customer.toString());
                    else
                        System.out.println("Not found customer.");
                    nextLine();
                }
                case 2 -> {
                    System.out.print("Type account ID: ");
                    int idConta = sc.nextInt();
                    Account conta = accountsManager.selectAccountById(idConta);
                    if (conta != null)
                        System.out.println(conta.toString());
                    else
                        System.out.println("Not found account.");
                    nextLine();
                }
                case 3 -> {
                    System.out.print("Type customer ID: ");
                    int idCliente2 = sc.nextInt();
                    Customer customer2 = customersManager.selectCustomerById(idCliente2);
                    if (customer2 != null) {
                        customer2.setActive(true);
                        System.out.println("Customer successfully activated.");
                    } else
                        System.out.println("Customer not found.");
                    nextLine();
                }
                case 4 -> {
                    System.out.print("Type customer ID: ");
                    int idCliente3 = sc.nextInt();
                    Customer customer3 = customersManager.selectCustomerById(idCliente3);
                    if (customer3 != null) {
                        customer3.setActive(false);
                        System.out.println("Customer successfully deactivated.!");
                    } else
                        System.out.println("Customer not found.");
                    nextLine();
                }
                case 5 -> {
                    running = false;
                    System.out.println("################# System exited #################");
                }
                default -> {
                    for (int i = 0; i < 5; ++i)
                        System.out.println();
                }
            }

        }


    }

    private static void nextLine() {
        System.out.println("\n");
    }

    private static void printMenu() {
        System.out.println("Select an option:\n");
        System.out.println("1 - Consult customer");
        System.out.println("2 - Consult account");
        System.out.println("3 - Activate a customer");
        System.out.println("4 - Deactivate a customer");
        System.out.println("5 - Exit");
        System.out.println();
    }

    private static void initBankSystem() {
        var accounts = new ArrayList<Account>();
        var customers = new ArrayList<Customer>();

        var account1 = new Account(1, 0, 0, true);
        var account2 = new Account(2, 0, 0, true);
        accounts.add(account1);
        accounts.add(account2);

        var customer1 = new Customer(1, "Jose Machado", 22, "mail@mail.com", account1.getId(), true);
        var customer2 = new Customer(2, "Joao Cardoso", 24, "mail@mail.com", account2.getId(), true);
        customers.add(customer1);
        customers.add(customer2);

        customersManager = new CustomersManager(customers);
        accountsManager = new AccountsManager(accounts);

    }

}

