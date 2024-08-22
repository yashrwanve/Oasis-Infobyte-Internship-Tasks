package oasis.task2;

import java.util.HashMap;
import java.util.Map;

public class ATMImplementation implements ATMOperations {
    private Map<String, User> users;
    private User currentUser;

    public ATMImplementation() {
        users = new HashMap<>();
        // Adding some users for testing
        users.put("user1", new User("user1", "1234", 1000));
        users.put("user2", new User("user2", "5678", 2000));
    }

    @Override
    public boolean authenticateUser(String userId, String pin) {
        if (users.containsKey(userId) && users.get(userId).getPin().equals(pin)) {
            currentUser = users.get(userId);  // Set the current user
            return true;
        }
        return false;
    }

    @Override
    public void showTransactionHistory() {
        if (currentUser == null) {
            System.out.println("No user is currently logged in.");
            return;
        }
        System.out.println("Transaction History:");
        for (String transaction : currentUser.getTransactionHistory()) {
            System.out.println(transaction);
        }
    }

    @Override
    public void withdrawMoney(double amount) {
        if (currentUser == null) {
            System.out.println("No user is currently logged in.");
            return;
        }
        if (amount > currentUser.getBalance()) {
            System.out.println("Insufficient balance!");
        } else {
            currentUser.setBalance(currentUser.getBalance() - amount);
            currentUser.addTransaction("Withdrawn: $" + amount);
            System.out.println("Withdrawal successful. New balance: $" + currentUser.getBalance());
        }
    }

    @Override
    public void depositMoney(double amount) {
        if (currentUser == null) {
            System.out.println("No user is currently logged in.");
            return;
        }
        currentUser.setBalance(currentUser.getBalance() + amount);
        currentUser.addTransaction("Deposited: $" + amount);
        System.out.println("Deposit successful. New balance: $" + currentUser.getBalance());
    }

    @Override
    public void transferMoney(String recipientId, double amount) {
        if (currentUser == null) {
            System.out.println("No user is currently logged in.");
            return;
        }
        if (!users.containsKey(recipientId)) {
            System.out.println("Recipient user ID not found!");
            return;
        }
        if (amount > currentUser.getBalance()) {
            System.out.println("Insufficient balance!");
        } else {
            User recipient = users.get(recipientId);
            currentUser.setBalance(currentUser.getBalance() - amount);
            recipient.setBalance(recipient.getBalance() + amount);
            currentUser.addTransaction("Transferred: $" + amount + " to " + recipientId);
            recipient.addTransaction("Received: $" + amount + " from " + currentUser.getUserId());
            System.out.println("Transfer successful. New balance: $" + currentUser.getBalance());
        }
    }
}
