package model;

import exception.InsufficientFundsException;
import exception.InvalidAmountException;

// Practical 6: Interface specifying the core transaction contract
public interface Transactable {
    void deposit(long amount) throws InvalidAmountException;
    boolean withdraw(long amount) throws InsufficientFundsException, InvalidAmountException;
}
