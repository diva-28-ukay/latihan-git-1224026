package com.latihan.git1224026;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Soal 8 (Bonus) - Unit test untuk BankAccount. Minimal 6 test case.
 */
class BankAccountTest {

    @Test
    void testInitialBalanceZero() {
        BankAccount acc = new BankAccount();
        assertEquals(0.0, acc.getBalance(), 0.001);
    }

    @Test
    void testInitialBalanceWithValue() {
        BankAccount acc = new BankAccount(100.0);
        assertEquals(100.0, acc.getBalance(), 0.001);
    }

    @Test
    void testInitialBalanceNegativeThrows() {
        assertThrows(IllegalArgumentException.class, () -> new BankAccount(-50.0));
    }

    @Test
    void testDepositMenambahSaldo() {
        BankAccount acc = new BankAccount(100.0);
        acc.deposit(50.0);
        assertEquals(150.0, acc.getBalance(), 0.001);
    }

    @Test
    void testDepositNegatifThrows() {
        BankAccount acc = new BankAccount(100.0);
        assertThrows(IllegalArgumentException.class, () -> acc.deposit(-10.0));
        assertThrows(IllegalArgumentException.class, () -> acc.deposit(0));
    }

    @Test
    void testWithdrawMengurangiSaldo() {
        BankAccount acc = new BankAccount(100.0);
        acc.withdraw(40.0);
        assertEquals(60.0, acc.getBalance(), 0.001);
    }

    @Test
    void testWithdrawMelebihiSaldoThrows() {
        BankAccount acc = new BankAccount(100.0);
        assertThrows(IllegalArgumentException.class, () -> acc.withdraw(150.0));
    }

    @Test
    void testWithdrawNegatifThrows() {
        BankAccount acc = new BankAccount(100.0);
        assertThrows(IllegalArgumentException.class, () -> acc.withdraw(-20.0));
    }

    @Test
    void testGetBalanceSetelahTransaksi() {
        BankAccount acc = new BankAccount();
        acc.deposit(200.0);
        acc.withdraw(75.0);
        assertEquals(125.0, acc.getBalance(), 0.001);
    }
}
