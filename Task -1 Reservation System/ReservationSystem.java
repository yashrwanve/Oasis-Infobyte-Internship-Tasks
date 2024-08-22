package oasis.task1;

import oasis.task1.CancellationSystem;
import oasis.task1.DatabaseConnector;
import oasis.task1.LoginSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class ReservationSystem {

    // Method to make a reservation
    public static void makeReservation(int userId, String trainNumber, String trainName, String classType, String journeyDate, String fromPlace, String toPlace, String pnrNumber) {
        String query = "INSERT INTO Reservations (user_id, train_number, train_name, class_type, journey_date, from_place, to_place, pnr_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setString(2, trainNumber);
            stmt.setString(3, trainName);
            stmt.setString(4, classType);
            stmt.setDate(5, java.sql.Date.valueOf(journeyDate));
            stmt.setString(6, fromPlace);
            stmt.setString(7, toPlace);
            stmt.setString(8, pnrNumber);
            stmt.executeUpdate();
            System.out.println("Reservation successful!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Show reservation menu after login
    public static void showReservationMenu(String username, Scanner scanner) {
        int userId = LoginSystem.getUserId(username);
        if (userId == -1) {
            System.out.println("User ID not found. Exiting...");
            return;
        }

        boolean running = true;

        while (running) {
            System.out.println("Reservation System Menu");
            System.out.println("1. Make a Reservation");
            System.out.println("2. Cancel a Reservation");
            System.out.println("3. Logout");

            System.out.print("Choose an option: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline

                switch (choice) {
                    case 1:
                        // Make a Reservation
                        System.out.print("Train Number: ");
                        String trainNumber = scanner.nextLine();
                        System.out.print("Train Name: ");
                        String trainName = scanner.nextLine();
                        System.out.print("Class Type: ");
                        String classType = scanner.nextLine();
                        System.out.print("Journey Date (YYYY-MM-DD): ");
                        String journeyDate = scanner.nextLine();
                        System.out.print("From Place: ");
                        String fromPlace = scanner.nextLine();
                        System.out.print("To Place: ");
                        String toPlace = scanner.nextLine();
                        System.out.print("PNR Number: ");
                        String pnrNumber = scanner.nextLine();

                        makeReservation(userId, trainNumber, trainName, classType, journeyDate, fromPlace, toPlace, pnrNumber);
                        break;

                    case 2:
                        // Cancel a Reservation
                        System.out.print("PNR Number: ");
                        String pnrToCancel = scanner.nextLine();
                        CancellationSystem.cancelReservation(pnrToCancel);
                        break;

                    case 3:
                        // Logout
                        System.out.println("Logging out...");
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
    }
}
