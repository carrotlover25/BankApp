/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.transaction;

import core.models.Account;

/**
 *
 * @author mfrey
 */
public class Deposit extends Transaction {

    public Deposit(Account sourceAccount, Account destinationAccount, double amount) {
        super(null, destinationAccount, amount);
    }

   
    @Override
    public String getType() {
        return "Deposit";
    }

    @Override
    public void makeTransaction() {
        destinationAccount.deposit(amount);
    }
    
}
