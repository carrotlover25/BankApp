  
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Account;
import core.models.User;
import core.models.storage.UserStorage;
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
                return new Response("ID must be numeric", Status.BAD_REQUEST);
            }

            try {
            balance = Double.parseDouble(initialBalance);
            if (balance < 0) {
                return new Response("Initial balance cannot be negative", Status.BAD_REQUEST);
            }
        } catch (NumberFormatException ex) {
            return new Response("Initial balance must be numeric", Status.BAD_REQUEST);
        }
            UserStorage storage = UserStorage.getInstance();
            User user = storage.getUser(userIdInt);
            if (user == null) {
                return new Response("Accounts can only be created for registered users", Status.NOT_FOUND);
            }

            String accountId = generateAccountId();
            Account account = new Account(accountId, user);
            user.addAccount(account);

            return new Response("Account created successfully", Status.CREATED);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    private static String generateAccountId() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            sb.append(rand.nextInt(10));
        }
        sb.append("-");
        for (int i = 0; i < 6; i++) {
            sb.append(rand.nextInt(10));
        }
        sb.append("-");
        for (int i = 0; i < 2; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }
}
