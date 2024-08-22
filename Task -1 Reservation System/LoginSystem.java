package oasis.task1;

import oasis.task1.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class LoginSystem {

    // Method to authenticate existing users
    public static boolean authenticateUser(String username, String password) {
        String query = "SELECT * FROM Users WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to register new users
    public static boolean registerUser(String username, String password) {
        String query = "INSERT INTO Users (username, password) VALUES (?, ?)";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to get user_id by username
    public static int getUserId(String username) {
        String query = "SELECT user_id FROM Users WHERE username = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("user_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if the user is not found
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Create a single Scanner instance
        boolean running = true;

        while (running) {
            System.out.println("Welcome to the Online Reservation System");
            System.out.println("1. Sign Up");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            System.out.print("Choose an option: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline

                switch (choice) {
                    case 1:
                        // Sign Up
                        System.out.print("Enter new username: ");
                        String signupUsername = scanner.nextLine();
                        System.out.print("Enter new password: ");
                        String signupPassword = scanner.nextLine();

                        if (registerUser(signupUsername, signupPassword)) {
                            System.out.println("Registration successful! You can now log in.");
                        } else {
                            System.out.println("Registration failed. Please try again.");
                        }
                        break;

                    case 2:
                        // Login
                        System.out.print("Username: ");
                        String loginUsername = scanner.nextLine();
                        System.out.print("Password: ");
                        String loginPassword = scanner.nextLine();

                        if (authenticateUser(loginUsername, loginPassword)) {
                            System.out.println("Login successful!");
                            // Pass the Scanner object to the reservation system
                            ReservationSystem.showReservationMenu(loginUsername, scanner);
                        } else {
                            System.out.println("Invalid login credentials.");
                        }
                        break;

                    case 3:
                        // Exit
                        System.out.println("Exiting the system. Goodbye!");
                        running = false;
                        break;

                    default:
                        System.out.println("Invalid option selected. Please try again.");
                        break;
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume the invalid input
            }
        }

        scanner.close();
    }
}
