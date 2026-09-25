package com.zak.zstocks.model;

public class User {

    private String name;
    private String email;
    private String phone;
    private int balance;

    // Required empty constructor for Firebase
    public User() {}

    public User(String name, String email, String phone, int balance) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public int getBalance() {
        return balance;
    }
}
