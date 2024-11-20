/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;


import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.User;
import core.models.storage.UserStorage;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class UserController {
  public static Response registerUser(String id, String firstname, String lastname, String age) {
        try {
            int idInt, ageInt;
            
            try {
                idInt = Integer.parseInt(id);
                if (idInt < 0 || idInt > 999999999) {
                    return new Response("Id must have between 1 and 9 numbers", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Id must be numeric", Status.BAD_REQUEST);
            }

            if (firstname.trim().isEmpty()) {
                return new Response("First name cannot be empty", Status.BAD_REQUEST);
            }

            if (lastname.trim().isEmpty()) {
                return new Response("Last name cannot be empty", Status.BAD_REQUEST);
            }

            try {
                ageInt = Integer.parseInt(age);
                if (ageInt < 18) {
                    return new Response("Age must be 18 or older", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Age must be numeric", Status.BAD_REQUEST);
            }

            
            User user = new User(idInt, firstname, lastname, ageInt);
            UserStorage storage = UserStorage.getInstance();
            
            if (!storage.addUser(user)) {
                return new Response("User ID already exists", Status.BAD_REQUEST);
            }
            
            return new Response("User created successfully", Status.CREATED);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response readUser(String id) {
        try {
            int idInt;
            try {
                idInt = Integer.parseInt(id);
                if (idInt < 0 || idInt > 999999999) {
                    return new Response("ID must have between 1 and 9 digits", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("ID must be numeric", Status.BAD_REQUEST);
            }

            UserStorage storage = UserStorage.getInstance();
            User user = storage.getUser(idInt);
            if (user == null) {
                return new Response("User not found", Status.NOT_FOUND);
            }

            return new Response("User found", Status.OK, user);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    public static void refreshUsers(DefaultTableModel model){
        try {
            UserStorage userStorage = UserStorage.getInstance();
            ArrayList<User> users = userStorage.getAllUsers();
            users.sort((user1, user2) -> Integer.compare(user1.getId(), user2.getId()));
            for (User user : users) {
            model.addRow(new Object[]{user.getId(), user.getFirstname() + " " + user.getLastname(), user.getAge(), user.getNumAccounts()});
        }
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Error", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}