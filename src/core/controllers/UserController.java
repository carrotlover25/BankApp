/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;


import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.User;
import core.models.storage.UserStorage;


public class UserController {
  public static Response registerUser(String id, String firstname, String lastname, String age) {
        try {
            int idInt, ageInt;
            
            try {
                idInt = Integer.parseInt(id);
                if (idInt < 0 || idInt > 999999999) {
                    return new Response("ID must have between 1 and 9 numbers", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Type numbers only", Status.BAD_REQUEST);
            }

            if (firstname.trim().isEmpty()) {
                return new Response("Field(s) cannot be empty", Status.BAD_REQUEST);
            }

            if (lastname.trim().isEmpty()) {
                return new Response("Field(s) cannot be empty", Status.BAD_REQUEST);
            }

            try {
                ageInt = Integer.parseInt(age);
                if (ageInt < 18) {
                    return new Response("You must be older than 18 to create an account", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Type numbers only", Status.BAD_REQUEST);
            }

            
            User user = new User(idInt, firstname, lastname, ageInt);
            UserStorage storage = UserStorage.getInstance();
            
            if (!storage.addUser(user)) {
                return new Response("ID already exists", Status.BAD_REQUEST);
            }
            
            return new Response("User created successfully!", Status.CREATED);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    
    
}