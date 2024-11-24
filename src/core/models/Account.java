
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models;
import core.models.User;


public class Account {
    private final String id;
    private final User owner;
    private double balance;

    public Account(String id, User owner) {
        this.id = id;
        this.owner = owner;
        this.balance = 0;
    }
    
    public Account(String id, User owner, double balance) {
        this.id = id;
        this.owner = owner;
        this.balance = balance;
        
    }
    
    public String getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    
 
}
