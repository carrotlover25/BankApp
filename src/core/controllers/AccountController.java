package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Account;
import core.models.User;
import core.models.storage.AccountStorage;
import core.models.storage.UserStorage;
import java.util.ArrayList;
import java.util.Random;



public class AccountController {

    public static Response createAccount(String userId, String initialBalance) {
        try {   
            int userIdInt;
            double balance;
            try {
                userIdInt = Integer.parseInt(userId);
                if (userIdInt < 0 || userIdInt > 999999999) {
                    return new Response("ID must have between 1 and 9 digits", Status.BAD_REQUEST);
                }
                
            } catch (NumberFormatException ex) {
                return new Response("Type numbers only", Status.BAD_REQUEST);
            }

            try {
                balance = Double.parseDouble(initialBalance);
                if (balance < 0) {
                    return new Response("Initial balance cannot be negative", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Type numbers only", Status.BAD_REQUEST);
            }
            UserStorage storage = UserStorage.getInstance();
            User user = storage.getUser(userIdInt);
            if (user == null) {
                return new Response("Accounts can only be created for registered users", Status.NOT_FOUND);
            }

            String accountId = generateAccountId();
            Account account = new Account(accountId, user, balance);
            user.addAccount(account);
            
            AccountStorage.getInstance().addAccount(account);

            return new Response("Account created successfully", Status.CREATED);
        } catch (Exception ex) {
            return new Response("Unexpected error: " + ex.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    private static String generateAccountId() {
        Random random = new Random();
        int first = random.nextInt(1000);
        int second = random.nextInt(1000000);
        int third = random.nextInt(100);

        String accountId = String.format("%03d", first) + "-" + String.format("%06d", second) + "-" + String.format("%02d", third);
        return accountId;
    }
    
    public static Response getAllAccounts() {
        try {
            AccountStorage accountStorage = AccountStorage.getInstance();
            ArrayList<Account> accounts = accountStorage.getAllAccounts();
            if (accounts.isEmpty()) {
                return new Response("No accounts found", Status.NOT_FOUND);
            }

            accounts.sort((acc1, acc2) -> acc1.getId().compareTo(acc2.getId()));

            return new Response("Accounts retrieved successfully", Status.OK, accounts);  // Devolver las cuentas en la respuesta
        } catch (Exception ex) {
            return new Response("An unexpected error occurred: " + ex.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

   
}