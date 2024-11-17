/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

import core.models.Account;
import java.util.ArrayList;


/**
 *
 * @author mfrey
 */
public class AccountStorage {
     private static AccountStorage instance;
     private ArrayList<Account> accounts;
     
     private AccountStorage(){
         this.accounts = new ArrayList<>();
     }
     
     public static AccountStorage getInstance(){
         if (instance == null){
             instance = new AccountStorage();
         }
         return instance;
     }
     
     public boolean addAccount(Account account){
         for (Account acc : this.accounts){
             if (account.getId().equals(acc.getId())){
                 return false;
             }
         }
         this.accounts.add(account);
         return true;
     }
     
    public Account getAccount(String accountId){
        for (Account acc : this.accounts){
            if (acc.getId().equals(accountId)){
                return acc;
            }
    }
        return null;
    }
    
    public ArrayList<Account> getAllAcounts(){
        this.accounts.sort((a1, a2) -> a1.getId().compareTo(a2.getId()));
        return this.accounts;
    }
}
