/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;
 
import core.models.User;
import java.util.ArrayList;

/**
 *
 * @author mfrey
 */
public class UserStorage {
    
    private static UserStorage instance;

   
    private ArrayList<User> users;
    
    private UserStorage(){
        this.users = new ArrayList<>();
    }
    
    public static UserStorage getInstance() {
        if (instance == null){
            instance = new UserStorage();
        }
     return instance;
    }
    
    public boolean addUser(User user){ //añadir user
        for (User u : this.users){
            if (u.getId() == user.getId()){
                return false;
            }
        }
        this.users.add(user);
        return true;
    }
    
    public User getUser(int id){ // obtener user
        for (User user : this.users){
            if (user.getId() == id){
                return user;
            }
        }
        return null;
    }
        
    public ArrayList<User> getAllUsers(){
        return this.users;
    }
}
