package com.thebank.model;

public class Customer {

    private int id;
    private String name;
    private int age;
    private String email;
    private boolean active;
    private int accountId;

    public Customer(int id, String name, int age, String email, int accountId, boolean active) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.accountId = accountId;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getAccountId() {
        return this.accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "========================="
                + "Id: " + this.id + "\n"
                + "Name: " + this.name + "\n"
                + "Email: " + this.email + "\n"
                + "Age: " + this.age + "\n"
                + "Status: " + (active ? "Active" : "Inactive") + "\n"
                + "=========================";
    }

}
