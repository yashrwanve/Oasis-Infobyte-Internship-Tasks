package oasis.task2;

public interface ATMOperations {
    boolean authenticateUser(String userId, String pin);
    void showTransactionHistory();
    void withdrawMoney(double amount);
    void depositMoney(double amount);
    void transferMoney(String recipientId, double amount);
}
