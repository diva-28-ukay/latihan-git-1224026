package com.latihan.git1224026;

/**
 * Soal 8 (Bonus) - BankAccount.
 * Validasi: amount tidak boleh negatif, withdraw tidak boleh melebihi saldo.
 */
public class BankAccount {

    private double balance;

    public BankAccount() {
        this.balance = 0.0;
    }

    public BankAccount(double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        this.balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdraw amount must be positive");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        this.balance -= amount;
    }

    public double getBalance() {
        return balance;
    }
}
