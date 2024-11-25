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
public class Withdraw extends Transaction {

    public Withdraw(Account sourceAccount, Account destinationAccount, double amount) {
        super(sourceAccount, null, amount);
    }

    @Override
    public String getType() {
        return "Withdraw";
    }

    @Override
    public void makeTransaction() {
        sourceAccount.withdraw(amount);
    }
    
}
