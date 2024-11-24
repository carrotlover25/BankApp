/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Account;
import core.models.Transaction;
import core.models.TransactionType;
import core.models.storage.AccountStorage;
import core.models.storage.TransactionStorage;

/**
 *
 * @author mfrey
 */
public class TransactionController {

     public static Response deposit(String destinationAcc, String amount) {
        try {
            double amountValue;
            try {
                amountValue = Double.parseDouble(amount);
                if (amountValue <= 0) {
                    return new Response("Amount needs to be greater than 0", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Type numbers only", Status.BAD_REQUEST);
            }

            AccountStorage accountStorage = AccountStorage.getInstance();
            TransactionStorage transactionStorage = TransactionStorage.getInstance();
            Account destinationAccount = accountStorage.getAccount(destinationAcc);

            if (destinationAccount == null) {
                return new Response("Destination account not found", Status.BAD_REQUEST);
            }

            destinationAccount.setBalance(amountValue + destinationAccount.getBalance());
            Transaction transaction = new Transaction(TransactionType.DEPOSIT, null, destinationAccount, amountValue);
            transactionStorage.addTransaction(transaction);
            return new Response("Transaction Successful!", Status.CREATED, transaction);
        } catch (Exception ex) {
            return new Response("Unexpected Error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response withdraw(String sourceAcc, String amount) {
        try {
            AccountStorage accountStorage = AccountStorage.getInstance();
            TransactionStorage transactionStorage = TransactionStorage.getInstance();
            Account sourceAccount = accountStorage.getAccount(sourceAcc);

            if (sourceAccount == null) {
                return new Response("Account not found", Status.NOT_FOUND);
            }

            double amountValue = Double.parseDouble(amount);
            if (amountValue <= 0) {
                return new Response("Amount needs to be greater than 0", Status.BAD_REQUEST);
            } else if (amountValue > sourceAccount.getBalance()) {
                return new Response("Not enough funds available", Status.BAD_REQUEST);
            }

            sourceAccount.setBalance(sourceAccount.getBalance() - amountValue);
            Transaction transaction = new Transaction(TransactionType.WITHDRAW, sourceAccount, null, amountValue);
            transactionStorage.addTransaction(transaction);
            return new Response("Transaction Successful!", Status.CREATED, transaction);
        } catch (NumberFormatException ex) {
            return new Response("Amount must be a number", Status.BAD_REQUEST);
        } catch (Exception ex) {
            return new Response("Unexpected Error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response transfer(String destAcc, String sourceAcc, String amount) {
        try {
            AccountStorage accountStorage = AccountStorage.getInstance();
            TransactionStorage transactionStorage = TransactionStorage.getInstance();

            Account sourceAccount = accountStorage.getAccount(sourceAcc);
            Account destAccount = accountStorage.getAccount(destAcc);

            if (sourceAccount == null || destAccount == null) {
                return new Response("Source or destination account not found", Status.NOT_FOUND);
            }

            if (sourceAccount.equals(destAccount)) {
                return new Response("Source and destination accounts must be different", Status.BAD_REQUEST);
            }

            double amountValue = Double.parseDouble(amount);
            if (amountValue <= 0) {
                return new Response("Amount must be greater than 0", Status.BAD_REQUEST);
            }

            if (sourceAccount.getBalance() < amountValue) {
                return new Response("Not enough funds available", Status.BAD_REQUEST);
            }

            sourceAccount.setBalance(sourceAccount.getBalance() - amountValue);
            destAccount.setBalance(amountValue + destAccount.getBalance());

            Transaction transaction = new Transaction(TransactionType.TRANSFER, sourceAccount, destAccount, amountValue);
            transactionStorage.addTransaction(transaction);
            return new Response("Transaction Successful!", Status.CREATED);
        } catch (NumberFormatException ex) {
            return new Response("Amount must be a number", Status.BAD_REQUEST);
        } catch (Exception ex) {
            return new Response("Unexpected Error", Status.INTERNAL_SERVER_ERROR);
        }
    }

}