package com.thebank.model;

public class Account {

    private int id;
    private double balance;
    private double specialLimit;
    private boolean active;

    public Account(int id, double balance, double specialLimit, boolean active) {
        this.id = id;
        this.balance = balance;
        this.specialLimit = specialLimit;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getSpecialLimit() {
        return specialLimit;
    }

    public void setSpecialLimit(double specialLimit) {
        this.specialLimit = specialLimit;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "========================="
                + "Id: " + this.id + "\n"
                + "Balance: " + this.balance + "\n"
                + "Status: " + (active ? "Active" : "Inactive") + "\n"
                + "=========================";
    }
}
