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
public class Transfer extends Transaction {

    public Transfer(Account sourceAccount, Account destinationAccount, double amount) {
        super(sourceAccount, destinationAccount, amount);
    }

    @Override
    public String getType() {
        return "Transfer";
    }

    @Override
    public void makeTransaction() {
       // if (sourceAccount.getBalance() >= amount){
        sourceAccount.withdraw(amount);
        destinationAccount.deposit(amount);   
       // }
        
        
    }

   
    
    
}
