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
   public static Response createTransaction(String type, String sourceId, String destinationId, String amount) {
        try {
            double amountValue;
            try {
                amountValue = Double.parseDouble(amount);
                if (amountValue <= 0) {
                    return new Response("Amount must be greater than zero", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Amount must be numeric", Status.BAD_REQUEST);
            }
            AccountStorage accountStorage = AccountStorage.getInstance();
            TransactionStorage transactionStorage = TransactionStorage.getInstance();
            Account sourceAccount = null;
            Account destinationAccount = null;

            if (!sourceId.isEmpty()) {
                sourceAccount = accountStorage.getAccount(sourceId);
                if (sourceAccount == null) {
                    return new Response("Source account not found", Status.NOT_FOUND);
                }
            }

            if (!destinationId.isEmpty()) {
                destinationAccount = accountStorage.getAccount(destinationId);
                if (destinationAccount == null) {
                    return new Response("Destination account not found", Status.NOT_FOUND);
                }
            }

            // Validar el tipo de transacción
            TransactionType transactionType;
            try {
                transactionType = TransactionType.valueOf(type.toUpperCase());
            } catch (IllegalArgumentException ex) {
                return new Response("Invalid transaction type", Status.BAD_REQUEST);
            }
            switch (transactionType) {
                case DEPOSIT:
                    if (destinationAccount == null) {
                        return new Response("Destination account is required for deposit", Status.BAD_REQUEST);
                    }
                    destinationAccount.deposit(amountValue);
                    break;

                case WITHDRAW:
                    if (sourceAccount == null) {
                        return new Response("Source account is required for withdrawal", Status.BAD_REQUEST);
                    }
                    if (sourceAccount.getBalance() < amountValue) {
                        return new Response("Insufficient funds in source account", Status.BAD_REQUEST);
                    }
                    sourceAccount.withdraw(amountValue);
                    break;

                case TRANSFER:
                    if (sourceAccount == null || destinationAccount == null) {
                        return new Response("Both source and destination accounts are required for transfer", Status.BAD_REQUEST);
                    }
                    if (sourceAccount.getBalance() < amountValue) {
                        return new Response("Insufficient funds in source account for transfer", Status.BAD_REQUEST);
                    }
                    sourceAccount.withdraw(amountValue);
                    destinationAccount.deposit(amountValue);
                    break;

                default:
                    return new Response("Unsupported transaction type", Status.BAD_REQUEST);
            }
            
            Transaction transaction = new Transaction(transactionType, sourceAccount, destinationAccount, amountValue);
            transactionStorage.addTransaction(transaction);

            return new Response("Transaction created successfully", Status.CREATED, transaction);

        } catch (Exception ex) {
            return new Response("Unexpected error: " + ex.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response getAllTransactions() {
        try {
            TransactionStorage transactionStorage = TransactionStorage.getInstance();
            return new Response("Transactions retrieved successfully", Status.OK, transactionStorage.getAllTransactions());
        } catch (Exception ex) {
            return new Response("Unexpected error: " + ex.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

}
