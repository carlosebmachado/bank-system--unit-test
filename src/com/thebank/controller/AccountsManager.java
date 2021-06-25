package com.thebank.controller;

import com.thebank.model.Account;

import java.util.List;

public class AccountsManager {

    private final List<Account> ACCOUNTS;

    public AccountsManager(List<Account> accounts) {
        this.ACCOUNTS = accounts;
    }

    public int countAccounts() {
        return ACCOUNTS.size();
    }

    public Account selectAccountById(int id) {
        for (var account : ACCOUNTS) {
            if (account.getId() == id)
                return account;
        }
        return null;
    }

    public void insertAccount(Account account) {
        this.ACCOUNTS.add(account);
    }

    public boolean deleteAccountById(int id) {
        for (var account : ACCOUNTS) {
            if (account.getId() == id) {
                ACCOUNTS.remove(account);
                return true;
            }
        }
        return false;
    }

    public boolean accountIsActive(int id) {
        for (var account : ACCOUNTS) {
            if (account.getId() == id)
                if (account.isActive()) {
                    return true;
                }
        }
        return false;
    }

    public boolean transferAmount(double value, int originId, int destinationId) {
        var originAccount = selectAccountById(originId);
        var destinationAccount = selectAccountById(destinationId);

        if (originAccount.getBalance() + originAccount.getSpecialLimit() >= value) {
            destinationAccount.setBalance(destinationAccount.getBalance() + value);
            originAccount.setBalance(originAccount.getBalance() - value);
            return true;
        }
        return false;
    }

}
