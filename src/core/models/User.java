/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models;

import java.util.ArrayList;
import core.models.Account;



public class User {
    private int id;
    private String firstname;
    private String lastname;
    private int age;
    private ArrayList<Account> accounts;

    public User(int id, String firstname, String lastname, int age) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.accounts = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public int getAge() {
        return age;
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public Object getNumAccounts() {
        return this.accounts.size();
    }
    
    
}