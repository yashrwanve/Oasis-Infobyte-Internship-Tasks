package oasis.task2;

import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ATMImplementation atm = new ATMImplementation();

        System.out.println("Welcome to the ATM system");
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter User PIN: ");
        String userPin = scanner.nextLine();

        if (atm.authenticateUser(userId, userPin)) {
            int choice;
            do {
                System.out.println("\nATM Menu:");
                System.out.println("1. Transaction History");
                System.out.println("2. Withdraw Money");
                System.out.println("3. Deposit Money");
                System.out.println("4. Transfer Money");
                System.out.println("5. Quit");
                System.out.print("Choose an option: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        atm.showTransactionHistory();
                        break;
                    case 2:
                        System.out.print("Enter amount to withdraw: ");
                        double withdrawAmount = scanner.nextDouble();
                        atm.withdrawMoney(withdrawAmount);
                        break;
                    case 3:
                        System.out.print("Enter amount to deposit: ");
                        double depositAmount = scanner.nextDouble();
                        atm.depositMoney(depositAmount);
                        break;
                    case 4:
                        System.out.print("Enter recipient user ID: ");
                        String recipientId = scanner.next();
                        System.out.print("Enter amount to transfer: ");
                        double transferAmount = scanner.nextDouble();
                        atm.transferMoney(recipientId, transferAmount);
                        break;
                    case 5:
                        System.out.println("Thank you for using the ATM. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } while (choice != 5);
        } else {
            System.out.println("Authentication failed. Exiting...");
        }
    }
}
